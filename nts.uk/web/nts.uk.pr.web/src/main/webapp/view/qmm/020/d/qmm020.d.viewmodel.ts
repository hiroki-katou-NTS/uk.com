module qmm020.d.viewmodel {
    export class ScreenModel {
        
        listBoxItems: KnockoutObservableArray<ItemList> = ko.observableArray([]);
        listBoxItemSelected: KnockoutObservable<string> = ko.observable('0');

        listTreeItems: KnockoutObservableArray<ItemTree> = ko.observableArray([]);
        listTreeItemSelected: KnockoutObservable<string> = ko.observable('0');
        
        constructor() {
            var self = this;
            self.listBoxItems([
                new ItemList({ code: '1', name: '2016/04 ~ 9999/12' }),
                new ItemList({ code: '2', name: '2015/04 ~ 2016/03' }),
                new ItemList({ code: '3', name: '2014/04 ~ 2015/03' })
            ]);
            self.listBoxItemSelected('1');

            self.listTreeItems([
                new ItemTree({ code: '001', name: 'Name 0001', childs: [{ code: '001001', name: 'Child 001' }, { code: '001002', name: 'Child 002' }] }),
                new ItemTree({ code: '002', name: 'Name 0002' }),
                new ItemTree({ code: '003', name: 'Name 0003' })
            ]);
            self.listTreeItemSelected('002');
        }

        openJDialog() {
            alert('J');
        }
        openKDialog() {
            alert('K');
        }
        openMDialog() {
            alert('M');
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
            this.name = param.code + ' ' + param.name;

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