module nts.uk.at.view.kmk008.g {
    export module service {
        export class Service {
            paths = {
                getMonth: "at/record/agreementMonthSetting/getAgreementMonthSetting/{0}",
                getYear: "at/record/agreementYearSetting/getAgreementYearSetting/{0}",

            };
            constructor() { }
            
            getMonth(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getMonth, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getYear(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getYear, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };


            public functionDemo(printType: number): JQueryPromise<any> {
                return null;
            };

        }
    }
}
