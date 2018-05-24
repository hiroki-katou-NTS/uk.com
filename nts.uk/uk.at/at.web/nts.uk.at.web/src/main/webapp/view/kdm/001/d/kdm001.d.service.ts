module nts.uk.at.view.kdm001.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "at/record/remainnumber/save"
            };
        
        
        export function save(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save,command);
        }

    }
}