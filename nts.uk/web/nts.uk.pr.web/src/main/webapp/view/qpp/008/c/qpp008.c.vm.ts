module qpp008.c.viewmodel {
    export class ScreenModel {

        /*GridList*/
        //C_LST_001
        items: KnockoutObservableArray<service.model.ComparingFormHeader>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<service.model.ComparingFormHeader>;

        //gridList2
        items2: KnockoutComputed<Array<ItemModel>>;
        columns2: KnockoutObservableArray<any>;
        currentCode2: KnockoutObservable<any>;
        currentItem2: KnockoutObservable<any>;


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

        nameValue: KnockoutObservable<string>;
        codeValue: KnockoutObservable<any>;

        /*TextEditer*/
        cInp002Code: KnockoutObservable<boolean>;
        allowEditCode: KnockoutObservable<boolean> = ko.observable(false);
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self._initFormHeader();
            self._initSwap();
            self._initFormDetail();

            self.cInp002Code = ko.observable(false);
            self.currentCode.subscribe(function(codeChanged) {
                if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                    self.currentItem(self.mappingFromJS(self.getItem(codeChanged)));
                    self.cInp002Code(false);
                    self.allowEditCode(true);
                }
            });

        }

        _initFormHeader(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'formCode', width: 60 },
                { headerText: '名称', prop: 'formName', width: 120 },
            ]);
            self.currentItem = ko.observable(new service.model.ComparingFormHeader('', ''));
            self.currentCode = ko.observable();
        }

        _initSwap(): void {
            let self = this;
            self.itemsSwap = ko.observableArray([]);
            let array = [];
            let array1 = [];
            let array2 = [];
            for (let i = 0; i < 10000; i++) {
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
            for (let i = 0; i < 10000; i++) {
                array2.push(new ItemModel("testy" + i, '基本給', "description"));
            }
            self.itemsSwap3(array2);
            self.columnsSwap3 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 }
            ]);
            self.currentCodeListSwap3 = ko.observableArray([]);

            /*TabPanel*/
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '支給', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '控除', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: '記事', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }

        _initFormDetail(): void {
            let self = this;
            self.items2 = ko.computed(function() {
                let x = [];
                x = x.concat(self.currentCodeListSwap());
                x = x.concat(self.currentCodeListSwap2());
                x = x.concat(self.currentCodeListSwap3());
                return x;
            }, self).extend({ deferred: true });

            self.columns2 = ko.observableArray([
                { headerText: '区分', prop: 'code', width: 60 },
                { headerText: 'コード', prop: 'code', width: 60 },
                { headerText: '名称', prop: 'name', width: 120 },

            ]);
            self.currentCode2 = ko.observable();
        }

        mappingFromJS(data) {
            return ko.mapping.fromJS(data);
        }

        refreshLayout(): void {
            let self = this;
            self.currentItem(self.mappingFromJS(new service.model.ComparingFormHeader('', '')));
            self.currentCode();
            self.allowEditCode(true);
            self.cInp002Code(true);

        }

        createButtonClick(): void {
            let self = this;
            self.refreshLayout();
        }

        insertData() {
            let self = this;
            let newData = ko.toJS(self.currentItem());
            if (self.cInp002Code()) {
                self.items.push(newData);
            } else {
                let updateIndex = _.findIndex(self.items(), function(item) { return item.formCode == newData.code; });
                self.items.splice(updateIndex, 1, newData);
            }
        }
        
        deleteData() {
            let self = this;
            let newDelData = ko.toJS(self.currentItem());
            let deleData = _.findIndex(self.items(), function(item) { return item.formCode == newDelData.code; });
            self.items.splice(deleData, 1);
        }

        getItem(codeNew): service.model.ComparingFormHeader {
            let self = this;
            let currentItem: service.model.ComparingFormHeader = _.find(self.items(), function(item) {
                return item.formCode === codeNew;
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
            this.items.push(new service.model.ComparingFormHeader('9', '基本給'));
        }

        removeItem() {
            this.items.shift();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getListComparingFormHeader().done(function(data: Array<service.model.ComparingFormHeader>) {
                self.adDataToItemsList(data);
                self.currentItem(_.first(self.items()));
                self.currentCode(self.currentItem().formCode);
                dfd.resolve(data);
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

        adDataToItemsList(data: Array<service.model.ComparingFormHeader>): void {
            let self = this;
            self.items([]);
            _.forEach(data, function(value) {
                self.items.push(value);
            });

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

    export class InsertUpdateFormHeaderModel {
        formCode: string;
        formName: string;

        constructor(formCode: string, formName: string) {
            this.formCode = formCode;
            this.formName = formName;
        }
    }

    export class DeleteFormHeaderModel {
        formCode: string;
        
        constructor(formCode: string) {
            this.formCode = formCode;
        }
    }
}

