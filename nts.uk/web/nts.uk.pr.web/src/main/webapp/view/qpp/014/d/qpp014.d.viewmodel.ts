// TreeGrid Node
module qpp014.d.viewmodel {
    export class ScreenModel {
        d_SEL_001_selectedCode: KnockoutObservable<any>;
        d_SEL_002_selectedCode: KnockoutObservable<any>;
        d_INP_001: any;
        d_LST_001_items: KnockoutObservableArray<any>;
        d_LST_001_itemSelected: KnockoutObservable<any>;
        
        constructor() {
            let self = this;

            self.d_SEL_001_selectedCode = ko.observable(1);
            self.d_SEL_002_selectedCode = ko.observable(1);
            self.d_INP_001 = {
                value: ko.observable('')
            };
            self.d_LST_001_items = ko.observableArray([]);
            for (let i = 1; i < 5; i++) {
                self.d_LST_001_items.push(({ code: '00' + i, name: ('基本給'), description: ('description' + i) }));
            }
            self.d_LST_001_itemSelected = ko.observable(0);
        }
    }
};
