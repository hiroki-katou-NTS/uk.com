module cps002.g.service{
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
   
    let path = "ctx/bs/person/info/setting/user/update/updateUserSetting";
    
    export function setUserSetting(objectSending){
        return ajax("com", path, objectSending);
    }
}