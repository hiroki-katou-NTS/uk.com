module nts.uk.at.view.kaf006.c.service {
    const paths: any = {
        changeHolidayDates: 'at/request/application/appforleave/changeHolidayDates', 
        checkBeforeRegisterHolidayDates: 'at/request/application/appforleave/checkBeforeRegisterHolidayDates', 
        registerHolidayDates: 'at/request/application/appforleave/registerHolidayDates'
    };

    export function changeHolidayDates(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.changeHolidayDates, input);
    }

    export function checkBeforeRegisterHolidayDates(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkBeforeRegisterHolidayDates, input);
    }

    export function registerHolidayDates(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerHolidayDates, input);
    }
}