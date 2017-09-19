module cps002.f.service{
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let basePath = "ctx/bs/person/info/category";
    let paths = {
        perInfoCtgHasItems:"/find/perInfoCtgHasItems"
    };
    
    export function getPerInfoCtgHasItems(startLetter){
        return ajax("com", basePath+paths.perInfoCtgHasItems);
    }
    
    export function getCardNo(){
    }
}