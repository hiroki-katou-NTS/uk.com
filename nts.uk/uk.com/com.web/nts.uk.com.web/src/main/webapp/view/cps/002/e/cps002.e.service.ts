module cps002.e.service{
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let basePath = "basic/organization/employee";
    let paths = {
        getEmlCode:"/getGenerateEmplCode",
        getCardNo: ""
    };
    
    export function getEmlCode(startLetter){
        return ajax("com", basePath+paths.getEmlCode, startLetter);
    }
    
    export function getCardNo(){
    }
}