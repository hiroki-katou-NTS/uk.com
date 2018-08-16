module nts.uk.com.view.cmf003.c {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;
    export module viewmodel {
        export class ScreenModel {

            // category
            categoriesDefault: KnockoutObservableArray<ItemCategory>;
            categoriesSelected: KnockoutObservableArray<ItemCategory>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCateSelected: KnockoutObservableArray<ItemSystemType>;

            // systemType
            systemTypes: KnockoutObservableArray<number>;
            selectedCode: KnockoutObservable<string> = ko.observable(null);
            currentItem: KnockoutObservable<ItemSystemType>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            headerCodeCategories: string = getText("CMF003_65");
            headerNameCategories: string = getText("CMF003_66");

            systemtypeFromB: KnockoutObservable<ItemSystemType>;
            categoriesFromB: KnockoutObservable<any>;
            constructor() {

                var self = this;
                let categoriesFB = getShared('CMF003_B_CATEGORIES');
                let systemtypeFB = getShared('CMF003_B_SYSTEMTYPE');
                self.currentCateSelected = ko.observableArray([]);
                var systemIdSelected;
                self.systemTypes = ko.observableArray([]);
                service.getSysTypes().done(function(data: Array<any>) {
                    if (data && data.length) {
                        _.forOwn(data, function(index) {
                            self.systemTypes.push(new ItemSystemType(index.type + '', index.name));
                        });

                        if (systemtypeFB != undefined) { 
                            systemIdSelected = systemtypeFB.code; 
                        } else { 
                            systemIdSelected = self.systemTypes()[0].code; 
                        }
                        self.selectedCode(systemIdSelected);
                    } else {

                    }

                }).fail(function(error) {
                    alertError(error);

                }).always(() => {

                });

                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.categoriesDefault = ko.observableArray([]);
                self.selectedCode.subscribe(value => {
                    if (value && value.length > 0) {
                    self.currentItem = _.find(self.systemTypes(), a => a.code === value);
                        service.getConditionList(parseInt(self.selectedCode())).done(function(data: Array<any>) {
                            
                            data = _.sortBy(data, ["categoryId"]);
                            if (systemtypeFB != undefined) {
                                _.forOwn(categoriesFB, function(index) {
                                     _.remove(data, function (e) {
                                            return e.categoryId == index.categoryId;
                                        });
                                });
                                self.currentCateSelected(categoriesFB);
                           }
                            self.categoriesDefault(data);
                            $("#swap-list-grid1 tr:first-child").focus();
                            
                        }).fail(function(error) {
                            alertError(error);
                        }).always(() => {
                            _.defer(() => {
                                $("#grd_Condition tr:first-child").focus();
                            });
                        });
                    }
                });

                self.columns = ko.observableArray([
                    { headerText: self.headerCodeCategories, key: 'categoryId', width: 70 },
                    { headerText: self.headerNameCategories, key: 'categoryName', width: 250 }
                ]);


                self.categoriesSelected = self.currentCateSelected;

            }

            remove() {
                let self = this;
                self.categoriesDefault.shift();
            }

            closeUp() {
                close();
            }

            submit() {
                let self = this;
                if (self.currentCateSelected().length == 0) {
                    alertError({ messageId: "Msg_577" });
                } else {
                    setShared("CMF003_C_CATEGORIES", JSON.stringify(self.currentCateSelected()));
                    setShared("CMF003_C_SYSTEMTYPE", self.currentItem);
                    close();
                }
            }

        }
    }

    class ItemCategory {
        schelperSystem: number;
        categoryId: string;
        categoryName: string;
        possibilitySystem: number;
        storedProcedureSpecified: number;
        timeStore: number;
        otherCompanyCls: number;
        attendanceSystem: number;
        recoveryStorageRange: number;
        paymentAvailability: number;
        storageRangeSaved: number;
        constructor(schelperSystem: number, categoryId: string, categoryName: string, possibilitySystem: number,
            storedProcedureSpecified: number, timeStore: number, otherCompanyCls: number, attendanceSystem: number,
            recoveryStorageRange: number, paymentAvailability: number, storageRangeSaved: number) {
            this.schelperSystem = schelperSystem;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.possibilitySystem = possibilitySystem;
            this.storedProcedureSpecified = storedProcedureSpecified;
            this.timeStore = timeStore;
            this.otherCompanyCls = otherCompanyCls;
            this.attendanceSystem = attendanceSystem;
            this.recoveryStorageRange = recoveryStorageRange;
            this.paymentAvailability = paymentAvailability;
            this.storageRangeSaved = storageRangeSaved;
        }
    }

    class ItemSystemType {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }



}