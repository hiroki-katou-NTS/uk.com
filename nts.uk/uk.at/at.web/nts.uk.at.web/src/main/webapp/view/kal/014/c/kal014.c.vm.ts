module nts.uk.at.kal014.c {

    import common=nts.uk.at.kal014.common;
    const PATH_API = {
        //TODO make API path
    }

    @bean()
    export class Kal014CViewModel extends ko.ViewModel {
        modalDTO: ModalDto = new ModalDto();
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;
        classifications: KnockoutObservableArray<any>;
        strComboDay: KnockoutObservableArray<any>;
        endComboDay: KnockoutObservableArray<any>;
        strSelected: KnockoutObservable<number>;
        endSelected: KnockoutObservable<number>;
        workPalceCategory: any;
        CLASSIFICATION: any;
        dateSpecify: KnockoutObservableArray<any>;
        monthSpecify: KnockoutObservableArray<any>;

        constructor(props: any) {
            super();
            const vm = this;
            vm.workPalceCategory = common.WORKPLACE_CATAGORY;
            vm.CLASSIFICATION = common.CLASSIFICATION;
            let modalData = nts.uk.ui.windows.getShared("KAL014CModalData");
            console.log(modalData);
            vm.initModalData(modalData);
            vm.strComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            vm.endComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            vm.strComboDay = ko.observableArray(__viewContext.enums.PreviousClassification);
            vm.endComboDay = ko.observableArray(__viewContext.enums.PreviousClassification);
            vm.classifications = ko.observableArray([
                new BoxModel(0, '')
            ]);
            vm.strSelected = ko.observable(0);
            vm.endSelected = ko.observable(0);
            vm.dateSpecify = ko.observableArray([
                {value: 0, name: vm.$i18n("KAL014_44")},
                {value: 1, name: ""}
            ]);
            vm.monthSpecify = ko.observableArray([
                {value: 0, name: vm.$i18n("KAL014_49")},
                {value: 1, name: ""}
            ]);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        /**
         * This function is responsible to initialized modal data
         *
         * @return boolean
         **/
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
                    startMonth: vm.modalDTO.startMonth(),
                    endMonth: vm.modalDTO.endMonth(),
                    classification: vm.modalDTO.classification(),
                    numberOfDayFromStart: vm.modalDTO.numberOfDayFromStart(),
                    numberOfDayFromEnd: vm.modalDTO.numberOfDayFromEnd(),
                    beforeAndAfterStart: vm.modalDTO.beforeAndAfterStart(),
                    beforeAndAfterEnd: vm.modalDTO.beforeAndAfterEnd(),
                }
                vm.$window.storage("KAL014CModalData", shareData).done(() => {
                    console.log("shareData:", nts.uk.ui.windows.getShared("KAL014CModalData"));
                    vm.cancel_Dialog();
                });
            }
        }

        /**
         * This function is responsible to error validation check[補足資料２]
         *
         * @return type void               *
         * */
        checkPeriod(): boolean {
            var vm = this;
            let mockStartCategory = "Same day";
            let mockEndCategory = "Closing end date";
            //TODO mockStartCategory ,mockEndCategory ->master data
            if (vm.modalDTO.categoryId() === vm.workPalceCategory.SCHEDULE_DAILY ||
                vm.modalDTO.categoryId() === vm.workPalceCategory.APPLICATION_APPROVAL ||
                vm.modalDTO.categoryId() === vm.workPalceCategory.MASTER_CHECK_DAILY) {
                /*
                * (a）開始区分＝「当日」　AND　終了区分＝「当日」
                * TODO [Same day] will be change with data master data.
                *
                */
                if (mockStartCategory === 'Same day' && mockEndCategory === 'Same day') {
                    /**
                     * ①開始の前後区分＝「後」　AND　終了の前後区分＝「前」
                     */
                    if (vm.modalDTO.beforeAndAfterStart === vm.CLASSIFICATION.AHEAD && vm.modalDTO.beforeAndAfterEnd === vm.CLASSIFICATION.BEFORE) {
                        vm.$dialog.error({messageId: "Msg_812"}).then(() => {
                            return false;
                        });
                    }
                    /**
                     * ②開始の前後区分＝「前」　AND　終了の前後区分＝「前」 　AND　開始の日数　＜　終了の日数
                     */
                    else if ((vm.modalDTO.beforeAndAfterStart === vm.CLASSIFICATION.BEFORE && vm.modalDTO.beforeAndAfterEnd === vm.CLASSIFICATION.BEFORE) && (vm.modalDTO.numberOfDayFromStart < vm.modalDTO.numberOfDayFromEnd)) {
                        vm.$dialog.error({messageId: "Msg_812"}).then(() => {
                            return false;
                        });
                    }
                    /**
                     * ③開始の前後区分＝「後」　AND　終了の前後区分＝「後」
                     */
                    else if ((vm.modalDTO.beforeAndAfterStart === vm.CLASSIFICATION.AHEAD && vm.modalDTO.beforeAndAfterEnd === vm.CLASSIFICATION.AHEAD) && (vm.modalDTO.numberOfDayFromStart > vm.modalDTO.numberOfDayFromEnd)) {
                        vm.$dialog.error({messageId: "Msg_812"}).then(() => {
                            return false;
                        });
                    } else {
                        return true;
                    }
                }
                /**
                 * (b）開始区分＝「当日」　AND　終了区分＝「締め終了日」
                 AND　終了の月数＝当月
                 * TODO ['Same day' and 'Closing end date' will be change to the master data]
                 */
                else if ((mockStartCategory === "Same day" && mockEndCategory === "Closing end date") && vm.modalDTO.endMonth() === 0) {
                    vm.$dialog.error({messageId: "Msg_813"}).then(() => {
                        return false;
                    });
                }
                /**
                 * (c）開始区分＝「締め開始日」　AND　終了区分＝「締め終了日」
                 AND　開始の月数　＞　終了の月数
                 * TODO ['closing start date' and 'Closing end date' will be change to the master data]
                 */
                else if ((mockStartCategory === "closing start date" && mockEndCategory === "Closing end date") && vm.modalDTO.startMonth() > vm.modalDTO.endMonth) {
                    vm.$dialog.error({messageId: "Msg_812"}).then(() => {
                        return false;
                    });
                } else {
                    return true;
                }
            }
            /*5	月次		(a)	開始月　<　終了月*/
            else if (vm.modalDTO.categoryId() === vm.workPalceCategory.MONTHLY && vm.modalDTO.startMonth() < vm.modalDTO.endMonth) {
                vm.$dialog.error({messageId: "Msg_812"}).then(() => {
                    return false;
                });
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
            this.endMonth = ko.observable(null);
            this.extractionPeriod = ko.observable('');
            this.classification = ko.observable('');
            this.numberOfDayFromStart = ko.observable('');
            this.numberOfDayFromEnd = ko.observable('');
            this.beforeAndAfterStart = ko.observable('');
            this.beforeAndAfterEnd = ko.observable('');
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