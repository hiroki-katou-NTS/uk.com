module nts.uk.at.kal014.b {

    import common=nts.uk.at.kal014.common;
    const PATH_API = {}

    @bean()
    export class Kal014BViewModel extends ko.ViewModel {
        modalDTO: ModalDto = new ModalDto();
        isStartDateEnable: KnockoutObservable<boolean>;
        isEndDateEnable: KnockoutObservable<boolean>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;
        workPalceCategory: any;

        constructor(props: any) {
            super();
            const vm = this;
            vm.workPalceCategory = common.WORKPLACE_CATAGORY;
            let modalData = nts.uk.ui.windows.getShared("KAL014BModalData");
            console.log(modalData);
            vm.modalDTO.categoryId(modalData.categoryId);
            vm.modalDTO.categoryName(modalData.categoryName);
            vm.modalDTO.startMonth(modalData.startDate);
            vm.modalDTO.endMonth(modalData.endDate);
            vm.isStartDateEnable = ko.observable(vm.checkStartDateISEnable());
            vm.isEndDateEnable = ko.observable(vm.checkEndDateISEnable());
            vm.strComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            vm.endComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        mounted() {

        }

        /**
         * This function is responsible to check start month disable enable check
         *
         * @return boolean
         **/
        checkStartDateISEnable(): boolean {
            const vm = this;
            return (vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_BASIC
                || vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_WORKPLACE
                || vm.modalDTO.categoryId() === vm.workPalceCategory.MONTHLY);
        }

        /**
         * This function is responsible to check end month disable enable check
         *
         * @return boolean
         **/
        checkEndDateISEnable(): boolean {
            const vm = this;
            return (vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_BASIC
                || vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_WORKPLACE);
        }

        /**
         * This function is responsible to close the modal
         *
         * @return type void         *
         * */
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        /**
         * This function is responsible to perform modal data action
         *
         * @return type void         *
         * */
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
                    startDate: vm.modalDTO.startMonth(),
                    endDate: vm.modalDTO.endMonth()
                }
                nts.uk.ui.windows.setShared("KAL014BModalData", shareData);
                console.log("shareData:", nts.uk.ui.windows.getShared("KAL014BModalData"));
                vm.cancel_Dialog();
            }
        }

        /**
         * This function is responsible to error validation check
         *
         * @return type void               *
         * */
        checkPeriod(): boolean {
            var vm = this;
            if ((vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_BASIC || vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_WORKPLACE) && vm.modalDTO.startMonth() > vm.modalDTO.endMonth()) {
                nts.uk.ui.dialog.alertError({messageId: "Msg_812"});
                return false;
            } else if (vm.modalDTO.categoryId() === vm.workPalceCategory.MONTHLY && vm.modalDTO.startMonth() < vm.modalDTO.endMonth()) {
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

        constructor() {
            this.categoryId = ko.observable('');
            this.categoryName = ko.observable('');
            this.startMonth = ko.observable('');
            this.endMonth = ko.observable('');
            this.extractionPeriod = ko.observable('');
        }
    }
}