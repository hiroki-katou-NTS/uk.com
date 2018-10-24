module nts.uk.pr.view.qmm001.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            updateHistoryDate: "shared/payrollgeneralpurposeparameters/UpdateHistoryDate",
            updateHistoryYearMonth: "shared/payrollgeneralpurposeparameters/UpdateHistoryYearMonth"
        };

        export function updateHistoryDate(data :any): JQueryPromise<any> {
            return ajax(paths.updateHistoryDate, data);
        }

        export function updateHistoryYearMonth(data :any): JQueryPromise<any> {
            return ajax(paths.updateHistoryYearMonth, data);
        }

    }
}