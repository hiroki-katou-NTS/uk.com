module nts.uk.com.view.cas012.c.viewmodel {
    export class ScreenModel {
        checkSetAdminFlag: KnockoutObservable<boolean>;
        listCompany: KnockoutObservableArray<any>;
        selectedCompany: KnockoutObservable<string>;
        
        constructor() {
            let self = this;
            self.checkSetAdminFlag = ko.observable(false);
            self.listCompany = ko.observableArray([]);
            self.selectedCompany = ko.observable('1');
            nts.uk.ui.windows.setShared('CAS012CResult', {
                decisionCompanyID: null,
                setRoleAdminFlag: false
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done(function(data) {
                self.listCompany(data.listCompany);
                dfd.resolve();    
            });
            return dfd.promise();
        }
        
        decision() {
            let self = this;
            let dataSetShare = {
                decisionCompanyID: self.checkSetAdminFlag() ? self.selectedCompany() : null,
                setRoleAdminFlag: self.checkSetAdminFlag()
            };
            nts.uk.ui.windows.setShared('CAS012CResult', dataSetShare);
            self.cancel_Dialog();
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
    }
}