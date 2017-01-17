module qpp008.c.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        allowEditCode: KnockoutObservable<boolean> = ko.observable(false);
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        /*SwapList*/
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columnsSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
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
        items2: KnockoutObservableArray<ItemModel>;
        //columns2: KnockoutObservableArray<any>;
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
            self.itemsSwap = ko.observableArray([]);
            /*TextEditer*/
            self.cInp002Code = ko.observable(false);
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel("test" + i, '基本給', "description"));
            }
            self.itemsSwap(array);

            self.columnsSwap = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 116 }
            ]);

            this.currentCodeListSwap = ko.observableArray([]);
            /*TabPanel*/
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-2');
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
                { headerText: '名称', prop: 'name', width: 115 },

            ]);
            //get event when hover on table by subcribe
            self.currentCode = ko.observable();
            self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
            self.currentCode.subscribe(function(codeChanged) {
                self.currentItem(ko.mapping.fromJS(self.getItem(codeChanged)));
                self.cInp002Code(false);
            });
           
            
            //gridList2
            self.items2 = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    this.items2.push(new ItemModel(code, code, code, code));
                }
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 },

            ]);
            this.currentCode2 = ko.observable();
            
        }
        refreshLayout(): void {
            let self = this;
            //self.allowEditCode(true);
            self.cInp002Code(true);
            self.currentItem(ko.mapping.fromJS(new ItemModel('', '', '','','')));
            //self.currentCode() = ko.observable();
           // self.currentItem=ko.observable(new ItemModel('', '', '','',''));
//            self.currentCode = ko.observable();
//            self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
            //elf.cInp002Code = ko.observable(false);
        }

        getItem(codeNew): ItemModel {
            let self = this;
            let currentItem: ItemModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });

            return currentItem;
        }

        addItem() {
            this.items.push(new ItemModel('999', '基本給', "description 1", "other1"));
            this.items2.push(new ItemModel('999', '基本給', "description 1", "other1"));
        }

        removeItem() {
            this.items.shift();
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getPaymentDateProcessingList().done(function(data) {
                // self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
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

