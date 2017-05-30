module nts.uk.pr.view.kcp002.a {

    export module viewmodel {

        export class ScreenModel {
            managementCategoryModel: KnockoutObservableArray<ManagementCategoryModel>;
            constructor() {
                var self = this;
                self.managementCategoryModel = ko.observableArray<ManagementCategoryModel>([]);
            }
        }
        
        export class ManagementCategoryModel{
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
        }
    }
}