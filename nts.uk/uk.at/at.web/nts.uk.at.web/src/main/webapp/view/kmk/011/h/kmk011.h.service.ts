module nts.uk.at.view.kmk011.h {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "",
            find: ""      
        };
        
        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save);
        }
        
        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }  
    }
}