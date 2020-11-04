module nts.uk.at.kal014.a {
    import getText = nts.uk.resource.getText;
    const PATH_API = {
        getEnumAlarmCategory: "at/function/alarm/get/enum/alarm/category"
    }

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {

        backButon: string = "/view/kal/013/a/index.xhtml";
        gridItems: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable(null);
        alarmPattern: AlarmPattern = new AlarmPattern('', '');
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

        constructor(props: any) {
            super();
            const vm = this;
            vm.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: getText('KAL014_17'),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: getText('KAL014_18'),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-3',
                    title: getText('KAL014_19'),
                    content: '.tab-content-3',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            vm.selectedTab = ko.observable('tab-1');
            vm.itemsSwap = ko.observableArray([]);
            vm.tableItems = ko.observableArray([]);
            var array = [];
            for (var i = 0; i < 6; i++) {
                array.push(new TableItem(i, "マスタチェック" + i, i, i, "締め開始日", i, i, i, i + 1));
            }
            this.tableItems(array);
            array = [];
            for (var i = 0; i < 100; i++) {
                array.push(new ItemModel(i, '基本給', "カテゴリ"));
            }
            vm.itemsSwap(array);
            vm.columns = ko.observableArray([
                {headerText: getText('KAL014_22'), key: 'category', width: 70},
                {headerText: getText('KAL014_23'), key: 'code', width: 70},
                {headerText: getText('KAL014_24'), key: 'name', width: 180}
            ]);
            vm.currentCodeListSwap = ko.observableArray([]);
            vm.test = ko.observableArray([]);
            vm.roundingRules = ko.observableArray([
                {code: '1', name: getText('KAL014_30')},
                {code: '2', name: getText('KAL014_31')}
            ]);
            vm.selectedRuleCode = ko.observable(1);
            vm.isNewMode = ko.observable(true);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            for (let i = 0; i < 100; i++) {
                vm.gridItems.push(new GridItem("code " + i + "", "name " + i));
            }
        }

        mounted() {

        }

        remove() {
            this.itemsSwap.shift();
        }

        clickTableItem =function(item: any) {
            const vm = this;
            if (item.categoryId === WORKPLACE_CATAGORY.MASTER_CHECK_BASIC
                || item.categoryId === WORKPLACE_CATAGORY.MASTER_CHECK_WORKPLACE
                || item.categoryId ===WORKPLACE_CATAGORY.MONTHLY) {
                nts.uk.ui.windows.setShared("KAL014BModalData", item);
                nts.uk.ui.windows.sub.modal("/view/kal/014/b/index.xhtml").onClosed(() => {
                    console.log(nts.uk.ui.windows.getShared("KAL014BModalData"));
                });
            }else{
                nts.uk.ui.windows.setShared("KAL014BModalData", item);
                nts.uk.ui.windows.sub.modal("/view/kal/014/c/index.xhtml").onClosed(() => {
                    console.log(nts.uk.ui.windows.getShared("KAL014BModalData"));
                });
            }
        }

        clickNewButton() {
            const vm = this;
            vm.isNewMode(true);
            vm.$errors("clear", ".nts-editor", "button").then(() => {
                $("#A3_2").focus();
                vm.currentCode("");
                vm.cleanInput();
            });
        }

        cleanInput() {
            const vm = this;
            vm.alarmPattern.code("");
            vm.alarmPattern.name("");
        }

        clickRegister() {
            const vm = this;
            vm.isNewMode(false);
        }

        clickDeleteButton() {
        }

        clickConfiguration() {
            alert("Configuration");
        }
    }

    class ItemModel {
        code: number;
        name: string;
        category: string;
        deletable: boolean;

        constructor(code: number, name: string, category: string) {
            this.code = code;
            this.name = name;
            this.category = category;
            this.deletable = code % 3 === 0;
        }
    }

    // define WORKPLACE CATAGORY
    export enum WORKPLACE_CATAGORY {
        // マスタチェック(基本)
        MASTER_CHECK_BASIC = <number> 0,
        // マスタチェック(職場)
        MASTER_CHECK_WORKPLACE = <number> 1,
        // マスタチェック(日次)
        MASTER_CHECK_DAILY = <number> 2,
        // スケジュール／日次
        SCHEDULE_DAILY = <number> 3,
        // 月次
        MONTHLY = <number> 4,
        // 申請承認
        APPLICATION_APPROVAL = <number> 5
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

        constructor(categoryId: any,
                    categoryName: string,
                    startMonth: any,
                    endMonth: any,
                    classification: any,
                    numberOfDayFromStart: any,
                    numberOfDayFromEnd: any,
                    beforeAndAfterStart: any,
                    beforeAndAfterEnd: any) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.startMonth = startMonth;
            this.endMonth = endMonth;
            this.classification = classification;
            this.numberOfDayFromStart = numberOfDayFromStart;
            this.numberOfDayFromEnd = numberOfDayFromEnd;
            this.beforeAndAfterStart = beforeAndAfterStart;
            this.beforeAndAfterEnd = beforeAndAfterEnd;
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
                case WORKPLACE_CATAGORY.MASTER_CHECK_BASIC:
                    extractionText = this.getMonthValue(item.startMonth) + " ~ " + this.getMonthValue(item.endMonth);
                    break;
                // マスタチェック(職場)
                case WORKPLACE_CATAGORY.MASTER_CHECK_WORKPLACE:
                    extractionText = this.getMonthValue(item.startMonth) + " ~ " + this.getMonthValue(item.endMonth);
                    break;
                //マスタチェック(日次)
                case WORKPLACE_CATAGORY.MASTER_CHECK_DAILY:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break;
                // スケジュール／日次
                case WORKPLACE_CATAGORY.SCHEDULE_DAILY:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break
                case WORKPLACE_CATAGORY.MONTHLY:
                    // 月次
                    extractionText = this.getMonthValue(item.startMonth);
                    break
                // 申請承認
                case WORKPLACE_CATAGORY.APPLICATION_APPROVAL:
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
            switch (month) {
                case 0:
                    return "当月";
                case 1:
                    return "1ヶ月前";
                case 2:
                    return "2ヶ月前";
                case 3:
                    return "3ヶ月前";
                case 4:
                    return "4ヶ月前";
                case 5:
                    return "5ヶ月前";
                case 6:
                    return "6ヶ月前";
                case 7:
                    return "7ヶ月前";
                case 8:
                    return "8ヶ月前";
                case 9:
                    return "9ヶ月前";
                case 10:
                    return "10ヶ月前";
                case 11:
                    return "11ヶ月前";
                case 12:
                    return "12ヶ月前";
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