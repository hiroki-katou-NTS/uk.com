module nts.uk.at.view.kcp002.a {

    import ManagementCategoryFindDto = service.model.ManagementCategoryFindDto;
    export module viewmodel {

        export class ScreenModel {
            managementCategoryModel: KnockoutObservableArray<ManagementCategoryModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeList: KnockoutObservableArray<string>;
            constructor() {
                var self = this;
                self.managementCategoryModel = ko.observableArray<ManagementCategoryModel>([]);

                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100  },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);
                
                self.currentCodeList = ko.observableArray<string>([]);
            }

            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findAllManagementCategory().done(data => {
                    console.log(data);
                    self.managementCategoryModel(data);
                    dfd.resolve(self);
                }).fail(function(error) {
                    console.log(error);
                });
                return dfd.promise();
            }
        }

        export class ManagementCategoryModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;

            constructor() {
                this.code = ko.observable('');
                this.name = ko.observable('');
            }

            updateDataDto(dto: ManagementCategoryFindDto) {
                this.code(dto.code);
                this.name(dto.code);
            }
        }
    }
}