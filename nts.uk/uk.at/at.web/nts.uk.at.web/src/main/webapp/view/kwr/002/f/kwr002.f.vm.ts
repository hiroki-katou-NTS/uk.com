/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.kwr002.f {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import windows = nts.uk.ui.windows;

    @bean()
    export class KWR002FViewModel extends ko.ViewModel {
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedName: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        F1_5_code: KnockoutObservable<string>;
        F1_6_name: KnockoutObservable<string>;

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
            self.F1_5_code = ko.observable('');
            self.F1_6_name = ko.observable('');


        }

        created() {
            let self = this;
            let code = nts.uk.ui.windows.getShared("codeScreenB");
            let name = nts.uk.ui.windows.getShared("nameScreenB");
            let dataFromScreenB = nts.uk.ui.windows.getShared("dataFromScreenB");
            self.selectedCode = ko.observable(code);
            self.selectedName = ko.observable(name);
            console.log(dataFromScreenB);


            // vm.F1_5_code = ko.observable(codeName.code());
        }

        mounted() {

        }

        executeCopy() {

        }

        closeDialog() {
            let self = this;
            // setShared('currentARESCode', self.currentARESCode(), true);
            windows.close();
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