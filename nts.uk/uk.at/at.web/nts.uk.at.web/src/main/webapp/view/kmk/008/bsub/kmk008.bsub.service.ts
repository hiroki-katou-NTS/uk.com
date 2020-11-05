module nts.uk.at.view.kmk008.bsub {
    export module service {
        export class Service {
            paths = {
				getAgreementTimeOfCompany: "screen/at/kmk008/b/get",
				addAgreementTimeOfCompany: "monthly/estimatedtime/company/add",
            }
            
            constructor() {
                
            }
            
            getAgreementTimeOfCompany(laborSystemAtr: number): JQueryPromise<any> {
				return nts.uk.request.ajax("at", this.paths.getAgreementTimeOfCompany, {laborSystemAtr: laborSystemAtr});
            };
            
            addAgreementTimeOfCompany(UpdateInsertTimeOfCompanyModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfCompany, UpdateInsertTimeOfCompanyModel);
            };
        }
    }
}
