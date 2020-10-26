module nts.uk.at.view.kmk008.f {
    export module service {
        export class Service {
            paths = {
                getList: "at/record/agreementTimeOfClassification/getAgreementTimeOfClassification/{0}",
                getDetail: "at/record/agreementTimeOfClassification/getAgreementTimeOfClassification/{0}/{1}",
                addAgreementTimeOfClassification: "at/record/agreementTimeOfClassification/addAgreementTimeOfClassification",
                updateAgreementTimeOfClassification: "at/record/agreementTimeOfClassification/updateAgreementTimeOfClassification",
                removeAgreementTimeOfClassification: "at/record/agreementTimeOfClassification/removeAgreementTimeOfClassification",
            }

            constructor() {

            }

            getList(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getList, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getDetail(laborSystemAtr: number, classificationCode: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDetail, laborSystemAtr, classificationCode);
                return nts.uk.request.ajax("at", _path);
            };

            addAgreementTimeOfClassification(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfClassification, UpdateInsertTimeOfEmploymentModel);
            };

            updateAgreementTimeOfClassification(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementTimeOfClassification, UpdateInsertTimeOfEmploymentModel);
            };

            removeAgreementTimeOfEmployment(DeleteTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfClassification, DeleteTimeOfEmploymentModel);
            }


        }
    }
}
