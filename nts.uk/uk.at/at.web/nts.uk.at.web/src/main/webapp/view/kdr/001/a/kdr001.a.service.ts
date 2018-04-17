module nts.uk.at.view.kdm001.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findAll: "at/function/holidaysremaining/findAll",
            };
        
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
    }
}