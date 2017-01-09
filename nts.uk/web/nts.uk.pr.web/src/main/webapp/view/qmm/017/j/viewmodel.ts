module nts.uk.pr.view.qmm017.j {
    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.itemList = ko.observableArray([
                    new BoxModel(1, 'box 1'),
                    new BoxModel(2, 'box 2')
                ]);
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);
            }
        }

    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}