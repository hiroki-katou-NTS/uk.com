module nts.uk.at.view.kdw008.a.service {

    var paths = {
        addDailyDetail: "at/function/dailyperformanceformat/addAuthorityDailyFormat",
        updateDailyDetail: "at/function/dailyperformanceformat/updateAuthorityDailyFormat",


        getListAuthorityDailyFormatCode: "at/function/dailyperformanceformat/getAuthorityDailyFormatCode",
        getDailyPerformance: "at/function/dailyperformanceformat/getAuthorityDailyFormat/{0}/{1}",
        removeAuthorityDailyFormat: "at/function/dailyperformanceformat/removeAuthorityFormat",

        //monthly
        getListMonthlyAttdItem: "at/record/attendanceitem/monthly/findall",
        getListMonPfmCorrectionFormat: "at/function/monthlycorrection/kdw008a/findall",
        getMonPfmCorrectionFormat: "at/function/monthlycorrection/kdw008a/findbycode/{0}",
        addMonPfmCorrectionFormat: "at/function/monthlycorrection/kdw008a/add",
        updateMonPfmCorrectionFormat: "at/function/monthlycorrection/kdw008a/update",
        deleteMonPfmCorrectionFormat: "at/function/monthlycorrection/kdw008a/delete",

        //delete by sheet no
        deleteAuthBySheet: "at/function/dailyperformanceformat/deletebysheet",
        
        //DailyAttendanceItem
        getDailyAttItem: "at/shared/scherec/attitem/getDailyAttItem",

        //MonthlyAttendanceItem
        getMonthlyAttItem: "at/shared/scherec/attitem/getMonthlyAttItem",
        
        getDailyDetail: "at/function/dailyperformanceformat/getAuthorityDailyFormat/{0}/{1}",
        getMonthlyDetail: "at/function/dailyperformanceformat/getAuthorityMonthlyFormat/{0}",
    }

    export function addDailyDetail(AddAuthorityDailyFormatCommand: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.addDailyDetail, AddAuthorityDailyFormatCommand);
    };

    export function updateDailyDetail(UpdateAuthorityDailyFormatCommand: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.updateDailyDetail, UpdateAuthorityDailyFormatCommand);
    };



    export function getListAuthorityDailyFormatCode(): JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getListAuthorityDailyFormatCode);
        return nts.uk.request.ajax("at", _path);
    };

    export function getDailyPerformance(businessTypeCode: string, sheetNo: number): JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getDailyPerformance, businessTypeCode, sheetNo);
        return nts.uk.request.ajax("at", _path);
    };

    export function removeAuthorityDailyFormat(RemoveAuthorityCommand: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.removeAuthorityDailyFormat, RemoveAuthorityCommand);
    };

    // monthly
    export function getListMonthlyAttdItem(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getListMonthlyAttdItem));
    };

    export function getListMonPfmCorrectionFormat(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getListMonPfmCorrectionFormat));
    };

    export function getMonPfmCorrectionFormat(monthlyPfmFormatCode: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getMonPfmCorrectionFormat, monthlyPfmFormatCode));
    };

    export function addMonPfmCorrectionFormat(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.addMonPfmCorrectionFormat, command);
    };

    export function updateMonPfmCorrectionFormat(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.updateMonPfmCorrectionFormat, command);
    };

    export function deleteMonPfmCorrectionFormat(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.deleteMonPfmCorrectionFormat, command);
    };
    //delete by sheet
    export function deleteAuthBySheet(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.deleteAuthBySheet, command);
    };
    
    export function getDailyAttItem(): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.getDailyAttItem);
    }
    export function getMonthlyAttItem(): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.getMonthlyAttItem);
    }
    export function getDailyDetail(code: string, sheetNo: number): JQueryPromise < any > {
        let _path = nts.uk.text.format(paths.getDailyDetail, code, sheetNo);
        return nts.uk.request.ajax("at", _path);
    };

    export function getMonthlyDetail(code: string): JQueryPromise < any > {
        let _path = nts.uk.text.format(paths.getMonthlyDetail, code);
        return nts.uk.request.ajax("at", _path);
    };
}
