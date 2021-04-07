$(function () {
    main.init();
});

var hostpath = "http://localhost:8080/";

var main = {
    user: {},
    init: function () {
        this.attachHandlers();
        //load login page if no user
        if (getCookie("token") == null || getCookie("token") == undefined || getCookie("token") == "") {
            this.loadpage("login",true);
        }
        else
            this.loadpage("booklistings",true);
    },
    loadpage: function (path, isFromMain = false) {
        var $iframe = $("#default_content_container");
        if (!isFromMain) {
            if ($iframe.length == 0)
                $iframe = $(window.parent.document.body).find("#default_content_container");
        }

        $(window.parent.document.body).find(".user-profile-container").hide();

        if (path.indexOf(".html") < 0)
            $iframe.attr("src", path + ".html");
        else
            $iframe.attr("src", path);
    },
    loadData: function ($div, templateName, data, callback) {
        $div.html("");
        var mainHTML = "";
        var templateHTML = $("#" + templateName).html();
        if (data.length == undefined)
        {
            var keys = Object.keys(data)
            $.each(keys, function (i, o) {
                templateHTML = templateHTML.replace("{{" + o + "}}", data[o] == "" || data[o] == null || data[o] == undefined ? "Not specified" : data[o]);
            });
            mainHTML += templateHTML;
        }
        else {
            $.each(data, function (ind, obj) {
                var keys = Object.keys(obj)
                $.each(keys, function (i, o) {
                    templateHTML = templateHTML.replace("{{" + o + "}}", obj[o] == "" || obj[o] == null || obj[o] == undefined ? "Not specified" : obj[o]);
                });
                mainHTML += templateHTML;
            });
        }
        $div.html(mainHTML);
        callback();
    },
    attachHandlers: function () {
        $(".user-profile img").on("click", function () {
            var $menuContainer = $(window.parent.document.body).find(".user-profile-container");
            var userJson = $.parseJSON(localStorage.getItem("user"));
            var menuHTML = $menuContainer.html();
            var thumbnailUrl = hostpath + "assets/books/no-image.jpg";
            if (userJson.thumbnailUrl != null && userJson.thumbnailUrl != undefined)
                thumbnailUrl = userJson.thumbnailUrl;

            menuHTML = menuHTML.replace(/{{thumbnailUrl}}/g, thumbnailUrl);
            menuHTML = menuHTML.replace(/{{firstName}}/g, userJson.firstname);
            menuHTML = menuHTML.replace(/{{lastName}}/g, userJson.lastname);
            menuHTML = menuHTML.replace(/{{email}}/g, userJson.email);
            
            $menuContainer.html(menuHTML);
            $menuContainer.toggle();

            $("#btnLogout").on("click", function () {
                localStorage.removeItem('user');
                deleteCookie("token");
                main.loadpage("login");
            });
        });
    },
    callback: function () { },
    showError: function (msg) {
        $.smallBox({
            title: msg,
            content: "",
            color: "#e08080",
            iconSmall: "fa fa-check bounce animated",
            timeout: 2000,
            width: 10
        });
    },
    showAlert: function (msg) {
        $.smallBox({
            title: msg,
            content: "",
            color: "#49cc90",
            iconSmall: "fa fa-check bounce animated",
            timeout: 2000,
            width: 10
        });
    },
}