module nts.uk.com.view.cmf003.c {
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = cmf005.share.model;
    import alertError = nts.uk.ui.dialog.alertError;
    export module viewmodel {
        export class ScreenModel {

            // swapList
            itemsSwap: KnockoutObservableArray<model.ItemCategory> = ko.observableArray([]);
            itemsSwapLeft: KnockoutObservableArray<model.ItemCategory> = ko.observableArray([]);
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwap: KnockoutObservableArray<any>;

            // comboBox
            itemList: KnockoutObservableArray<ItemModelCombox>;
            selectedCode: KnockoutObservable<string>;
            currentItem: KnockoutObservable<ItemModelCombox>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            constructor() {
                var self = this;

                self.itemList = ko.observableArray<any>;
                service.getSysTypes().done(function(data: Array<any>) {
                    if (data && data.length) {
                        _.forOwn(data, function(index) {
                            self.itemList.push(new ItemModelCombox(index.type, index.name));
                        });

                    } else {

                    }

                }).fail(function(error) {
                    alertError(error);

                }).always(() => {

                });

                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);

                self.selectedCode = ko.observableArray<any>;
               
                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'categoryId', width: 70 },
                    { headerText: '名称', key: 'categoryName', width: 200 }
                ]);
                var array = [];
                for (var i = 1; i < 10; i++) {
                    array.push(new model.ItemCategory('0000' + i, 'cccccc' + i, '1', '2'));
                }
                self.itemsSwap(array);
                console.log(self.itemsSwap().length);
                self.currentCodeListSwap = ko.observableArray([]);
                self.itemsSwapLeft = self.currentCodeListSwap;
            }

            remove() {
                self.itemsSwap.shift();
            }

            closePopup() {
                close();
            }

            submit() {
                let self = this;
                if (self.currentCodeListSwap().length == 0) {
                    alertError({ messageId: "Msg_471" });
                } else {
                    setShared("CMF005COutput", { listCategoryChose: self.currentCodeListSwap(), systemTypeId: self.currentItem });
                    close();
                }
            }

        }
    }

    class ItemModel {
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

        constructor(categoryId: string, categoryName: string, timeStore: number, storageRangeSaved: number) {

            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.timeStore = timeStore;
            this.storageRangeSaved = storageRangeSaved;
        }
    }

    class ItemModelCombox {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }



}