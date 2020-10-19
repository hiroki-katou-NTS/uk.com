module nts.uk.at.view.kwr008.b.service{
    import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        getOutputItemSetting : "at/function/annualworkschedule/get/outputitemsetting",
        deleteOutputItemSetting: "at/function/annualworkschedule/delete/outputitemsetting",
        updateOutputItemSetting: "at/function/annualworkschedule/update/outputitemsetting",
        registerOutputItemSetting: "at/function/annualworkschedule/add/outputitemsetting",
        getValueOutputFormat: "at/function/annualworkschedule/get/enum/valueoutputformat",
        getOutputAgreementTime : "at/function/annualworkschedule/get/enum/outputagreementtime",
        getListItemOutput : "at/function/annualworkschedule/get/listItemOutput/",
        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName",
        getMonthlyAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListMonthlyByAttendanceAtr/",
        getOptItemByAtr: "at/record/attendanceitem/monthly/getattendcomparison/"
    }
    
    export function getOutItemSettingCode(): JQueryPromise<Array<share.OutputSettingCodeDto>>{
        return ajax(paths.getOutputItemSetting);    
    }
    
    export function registerOutputItemSetting(command : any): JQueryPromise<any>{
        return ajax(paths.registerOutputItemSetting, command);
    }
    
    export function deleteOutputItemSetting(command : any): JQueryPromise<any>{
        return ajax(paths.deleteOutputItemSetting, command);
    }
    
    export function updateOutputItemSetting(command : any): JQueryPromise<any>{
        return ajax(paths.updateOutputItemSetting, command);
    }
    
    export function getValueOutputFormat(): JQueryPromise<Array<share.EnumConstantDto>>{
        return ajax(paths.getValueOutputFormat);    
    }
    
    export function getOutputAgreementTime(): JQueryPromise<Array<share.EnumConstantDto>>{
        return ajax(paths.getOutputAgreementTime);
    }
    
    export function getListItemOutput(itemOutputSettingCode : string): JQueryPromise<any>{
        return ajax(paths.getListItemOutput + itemOutputSettingCode);
    }
    
    export function getMonthlyAttendanceItemByCodes(codes) {
        return ajax("at", paths.getMonthlyAttendanceItemByCodes, codes);
    }
    
    export function getMonthlyAttendanceItemByAtr(atr) {
        return nts.uk.request.ajax("at", paths.getMonthlyAttendanceItemByAtr + atr);
    }
    
    export function getOptItemByAtr(atr) {
        return nts.uk.request.ajax("at", paths.getOptItemByAtr + atr);
    }
}