module nts.uk.pr.view.qpp007.j {
    export module viewmodel {
        import SalaryCategory = nts.uk.pr.view.qpp007.c.viewmodel.SalaryCategory;

        export class ScreenModel {
            tabs1: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            tabs2: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab1: KnockoutObservable<string>;
            selectedTab2: KnockoutObservable<string>;

            name: KnockoutObservable<string>;

            itemsSwap: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwap: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.name = ko.observable('');
                self.tabs1 = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: SalaryCategory.PAYMENT_TOTAL, title: '支給集計', content: '#1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.DEDUCTION_TABULATION, title: '控除集計', content: '#2', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.tabs2 = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: 'tab-1', title: '集計項目1', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '集計項目2', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: '集計項目3', content: '#tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: '集計項目4', content: '#tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },

                ]);

                self.selectedTab1 = ko.observable(SalaryCategory.PAYMENT_TOTAL);
                self.selectedTab2 = ko.observable('tab-1');

                this.itemsSwap = ko.observableArray<ItemModel>([]);

                var array = [];
                for (var i = 0; i < 20; i++) {
                    array.push(new ItemModel(i, '基本給', "description"));
                }
                this.itemsSwap(array);

                this.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);

                this.currentCodeListSwap = ko.observableArray([]);

            }
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

            public closeDialogBtnClicked() {
                nts.uk.ui.windows.close();
            }

        }
        class ItemModel {
            code: number;
            name: string;
            description: string;
            deletable: boolean;
            constructor(code: number, name: string, description: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.deletable = code % 3 === 0;
            }
        }
    }
}