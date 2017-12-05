module nts.uk.com.view.cas012.c.viewmodel {
    export class ScreenModel {

        checkSetAdminFlag: KnockoutObservable<boolean>;
        //ComboBox Company
        listCompany: KnockoutObservableArray<Company>;
        selectedCompany: KnockoutObservable<string>;
        constructor() {
            let self = this;
            self.checkSetAdminFlag = ko.observable(false);
            self.listCompany = ko.observableArray([]);
            self.selectedCompany = ko.observable('1');
            
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done(function(data) {
                console.log(data);

                self.listCompany(data.listCompany);
                dfd.resolve();
            });



            return dfd.promise();
        }
        decision() {
            let self = this;
            let dataSetShare = {
                decisionCompanyID: self.checkSetAdminFlag()? self.selectedCompany() : null,
                checkSetAdminFlag: self.checkSetAdminFlag()
            };
            nts.uk.ui.windows.setShared('CAS012A', dataSetShare);
                nts.uk.ui.windows.setShared("ReturnData", dataSetShare);
                console.log(dataSetShare);
           
            self.cancel_Dialog();
        }


        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
    }
}