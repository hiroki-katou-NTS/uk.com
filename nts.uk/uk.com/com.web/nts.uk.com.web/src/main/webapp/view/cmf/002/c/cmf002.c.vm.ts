module nts.uk.com.view.cmf002.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        currentStandardOutputItem: KnockoutObservable<model.StandardOutputItem> = ko.observable(new model.StandardOutputItem(null, null, null, null, 0, null));
        selectedStandardOutputItemCode: KnockoutObservable<string> = ko.observable("");
        listStandardOutputItem: KnockoutObservableArray<model.StandardOutputItem> = ko.observableArray([]);
        itemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        itemType: KnockoutObservable<number> = ko.observable(0);
        conditionCode: KnockoutObservable<string> = ko.observable("");
        conditionName: KnockoutObservable<string>;
        categoryId: KnockoutObservable<number> = ko.observable(1);
        categoryName: KnockoutObservable<string>;

        // itemCode: KnockoutObservable<string>;
        // itemName: KnockoutObservable<string>;
        // formula: KnockoutObservable<string>;
        // itemType: KnockoutObservable<number>;

        selectedExOutputCateItemDatas: KnockoutObservableArray<string> = ko.observableArray([]);
        listExOutCateItemData: KnockoutObservableArray<model.ExternalOutputCategoryItemData> = ko.observableArray([]);

        selectedCategoryItems: KnockoutObservableArray<string> = ko.observableArray([]);
      //  listCategoryItem: KnockoutObservableArray<model.CategoryItem> = ko.observableArray([]);

        constructor() {
            let self = this;
            let params = getShared("CMF002bParams");
            let _rsList: Array<model.ItemModel> = model.getItemTypes();
            self.itemTypes(_rsList);

            self.conditionName = ko.observable("Condition Name"); //params.conditionName + "　" + params.conditionCode
            self.categoryName = ko.observable("Category Name"); // params.categoryName
            // self.itemCode = ko.observable("Item Code");
            // self.itemName = ko.observable("Item Name");
            // self.itemType = ko.observable(0);
            //self.formula = ko.observable("A1+B2+C3");
            //self.selectedStandardOutputItemCode = ko.observable("123");
           // self.selectedExternalOutputCategoryItemData = ko.observable("123");

            self.selectedStandardOutputItemCode.subscribe(code => {
                if (code) {
//                    block.invisible();
//                    service.findByCode(params.conditionCode, self.selectedStandardOutputItemCode()).done(data => {
//                        if (data) {
//                            self.isNewMode(false);
//                            let item = new model.StandardOutputItem(data.outputItemCode, data.outputItemName, data.conditionSettingCode, "", data.itemType);
//                            self.currentStandardOutputItem(item);
//                            // self.itemCode(data.outputItemCode);
//                            //  self.itemName(data.outputItemName);
//                            //  self.itemType(data.itemType);
//                            self.listCategoryItem(data.categoryItems);
//                        }
//                    }).fail(function(error) {
//                        alertError(error);
//                    }).always(() => {
//                        block.clear();
//                    });

                    self.isNewMode(false);
                    let stdOutItem = _.find(self.listStandardOutputItem(), x => { return x.outItemCd() == code; });
                    self.currentStandardOutputItem(stdOutItem);
                    self.itemType(stdOutItem.itemType());
                   // self.listCategoryItem(stdOutItem.categoryItems());
                } else {
                    self.settingNewMode();
                }
            });
            
            self.itemType.subscribe(code => {
                self.currentStandardOutputItem().itemType(code);
                service.getAllCategoryItem(self.categoryId(), code).done((categoryItems: Array<any>) => {
                    if (categoryItems && categoryItems.length) {
                        let rsCategoryItems: Array<model.ExternalOutputCategoryItemData> = _.map(categoryItems, x => {
                            return new model.ExternalOutputCategoryItemData(x.itemNo, x.itemName);
                        });
                        self.listExOutCateItemData(rsCategoryItems);
                    }
                    else {
                        self.listExOutCateItemData([]);
                    }
                });
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            $.when(
                service.getAllCategoryItem(self.categoryId(), self.itemType()),
                service.getOutItems(self.conditionCode())
            ).done((
                categoryItems: Array<any>,
                outputItems: Array<any>) => {
                if (categoryItems && categoryItems.length) {
                    let rsCategoryItems: Array<model.ExternalOutputCategoryItemData> = _.map(categoryItems, x => {
                        return new model.ExternalOutputCategoryItemData(x.itemNo, x.itemName);
                    });
                    self.listExOutCateItemData(rsCategoryItems);
                }

                if (outputItems && outputItems.length) {
                    let rsOutputItems: Array<model.StandardOutputItem> = _.map(outputItems, x => {
                        let listCategoryItem: Array<model.CategoryItem> = _.map(x.categoryItems, y => {
                            return new model.CategoryItem(self.categoryId(), y.categoryItemNo,
                                y.categoryItemName, y.operationSymbol, y.displayOrder);
                        });
                        return new model.StandardOutputItem(x.outItemCd, x.outItemName, x.condSetCd,
                            "", x.itemType, listCategoryItem);
                    });
                    self.listStandardOutputItem(rsOutputItems);
                    self.selectedStandardOutputItemCode(rsOutputItems[0].outItemCd());
                }
                dfd.resolve();
            }).fail((error) => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                nts.uk.ui.block.clear();
            });

            return dfd.promise();
        }

        // 新規登録を実行する
        settingNewMode() {
            let self = this;
            self.isNewMode(true);
            self.selectedStandardOutputItemCode("");
            self.currentStandardOutputItem(new model.StandardOutputItem(null, null, null, null, 0, null));
        }
        
        isActiveSymbolAnd() {
            let self = this;
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.CHARACTER) {
                return true;
            }

            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.IN_SERVICE_CATEGORY
                && self.currentStandardOutputItem().categoryItems().length === 0) {
                return true;
            }
            return false;
        }

        isActiveSymbolPlus() {
            let self = this;
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.NUMERIC) {
                return true;
            }
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.DATE
              && self.currentStandardOutputItem().categoryItems().length === 0) {
                return true;
            }
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.TIME) {
                return true;
            }
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.TIME_OF_DAY
              && self.currentStandardOutputItem().categoryItems().length === 0) {
                return true;
            }
            return false;
        }

        isActiveSymbolMinus() {
            let self = this;
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.NUMERIC
              && self.currentStandardOutputItem().categoryItems().length > 0) {
                return true;
            }
            if (self.currentStandardOutputItem().itemType() === model.ITEM_TYPE.TIME
              && self.currentStandardOutputItem().categoryItems().length > 0) {
                return true;
            }
            return false;
        }
        
        clickSymbolAnd() {
            let self = this;
            self.addCategoryItem(model.SYMBOL.AND);
        }
        
        clickSymbolPlus() {
            let self = this;
            self.addCategoryItem(model.SYMBOL.PLUS);
        }
        
        clickSymbolMinus() {
            let self = this;
            self.addCategoryItem(model.SYMBOL.MINUS);
        }
        
        addCategoryItem(operatorSymbol: any) : void {
            let self = this;
            let categoryItems: Array<model.CategoryItem> = self.currentStandardOutputItem().categoryItems();
            let maxDisplayOrder = _.maxBy(categoryItems, item => {
                return item.displayOrder;
            });
            let nextDisplayOrder = maxDisplayOrder ? maxDisplayOrder.displayOrder + 1 : 1;
            for (let i = 0; i < self.selectedExOutputCateItemDatas().length; i++) {
                let exOutCateItemData = _.find(self.listExOutCateItemData(), item => {
                    return item.itemNo() == self.selectedExOutputCateItemDatas()[i];
                });
                if (categoryItems.length > 0) {
                    categoryItems.push(new model.CategoryItem(self.categoryId(), self.selectedExOutputCateItemDatas()[i],
                        exOutCateItemData.itemName(), operatorSymbol, nextDisplayOrder + i));
                }
                else {
                    categoryItems.push(new model.CategoryItem(self.categoryId(), self.selectedExOutputCateItemDatas()[i],
                        exOutCateItemData.itemName(), null, nextDisplayOrder + i));
                }
            }
            self.currentStandardOutputItem().categoryItems(categoryItems);
        }
        
        // 出力項目を登録する
        registerOutputItem() {
            let self = this;
            let currentStandardOutputItem: model.StandardOutputItem = self.currentStandardOutputItem();
            $('.nts-input').trigger("validate");

            if (errors.hasError() === false && self.isValid()) {
                block.invisible();
                if (self.isNewMode()) {
                    // Add
                    service.addOutputItem(ko.toJS(currentStandardOutputItem)).done(() => {
                        service.getOutItems(self.conditionCode()).done((outputItems: Array<any>) => {
                            if (outputItems && outputItems.length) {
                                let rsOutputItems: Array<model.StandardOutputItem> = _.map(outputItems, x => {
                                    return new model.StandardOutputItem(x.outItemCd, x.outItemName, x.condSetCd,
                                        "", x.itemType, x.categoryItems);
                                });
                                self.listStandardOutputItem(rsOutputItems);
                                self.selectedStandardOutputItemCode(currentStandardOutputItem.outItemCd());
                            }
                            else {
                                self.listStandardOutputItem([]);
                            }
                        });
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    // Update
                    service.updateOutputItem(ko.toJS(currentStandardOutputItem)).done(() => {
                        service.getOutItems(self.conditionCode()).done((outputItems: Array<any>) => {
                            if (outputItems && outputItems.length) {
                                let rsOutputItems: Array<model.StandardOutputItem> = _.map(outputItems, x => {
                                    return new model.StandardOutputItem(x.outItemCd, x.outItemName, x.condSetCd,
                                        "", x.itemType, x.categoryItems);
                                });
                                self.listStandardOutputItem(rsOutputItems);
                                self.selectedStandardOutputItemCode(currentStandardOutputItem.outItemCd());
                            }
                            else {
                                self.listStandardOutputItem([]);
                            }
                        });
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                }
            }
        }
        
        deleteOutputItem() {
            let self = this;
            let currentStandardOutputItem: model.StandardOutputItem = self.currentStandardOutputItem();
            let listOutputItem = self.listStandardOutputItem;
            block.invisible();
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                if (currentStandardOutputItem.outItemCd()) {
                    let index: number = _.findIndex(listOutputItem(), function(x) {
                        return x.outItemCd() == currentStandardOutputItem.outItemCd()
                    });

                    service.removeOutputItem(ko.toJS(currentStandardOutputItem)).done(function() {
                        service.getOutItems(self.conditionCode()).done((outputItems: Array<any>) => {
                            if (outputItems && outputItems.length) {
                                let rsOutputItems: Array<model.StandardOutputItem> = _.map(outputItems, x => {
                                    return new model.StandardOutputItem(x.outItemCd, x.outItemName, x.condSetCd,
                                        "", x.itemType, x.categoryItems);
                                });
                                self.listStandardOutputItem(rsOutputItems);
                                self.selectedStandardOutputItemCode(currentStandardOutputItem.outItemCd());
                            }
                            else {
                                self.listStandardOutputItem([]);
                            }
                            if (self.listStandardOutputItem().length == 0) {
                                self.selectedStandardOutputItemCode("");
                                self.isNewMode(true);
                                nts.uk.ui.errors.clearAll();
                            } else {
                                if (index == self.listStandardOutputItem().length) {
                                    self.selectedStandardOutputItemCode(self.listStandardOutputItem()[index - 1].outItemCd());
                                } else {
                                    self.selectedStandardOutputItemCode(self.listStandardOutputItem()[index].outItemCd());
                                }
                            }
                        });
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                    })
                }
            }).then(() => {
                $('.nts-input').ntsError('clear');
                nts.uk.ui.errors.clearAll();
                block.clear();
            });;
        }
        
        // 外部出力項目登録確認
        isValid() {
            let self = this;
            if (self.listExOutCateItemData().length === 0) {
                alertError({ messageId: "Msg_656" });
                return false;
            }
            if (!self.isNewMode()) {
                return true;
            }

            let stdOutItem = _.find(self.listStandardOutputItem(), x => {
                return x.outItemCd() === self.currentStandardOutputItem().outItemCd();
            });
            
            if (stdOutItem) {
                alertError({ messageId: "Msg_3" });
                return false;
            }
            return true;
        }
        

        openCMF002g() {
            modal("/view/cmf/002/g/index.xhtml").onClosed(function() {

            });
        }

        openCMF002h() {
            modal("/view/cmf/002/h/index.xhtml").onClosed(function() {

            });
        }

        openCMF002f() {
            modal("/view/cmf/002/f/index.xhtml").onClosed(function() {

            });
        }

        openItemTypeSetting() {
            let self = this;
            let url = "";
            switch (self.currentStandardOutputItem().itemType()) {
                case model.ITEM_TYPE.NUMERIC:
                    url = "/view/cmf/002/i/index.xhtml";
                    break;
                case model.ITEM_TYPE.CHARACTER:
                    url = "/view/cmf/002/j/index.xhtml";
                    break;
                case model.ITEM_TYPE.DATE:
                    url = "/view/cmf/002/k/index.xhtml";
                    break;
                case model.ITEM_TYPE.TIME:
                    url = "/view/cmf/002/l/index.xhtml";
                    break;
                case model.ITEM_TYPE.TIME_OF_DAY:
                    url = "/view/cmf/002/m/index.xhtml";
                    break;
                case model.ITEM_TYPE.IN_SERVICE_CATEGORY:
                    url = "/view/cmf/002/n/index.xhtml";
                    break;
            }
            modal(url).onClosed(function() {

            });
        }
        
        // Close dialog
        closeSetting() {
            nts.uk.ui.windows.close();
        }
    }
}