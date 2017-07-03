module nts.uk.at.view.kmk008.e {
    export module service {
        export class Service {
            paths = {
                getList: "at/record/agreementTimeOfWorkPlace/getAgreementTimeOfWorkPlace/{0}",
                getDetail: "at/record/agreementTimeOfWorkPlace/getAgreementTimeOfWorkPlace/{0}/{1}",
                addAgreementTimeOfWorkPlace: "at/record/agreementTimeOfWorkPlace/addAgreementTimeOfWorkPlace",
                updateAgreementTimeOfWorkplace: "at/record/agreementTimeOfWorkPlace/updateAgreementTimeOfWorkplace",
                removeAgreementTimeOfWorkplace: "at/record/agreementTimeOfWorkPlace/removeAgreementTimeOfWorkplace",
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

            addAgreementTimeOfWorkPlace(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfWorkPlace, UpdateInsertTimeOfEmploymentModel);
            };

            updateAgreementTimeOfWorkplace(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementTimeOfWorkplace, UpdateInsertTimeOfEmploymentModel);
            };

            removeAgreementTimeOfWorkplace(DeleteTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfWorkplace, DeleteTimeOfEmploymentModel);
            }
        }
    }
}
