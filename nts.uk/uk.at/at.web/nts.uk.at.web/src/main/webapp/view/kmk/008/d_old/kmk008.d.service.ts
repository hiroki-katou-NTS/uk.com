module nts.uk.at.view.kmk008.d_old {
    export module service {
        export class Service {
            paths = {
                getList: "at/record/agreementTimeOfEmployment/getAgreementTimeOfEmployment/{0}",
                getDetail: "at/record/agreementTimeOfEmployment/getAgreementTimeOfEmployment/{0}/{1}",
                addAgreementTimeOfEmployment: "at/record/agreementTimeOfEmployment/addAgreementTimeOfEmployment",
                removeAgreementTimeOfEmployment: "at/record/agreementTimeOfEmployment/removeAgreementTimeOfEmployment",
                updateAgreementTimeOfEmployment: "at/record/agreementTimeOfEmployment/updateAgreementTimeOfEmployment",

            }

            constructor() {

            }

            getList(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getList, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getDetail(laborSystemAtr: number, employmentCategoryCode: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDetail, laborSystemAtr, employmentCategoryCode);
                return nts.uk.request.ajax("at", _path);
            };

            addAgreementTimeOfEmployment(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfEmployment, UpdateInsertTimeOfEmploymentModel);
            };

            removeAgreementTimeOfEmployment(DeleteTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfEmployment, DeleteTimeOfEmploymentModel);
            }

            updateAgreementTimeOfEmployment(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementTimeOfEmployment, UpdateInsertTimeOfEmploymentModel);
            };
        }
    }
}
