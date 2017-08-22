module nts.uk.com.view.cps005.a {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
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
                new service.Service().getAllPerInfoCtg().done(function(data: IData) {
                    self.currentData(new DataModel(data));
                    if (data && data.categoryList && data.categoryList.length > 0) {
                        self.currentData().perInfoCtgSelectCode(data.categoryList[0].id);
                    }
                    dfd.resolve();
                }).fail(error => { });
                return dfd.promise();
            }

            register() {
                let self = this;
                self.currentData().perInfoCtgSelectCode("");
                self.currentData().currentCtgSelected(new PerInfoCtgModel(null));
            }

            addUpdateData() {
                let self = this,
                    newCategory = new AddUpdatePerInfoCtgModel(self.currentData().currentCtgSelected());
                new service.Service().addPerInfoCtg(newCategory).done().fail(error => {
                    nts.uk.ui.dialog.alertError({ messageId: error });
                });
            }

            updateData() {

            }
        }
    }

    export class DataModel {
        categoryList: KnockoutObservableArray<PerInfoCtgModel> = ko.observableArray([]);
        perInfoCtgSelectCode: KnockoutObservable<string> = ko.observable("");
        currentCtgSelected: KnockoutObservable<PerInfoCtgModel> = ko.observable(new PerInfoCtgModel(null));

        historyClassification: Array<any> = [
            { code: 1, name: nts.uk.resource.getText("CPS005_53") },
            { code: 2, name: nts.uk.resource.getText("CPS005_54") },
        ];
        //<!-- mapping CategoryType enum value = 3 or 4 or 5 . But using enum HistoryType to display -->
        historyTypes: any = new Array<any>();
        //mapping CategoryType enum value = 1 or 2. Theo thiết kế không lấy từ enum CategoryType
        singleMultipleType: Array<any> = [
            { value: 1, name: nts.uk.resource.getText("CPS005_55") },
            { value: 2, name: nts.uk.resource.getText("CPS005_56") },
        ];

        constructor(data: IData) {
            let self = this;
            if (data) {
                self.categoryList(_.map(data.categoryList, item => { return new PerInfoCtgModel(item) }));
                self.historyTypes = data.historyTypes ? data.historyTypes : [];
            }
            //subscribe select category code
            self.perInfoCtgSelectCode.subscribe(newId => {
                if (textUK.isNullOrEmpty(newId)) return;
                new service.Service().getPerInfoCtgWithItemsName(newId).done(function(data: IPersonInfoCtg) {
                    self.currentCtgSelected(new PerInfoCtgModel(data));
                });
            });
        }
    }


    export class PerInfoCtgModel {
        id: string = "";
        categoryName: string = "";
        perInfoCtgName: KnockoutObservable<string> = ko.observable("");
        historyFixedName: string = "";
        categoryType: number = 1;
        categoryTypeName: string = "";
        historyClassSelected: KnockoutObservable<number> = ko.observable(1);
        // historyTypesSelected and singleMulTypeSelected == categoryType
        historyTypesSelected: KnockoutObservable<number> = ko.observable(1);
        singleMulTypeSelected: KnockoutObservable<number> = ko.observable(1);
        itemNameList: KnockoutObservableArray<PerInfoItemModel> = ko.observableArray([]);
        //all visiable
        historyTypesDisplay: KnockoutObservable<boolean> = ko.observable(true);
        fixedIsSelected: KnockoutObservable<boolean> = ko.observable(false);
        constructor(data: IPersonInfoCtg) {
            let self = this;
            if (data) {
                self.id = data.id || "";
                self.categoryName = data.categoryName || "";
                self.perInfoCtgName(data.categoryName || "");
                self.itemNameList(_.map(data.itemNameList, item => { return new PerInfoItemModel(item) }));
                self.historyFixedName = (data.categoryType == 1 || data.categoryType == 2) ? nts.uk.resource.getText("CPS005_54") : nts.uk.resource.getText("CPS005_53");
                self.categoryType = data.categoryType;
                switch (self.categoryType) {
                    case 1:
                        self.categoryTypeName = nts.uk.resource.getText("CPS005_55");
                        break;
                    case 2:
                        self.categoryTypeName = nts.uk.resource.getText("CPS005_56");
                        break;
                    case 3:
                        self.categoryTypeName = nts.uk.resource.getText("Enum_HistoryTypes_CONTINUOUS");
                        break;
                    case 4:
                        self.categoryTypeName = nts.uk.resource.getText("Enum_HistoryTypes_NO_DUPLICATE");
                        break;
                    case 5:
                        self.categoryTypeName = nts.uk.resource.getText("Enum_HistoryTypes_DUPLICATE");
                        break;
                }
                self.historyClassSelected((data.categoryType == 1 || data.categoryType == 2) ? 2 : 1);
                self.singleMulTypeSelected(data.categoryType || 1);
                self.historyTypesDisplay(false);
                if (self.historyClassSelected() == 1) {
                    self.historyTypesSelected(data.categoryType - 2);
                    self.singleMulTypeSelected(1);
                    self.historyTypesDisplay(true);
                }
                self.fixedIsSelected(data.isFixed == 1 ? true : false);
            }
            //subscribe select history type (1: history, 2: not history)
            self.historyClassSelected.subscribe(newHisClassification => {
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

    export class AddUpdatePerInfoCtgModel {
        categoryName: string;
        categoryType: number;
        constructor(data: PerInfoCtgModel) {
            let self = this;
            self.categoryName = data.perInfoCtgName();
            if (data.historyClassSelected() == 2) {
                self.categoryType = data.singleMulTypeSelected();
            } else {
                self.categoryType = data.historyTypesSelected() + 2;
            }
        }
    }

    interface IData {
        historyTypes: any;
        categoryList: Array<IPersonInfoCtg>;
    }

    interface IPersonInfoCtg {
        id: string;
        categoryName: string;
        isFixed?: number;
        categoryType?: number;
        itemNameList?: Array<string>;
    }
}

