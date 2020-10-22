module nts.uk.at.view.kmk008.c {
    export module service {
        export class Service {
            paths = {
                // getAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/getAgreementTimeOfCompany/{0}",
				getAgreementTimeOfCompany: "screen/at/kmk008/b/get",
                //addAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/addAgreementTimeOfCompany",
				addAgreementTimeOfCompany: "monthly/estimatedtime/company/add",
				updateAgreementTimeOfCompany: "monthly/estimatedtime/company/add",
                //updateAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/updateAgreementTimeOfCompany"
            }
            
            constructor() {
                
            }
            
            getAgreementTimeOfCompany(laborSystemAtr: number): JQueryPromise<any> {
                // let _path = nts.uk.text.format(this.paths.getAgreementTimeOfCompany, laborSystemAtr);
                //return nts.uk.request.ajax("at", _path);
				return nts.uk.request.ajax("at", this.paths.getAgreementTimeOfCompany, {laborSystemAtr: laborSystemAtr});
            };
            
            addAgreementTimeOfCompany(UpdateInsertTimeOfCompanyModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfCompany, UpdateInsertTimeOfCompanyModel);
            };
                
            updateAgreementTimeOfCompany(UpdateInsertTimeOfCompanyModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementTimeOfCompany, UpdateInsertTimeOfCompanyModel);
            };
        }
    }
}
