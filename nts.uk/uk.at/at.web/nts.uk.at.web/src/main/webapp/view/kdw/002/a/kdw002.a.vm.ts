module nts.uk.at.view.kdw002.a {
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        export class ScreenModel {
            value: KnockoutObservable<string>;
            unitRoundings: KnockoutObservableArray<any>;
            selectedOption: KnockoutObservable<any>;
            dataSource: KnockoutObservableArray<ItemModel>;
            singleSelectedCode: any;
            columns: KnockoutObservableArray<any>;
            constructor() {
                var self = this;
                self.value = ko.observable('');
                self.unitRoundings = ko.observableArray(['1分', '5分', '10分', '15分', '30分', '60分']),
                    self.selectedOption = ko.observableArray(['1分'])

                this.dataSource = ko.observableArray([]);
                for (var i = 1; i < 10; i++) {
                    var code = '0' + i;
                    var name = 'name' + i;
                    this.dataSource.push(new ItemModel(code, name));
                }
                self.singleSelectedCode = ko.observable(null);
                 this.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 230 }
            ]);
                $(".clear-btn").hide();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }


        }

        class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                var self = this;
                self.code = code;
                self.name = name;

            }
        }
    }
}
