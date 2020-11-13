module nts.uk.at.kal014.a {

    import common=nts.uk.at.kal014.common;
    const PATH_API = {}

    const SCREEN = {
        B: 'B',
        C: 'C'
    }

    @bean()
    export class Kal014AViewModel extends ko.ViewModel {

        backButon: string = "/view/kal/013/a/index.xhtml";
        gridItems: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable(null);
        alarmPattern: AlarmPattern = new AlarmPattern('', '');
        selectedExecutePermission: KnockoutObservable<any>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        isNewMode: KnockoutObservable<boolean>;
        itemsSwap: KnockoutObservableArray<ItemModel>;
        tableItems: KnockoutObservableArray<TableItem>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        test: KnockoutObservableArray<any>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        workPalceCategory: any;

        constructor(props: any) {
            super();
            const vm = this;
            vm.workPalceCategory = common.WORKPLACE_CATAGORY;
            vm.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: vm.$i18n('KAL014_17'),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: vm.$i18n('KAL014_18'),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-3',
                    title: vm.$i18n('KAL014_19'),
                    content: '.tab-content-3',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            vm.selectedTab = ko.observable('tab-1');
            vm.itemsSwap = ko.observableArray([]);
            vm.tableItems = ko.observableArray([]);
            var array = [];
            //TODO change mock data with master data
            for (var i = 0; i < 6; i++) {
                array.push(new TableItem(i, "マスタチェック" + i, i, i, "締め開始日", i, i, i, i + 1, vm.workPalceCategory));
            }
            this.tableItems(array);
            array = [];
            //TODO change mock data with master data
            for (var i = 0; i < 100; i++) {
                array.push(new ItemModel(i, '基本給', "カテゴリ"));
            }
            vm.itemsSwap(array);
            vm.columns = ko.observableArray([
                {headerText: vm.$i18n('KAL014_22'), key: 'category', width: 70},
                {headerText: vm.$i18n('KAL014_23'), key: 'code', width: 70},
                {headerText: vm.$i18n('KAL014_24'), key: 'name', width: 180}
            ]);
            vm.currentCodeListSwap = ko.observableArray([]);
            vm.test = ko.observableArray([]);
            vm.roundingRules = ko.observableArray([
                {code: '1', name: vm.$i18n('KAL014_30')},
                {code: '2', name: vm.$i18n('KAL014_31')}
            ]);
            vm.selectedRuleCode = ko.observable(null);
            vm.isNewMode = ko.observable(true);
            vm.selectedExecutePermission = ko.observable("");
            $("#fixed-table").ntsFixedTable({height: 320, width: 830});
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            for (let i = 0; i < 100; i++) {
                vm.gridItems.push(new GridItem("code " + i + "", "name " + i));
            }
            vm.currentCode.subscribe((code:any)=>{
                alert(code);
                //TODO write server side logic and call API here
            });
        }

        remove() {
            this.itemsSwap.shift();
        }

        /**
         * This function is responsible to click per item action and take decision which screen will open
         * @param item
         * @return type void
         *
         * */
        clickTableItem(item: any) {
            const vm = this;
            if (item.categoryId === vm.workPalceCategory.MASTER_CHECK_BASIC
                || item.categoryId === vm.workPalceCategory.MASTER_CHECK_WORKPLACE
                || item.categoryId === vm.workPalceCategory.MONTHLY) {
                vm.openScreen(item, SCREEN.B);
            } else {
                vm.openScreen(item, SCREEN.C);
            }
        }

        /**
         * This function is responsible open modal KAL014 B, C
         * @param item
         * @param type <screen type B and C>
         * @return type void
         *
         * */
        openScreen(item: any, type: any) {
            const vm = this;
            let modalPath = type === SCREEN.B ? '/view/kal/014/b/index.xhtml' : '/view/kal/014/c/index.xhtml';
            let modalDataKey = type === SCREEN.B ? 'KAL014BModalData' : 'KAL014CModalData';
            vm.$window.storage(modalDataKey, item).done(() => {
                vm.$window.modal(modalPath)
                    .then((result: any) => {
                        console.log(nts.uk.ui.windows.getShared(modalDataKey));
                        //TODO write businees login with return data and make sure server side logic
                    });
            });
        }

        /**
         * This function is responsible to make screen the new mode
         * @return type void
         *
         * */
        clickNewButton() {
            const vm = this;
            vm.isNewMode(true);
            vm.$errors("clear", ".nts-editor", "button").then(() => {
                $("#A3_2").focus();
                vm.currentCode("");
                vm.cleanInput();
            });
        }

        /**
         * This function is responsible clean input data
         * @return type void
         *
         * */
        cleanInput() {
            const vm = this;
            vm.alarmPattern.code("");
            vm.alarmPattern.name("");
        }

        /**
         * This function is responsible to register data
         * @return type void
         *
         * */
        clickRegister() {
            const vm = this;
            vm.isNewMode(false);
            //TODO server business logic
        }

        /**
         * This function is responsible to delete data
         * @return type void
         *
         * */
        clickDeleteButton() {
            //TODO server business logic
        }

        /**
         * This function is responsible to configuration action
         * @return type void
         *
         * */
        clickConfiguration() {
            alert("Configuration");
            //TODO server business logic
        }
    }

    class ItemModel {
        code: number;
        name: string;
        category: string;

        constructor(code: number, name: string, category: string) {
            this.code = code;
            this.name = name;
            this.category = category;
        }
    }

    class TableItem {
        categoryId: any;
        categoryName: string;
        extractionPeriod: string;
        startMonth: any;
        endMonth: any;
        classification: any;
        numberOfDayFromStart: any;
        numberOfDayFromEnd: any;
        beforeAndAfterStart: any;
        beforeAndAfterEnd: any;
        workPalceCategory: any;

        constructor(categoryId: any,
                    categoryName: string,
                    startMonth: any,
                    endMonth: any,
                    classification: any,
                    numberOfDayFromStart: any,
                    numberOfDayFromEnd: any,
                    beforeAndAfterStart: any,
                    beforeAndAfterEnd: any,
                    workPalceCategory: any) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.startMonth = startMonth;
            this.endMonth = endMonth;
            this.classification = classification;
            this.numberOfDayFromStart = numberOfDayFromStart;
            this.numberOfDayFromEnd = numberOfDayFromEnd;
            this.beforeAndAfterStart = beforeAndAfterStart;
            this.beforeAndAfterEnd = beforeAndAfterEnd;
            this.workPalceCategory = workPalceCategory;
            this.extractionPeriod = (this.getExtractionPeriod(this));
        }

        /**
         * This function is responsible for getting extraction Period
         *
         * @param TableItem
         * @return string
         * */
        getExtractionPeriod(item: TableItem): string {
            const vm = this;
            let extractionText = "";
            switch (item.categoryId) {
                // マスタチェック(基本)
                case item.workPalceCategory.MASTER_CHECK_BASIC:
                    extractionText = this.getMonthValue(item.startMonth) + " ~ " + this.getMonthValue(item.endMonth);
                    break;
                // マスタチェック(職場)
                case item.workPalceCategory.MASTER_CHECK_WORKPLACE:
                    extractionText = this.getMonthValue(item.startMonth) + " ~ " + this.getMonthValue(item.endMonth);
                    break;
                //マスタチェック(日次)
                case item.workPalceCategory.MASTER_CHECK_DAILY:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break;
                // スケジュール／日次
                case item.workPalceCategory.SCHEDULE_DAILY:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break
                // 月次
                case item.workPalceCategory.MONTHLY:
                    extractionText = this.getMonthValue(item.startMonth);
                    break
                // 申請承認
                case item.workPalceCategory.APPLICATION_APPROVAL:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break
            }
            return extractionText;
        }

        /**
         * This function is responsible for getting month value
         *
         * @param month
         * @return string
         * */
        getMonthValue(month: any): string {
            if (month === 0) {
                return "当月";
            } else {
                return month + "ヶ月前";
            }
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

    class AlarmPattern {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }
}