module nts.uk.com.view.cas004.a {
    export module viewModel {
        export class ScreenModel {

            dataSource: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;

            constructor() {

                let self = this;
                self.dataSource = ko.observableArray([]);
                self.currentCode = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CAS004_13'), prop: 'loginId', width: 100 },
                    { headerText: nts.uk.resource.getText('CAS004_14'), prop: 'name', width: 230 }
                ]);

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getCompanyImportList().done(function(data) {

                });
                return dfd.promise();
            }
        }
    }
}