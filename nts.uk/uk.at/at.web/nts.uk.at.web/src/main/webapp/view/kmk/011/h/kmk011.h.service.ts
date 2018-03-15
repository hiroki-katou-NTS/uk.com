module nts.uk.at.view.kmk011.h {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "at/record/divergence/time/history/divergenceRefTimeUsageUnit/save",
            find: "at/record/divergence/time/history/divergenceRefTimeUsageUnit/find"      
        };
        
        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save);
        }
        
        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.find);
        }  
    }
}