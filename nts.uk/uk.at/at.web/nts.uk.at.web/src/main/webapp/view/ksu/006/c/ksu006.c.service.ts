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
        
        /**
         * findCompletionList
         */
        export function findCompletionList(): JQueryPromise<Array<model.EnumerationModel>> {
            return nts.uk.request.ajax(paths.findCompletionList);
        }
        
        /**
         * findAllExternalBudgetLog
         */
        export function findAllExternalBudgetLog(query: any): JQueryPromise<Array<model.ExternalBudgetLogModel>> {
            return nts.uk.request.ajax(paths.findAllExternalBudgetLog, query);
        }
        
        /**
         * downloadDetailError
         */
        export function downloadDetailError(executeId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportDetailError + "/" +  executeId);
        }

        export module model {
            
            /**
             * EnumerationModel
             */
            export interface EnumerationModel {
                value: number;
                fieldName: string;
                localizedName: string;
            }
            
            /**
             * ExternalBudgetLogModel
             */
            export interface ExternalBudgetLogModel {
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
            }
        }
    }
}