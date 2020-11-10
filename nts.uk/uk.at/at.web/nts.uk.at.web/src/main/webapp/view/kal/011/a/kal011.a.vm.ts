module nts.uk.at.kal011.a {

    import common=nts.uk.at.kal014.common;
    const PATH_API = {}

    @bean()
    export class Kal011AViewModel extends ko.ViewModel {
        alarmList: KnockoutObservableArray<AlarmPattern>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        isAllchecked: KnockoutObservable<boolean>;
        isFromChildUpdate: KnockoutObservable<boolean>
        isFromParentUpdate: KnockoutObservable<boolean>
        itemList: KnockoutObservableArray<TableItem> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<any>;
        // work place list
        multiSelectedId: KnockoutObservable<any>;
        baseDate: KnockoutObservable<Date>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        treeGrid: TreeComponentOption;
        processingState: KnockoutObservable<any>;
        workPalceCategory: any;

        constructor(props: any) {
            super();
            const vm = this;
            vm.workPalceCategory = common.WORKPLACE_CATAGORY;
            vm.alarmList = ko.observableArray([]);
            for (let i = 0; i < 11; i++) {
                vm.alarmList.push(new AlarmPattern("0" + i, "基本給" + i));
            }
            vm.selectedCode = ko.observable('1');
            vm.currentCodeList = ko.observableArray([]);
            vm.baseDate = ko.observable(new Date());
            vm.multiSelectedId = ko.observableArray([]);
            vm.alreadySettingList = ko.observableArray([]);
            vm.isAllchecked = ko.observable(false);
            vm.isFromChildUpdate = ko.observable(false);
            vm.isFromParentUpdate = ko.observable(false);
            // mock data
            vm.processingState = ko.observable(1);
            vm.treeGrid = {
                isMultipleUse: false,
                isMultiSelect: true,
                startMode: StartMode.WORKPLACE,
                selectedId: vm.multiSelectedId,
                baseDate: vm.baseDate,
                selectType: SelectionType.SELECT_FIRST_ITEM,
                isShowSelectButton: true,
                isDialog: false,
                alreadySettingList: vm.alreadySettingList,
                maxRows: 12,
                tabindex: -1,
                systemType: 2
            };
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            // mock data
            for (let i = 0; i < 6; i++) {
                vm.itemList.push(new TableItem(false, i, 'マスタチェック(' + i + ')', moment(vm.baseDate()).format("YYYY/MM/DD"), moment(vm.baseDate()).format("YYYY/MM/DD"), vm));
            }
            $('#tree-grid').ntsTreeComponent(vm.treeGrid);
            vm.isAllchecked.subscribe((isChecked) => {
                if (!vm.isFromChildUpdate()) {
                    vm.isFromParentUpdate(true);
                    for (let index = 0; index < vm.itemList().length; index++) {
                        vm.itemList()[index].isChecked(isChecked);
                    }
                    vm.isFromParentUpdate(false);
                } else {
                    vm.isFromChildUpdate(false);
                }
            });

            vm.selectedCode.subscribe((code) => {
                //TODO write the business logic with server side data
                // alert(code);
            });
        }

        /*
        * This function is responsible check and uncheck all item
        *
        * @return void
        * */
        checkAll() {
            const vm = this;
            if (!vm.isFromParentUpdate()) {
                let isAllSeletet = _.find(vm.itemList(), (x: TableItem) => {
                        return x.isChecked() == false
                    }
                );
                vm.isFromChildUpdate(true);
                if (!!isAllSeletet) {
                    vm.isAllchecked(false);
                } else {
                    vm.isAllchecked(true);
                }
            }
        }

        /*
       * This function is responsible to open modal
       *
       * @return void
       * */
        openModal() {
            const vm = this;
            if (vm.multiSelectedId().length === 0) {
                vm.$dialog.error("Msg_834").then(() => {
                    return
                });
            }
            if (vm.selectedCode() === '') {
                vm.$dialog.error("Msg_1167").then(() => {
                    return
                });
            }
            if (vm.itemList().length === 0) {
                vm.$dialog.error("Msg_1168").then(() => {
                    return
                });
            }
            let modalData = {
                executionStartDateTime: moment(vm.baseDate()).format("YYYY/MM/DD:H:mm:ss"),
                processingState: vm.processingState(),
                selectedCode: vm.selectedCode()
            }
            vm.$window.storage('KAL011DModalData', modalData).done(() => {
                vm.$window.modal('/view/kal/011/d/index.xhtml')
                    .then((result: any) => {
                        vm.openKal011BMOdal();
                    });
            });
        }

        /*
         * This function is responsible to open Kal011B
         *
         * @return void
         * */
        openKal011BMOdal() {
            const vm = this;
            vm.$window.storage('KAL011DModalData').done((data) => {
                if (!data.isClose) {
                    vm.$window.storage('KAL011BModalData', data).done(() => {
                        vm.$window.modal('/view/kal/011/b/index.xhtml')
                            .then((result: any) => {
                                $('#grid > *').attr("tabindex", "-1");
                            })
                    });
                }
            });
        }
    }

    export interface TreeComponentOption {
        /**
         * is Show Already setting.
         */
        isShowAlreadySet: boolean;

        /**
         * is Multi use (複数使用区分). Setting use multiple components?
         */
        isMultipleUse: boolean;

        /**
         * is Multi select (選択モード). Setting multiple selection in grid.
         */
        isMultiSelect: boolean;

        /**
         * Tree type, if not set, default is work place.
         */
        startMode?: StartMode;

        /**
         * selected value.
         * May be string or Array<string>
         */
        selectedId: KnockoutObservable<any>;

        /**
         * Base date.
         */
        baseDate: KnockoutObservable<Date>;

        /**
         * Select mode
         */
        selectType: SelectionType;

        /**
         * isShowSelectButton
         * Show/hide button select all and selected sub parent
         */
        isShowSelectButton: boolean;

        /**
         * is dialog, if is main screen, set false,
         */
        isDialog: boolean;

        /**
         * Default padding of KCPs
         */
        hasPadding?: boolean;

        /**
         * Already setting list code. structure: {id: string, isAlreadySetting: boolean}
         * ignore when isShowAlreadySet = false.
         */
        alreadySettingList?: KnockoutObservableArray<UnitAlreadySettingModel>;

        /**
         * Limit display row
         */
        maxRows?: number;

        /**
         * set tabIndex
         */
        tabindex?: number;

        /**
         * system type
         */
        systemType: SystemType;

        // 参照範囲の絞
        restrictionOfReferenceRange?: boolean;

        /**
         * Check is show no select row in grid list.
         */
        isShowNoSelectRow?: boolean;

        /**
         * Show all levels of department workplace on start
         */
        isFullView?: boolean;
        width?: number;
        listDataDisplay: Array<any>;
    }

    export class SelectionType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export class StartMode {
        static WORKPLACE = 0;

        // 部門対応 #106784
        static DEPARTMENT = 1;
    }

    /**
     * System type ~ システム区分
     *
     */
    export class SystemType {

        // 個人情報
        static PERSONAL_INFORMATION: number = 1;

        // 就業
        static EMPLOYMENT: number = 2;

        // 給与
        static SALARY: number = 3;

        // 人事
        static HUMAN_RESOURCES: number = 4;

        // 管理者
        static ADMINISTRATOR: number = 5;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

    export class DateRangePickerModel {
        startDate: string;
        endDate: string;

        constructor(startDate: string, endDate: string) {
            let self = this;
            self.startDate = startDate;
            self.endDate = endDate;
        }

        public static isSamePeriod(a: DateRangePickerModel, b: DateRangePickerModel): boolean {
            return a.startDate === b.startDate && a.endDate === b.endDate
        }
    }

    class AlarmPattern {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class GridItem {
        code: string
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class TableItem {
        isChecked: KnockoutObservable<boolean>;
        categoryId: any
        categoryName: any;
        startDate: any;
        endDate: any;
        dateRange: KnockoutObservable<DateRangePickerModel>;
        viewModel: Kal011AViewModel;
        type: KnockoutObservable<string>;

        constructor(isChecked: boolean, categoryId: any, categoryName: any, startDate: any, endDate: any, viewModel: Kal011AViewModel) {
            this.isChecked = ko.observable(isChecked);
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.dateRange = ko.observable(new DateRangePickerModel(startDate, endDate));
            this.viewModel = viewModel;
            this.type = ko.observable(this.getType(categoryId, viewModel.workPalceCategory));
            this.isChecked.subscribe((v) => {
                this.viewModel.checkAll();
            });
        }

        /*
        * This function is responsible to make decision date range type
        *
        * @return void
        * */
        getType(categoryId: any, workPalceCategory: any): string {
            if (categoryId === workPalceCategory.MASTER_CHECK_BASIC ||
                categoryId === workPalceCategory.MASTER_CHECK_WORKPLACE ||
                categoryId === workPalceCategory.MONTHLY) {
                return 'date'
            }
            return 'yearmonth'
        }
    }
}