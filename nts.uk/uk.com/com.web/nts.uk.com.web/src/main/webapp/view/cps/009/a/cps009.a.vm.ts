module nts.uk.com.view.cps009.a.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ViewModel {
        categoryList: KnockoutObservableArray<any> = ko.observableArray([]);
        itemList: KnockoutObservableArray<any> = ko.observableArray([]);
        initValueList: KnockoutObservableArray<any> = ko.observableArray([]);
        categoryId: KnockoutObservable<string> = ko.observable('');
        ctgColums: KnockoutObservableArray<any>;
        itemValueLst: KnockoutObservableArray<any>;
        selectionColumns: any;
        currentCategory: KnockoutObservable<CategoryInfoDetail>;
        comboItems: any;
        comboColumns: any;
        items = _(new Array(10)).map((x, i) => new GridItem(i + 1)).value();
        constructor() {

            let self = this;

            self.initValue();
            self.start();

            self.categoryId.subscribe(function(value: string) {

                self.currentCategory().setData({
                    categoryCode: value,
                    categoryName: value,
                    itemList: self.itemList()
                });

                self.currentCategory.valueHasMutated();
            });

        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            for (var i = 0; i < 10; i++) {
                self.categoryList.push(new CategoryInfo({ id: i.toString(), categoryName: 'A', categoryCode: "000" + i.toString() }));
                self.itemList.push(new ItemInfo({ id: i.toString(), itemCode: "000" + i.toString(), itemName: "B" }));
                self.initValueList.push(new InitValue({ id: i.toString(), itemName: "C", comboxValue: "1", value: "HHH" }));
            }
            self.categoryId(self.categoryList()[0].id);

            return dfd.promise();
        }

        initValue() {
            let self = this;

            self.ctgColums = ko.observableArray([
                { headerText: 'id', key: 'id', width: 100, hidden: true },
                { headerText: text('CPS009_10'), key: 'categoryCode', width: 80 },
                { headerText: text('CPS006_11'), key: 'categoryName', width: 160 }
            ]);

            self.itemValueLst = ko.observableArray(
                [new ItemModel('1', '基本給'),
                    new ItemModel('2', '役職手当'),
                    new ItemModel('3', '基本給2')]);

            self.selectionColumns = [{ prop: 'id', length: 4 },
                { prop: 'itemName', length: 8 }];

            self.currentCategory = ko.observable(new CategoryInfoDetail({
                categoryCode: '', categoryName: '', itemList: []
            }));

            self.comboItems = [new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給2')];

            self.comboColumns = [{ prop: 'code', length: 4 },
                { prop: 'name', length: 8 }];

        }

        // thiet lap item hang loat
        openBDialog() {

            let self = this;

            setShared('categoryInfo', self.currentCategory());
            block.invisible();
            modal('/view/cps/009/b/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });

        }

        // copy initVal
        openCDialog() {

            let self = this;

            setShared('categoryInfo', self.currentCategory());

            block.invisible();

            modal('/view/cps/009/c/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });

        }


        // new initVal
        openDDialog() {

            let self = this;

            setShared('categoryInfo', self.currentCategory());

            block.invisible();

            modal('/view/cps/009/d/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });

        }
    }
    
    export interface ICategoryInfo {
        id: string;
        categoryName: string;
        categoryCode: string;
    }

    export class CategoryInfo {
        id: string;
        categoryCode: string;
        categoryName: string;
        constructor(params: ICategoryInfo) {
            this.id = params.id;
            this.categoryName = params.categoryName;
            this.categoryCode = params.categoryCode;
        }

    }
    
    export interface IItemInfo {
        id: string;
        itemCode: string;
        itemName: string;
    }

    export class ItemInfo {
        id: string;
        itemCode: string;
        itemName: string;
        constructor(params: IItemInfo) {
            this.id = params.id;
            this.itemCode = params.itemCode;
            this.itemName = params.itemName;
        }
    }

    export interface IInitValue {
        id: string;
        itemName: string;
        comboxValue: string;
        value: string;
    }

    export class InitValue {
        id: string;
        itemName: string;
        comboxValue: string;
        value: string;
        constructor(params: IInitValue) {
            this.id = params.id;
            this.itemName = params.itemName;
            this.comboxValue = params.comboxValue;
            this.value = params.value;
        }
    }

    export interface ICategoryInfoDetail {
        categoryCode: string;
        categoryName: string;
        itemList?: Array<any>;
    }

    export class CategoryInfoDetail {
        categoryCode: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        currentItemId: KnockoutObservable<string> = ko.observable('');
        itemColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS009_15'), key: 'itemCode', width: 50 },
            { headerText: text('CPS009_16'), key: 'itemName', width: 200 }
        ]);
        constructor(params: ICategoryInfoDetail) {
            this.categoryCode = ko.observable(params.categoryCode);
            this.categoryName = ko.observable(params.categoryName);
            this.itemList = ko.observableArray(params.itemList);
        }

        setData(params: ICategoryInfoDetail) {
            this.categoryCode(params.categoryCode);
            this.categoryName(params.categoryName);
            this.itemList(params.itemList);
        }
    }

    class GridItem {
        id: number;
        flag: boolean;
        ruleCode: string;
        combo: string;
        text1: string;
        constructor(index: number) {
            this.id = index;
            this.flag = index % 2 == 0;
            this.ruleCode = String(index % 3 + 1);
            this.combo = String(index % 3 + 1);
            this.text1 = "TEXT";
        }
    }

    function makeIcon(value, row) {
        if (value == "true")
            return "●";
        return '';
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }


}