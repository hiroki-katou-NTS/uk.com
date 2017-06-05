module nts.uk.at.view.kcp002.a {

    import ClassificationFindDto = service.model.ClassificationFindDto;
    export module viewmodel {

        export class ScreenModel {
            ClassificationModel: KnockoutObservableArray<ClassificationModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeList: KnockoutObservableArray<string>;
            constructor() {
                var self = this;
                self.ClassificationModel = ko.observableArray<ClassificationModel>([]);

                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100  },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);
                
                self.currentCodeList = ko.observableArray<string>([]);
            }

            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findAllClassification().done(data => {
                    console.log(data);
                    self.ClassificationModel(data);
                    dfd.resolve(self);
                }).fail(function(error) {
                    console.log(error);
                });
                return dfd.promise();
            }
        }

        export class ClassificationModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;

            constructor() {
                this.code = ko.observable('');
                this.name = ko.observable('');
            }

            updateDataDto(dto: ClassificationFindDto) {
                this.code(dto.code);
                this.name(dto.code);
            }
        }
    }
}