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
            isUpdate: boolean = false;
            constructor() {
                let self = this,
                    dataModel = new DataModel(null);
                self.currentData = ko.observable(dataModel);
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                new service.Service().getAllPerInfoCtg().done(function(data: IData) {
                    self.isUpdate = false;
                    if (data && data.categoryList && data.categoryList.length > 0) {
                        self.currentData().categoryList(_.map(data.categoryList, item => { return new PerInfoCtgModel(item) }));
                        self.isUpdate = true;
                        self.currentData().perInfoCtgSelectCode(data.categoryList[0].id);
                    } else {
                        self.register();
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }

            reloadData(newCtgName?: string) {
                let self = this,
                    dfd = $.Deferred();
                new service.Service().getAllPerInfoCtg().done(function(data: IData) {
                    self.isUpdate = false;
                    if (data && data.categoryList && data.categoryList.length > 0) {
                        self.currentData().categoryList(_.map(data.categoryList, item => { return new PerInfoCtgModel(item) }));
                        self.isUpdate = true;
                        if (newCtgName) {
                            let newCtg = _.find(data.categoryList, item => { return item.categoryName == newCtgName });
                            self.currentData().perInfoCtgSelectCode(newCtg ? newCtg.id : "");
                        }
                    } else {
                        self.register();
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }

            register() {
                let self = this;
                nts.uk.ui.errors.clearAll();
                self.currentData().perInfoCtgSelectCode("");
                self.currentData().currentCtgSelected(new PerInfoCtgModel(null));
                self.isUpdate = false;
                self.currentData().historyClassEnable(true);
                $("#category-name-control").focus();
            }

            addUpdateData() {
                let self = this;
                if (self.isUpdate) {
                    let updateCategory = new UpdatePerInfoCtgModel(self.currentData().currentCtgSelected());
                    new service.Service().updatePerInfoCtg(updateCategory).done(() => {
                        self.reloadData();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).fail(error => {
                        nts.uk.ui.dialog.alertError(error);
                    });
                } else {
                    let newCategory = new AddPerInfoCtgModel(self.currentData().currentCtgSelected());
                    new service.Service().addPerInfoCtg(newCategory).done(() => {
                        self.reloadData(newCategory.categoryName);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                            nts.uk.ui.dialog.confirm({ messageId: "Msg_213" }).ifYes(() => {
                                alert("Show dialog B");
                            }).ifNo(() => {
                                return;
                            })
                        });
                    }).fail(error => {
                        nts.uk.ui.dialog.alertError(error);
                    });
                }

            }

            openDialogB() {

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
        historyClassEnable: KnockoutObservable<boolean> = ko.observable(true);
        constructor(data: IData) {
            let self = this;
            if (data) {
                self.categoryList(_.map(data.categoryList, item => { return new PerInfoCtgModel(item) }));
                self.historyTypes = data.historyTypes ? data.historyTypes : [];
            }
            //subscribe select category code
            self.perInfoCtgSelectCode.subscribe(newId => {
                if (textUK.isNullOrEmpty(newId)) return;
                nts.uk.ui.errors.clearAll();
                new service.Service().getPerInfoCtgWithItemsName(newId).done(function(data: IPersonInfoCtg) {
                    self.currentCtgSelected(new PerInfoCtgModel(data));
                    self.historyClassEnable(false)
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

    export class AddPerInfoCtgModel {
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

    export class UpdatePerInfoCtgModel {
        id: string
        categoryName: string;
        categoryType: number;
        constructor(data: PerInfoCtgModel) {
            let self = this;
            self.id = data.id;
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

