module nts.uk.at.view.kdm001.i {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            add: "at/record/remaingnumber/add",
            getByIdAndUnUse: "at/record/remaingnumber/getByIdAndUnUse"
        };

        export function add(command) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.add,command);
        }

        export function getByIdAndUnUse(sid: string) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getByIdAndUnUse, sid);
        }
    }
}