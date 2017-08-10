module nts.uk.pr.view.ksu006.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            executeImportFile: "at/schedule/budget/external/import/execute",
            findErrors: "at/schedule/budget/external/error/find",
        };
        
        export function executeImportFile(command: any): JQueryPromise<any> {
             return nts.uk.request.ajax(servicePath.executeImportFile, command);
        }
        
        export function findErrors(executeId: string): JQueryPromise<model.ErrorModel> {
             return nts.uk.request.ajax(servicePath.findErrors + "/" + executeId);
        }
        
        export module model {
            
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