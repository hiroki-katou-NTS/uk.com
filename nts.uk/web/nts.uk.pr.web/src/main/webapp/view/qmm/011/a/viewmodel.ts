module qmm011.a.viewmodel {

    export class ScreenModel {
        blst001: KnockoutObservableArray<BItemModelLST001>;
        blstsel001: KnockoutObservableArray<string>;
        bsel001: KnockoutObservableArray<BItemModelSEL001>;
        bsel002: KnockoutObservableArray<BItemModelSEL001>;
        bsel003: KnockoutObservableArray<BItemModelSEL001>;
        bsel004: KnockoutObservableArray<BItemModelSEL001>;
        bsel005: KnockoutObservableArray<BItemModelSEL001>;
        bsel006: KnockoutObservableArray<BItemModelSEL001>;
        bsel001Code: KnockoutObservable<string>;
        bsel002Code: KnockoutObservable<string>;
        bsel003Code: KnockoutObservable<string>;
        bsel004Code: KnockoutObservable<string>;
        bsel005Code: KnockoutObservable<string>;
        bsel006Code: KnockoutObservable<string>;
        clst001: KnockoutObservableArray<CItemModelLST001>;
        clstsel001: KnockoutObservableArray<string>;
        csel001: KnockoutObservableArray<CItemModelSEL001>;
        csel0011: KnockoutObservableArray<CItemModelSEL001>;
        csel0012: KnockoutObservableArray<CItemModelSEL001>;
        csel0013: KnockoutObservableArray<CItemModelSEL001>;
        csel0014: KnockoutObservableArray<CItemModelSEL001>;
        csel0015: KnockoutObservableArray<CItemModelSEL001>;
        csel0016: KnockoutObservableArray<CItemModelSEL001>;
        csel0017: KnockoutObservableArray<CItemModelSEL001>;
        csel0018: KnockoutObservableArray<CItemModelSEL001>;
        csel0019: KnockoutObservableArray<CItemModelSEL001>;
        csel001Code: KnockoutObservable<string>;
        csel0011Code: KnockoutObservable<string>;
        csel0012Code: KnockoutObservable<string>;
        csel0013Code: KnockoutObservable<string>;
        csel0014Code: KnockoutObservable<string>;
        csel0015Code: KnockoutObservable<string>;
        csel0016Code: KnockoutObservable<string>;
        csel0017Code: KnockoutObservable<string>;
        csel0018Code: KnockoutObservable<string>;
        csel0019Code: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        isEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            var valueblst001 = ko.observableArray([
                new BItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                new BItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                new BItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                new BItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
            ]);
            var valuebsel001 = ko.observableArray([
                new BItemModelSEL001("0", "切り捨て"),
                new BItemModelSEL001("1", "切り上げ"),
                new BItemModelSEL001("2", "四捨五入"),
                new BItemModelSEL001("3", "五捨六入"),
                new BItemModelSEL001("4", "五捨五超入")
            ]);
            self.blst001 = valueblst001;
            self.blstsel001 = ko.observableArray([]);
            self.bsel001 = valuebsel001;
            self.bsel002 = valuebsel001;
            self.bsel003 = valuebsel001;
            self.bsel004 = valuebsel001;
            self.bsel005 = valuebsel001;
            self.bsel006 = valuebsel001;
            self.bsel001Code = ko.observable(null);
            self.bsel002Code = ko.observable(null);
            self.bsel003Code = ko.observable(null);
            self.bsel004Code = ko.observable(null);
            self.bsel005Code = ko.observable(null);
            self.bsel006Code = ko.observable(null);
            var valueclst001 = ko.observableArray([
                new CItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                new CItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                new CItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                new CItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
            ]);
            var valuecsel001 = ko.observableArray([
                new CItemModelSEL001("0", "切り捨て"),
                new CItemModelSEL001("1", "切り上げ"),
                new CItemModelSEL001("2", "四捨五入"),
                new CItemModelSEL001("3", "五捨六入"),
                new CItemModelSEL001("4", "五捨五超入")
            ]);
            self.clst001 = valueclst001;
            self.csel001 = valuecsel001;
            self.csel0011 = valuecsel001;
            self.csel0012 = valuecsel001;
            self.csel0013 = valuecsel001;
            self.csel0014 = valuecsel001;
            self.csel0015 = valuecsel001;
            self.csel0016 = valuecsel001;
            self.csel0017 = valuecsel001;
            self.csel0018 = valuecsel001;
            self.csel0019 = valuecsel001;
            self.clstsel001 = ko.observableArray([]);
            self.csel001Code = ko.observable(null);
            self.csel0011Code = ko.observable(null);
            self.csel0012Code = ko.observable(null);
            self.csel0013Code = ko.observable(null);
            self.csel0014Code = ko.observable(null);
            self.csel0015Code = ko.observable(null);
            self.csel0016Code = ko.observable(null);
            self.csel0017Code = ko.observable(null);
            self.csel0018Code = ko.observable(null);
            self.csel0019Code = ko.observable(null);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(2);
            self.isEnable = ko.observable(true);
        }
    }

    export class BItemModelLST001 {
        code: string;
        name: string
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class BItemModelSEL001 {
        code: string;
        name: string
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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
