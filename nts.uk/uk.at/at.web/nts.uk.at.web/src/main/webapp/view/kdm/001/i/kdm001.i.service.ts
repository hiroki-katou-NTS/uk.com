module nts.uk.at.view.kdm001.i {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                add: "at/record/remaingnumber/add"
            };
        
        
        export function add(command) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.add,command);
        }

    }
}