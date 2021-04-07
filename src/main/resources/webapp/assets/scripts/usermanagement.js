$(function () {
    user.eventHandlers();
    $("#txtUserName").val("plakhan");
    $("#txtPassword").val("plakhan");
});

var user = {
    details: {
    },
    login: function (username, password) {
        var $frmLogin = $("#frmLogin");
        var hasError = false;
        if (isNullorUndefined(username)) {
            $frmLogin.find(".username-error").html("Invalid username");
            hasError = true;
        }
        else
            $frmLogin.find(".username-error").html("");

        if (isNullorUndefined(password)) {
            $frmLogin.find(".password-error").html("Invalid password");
            hasError = true;
        }

        if (!hasError) {
            deleteCookie("token");
            api.getCall("users/login", { "username": username, "password": password }, function (result) {
                main.loadpage("booklistings");
                user.resetForm($frmLogin);
            });

        }
    },
    register: function (dataJson, thumbnail) {
        var $frmRegister = $("#frmRegister");
        var hasError = false;
        if (isNullorUndefined(dataJson.firstname)) {
            $frmRegister.find(".fname-error").html("Invalid first name");
            hasError = true;
        }
        else
            $frmRegister.find(".fname-error").html("");

        if (isNullorUndefined(dataJson.email) || !regex.email.test(dataJson.email)) {
            $frmRegister.find(".email-error").html("Invalid email address");
            hasError = true;
        }
        else
            $frmRegister.find(".email-error").html("");

        if (isNullorUndefined(dataJson.username)) {
            $frmRegister.find(".username-error").html("Invalid user name");
            hasError = true;
        }
        else
            $frmRegister.find(".username-error").html("");

        if (isNullorUndefined(dataJson.password)) {
            $frmRegister.find(".password-error").html("Invalid password");
            hasError = true;
        }
        else
            $frmRegister.find(".password-error").html("");

        if (!isNullorUndefined(dataJson.phone)) {
            if (!regex.phone.test(dataJson.phone))
                $frmRegister.find(".phone-error").html("Invalid phone number");
            else
                $frmRegister.find(".phone-error").html("");
        }

        if (!hasError) {
            api.postCall("users/register", dataJson, function (result) {
                var formdata = new FormData();
                formdata.append("file", thumbnail);
                api.uploadFileCall("users/thumbnail?userId=" + result.id, formdata, function (result) {
                    deleteCookie("token");
                    main.loadpage("login");
                    this.resetForm($frmRegister);
                });
            });
        }
    },
    sendForgotPasswordLink: function (email) {
        var $frmForgotPassword = $("#frmForgotPassword");
        var hasError = false;
        if (isNullorUndefined(email)) {
            $frmForgotPassword.find(".email-error").html("Invalid email");
            hasError = true;
        }
        else
            $frmForgotPassword.find(".email-error").html("");

        if (!hasError) {
            api.postCall("users/resetpassword", {"email":email}, function () {
                this.resetForm($frmForgotPassword);
                alert("We have sent you a temporary password on email.");
            });
        }
    },
    changePassword: function () { },
    resetForm: function ($form) {
        $form.find(".error-note").each(function () { $(this).html(""); });
        $form.find("input.form-control").not(".btn").each(function () { $(this).val(""); });
    },
    eventHandlers: function () {
        $("#btnLogin").on("click", function () {
            var userName = $("#txtUserName").val();
            var password = $("#txtPassword").val();
            user.login(userName, password);
        });

        $("#linkCreateAccount").off("click").on("click", function () {
            $("#frmLogin").slideUp();
            $("#frmRegister").slideDown();
            $("#frmForgotPassword").slideUp();
            $(this).hide();
            $("#linkLogIn").show();
        });

        $("#linkLogIn").off("click").on("click", function () {
            $("#frmLogin").slideDown();
            $("#frmRegister").slideUp();
            $("#frmForgotPassword").slideUp();
            $(this).hide();
            $("#linkCreateAccount").show();
        });

        $("#linkForgotPassword").off("click").on("click", function () {
            $("#frmLogin").slideUp();
            $("#frmRegister").slideUp();
            $("#frmForgotPassword").slideDown();
            $("#linkLogIn").show();
            $("#linkCreateAccount").hide();
        });

        $("#fileUserProfile").on("change", function () {
            if (this.files && this.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#imgUserProfile')
                        .attr('src', e.target.result);
                };
                reader.readAsDataURL(this.files[0]);
            }
        });

        $("#btnRegister").on("click", function () {
            var data = {};
            data["firstname"] = $("#txtFirstName").val();
            data["lastname"] = $("#txtLastName").val();
            data["email"] = $("#txtEmail").val();
            data["phone"] = $("#txtPhone").val();
            data["username"] = $("#txtNewUserName").val();
            data["password"] = $("#txtNewPassword").val();
            user.register(data, document.getElementById('fileUserProfile').files[0]);
        });

        $("#btnForgotPoassword").off("click").on("click", function () {
            user.sendForgotPasswordLink($("#txtForgotPasswordEmail").val());
        });

        $("#btnSaveChanges").off("click").on("click", function () {
            var newJson = {};
            newJson["firstname"] = $("#txtFirstName").val();
            newJson["lastname"] = $("#txtLastName").val();
            newJson["email"] = $("#txtEmail").val();
            newJson["phone"] = $("#txtPhone").val();
            var thumbnail = document.getElementById('fileUserPhotoUrl').files[0];
            var formdata = new FormData();
            formdata.append("file", thumbnail);
            api.putCall("users/edit", newJson, function (result) {
                alert("User profile successfully updated!");
                api.uploadFileCall("users/thumbnail?userId=" + parseInt($("#txtUserId").val()), formdata, function (result) {
                    main.loadpage("booklistings");
                });
            });
            
        });

        $("#fileUserPhotoUrl").on("change", function () {
            if (this.files && this.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#imgUserPhoto')
                        .attr('src', e.target.result);
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    },
    loadEditPage: function () {
        var $editProfileForm = $("#edit_profile_container");
        var userJson = $.parseJSON(localStorage.getItem("user"));
        var thumbnailUrl = hostpath + "assets/books/no-image.jpg";
        if (userJson.thumbnailUrl != null && userJson.thumbnailUrl != undefined)
            thumbnailUrl = hostpath+userJson.thumbnailUrl;
        $editProfileForm.find("img").attr("src", thumbnailUrl);
        $editProfileForm.find("#txtUserId").val(userJson.id);
        $editProfileForm.find("#txtUserName").val(userJson.username);
        $editProfileForm.find("#txtFirstName").val(userJson.firstname);
        $editProfileForm.find("#txtLastName").val(userJson.lastname);
        $editProfileForm.find("#txtEmail").val(userJson.email);
        $editProfileForm.find("#txtPhone").val(userJson.phone);
    },
    uploadProfile: function () {
    }
}