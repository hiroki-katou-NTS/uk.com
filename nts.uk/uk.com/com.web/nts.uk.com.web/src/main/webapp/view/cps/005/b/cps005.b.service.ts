module nts.uk.com.view.cps005.b {
    export module service {
        export class Service {
            paths = {
                getDetailYearMonth: "at/record/agreementMonthSetting/getAgreementMonthSetting/{0}",
            }

            constructor() {

            }

            getDetailYearMonth(employeeId: string): JQueryPromise<any> {
                //                let _path = nts.uk.text.format(this.paths.getDetailYearMonth, employeeId);
                return //nts.uk.request.ajax("com", _path);
            };


        }
    }
}
