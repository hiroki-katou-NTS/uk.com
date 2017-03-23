module qpp007.a {
    export module service {

        // Service paths.
        var servicePath = {
           getallOutputSetting: "?"
        };
        /**
         * get All Output Setting
         */
        export function getallOutputSetting(): JQueryPromise<model.OutputSetting[]>{
            //return nts.uk.request.ajax(servicePath.getallOutputSetting);
            var dfd = $.Deferred<model.OutputSetting[]>();
            // Fake data.
            var data = [];
            for (var i = 1; i <= 10; i++) {
                data.push({code: '0' + i, name: 'Output Item Setting ' + i});
            }            
            dfd.resolve(data);
            return dfd.promise();
         }
        
        export module model{
            export class OutputSetting{
                code: string;
                name: string;    
            }
        }
    }
}
