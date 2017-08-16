module nts.uk.pr.view.ksu006.c {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            findCompletionList: "at/schedule/budget/external/log/find/completionenum",
            findAllExternalBudgetLog: "at/schedule/budget/external/log/findAll/log",
            exportDetailError: "at/schedule/budget/external/log/export",
        };
        
        export function findCompletionList(): JQueryPromise<Array<model.EnumerationModel>> {
            return nts.uk.request.ajax(paths.findCompletionList);
        }
        
        export function findAllExternalBudgetLog(query: any): JQueryPromise<Array<model.ExternalBudgetLogModel>> {
            return nts.uk.request.ajax(paths.findAllExternalBudgetLog, query);
        }
        
        export function downloadDetailError(executeId: string): JQueryPromise<Array<model.ExternalBudgetLogModel>> {
            return nts.uk.request.exportFile(paths.exportDetailError + "/" +  executeId);
        }

        export module model {
            
            export class EnumerationModel {
                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    let self = this;
                    self.value = value;
                    self.fieldName = fieldName;
                    self.localizedName = localizedName;
                }
            }
            
            export class ExternalBudgetLogModel {
                executeId: string;
                startDate: string;
                endDate: string;
                extBudgetName: string;
                fileName: string;
                statusVal: number;
                statusDes: string;
                numberSuccess: number;
                numberFail: number;
                download: string;
                
                constructor(executeId: string, startDate: string, endDate: string, extBudgetName: string,
                    fileName: string, statusVal: number, statusDes: string, numberSuccess: number, numberFail: number) {
                    let self = this;
                    self.executeId = executeId;
                    self.startDate = startDate;
                    self.endDate = endDate;
                    self.extBudgetName = extBudgetName;
                    self.fileName = fileName;
                    self.statusVal = statusVal;
                    self.statusDes = statusDes;
                    self.numberSuccess = numberSuccess;
                    self.numberFail = numberFail;
                    self.download = null;
                }
            }
            
        }

    }
}