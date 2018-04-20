module nts.uk.at.view.kdr001.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findAll: "at/function/holidaysremaining/findAll",
                saveAsExcel: "at/function/holidaysremaining/employee",
                getDate: "at/function/holidaysremaining/getDate",
                getPermissionOfEmploymentForm: "at/function/holidaysremaining/getPermissionOfEmploymentForm"
            };
        
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
        export function saveAsExcel(data: model.appInfor) {
            return nts.uk.request.exportFile("at", path.saveAsExcel, data);
        }
        
        export function getDate() {
            return nts.uk.request.exportFile("at", path.getDate);
        }
        
        export function getPermissionOfEmploymentForm() {
            return nts.uk.request.exportFile("at", path.getPermissionOfEmploymentForm);
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
            
            export class date{
                startDate : string;
                endDate : string;
                
                constructor(startDate : string, endDate : string){
                        this.startDate = startDate;
                        this.endDate = endDate;
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