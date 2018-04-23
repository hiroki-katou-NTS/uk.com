module nts.uk.at.view.kdr001.a {
    export module service {
        /**
         * define path to service
         */
        var paths: any = {
                findAll: "at/function/holidaysremaining/findAll",
                saveAsExcel: "at/function/holidaysremaining/employee"
            };
        
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAll);
        }
        
        export function saveAsExcel(data: model.appInfor) {
            return nts.uk.request.exportFile("at", paths.saveAsExcel, data);
        }
        
        export module model {
            export class appInfor {
                holidayRemainingOutputCondition: any;
                lstEmpIds: any[];
                constructor(holidayRemainingOutputCondition: any, lstEmpIds: any[]) {
                    this.holidayRemainingOutputCondition = holidayRemainingOutputCondition;
                    this.lstEmpIds = lstEmpIds;
                }
            }
            
            export class holidayRemainingOutputCondition
            {
                startMonth: string;
                endMonth: string;
                outputItemSettingCode: string;
                pageBreak: number;
                
                constructor(startMonth: string, endMonth: string, outputItemSettingCode: string, pageBreak: number){
                    this.startMonth = startMonth;
                    this.endMonth = endMonth;
                    this.outputItemSettingCode = outputItemSettingCode;
                    this.pageBreak = pageBreak;
                }
            }
        }
    }
}