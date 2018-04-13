module nts.uk.at.view.kdw008.a {
    export module service {
        export class Service {
            paths = {
                addDailyDetail: "at/function/dailyperformanceformat/addAuthorityDailyFormat",
                updateDailyDetail: "at/function/dailyperformanceformat/updateAuthorityDailyFormat",


                getListAuthorityDailyFormatCode: "at/function/dailyperformanceformat/getAuthorityDailyFormatCode",
                getDailyPerformance: "at/function/dailyperformanceformat/getAuthorityDailyFormat/{0}/{1}",
                removeAuthorityDailyFormat: "at/function/dailyperformanceformat/removeAuthorityFormat",
                
                //monthly
                getListMonthlyAttdItem:"at/record/attendanceitem/monthly/findall"
            }

            constructor() {

            }
            addDailyDetail(AddAuthorityDailyFormatCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addDailyDetail, AddAuthorityDailyFormatCommand);
            };

            updateDailyDetail(UpdateAuthorityDailyFormatCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateDailyDetail, UpdateAuthorityDailyFormatCommand);
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
            
            // monthly
            getListMonthlyAttdItem(): JQueryPromise<any> {
                return nts.uk.request.ajax("at",nts.uk.text.format(this.paths.getListMonthlyAttdItem));
            };
        }
    }
}
