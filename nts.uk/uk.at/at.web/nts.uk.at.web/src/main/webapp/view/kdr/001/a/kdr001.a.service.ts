module nts.uk.at.view.kdr001.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findAll: "at/function/holidaysremaining/findAll",
                saveAsExcel: "at/function/holidaysremaining/employee"
            };
        
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
        export function saveAsExcel(data: model.appInfor) {
            return nts.uk.request.exportFile("at", path.saveAsExcel, data);
        }
        
        export module model {
            export class appInfor {
                baseDate: any;
                lstEmpIds: string[];
                constructor(baseDate: any, lstEmpIds: string[]) {
                    this.baseDate = baseDate;
                    this.lstEmpIds = lstEmpIds;
                }
            }
        }
    }
}