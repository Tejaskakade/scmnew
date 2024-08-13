package com.scmnew.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scmnew.helper.AppConstant;
import com.scmnew.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;            //we created a cloudinary bean in AppCinfig class
    
     //this constructor is work  as autowiring the cloudinary
    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String UploadImage(MultipartFile contactImage, String filename) {
       
        // we have write code to upload image to  server like cloud, cloudinary

       

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];

            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id",filename
            ));

            return this.getUrlFromPublicId(filename);

        } catch (IOException e) {
            
            e.printStackTrace();
            return null;
        }

        // and return the URL


       
    }


    @Override
    public String getUrlFromPublicId(String publicId) {
        
        return cloudinary
        .url()
        .transformation(
            new Transformation<>().width(AppConstant.CONTACT_IMAGE_WIDTH).height(AppConstant.CONTACT_IMAGE_HEIGHT).crop(AppConstant.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);
    }

}
