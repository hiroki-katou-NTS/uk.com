module nts.uk.com.view.cps005.a {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import service = nts.uk.com.view.cps005.a.service;
    import textUK = nts.uk.text;

    export module viewmodel {
        export class ScreenModel {
            currentData: KnockoutObservable<DataModel>;
            constructor() {
                let self = this,
                    dataModel = new DataModel(null);
                self.currentData = ko.observable(dataModel);
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            register() {

            }

            addUpdateData() {

            }

            updateData() {

            }
        }
    }

    export class DataModel {
        perInfoCategoryList: KnockoutObservableArray<PerInfoCategoryModel> = ko.observableArray([
            new PerInfoCategoryModel({ categoryCode: "C01", categoryName: "A1", fixedAtr: 0, categoryType: 1 }),
            new PerInfoCategoryModel({ categoryCode: "C02", categoryName: "A2", fixedAtr: 1, categoryType: 2, categoryTypeName: nts.uk.resource.getText("CPS005_56") }),
            new PerInfoCategoryModel({ categoryCode: "C03", categoryName: "A3", fixedAtr: 0, categoryType: 5 }),
            new PerInfoCategoryModel({ categoryCode: "C04", categoryName: "A4", fixedAtr: 1, categoryType: 4, categoryTypeName: "非連続" })
        ]);
        perInfoCategorySelectCode: KnockoutObservable<string> = ko.observable("C01");
        currentCategorySelected: KnockoutObservable<PerInfoCategoryModel> = ko.observable(this.perInfoCategoryList()[0]);

        historyClassification: Array<any> = [
            { code: 1, name: nts.uk.resource.getText("CPS005_53") },
            { code: 2, name: nts.uk.resource.getText("CPS005_54") },
        ];
        //<!-- mapping CategoryType enum value = 3 or 4 or 5 . But using enum HistoryType to display -->
        historyTypes: Array<any> = [
            { value: 1, localizedName: "連続" },
            { value: 2, localizedName: "非連続" },
            { value: 3, localizedName: "重複" },
        ];
        //mapping CategoryType enum value = 1 or 2. Theo thiết kế không lấy từ enum CategoryType
        singleMultipleType: Array<any> = [
            { value: 1, name: nts.uk.resource.getText("CPS005_55") },
            { value: 2, name: nts.uk.resource.getText("CPS005_56") },
        ];

        constructor(data: IData) {
            let self = this;
            if (!data) return;
            self.perInfoCategoryList = ko.observableArray(_.map(data.categoryList, item => { return new PerInfoCategoryModel(item) }));
            
            //subscribe select category code
            self.perInfoCategorySelectCode.subscribe(newCategoryCode => {
                let cateType;
                if (textUK.isNullOrEmpty(newCategoryCode)) return;
                self.currentCategorySelected(_.find(self.perInfoCategoryList(), item => { return item.categoryCode == newCategoryCode }));
                self.currentCategorySelected().fixedIsSelected(false);
                if (self.currentCategorySelected().fixedAtr == true) {
                    self.currentCategorySelected().fixedIsSelected(true);
                }
            });
        }
    }


    export class PerInfoCategoryModel {
        categoryCode: string = "";
        categoryName: string = "";
        categoryCodeKnockout: KnockoutObservable<string> = ko.observable("");
        categoryNameKnockout: KnockoutObservable<string> = ko.observable("");
        fixedAtr: boolean;
        historyClassificationFixed: string = "";// tính toán app
        categoryType: number = 1;
        categoryTypeName: string = "";
        historyClassificationSelected: KnockoutObservable<number> = ko.observable(1);
        historyTypesSelected: KnockoutObservable<number> = ko.observable(1);
        singleMultipleTypeSelected: KnockoutObservable<number> = ko.observable(1);
        //all visiable
        historyTypesDisplay: KnockoutObservable<boolean> = ko.observable(false);
        fixedIsSelected: KnockoutObservable<boolean> = ko.observable(false);
        itemNameList: KnockoutObservableArray<PerInfoItemModel> = ko.observableArray([]);
        constructor(data: IPersonInfoCategory) {
            let self = this;
            self.categoryCode = data.categoryCode || "";
            self.categoryName = data.categoryName || "";
            self.categoryCodeKnockout(data.categoryCode || "");
            self.categoryNameKnockout(data.categoryName || "");
            self.fixedAtr = data.fixedAtr == 1 ? true : false;
            for (let i = 1; i < 5; i++) {
                self.itemNameList.push(new PerInfoItemModel(self.categoryName + i));
            }
            self.historyClassificationFixed = (data.categoryType == 1 || data.categoryType == 2) ? nts.uk.resource.getText("CPS005_54") : nts.uk.resource.getText("CPS005_53");
            self.categoryType = data.categoryType;
            self.categoryTypeName = data.categoryTypeName || "";
            self.historyClassificationSelected((data.categoryType == 1 || data.categoryType == 2) ? 2 : 1);
            self.singleMultipleTypeSelected(data.categoryType || 1);
            if (self.historyClassificationSelected() == 1) {
                self.historyTypesSelected(data.categoryType - 2);
                self.singleMultipleTypeSelected(1);
                self.historyTypesDisplay(true);
            }
            self.fixedIsSelected(self.fixedAtr);
            //self.itemNameList(_.map(data.itemNameList, item => {return new PerInfoItemModel(item)}));
            //subscribe select history type (1: history, 2: not history)
            self.historyClassificationSelected.subscribe(newHisClassification => {
                if (textUK.isNullOrEmpty(newHisClassification)) return;
                self.historyTypesDisplay(false);
                if (newHisClassification == 1) {
                    self.historyTypesDisplay(true);
                }
            });
        }

    }

    export class PerInfoItemModel {
        itemName: string;
        constructor(itemName: string) {
            let self = this;
            self.itemName = itemName;
        }
    }

    interface IData {
        categoryList: Array<IPersonInfoCategory>;
    }

    interface IPersonInfoCategory {
        categoryCode: string;
        categoryName: string;
        fixedAtr: number;
        categoryType: number;
        categoryTypeName?: string;
        itemNameList?: Array<string>;
    }
}

