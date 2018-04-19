module nts.uk.at.view.kdr001.b {
    export module service {
        var path: any = {
            findAll: "at/function/holidaysremaining/findAll",
            addHoliday: "at/function/holidaysremaining/add",
            updateHoliday: "at/function/holidaysremaining/update",
            removeHoliday: "at/function/holidaysremaining/remove",
        };


        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        //insert
        export function addHolidayRemaining(command): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.addHoliday, command);
        }

        //update
        export function updateHolidayRemaining(command): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.updateHoliday, command);
        }

        //delete
        export function removeHolidayRemaining(command): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.removeHoliday, command);
        }

    }
}

