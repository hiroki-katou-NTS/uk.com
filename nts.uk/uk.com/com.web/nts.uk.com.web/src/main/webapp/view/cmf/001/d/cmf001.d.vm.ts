module nts.uk.com.view.cmf001.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        systemType: model.ItemModel;

        listCategory: KnockoutObservableArray<model.ExternalAcceptanceCategory>;
        selectedCategory: KnockoutObservable<string>;

        listCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData>;
        listSelectedCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData> = ko.observableArray([]);
        selectedCategoryItem: KnockoutObservable<number>;

        listAcceptItem: KnockoutObservableArray<model.StandardAcceptItem> = ko.observableArray([]);
        selectedAcceptItem: KnockoutObservable<number> = ko.observable(0);

        stdCondSet: KnockoutObservable<model.StandardAcceptanceConditionSetting> = ko.observable(null);
        stdCondSetCd: KnockoutObservable<string> = ko.observable(null);
        dataTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getItemTypes());

        enableCategory: KnockoutObservable<boolean> = ko.observable(true);
        startLoad: KnockoutObservable<boolean> = ko.observable(true);

        fileId: KnockoutObservable<string>;
        filename: KnockoutObservable<string>;
        fileInfo: KnockoutObservable<any>;
        fileDataTotalLine: KnockoutObservable<number> = ko.observable(null);
        onchange: (filename) => void;
        listMappingData: KnockoutObservableArray<model.MappingListData> = ko.observableArray([]);
        
        constructor(data: any) {
            var self = this;
            let item = _.find(model.getSystemTypes(), x => { return x.code == data.systemType; });
            self.systemType = item;
            
            self.stdCondSetCd(data.conditionCode);

            self.listCategory = ko.observableArray([]);
            self.selectedCategory = ko.observable('');

            self.listCategoryItem = ko.observableArray([]);

            self.selectedCategory.subscribe((data) => {
                if (data && !self.startLoad()) {
                    self.loadCategoryItemData(data);
                    self.listAcceptItem.removeAll();
                }
            });

            self.selectedCategoryItem = ko.observable(1);
            $("#fixed-table").ntsFixedTable({ height: 540 });

            this.fileId = ko.observable(null);
            this.filename = ko.observable(null);
            this.fileInfo = ko.observable(null);
            this.onchange = (filename) => {
            };

            self.selectedAcceptItem.subscribe((data) => {
                $("#fixed-table tr").removeClass("my-active-row");
                $("#fixed-table tr[data-id='" + data + "']").addClass("my-active-row");
            });
        }

        finished(fileInfo: any) {
            var self = this;
            self.fileId(fileInfo.id);
            block.invisible();
            service.getNumberOfLine(self.fileId()).done(function(totalLine: any) {
                self.fileDataTotalLine(totalLine);
                if (self.screenFileCheck()) {
                    //write data mapping
                    block.invisible();
                    service.getRecord(self.fileId(), self.stdCondSet().csvDataItemLineNumber(), self.stdCondSet().csvDataStartLine()).done((rs: Array<any>) => {
                        let _rsList: Array<model.MappingListData> = _.map(rs, x => {
                            return new model.MappingListData(x.colNum, x.colName, x.sampleData);
                        });
                        self.listMappingData(_rsList);
                        if (self.listAcceptItem().length > 0) {
                            //Clear "CSV data item name" and "sample data" on the screen, msg985
                            for (var i = 0; i < self.listAcceptItem().length; i++) {
                                self.listAcceptItem()[i].csvItemNumber(null);
                                self.listAcceptItem()[i].csvItemName(null);
                                self.listAcceptItem()[i].sampleData(null);
                            } 
                            info({messageId: "Msg_985"});
                        }
                    }).fail(function(err) {
                        alertError(err);
                    }).always(() => {
                        block.clear();
                    });
                    
                } else {
                    return;
                }
            }).fail(function(err) {
                alertError(err);
            }).always(() => {
                block.clear();
            });
        }

        btnLeftClick() {
            let self = this;
            if (self.selectedAcceptItem() > 0 && self.selectedAcceptItem() <= self.listAcceptItem().length) {
                let selectedAItem = _.find(self.listAcceptItem(), x => { return x.acceptItemNumber() == self.selectedAcceptItem(); });
                let selectedCItem = _.find(self.listSelectedCategoryItem(), x => { return x.itemNo == selectedAItem.categoryItemNo(); });
                if (selectedCItem.required) {
                    alertError({messageId: "Msg_898"});
                } else {
                    self.listAcceptItem.remove(selectedAItem);
                    self.listCategoryItem.push(selectedCItem);
                    self.listCategoryItem(_.sortBy(self.listCategoryItem(), ['itemNo']));
                    self.listSelectedCategoryItem.remove(selectedCItem);
                    for (var i = 0; i < self.listAcceptItem().length; i++) {
                        self.listAcceptItem()[i].acceptItemNumber(i + 1);
                    }
                    if (self.selectedAcceptItem() >= self.listAcceptItem().length)
                        self.selectedAcceptItem(self.listAcceptItem().length);
                    else
                        self.selectedAcceptItem.valueHasMutated();
                }
            } else {
                alertError({messageId: "Msg_897"});
            }
        }

        btnRightClick() {
            let self = this;
            let selectedIndex = _.findIndex(self.listCategoryItem(), x => { return x.itemNo == self.selectedCategoryItem(); });
            if (selectedIndex >= 0) {
                let i = self.listAcceptItem().length + 1;
                let selectedItem = _.find(self.listCategoryItem(), x => { return x.itemNo == self.selectedCategoryItem(); });
                let item = new model.StandardAcceptItem(null, null, 0, i, selectedItem.itemName, self.stdCondSet().systemType(), self.stdCondSet().conditionSettingCode(), selectedItem.itemNo);
                self.listAcceptItem.push(item);
                self.listSelectedCategoryItem.push(selectedItem);
                self.listCategoryItem.remove(selectedItem);
                if (selectedIndex >= self.listCategoryItem().length && self.listCategoryItem().length > 0)
                    self.selectedCategoryItem(self.listCategoryItem()[self.listCategoryItem().length - 1].itemNo);
                else
                    self.selectedCategoryItem(self.listCategoryItem()[selectedIndex] ? self.listCategoryItem()[selectedIndex].itemNo : null);
            } else {
                alertError({messageId: "Msg_894"});
            }
        }

        //open screen G, H, I, J
        dataTypeSetting(data: model.StandardAcceptItem) {
            let self = this, url = "", formatSetting = null, paramName = "";
            switch (data.itemType()) {
                case model.ITEM_TYPE.NUMERIC:
                    url = "/view/cmf/001/g/index.xhtml";
                    paramName = "CMF001gParams";
                    if (data.numberFormatSetting)
                        formatSetting = ko.toJS(data.numberFormatSetting);
                    break;
                case model.ITEM_TYPE.CHARACTER:
                    url = "/view/cmf/001/h/index.xhtml";
                    paramName = "CMF001hParams";
                    if (data.charFormatSetting)
                        formatSetting = ko.toJS(data.charFormatSetting);
                    break;
                case model.ITEM_TYPE.DATE:
                    url = "/view/cmf/001/i/index.xhtml";
                    paramName = "CMF001iParams";
                    if (data.dateFormatSetting)
                        formatSetting = ko.toJS(data.dateFormatSetting);
                    break;
                case model.ITEM_TYPE.INS_TIME:
                    url = "/view/cmf/001/j/index.xhtml";
                    paramName = "CMF001jParams";
                    if (data.instTimeFormatSetting)
                        formatSetting = ko.toJS(data.instTimeFormatSetting);
                    break;
                case model.ITEM_TYPE.TIME:
                    url = "/view/cmf/001/j/index.xhtml";
                    paramName = "CMF001jParams";
                    if (data.timeFormatSetting)
                        formatSetting = ko.toJS(data.timeFormatSetting);
                    break;
            }
            setShared(paramName, { formatSetting: formatSetting, inputMode: true, lineNumber: data.acceptItemNumber() }, true);

            modal(url).onClosed(function() {
                var output = getShared('CMF001FormatOutput');
                if (output) {
                    let fs = output.formatSetting; 
                    switch (data.itemType()) {
                        case model.ITEM_TYPE.NUMERIC:
                            data.numberFormatSetting(new model.NumericDataFormatSetting(
                                                        fs.effectiveDigitLength, fs.startDigit, fs.endDigit, 
                                                        fs.decimalDivision, fs.decimalDigitNumber, fs.decimalPointClassification, 
                                                        fs.decimalFraction, fs.codeConvertCode, fs.fixedValue, fs.valueOfFixedValue));
                            break;
                        case model.ITEM_TYPE.CHARACTER:
                            data.charFormatSetting(new model.CharacterDataFormatSetting(
                                                fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                                fs.codeEditing, fs.codeEditDigit, fs.codeEditingMethod,
                                                fs.codeConvertCode, fs.fixedValue, fs.fixedVal));
                            break;
                        case model.ITEM_TYPE.DATE:
                            data.dateFormatSetting(new model.DateDataFormatSetting(fs.formatSelection, fs.fixedValue, fs.valueOfFixedValue));
                            break;
                        case model.ITEM_TYPE.INS_TIME:
                            data.instTimeFormatSetting(new model.InstantTimeDataFormatSetting(
                                                fs.effectiveDigitLength,
                                                fs.startDigit,
                                                fs.endDigit,
                                                fs.decimalSelect,
                                                fs.hourMinSelect,
                                                fs.delimiterSet,
                                                fs.roundProc,
                                                fs.roundProcCls,
                                                fs.fixedValue,
                                                fs.valueOfFixedValue));
                            break;
                        case model.ITEM_TYPE.TIME:
                            data.timeFormatSetting(new model.TimeDataFormatSetting(
                                                fs.effectiveDigitLength,
                                                fs.startDigit,
                                                fs.endDigit,
                                                fs.decimalSelect,
                                                fs.hourMinSelect,
                                                fs.delimiterSet,
                                                fs.roundProc,
                                                fs.roundProcCls,
                                                fs.fixedValue,
                                                fs.valueOfFixedValue));
                            break;
                    }
                }
            });
        }

        openCMF001l(data: model.StandardAcceptItem) {
            let self = this, condition = null;
            if (data.screenConditionSetting) condition = ko.toJS(data.screenConditionSetting);
            setShared('CMF001lParams', {
                dataType: data.itemType(),
                condition: condition,
                inputMode: true
            }, true);

            modal("/view/cmf/001/l/index.xhtml").onClosed(function() {
                var output = getShared('CMF001lOutput');
                if (output) {
                    data.screenConditionSetting(new model.AcceptScreenConditionSetting(data.acceptItemName(), output.selectComparisonCondition,
                                output.timeConditionValue2, output.timeConditionValue1, 
                                output.timeMomentConditionValue2, output.timeMomentConditionValue1,
                                output.dateConditionValue2, output.dateConditionValue1, 
                                output.characterConditionValue2, output.characterConditionValue1, 
                                output.numberConditionValue2, output.numberConditionValue1,
                                data.conditionSettingCode(), data.acceptItemNumber()));
                }
            });
        }

        openCMF001f() {
            let self = this;
            modal("/view/cmf/001/f/index.xhtml").onClosed(function() {

            });
        }

        openCMF001e(data: model.StandardAcceptItem) {
            let self = this;
            if (self.screenFileCheck()) {
                let listCsvData = ko.toJS(self.listMappingData);
                setShared('CMF001eParams', {
                    listCsvItem: listCsvData,
                    selectedCsvItemNumber: data.csvItemNumber()
                }, true);
    
                modal("/view/cmf/001/e/index.xhtml").onClosed(function() {
                    var output = getShared('CMF001eOutput');
                    if (output) {
                        data.csvItemName(output.selectedCsvItem.csvItemName);
                        data.csvItemNumber(output.selectedCsvItem.csvItemNumber);
                        data.sampleData(output.selectedCsvItem.sampleData);
                    }
                });
            } else {
                return;
            }
        }

        openCMF001b() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                if (self.stdCondSet().categoryId() == null)
                    self.stdCondSet().categoryId(self.selectedCategory());
                let command = {conditionSetting: ko.toJS(self.stdCondSet), listItem: ko.toJS(self.listAcceptItem)};
                service.registerDataAndReturn(command).done(() => {
                    self.enableCategory(false);
                    nts.uk.request.jump("/view/cmf/001/b/index.xhtml", {
                        conditionCode: self.stdCondSet().conditionSettingCode(),
                        sysType: self.systemType.code
                    });
                }).fail(function(error) {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getOneStdData(self.systemType.code, self.stdCondSetCd()).done((cond) => {
                if (cond) {
                    self.stdCondSet(new model.StandardAcceptanceConditionSetting(cond.systemType, 
                                                        cond.conditionSettingCode, cond.conditionSettingName, 
                                                        cond.deleteExistData, cond.acceptMode, 
                                                        cond.csvDataItemLineNumber == null ? 1 : cond.csvDataItemLineNumber, 
                                                        cond.csvDataStartLine == null ? 2 : cond.csvDataStartLine, 
                                                        cond.deleteExistDataMethod, cond.categoryId));
                    service.getAllCategory().done((rs: Array<any>) => {
                        if (rs && rs.length) {
                            let _rsList: Array<model.ExternalAcceptanceCategory> = _.map(rs, x => {
                                return new model.ExternalAcceptanceCategory(x.categoryId, x.categoryName);
                            });
                            self.listCategory(_rsList);
                            service.getAllData(self.systemType.code, self.stdCondSet().conditionSettingCode()).done(function(data: Array<any>) {
                                if (data && data.length) {//co du lieu dang ki
                                    self.selectedCategory(self.stdCondSet().categoryId());
                                    self.loadCategoryItemData(self.stdCondSet().categoryId()).done(() => {
                                        let _rsList: Array<model.StandardAcceptItem> = _.map(data, rs => {
                                        let formatSetting = null, fs = null, screenCondition: model.AcceptScreenConditionSetting = null;
                                            switch (rs.itemType) {
                                                case model.ITEM_TYPE.NUMERIC:
                                                    fs = rs.numberFormatSetting;
                                                    if (fs)
                                                        formatSetting = new model.NumericDataFormatSetting(
                                                            fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                                            fs.decimalDivision, fs.decimalDigitNum, fs.decimalPointCls,
                                                            fs.decimalFraction, fs.cdConvertCd, fs.fixedValue, fs.valueOfFixedValue);
                                                    break;
                                                case model.ITEM_TYPE.CHARACTER:
                                                    fs = rs.charFormatSetting;
                                                    if (fs)
                                                        formatSetting = new model.CharacterDataFormatSetting(
                                                            fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                                            fs.cdEditing, fs.cdEditDigit, fs.cdEditMethod,
                                                            fs.cdConvertCd, fs.fixedValue, fs.fixedVal);
                                                    break;
                                                case model.ITEM_TYPE.DATE:
                                                    fs = rs.dateFormatSetting;
                                                    if (fs)
                                                        formatSetting = new model.DateDataFormatSetting(fs.formatSelection, fs.fixedValue, fs.valueOfFixedValue);
                                                    break;
                                                case model.ITEM_TYPE.INS_TIME:
                                                    fs = rs.instTimeFormatSetting;
                                                    if (fs)
                                                        formatSetting = new model.InstantTimeDataFormatSetting(
                                                            fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                                            fs.decimalSelect, fs.hourMinSelect, fs.delimiterSet,
                                                            fs.roundProc, fs.roundProcCls, fs.fixedValue, fs.valueOfFixedValue);
                                                    break;
                                                case model.ITEM_TYPE.TIME:
                                                    fs = rs.timeFormatSetting;
                                                    if (fs)
                                                        formatSetting = new model.TimeDataFormatSetting(
                                                            fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                                            fs.decimalSelect, fs.hourMinSelect, fs.delimiterSet,
                                                            fs.roundProc, fs.roundProcCls, fs.fixedValue, fs.valueOfFixedValue);
                                                    break;
                                            }
                                            if (rs.screenConditionSetting) {
                                                let sc = rs.screenConditionSetting;
                                                screenCondition = new model.AcceptScreenConditionSetting(rs.acceptItemName, sc.selectComparisonCondition,
                                                            sc.timeConditionValue2, sc.timeConditionValue1, 
                                                            sc.timeMomentConditionValue2, sc.timeMomentConditionValue1,
                                                            sc.dateConditionValue2, sc.dateConditionValue1, 
                                                            sc.characterConditionValue2, sc.characterConditionValue1, 
                                                            sc.numberConditionValue2, sc.numberConditionValue1,
                                                            rs.conditionCode, rs.acceptItemNumber);
                                            }
                                            return new model.StandardAcceptItem(rs.csvItemName, rs.csvItemNumber, rs.itemType, rs.acceptItemNumber, rs.acceptItemName, rs.systemType, rs.conditionSettingCode, rs.categoryItemNo, formatSetting, screenCondition);
                                        });
                                        //_rsList = _.sortBy(_rsList, ['code']);
                                        self.listAcceptItem(_rsList);
                                        _.each(self.listAcceptItem(), rs => {
                                            let item = _.find(self.listCategoryItem(), x => { return x.itemNo == rs.categoryItemNo(); });
                                            rs.acceptItemName(item.itemName);
                                            self.listSelectedCategoryItem.push(item);
                                            self.listCategoryItem.remove(item);
                                        });
                                        self.enableCategory(false);
                                    });
                                    
                                } else {//chua co du lieu, dang ki moi
                                    self.startLoad(false);
                                    self.selectedCategory(self.listCategory()[0].categoryId);
                                }
                                dfd.resolve();
                            }).fail(function(error) {
                                alertError(error);
                                dfd.reject();
                            }).always(() => {
                                block.clear();
                            });
                        } else {
                            alertError({messageId: "Msg_74", messageParams: ["カテゴリ"]});
                        }
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }                
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });         
            return dfd.promise();
        }
        
        private loadCategoryItemData(categoryId: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getCategoryItem(categoryId).done((rs: Array<any>) => {
                if (rs && rs.length) {
                    let _rsList: Array<model.ExternalAcceptanceCategoryItemData> = _.map(rs, x => {
                        return new model.ExternalAcceptanceCategoryItemData(x.itemNo, x.itemName, x.requiredCls);
                    });
                    self.listCategoryItem(_rsList);
                }
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        registerData() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                if (self.stdCondSet().categoryId() == null)
                    self.stdCondSet().categoryId(self.selectedCategory());
                let command = {conditionSetting: ko.toJS(self.stdCondSet), listItem: ko.toJS(self.listAcceptItem)};
                service.registerData(command).done(() => {
                    self.enableCategory(false);
                    info({ messageId: "Msg_15" });
                }).fail(function(error) {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }
        
        private screenFileCheck(): boolean {
            let self = this;
            //check fileId null => msg 899
            if (self.fileId() == null || self.fileId() == undefined) {
                alertError({messageId: "Msg_899"});
                return false;
            }
            //check csvDataLineNumber Not input or exceeding the number of lines of CSV data => msg 900
            if (!nts.uk.ntsNumber.isNumber(self.stdCondSet().csvDataItemLineNumber(), false) || self.stdCondSet().csvDataItemLineNumber() > self.fileDataTotalLine()) {
                alertError({messageId: "Msg_900"});
                return false;
            }
            //check csvDataStartLine Not input or exceeding the number of lines of CSV data => msg 901
            if (!nts.uk.ntsNumber.isNumber(self.stdCondSet().csvDataStartLine(), false) || self.stdCondSet().csvDataStartLine() > self.fileDataTotalLine()) {
                alertError({messageId: "Msg_901"});
                return false;
            }
            
            return true;
        }
        
        refreshCsvData() {
            let self = this;
            if (self.screenFileCheck()) {
                //write data mapping
                //rewrite csv item name and sample data
                block.invisible();
                service.getRecord(self.fileId(), self.stdCondSet().csvDataItemLineNumber(), self.stdCondSet().csvDataStartLine()).done((rs: Array<any>) => {
                    let _rsList: Array<model.MappingListData> = _.map(rs, x => {
                        return new model.MappingListData(x.colNum, x.colName, x.sampleData);
                    });
                    self.listMappingData(_rsList);
                    _.each(self.listAcceptItem(), rs => {
                        let data = _.find(self.listMappingData(), x => { return x.csvItemNumber == rs.csvItemNumber(); });
                        if (data) {
                            rs.csvItemName(data.csvItemName);
                            rs.sampleData(data.sampleData);
                        }
                    });
                }).fail(function(err) {
                    alertError(err);
                }).always(() => {
                    block.clear();
                });
            } else {
                return;
            }
        }
    }
}

$(function() {
    $("#fixed-table").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.selectedAcceptItem(id);
    })
})