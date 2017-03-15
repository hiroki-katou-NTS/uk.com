module qpp008.c.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        allowEditCode: KnockoutObservable<boolean> = ko.observable(false);
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        /*SwapList*/
        //swapList1
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columnsSwap: KnockoutObservableArray<any>;
        currentCodeListSwap: KnockoutObservableArray<ItemModel>;
        //swapList2
        itemsSwap2: KnockoutObservableArray<ItemModel>;
        columnsSwap2: KnockoutObservableArray<any>
        currentCodeListSwap2: KnockoutObservableArray<ItemModel>;
        //swapList3
        itemsSwap3: KnockoutObservableArray<ItemModel>;
        columnsSwap3: KnockoutObservableArray<any>
        currentCodeListSwap3: KnockoutObservableArray<ItemModel>;
        /*TabPanel*/
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        /*GridList*/
        //gridList1
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        //        currentItem: any;
        nameValue: KnockoutObservable<string>;
        codeValue: KnockoutObservable<any>;
        //gridList2
        items2: KnockoutComputed<Array<ItemModel>>;
        columns2: KnockoutObservableArray<any>;
        currentCode2: KnockoutObservable<any>;
        currentItem2: KnockoutObservable<any>;
        /*TextEditer*/
        cInp002Code: KnockoutObservable<boolean>;
        currentItem: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            /*SwapList*/
            //swapList1
            self.itemsSwap = ko.observableArray([]);
            var array = [];
            var array1 = [];
            var array2 = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel("testz" + i, '基本給', "description"));
            }
            self.itemsSwap(array);

            self.columnsSwap = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 }
            ]);

            self.currentCodeListSwap = ko.observableArray([]);
            //swapList2
            self.itemsSwap2 = ko.observableArray([]);
            for (var i = 0; i < 10000; i++) {
                array1.push(new ItemModel("testx" + i, '基本給', "description"));
            }
            self.itemsSwap2(array1);

            self.columnsSwap2 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 }
            ]);
            self.currentCodeListSwap2 = ko.observableArray([]);
            //swapList3
            self.itemsSwap3 = ko.observableArray([]);
            for (var i = 0; i < 10000; i++) {
                array2.push(new ItemModel("testy" + i, '基本給', "description"));
            }
            self.itemsSwap3(array2);
            self.columnsSwap3 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 }
            ]);
            self.currentCodeListSwap3 = ko.observableArray([]);
            /*TextEditer*/
            self.cInp002Code = ko.observable(false);

            /*TabPanel*/
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '支給', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '控除', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: '記事', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            /*gridList*/
            //gridList1
            self.items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    this.items.push(new ItemModel(code, code, code, code));
                }
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 },

            ]);
            //get event when hover on table by subcribe
            self.currentCode = ko.observable();
            self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
            self.currentCode.subscribe(function(codeChanged) {
                self.currentItem(ko.mapping.fromJS(self.getItem(codeChanged)));
                self.cInp002Code(false);
            });
            self.items2 = ko.computed(function(){
                var x = [];
                x = x.concat(self.currentCodeListSwap());
                x = x.concat(self.currentCodeListSwap2());
                x = x.concat(self.currentCodeListSwap3());
                return x;
            }, self).extend({ deferred: true });
//           
            self.columns2 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 },

            ]);
            self.currentCode2 = ko.observable();

        }
        refreshLayout(): void {
            let self = this;
            //self.allowEditCode(true);
            self.cInp002Code(true);
            self.currentItem(ko.mapping.fromJS(new ItemModel('', '', '', '', '')));
           
        }
        insertData() {
            let self = this;
            let newData = ko.toJS(self.currentItem());
            if (self.cInp002Code()) {
                self.items.push(newData);
            } else {
                let updateIndex = _.findIndex(self.items(), function(item) { return item.code == newData.code; });
                self.items.splice(updateIndex, 1, newData);
            }
        }
        deleteData() {
            let self = this;
            let newDelData = ko.toJS(self.currentItem());
            let deleData = _.findIndex(self.items(), function(item) { return item.code == newDelData.code; });
            self.items.splice(deleData, 1);
        }

        getItem(codeNew): ItemModel {
            let self = this;
            let currentItem: ItemModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });

            return currentItem;
        }
        findDuplicateSwaps(codeNew) {
            let self = this;
            let value;
            let checkItemSwap = _.find(self.items2(), function(item) {
                return item.code == codeNew
            });
            if (checkItemSwap == undefined) {
                value = false;
            }
            else {
                value = true;
            }
            return value;
        }
        
        addItem() {
            this.items.push(new ItemModel('999', '基本給', "description 1", "other1"));
//            this.items2().push(new ItemModel('999', '基本給', "description 1", "other1"));
        }

        removeItem() {
            this.items.shift();
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
             dfd.resolve();
//            service.getPaymentDateProcessingList().done(function(data) {
//                // self.paymentDateProcessingList(data);
//                dfd.resolve();
//            }).fail(function(res) {
//
//            });
            return dfd.promise();
        }
        closeDialog(): any{
            nts.uk.ui.windows.close();   
        }
    }
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
    }
}

