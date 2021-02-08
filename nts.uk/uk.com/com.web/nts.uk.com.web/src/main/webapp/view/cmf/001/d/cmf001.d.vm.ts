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
        
        selectedEncoding: KnockoutObservable<number> = ko.observable(3);
        encodingList: KnockoutObservableArray<model.EncodingModel> = ko.observableArray(model.getEncodingList());

        constructor(data: any) {
            var self = this;
            let item = _.find(model.getSystemTypes(), x => { return x.code == data.systemType; });
            self.systemType = item;

            self.stdCondSetCd(data.conditionCode);

            self.listCategory = ko.observableArray([]);
            self.selectedCategory = ko.observable('');

            self.listCategoryItem = ko.observableArray([]);
            
            self.selectedCategory.subscribe((data) => {
                if (data) {
                    self.loadCategoryItemData(data);
                    if (!self.startLoad()) {
                        self.listAcceptItem.removeAll();
                    }
                }
            });

            self.selectedCategoryItem = ko.observable(1);
            $("#fixed-table").ntsFixedTable({ height: 315 });

            this.fileId = ko.observable(null);
            this.filename = ko.observable(null);
            this.fileInfo = ko.observable(null);
            this.onchange = (filename) => {
                let fileInput: HTMLElement = $(".fileinput").get(0);
                if (fileInput.files[0].size == 0) {
                    setTimeout(() => {
                        self.fileId(null);
                        self.filename(null);
                    }, 10);
                } 
            };

            self.selectedAcceptItem.subscribe((data) => {
                $("#fixed-table tr").removeClass("my-active-row");
                $("#fixed-table tr[data-id='" + data + "']").addClass("my-active-row");
            });
            self.selectedEncoding.subscribe((data) => {
                console.log(data);
            });
        }

        private scrollIntoSelectedAcceptItem(data: number): void {
            $("#fixed-table tr[data-id='" + data + "']")[0].focus();
        }

        finished(fileInfo: any) {
            var self = this;
            self.fileId(fileInfo.id);
            console.log(fileInfo);
            block.invisible();
            service.getNumberOfLine(self.fileId(),self.selectedEncoding()).done(function(totalLine: any) {
                self.fileDataTotalLine(totalLine);
                self.setListMappingData(() => {
                    if (self.listAcceptItem().length > 0) {
                        //Clear "CSV data item name" and "sample data" on the screen, msg985
                        for (var i = 0; i < self.listAcceptItem().length; i++) {
                            self.listAcceptItem()[i].csvItemNumber(null);
                            self.listAcceptItem()[i].csvItemName(null);
                            self.listAcceptItem()[i].sampleData(null);
                        } 
                        info({messageId: "Msg_985"});
                    }
                });
            }).fail(function(err) {
                alertError(err).then(() => {
                    self.fileId(null);
                    self.filename(null);
                });
            }).always(() => {
                block.clear();
            });
        }

        btnLeftClick() {
            let self = this;
            if (self.selectedAcceptItem() > 0 && self.selectedAcceptItem() <= self.listAcceptItem().length) {
                let selectedAItemIndex = _.findIndex(self.listAcceptItem(), x => { return x.acceptItemNumber() == self.selectedAcceptItem(); });
                let selectedAItem = _.find(self.listAcceptItem(), x => { return x.acceptItemNumber() == self.selectedAcceptItem(); });
                let selectedCItem = _.find(self.listSelectedCategoryItem(), x => { return x.itemNo == selectedAItem.categoryItemNo(); });
                if (selectedCItem.required) {
                    alertError({messageId: "Msg_898"});
                } else {
                    self.listAcceptItem.remove(selectedAItem);
                    self.listSelectedCategoryItem.remove(selectedCItem);
                    self.selectedCategoryItem(selectedCItem.itemNo);
                    for (var i = 0; i < self.listAcceptItem().length; i++) {
                        self.listAcceptItem()[i].acceptItemNumber(i + 1);
                    }
                    if (self.selectedAcceptItem() >= self.listAcceptItem().length) {
                        selectedAItemIndex = self.listAcceptItem().length;
                        self.selectedAcceptItem(selectedAItemIndex);
                    } else {
                        self.selectedAcceptItem.valueHasMutated();
                    }
                    self.listCategoryItem.push(selectedCItem);
                    self.listCategoryItem(_.sortBy(self.listCategoryItem(), ['itemNo']));
                    self.scrollIntoSelectedAcceptItem(selectedAItemIndex);
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
                let item = new model.StandardAcceptItem(null, null, 0, i, selectedItem.itemName, self.stdCondSet().systemType(), self.stdCondSet().conditionSetCode(), selectedItem.itemNo);
                self.listAcceptItem.push(item);
                self.listSelectedCategoryItem.push(selectedItem);
                self.selectedAcceptItem(self.listAcceptItem().length);
                self.scrollIntoSelectedAcceptItem(self.listAcceptItem().length);
                if (self.listCategoryItem().length > 1) {
                    if (selectedIndex >= self.listCategoryItem().length - 1) {
                        self.selectedCategoryItem(self.listCategoryItem()[self.listCategoryItem().length - 2].itemNo);
                    } else {
                        self.selectedCategoryItem(self.listCategoryItem()[selectedIndex + 1] ? self.listCategoryItem()[selectedIndex + 1].itemNo : null);
                    }
                }
                /* remove after set selected
                   because problem with scroll when remove before set selected */
                self.listCategoryItem.remove(selectedItem);
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
                    if (data.numberFormatSetting())
                        formatSetting = ko.toJS(data.numberFormatSetting);
                    break;
                case model.ITEM_TYPE.CHARACTER:
                    url = "/view/cmf/001/h/index.xhtml";
                    paramName = "CMF001hParams";
                    if (data.charFormatSetting())
                        formatSetting = ko.toJS(data.charFormatSetting);
                    break;
                case model.ITEM_TYPE.DATE:
                    url = "/view/cmf/001/i/index.xhtml";
                    paramName = "CMF001iParams";
                    if (data.dateFormatSetting())
                        formatSetting = ko.toJS(data.dateFormatSetting);
                    break;
                case model.ITEM_TYPE.INS_TIME:
                    url = "/view/cmf/001/j/index.xhtml";
                    paramName = "CMF001jParams";
                    if (data.instTimeFormatSetting())
                        formatSetting = ko.toJS(data.instTimeFormatSetting);
                    break;
                case model.ITEM_TYPE.TIME:
                    url = "/view/cmf/001/j/index.xhtml";
                    paramName = "CMF001jParams";
                    if (data.timeFormatSetting())
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
                            if (data.numberFormatSetting() == null) {
                                data.numberFormatSetting(new model.NumericDataFormatSetting(null, null, null, null, null, null, null, null, null, null));
                            }
                            data.numberFormatSetting().fixedValue(fs.fixedValue);
                            if (fs.fixedValue) {
                                data.numberFormatSetting().valueOfFixedValue(fs.valueOfFixedValue);
                            } else {
                                data.numberFormatSetting().effectiveDigitLength(fs.effectiveDigitLength);
                                if (fs.effectiveDigitLength) {
                                    data.numberFormatSetting().startDigit(fs.startDigit);
                                    data.numberFormatSetting().endDigit(fs.endDigit);
                                }
                                data.numberFormatSetting().decimalDivision(fs.decimalDivision);
                                if (fs.decimalDivision) {
                                    data.numberFormatSetting().decimalDigitNumber(fs.decimalDigitNumber);
                                    data.numberFormatSetting().decimalPointClassification(fs.decimalPointClassification);
                                } else {
                                    data.numberFormatSetting().decimalFraction(fs.decimalFraction);
                                }
                                data.numberFormatSetting().codeConvertCode(fs.codeConvertCode);
                            }
                            data.charFormatSetting(null);
                            data.dateFormatSetting(null);
                            data.timeFormatSetting(null);
                            data.instTimeFormatSetting(null);
                            break;
                        case model.ITEM_TYPE.CHARACTER:
                            if (data.charFormatSetting() == null) {
                                data.charFormatSetting(new model.CharacterDataFormatSetting(null, null, null, null, null, null, null, null, null));
                            }
                            data.charFormatSetting().fixedValue(fs.fixedValue);
                            if (fs.fixedValue) {
                                data.charFormatSetting().fixedVal(fs.fixedVal);
                            } else {
                                data.charFormatSetting().effectiveDigitLength(fs.effectiveDigitLength);
                                if (fs.effectiveDigitLength) {
                                    data.charFormatSetting().startDigit(fs.startDigit);
                                    data.charFormatSetting().endDigit(fs.endDigit);
                                }
                                data.charFormatSetting().codeEditing(fs.codeEditing);
                                if (fs.codeEditing) {
                                    data.charFormatSetting().codeEditDigit(fs.codeEditDigit);
                                    data.charFormatSetting().codeEditingMethod(fs.codeEditingMethod);
                                }
                                data.charFormatSetting().codeConvertCode(fs.codeConvertCode);
                            }
                            data.numberFormatSetting(null);
                            data.dateFormatSetting(null);
                            data.timeFormatSetting(null);
                            data.instTimeFormatSetting(null);
                            break;
                        case model.ITEM_TYPE.DATE:
                            if (data.dateFormatSetting() == null) {
                                data.dateFormatSetting(new model.DateDataFormatSetting(null, null, null));
                            }
                            data.dateFormatSetting().fixedValue(fs.fixedValue);
                            if (fs.fixedValue) {
                                data.dateFormatSetting().valueOfFixedValue(fs.valueOfFixedValue);
                            } else {
                                data.dateFormatSetting().formatSelection(fs.formatSelection);
                            }
                            data.charFormatSetting(null);
                            data.numberFormatSetting(null);
                            data.timeFormatSetting(null);
                            data.instTimeFormatSetting(null);
                            break;
                        case model.ITEM_TYPE.INS_TIME:
                            if (data.instTimeFormatSetting() == null) {
                                data.instTimeFormatSetting(new model.InstantTimeDataFormatSetting(null, null, null, null, null, null, null, null, null, null));
                            }
                            data.instTimeFormatSetting().fixedValue(fs.fixedValue);
                            if (fs.fixedValue) {
                                data.instTimeFormatSetting().valueOfFixedValue(fs.valueOfFixedValue);
                            } else {
                                data.instTimeFormatSetting().effectiveDigitLength(fs.effectiveDigitLength);
                                if (fs.effectiveDigitLength) {
                                    data.instTimeFormatSetting().startDigit(fs.startDigit);
                                    data.instTimeFormatSetting().endDigit(fs.endDigit);
                                }
                                data.instTimeFormatSetting().decimalSelect(fs.decimalSelect);
                                if (fs.decimalSelect) {
                                    data.instTimeFormatSetting().roundProc(fs.roundProc);
                                    if (fs.roundProc) {
                                        data.instTimeFormatSetting().roundProcCls(fs.roundProcCls);
                                    }
                                } else {
                                    data.instTimeFormatSetting().hourMinSelect(fs.hourMinSelect);
                                }
                                data.instTimeFormatSetting().delimiterSet(fs.delimiterSet);
                            }
                            data.charFormatSetting(null);
                            data.dateFormatSetting(null);
                            data.timeFormatSetting(null);
                            data.numberFormatSetting(null);
                            break;
                        case model.ITEM_TYPE.TIME:
                            if (data.timeFormatSetting() == null) {
                                data.timeFormatSetting(new model.TimeDataFormatSetting(null, null, null, null, null, null, null, null, null, null));
                            }
                            data.timeFormatSetting().fixedValue(fs.fixedValue);
                            if (fs.fixedValue) {
                                data.timeFormatSetting().valueOfFixedValue(fs.valueOfFixedValue);
                            } else {
                                data.timeFormatSetting().effectiveDigitLength(fs.effectiveDigitLength);
                                if (fs.effectiveDigitLength) {
                                    data.timeFormatSetting().startDigit(fs.startDigit);
                                    data.timeFormatSetting().endDigit(fs.endDigit);
                                }
                                data.timeFormatSetting().decimalSelect(fs.decimalSelect);
                                if (fs.decimalSelect) {
                                    data.timeFormatSetting().roundProc(fs.roundProc);
                                    if (fs.roundProc) {
                                        data.timeFormatSetting().roundProcCls(fs.roundProcCls);
                                    }
                                } else {
                                    data.timeFormatSetting().hourMinSelect(fs.hourMinSelect);
                                }
                                data.timeFormatSetting().delimiterSet(fs.delimiterSet);
                            }
                            data.charFormatSetting(null);
                            data.dateFormatSetting(null);
                            data.numberFormatSetting(null);
                            data.instTimeFormatSetting(null);
                            break;
                    }
                }
            });
        }

        openCMF001l(data: model.StandardAcceptItem) {
            let self = this, condition = null;
            if (data.screenConditionSetting()) condition = ko.toJS(data.screenConditionSetting);
            setShared('CMF001lParams', {
                dataType: data.itemType(),
                itemName: data.acceptItemName(),
                condition: condition,
                inputMode: true
            });

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
                self.stdCondSet().characterCode(self.selectedEncoding());
                let command = {conditionSetting: ko.toJS(self.stdCondSet), listItem: ko.toJS(self.listAcceptItem)};
                service.registerDataAndReturn(command).done(() => {
                    self.enableCategory(false);
                    info({ messageId: "Msg_15" }).then(() => {
                        nts.uk.request.jump("/view/cmf/001/b/index.xhtml", {
                            conditionCode: self.stdCondSet().conditionSetCode(),
                            sysType: self.systemType.code
                        });
                    });
                }).fail(function(error) {
                    if (!error.messageId) error.messageId = "Msg_904"; //エラーリストにエラーメッセージがある場合
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
                                                        cond.conditionSetCode, cond.conditionSetName, 
                                                        cond.deleteExistData, cond.acceptMode, 
                                                        cond.csvDataItemLineNumber == null ? 1 : cond.csvDataItemLineNumber, 
                                                        cond.csvDataStartLine == null ? 2 : cond.csvDataStartLine, 
                                                        cond.characterCode,
                                                        cond.deleteExistDataMethod, cond.categoryId));
                    if (cond.characterCode == null)
                        self.selectedEncoding(3);
                    else
                        self.selectedEncoding(cond.characterCode);
                    service.getAllCategory().done((rs: Array<any>) => {
                        if (rs && rs.length) {
                            let _rsList: Array<model.ExternalAcceptanceCategory> = _.map(rs, x => {
                                return new model.ExternalAcceptanceCategory(x.categoryId, x.categoryName);
                            });
                            self.listCategory(_rsList);
                            if (!nts.uk.text.isNullOrEmpty(cond.categoryId)) {
                                self.enableCategory(false);
                                self.selectedCategory(self.stdCondSet().categoryId());
                            } else {
                                self.selectedCategory(self.listCategory()[0].categoryId);
                            }
                            service.getAllData(self.systemType.code, self.stdCondSet().conditionSetCode()).done(function(data: Array<any>) {
                                if (data && data.length) {//co du lieu dang ki
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
                                                            moment.utc(sc.dateConditionValue2, "YYYY/MM/DD").toISOString(), moment.utc(sc.dateConditionValue1, "YYYY/MM/DD").toISOString(), 
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
                                            if (item) {
                                                rs.acceptItemName(item.itemName);
                                                self.listSelectedCategoryItem.push(item);
                                                self.listCategoryItem.remove(item);
                                            }
                                        });
                                    });
                                } else {//chua co du lieu, dang ki moi
                                    self.startLoad(false);
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
                self.stdCondSet().characterCode(self.selectedEncoding());
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
            //check csvDataLineNumber Not input or exceeding the number of lines of CSV data => msg 900
            if (!nts.uk.ntsNumber.isNumber(self.stdCondSet().csvDataItemLineNumber(), false) ||
                self.stdCondSet().csvDataItemLineNumber() == 0 ||
                self.stdCondSet().csvDataItemLineNumber() > self.fileDataTotalLine()) {
                alertError({messageId: "Msg_900"});
                return false;
            }
            //check csvDataStartLine Not input or exceeding the number of lines of CSV data => msg 901
            if (!nts.uk.ntsNumber.isNumber(self.stdCondSet().csvDataStartLine(), false) ||
                self.stdCondSet().csvDataStartLine() == 0 ||
                self.stdCondSet().csvDataStartLine() > self.fileDataTotalLine()) {
                alertError({messageId: "Msg_901"});
                return false;
            }
            return true;
        }
        
        refreshCsvData() {
            let self = this;
            self.setListMappingData(() => {
                _.each(self.listAcceptItem(), rs => {
                    let data = _.find(self.listMappingData(), x => { return x.csvItemNumber == rs.csvItemNumber(); });
                    if (data) {
                        rs.csvItemName(data.csvItemName);
                        rs.sampleData(data.sampleData);
                    }
                });
            });
        }

        private setListMappingData(refreshListAcceptItem: () => void) {
            let self = this;
            if (self.screenFileCheck()) {
                //write data mapping
                block.invisible();
                service.getRecord(self.fileId(), self.stdCondSet().csvDataItemLineNumber(), self.stdCondSet().csvDataStartLine(), self.selectedEncoding()).done((rs: Array<any>) => {
                    let _rsList: Array<model.MappingListData> = _.map(rs, x => {
                        return new model.MappingListData(x.colNum, x.colName, x.sampleData);
                    });
                    self.listMappingData(_rsList);
                    refreshListAcceptItem();
                }).fail(function(error) {
                    //Clear "CSV data item name" and "sample data" on the screen
                    for (var i = 0; i < self.listAcceptItem().length; i++) {
                        self.listAcceptItem()[i].csvItemNumber(null);
                        self.listAcceptItem()[i].csvItemName(null);
                        self.listAcceptItem()[i].sampleData(null);
                    }
                    $('#file-upload').find('.filenamelabel').text('');
                    $('#file-upload').find("input").val('');
                    if (self.isIE()) { //Internet Explorer 6-11 not fire event change
                        $('#file-upload').find("input").change();
                    }
                    self.listMappingData([]);
                    self.fileId(null);
                    if (!error.messageId) error.messageId = "Msg_1158"; //RawErrorMessage
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            } else {
                return;
            }
        }

        /**
         * is Internet Explorer 6-11
         */
        private isIE(): boolean {
            // Internet Explorer 6-11
            let _document: any = document;
            return /*@cc_on!@*/false || !!_document.documentMode;
        }
    }
}

$(function() {
    $("#fixed-table").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.selectedAcceptItem(id);
    })
})