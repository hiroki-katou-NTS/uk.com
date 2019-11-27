module nts.uk.pr.view.qmm016.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import getText = nts.uk.resource.getText;
    import WageTableContent = nts.uk.pr.view.qmm016.share.model.WageTableContent;
    import formatNumber = nts.uk.ntsNumber.formatNumber;
    import NumberEditorOption = nts.uk.ui.option.NumberEditorOption;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    
    const NEW_HIST_ID = "zzzzzz10";
    
    export class ScreenModel {

        // screen state
        addHistoryMode: KnockoutObservable<boolean> = ko.observable(false);
        updateMode: KnockoutObservable<boolean> = ko.observable(false);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);

        // tab panel
        tabs: any;
        selectedTab: any;

        // screen item
        selectedWageTableIdentifier: KnockoutObservable<string> = ko.observable("");
        selectedWageTable: KnockoutObservable<model.WageTable> = ko.observable(new model.WageTable(null));
        selectedHistory: KnockoutObservable<model.GenericHistoryYearMonthPeriod> = ko.observable(new model.GenericHistoryYearMonthPeriod({
            historyID: "",
            startMonth: null,
            endMonth: 999912
        }));
        wageTableTreeList: KnockoutObservableArray<Node> = ko.observableArray([]);
        elementRangeSetting: KnockoutObservable<model.ElementRangeSetting> = ko.observable(new model.ElementRangeSetting(null));
        wageTableContent: KnockoutObservable<model.WageTableContent> = ko.observable(new model.WageTableContent(null));

        // master data
        qualificationInformationData: Array<model.QualificationInformation>;
        qualificationGroupSettingData: Array<model.QualificationGroupSetting>;
        fakeSelectedValue: KnockoutObservable<any> = ko.observable(null);
        listSecondDimension: KnockoutObservableArray<any> = ko.observableArray([]);
        listThirdDimension: KnockoutObservableArray<any> = ko.observableArray([]);
        wageTableContent2dData: KnockoutObservableArray<model.TwoDmsElementItem> = ko.observableArray([]);
        backupTreeList: Array<any> = [];

        constructor() {
            let self = this;
            self.initTabPanel();
            self.selectedWageTableIdentifier.subscribe((newValue) => {
                nts.uk.ui.errors.clearAll();
                if (self.addHistoryMode()) {
                    self.convertToTreeList(self.backupTreeList);
                    self.addHistoryMode(false);
                }
                if (_.isEmpty(newValue)) {
                    self.wageTableContent(new model.WageTableContent(null));
                    self.elementRangeSetting(new model.ElementRangeSetting(null));
                    self.selectedWageTable(new model.WageTable(null));
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod({
                        historyID: "",
                        startMonth: null,
                        endMonth: 999912
                    }));
                    self.selectedTab('tab-1');
                    self.updateMode(false);
                    self.isSelectedHistory(false);
                    self.tabs()[1].enable(false);
                    $("#A5_2").focus();
                } else {
                    self.wageTableContent2dData([]);
                    self.listSecondDimension([]);
                    self.listThirdDimension([]);
                    self.fakeSelectedValue(null);
                    self.elementRangeSetting(new model.ElementRangeSetting(null));
                    self.wageTableContent(new model.WageTableContent(null));
                    if (newValue.indexOf(NEW_HIST_ID) < 0) {
                        self.showWageTableInfoByValue(newValue);
                    }
                    self.updateMode(true);
                    $("#A3_1").focus();
                }
                _.forIn(window.localStorage, (value: string, objKey: string) => {
                    if (true === _.startsWith(objKey, 'ThirdDimension')) {
                        window.localStorage.removeItem(objKey);
                    }
                });
            });
            self.initComponents();
            self.fakeSelectedValue.subscribe((oldValue) => {
                if (oldValue != null && !_.isEmpty(self.wageTableContent()) && !_.isEmpty(self.wageTableContent().payment())) {
                    let i3rdIndex = _.findIndex(self.wageTableContent().payment(), p => {
                        return p.masterCode() == oldValue || p.frameNumber() == oldValue;
                    });
                    if (i3rdIndex >= 0) {
                        self.cacheGridData(i3rdIndex);
                    }
                }
            }, null, "beforeChange");
            self.fakeSelectedValue.subscribe(value => {
                nts.uk.ui.errors.clearAll();
                if (value != null && !_.isEmpty(self.wageTableContent()) && !_.isEmpty(self.wageTableContent().payment())) {
                    let i3rdIndex = _.findIndex(self.wageTableContent().payment(), p => {
                        return p.masterCode() == value || p.frameNumber() == value;
                    });
                    if (i3rdIndex >= 0) {
                        block.invisible();
                        if (self.wageTableContent().brandNew) { // neu la tao moi
                            let gridData = [];
                            let cachedGridData = localStorage.getItem("ThirdDimension" + i3rdIndex);
                            if (cachedGridData) { // neu da get content va luu cache 
                                gridData = JSON.parse(cachedGridData);
                            } else { // chua co cache thi lay data default
                                gridData = self.convertGridDataSource(ko.toJS(self.wageTableContent2dData()));
                            }
                            self.changeGridData(gridData);
                            block.clear();
                        } else { // neu khong phai tao moi
                            let cachedGridData = localStorage.getItem("ThirdDimension" + i3rdIndex);
                            if (cachedGridData) { // neu da get content va luu cache thi khong phai lay data tu server
                                let gridData = JSON.parse(cachedGridData)
                                self.changeGridData(gridData);
                                block.clear();
                            } else { // neu chua get content va luu cache thi lay data tu server
                                let thirdDimension = _.find(self.wageTableContent().payment(), p => {
                                    return p.masterCode() == value || p.frameNumber() == value;
                                });
                                let params = {
                                    wageTableCode: self.selectedWageTable().wageTableCode(),
                                    historyId: self.selectedHistory().historyID(),
                                    thirdMasterCode: thirdDimension.masterCode(),
                                    thirdFrameNumber: thirdDimension.frameNumber()
                                };
                                service.getWageTableByThirdDimension(params).done(queryData => {
                                    let gridData = queryData.list2dElements;
                                    if (!_.isEmpty(gridData)) {
                                        gridData = self.convertGridDataSource(gridData);
                                    } else { // neu khong co data tu server thi lay data default
                                        gridData = self.convertGridDataSource(ko.toJS(self.wageTableContent2dData()));
                                    }
                                    localStorage.setItem("ThirdDimension" + i3rdIndex, JSON.stringify(gridData));
                                    self.changeGridData(gridData);
                                    block.clear();
                                });
                            }
                        }
                    }
                }
            });
        }

        changeGridData(gridData) {
            if ($("#grid2").data("igGrid")) {
                $("#grid2").igGrid("option", "dataSource", gridData);
                if (gridData.length > 10 && $("#grid2").igGridPaging('option', 'currentPageIndex') > 0) {
                    $("#grid2").igGridPaging('option', 'currentPageIndex', 0);
                }
            }
            $("#grid2").ntsGrid("resetOrigDataSource");
        }

        initComponents() {
            let self = this;
            $('#A8_2').ntsFixedTable({width: 300});
            $('.normal-fixed-table').ntsFixedTable({width: 800});
            $("#fixed-table-1d").ntsFixedTable({width: 600, height: 343});
            $('.fixed-table-top').ntsFixedTable({width: 300, height: 34});
            if (/Chrome/.test(navigator.userAgent)) {
                $('.fixed-table-top').ntsFixedTable({width: 300, height: 34});
                $('.fixed-table-body').ntsFixedTable({width: 600, height: 207});
                $('#E5_1').ntsFixedTable({width: 820, height: 344});
            } else {
                $('.fixed-table-body').ntsFixedTable({width: 600, height: 204});
                $('#E5_1').ntsFixedTable({width: 820, height: 341});
            }
        }

        convertToTreeList(wageTableData: Array<model.IWageTable>) {
            let self = this;
            let wageTableTreeData: Array<Node> = wageTableData.map(item => {
                return new Node(item);
            });
            self.wageTableTreeList(wageTableTreeData);
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: getText("QMM016_11"),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: getText("QMM016_12"),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }

        startPage(identifier?: string): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            self.wageTableTreeList([]);
            service.getAllWageTable().done((data: Array<any>) => {
                self.backupTreeList = data;
                if (!_.isEmpty(data)) {
                    self.convertToTreeList(data);
                    if (identifier == null) {
                        identifier = self.wageTableTreeList()[0].histories.length > 0 ? self.wageTableTreeList()[0].histories[0].identifier : self.wageTableTreeList()[0].identifier;
                    }
                    if (self.selectedWageTableIdentifier() == identifier)
                        self.selectedWageTableIdentifier.valueHasMutated();
                    else
                        self.selectedWageTableIdentifier(identifier);

                } else {
                    self.selectedWageTableIdentifier("");
                }
                dfd.resolve();
            }).fail(error => {
                dfd.reject();
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        showWageTableInfoByValue(identifier: string) {
            let self = this;
            let selectedWageTableCode: string = identifier.substring(0, 3);
            block.invisible();
            service.getWageTableByCode(selectedWageTableCode).done((selectedWageTable: model.IWageTable) => {
                self.selectedWageTable(new model.WageTable(selectedWageTable));
                // if select history
                if (identifier.length > 36) {
                    let selectedHistoryID = identifier.substring(3, identifier.length);
                    let selectedHistory = _.find(selectedWageTable.histories, {historyID: selectedHistoryID});
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                    self.isSelectedHistory(true);
                    self.tabs()[1].enable(true);
                    self.showSettingDataByValue(identifier);
                } else {
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
                    self.selectedTab('tab-1');
                    self.isSelectedHistory(false);
                    self.tabs()[1].enable(false);
                    block.clear();
                }
            }).fail(error => {
                dialog.alertError(error);
            });
        }

        showSettingDataByValue(identifier: string): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            if ($("#grid2").data("igGrid")) {
                $("#grid2").igGrid("option", "dataSource", []);
            }
            if (identifier.length > 36) {
                block.invisible();
                let selectedHistoryID = identifier.substring(3, identifier.length);
                if (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.QUALIFICATION) {
                    //Get Wage Table Qualification in screen E
                    service.getWageTableQualification(selectedHistoryID, true).done((result: any) => {
                        let wageTableContent = {
                            historyID: selectedHistoryID,
                            payments: [],
                            qualificationGroupSettings: result
                        };
                        self.wageTableContent(new WageTableContent(wageTableContent));
                        $("#A3_1").focus();
                    }).always(() => {
                        block.clear();
                        dfd.resolve();
                    });
                } else {
                    service.getWageTableContent(selectedHistoryID, identifier.substring(0, 3)).done((contentData) => {
                        self.elementRangeSetting(new model.ElementRangeSetting(contentData.elemRangeSet));
                        if (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.TWO_DIMENSION && !_.isEmpty(contentData.list2dElements)) {
                            let lst2nd: Array<any> = contentData.list2dElements[0].listSecondDms;
                            let items = self.convertGridDataSource(contentData.list2dElements),
                                columns = self.getGridColumns(lst2nd);
                            self.wageTableContent(new model.WageTableContent(contentData));
                            self.displayGrid(items, columns);
                        } else if (!_.isEmpty(contentData.list3dElements) && (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.THREE_DIMENSION
                                || self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.FINE_WORK)) {
                            let lst2nd: Array<any> = contentData.list2dElements[0].listSecondDms;
                            let lst3rd: Array<any> = contentData.list3dElements;
                            let items = !_.isEmpty(lst3rd[0].listFirstDms) ? self.convertGridDataSource(lst3rd[0].listFirstDms) : self.convertGridDataSource(contentData.list2dElements),
                                columns = self.getGridColumns(lst2nd);
                            for (let i = 0; i < lst3rd.length; i++) {
                                if (!_.isEmpty(lst3rd[i].listFirstDms)) {
                                    localStorage.setItem("ThirdDimension" + i, JSON.stringify(self.convertGridDataSource(lst3rd[i].listFirstDms)));
                                    lst3rd[i].listFirstDms = [];
                                }
                            }
                            self.wageTableContent2dData(contentData.list2dElements.map(item => new model.TwoDmsElementItem(item)));
                            self.listThirdDimension(lst3rd.map(i => {
                                if (i.masterCode != null && i.frameNumber != null) {
                                    return {value: i.frameNumber, name: i.frameLowerLimit};
                                } else if (i.frameNumber == null) {
                                    return {value: i.masterCode, name: i.masterName};
                                } else {
                                    return {
                                        value: i.frameNumber,
                                        name: formatNumber(i.frameLowerLimit, new NumberEditorOption({
                                            grouplength: 3,
                                            decimallength: 2
                                        }))
                                        + getText("QMM016_31")
                                        + formatNumber(i.frameUpperLimit, new NumberEditorOption({
                                            grouplength: 3,
                                            decimallength: 2
                                        }))
                                    };
                                }
                            }));
                            self.wageTableContent(new model.WageTableContent(contentData));
                            self.displayGrid(items, columns);
                        } else if (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.ONE_DIMENSION && !_.isEmpty(contentData.list1dElements)) { // one dimension
                            self.wageTableContent(new model.WageTableContent(contentData));
                            self.createOneDimensionTable();
                        }
                    }).fail(error => {
                        dialog.alertError(error);
                    }).always(() => {
                        block.clear();
                        dfd.resolve();
                    });
                }
            } else {
                dfd.resolve();
            }
            return dfd.promise();
        }

        cacheGridData(i) {
            let gridData = $("#grid2").igGrid("option", "dataSource");
            let newUpdatedCells = $("#grid2").ntsGrid("updatedCells");
            if (newUpdatedCells) {
                let cachedUpdatedData = localStorage.getItem("ThirdDimensionUpdate" + i);
                let updatedData = cachedUpdatedData ? JSON.parse(cachedUpdatedData) : [];
                newUpdatedCells.forEach(cell => {
                    let c = _.findIndex(updatedData, u => {
                        return u.rowId == cell.rowId && u.columnKey == cell.columnKey;
                    });
                    if (c >= 0)
                        updatedData[c] = cell;
                    else
                        updatedData.push(cell);
                });
                localStorage.setItem("ThirdDimensionUpdate" + i, JSON.stringify(updatedData));
            }
            localStorage.setItem("ThirdDimension" + i, JSON.stringify(gridData));
            return gridData;
        }

        createNewWageTable() {
            let self = this;
            if (_.isEmpty(self.selectedWageTableIdentifier()))
                self.selectedWageTableIdentifier.valueHasMutated();
            else
                self.selectedWageTableIdentifier("");
        }

        registerWageTable() {
            let self = this, dimensionCheck = true, listErrorMsg = [];
            $(".nts-input").filter(":enabled").trigger("validate");
            setTimeout(() => {
                if ((self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.THREE_DIMENSION
                        || self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.FINE_WORK) && self.updateMode()) {
                    let validators = $("#grid2").data("ntsValidators");
                    for (var i = 0; i < self.wageTableContent().payment().length; i++) {
                        let gridData = [];
                        listErrorMsg = [];
                        // neu la chieu thu 3 dang chon thi lay data tren grid
                        if (self.wageTableContent().payment()[i].masterCode() == self.fakeSelectedValue() || self.wageTableContent().payment()[i].frameNumber() == self.fakeSelectedValue()) {
                            gridData = self.cacheGridData(i);
                        } else { // neu khong phai chieu dang chon thi lay data tu cache
                            let cachedGridData = localStorage.getItem("ThirdDimension" + i);
                            if (cachedGridData) {
                                gridData = JSON.parse(cachedGridData)
                            }
                        }
                        // neu co data thi check xem da nhap dung het chua
                        if (gridData.length > 0) {
                            gridData.forEach(rowData => {
                                for (let property in rowData) {
                                    if (rowData.hasOwnProperty(property) && property.indexOf("secondFrameNo") >= 0) {
                                        let validateResult = validators[property].probe(rowData[property]);
                                        if (!validateResult.isValid && listErrorMsg.indexOf(validateResult.errorMessage) < 0) {
                                            listErrorMsg.push(validateResult.errorMessage)
                                        }
                                    }
                                }
                            });
                            // chua nhap het bang thi chuyen sang chieu thu 3 do va validate
                            if (listErrorMsg.length > 0) {
                                if (self.wageTableContent().payment()[i].masterCode() != self.fakeSelectedValue() && self.wageTableContent().payment()[i].frameNumber() != self.fakeSelectedValue()) {
                                    self.fakeSelectedValue(self.wageTableContent().payment()[i].masterCode() == null ? self.wageTableContent().payment()[i].frameNumber() : self.wageTableContent().payment()[i].masterCode());
                                }
                                dimensionCheck = false;
                                break;
                            }
                        }
                    }
                } else if (!_.isEmpty($("#grid2").ntsGrid("errors"))) {
                    dimensionCheck = false;
                }
                if (!nts.uk.ui.errors.hasError() && dimensionCheck) {
                    if (self.updateMode()) {
                        self.updateData();
                    } else {
                        self.addNewData();
                    }
                } else if (!dimensionCheck) {
                    let message = "";
                    if (listErrorMsg.length > 0) {
                        listErrorMsg.forEach(e => {
                            message += e + '\n';
                        });
                    } else {
                        let errors = $("#grid2").ntsGrid("errors");
                        errors.forEach(e => {
                            if (message.indexOf(e.message) < 0)
                                message += e.message + '\n';
                        });
                    }
                    dialog.alertError({message: message.replace(/undefined/g, "0")}).then(() => {
                        $("#grid2_container").focus();
                    });
                }
            }, 200);
        }

        addNewData() {
            let self = this;
            block.invisible();
            let command = {
                wageTableCode: self.selectedWageTable().wageTableCode(),
                wageTableName: self.selectedWageTable().wageTableName(),
                elementInformation: ko.toJS(self.selectedWageTable().elementInformation),
                elementSetting: self.selectedWageTable().elementSetting(),
                remarkInformation: self.selectedWageTable().remarkInformation(),
                history: ko.toJS(self.selectedHistory)
            }
            if (command.elementSetting == model.ELEMENT_SETTING.FINE_WORK) {
                command.elementInformation.oneDimensionElement.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM;
                command.elementInformation.oneDimensionElement.optionalAdditionalElement = "F204";
                command.elementInformation.twoDimensionElement.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM;
                command.elementInformation.twoDimensionElement.optionalAdditionalElement = "F217";
                command.elementInformation.threeDimensionElement.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.MASTER_FIELD;
                command.elementInformation.threeDimensionElement.fixedElement = "M007";
            }
            service.addNewWageTable(command).done((histId: string) => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    self.startPage(command.wageTableCode + histId);
                });
            }).fail(error => {
                dialog.alertError(error).then(() => {
                    if (error.messageId == "Msg_3")
                        $("#A5_2").focus();
                    if (error.messageId == "MsgQ_242") {
                        if (command.elementInformation.oneDimensionElement.masterNumericClassification == null)
                            $("#A8_5").focus();
                        else if (command.elementInformation.twoDimensionElement.masterNumericClassification == null
                            && (command.elementSetting == model.ELEMENT_SETTING.TWO_DIMENSION
                                || command.elementSetting == model.ELEMENT_SETTING.THREE_DIMENSION)) {
                            $("#A8_8").focus();
                        } else if (command.elementInformation.threeDimensionElement.masterNumericClassification == null
                            && command.elementSetting == model.ELEMENT_SETTING.THREE_DIMENSION) {
                            $("#A8_11").focus();
                        }
                    }
                });
            }).always(() => {
                block.clear();
            });
        }

        updateData() {
            let self = this;
            block.invisible();
            let command = {
                wageTableCode: self.selectedWageTable().wageTableCode(),
                wageTableName: self.selectedWageTable().wageTableName(),
                remarkInformation: self.selectedWageTable().remarkInformation(),
                history: ko.toJS(self.selectedHistory),
                elementRange: ko.toJS(self.elementRangeSetting),
                wageTableContent: {
                    historyID: self.wageTableContent().historyID(),
                    brandNew: self.wageTableContent().brandNew
                }
            }
            switch (self.selectedWageTable().elementSetting()) {
                case model.ELEMENT_SETTING.ONE_DIMENSION:
                    if (command.elementRange.firstElementRange.valueChanged)
                        command.wageTableContent["oneDimensionPayment"] = [];
                    else if (command.wageTableContent.brandNew) {
                        let gridData = $("#grid2").igGrid("option", "dataSource");
                        let payments = ko.toJS(self.wageTableContent().payment());
                        for (let i = 0; i < payments.length; i++) {
                            payments[i].paymentAmount = gridData[i].secondFrameNoX;
                        }
                        command.wageTableContent["oneDimensionPayment"] = payments;
                    } else {
                        let inputData = $("#grid2").ntsGrid("updatedCells");
                        let payments = [];
                        self.wageTableContent().payment().forEach(i => {
                            let tmp = _.find(inputData, c => c.rowId == i.frameNumber() || c.rowId == i.masterCode());
                            if (tmp) {
                                i.paymentAmount(tmp.value);
                                payments.push(i);
                            }
                        });
                        command.wageTableContent["oneDimensionPayment"] = ko.toJS(payments);
                    }
                    break;
                case model.ELEMENT_SETTING.TWO_DIMENSION:
                    if (command.elementRange.firstElementRange.valueChanged || command.elementRange.secondElementRange.valueChanged)
                        command.wageTableContent["twoDimensionPayment"] = [];
                    else if (command.wageTableContent.brandNew) {
                        let gridData = $("#grid2").igGrid("option", "dataSource");
                        command.wageTableContent["twoDimensionPayment"] = self.convertSourceData(gridData, true);
                    } else {
                        let inputData = $("#grid2").ntsGrid("updatedCells");
                        command.wageTableContent["twoDimensionPayment"] = self.convertUpdatedData(ko.toJS(self.wageTableContent().payment()), inputData);
                    }
                    break;
                case model.ELEMENT_SETTING.THREE_DIMENSION:
                    if (command.elementRange.firstElementRange.valueChanged
                        || command.elementRange.secondElementRange.valueChanged
                        || command.elementRange.thirdElementRange.valueChanged) {
                        command.wageTableContent["threeDimensionPayment"] = [];
                    } else {
                        let payments = ko.toJS(self.wageTableContent().payment());
                        for (let i = 0; i < payments.length; i++) {
                            if (command.wageTableContent.brandNew) {
                                let cachedData = localStorage.getItem("ThirdDimension" + i);
                                if (cachedData) {
                                    let data = JSON.parse(cachedData);
                                    payments[i].listFirstDms = self.convertSourceData(data);
                                } else {
                                    payments[i].listFirstDms = ko.toJS(self.wageTableContent2dData());
                                }
                            } else {
                                let cachedInputData = localStorage.getItem("ThirdDimensionUpdate" + i);
                                if (cachedInputData) {
                                    let inputData = JSON.parse(cachedInputData);
                                    payments[i].listFirstDms = self.convertUpdatedData(ko.toJS(self.wageTableContent2dData()), inputData);
                                }
                            }
                        }
                        command.wageTableContent["threeDimensionPayment"] = payments;
                    }
                    break;
                case model.ELEMENT_SETTING.QUALIFICATION:
                    command.wageTableContent.brandNew = true;
                    command.wageTableContent["wageTableQualifications"] = ko.toJS(self.wageTableContent().qualificationGroupSetting());
                    break;
                case model.ELEMENT_SETTING.FINE_WORK:
                    if (command.elementRange.firstElementRange.valueChanged
                        || command.elementRange.secondElementRange.valueChanged) {
                        command.wageTableContent["workLevelPayment"] = [];
                    } else {
                        let payments = ko.toJS(self.wageTableContent().payment());
                        for (let i = 0; i < payments.length; i++) {
                            if (command.wageTableContent.brandNew) {
                                let cachedData = localStorage.getItem("ThirdDimension" + i);
                                if (cachedData) {
                                    let data = JSON.parse(cachedData);
                                    payments[i].listFirstDms = self.convertSourceData(data);
                                } else {
                                    payments[i].listFirstDms = ko.toJS(self.wageTableContent2dData());
                                }
                            } else {
                                let cachedInputData = localStorage.getItem("ThirdDimensionUpdate" + i);
                                if (cachedInputData) {
                                    let inputData = JSON.parse(cachedInputData);
                                    payments[i].listFirstDms = self.convertUpdatedData(ko.toJS(self.wageTableContent2dData()), inputData);
                                }
                            }
                        }
                        command.wageTableContent["workLevelPayment"] = payments;
                    }
                    break;
                default:
                    break;
            }
            service.updateWageTable(command).done((historyId: string) => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    if (self.addHistoryMode()) self.addHistoryMode(false);
                    self.startPage(command.wageTableCode + historyId);
                });
            }).fail(error => {
                dialog.alertError(error).then(() => {
                    if (error.messageId == "MsgQ_153" || error.messageId == "MsgQ_154" || error.messageId == "MsgQ_155") {
                        switch (self.selectedWageTable().elementSetting()) {
                            case model.ELEMENT_SETTING.ONE_DIMENSION:
                                $("#B2_8").focus();
                                break;
                            case model.ELEMENT_SETTING.TWO_DIMENSION:
                                if (error.messageId == "MsgQ_153")
                                    $("#C2_8").focus();
                                else
                                    $("#C2_15").focus();
                                break;
                            case model.ELEMENT_SETTING.THREE_DIMENSION:
                                if (error.messageId == "MsgQ_153")
                                    $("#D2_8").focus();
                                else if (error.messageId == "MsgQ_154")
                                    $("#D2_15").focus();
                                else
                                    $("#D2_22").focus();
                                break;
                            case model.ELEMENT_SETTING.FINE_WORK:
                                if (error.messageId == "MsgQ_153")
                                    $("#F2_8").focus();
                                else
                                    $("#F2_15").focus();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }).always(() => {
                block.clear();
            });
        }

        settingQualificationGroup() {
            let self = this;
            modal("/view/qmm/016/h/index.xhtml").onClosed(() => {

            });
        }

        createNewHistory() {
            let self = this;
            let selectedWageTable = ko.toJS(self.selectedWageTable);
            let history = selectedWageTable.histories;
            setShared("QMM016_I_PARAMS", {selectedWageTable: selectedWageTable, history: history});
            modal("/view/qmm/016/i/index.xhtml").onClosed(() => {
                let params = getShared("QMM016_I_RES_PARAMS");
                if (params) {
                    let wageTableTreeList = ko.toJS(self.wageTableTreeList);
                    let newHistoryID = NEW_HIST_ID; //nts.uk.util.randomId();
                    let latestHistoryID = "";
                    // update previous history
                    if (history.length > 0) {
                        history[0].endMonth = params.startMonth - 1;
                        latestHistoryID = history[0].historyID;
                    }
                    // add new history
                    let newHistory = {historyID: newHistoryID, startMonth: params.startMonth, endMonth: 999912};
                    history.unshift(newHistory);
                    wageTableTreeList.forEach(wageTable => {
                        if (wageTable.wageTableCode == selectedWageTable.wageTableCode) {
                            wageTable.histories = history;
                            wageTable = new model.WageTable(wageTable);
                        }
                    });
                    // update wage table and tree grid
                    self.convertToTreeList(wageTableTreeList);
                    self.selectedWageTableIdentifier(selectedWageTable.wageTableCode + newHistoryID);

                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(newHistory));
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_LAST_HISTORY && history.length > 0) {
                        // get previous history's wage table and set brandNew = true
                        self.showSettingDataByValue(selectedWageTable.wageTableCode + latestHistoryID).done(() => {
                            self.wageTableContent().brandNew = true;
                        });
                    } else {
                        self.elementRangeSetting(new model.ElementRangeSetting(null));
                        self.wageTableContent(new WageTableContent(null));
                    }
                    self.addHistoryMode(true);
                }
            });
        }

        editHistory() {
            let self = this;
            let selectedWageTable = ko.toJS(self.selectedWageTable), selectedHistory = ko.toJS(self.selectedHistory);
            let history = selectedWageTable.histories;
            setShared("QMM016_J_PARAMS", {
                selectedWageTable: selectedWageTable,
                history: history,
                selectedHistory: selectedHistory
            });
            modal("/view/qmm/016/j/index.xhtml").onClosed(() => {
                let params = getShared("QMM016_J_RES_PARAMS");
                if (params) {
                    block.invisible();
                    self.startPage(params.historyId ? self.selectedWageTable().wageTableCode() + params.historyId : null);
                }
            });
        }

        selectElement(dimension) {
            let self = this, selected = null, otherSelected = [],
                elemInfo = ko.toJS(self.selectedWageTable().elementInformation());
            if (dimension == 1) {
                selected = elemInfo.oneDimensionElement.fixedElement ? elemInfo.oneDimensionElement.fixedElement : elemInfo.oneDimensionElement.optionalAdditionalElement;
                if (elemInfo.twoDimensionElement.fixedElement || elemInfo.twoDimensionElement.optionalAdditionalElement)
                    otherSelected.push(elemInfo.twoDimensionElement.fixedElement ? elemInfo.twoDimensionElement.fixedElement : elemInfo.twoDimensionElement.optionalAdditionalElement);
                if (elemInfo.threeDimensionElement.fixedElement || elemInfo.threeDimensionElement.optionalAdditionalElement)
                    otherSelected.push(elemInfo.threeDimensionElement.fixedElement ? elemInfo.threeDimensionElement.fixedElement : elemInfo.threeDimensionElement.optionalAdditionalElement);
            }
            if (dimension == 2) {
                selected = elemInfo.twoDimensionElement.fixedElement ? elemInfo.twoDimensionElement.fixedElement : elemInfo.twoDimensionElement.optionalAdditionalElement;
                if (elemInfo.oneDimensionElement.fixedElement || elemInfo.oneDimensionElement.optionalAdditionalElement)
                    otherSelected.push(elemInfo.oneDimensionElement.fixedElement ? elemInfo.oneDimensionElement.fixedElement : elemInfo.oneDimensionElement.optionalAdditionalElement);
                if (elemInfo.threeDimensionElement.fixedElement || elemInfo.threeDimensionElement.optionalAdditionalElement)
                    otherSelected.push(elemInfo.threeDimensionElement.fixedElement ? elemInfo.threeDimensionElement.fixedElement : elemInfo.threeDimensionElement.optionalAdditionalElement);
            }
            if (dimension == 3) {
                selected = elemInfo.threeDimensionElement.fixedElement ? elemInfo.threeDimensionElement.fixedElement : elemInfo.threeDimensionElement.optionalAdditionalElement;
                if (elemInfo.oneDimensionElement.fixedElement || elemInfo.oneDimensionElement.optionalAdditionalElement)
                    otherSelected.push(elemInfo.oneDimensionElement.fixedElement ? elemInfo.oneDimensionElement.fixedElement : elemInfo.oneDimensionElement.optionalAdditionalElement);
                if (elemInfo.twoDimensionElement.fixedElement || elemInfo.twoDimensionElement.optionalAdditionalElement)
                    otherSelected.push(elemInfo.twoDimensionElement.fixedElement ? elemInfo.twoDimensionElement.fixedElement : elemInfo.twoDimensionElement.optionalAdditionalElement);
            }
            setShared("QMM016_G_PARAMS", {selected: selected, otherSelected: otherSelected});
            modal("/view/qmm/016/g/index.xhtml").onClosed(() => {
                let params: any = getShared("QMM016_G_RES_PARAMS");
                if (params) {
                    let selectedElement: model.IElementAttribute = params.selectedElement;
                    if (dimension == 1)
                        self.selectedWageTable().elementInformation().oneDimensionElement(new model.ElementAttribute(selectedElement));
                    if (dimension == 2)
                        self.selectedWageTable().elementInformation().twoDimensionElement(new model.ElementAttribute(selectedElement));
                    if (dimension == 3)
                        self.selectedWageTable().elementInformation().threeDimensionElement(new model.ElementAttribute(selectedElement));
                }
            });
        }

        createOneDimensionWageTable() {
            // B2_8、B2_10、B2_11のエラーチェックを行う
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.wageTableContent(new model.WageTableContent(null));
            let params = {
                historyID: self.selectedHistory().historyID(),
                wageTableCode: self.selectedWageTable().wageTableCode(),
                firstElementRange: null,
                secondElementRange: null,
                thirdElementRange: null
            };
            if (self.selectedWageTable().elementInformation().oneDimensionElement().masterNumericClassification()
                == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
                params.firstElementRange = self.getElementRange(firstElementRange, $("#B2_8"), $("#B2_10"), $("#B2_11"));
                if (params.firstElementRange == null) {
                    return;
                }
            }
            block.invisible();
            service.createOneDimentionWageTable(params).done(data => {
                if (!_.isEmpty(data)) {
                    self.elementRangeSetting().historyID(params.historyID);
                    self.elementRangeSetting().firstElementRange().valueChanged = false;
                    self.elementRangeSetting().secondElementRange(null);
                    self.elementRangeSetting().thirdElementRange(null);
                    self.wageTableContent(new model.WageTableContent(data));
                    self.createOneDimensionTable();
                    $("#grid2_container").focus();
                }
            }).fail(error => {
                dialog.alertError(error).then(() => {
                    if (error.messageId == "MsgQ_250")
                        $("#B2_8").focus();
                });
            }).always(() => {
                block.clear();
            });
        }

        createTwoDimensionWageTable() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.wageTableContent(new model.WageTableContent(null));
            self.listSecondDimension([]);
            let params = {
                historyID: self.selectedHistory().historyID(),
                wageTableCode: self.selectedWageTable().wageTableCode(),
                firstElementRange: null,
                secondElementRange: null,
                thirdElementRange: null
            };
            if (self.selectedWageTable().elementInformation().oneDimensionElement().masterNumericClassification()
                == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                // C2_8、C2_10、C2_11の状態を取得
                let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
                params.firstElementRange = self.getElementRange(firstElementRange, $("#C2_8"), $("#C2_10"), $("#C2_11"));
                if (params.firstElementRange == null) {
                    return;
                }
            }
            if (self.selectedWageTable().elementInformation().twoDimensionElement().masterNumericClassification()
                == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                // C2_15、C2_17、C2_18の状態を取得
                let secondElementRange = ko.toJS(self.elementRangeSetting).secondElementRange;
                params.secondElementRange = self.getElementRange(secondElementRange, $("#C2_15"), $("#C2_17"), $("#C2_18"));
                if (params.secondElementRange == null) {
                    return;
                }
            }
            block.invisible();
            service.createTwoDimentionWageTable(params).done((data: any) => {
                if (!_.isEmpty(data)) {
                    self.elementRangeSetting().historyID(params.historyID);
                    self.elementRangeSetting().firstElementRange().valueChanged = false;
                    self.elementRangeSetting().secondElementRange().valueChanged = false;
                    self.elementRangeSetting().thirdElementRange(null);
                    let lst2nd: Array<any> = data.list2dElements[0].listSecondDms;
                    let items = self.convertGridDataSource(data.list2dElements), columns = self.getGridColumns(lst2nd);
                    self.wageTableContent(new model.WageTableContent(data));
                    self.displayGrid(items, columns);
                    $("#grid2_container").focus();
                }
            }).fail(error => {
                dialog.alertError(error).then(() => {
                    if (error.messageId == "MsgQ_251")
                        $("#C2_8").focus();
                    if (error.messageId == "MsgQ_252")
                        $("#C2_15").focus();
                });
            }).always(() => {
                block.clear();
            });
        }

        createThreeDimensionWageTable(isWorkLevel?: boolean) {
            let self = this;
            nts.uk.ui.errors.clearAll();
            if ($("#grid2").data("igGrid")) {
                $("#grid2").igGrid("option", "dataSource", []);
            }
            self.wageTableContent(new model.WageTableContent(null));
            self.listThirdDimension([]);
            self.wageTableContent2dData([]);
            self.fakeSelectedValue(null);
            _.forIn(window.localStorage, (value: string, objKey: string) => {
                if (true === _.startsWith(objKey, 'ThirdDimension')) {
                    window.localStorage.removeItem(objKey);
                }
            });
            let params = {
                historyID: self.selectedHistory().historyID(),
                wageTableCode: self.selectedWageTable().wageTableCode(),
                firstElementRange: null,
                secondElementRange: null,
                thirdElementRange: null
            };
            if (isWorkLevel == true) {
                // F2_8、F2_10、F2_11の状態を取得
                let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
                params.firstElementRange = self.getElementRange(firstElementRange, $("#F2_8"), $("#F2_10"), $("#F2_11"));
                if (params.firstElementRange == null) {
                    return;
                }
    
                // F2_15、F2_17、F2_18の状態を取得
                let secondElementRange = ko.toJS(self.elementRangeSetting).secondElementRange;
                params.secondElementRange = self.getElementRange(secondElementRange, $("#F2_15"), $("#F2_17"), $("#F2_18"));
                if (params.secondElementRange == null) {
                    return;
                }
            } else {
                if (self.selectedWageTable().elementInformation().oneDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                    // D2_8、D2_10、D2_11の状態を取得
                    let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
                    params.firstElementRange = self.getElementRange(firstElementRange, $("#D2_8"), $("#D2_10"), $("#D2_11"));
                    if (params.firstElementRange == null) {
                        return;
                    }
                }
                if (self.selectedWageTable().elementInformation().twoDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                    // D2_15、D2_17、D2_18の状態を取得
                    let secondElementRange = ko.toJS(self.elementRangeSetting).secondElementRange;
                    params.secondElementRange = self.getElementRange(secondElementRange, $("#D2_15"), $("#D2_17"), $("#D2_18"));
                    if (params.secondElementRange == null) {
                        return;
                    }
                }
                if (self.selectedWageTable().elementInformation().threeDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                    // D2_22、D2_24、D2_25の状態を取得
                    let thirdElementRange = ko.toJS(self.elementRangeSetting).thirdElementRange;
                    params.thirdElementRange = self.getElementRange(thirdElementRange, $("#D2_22"), $("#D2_24"), $("#D2_25"));
                    if (params.thirdElementRange == null) {
                        return;
                    }
                }
            }
            block.invisible();
            service.createThreeDimentionWageTable(params).done(data => {
                if (!_.isEmpty(data)) {
                    self.elementRangeSetting().historyID(params.historyID);
                    self.elementRangeSetting().firstElementRange().valueChanged = false;
                    self.elementRangeSetting().secondElementRange().valueChanged = false;
                    self.elementRangeSetting().thirdElementRange().valueChanged = false;
                    let lst2nd: Array<any> = data.list2dElements[0].listSecondDms;
                    let items = self.convertGridDataSource(data.list2dElements), columns = self.getGridColumns(lst2nd);
                    self.wageTableContent2dData(data.list2dElements.map(item => new model.TwoDmsElementItem(item)));
                    let lst3rd: Array<any> = data.list3dElements;
                    for (let index = 0; index < lst3rd.length; index++) {
                        localStorage.setItem("ThirdDimension" + index, JSON.stringify(self.convertGridDataSource(data.list2dElements)));
                        let i = lst3rd[index], item = {};
                        if (i.masterCode != null && i.frameNumber != null) {
                            item = {value: i.frameNumber, name: i.frameLowerLimit + ""};
                        } else if (i.frameNumber == null) {
                            item = {value: i.masterCode, name: i.masterName};
                        } else {
                            item = {
                                value: i.frameNumber,
                                name: formatNumber(i.frameLowerLimit, new NumberEditorOption({
                                    grouplength: 3,
                                    decimallength: 2
                                }))
                                + getText("QMM016_31")
                                + formatNumber(i.frameUpperLimit, new NumberEditorOption({
                                    grouplength: 3,
                                    decimallength: 2
                                }))
                            };
                        }
                        self.listThirdDimension.push(item);
                    }
                    self.wageTableContent(new model.WageTableContent(data));
                    self.displayGrid(items, columns);
                    $("#grid2_container").focus();
                }
            }).fail(error => {
                dialog.alertError(error).then(() => {
                    if (error.messageId == "MsgQ_251") {
                        if (isWorkLevel == true) {
                            $("#F2_8").focus();
                        } else {
                            $("#D2_8").focus();
                        }
                    }
                    if (error.messageId == "MsgQ_252") {
                        if (isWorkLevel == true) {
                            $("#F2_15").focus();
                        } else {
                            $("#D2_15").focus();
                        }
                    }
                    if (error.messageId == "MsgQ_253") {
                        $("#D2_22").focus();
                    }
                });
            }).always(() => {
                block.clear();
            });
        }

        createWageTableQualification() {
            let self = this,
                historyId = self.selectedHistory().historyID();
            nts.uk.ui.errors.clearAll();
            service.getWageTableQualification(historyId, false).done((result: any) => {
                let wageTableContent = {
                    historyID: historyId,
                    payments: [],
                    qualificationGroupSettings: result
                };
                self.wageTableContent(new WageTableContent(wageTableContent));
                self.wageTableContent().brandNew = true;
                if (!_.isEmpty(result))
                    $(".input-amount")[0].focus();
            });
        }

        createWorkLevelWageTable() {
            let self = this;
            self.createThreeDimensionWageTable(true);
        }

        getElementRange(elementRange, controlLower, controlUpper, controlStep): any {
            if (!nts.uk.ntsNumber.isNumber(elementRange.rangeLowerLimit, true)) {
                dialog.alertError({messageId: 'MsgQ_3'}).then(() => {
                    controlLower.focus();
                });
                return null;
            }
            if (!nts.uk.ntsNumber.isNumber(elementRange.rangeUpperLimit, true)) {
                dialog.alertError({messageId: 'MsgQ_3'}).then(() => {
                    controlUpper.focus();
                });
                return null;
            }
            if (!nts.uk.ntsNumber.isNumber(elementRange.stepIncrement, true)) {
                dialog.alertError({messageId: 'MsgQ_3'}).then(() => {
                    controlStep.focus();
                });
                return null;
            }
            if (Number(elementRange.rangeLowerLimit) > Number(elementRange.rangeUpperLimit)) {
                dialog.alertError({messageId: 'MsgQ_3'}).then(() => {
                    controlLower.focus();
                });
                return null;
            }
            return elementRange;
        }

        displayGrid(dataSource: Array<any>, columns: Array<any>, isOneDimension?: boolean) {
            let columnResizeSettings = [
                {columnKey: 'firstFrameNo', allowResizing: false},
                {columnKey: 'firstFrameName', allowResizing: false}
            ];
            if (isOneDimension) {
                columnResizeSettings.push({columnKey: 'secondFrameNoX', allowResizing: false});
            }
            let features = [
                {
                    name: 'Resizing',
                    columnSettings: columnResizeSettings
                },
                {
                    name: 'ColumnFixing', fixingDirection: 'left',
                    showFixButtons: false,
                    columnSettings: [
                        {columnKey: 'firstFrameNo', isFixed: true},
                        {columnKey: 'firstFrameName', isFixed: true}
                    ]
                }
            ];
            if (dataSource.length > 10) {
                features.push({name: 'Paging', pageSize: 10, showPageSizeDropDown: false, currentPageIndex: 0});
            }
            $("#grid2").ntsGrid({
                width: '750px',
                height: dataSource.length > 10 ? '397px' : '350px',
                dataSource: dataSource,
                primaryKey: 'firstFrameNo',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                preventEditInError: false,
                hidePrimaryKey: true,
                columns: columns,
                features: features,
                ntsFeatures: [
                    {name: 'CopyPaste'},
                    {name: 'CellEdit'},
                    {
                        name: "Sheet",
                        initialDisplay: "sheet1",
                        sheets: [
                            {name: "sheet1", text: "Sheet 1", columns: columns.slice(2).map(i => i.key)}
                        ]
                    },
                    {
                        name: "CellState",
                        rowId: "rowId",
                        columnKey: "columnKey",
                        state: "state",
                        states: []
                    }
                ],
                ntsControls: []
            });
            $("#grid2").igGridResizing("resize", columns[columns.length - 1].key, 165);
        }

        convertGridDataSource(data: Array<any>): Array<any> {
            let items = [];
            data.forEach(d => {
                let columnKey = d.masterCode, columnName = d.masterName;
                if (d.frameNumber != null) {
                    columnKey = d.frameNumber;
                    columnName = formatNumber(d.frameLowerLimit, new NumberEditorOption({
                            grouplength: 3,
                            decimallength: 2
                        }))
                        + getText("QMM016_31")
                        + formatNumber(d.frameUpperLimit, new NumberEditorOption({grouplength: 3, decimallength: 2}));
                }
                let rowData = {firstFrameNo: columnKey + "", firstFrameName: columnName};
                d.listSecondDms.forEach(t => {
                    let columnKey2 = "secondFrameNo" + t.masterCode;
                    if (t.frameNumber != null) {
                        columnKey2 = "secondFrameNo" + t.frameNumber;
                    }
                    rowData[columnKey2] = t.paymentAmount == null ? null : t.paymentAmount + "";
                });
                items.push(rowData);
            });
            return items;
        }

        convertSourceData(gridData: Array<any>, twoDimension?: boolean): Array<any> {
            if (_.isEmpty(gridData))
                return [];
            let self = this;
            let data = twoDimension ? ko.toJS(self.wageTableContent().payment()) : ko.toJS(self.wageTableContent2dData());
            for (let i = 0; i < data.length; i++) {
                let rowData = gridData[i];
                if (rowData) {
                    data[i].listSecondDms.forEach(c => {
                        if (c.masterCode) {
                            c.paymentAmount = rowData["secondFrameNo" + c.masterCode];
                        } else {
                            c.paymentAmount = rowData["secondFrameNo" + c.frameNumber];
                        }
                    });
                }
            }
            return data;
        }

        convertUpdatedData(data: Array<any>, inputData: Array<any>): Array<any> {
            if (_.isEmpty(data) || _.isEmpty(inputData))
                return [];
            let self = this;
            let result = [];
            for (let i = 0; i < data.length; i++) {
                let tmp = inputData.filter(c => c.rowId == data[i].frameNumber || c.rowId == data[i].masterCode);
                if (tmp.length > 0) {
                    let r = [];
                    data[i].listSecondDms.forEach(c => {
                        let value = _.find(tmp, t => t.columnKey == "secondFrameNo" + c.masterCode || t.columnKey == "secondFrameNo" + c.frameNumber);
                        if (value) {
                            c.paymentAmount = value.value;
                            ;
                            r.push(c);
                        }
                    });
                    if (r.length > 0) {
                        data[i].listSecondDms = r;
                        result.push(data[i]);
                    }
                }
            }
            return result;
        }

        getGridColumns(data: Array<any>): Array<any> {
            let self = this;
            let elementName = self.selectedWageTable().elementInformation().oneDimensionElement().elementName();
            let columns: Array<any> = [
                {
                    headerText: 'ID', key: 'firstFrameNo', dataType: 'string',
                    width: '60px', ntsControl: 'Label', hidden: false
                },
                {
                    headerText: isNullOrUndefined(elementName) ? "" : elementName,
                    key: 'firstFrameName', dataType: 'string', width: '153px',
                    ntsControl: 'Label', columnCssClass: 'custom-ntsgrid-header limited-label',
                    headerCssClass: 'custom-ntsgrid-header'
                }
            ];
            data.forEach(i => {
                let columnKey = "secondFrameNo" + i.masterCode, columnName = _.escape(i.masterName);
                if (i.frameNumber != null) {
                    columnKey = "secondFrameNo" + i.frameNumber;
                    columnName = formatNumber(i.frameLowerLimit, new NumberEditorOption({
                            grouplength: 3,
                            decimallength: 2
                        }))
                        + getText("QMM016_31")
                        + formatNumber(i.frameUpperLimit, new NumberEditorOption({grouplength: 3, decimallength: 2}));
                }
                let column = {
                    headerText: columnName,
                    key: columnKey,
                    dataType: 'string',
                    width: '165px',
                    columnCssClass: 'currency-symbol halign-right',
                    headerCssClass: "custom-ntsgrid-header",
                    constraint: {
                        min: -9999999999,
                        max: 9999999999,
                        decimalLength: 0,
                        integer: true,
                        cDisplayType: "Currency"
                    }
                };
                columns.push(column);
            });
            return columns;
        }

        createOneDimensionTable() {
            let self = this;
            let columns: Array<any> = [
                {
                    headerText: 'ID', key: 'firstFrameNo', dataType: 'string',
                    width: '60px', ntsControl: 'Label', hidden: false
                },
                {
                    headerText: self.selectedWageTable().elementInformation().oneDimensionElement().elementName(),
                    key: 'firstFrameName', dataType: 'string', width: '153px', ntsControl: 'Label',
                    columnCssClass: 'custom-ntsgrid-header limited-label', headerCssClass: 'custom-ntsgrid-header'
                },
                {
                    headerText: getText("QMM016_38"),
                    key: 'secondFrameNoX',
                    dataType: 'string',
                    width: '165px',
                    columnCssClass: 'currency-symbol halign-right',
                    headerCssClass: "custom-ntsgrid-header",
                    constraint: {
                        min: -9999999999,
                        max: 9999999999,
                        decimalLength: 0,
                        integer: true,
                        cDisplayType: "Currency"
                    }
                }
            ];
            let items = self.wageTableContent().payment().map(i => {
                return {
                    firstFrameNo: i.masterCode() ? i.masterCode() : i.frameNumber() + "",
                    firstFrameName: i.displayText(),
                    secondFrameNoX: i.paymentAmount()
                };
            });
            self.displayGrid(items, columns, true);
        }


        exportExcel() {
            let params = {
                date: null,
                mode: 6
            };

            nts.uk.ui.windows.setShared("CDL028_INPUT", params);
            nts.uk.ui.windows.sub.modal('com', '/view/cdl/028/a/index.xhtml').onClosed(() => {
                var result = nts.uk.ui.windows.getShared('CDL028_A_PARAMS');
                if (result.status) {
                    nts.uk.ui.block.grayout();
                    let startDate = result.standardYearMonth;
                    let data = {
                        startDate: startDate
                    }
                    service.exportExcel(data).done(function () {
                        //nts.uk.ui.windows.close();
                    }).fail(function (error) {
                        nts.uk.ui.dialog.alertError({messageId: error.messageId});
                    }).always(function () {
                        nts.uk.ui.block.clear();
                    });
                }
            });
        }
    }

    class Node {
        wageTableCode: string;
        wageTableName: string;
        histories: Array<any>;
        identifier: string;
        nodeText: string;

        constructor(params: model.IWageTable) {
            this.wageTableCode = params.wageTableCode;
            this.wageTableName = params.wageTableName;
            this.histories = params.histories.map((historyItem: any) => {
                historyItem.nodeText = nts.uk.time.formatYearMonth(historyItem.startMonth) + " " + getText("QMM016_31") + " " + nts.uk.time.formatYearMonth(historyItem.endMonth);
                historyItem.identifier = params.wageTableCode + historyItem.historyID;
                // prevent handler from null value exception when use search box
                historyItem.wageTableCode = "";
                historyItem.wageTableName = "";
                return historyItem;
            });
            this.identifier = params.wageTableCode;
            this.nodeText =  _.escape(params.wageTableCode + " " + params.wageTableName);
        }
    }
    
}

