module qmm020.d.viewmodel {
    export class ScreenModel {
        listTreeColumns: Array<any> = [];

        model: KnockoutObservable<ItemModel> = ko.observable(undefined);
        dirty: nts.uk.ui.DirtyChecker = new nts.uk.ui.DirtyChecker(this.model);


        constructor() {
            var self = this;

            self.listTreeColumns = [{ headerText: '', key: 'code', dataType: 'string', hidden: true },
                { headerText: 'コード/名称', key: 'name', dataType: 'string', hidden: false, width: '450px', template: '<span>${code} ${name}</span>' },
                { headerText: '給与明細書', key: 'bonus', dataType: 'object', hidden: false, width: '250px', template: '<button class="d-btn-001" onclick="__viewContext.viewModel.viewmodelD.openMDialog()">選択</button><span>${bonus.code} ${bonus.name}</span>' },
                { headerText: '賞与明細書', key: 'payment', dataType: 'object', hidden: false, width: '250px', template: '<button class="d-btn-002" onclick="__viewContext.viewModel.viewmodelD.openMDialog()">選択</button><span>${payment.code} ${payment.name}</span>' }];

            self.start();
        }

        start() {
            let self = this;

            self.model(new ItemModel({ ItemTrees: [], ItemLists: [] }));

            // replace below code by service            
            self.model().ItemLists([
                new ItemList({ historyId: '1', startDate: 201604, endDate: 999912 }),
                new ItemList({ historyId: '2', startDate: 201504, endDate: 201603 }),
                new ItemList({ historyId: '3', startDate: 201404, endDate: 201503 })
            ]);
            self.model().ItemListSelected('1');

            self.model().ItemTrees([
                new ItemTree({
                    code: '001', name: 'Name 0001',
                    bonusDetailCode: '01',
                    bonusDetailName: 'Bonus 01',
                    paymentDetailCode: '10',
                    paymentDetailName: 'Payment 10',
                    childs: [{
                        code: '001001', name: 'Child 001',
                        bonusDetailCode: '01',
                        bonusDetailName: 'Bonus 01',
                        paymentDetailCode: '10',
                        paymentDetailName: 'Payment 10'
                    },
                        {
                            code: '001002', name: 'Child 002',
                            bonusDetailCode: '01',
                            bonusDetailName: 'Bonus 01',
                            paymentDetailCode: '10',
                            paymentDetailName: 'Payment 10'
                        }]
                }),
                new ItemTree({
                    code: '002', name: 'Name 0002',
                    bonusDetailCode: '01',
                    bonusDetailName: 'Bonus 01',
                    paymentDetailCode: '10',
                    paymentDetailName: 'Payment 10'
                }),
                new ItemTree({
                    code: '003', name: 'Name 0003',
                    bonusDetailCode: '01',
                    bonusDetailName: 'Bonus 01',
                    paymentDetailCode: '10',
                    paymentDetailName: 'Payment 10'
                })
            ]);

            self.model().ItemTreeSelected('002');

            // reset dirty check
            self.dirty.reset();
        }

        openJDialog() {
            let self = this;
            nts.uk.ui.windows.setShared("J_DATA", { displayMode: 2, startYm: 201701, endYm: 201708 });
            nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { width: 485, height: 550, title: '履歴の追加', dialogClass: "no-close" })
                .onClosed(function() {
                    nts.uk.ui.windows.setShared("J_DATA", null);
                    let value = nts.uk.ui.windows.getShared('J_RETURN');
                    if (value) {
                        // set data into gridview
                        debugger;
                    }
                });
        }

        openKDialog() {
            let self = this;

            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { width: 485, height: 550, title: '履歴の編集', dialogClass: "no-close" });
        }

        openMDialog() {
            let self = this;
            let currentItemTree = self.model().currentItemTree();
            if (!!currentItemTree) {
                currentItemTree.dialog(true);
            }

            nts.uk.ui.windows.setShared('M_BASEYM', 201707);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                .onClosed(function() {
                    currentItemTree.dialog(false);
                    let value = nts.uk.ui.windows.getShared('M_RETURN');
                    // set data into gridview
                });
        }

        saveData() {
            let self = this;
            console.log('D');
        }
    }

    interface IItemModel {
        ItemTrees: Array<IItemTree>;
        ItemLists: Array<IItemList>;
    }

    class ItemModel {
        ItemTrees: KnockoutObservableArray<ItemTree> = ko.observableArray([]);
        ItemTreeSelected: KnockoutObservable<string> = ko.observable(undefined);
        ItemLists: KnockoutObservableArray<ItemList> = ko.observableArray([]);
        ItemListSelected: KnockoutObservable<string> = ko.observable(undefined);

        constructor(param: IItemModel) {
            let self = this;
            self.ItemTrees(param.ItemTrees.map((m) => { return new ItemTree(m); }));
            self.ItemLists(param.ItemLists.map((m) => { return new ItemList(m); }));
        }

        currentItemTree(): ItemTree {
            let self = this;
            return _.find(self.ItemTrees(), function(m: ItemTree) { return m.name == self.ItemTreeSelected(); });
        }

        currentItemList(): ItemList {
            let self = this;
            return _.find(self.ItemLists(), function(m: ItemList) { return m.historyId == self.ItemListSelected(); });
        }
    }

    interface IItemTree {
        code: string;
        name: string;
        bonusDetailCode?: string;
        bonusDetailName?: string;
        paymentDetailCode?: string;
        paymentDetailName?: string;
        childs?: Array<IItemTree>;
    }

    class ItemTree {
        code: string;
        name: string;

        bonusDetailCode: string;
        bonusDetailName: string;
        paymentDetailCode: string;
        paymentDetailName: string;
        childs: Array<ItemTree>;
        dialog: KnockoutObservable<boolean> = ko.observable(false);
        constructor(param: IItemTree) {
            this.code = param.code;
            this.name = param.name;

            this.bonusDetailCode = param.bonusDetailCode || '';
            this.bonusDetailName = param.bonusDetailName || '';

            this.paymentDetailCode = param.paymentDetailCode || '';
            this.paymentDetailName = param.paymentDetailName || '';
            this.childs = (param.childs || []).map((c) => { return new ItemTree(c); });
        }
    }


    interface IItemList {
        bonusDetailCode?: string;
        bonusDetailName?: string;
        endDate: number;
        historyId: string;
        paymentDetailCode?: string;
        paymentDetailName?: string;
        startDate: number;
    }

    // list view model
    class ItemList {
        bonusDetailCode: string;
        bonusDetailName: string;
        endDate: number;
        historyId: string;
        paymentDetailCode: string;
        paymentDetailName: string;
        startDate: number;
        text: string;
        isMaxDate: boolean = false;

        constructor(param: IItemList) {
            this.historyId = param.historyId;
            this.endDate = param.endDate;
            this.startDate = param.startDate;

            this.bonusDetailCode = param.bonusDetailCode || '';
            this.bonusDetailName = param.bonusDetailName || '';

            this.paymentDetailCode = param.paymentDetailCode || '';
            this.paymentDetailName = param.paymentDetailName || '';

            this.text = nts.uk.time.formatYearMonth(param.startDate) + " ~ " + nts.uk.time.formatYearMonth(param.endDate);
        }
    }
}