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

        currentCategory: KnockoutObservable<any> = ko.observable(new CategoryInfo({ categoryId: '2', categoryName: 'B', categoryCode: "001", categoryType: 3, isUse: true }));

        constructor() {
            let self = this;
            self.start();
            self.currentCategoryId.subscribe(function(value) {
                service.getAllPerInfoItemDefByCtgId(value).done(function(data: Array<any>) {
                    if (data.length > 0) {
                        self.itemList(data);
                    };
                })

            })
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            service.getAllCategory().done(function(data) {
                if (data.length > 0) {
                    self.categoryList(data);
                    self.currentCategoryId(self.categoryList()[0].id);
                }
                dfd.resolve();
            });

            return dfd.promise();
        }

        OpenDModal() {

            let self = this;
            setShared('categoryInfo', self.currentCategory());
            block.invisible();
            nts.uk.ui.windows.sub.modal('/view/cas/001/d/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }

    }
    export interface ICategoryInfo {
        categoryId: string;
        categoryName: string;
        categoryCode: string;
        categoryType: number;
        isUse: boolean;
    }

    export class CategoryInfo {
        categoryId: string;
        categoryName: string;
        categoryCode: string;
        categoryType: number;
        isUse: boolean;
        constructor(params: ICategoryInfo) {
            this.categoryId = params.categoryId;
            this.categoryName = params.categoryName;
            this.categoryCode = params.categoryCode;
            this.categoryType = params.categoryType;
            this.isUse = params.isUse;
        }


    }





}