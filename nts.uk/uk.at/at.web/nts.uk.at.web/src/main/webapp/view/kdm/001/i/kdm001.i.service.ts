module nts.uk.at.view.kdm001.i {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                add: "at/record/subhdmana/add"
            };
        
        
        export function add() : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.add);
        }

    }
}