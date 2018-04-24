module nts.uk.at.view.kdr001.b {
    export module service {
        var paths: any = {
            findAll: "at/function/holidaysremaining/findAll",
            addHoliday: "at/function/holidaysremaining/add",
            updateHoliday: "at/function/holidaysremaining/update",
            removeHoliday: "at/function/holidaysremaining/remove",
            findAnnualPaidLeave: "ctx/at/share/vacation/setting/annualpaidleave/find/setting",
            findRetentionYearly: "ctx/at/shared/vacation/setting/retentionyearly/find",
            findCompensatory: "ctx/at/shared/vacation/setting/compensatoryleave/find",
            findSubstVacation: "ctx/at/shared/vacation/setting/substvacation/com/find",
            findAllSpecialHoliday: "shared/specialholiday/findByCid",
        };

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
        
        export function findAnnualPaidLeave(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAnnualPaidLeave);
        }
        
        export function findRetentionYearly(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findRetentionYearly);
        }
        
        export function findCompensatory(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findCompensatory);
        }
        
        export function findSubstVacation(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findSubstVacation);
        }

        export function findAllSpecialHoliday(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAllSpecialHoliday);
        }
    }
}

