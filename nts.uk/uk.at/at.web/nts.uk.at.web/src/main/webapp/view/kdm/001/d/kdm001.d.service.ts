module nts.uk.at.view.kdm001.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "at/record/remainnumber/save",
                getByIdAndUnUse: "at/record/remainnumber/getByIdAndUnUse/{0}/{1}"
            };
        
        export function save(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save,command);
        }

        export function getByIdAndUnUse(sid: string, closureId: any) : JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getByIdAndUnUse, sid, closureId);
            return nts.uk.request.ajax('at', _path);
        }

    }
}