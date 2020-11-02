module nts.uk.at.kal014.a {
    import getText = nts.uk.resource.getText;
    const PATH_API = {
        getEnumAlarmCategory : "at/function/alarm/get/enum/alarm/category"
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
        clickTableItem = function (item: any) {
            const vm = this;
            nts.uk.ui.windows.setShared("KAL014BModalData", item);
            nts.uk.ui.windows.sub.modal("/view/kal/014/b/index.xhtml").onClosed(() => {
            });
        }

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
            for (var i = 1; i <= 100; i++) {
                array.push(new TableItem("cat" + i,"マスタチェック"+i ,"当月の締め開始日","　当月の締め終了日 "));
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
            for (let i = 0; i < 100; i++) {
                vm.gridItems.push(new GridItem("code " + i + "", "name " + i));
            }
        }

        mounted() {

        }

        remove() {
            this.itemsSwap.shift();
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

    class TableItem {
        categoryId: string;
        categoryName: string;
        extractionPeriod: string;
        startDate: string;
        endDate: string;

        constructor(categoryId: string, categoryName: string, startDate: string, endDate: string) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.categoryId = categoryId;
            this.extractionPeriod = (startDate+"~"+endDate);
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