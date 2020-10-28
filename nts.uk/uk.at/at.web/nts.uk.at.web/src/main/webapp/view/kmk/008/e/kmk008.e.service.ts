module nts.uk.at.view.kmk008.e {
    export module service {
        export class Service {
            paths = {
                getList: "screen/at/kmk008/e/getClassificationCodes/{0}",
                getDetail: "screen/at/kmk008/e/get",
                addAgreementTimeOfClassification: "monthly/estimatedtime/classification/add",
                removeAgreementTimeOfClassification: "monthly/estimatedtime/classification/delete",
            };

            constructor() {

            }

            getList(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getList, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getDetail(laborSystemAtr: number, classificationCode: string): JQueryPromise<any> {
				return nts.uk.request.ajax("at", this.paths.getDetail, {
					laborSystemAtr: laborSystemAtr,
					classificationCode: classificationCode
				});
            };

            addAgreementTimeOfClassification(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfClassification, UpdateInsertTimeOfEmploymentModel);
            };

            removeAgreementTimeOfEmployment(DeleteTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfClassification, DeleteTimeOfEmploymentModel);
            }


        }
    }
}
