module nts.uk.pr.view.qpp007.j {
    export module viewmodel {
        import TaxDivision = nts.uk.pr.view.qpp007.c.viewmodel.TaxDivision;

        export class ScreenModel {
            taxDivisionTab: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            aggregateItemTab: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedDivision: KnockoutObservable<string>;
            selectedAggregateItem: KnockoutObservable<string>;

            name: KnockoutObservable<string>;

            itemsSwap: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwap: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.name = ko.observable('');
                self.taxDivisionTab = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: TaxDivision.PAYMENT, title: '支給集計', content: '#tab-payment', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: TaxDivision.DEDUCTION, title: '控除集計', content: '#tab-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.aggregateItemTab = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: 'aggregate1', title: '集計項目1', content: '#aggregate-item-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate2', title: '集計項目2', content: '#aggregate-item-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate3', title: '集計項目3', content: '#aggregate-item-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate4', title: '集計項目4', content: '#aggregate-item-4', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate5', title: '集計項目5', content: '#aggregate-item-5', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate6', title: '集計項目6', content: '#aggregate-item-6', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate7', title: '集計項目7', content: '#aggregate-item-7', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate8', title: '集計項目8', content: '#aggregate-item-8', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate9', title: '集計項目9', content: '#aggregate-item-9', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'aggregate10', title: '集計項目10', content: '#aggregate-item-10', enable: ko.observable(true), visible: ko.observable(true) }

                ]);

                self.selectedDivision = ko.observable(TaxDivision.PAYMENT);
                self.selectedAggregateItem = ko.observable('aggregate1');

                this.itemsSwap = ko.observableArray<ItemModel>([]);

                var array = [];
                for (var i = 0; i < 20; i++) {
                    array.push(new ItemModel(i, '基本給'));
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
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}