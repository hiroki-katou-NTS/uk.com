module nts.uk.pr.view.qmm001.c {
    import ajax = nts.uk.request.ajax;
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            updateHistoryDate: "shared/payrollgeneralpurposeparameters/UpdateHistoryDate",
            updateHistoryYearMonth: "shared/payrollgeneralpurposeparameters/UpdateHistoryYearMonth"
        };

        export function updateHistoryDate(data :any): JQueryPromise<any> {
            return ajax(path.updateHistoryDate, data);
        }

        export function updateHistoryYearMonth(data :any): JQueryPromise<any> {
            return ajax(path.updateHistoryYearMonth, data);
        }

    }
}