var regex = {
    email: /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i,
    phone: /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/
}

function isNullorUndefined(val) {
    if (val == "" || val == null || val == undefined)
        return true;
    else
        return false;
}