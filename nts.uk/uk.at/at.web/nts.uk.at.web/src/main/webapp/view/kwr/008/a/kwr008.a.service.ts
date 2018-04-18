module nts.uk.at.view.kwr008.a.service{
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        getPageBreakSelection : "at/function/annualworkschedule/get/enum/pagebreak",
        getOutputItemSetting : "at/function/annualworkschedule/get/outputitemsetting"  
    }
    
    export function getPageBreakSelection(): JQueryPromise<Array<share.EnumConstantDto>>{
        return nts.uk.request.ajax(paths.getPageBreakSelection);
    }
    
    export function getOutItemSettingCode(): JQueryPromise<Array<share.OutputSettingCodeDto>>{
        return nts.uk.request.ajax(paths.getOutputItemSetting);    
    }
}