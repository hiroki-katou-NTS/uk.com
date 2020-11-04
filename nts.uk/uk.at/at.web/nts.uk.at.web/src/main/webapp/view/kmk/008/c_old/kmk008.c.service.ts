module nts.uk.at.view.kmk008.c {
    export module service {
        export class Service {
            paths = {
                getAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/getAgreementTimeOfCompany/{0}",
                addAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/addAgreementTimeOfCompany",
                updateAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/updateAgreementTimeOfCompany"
            }
            
            constructor() {
                
            }
            
            getAgreementTimeOfCompany(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAgreementTimeOfCompany, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
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
