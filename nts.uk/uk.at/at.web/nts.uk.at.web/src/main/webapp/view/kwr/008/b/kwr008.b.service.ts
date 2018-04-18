module ts.uk.at.view.kwr008.b.service{
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        getOutputItemSetting : "at/function/annualworkschedule/get/outputitemsetting"    
    }
    
    export function getOutItemSettingCode(): JQueryPromise<Array<share.OutputSettingCodeDto>>{
        return nts.uk.request.ajax(paths.getOutputItemSetting);    
    }
}