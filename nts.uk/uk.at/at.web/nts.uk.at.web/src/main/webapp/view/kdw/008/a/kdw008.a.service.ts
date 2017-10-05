module nts.uk.at.view.kdw008.a {
    export module service {
        export class Service {
            paths = {
                addDailyDetail: "at/function/dailyperformanceformat/addAuthorityDailyFormat",
                updateDailyDetail: "at/function/dailyperformanceformat/updateAuthorityDailyFormat",
                addMonthlyDetail: "at/function/dailyperformanceformat/addAuthorityMonthlyFormat",
                updateMonthlyDetail: "at/function/dailyperformanceformat/updateAuthorityMonthlyFormat",
                getListAuthorityDailyFormatCode: "at/function/dailyperformanceformat/getAuthorityDailyFormatCode",
                getDailyPerformance: "at/function/dailyperformanceformat/getAuthorityDailyFormat/{0}/{1}",
                removeAuthorityDailyFormat: "at/function/dailyperformanceformat/removeAuthorityFormat"
            }

            constructor() {

            }
            addDailyDetail(AddBusinessTypeDailyDetailCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addDailyDetail, AddBusinessTypeDailyDetailCommand);
            };

            updateDailyDetail(UpdateBusinessTypeDailyCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateDailyDetail, UpdateBusinessTypeDailyCommand);
            };

            addMonthlyDetail(AddBusinessTypeMonthlyCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addMonthlyDetail, AddBusinessTypeMonthlyCommand);
            };

            updateMonthlyDetail(UpdateBusinessTypeMonthlyCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateMonthlyDetail, UpdateBusinessTypeMonthlyCommand);
            };

            getBusinessType(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getListAuthorityDailyFormatCode);
                return nts.uk.request.ajax("at", _path);
            };

            getDailyPerformance(businessTypeCode: string, sheetNo: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDailyPerformance, businessTypeCode, sheetNo);
                return nts.uk.request.ajax("at", _path);
            };

            removeAuthorityDailyFormat(RemoveAuthorityCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAuthorityDailyFormat, RemoveAuthorityCommand);
            };
        }
    }
}
