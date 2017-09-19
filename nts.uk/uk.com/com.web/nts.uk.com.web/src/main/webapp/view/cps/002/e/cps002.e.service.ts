module cps002.e.service{
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let basePath = "basic/organization/employee";
    let paths = {
        getEmlCode:"/getGenerateEmplCode",
        getCardNo: "/getGenerateCardNo"
    };
    
    export function getEmlCode(startLetters){
        return ajax("com", basePath+paths.getEmlCode, startLetters);
    }
    
    export function getCardNo(startLetters){
        return ajax("com", basePath+paths.getCardNo, startLetters);
    }
}