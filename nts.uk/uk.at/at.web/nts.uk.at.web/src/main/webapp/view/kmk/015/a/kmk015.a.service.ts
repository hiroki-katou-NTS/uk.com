module nts.uk.at.view.kmk015.a {
    export module service {
        
        let servicePath: any = {
            findListWorkType: 'at/share/worktype/findWorkTypeByCondition',
            getHistoryByWorkType: 'at/request/application/vacation/getHistoryByWorkType',
            insertHistory: 'at/request/application/vacation/settingHistory'
        };
        
        export function findListWorkType(): JQueryPromise<Array<model.WorkType>> {
            return nts.uk.request.ajax(servicePath.findListWorkType);
        }
        
        export function getHistoryByWorkType(workTypeCode : string): JQueryPromise<Array<model.History>> {
            return nts.uk.request.ajax(servicePath.getHistoryByWorkType + '/' + workTypeCode);
        }
        
        export function insertHistory(command: model.SaveVacationHistoryCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.insertHistory, command);
        }
        
        export module model {
            export class WorkType {
                abbreviationName: string;
                companyId: string;
                displayAtr: number;
                memo: string;
                name: string;
                sortOrder: number;
                symbolicName: string;
                workTypeCode: string;
                abolishAtr: number;
            }
            
            export class SaveVacationHistoryCommand {
                
                isCreateMode: boolean;
                workTypeCode: string;
                maxDay: number;
                vacationHistory: SaveHistory;
                
                constructor(isCreateMode: boolean, workTypeCode: string, maxDay: number, vacationHistory: SaveHistory) {
                    this.isCreateMode = isCreateMode;
                    this.workTypeCode = workTypeCode;
                    this.maxDay = maxDay;
                    this.vacationHistory = vacationHistory;
                }
            }
            
            /**
             * History (for save command)
             */
            export class SaveHistory {
                historyId: string;
                startDate: Date;
                endDate: Date;
                
                constructor(historyId: string, startDate: Date, endDate: Date) {
                    this.historyId = historyId;
                    this.startDate = startDate;
                    this.endDate = endDate;
                }          
            }
            
            export class History {
                historyId: string;
                startDate: Date;
                endDate: Date;
                maxDay: number;
            }
        }
    }
}