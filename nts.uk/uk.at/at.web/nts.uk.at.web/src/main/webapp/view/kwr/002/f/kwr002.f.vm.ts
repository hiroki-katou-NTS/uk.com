/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.kwr002.f {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import windows = nts.uk.ui.windows;
    import blockUI = nts.uk.ui.block;
    @bean()
    export class KWR002FViewModel extends ko.ViewModel {
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedName: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        duplicateCode: KnockoutObservable<string>;
        duplicateName: KnockoutObservable<string>;
        layoutId: string;
        selectionType: string;

        /**
         * Constructor.
         */
        constructor() {
            super();
            var vm = this;
            vm.itemList = ko.observableArray([]);
            vm.selectedCode = ko.observable('');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);
            vm.duplicateCode = ko.observable('');
            vm.duplicateName = ko.observable('');
        }

        created() {
            let vm = this;
            let dataFromScreenB = nts.uk.ui.windows.getShared("dataFromScreenB");
            vm.selectedCode = ko.observable(dataFromScreenB.code);
            vm.selectedName = ko.observable(dataFromScreenB.name);
            vm.selectionType = dataFromScreenB.itemSelectedType;
            vm.layoutId = dataFromScreenB.layoutId;
        }

        executeCopy() {
            let vm = this;
            let dataCopy = new AttendanceDuplicateDto(vm.selectedCode()
                                                    , vm.selectedName()
                                                    , ''
                                                    , vm.duplicateCode()
                                                    , vm.duplicateName());
            if (_.isEqual(vm.selectedCode, vm.duplicateCode())) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_355" });
                return;
            }

            vm.$blockui('show');

            service.executeCopy(dataCopy).done((data: any) => {

            }).fail(function(err) {
                vm.$dialog.alert(err.messageId);
            }).always(function() {
                vm.$blockui('clear');
            });
        }

        closeDialog() {
            let vm = this;
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

    class AttendanceDuplicateDto {
        code: string;
        name: string;
        duplicateCode: string;
        duplicateName: string;
        selectedType: number;
        layoutId: string;


        constructor(code: string
            , name: string
            , layoutId: string
            , duplicateCode: string
            , duplicateName: string) {
            this.code = code;
            this.name = name;
            this.layoutId = layoutId;
            this.duplicateCode = duplicateCode;
            this.duplicateName = duplicateName;
        }
    }

}