module nts.uk.pr.view.ksu006.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            executeImportFile: "at/schedule/budget/external/import/execute",
        };
        
        export function executeImportFile(command: any): JQueryPromise<any> {
             return nts.uk.request.ajax(servicePath.executeImportFile, command);
        }
        
        export module model {
        }

    }
}