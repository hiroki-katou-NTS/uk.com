module cps008.c.viewmodel {


    export class ViewModel {

        layoutCode: KnockoutObservable<string>;
        layoutName: KnockoutObservable<string>;
        A_INP_LAYOUT_CODE: KnockoutObservable<string>;
        A_INP_LAYOUT_NAME: KnockoutObservable<string>;
        A_INP_LAYOUT_CODE_ENABLE: KnockoutObservable<boolean>;
        A_INP_LAYOUT_NAME_ENABLE: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.layoutCode = ko.observable('001');
            self.layoutName = ko.observable('test');
            self.A_INP_LAYOUT_CODE = ko.observable(null);
            self.A_INP_LAYOUT_NAME = ko.observable(null);
            self.A_INP_LAYOUT_CODE_ENABLE = ko.observable(true);
            self.A_INP_LAYOUT_NAME_ENABLE = ko.observable(true);
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            return null;
        }
    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;


        }
    }
}