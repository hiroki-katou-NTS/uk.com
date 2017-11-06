module nts.uk.pr.view.ksu006.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            executeImportFile: "at/schedule/budget/external/import/execute",
            findErrors: "at/schedule/budget/external/error/find",
            exportDetailError: "at/schedule/budget/external/log/export",
        };
        
        /**
         * executeImportFile
         */
        export function executeImportFile(command: any): JQueryPromise<any> {
             return nts.uk.request.ajax(servicePath.executeImportFile, command);
        }
        
        /**
         * findErrors
         */
        export function findErrors(executeId: string): JQueryPromise<Array<model.ErrorModel>> {
             return nts.uk.request.ajax(servicePath.findErrors + "/" + executeId);
        }
        
        /**
         * downloadDetailError
         */
        export function downloadDetailError(executeId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile(servicePath.exportDetailError + "/" +  executeId);
        }
        
        export module model {
            
            /**
             * ErrorModel
             */
            export interface ErrorModel {
                order: number;
                lineNo: number;
                columnNo: number;
                wpkCode: string;
                actualValue: string;
                acceptedDate: string;
                errorContent: string;
            }
        }

    }
}