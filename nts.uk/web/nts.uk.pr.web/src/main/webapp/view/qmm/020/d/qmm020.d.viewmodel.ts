module qmm020.d.viewmodel {
    export class ScreenModel {
        listBoxItems: KnockoutObservableArray<ItemList> = ko.observableArray([]);
        listBoxItemSelected: KnockoutObservable<string> = ko.observable('0');

        listTreeItems: KnockoutObservableArray<ItemTree> = ko.observableArray([]);
        listTreeItemSelected: KnockoutObservable<string> = ko.observable('0');
        listTreeColumns: Array<any> = [];
        dirty: nts.uk.ui.DirtyChecker = new nts.uk.ui.DirtyChecker(this.listTreeItems);

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

            // replace below code by service            
            self.listBoxItems([
                new ItemList({ code: '1', name: '2016/04 ~ 9999/12' }),
                new ItemList({ code: '2', name: '2015/04 ~ 2016/03' }),
                new ItemList({ code: '3', name: '2014/04 ~ 2015/03' })
            ]);
            self.listBoxItemSelected('1');

            self.listTreeItems([
                new ItemTree({
                    code: '001', name: 'Name 0001',
                    bonus: new ItemList({ code: '01', name: 'Bonus 01' }),
                    payment: new ItemList({ code: '50', name: 'Payment 50' }),
                    childs: [{
                        code: '001001', name: 'Child 001',
                        bonus: new ItemList({ code: '01', name: 'Bonus 01' }),
                        payment: new ItemList({ code: '50', name: 'Payment 50' })
                    },
                        {
                            code: '001002', name: 'Child 002',
                            bonus: new ItemList({ code: '01', name: 'Bonus 01' }),
                            payment: new ItemList({ code: '50', name: 'Payment 50' })
                        }]
                }),
                new ItemTree({
                    code: '002', name: 'Name 0002',
                    bonus: new ItemList({ code: '01', name: 'Bonus Name 01' }),
                    payment: new ItemList({ code: '50', name: 'Payment Name 50' })
                }),
                new ItemTree({
                    code: '003', name: 'Name 0003',
                    bonus: new ItemList({ code: '01', name: 'Bonus Name 01' }),
                    payment: new ItemList({ code: '50', name: 'Payment Name 50' })
                })
            ]);
            self.listTreeItemSelected('002');

            // reset dirty check
            self.dirty.reset();
        }

        openJDialog() {
            alert('J');
        }

        openKDialog() {
            alert('K');
        }

        openMDialog() {
            let self = this;

            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" });
        }
    }

    interface IItemTree {
        code: string;
        name: string;
        bonus?: IItemList;
        payment?: IItemList;
        childs?: Array<IItemTree>;
    }

    class ItemTree {
        code: string;
        name: string;

        bonus: ItemList;
        payment: ItemList;
        childs: Array<ItemTree>;
        constructor(param: IItemTree) {
            this.code = param.code;
            this.name = param.name;
            this.bonus = param.bonus;
            this.payment = param.payment;
            this.childs = (param.childs || []).map((c) => { return new ItemTree(c); });
        }
    }

    interface IItemList {
        code: string;
        name: string;
    }

    class ItemList {
        code: string;
        name: string;
        constructor(param: IItemList) {
            this.code = param.code;
            this.name = param.name;
        }
    }
}