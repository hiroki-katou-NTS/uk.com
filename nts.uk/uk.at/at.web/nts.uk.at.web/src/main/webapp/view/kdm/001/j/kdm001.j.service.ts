module nts.uk.at.view.kdm001.j {
    export module service {
        
        var paths = {
            getAll: "at/record/remainnumber/subhd/getAll/{0}",
            update: "at/record/remainnumber/subhd/update",
        }
        /**
         * get list day off 
         */
        export function getAll(leaveId): JQueryPromise<any> {
             return nts.uk.request.ajax("at", paths.getAll, leaveId);
        }
        /**
         * update
         */
        export function update(data): JQueryPromise<any> {
             return nts.uk.request.ajax("at", paths.update, data);
        }
    }
}