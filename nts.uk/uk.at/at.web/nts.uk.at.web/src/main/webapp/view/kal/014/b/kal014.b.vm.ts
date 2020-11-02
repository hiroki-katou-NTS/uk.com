module nts.uk.at.kal014.b {

    const PATH_API = {}

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {
        modalDTO: ModalDto = new ModalDto(null, "", "", "");
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isStartDateEnable: KnockoutObservable<boolean>;
        isEndDateEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;

        constructor(props: any) {
            super();
            const vm = this;
            let modalData = nts.uk.ui.windows.getShared("KAL014BModalData");
            console.log(modalData);
            vm.modalDTO.categoryId(modalData.categoryId);
            vm.modalDTO.categoryName(modalData.categoryName);
            vm.modalDTO.startDate(modalData.startDate);
            vm.modalDTO.endDate(modalData.endDate);
            vm.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            for (let i = 1; i < 100; i++) {

            }

            vm.selectedCode = ko.observable('1');
            vm.isStartDateEnable = ko.observable(vm.checkStartDateISEnable());
            vm.isEndDateEnable = ko.observable(vm.checkEndDateISEnable());
            vm.isEditable = ko.observable(true);
            vm.strComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            vm.endComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
        }

        created() {
            const vm = this;
        }

        mounted() {

        }

        checkStartDateISEnable(): boolean {
            const vm = this;
            return (vm.modalDTO.categoryId() === 0 || vm.modalDTO.categoryId() === 1 || vm.modalDTO.categoryId() === 4);
        }

        checkEndDateISEnable(): boolean {
            const vm = this;
            return (vm.modalDTO.categoryId() === 0 || vm.modalDTO.categoryId() === 1);
        }

        setDefault() {
            var self = this;
            // nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        decide(): any {
            var vm = this;
            if (vm.modalDTO.startDate() === 0) {
                $(".strComboMonth").focus();
            }
            if (vm.modalDTO.endDate() === 0) {
                $(".endComboMonth").focus();
            }
            if ($(".nts-input").ntsError("hasError")) {
                return;
            } else if (vm.checkPeriod()) {
                let shareData = {
                    categoryId: vm.modalDTO.categoryId,
                    categoryName: vm.modalDTO.categoryName,
                    extractionPeriod: vm.modalDTO.extractionPeriod,
                    startDate: vm.modalDTO.startDate,
                    endDate: vm.modalDTO.endDate
                }
                nts.uk.ui.windows.setShared("KAL014BModalData", shareData);
                console.log("shareData:",nts.uk.ui.windows.getShared("KAL014BModalData"));
                vm.cancel_Dialog();
            }
        }

        checkPeriod(): boolean {
            var vm = this;
            if ((vm.modalDTO.categoryId() === 0 || vm.modalDTO.categoryId() === 1) && vm.modalDTO.startDate() > vm.modalDTO.endDate()) {
                nts.uk.ui.dialog.alertError({messageId: "Msg_812"});
                return false;
            } else if (vm.modalDTO.categoryId() === 4 && vm.modalDTO.startDate() < vm.modalDTO.endDate()) {
                nts.uk.ui.dialog.alertError({messageId: "Msg_812"});
                return false;
            } else {
                return true;
            }
        }
    }

    class ModalDto {
        categoryId: KnockoutObservable<any>;
        categoryName: KnockoutObservable<string>;
        extractionPeriod: KnockoutObservable<string>;
        startDate: KnockoutObservable<any>;
        endDate: KnockoutObservable<any>;

        constructor(categoryId: any, categoryName: string, startDate: any, endDate: any) {
            this.categoryId = ko.observable(categoryId);
            this.categoryName = ko.observable(categoryName);
            this.categoryId = ko.observable(categoryId);
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
            this.extractionPeriod = ko.observable(startDate + "~" + endDate);
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