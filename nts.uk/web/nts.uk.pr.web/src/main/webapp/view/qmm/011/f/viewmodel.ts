module qmm011.f {
    export module viewmodel {
        export class ScreenModel {
            fsel001: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.fsel001 = ko.observableArray([
                    new BoxModel(1, '履歴を削除する'),
                    new BoxModel(2, '履歴を修正する')
                ]);
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);
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