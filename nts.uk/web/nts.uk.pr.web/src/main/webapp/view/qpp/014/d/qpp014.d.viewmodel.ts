// TreeGrid Node
module qpp014.d.viewmodel {
    export class ScreenModel {
        d_SEL_001_selectedCode: KnockoutObservable<any>;
        d_SEL_002_selectedCode: KnockoutObservable<any>;
        d_INP_001: any;
        d_LST_001_items: KnockoutObservableArray<any>;
        d_LST_001_itemSelected: KnockoutObservable<any>;
        d_nextScreen: KnockoutObservable<string>;

        constructor() {
            let self = this;

            self.d_SEL_001_selectedCode = ko.observable(1);
            self.d_SEL_002_selectedCode = ko.observable(1);
            self.d_INP_001 = {
                value: ko.observable('')
            };
            self.d_LST_001_items = ko.observableArray([]);
            for (let i = 1; i < 31; i++) {
                self.d_LST_001_items.push(({ code: '00' + i, name: '基本給' + i, description: ('description' + i) }));
            }
            self.d_LST_001_itemSelected = ko.observable(0);
            self.d_nextScreen = ko.computed(function() {
                return self.d_SEL_002_selectedCode() == 1 ? 'screeng' : 'screenh';
            })
        }

        openEDialog() {
            nts.uk.ui.windows.sub.modal("/view/qpp/014/e/index.xhtml", { title: "振込データの作成結果一覧", dialogClass: "no-close" });
        }
    }
};
