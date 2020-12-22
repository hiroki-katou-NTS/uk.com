module nts.uk.at.view.kmk008.c {
    export module service {
        export class Service {
            paths = {
				getList: "screen/at/kmk008/c/getEmploymentCodes/{0}",
				getDetail: 'screen/at/kmk008/c/get',
				addAgreementTimeOfEmployment: "monthly/estimatedtime/employment/add",
				removeAgreementTimeOfEmployment: "monthly/estimatedtime/employment/delete",
				copySetting: "monthly/estimatedtime/employment/copy"
            };

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

            addAgreementTimeOfEmployment(updateInsertTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfEmployment, updateInsertTimeOfEmploymentModel);
            };

			copySetting(command: any): JQueryPromise<Array<any>> {
				return nts.uk.request.ajax(this.paths.copySetting, command);
			}

            removeAgreementTimeOfEmployment(deleteTimeOfEmploymentModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfEmployment, deleteTimeOfEmploymentModel);
            }
        }
    }
}
