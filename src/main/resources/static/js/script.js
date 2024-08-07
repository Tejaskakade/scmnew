console.log("script is loaded");


// change theme work start

let currentTheme = getTheme();
//initially 

document.addEventListener("DOMContentLoaded", () => {
    changeTheme(currentTheme);
});



//todo
function changeTheme() {

    //set to web page
    changePageTheme(currentTheme, currentTheme);


    // set the listner to change theme button
    const changeThemeButton = document.querySelector('#theme_change_button')
        // currentTheme == 'light' ? 'dark' : 'light';

    changeThemeButton.addEventListener('click', (event) => {

        let oldTheme = currentTheme;

        console.log("change theme button clicked");

        if (currentTheme == "dark") {
            currentTheme = "light"

        } else {
            currentTheme = "dark";

        }
        changePageTheme(currentTheme, oldTheme);

    });

}

//set theme to local  storage
function setTheme(theme) {
    localStorage.setItem("theme", theme);

}


//get theme for local storage
function getTheme() {
    let theme = localStorage.getItem("theme")
    return theme ? theme : "light";

}

function changePageTheme(theme, oldTheme) {

    // localstorage me update karenge
    setTheme(currentTheme);

    // remove the current theme
    document.querySelector('html').classList.remove(oldTheme);

    // set the current theme
    document.querySelector('html').classList.add(theme);

    // change the text of button
    document.querySelector("#theme_change_button").querySelector('span').textContent = theme == 'light' ? 'dark' : 'light';

}

// change theme work end