console.log("admin user");


// this function is used in add_contact.html file and is used to show uploaded image 
document.querySelector("#image_file_input").addEventListener('change', function(event) {

    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function() {
        document.querySelector("#upload_image_preview").setAttribute("src", reader.result);
    };

    reader.readAsDataURL(file);

})