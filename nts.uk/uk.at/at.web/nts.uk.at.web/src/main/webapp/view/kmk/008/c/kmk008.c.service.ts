module nts.uk.at.view.kmk008.c {
    export module service {
        export class Service {
            paths = {
				getList: "screen/at/kmk008/c/getEmploymentCodes/{0}",
				getDetail: 'screen/at/kmk008/c/get',
				addAgreementTimeOfEmployment: "monthly/estimatedtime/employment/add",
				removeAgreementTimeOfEmployment: "monthly/estimatedtime/employment/delete",
            }

            constructor() {

            }

            getList(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getList, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getDetail(laborSystemAtr: number, employmentCategoryCode: string): JQueryPromise<any> {
				return nts.uk.request.ajax("at", this.paths.getDetail, {
					laborSystemAtr: laborSystemAtr,
					employmentCode: employmentCategoryCode
				});
            };

            addAgreementTimeOfEmployment(UpdateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfEmployment, UpdateInsertTimeOfEmploymentModel);
            };

            removeAgreementTimeOfEmployment(DeleteTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfEmployment, DeleteTimeOfEmploymentModel);
            }
        }
    }
}
