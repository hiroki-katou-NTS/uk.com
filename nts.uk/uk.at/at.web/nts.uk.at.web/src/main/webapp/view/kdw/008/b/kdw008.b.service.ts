module nts.uk.at.view.kdw008.b {
    export module service {
        export class Service {
            paths = {
                getDailyDetail: "at/record/businesstype/findBusinessTypeDailyDetail/{0}/{1}",
                addDailyDetail: "at/record/businesstype/addBusinessTypeDailyDetail",
                updateDailyDetail: "at/record/businesstype/updateBusinessTypeDailyDetail",
                getMonthlyDetail: "at/record/businesstype/findBusinessTypeMonthlyDetail/{0}",
                addMonthlyDetail: "at/record/businesstype/addBusinessTypeMonthlyDetail",
                updateMonthlyDetail: "at/record/businesstype/updateBusinessTypeMonthlyDetail",
                getAttendanceItem: "at/record/businesstype/attendanceItem/findAll",
                getSheetNo: "at/record/businesstype/findBusinessTypeDailyDetail/findSheetNo/{0}",
                getBusinessType: "at/record/businesstype/findAll",
                getDailyPerformance: "at/record/businesstype/find/businessTypeDetail/{0}/{1}"
            }

            constructor() {

            }

            getDailyDetail(businessTypeCode: string, sheetNo: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDailyDetail, businessTypeCode, sheetNo);
                return nts.uk.request.ajax("at", _path);
            };

            addDailyDetail(AddBusinessTypeDailyDetailCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addDailyDetail, AddBusinessTypeDailyDetailCommand);
            };

            updateDailyDetail(UpdateBusinessTypeDailyCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateDailyDetail, UpdateBusinessTypeDailyCommand);
            };

            getMonthlyDetail(businessTypeCode: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getMonthlyDetail, businessTypeCode);
                return nts.uk.request.ajax("at", _path);
            };

            addMonthlyDetail(AddBusinessTypeMonthlyCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addMonthlyDetail, AddBusinessTypeMonthlyCommand);
            };

            updateMonthlyDetail(UpdateBusinessTypeMonthlyCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateMonthlyDetail, UpdateBusinessTypeMonthlyCommand);
            };

            getAttendanceItem(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAttendanceItem);
                return nts.uk.request.ajax("at", _path);
            };

            getSheetNo(businessTypeCode: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getSheetNo, businessTypeCode);
                return nts.uk.request.ajax("at", _path);
            };

            getBusinessType(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getBusinessType);
                return nts.uk.request.ajax("at", _path);
            };

            getDailyPerformance(businessTypeCode: string, sheetNo: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDailyPerformance, businessTypeCode, sheetNo);
                return nts.uk.request.ajax("at", _path);
            };


        }
    }
}
