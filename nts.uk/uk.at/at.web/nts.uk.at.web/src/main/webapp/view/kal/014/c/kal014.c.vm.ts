module nts.uk.at.kal014.c {

    const PATH_API = {}

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {
        modalDTO: ModalDto = new ModalDto();
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isStartDateEnable: KnockoutObservable<boolean>;
        isEndDateEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;
        classifications: KnockoutObservableArray<any>;
        classificationVal: KnockoutObservable<any>;
        strComboDay: KnockoutObservableArray<any>;
        endComboDay: KnockoutObservableArray<any>;

        constructor(props: any) {
            super();
            const vm = this;
            let modalData = nts.uk.ui.windows.getShared("KAL014BModalData");
            console.log(modalData);
            vm.initModalData(modalData);
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
            vm.strComboDay = ko.observableArray(__viewContext.enums.PreviousClassification);
            vm.endComboDay = ko.observableArray(__viewContext.enums.PreviousClassification);

            vm.classifications = ko.observableArray([
                new BoxModel(1, '')
            ]);
            vm.classificationVal = ko.observable(1);
        }

        created() {
            const vm = this;
        }

        mounted() {

        }

        initModalData(modalData: any) {
            const vm = this;
            vm.modalDTO.categoryId(modalData.categoryId);
            vm.modalDTO.categoryName(modalData.categoryName);
            vm.modalDTO.startMonth(modalData.startMonth);
            vm.modalDTO.endMonth(modalData.endMonth);
            vm.modalDTO.classification(modalData.classification);
            vm.modalDTO.numberOfDayFromStart(modalData.numberOfDayFromStart);
            vm.modalDTO.numberOfDayFromEnd(modalData.numberOfDayFromEnd);
            vm.modalDTO.beforeAndAfterStart(modalData.beforeAndAfterStart);
            vm.modalDTO.beforeAndAfterEnd(modalData.beforeAndAfterEnd);
        }

        checkStartDateISEnable(): boolean {
            const vm = this;
            return vm.modalDTO.categoryId() === 3;
        }

        checkEndDateISEnable(): boolean {
            const vm = this;
            return vm.modalDTO.categoryId() === 3;
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
            if (vm.modalDTO.startMonth() === 0) {
                $(".strComboMonth").focus();
                return;
            }
            if (vm.modalDTO.endMonth() === 0) {
                $(".endComboMonth").focus();
                return;
            } else if (vm.checkPeriod()) {
                let shareData = {
                    categoryId: vm.modalDTO.categoryId(),
                    categoryName: vm.modalDTO.categoryName(),
                    extractionPeriod: vm.modalDTO.extractionPeriod(),
                    startDate: vm.modalDTO.startDate(),
                    endDate: vm.modalDTO.endDate()
                }
                nts.uk.ui.windows.setShared("KAL014BModalData", shareData);
                console.log("shareData:", nts.uk.ui.windows.getShared("KAL014BModalData"));
                vm.cancel_Dialog();
            }
        }

        checkPeriod(): boolean {
            var vm = this;
            if ((vm.modalDTO.categoryId() === 0 || vm.modalDTO.categoryId() === 1) && vm.modalDTO.startMonth() > vm.modalDTO.endMonth()) {
                nts.uk.ui.dialog.alertError({messageId: "Msg_812"});
                return false;
            } else if (vm.modalDTO.categoryId() === 4 && vm.modalDTO.startMonth() < vm.modalDTO.endMonth()) {
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
        startMonth: KnockoutObservable<any>;
        endMonth: KnockoutObservable<any>;
        classification: any;
        numberOfDayFromStart: any;
        numberOfDayFromEnd: any;
        beforeAndAfterStart: any;
        beforeAndAfterEnd: any;

        constructor() {
            this.categoryId = ko.observable('');
            this.categoryName = ko.observable('');
            this.categoryId = ko.observable('');
            this.startMonth = ko.observable('');
            this.endMonth = ko.observable('');
            this.extractionPeriod = ko.observable('');
            this.classification = ko.observable('');
            this.numberOfDayFromStart = ko.observable('');
            this.numberOfDayFromEnd = ko.observable('');
            this.beforeAndAfterStart = ko.observable('');
            this.beforeAndAfterEnd = ko.observable('');
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

    class BoxModel {
        id: number;
        name: string;

        constructor(id: number, name: string) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}