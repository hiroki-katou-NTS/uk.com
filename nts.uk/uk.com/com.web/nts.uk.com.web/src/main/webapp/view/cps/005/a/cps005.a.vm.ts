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
            currentData: KnockoutObservable<DataModel>

            constructor() {
                let self = this,
                    dataModel = new DataModel(null);
                self.currentData = ko.observable(dataModel);
                
                dataModel.perInfoCategorySelectCode.subscribe(newCategoryCode => {
                    if(textUK.isNullOrEmpty(newCategoryCode)) return;
                    self.currentData().currentCategorySelected(_.find(self.currentData().perInfoCategoryList(), item => {return item.categoryCode == newCategoryCode}));
                });
                
                dataModel.historyClassificationSelected.subscribe(newHisClassification => {
                     if(textUK.isNullOrEmpty(newHisClassification)) return;
                     if(newHisClassification == 1){
                         self.currentData().historyTypesDisplay(true);
                         self.currentData().singleMultipleTypeDisplay(false);
                         return;
                     }
                     self.currentData().historyTypesDisplay(false);
                     self.currentData().singleMultipleTypeDisplay(true);
                });
                
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
             new PerInfoCategoryModel({ categoryCode: "C01", categoryName: "A1" }),
             new PerInfoCategoryModel({ categoryCode: "C02", categoryName: "A2" }),
             new PerInfoCategoryModel({ categoryCode: "C03", categoryName: "A3" }),
             new PerInfoCategoryModel({ categoryCode: "C04", categoryName: "A4" })
        ]);
        perInfoCategorySelectCode: KnockoutObservable<string> = ko.observable("C01");
        currentCategorySelected: KnockoutObservable<PerInfoCategoryModel> = ko.observable(this.perInfoCategoryList()[0]);
        
        historyClassification: Array<any> = [
            { code: 1, name: nts.uk.resource.getText("CPS005_53")},
            { code: 2, name: nts.uk.resource.getText("CPS005_54")},
        ];
        historyClassificationSelected: KnockoutObservable<number> = ko.observable(1);
        //<!-- mapping CategoryType enum value = 3 or 4 or 5 . But using enum HistoryType to display -->
        historyTypes:  Array<any> = [
            { value: 1, localizedName: "連続"},
            { value: 2, localizedName: "非連続"},
            { value: 3, localizedName: "重複"},
        ];
        historyTypesSelected: KnockoutObservable<number> = ko.observable(1);
        
        //mapping CategoryType enum value = 1 or 2. Theo thiết kế không lấy từ enum CategoryType
        singleMultipleType:  Array<any> = [
            { value: 1, name: nts.uk.resource.getText("CPS005_55")},
            { value: 2, name: nts.uk.resource.getText("CPS005_56")},
        ];
        singleMultipleTypeSelected: KnockoutObservable<number> = ko.observable(1);
        perInfoItemDefinitionList: KnockoutObservableArray<string> = ko.observableArray(["A", "B", "C", "D"]);
        //all visiable
        historyTypesDisplay: KnockoutObservable<boolean> = ko.observable(true);
        singleMultipleTypeDisplay: KnockoutObservable<boolean> = ko.observable(false);
        //all enable
        
        constructor(data: IData) {
            if (!data) return;
            this.perInfoCategoryList = ko.observableArray(_.map(data.categoryList, item => { return new PerInfoCategoryModel(item) }));
        }
    }


    export class PerInfoCategoryModel {
        categoryCode: string = "";
        categoryName: string =  "";
        categoryCodeKnockout: KnockoutObservable<string> =  ko.observable("");
        categoryNameKnockout: KnockoutObservable<string> =  ko.observable("");
        constructor(data: IPersonInfoCategory) {
            this.categoryCode = data.categoryCode || "";
            this.categoryName = data.categoryName || "";
            this.categoryCodeKnockout(data.categoryCode || "");
            this.categoryNameKnockout(data.categoryName || "");
        }
    }

    interface IData {
        categoryList: Array<IPersonInfoCategory>;
    }

    interface IPersonInfoCategory {
        categoryCode: string;
        categoryName: string;
    }
}

