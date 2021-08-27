module nts.uk.at.view.kwr008.a.service{
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        getPeriod : "at/function/annualworkschedule/get/period",
        getCurentMonth : "at/function/annualworkschedule/get/currentMonth",
        checkAverage : "at/function/annualworkschedule/checkAverage/",
        getPageBreakSelection : "at/function/annualworkschedule/get/enum/pagebreak",
        getOutputItemSetting : "at/function/annualworkschedule/get/outputitemsetting",
        getCurrentLoginerRole: "at/function/annualworkschedule/getCurrentLoginerRole",
        findAllBySettingType: "at/function/annualworkschedule/findAll/",
        getAuthorityOfWorkPerformance: "com/function/attendancerecord/export/setting/getAuthorityOfWorkPerformance",
        getStartMonth : "at/function/annualworkschedule/get/startMonth",
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
    
    export function getCurentMonth(): JQueryPromise<any>{
        return nts.uk.request.ajax(paths.getCurentMonth);
    }
    
    export function checkAverage(settingId: any): JQueryPromise<any>{
        return nts.uk.request.ajax(paths.checkAverage + settingId);
    }

    export function findAllBySettingType(settingType: number, printForm: number): JQueryPromise<Array<share.SetOutputItemOfAnnualWorkSchDto>> {
        return nts.uk.request.ajax(paths.findAllBySettingType + settingType + '/' + printForm);
    }

    export function getAuthorityOfWorkPerformance() : JQueryPromise<any>{
        return nts.uk.request.ajax("at", paths.getAuthorityOfWorkPerformance);
    }

    export function getStartMonth(): JQueryPromise<any> {
      return nts.uk.request.ajax("at", paths.getStartMonth);
    }
}