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
        currentStandardOutputItem: KnockoutObservable<model.StandardOutputItem>;
        selectedStandardOutputItemCode: KnockoutObservable<string> = ko.observable("");
        listStandardOutputItem: KnockoutObservableArray<model.StandardOutputItem> = ko.observableArray([]);
        itemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        itemType: KnockoutObservable<number> = ko.observable(0);
        conditionCode: KnockoutObservable<string>;
        conditionName: KnockoutObservable<string>;
        categoryId: KnockoutObservable<number>;
        categoryName: KnockoutObservable<string>;
        formulaResult: KnockoutObservable<string> = ko.observable("");

        atWorkDataOutputItem: KnockoutObservable<model.AtWorkDataOutputItem>;
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting>;
        dateDataFormatSetting: KnockoutObservable<model.DateDataFormatSetting>;
        inTimeDataFormatSetting: KnockoutObservable<model.InTimeDataFormatSetting>;
        numberDataFormatSetting: KnockoutObservable<model.NumberDataFormatSetting>;
        timeDataFormatSetting: KnockoutObservable<model.TimeDataFormatSetting>;

        selectedExOutputCateItemDatas: KnockoutObservableArray<string> = ko.observableArray([]);
        listExOutCateItemData: KnockoutObservableArray<model.ExternalOutputCategoryItemData> = ko.observableArray([]);

        selectedCategoryItems: KnockoutObservableArray<string> = ko.observableArray([]);
        categoryItems: KnockoutObservableArray<model.CategoryItem> = ko.observableArray([]);
        constructor() {
            let self = this;
            let params = getShared("CMF002_C_PARAMS");
            let _rsList: Array<model.ItemModel> = model.getItemTypes();
            self.itemTypes(_rsList);

            self.conditionName = ko.observable(params.conditionSetCode + "　" + params.conditionSetName);
            self.categoryName = ko.observable(params.categoryName);
            self.categoryId = ko.observable(params.categoryId);
            self.conditionCode = ko.observable(params.conditionSetCode);
            self.currentStandardOutputItem = ko.observable(new model.StandardOutputItem(null, null, self.conditionCode(), 0, null));

            self.selectedStandardOutputItemCode.subscribe(code => {
                if (code) {
                    block.invisible();
                    let currentOutputItem = _.find(self.listStandardOutputItem(), item => {
                        return item.outItemCd() == code;
                    });
                    if (currentOutputItem) {
                        self.currentStandardOutputItem(currentOutputItem);
                        self.itemType(currentOutputItem.itemType());
                        self.categoryItems(currentOutputItem.categoryItems());
                        self.isNewMode(false);

                        service.getDataFormatSetting(self.itemType(), self.conditionCode(), code).done((data) => {
                            if (data) {
                                switch (self.itemType()) {
                                    case model.ITEM_TYPE.NUMERIC:
                                        if (self.numberDataFormatSetting != undefined) {
                                            self.numberDataFormatSetting(new model.NumberDataFormatSetting(data));
                                        }
                                        else {
                                            self.numberDataFormatSetting = ko.observable(new model.NumberDataFormatSetting(data))
                                        }
                                        break;
                                    case model.ITEM_TYPE.CHARACTER:
                                        if (self.characterDataFormatSetting != undefined) {
                                            self.characterDataFormatSetting(new model.CharacterDataFormatSetting(data));
                                        }
                                        else {
                                            self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(data))
                                        }
                                        break;
                                    case model.ITEM_TYPE.DATE:
                                        if (self.dateDataFormatSetting != undefined) {
                                            self.dateDataFormatSetting(new model.DateDataFormatSetting(data));
                                        }
                                        else {
                                            self.dateDataFormatSetting = ko.observable(new model.DateDataFormatSetting(data))
                                        }
                                        break;
                                    case model.ITEM_TYPE.TIME:
                                        if (self.timeDataFormatSetting != undefined) {
                                            self.timeDataFormatSetting(new model.TimeDataFormatSetting(data));
                                        }
                                        else {
                                            self.timeDataFormatSetting = ko.observable(new model.TimeDataFormatSetting(data))
                                        }
                                        break;
                                    case model.ITEM_TYPE.INS_TIME:
                                        if (self.inTimeDataFormatSetting != undefined) {
                                            self.inTimeDataFormatSetting(new model.InTimeDataFormatSetting(data));
                                        }
                                        else {
                                            self.inTimeDataFormatSetting = ko.observable(new model.InTimeDataFormatSetting(data))
                                        }
                                        break;
                                    case model.ITEM_TYPE.AT_WORK_CLS:
                                        if (self.atWorkDataOutputItem != undefined) {
                                            self.atWorkDataOutputItem(new model.AtWorkDataOutputItem(data));
                                        }
                                        else {
                                            self.atWorkDataOutputItem = ko.observable(new model.AtWorkDataOutputItem(data))
                                        }
                                        break;
                                }
                            }
                        }).fail((error) => {
                            alertError(error);
                        }).always(() => {
                            block.clear();
                        });
                    }
                    else {
                        self.settingNewMode();
                    }
                    self.setFocus();
                    _.defer(() => { errors.clearAll() });
                } else {
                    self.settingNewMode();
                }
            });

            self.itemType.subscribe(code => {
                self.categoryItems([]);
                self.selectedExOutputCateItemDatas([]);

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

            self.categoryItems.subscribe(function(values: Array<model.CategoryItem>) {
                let newFormulaResult = "";
                _.forEach(values, item => {
                    newFormulaResult = newFormulaResult + item.dispOperationSymbol + item.categoryItemName();
                });
                self.formulaResult(newFormulaResult);
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            $.when(
                service.getAllCategoryItem(self.categoryId(), self.itemType()),
                self.getAllOutputItem(null)
            ).done((
                categoryItems: Array<any>) => {
                if (categoryItems && categoryItems.length) {
                    categoryItems = _.sortBy(categoryItems, ['itemNo']);
                    let rsCategoryItems: Array<model.ExternalOutputCategoryItemData> = _.map(categoryItems, x => {
                        return new model.ExternalOutputCategoryItemData(x.itemNo, x.itemName);
                    });
                    self.listExOutCateItemData(rsCategoryItems);
                }
                dfd.resolve(self);
            }).fail((error) => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });

            return dfd.promise();
        }

        setFocus() {
            let self = this;
            if (self.isNewMode()) {
                $('#C4_1').focus();
            } else {
                $('#C7_2').focus();
            }
        }

        // 新規登録を実行する
        settingNewMode() {
            let self = this;
            self.isNewMode(true);
            self.selectedStandardOutputItemCode("");
            self.currentStandardOutputItem(new model.StandardOutputItem(null, null, self.conditionCode(), 0, null));
            self.itemType(0);
            self.categoryItems([]);
            self.setFocus();
        }

        isActiveSymbolAnd() {
            let self = this;
            if (self.itemType() === model.ITEM_TYPE.CHARACTER) {
                return true;
            }

            if (self.itemType() === model.ITEM_TYPE.AT_WORK_CLS
                && self.categoryItems().length === 0) {
                return true;
            }
            return false;
        }

        isActiveSymbolPlus() {
            let self = this;
            if (self.itemType() === model.ITEM_TYPE.NUMERIC) {
                return true;
            }
            if (self.itemType() === model.ITEM_TYPE.DATE
                && self.categoryItems().length === 0) {
                return true;
            }
            if (self.itemType() === model.ITEM_TYPE.TIME) {
                return true;
            }
            if (self.itemType() === model.ITEM_TYPE.INS_TIME
                && self.categoryItems().length === 0) {
                return true;
            }
            return false;
        }

        isActiveSymbolMinus() {
            let self = this;
            if (self.itemType() === model.ITEM_TYPE.NUMERIC
                && self.categoryItems().length > 0) {
                return true;
            }
            if (self.itemType() === model.ITEM_TYPE.TIME
                && self.categoryItems().length > 0) {
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

        addCategoryItem(operatorSymbol: any): void {
            let self = this;
            let categoryItems: Array<model.CategoryItem> = self.categoryItems();
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
            self.categoryItems(categoryItems);
        }

        clickRemoveCtgItem() {
            let self = this;
            let categoryItems: Array<model.CategoryItem> = self.categoryItems();
            _.each(self.selectedCategoryItems(), key => {
                _.remove(categoryItems, item => {
                    return item.categoryItemNo() == key;
                });
            });
            self.categoryItems(categoryItems);
        }

        getAllOutputItem(code?: string): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            self.listStandardOutputItem.removeAll();

            service.getOutItems(self.conditionCode()).done((outputItems: Array<any>) => {
                if (outputItems && outputItems.length) {
                    outputItems = _.sortBy(outputItems, ['outItemCd']);
                    let rsOutputItems: Array<model.StandardOutputItem> = _.map(outputItems, x => {
                        let listCategoryItem: Array<model.CategoryItem> = _.map(x.categoryItems, y => {
                            return new model.CategoryItem(self.categoryId(), y.categoryItemNo,
                                y.categoryItemName, y.operationSymbol, y.displayOrder);
                        });
                        return new model.StandardOutputItem(x.outItemCd, x.outItemName, x.condSetCd,
                            x.itemType, listCategoryItem);
                    });
                    self.listStandardOutputItem(rsOutputItems);
                    if (code) {
                        if (code == self.selectedStandardOutputItemCode())
                            self.selectedStandardOutputItemCode.valueHasMutated();
                        else
                            self.selectedStandardOutputItemCode(code);
                    }
                    else {
                        self.selectedStandardOutputItemCode(rsOutputItems[0].outItemCd());
                    }
                }
                else {
                    errors.clearAll();
                    self.listStandardOutputItem([]);
                    self.settingNewMode();
                }
                dfd.resolve();
            }).fail(function(res) {
                alertError({ messageId: res.messageId });
                dfd.reject();
            }).always(function() {
                block.clear();
            });

            return dfd.promise();
        }

        // 出力項目を登録する
        registerOutputItem() {
            let self = this;
            let currentStandardOutputItem: model.StandardOutputItem = self.currentStandardOutputItem();

            $('.nts-input').trigger("validate");
            currentStandardOutputItem.itemType(self.itemType());
            currentStandardOutputItem.categoryItems(self.categoryItems());
            switch (self.itemType()) {
                case model.ITEM_TYPE.NUMERIC:
                    if (self.numberDataFormatSetting != undefined) {
                        currentStandardOutputItem.numberDataFormatSetting(self.numberDataFormatSetting());
                    }
                    break;
                case model.ITEM_TYPE.CHARACTER:
                    if (self.characterDataFormatSetting != undefined) {
                        currentStandardOutputItem.characterDataFormatSetting(self.characterDataFormatSetting());
                    }
                    break;
                case model.ITEM_TYPE.DATE:
                    if (self.dateDataFormatSetting != undefined) {
                        currentStandardOutputItem.dateDataFormatSetting(self.dateDataFormatSetting());
                    }
                    break;
                case model.ITEM_TYPE.TIME:
                    if (self.timeDataFormatSetting != undefined) {
                        currentStandardOutputItem.timeDataFormatSetting(self.timeDataFormatSetting());
                    }
                    break;
                case model.ITEM_TYPE.INS_TIME:
                    if (self.inTimeDataFormatSetting != undefined) {
                        currentStandardOutputItem.inTimeDataFormatSetting(self.inTimeDataFormatSetting());
                    }
                    break;
                case model.ITEM_TYPE.AT_WORK_CLS:
                    if (self.atWorkDataOutputItem != undefined) {
                        currentStandardOutputItem.atWorkDataOutputItem(self.atWorkDataOutputItem());
                    }
                    break;
            }
            if (errors.hasError() === false && self.isValid()) {
                block.invisible();
                if (self.isNewMode()) {

                    // Add
                    service.addOutputItem(ko.toJS(currentStandardOutputItem)).done(() => {
                        self.getAllOutputItem(currentStandardOutputItem.outItemCd()).done(() => {
                            info({ messageId: "Msg_15" }).then(() => {
                                self.setFocus();
                            });
                        });
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    // Update
                    service.updateOutputItem(ko.toJS(currentStandardOutputItem)).done(() => {
                        self.getAllOutputItem(currentStandardOutputItem.outItemCd()).done(() => {
                            info({ messageId: "Msg_15" }).then(() => {
                                self.setFocus();
                            });
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
                        self.getAllOutputItem(currentStandardOutputItem.outItemCd()).done(() => {
                            info({ messageId: "Msg_16" }).then(() => {
                                if (self.listStandardOutputItem().length == 0) {
                                    self.selectedStandardOutputItemCode('');
                                    self.isNewMode(true);
                                    self.setFocus();
                                } else {
                                    if (index == self.listStandardOutputItem().length) {
                                        self.selectedStandardOutputItemCode(self.listStandardOutputItem()[index - 1].outItemCd());
                                    } else {
                                        self.selectedStandardOutputItemCode(self.listStandardOutputItem()[index].outItemCd());
                                    }
                                }
                                errors.clearAll();
                            });
                        });
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                    })
                }
            }).then(() => {
                $('.nts-input').ntsError('clear');
                errors.clearAll();
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
            let paramName = "";
            let formatSetting = null;
            switch (self.itemType()) {
                case model.ITEM_TYPE.NUMERIC:
                    url = "/view/cmf/002/i/index.xhtml";
                    paramName = "CMF002_I_PARAMS";
                    if (self.numberDataFormatSetting != undefined) {
                        formatSetting = ko.toJS(self.numberDataFormatSetting);
                    }
                    break;
                case model.ITEM_TYPE.CHARACTER:
                    url = "/view/cmf/002/j/index.xhtml";
                    paramName = "CMF002_J_PARAMS";
                    if (self.characterDataFormatSetting != undefined) {
                        formatSetting = ko.toJS(self.characterDataFormatSetting);
                    }
                    break;
                case model.ITEM_TYPE.DATE:
                    url = "/view/cmf/002/k/index.xhtml";
                    paramName = "CMF002_K_PARAMS";
                    if (self.dateDataFormatSetting != undefined) {
                        formatSetting = ko.toJS(self.dateDataFormatSetting);
                    }
                    break;
                case model.ITEM_TYPE.TIME:
                    url = "/view/cmf/002/l/index.xhtml";
                    paramName = "CMF002_L_PARAMS";
                    if (self.timeDataFormatSetting != undefined) {
                        formatSetting = ko.toJS(self.timeDataFormatSetting);
                    }
                    break;
                case model.ITEM_TYPE.INS_TIME:
                    url = "/view/cmf/002/m/index.xhtml";
                    paramName = "CMF002_M_PARAMS";
                    if (self.inTimeDataFormatSetting != undefined) {
                        formatSetting = ko.toJS(self.inTimeDataFormatSetting);
                    }
                    break;
                case model.ITEM_TYPE.AT_WORK_CLS:
                    url = "/view/cmf/002/n/index.xhtml";
                    paramName = "CMF002_N_PARAMS";
                    if (self.atWorkDataOutputItem != undefined) {
                        formatSetting = ko.toJS(self.atWorkDataOutputItem);
                    }
                    break;
            }
            setShared(paramName, { screenMode: model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL, formatSetting: formatSetting });
            modal(url).onClosed(function() {
                let output = getShared('CMF002_C_PARAMS');
                if (output) {
                    let fs = output.formatSetting;
                    switch (self.itemType()) {
                        case model.ITEM_TYPE.NUMERIC:
                            if (self.numberDataFormatSetting != undefined) {
                                self.numberDataFormatSetting(fs);
                            }
                            else {
                                self.numberDataFormatSetting = ko.observable(new model.NumberDataFormatSetting(fs))
                            }
                            break;
                        case model.ITEM_TYPE.CHARACTER:
                            if (self.characterDataFormatSetting != undefined) {
                                self.characterDataFormatSetting(fs);
                            }
                            else {
                                self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(fs))
                            }
                            break;
                        case model.ITEM_TYPE.DATE:
                            if (self.dateDataFormatSetting != undefined) {
                                self.dateDataFormatSetting(fs);
                            }
                            else {
                                self.dateDataFormatSetting = ko.observable(new model.DateDataFormatSetting(fs))
                            }
                            break;
                        case model.ITEM_TYPE.TIME:
                            if (self.timeDataFormatSetting != undefined) {
                                self.timeDataFormatSetting(fs);
                            }
                            else {
                                self.timeDataFormatSetting = ko.observable(new model.TimeDataFormatSetting(fs))
                            }
                            break;
                        case model.ITEM_TYPE.INS_TIME:
                            if (self.inTimeDataFormatSetting != undefined) {
                                self.inTimeDataFormatSetting(fs);
                            }
                            else {
                                self.inTimeDataFormatSetting = ko.observable(new model.InTimeDataFormatSetting(fs))
                            }
                            break;
                        case model.ITEM_TYPE.AT_WORK_CLS:
                            if (self.atWorkDataOutputItem != undefined) {
                                self.atWorkDataOutputItem(fs);
                            }
                            else {
                                self.atWorkDataOutputItem = ko.observable(new model.AtWorkDataOutputItem(fs))
                            }
                            break;
                    }
                }
            });
        }

        // Close dialog
        closeSetting() {
            nts.uk.ui.windows.close();
        }
    }
}