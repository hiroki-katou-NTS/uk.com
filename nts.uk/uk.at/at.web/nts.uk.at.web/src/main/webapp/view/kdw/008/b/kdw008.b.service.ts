module nts.uk.at.view.kdw008.b.service {

    var paths = {
        addDailyDetail: "at/record/businesstype/addBusTypeFormat",
        updateDailyDetail: "at/record/businesstype/updateBusTypeFormat",
        //                addMonthlyDetail: "at/record/businesstype/addBusinessTypeMonthlyDetail",
        //                updateMonthlyDetail: "at/record/businesstype/updateBusinessTypeM                  
        getBusinessType: "at/record/businesstype/findAll",
        getDailyDetail: "at/record/businesstype/findBusinessTypeDailyDetail/{0}/{1}",
        getMonthlyDetail: "at/record/businesstype/findBusinessTypeMonthlyDetail/{0}",

        //monthly
        getListMonthlyAttdItem: "at/record/attendanceitem/monthly/findall",
        getNameMonthly: "screen/at/correctionofdailyperformance/getNameMonthlyAttItem",

        // monthly tab3
        getListMonthRight: "at/function/monthlycorrection/findbycode/{0}",
        updateMonthly: "at/function/monthlycorrection/updatemonthly",

        //delete by sheet
        deleteBusiFormatBySheet: "at/record/businesstype/deletebysheet",

        //DailyAttendanceItem
        getDailyAttItem: "at/shared/scherec/attitem/getDailyAttItem",

        //MonthlyAttendanceItem
        getMonthlyAttItem: "at/shared/scherec/attitem/getMonthlyAttItem",
    }

    export function addDailyDetail(AddBusTypeCommand: any): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.addDailyDetail, AddBusTypeCommand);
    };

    export function updateDailyDetail(UpdateBusTypeCommand: any): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.updateDailyDetail, UpdateBusTypeCommand);
    };
    //monthly
    export function updateMonthly(command: any): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.updateMonthly, command);
    };

    //delete by sheet 
    export function deleteBusiFormatBySheet(command: any): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.deleteBusiFormatBySheet, command);
    };
    //            addMonthlyDetail(AddBusinessTypeMonthlyCommand: any): JQueryPromise<any> {
    //                return nts.uk.request.ajax("at", paths.addMonthlyDetail, AddBusinessTypeMonthlyCommand);
    //            };

    //            updateMonthlyDetail(UpdateBusinessTypeMonthlyCommand: any): JQueryPromise<any> {
    //                return nts.uk.request.ajax("at", paths.updateMonthlyDetail, UpdateBusinessTypeMonthlyCommand);
    //            };

    export function getBusinessType(): JQueryPromise < any > {
        let _path = nts.uk.text.format(paths.getBusinessType);
        return nts.uk.request.ajax("at", _path);
    };

    export function getDailyDetail(businessTypeCode: string, sheetNo: number): JQueryPromise < any > {
        let _path = nts.uk.text.format(paths.getDailyDetail, businessTypeCode, sheetNo);
        return nts.uk.request.ajax("at", _path);
    };

    export function getMonthlyDetail(businessTypeCode: string): JQueryPromise < any > {
        let _path = nts.uk.text.format(paths.getMonthlyDetail, businessTypeCode);
        return nts.uk.request.ajax("at", _path);
    };

    export function getListMonthRight(businessTypeCode: string): JQueryPromise < any > {
        let _path = nts.uk.text.format(paths.getListMonthRight, businessTypeCode);
        return nts.uk.request.ajax("at", _path);
    };

    // monthly
    export function getListMonthlyAttdItem(): JQueryPromise < any > {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getListMonthlyAttdItem));
    };
    export function getNameMonthly(listID : any): JQueryPromise < any > {
        return   nts.uk.request.ajax("at", paths.getNameMonthly, listID);
    }

    export function getDailyAttItem(): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.getDailyAttItem);
    }
    export function getMonthlyAttItem(): JQueryPromise < any > {
        return nts.uk.request.ajax("at", paths.getMonthlyAttItem);
    }

}
