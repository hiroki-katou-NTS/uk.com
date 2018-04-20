module nts.uk.at.view.kdr001.b {
    export module service {
        var paths: any = {
            findAllSpecialHoliday: 'shared/specialholiday/findByCid',
            findAll: "at/function/holidaysremaining/findAll",
            addHoliday: "at/function/holidaysremaining/add",
            updateHoliday: "at/function/holidaysremaining/update",
            removeHoliday: "at/function/holidaysremaining/remove"
        };

        export function findAllSpecialHoliday(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAllSpecialHoliday);
        }
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAll);
        }
        //insert
        export function addHolidayRemaining(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.addHoliday, command);
        }

        //update
        export function updateHolidayRemaining(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateHoliday, command);
        }

        //delete
        export function removeHolidayRemaining(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.removeHoliday, command);
        }

    }
}

