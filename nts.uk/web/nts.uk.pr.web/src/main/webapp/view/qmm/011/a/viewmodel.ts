module qmm011.a.viewmodel {

     export class ScreenModel {
        clst001: KnockoutObservableArray<CItemModelLST001>;
        csel001: KnockoutObservableArray<CItemModelSEL001>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.clst001 = ko.observableArray([
                new CItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                new CItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                new CItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                new CItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
            ]);
             self.csel001 = ko.observableArray([
                new CItemModelSEL001("0", "切り捨て"),
                new CItemModelSEL001("1", "切り上げ"),
                new CItemModelSEL001("2", "四捨五入"),
                new CItemModelSEL001("3", "五捨六入"),
                new CItemModelSEL001("4", "五捨五超入")
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(2);
            self.selectedCode = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);
        }
    }

    export class CItemModelLST001 {
        code: string;
        name: string
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class CItemModelSEL001 {
        code: string;
        name: string
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}
