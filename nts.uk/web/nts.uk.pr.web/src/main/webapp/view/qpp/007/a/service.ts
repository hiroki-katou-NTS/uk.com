module qpp007.a {
    export module service {

        // Service paths.
        var servicePath = {
           getallOutputSetting: "ctx/pr/report/wageledger/outputsetting/findAll"
        };
        /**
         * get All Output Setting
         */
        export function getallOutputSetting(): JQueryPromise<model.OutputSetting[]>{
            return nts.uk.request.ajax(servicePath.getallOutputSetting);
         }
        
        export module model{
            export class OutputSetting{
                code: string;
                name: string;    
            }
        }
    }
}
