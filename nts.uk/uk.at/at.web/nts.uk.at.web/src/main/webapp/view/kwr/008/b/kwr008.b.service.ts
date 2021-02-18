module nts.uk.at.view.kwr008.b.service{
    import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        findAllBySettingType: "at/function/annualworkschedule/findAll/",
        deleteOutputItemSetting: "at/function/annualworkschedule/delete/outputitemsetting",
        saveOutputItemSetting: "at/function/annualworkschedule/save/outputitemsetting",
        getValueOutputFormat: "at/function/annualworkschedule/get/enum/valueoutputformat",
        getOutputAgreementTime : "at/function/annualworkschedule/get/enum/outputagreementtime",
        getListItemOutput : "at/function/annualworkschedule/get/listItemOutput/",
        getMonthlyAttendanceItemByCodes: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName",
        getMonthlyAttendanceItemByAtr: "at/record/businesstype/attendanceItem/getListMonthlyByAttendanceAtrNew/",
        getOptItemByAtr: "at/record/attendanceitem/monthly/getattendcomparison/",
        getAtdItemsByDisplayFormat: "at/function/annualworkschedule/getAtdItemsByDisplayFormat/",
        findByLayoutId: "at/function/annualworkschedule/findByLayoutId/"
    }
    
    export function findAllBySettingType(settingType: number, printForm: number)
                        : JQueryPromise<Array<share.SetOutputItemOfAnnualWorkSchDto>> {
        return nts.uk.request.ajax(paths.findAllBySettingType + settingType + '/' + printForm);
    }

    export function findByLayoutId(layoutId: string): JQueryPromise<share.SetOutputItemOfAnnualWorkSchDto> {
        return nts.uk.request.ajax(paths.findByLayoutId + layoutId);
    }
    
    export function saveOutputItemSetting(command : any): JQueryPromise<any>{
        return ajax(paths.saveOutputItemSetting, command);
    }
    
    export function deleteOutputItemSetting(command : any): JQueryPromise<any>{
        return ajax(paths.deleteOutputItemSetting, command);
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
    
    export function getMonthlyAttendanceItemByCodes(codes: any) {
        return ajax("at", paths.getMonthlyAttendanceItemByCodes, codes);
    }
    
    export function getMonthlyAttendanceItemByAtr(atr: any) {
        return nts.uk.request.ajax("at", paths.getMonthlyAttendanceItemByAtr + atr);
    }
    
    export function getOptItemByAtr(atr: any) {
        return nts.uk.request.ajax("at", paths.getOptItemByAtr + atr);
    }

    export function getAtdItemsByDisplayFormat(displayFormat: number): JQueryPromise<Array<share.AttendanceItemDto>>{
        return nts.uk.request.ajax("at", paths.getAtdItemsByDisplayFormat + displayFormat);
    }
}