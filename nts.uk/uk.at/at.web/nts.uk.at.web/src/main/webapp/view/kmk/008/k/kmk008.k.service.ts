module nts.uk.at.view.kmk008.k {
    export module service {
        export class Service {
            paths = {
                getDetailYearMonth: "at/record/agreementMonthSetting/getAgreementMonthSetting/{0}",
                addAgreementMonthSetting: "at/record/agreementMonthSetting/addAgreementMonthSetting",
                removeAgreementMonthSetting: "at/record/agreementMonthSetting/removeAgreementMonthSetting",
                updateAgreementMonthSetting: "at/record/agreementMonthSetting/updateAgreementMonthSetting",

                getDetailYear: "at/record/agreementYearSetting/getAgreementYearSetting/{0}",
                addAgreementYearSetting: "at/record/agreementYearSetting/addAgreementYearSetting",
                removeAgreementYearSetting: "at/record/agreementYearSetting/removeAgreementYearSetting",
                updateAgreementYearSetting: "at/record/agreementYearSetting/updateAgreementYearSetting",
            }

            constructor() {

            }

            getDetailYearMonth(employeeId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDetailYearMonth, employeeId);
                return nts.uk.request.ajax("at", _path);
            };

            addAgreementMonthSetting(AddUpdateMonthSettingModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementMonthSetting, AddUpdateMonthSettingModel);
            };

            updateAgreementMonthSetting(specialHoliday: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementMonthSetting, specialHoliday);
            };

            removeAgreementMonthSetting(DeleteMonthSettingModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementMonthSetting, DeleteMonthSettingModel);
            }


            getDetailYear(employeeId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDetailYear, employeeId);
                return nts.uk.request.ajax("at", _path);
            };

            addAgreementYearSetting(AddUpdateYearSettingModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementYearSetting, AddUpdateYearSettingModel);
            };

            updateAgreementYearSetting(AddUpdateYearSettingModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementYearSetting, AddUpdateYearSettingModel);
            };

            removeAgreementYearSetting(DeleteYearSettingModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementYearSetting, DeleteYearSettingModel);
            }
        }
    }
}
