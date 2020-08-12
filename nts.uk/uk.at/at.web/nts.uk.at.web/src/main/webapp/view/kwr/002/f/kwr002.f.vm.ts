/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.kwr002.f {

    @bean()
    export class KWR002FViewModel extends ko.ViewModel {
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        F1_5_value: KnockoutObservable<string>;
        F1_6_value: KnockoutObservable<string>;

        /**
         * Constructor.
         */
        constructor() {
            super();
            var self = this;
            self.itemList = ko.observableArray([]);

            self.selectedCode = ko.observable('');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.F1_6_value = ko.observable('');
            self.F1_6_value = ko.observable('');
        }

        created() {

        }

        mounted() {

        }

        executeCopy() {

        }

        closeDialog() {

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