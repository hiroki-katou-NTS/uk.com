module nts.uk.com.view.cps006.a.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        categoryList: KnockoutObservableArray<any> = ko.observableArray([]);
        isUseCategoryLst: KnockoutObservableArray<any> = ko.observableArray([]);
        categoryRootList: KnockoutObservableArray<any> = ko.observableArray([]);
        itemList: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCategoryId: KnockoutObservable<any> = ko.observable("");
        currentItemId: KnockoutObservable<any> = ko.observable("");
        colums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS006_6'), key: 'categoryName', width: 230 },
            { headerText: text('CPS006_7'), key: 'isAbolition', width: 50 }
        ]);
        itemColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS006_16'), key: 'itemName', width: 250 },
            { headerText: text('CPS006_17'), key: 'isAbolition', width: 50 }
        ]);
        currentCategory: KnockoutObservable<CategoryInfoDetail> = ko.observable((new CategoryInfoDetail({
            id: '', categoryNameDefault: '',
            categoryName: '', categoryType: 4, isAbolition: "", itemList: []
        })));
        // nếu sử dụng thì bằng true và ngược lại
        isAbolished: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self.start(undefined);
            self.currentCategory().id.subscribe(function(value) {
                self.getDetailCategory(value);
            });
            self.isAbolished.subscribe(function(value) {
                if (value) {
                    $("#category_grid").igGrid("option", "dataSource", self.categoryList());
                } else {
                    let category = _.find(self.isUseCategoryLst(), x => { return x.id == self.currentCategory().id() });
                    if (category === undefined) {
                        self.currentCategory().id(self.isUseCategoryLst()[0].id);
                    }
                    $("#category_grid").igGrid("option", "dataSource", self.isUseCategoryLst());
                }

            });

        }

        getDetailCategory(id: string) {
            let self = this;
            self.itemList.removeAll();
            service.getAllPerInfoItemDefByCtgId(id).done(function(data: Array<any>) {
                if (data.length > 0) {
                    self.itemList(_.map(data, x => new ItemInfo({
                        id: x.id,
                        perInfoCtgId: x.perInfoCtgId,
                        itemCode: x.itemCode,
                        itemName: x.itemName,
                        isAbolition: x.isAbolition == 1 ? "<i  style=\"margin-left: 10px\" class=\"icon icon-close\"></i>" : ""
                    })));
                };
            });


            let category = _.find(self.categoryList(), function(obj: any) { return obj.id === id });
            let categoryRoot = _.find(self.categoryRootList(), function(obj: any) {
                return obj.categoryCode === category.categoryCode
            });
            self.currentCategory().setData({
                id: id, categoryNameDefault: categoryRoot.categoryName, categoryName: category.categoryName,
                categoryType: category.categoryType, isAbolition: category.isAbolition, itemList: self.itemList()
            });
            self.currentCategory.valueHasMutated();

        }

        start(id: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            self.categoryList.removeAll();
            self.categoryRootList.removeAll();
            self.isUseCategoryLst.removeAll();

            service.getAllPerInfoCtgByRoot().done(function(data: Array<any>) {
                if (data.length > 0) {
                    self.categoryRootList(_.map(data, x => new CategoryInfo({
                        id: x.id,
                        categoryCode: x.categoryCode,
                        categoryName: x.categoryName,
                        categoryType: x.categoryType,
                        isAbolition: ""
                    })));
                }
            });
            service.getAllCategory().done(function(data: Array<any>) {
                if (data.length > 0) {
                    self.categoryList(_.map(data, x => new CategoryInfo({
                        id: x.id,
                        categoryCode: x.categoryCode,
                        categoryName: x.categoryName,
                        categoryType: x.categoryType,
                        isAbolition: x.isAbolition == 1 ? "<i  style=\"margin-left: 10px\" class=\"icon icon-close\"></i>" : ""
                    })));

                    self.isUseCategoryLst(_.map(_.filter(data, x => { return x.isAbolition == 0 }), x => new CategoryInfo({
                        id: x.id,
                        categoryCode: x.categoryCode,
                        categoryName: x.categoryName,
                        categoryType: x.categoryType,
                        isAbolition: ""
                    })));
                    if (id === undefined) {
                        self.currentCategory().id(self.categoryList()[0].id);
                    } else {
                        self.currentCategory().id(id);
                    }

                }
                dfd.resolve();
            });

            return dfd.promise();
        }

        openBModal() {

            let self = this;
            setShared('categoryInfo', self.currentCategory());
            setShared('currentItemId', self.currentItemId());
            block.invisible();
            nts.uk.ui.windows.sub.modal('/view/cps/006/b/index.xhtml', { title: '' }).onClosed(function(): any {
                self.start(undefined).done(() => {
                    block.clear();
                });
            });
        }

        openCDL022Modal() {
            let self = this,
                cats = _.map(ko.toJS(self.categoryList), (x: any) => { return { id: x.id, name: x.categoryName }; });

            setShared('CDL020_PARAMS', cats);
            nts.uk.ui.windows.sub.modal('/view/cdl/022/a/index.xhtml', { title: '' }).onClosed(function(): any {
                self.categoryList(getShared('CDL020_VALUES'));
                 $("#category_grid").igGrid("option", "dataSource", self.categoryList());
            });
        }

        registerCategoryInfo() {
            let self = this,
                cat = ko.toJS(self.currentCategory),
                command = {
                    id: cat.id,
                    categoryName: cat.categoryName,
                    isAbolition: cat.isAbolition
                    
                };
            
            service.update(command).done(function(data) {
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    self.start(command.id);
                });
            })

        }


    }
    export interface ICategoryInfo {
        id: string;
        categoryName: string;
        categoryCode: string;
        categoryType: number;
        isAbolition: string;
    }

    export class CategoryInfo {
        id: string;
        categoryCode: string;
        categoryName: string;
        categoryType: number;
        isAbolition: string;
        constructor(params: ICategoryInfo) {
            this.id = params.id;
            this.categoryName = params.categoryName;
            this.categoryCode = params.categoryCode;
            this.categoryType = params.categoryType;
            this.isAbolition = params.isAbolition;
        }

    }

    export interface IItemInfo {
        id: string;
        perInfoCtgId: string;
        itemCode: string;
        itemName: string;
        isAbolition: string;
    }

    export class ItemInfo {
        id: string;
        perInfoCtgId: string;
        itemCode: string;
        itemName: string;
        isAbolition: string;
        constructor(params: IItemInfo) {
            this.id = params.id;
            this.perInfoCtgId = params.perInfoCtgId;
            this.itemCode = params.itemCode;
            this.itemName = params.itemName;
            this.isAbolition = params.isAbolition;
        }
    }

    export interface ICategoryInfoDetail {
        id: string;
        categoryNameDefault: string;
        categoryName: string;
        categoryType: number;
        isAbolition: string;
        itemList?: Array<ItemInfo>;
    }

    export class CategoryInfoDetail {
        id: KnockoutObservable<string>;
        categoryNameDefault: string;
        categoryName: KnockoutObservable<string>;
        categoryType: number;
        isAbolition: KnockoutObservable<boolean>;
        constructor(params: ICategoryInfoDetail) {
            this.id = ko.observable(params.id);
            this.categoryNameDefault = params.categoryNameDefault;
            this.categoryName = ko.observable(params.categoryName);
            this.categoryType = params.categoryType;
            this.isAbolition = ko.observable(false);
        }

        setData(params: ICategoryInfoDetail) {
            this.id(params.id);
            this.categoryNameDefault = params.categoryNameDefault;
            this.categoryName(params.categoryName);
            this.categoryType = params.categoryType;
            this.isAbolition(params.isAbolition != '' ? true : false);
        }
    }




}