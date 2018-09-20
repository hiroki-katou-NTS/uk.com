module nts.uk.at.view.kwr008.a.service{
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        getPeriod : "at/function/annualworkschedule/get/period",
        getPageBreakSelection : "at/function/annualworkschedule/get/enum/pagebreak",
        getOutputItemSetting : "at/function/annualworkschedule/get/outputitemsetting",
        getCurrentLoginerRole: "at/function/annualworkschedule/getCurrentLoginerRole"
    }

    export function getCurrentLoginerRole() : JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getCurrentLoginerRole);
    }

    export function getPeriod(): JQueryPromise<any>{
        return nts.uk.request.ajax(paths.getPeriod);
    }

    export function getPageBreakSelection(): JQueryPromise<Array<share.EnumConstantDto>>{
        return nts.uk.request.ajax(paths.getPageBreakSelection);
    }

    export function getOutItemSettingCode(): JQueryPromise<Array<share.OutputSettingCodeDto>>{
        return nts.uk.request.ajax(paths.getOutputItemSetting);
    }
}