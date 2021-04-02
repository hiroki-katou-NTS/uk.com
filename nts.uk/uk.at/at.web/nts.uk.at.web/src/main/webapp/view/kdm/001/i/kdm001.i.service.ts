module nts.uk.at.view.kdm001.i {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            add: "at/record/remaingnumber/add",
            getByIdAndUnUse: "at/record/remaingnumber/getByIdAndUnUse/{0}/{1}"
        };

        export function add(command) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.add,command);
        }

        export function getByIdAndUnUse(sid: string, closureId: any) : JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getByIdAndUnUse, sid, closureId);
            return nts.uk.request.ajax('at', _path);
        }
    }
}