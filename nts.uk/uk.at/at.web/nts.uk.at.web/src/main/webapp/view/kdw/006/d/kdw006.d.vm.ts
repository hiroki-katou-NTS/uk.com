module nts.uk.at.view.kdw006.d {
    export module viewmodel {
        export class ScreenModel {
            //Define radio button group
            itemList: KnockoutObservableArray<any> = ko.observableArray([
                new BoxModel(1, 'エラーがあっても確認できる'),
                new BoxModel(2, 'エラーを訂正するまでチェックできない'),
                new BoxModel(3, 'エラーを訂正するまで登録できない')
            ]);;
            selectedId: KnockoutObservable<number> = ko.observable(1);
            enable2: KnockoutObservable<boolean> = ko.observable(true);

            //Define checkbox
            enable: KnockoutObservable<boolean> = ko.observable(true);

            constructor() {
                let self = this;
            }

            saveData() {
                alert('screen d');
            }

            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }

        export class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}
