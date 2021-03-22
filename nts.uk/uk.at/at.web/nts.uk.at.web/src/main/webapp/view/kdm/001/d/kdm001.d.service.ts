module nts.uk.at.view.kdm001.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "at/record/remainnumber/save",
                getByIdAndUnUse: "at/record/remainnumber/getByIdAndUnUse"
            };
        
        
        export function save(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save,command);
        }

        export function getByIdAndUnUse(sid: string) : JQueryPromise<any> {
            return nts.uk.request.ajax('at', path.getByIdAndUnUse, sid);
        }

    }
}