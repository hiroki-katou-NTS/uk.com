module nts.uk.at.view.kmf001.m {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            startUp: '',
            
            save: '',
        };
        
        export function startUp(): JQueryPromise<any> {
           // return nts.uk.request.ajax(servicePath.startUp);
            let dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }

        export function save(command: any): JQueryPromise<any> {
            //return nts.uk.request.ajax(servicePath.save, command);
            let dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
    }
}
