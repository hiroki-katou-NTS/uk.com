module nts.uk.at.view.kmf002.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "",
                findAll: "",
                remove: "",
                getPubHDPeriodEnum: "bs/employee/holidaysetting/config/enum/publicholidayperiod",
                getDayOfPubHDEnum: "bs/employee/holidaysetting/config/enum/dayofpublicholiday",
                getPubHDManageClassificationEnum: "bs/employee/holidaysetting/config/enum/pubhdmanagementatr",
                getPublicHolidayCarryOverDeadline: "bs/employee/holidaysetting/config/enum/publicholidaycarryoverdeadline",
                getDaysOfTheWeek: "bs/employee/holidaysetting/config/enum/dayofweek",
            };
        
        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.save);
        }
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.findAll);
        }
        
        export function getPubHDPeriodEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getPubHDPeriodEnum);
        }
        
        export function getDayOfPubHDEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getDayOfPubHDEnum);
        }
        
        export function getPubHDManageClassificationEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getPubHDManageClassificationEnum);
        }
        
        export function getPublicHolidayCarryOverDeadline(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getPublicHolidayCarryOverDeadline);
        }
        
        export function getDaysOfTheWeek(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getDaysOfTheWeek);
        }
    }
    
}