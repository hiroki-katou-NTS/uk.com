module nts.uk.at.view.kdm001.j {
     import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;
    export module service {
        
        var paths = {
            getAll: "at/record/remainnumber/subhd/getAll/{0}/{1}",
            update: "at/record/remainnumber/subhd/updateComDayOff",
        }
        /**
         * get list day off 
         */
        export function getAll(leaveId: string,employeeId: string): JQueryPromise<any> {
             return ajax(format(paths.getAll, leaveId,employeeId));
        }
        /**
         * update
         */
        export function update(data): JQueryPromise<any> {
             return ajax(paths.update, data);
        }
    }
}