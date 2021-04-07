var api = {
    postCall: function (apiURL, data, callback) {
        var token = "";
        var headerJson = {};
        if (apiURL.indexOf("/register") < 0)
            token = getCookie("token");
        if (token != "")
            headerJson = { 'Authorization': 'Bearer ' + token }

        var _call = $.ajax({
            url: hostpath+apiURL,
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json",
            headers: headerJson,
            success: function (result) {
                callback(result); 
            },
            error: function (er) { }
        });
    },
    getCall: function (apiURL, data, callback) {

        var token = "";
        var headerJson = {};
        if (apiURL.indexOf("/login") < 0)
            token=getCookie("token");
        if (token != "")
            headerJson = { 'Authorization': 'Bearer ' + token, 'accept': '*/*' }

        var _call = $.ajax({
            url: hostpath + apiURL,
            type: "GET",
            data: data,
            contentType: "application/json",
            dataType: "json",
            headers: headerJson,
            success: function (result) {
                if (apiURL.indexOf("/login") >= 0)
                    setCookie("token", result["token"],365);
                callback(result); 
            },
            error: function (er) {
            }
        });
    },
    putCall: function (apiURL, data, callback) {
        var headerJson = {};
        var token=getCookie("token");
        if (token != "")
            headerJson = { 'Authorization': 'Bearer ' + token }

        var _call = $.ajax({
            url: hostpath + apiURL,
            type: "PUT",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json",
            headers: headerJson,
            success: function (result) {
                callback(result);
            }
        });
    },
    uploadFileCall: function(apiURL, data, callback) {
        var headerJson = {};
        var token = getCookie("token");
        if (token != "")
            headerJson = { 'Authorization': 'Bearer ' + token , 'accept': '*/*'}

        var _call = $.ajax({
            url: hostpath + apiURL,
            type: "PUT",
            data: data,
            processData: false,
            contentType: false,
            mimeType: "multipart/form-data",
            headers: headerJson,
            success: function (result) {
                callback(result);
            }
        });
    },
    deleteCall: function (apiURL, callback) {
        var headerJson = {};
        var token = getCookie("token");
        if (token != "")
            headerJson = { 'Authorization': 'Bearer ' + token }

        var _call = $.ajax({
            url: hostpath + apiURL,
            type: "DELETE",
            headers: headerJson,
            success: function (result) {
                callback(result);
            }
        });
    },
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteCookie(cname) {
    document.cookie = cname + "= ; expires = Thu, 01 Jan 1970 00:00:00 GMT";
}