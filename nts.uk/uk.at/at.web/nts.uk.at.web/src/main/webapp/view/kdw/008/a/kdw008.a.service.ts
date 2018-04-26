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
                getListMonthlyAttdItem:"at/record/attendanceitem/monthly/findall",
                getListMonPfmCorrectionFormat:"at/function/monthlycorrection/kdw008a/findall",
                getMonPfmCorrectionFormat:"at/function/monthlycorrection/kdw008a/findbycode/{0}",
                addMonPfmCorrectionFormat:"at/function/monthlycorrection/kdw008a/add",
                updateMonPfmCorrectionFormat:"at/function/monthlycorrection/kdw008a/update",
                deleteMonPfmCorrectionFormat:"at/function/monthlycorrection/kdw008a/delete",
                
                //delete by sheet no
                deleteAuthBySheet:"at/function/dailyperformanceformat/deletebysheet"
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
            
            getListMonPfmCorrectionFormat(): JQueryPromise<any> {
                return nts.uk.request.ajax("at",nts.uk.text.format(this.paths.getListMonPfmCorrectionFormat));
            };
            
            getMonPfmCorrectionFormat(monthlyPfmFormatCode : string): JQueryPromise<any> {
                return nts.uk.request.ajax("at",nts.uk.text.format(this.paths.getMonPfmCorrectionFormat,monthlyPfmFormatCode));
            };
            
            addMonPfmCorrectionFormat(command: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addMonPfmCorrectionFormat, command);
            };
            
            updateMonPfmCorrectionFormat(command: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateMonPfmCorrectionFormat, command);
            };
            
            deleteMonPfmCorrectionFormat(command: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.deleteMonPfmCorrectionFormat, command);
            };
            //delete by sheet
            deleteAuthBySheet(command: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.deleteAuthBySheet, command);
            };
        }
    }
}
