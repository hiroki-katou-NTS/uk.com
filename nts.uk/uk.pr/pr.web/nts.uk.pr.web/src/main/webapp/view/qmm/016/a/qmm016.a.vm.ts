module nts.uk.pr.view.qmm016.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import getText = nts.uk.resource.getText;
    import WageTableContent = nts.uk.pr.view.qmm016.share.model.WageTableContent;
    
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
        selectedHistory: KnockoutObservable<model.GenericHistoryYearMonthPeriod> = ko.observable(new model.GenericHistoryYearMonthPeriod({historyID: "", startMonth: null, endMonth: 999912}));
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
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod({historyID: "", startMonth: null, endMonth: 999912}));
                    self.selectedTab('tab-1');
                    self.updateMode(false);
                    self.isSelectedHistory(false);
                    $("#A5_2").focus();
                } else {
                    self.wageTableContent2dData([]);
                    self.listSecondDimension([]);
                    self.listThirdDimension([])
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
                    let i3rdIndex = _.findIndex(self.wageTableContent().payment(), p => {return p.masterCode() == oldValue || p.frameNumber() == oldValue;} );
                    if (i3rdIndex >= 0) {
                        if (!_.isEmpty(self.wageTableContent().payment()[i3rdIndex].listFirstDms)) {
                            let data = self.wageTableContent2dData(),
                                isEmptyAll = true;
                            for (let i = 0; i < data.length; i++) {
                                let isBreak = false;
                                for (let j = 0; j < data[i].listSecondDms().length; j++) {
                                    if (self.wageTableContent2dData()[i].listSecondDms()[j].paymentAmount() != null) {
                                        isBreak = true;
                                        break;
                                    }
                                }
                                if (isBreak) {
                                    isEmptyAll = false;
                                    break;
                                }
                            }
                            if (isEmptyAll)
                                localStorage.removeItem("ThirdDimension" + i3rdIndex);
                            else
                                localStorage.setItem("ThirdDimension" + i3rdIndex, JSON.stringify(ko.toJS(data)));
                        }
                    } 
                }
            }, null, "beforeChange");
            self.fakeSelectedValue.subscribe(value => {
                nts.uk.ui.errors.clearAll();
                if (value != null && !_.isEmpty(self.wageTableContent()) && !_.isEmpty(self.wageTableContent().payment())) {
                    let i3rdIndex = _.findIndex(self.wageTableContent().payment(), p => {return p.masterCode() == value || p.frameNumber() == value;} );
                    if (i3rdIndex >= 0) {
                        block.invisible();
                        let cachedData = localStorage.getItem("ThirdDimension" + i3rdIndex);
                        let data = cachedData ? JSON.parse(cachedData).map(item => new model.TwoDmsElementItem(item)) : [];
                        if (data.length > 0) {
                            for (let i = 0; i < self.wageTableContent2dData().length; i++) {
                                let rowData = data[i].listSecondDms();
                                for (let j = 0; j < self.wageTableContent2dData()[i].listSecondDms().length; j++) {
                                    self.wageTableContent2dData()[i].listSecondDms()[j].paymentAmount(rowData[j].paymentAmount());
                                }
                            }
                        } else {
                            self.wageTableContent2dData().forEach(r => {
                                r.listSecondDms().forEach(c => {
                                    c.paymentAmount(null);
                                });
                            });
                        }
                        block.clear();
						$(".input-amount")[0].focus();
                    }
                }
            }); 
        }

        initComponents() {
            let self = this;
            $('#A8_2').ntsFixedTable({ width: 300 });
            $('.normal-fixed-table').ntsFixedTable({ width: 600 });
            $("#fixed-table-1d").ntsFixedTable({ width: 600, height: 343 });
            $('.fixed-table-top').ntsFixedTable({ width: 300, height: 34 });
            if (/Chrome/.test(navigator.userAgent)) {
                $('.fixed-table-top').ntsFixedTable({ width: 300, height: 34 });
                $('.fixed-table-body').ntsFixedTable({ width: 600, height: 207 });
                $('#E5_1').ntsFixedTable({ width: 800, height: 344 });
            } else {
                $('.fixed-table-body').ntsFixedTable({ width: 600, height: 204 });
                $('#E5_1').ntsFixedTable({ width: 800, height: 341 });
            }
        }

        convertToTreeList(wageTableData: Array<model.IWageTable>) {
            let self = this;
            let wageTableTreeData: Array<Node> = wageTableData.map(item => {
                return new Node(item);
            })
            self.wageTableTreeList(wageTableTreeData);
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText("QMM016_11"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText("QMM016_12"), content: '.tab-content-2', enable: ko.computed(() => { return self.updateMode() && self.isSelectedHistory(); }), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }
        
        syncScroll(source, follow1, follow2) {
            let self = this;
            source.scroll((e) => {
                follow1.scrollTop(source.scrollTop()); 
                follow2.scrollLeft(source.scrollLeft());
            });
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
                    let selectedHistory = _.find(selectedWageTable.histories, { historyID: selectedHistoryID });
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                    self.isSelectedHistory(true);
                    self.showSettingDataByValue(identifier);
                } else {
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
                    self.selectedTab('tab-1');
                    self.isSelectedHistory(false);
                }
            }).fail(error => {
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        showSettingDataByValue(identifier: string) {
            let self = this;
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
                    }).always(() => {
                        block.clear();
                    });
                } else {
					service.getWageTableContent(selectedHistoryID, identifier.substring(0, 3)).done((contentData) => {
						if (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.TWO_DIMENSION && !_.isEmpty(contentData.list2dElements)) {
							let lst2nd: Array<any> = contentData.list2dElements[0].listSecondDms;
							self.listSecondDimension(lst2nd.map(i => {
								if (i.masterCode) {
									return {value: i.masterCode, name: i.masterName};
								} else {
									return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
								}
							}));
						} else if (!_.isEmpty(contentData.list3dElements) && (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.THREE_DIMENSION 
                                    || self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.FINE_WORK)) {
							let lst2nd: Array<any> = contentData.list2dElements[0].listSecondDms;
                            let lst3rd: Array<any> = contentData.list3dElements;
                            for (let i = 0; i < lst3rd.length; i++) {
                                if (!_.isEmpty(lst3rd[i].listFirstDms))
                                    localStorage.setItem("ThirdDimension" + i, JSON.stringify(lst3rd[i].listFirstDms));
                            }
                            if (self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.THREE_DIMENSION) {
                                self.listThirdDimension(lst3rd.map(i => {
                                    if (i.masterCode) {
                                        return {value: i.masterCode, name: i.masterName};
                                    } else {
                                        return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                                    }
                                }));
                            } else {
                                self.listThirdDimension(lst3rd.map(i => {
                                    return {value: i.frameNumber, name: i.frameLowerLimit};
                                }));
                            }
							self.listSecondDimension(lst2nd.map(i => {
								if (i.masterCode) {
									return {value: i.masterCode, name: i.masterName};
								} else {
									return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
								}
							}));
                            self.wageTableContent2dData(contentData.list2dElements.map(item => new model.TwoDmsElementItem(item)));
						}
						self.wageTableContent(new model.WageTableContent(contentData));
						self.elementRangeSetting(new model.ElementRangeSetting(contentData.elemRangeSet));
						if (!_.isEmpty(contentData.list3dElements)) {
							$("#content-wrapper").unbind();
                            self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
						}
					}).fail(error => {
						dialog.alertError(error);
					}).always(() => {
						block.clear();
					});
				}
			}
        }
        
        createNewWageTable() {
            let self = this;
            if (_.isEmpty(self.selectedWageTableIdentifier()))
                self.selectedWageTableIdentifier.valueHasMutated();
            else
                self.selectedWageTableIdentifier("");
        }

        registerWageTable() {
            let self = this;
            $(".nts-input").filter(":enabled").trigger("validate");
            if ((self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.THREE_DIMENSION || self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.FINE_WORK) && self.updateMode()) {
                for (var i = 0; i < self.wageTableContent().payment().length; i++) {
                    let data = null;
                    if (self.wageTableContent().payment()[i].masterCode() == self.fakeSelectedValue() || self.wageTableContent().payment()[i].frameNumber() == self.fakeSelectedValue()) {
                        data = ko.toJS(self.wageTableContent2dData());
                    } else {
                        let cachedData = localStorage.getItem("ThirdDimension" + i);
                        data = cachedData ? JSON.parse(cachedData) : null;
                    }
                    if (data) {
                        let inputTotal = data.length * data[0].listSecondDms.length, inputed = 0;
                        for (var j = 0; j < data.length; j++) {
                            let firstDms = data[j];
                            for (var k = 0; k < firstDms.listSecondDms.length; k++) {
                                let input = firstDms.listSecondDms[k];
                                if (input.paymentAmount != null && input.paymentAmount != "") {
                                    inputed++;
                                }
                            }
                        }
                        if (0 < inputed && inputed < inputTotal) {
                            if (self.wageTableContent().payment()[i].masterCode() != self.fakeSelectedValue() && self.wageTableContent().payment()[i].frameNumber() != self.fakeSelectedValue()) {
                                self.fakeSelectedValue(self.wageTableContent().payment()[i].masterCode() == null ? self.wageTableContent().payment()[i].frameNumber() : self.wageTableContent().payment()[i].masterCode());
                                $(".nts-input").filter(":enabled").trigger("validate");
                            }
                            break;
                        } else if (inputed == 0)
                            localStorage.removeItem("ThirdDimension" + i);
                        else if (inputed == inputTotal)
                            localStorage.setItem("ThirdDimension" + i, JSON.stringify(data));
                    }
                    
                }
            }
            if (!nts.uk.ui.errors.hasError()) {
                if (self.updateMode()) {
                    self.updateData();
                } else {
                    self.addNewData();
                }
            }
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
                command.elementInformation.twoDimensionElement.optionalAdditionalElement = "F208";
            }
            service.addNewWageTable(command).done((histId: string) => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    self.startPage(command.wageTableCode + histId);
                });
            }).fail(error => {
                dialog.alertError(error).then(() => {
                    if (error.messageId == "Msg_3")
                        $("#A5_2").focus();
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
                wageTableContent: ko.toJS(self.wageTableContent)
            }
            switch (self.selectedWageTable().elementSetting()) {
                case model.ELEMENT_SETTING.ONE_DIMENSION:
                    ko.utils.extend(command.wageTableContent, {oneDimensionPayment: command.wageTableContent.payment});
                    break;
                case model.ELEMENT_SETTING.TWO_DIMENSION:
                    ko.utils.extend(command.wageTableContent, {twoDimensionPayment: command.wageTableContent.payment});
                    break;
                case model.ELEMENT_SETTING.THREE_DIMENSION:
                    for (let i = 0; i < command.wageTableContent.payment.length; i++) {
                        let cachedData = localStorage.getItem("ThirdDimension" + i);
                        if (cachedData) {
                            let data = JSON.parse(cachedData);
                            command.wageTableContent.payment[i].listFirstDms = data;
                            localStorage.removeItem("ThirdDimension" + i);
                        }
                    }
                    ko.utils.extend(command.wageTableContent, {threeDimensionPayment: command.wageTableContent.payment});
                    break;
                case model.ELEMENT_SETTING.QUALIFICATION:
                    ko.utils.extend(command.wageTableContent, {wageTableQualifications: command.wageTableContent.qualificationGroupSetting});
                    break;
                case model.ELEMENT_SETTING.FINE_WORK:
                    for (let i = 0; i < command.wageTableContent.payment.length; i++) {
                        let cachedData = localStorage.getItem("ThirdDimension" + i);
                        if (cachedData) {
                            let data = JSON.parse(cachedData);
                            command.wageTableContent.payment[i].listFirstDms = data;
                            localStorage.removeItem("ThirdDimension" + i);
                        }
                    }
                    ko.utils.extend(command.wageTableContent, {workLevelPayment: command.wageTableContent.payment});
                    break;
                default: break;
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
                            default: break;
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
            setShared("QMM016_I_PARAMS", { selectedWageTable: selectedWageTable, history: history });
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
                    let newHistory = { historyID: newHistoryID, startMonth: params.startMonth, endMonth: 999912 };
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
                        self.showSettingDataByValue(selectedWageTable.wageTableCode + latestHistoryID);
                        self.wageTableContent().historyID("");
                        self.elementRangeSetting().historyID("");
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
            setShared("QMM016_J_PARAMS", { selectedWageTable: selectedWageTable, history: history, selectedHistory: selectedHistory });
            modal("/view/qmm/016/j/index.xhtml").onClosed(() => {
                let params = getShared("QMM016_J_RES_PARAMS");
                if (params) {
                    block.invisible();
                    self.startPage(params.historyId ? self.selectedWageTable().wageTableCode() + params.historyId : null);
                }
            });
        }

        selectElement(dimension) {
            let self = this, selected = null, otherSelected = [], elemInfo = ko.toJS(self.selectedWageTable().elementInformation());
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
                if (params.firstElementRange == null) return;
            }
            block.invisible();
            service.createOneDimentionWageTable(params).done(data => {
                if (!_.isEmpty(data)) {
                    self.elementRangeSetting().historyID(params.historyID);
                    self.elementRangeSetting().secondElementRange(null);
                    self.elementRangeSetting().thirdElementRange(null);
                    self.wageTableContent(new model.WageTableContent(data));
                    $(".input-amount")[0].focus();
                }
            }).fail(error => {
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
        }
        
        createTwoDimensionWageTable() {
            let self = this;
            nts.uk.ui.errors.clearAll();
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
                if (params.firstElementRange == null) return;
            }
            if (self.selectedWageTable().elementInformation().twoDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                // C2_15、C2_17、C2_18の状態を取得
                let secondElementRange = ko.toJS(self.elementRangeSetting).secondElementRange;
                params.secondElementRange = self.getElementRange(secondElementRange, $("#C2_15"), $("#C2_17"), $("#C2_18"));
                if (params.secondElementRange == null) return;
            }
            block.invisible();
            service.createTwoDimentionWageTable(params).done((data: any) => {
                if (!_.isEmpty(data)) {
                    let lst2nd: Array<any> = data.list2dElements;
                    self.listSecondDimension(lst2nd[0].listSecondDms.map(i => {
                        if (i.masterCode) {
                            return {value: i.masterCode, name: i.masterName};
                        } else {
                            return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                        }
                    }));
                    self.elementRangeSetting().historyID(params.historyID);
                    self.elementRangeSetting().thirdElementRange(null);
                    self.wageTableContent(new model.WageTableContent(data));
                    $("#content-wrapper").unbind();
                    self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
                }
            }).fail(error => {
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        createThreeDimensionWageTable() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.fakeSelectedValue(null);
            let params = {
                historyID: self.selectedHistory().historyID(),
                wageTableCode: self.selectedWageTable().wageTableCode(),
                firstElementRange: null,
                secondElementRange: null,
                thirdElementRange: null
            };
            if (self.selectedWageTable().elementInformation().oneDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                // D2_8、D2_10、D2_11の状態を取得
                let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
                params.firstElementRange = self.getElementRange(firstElementRange, $("#D2_8"), $("#D2_10"), $("#D2_11"));
                if (params.firstElementRange == null) return;
            }
            if (self.selectedWageTable().elementInformation().twoDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                // D2_15、D2_17、D2_18の状態を取得
                let secondElementRange = ko.toJS(self.elementRangeSetting).secondElementRange;
                params.secondElementRange = self.getElementRange(secondElementRange, $("#D2_15"), $("#D2_17"), $("#D2_18"));
                if (params.secondElementRange == null) return;
            }
            if (self.selectedWageTable().elementInformation().threeDimensionElement().masterNumericClassification()
                    == model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM) {
                // D2_22、D2_24、D2_25の状態を取得
                let thirdElementRange = ko.toJS(self.elementRangeSetting).thirdElementRange;
                params.thirdElementRange = self.getElementRange(thirdElementRange, $("#D2_22"), $("#D2_24"), $("#D2_25"));
                if (params.thirdElementRange == null) return;
            }
            block.invisible();
            service.createThreeDimentionWageTable(params).done(data => {
                if (!_.isEmpty(data)) {
                    let lst3rd: Array<any> = data.list3dElements;
                    self.listThirdDimension(lst3rd.map(i => {
                        if (i.masterCode) {
                            return {value: i.masterCode, name: i.masterName};
                        } else {
                            return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                        }
                    }));
                    let lst2nd: Array<any> = data.list2dElements[0].listSecondDms;
                    self.listSecondDimension(lst2nd.map(i => {
                        if (i.masterCode) {
                            return {value: i.masterCode, name: i.masterName};
                        } else {
                            return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                        }
                    }));
                    self.elementRangeSetting().historyID(params.historyID);
                    self.wageTableContent(new model.WageTableContent(data));
                    self.wageTableContent2dData(data.list2dElements.map(item => new model.TwoDmsElementItem(item)));
                    $("#content-wrapper").unbind();
                    self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
                    $(".input-amount")[0].focus();
                }
            }).fail(error => {
                dialog.alertError(error);
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
            });
        }
        
        createWorkLevelWageTable() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            let params = {
                historyID: self.selectedHistory().historyID(),
                wageTableCode: self.selectedWageTable().wageTableCode(),
                firstElementRange: null,
                secondElementRange: null,
                thirdElementRange: null
            };
            
            // F2_8、F2_10、F2_11の状態を取得
            let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
            params.firstElementRange = self.getElementRange(firstElementRange, $("#F2_8"), $("#F2_10"), $("#F2_11"));
            if (params.firstElementRange == null) return;
            
            // F2_15、F2_17、F2_18の状態を取得
            let secondElementRange = ko.toJS(self.elementRangeSetting).secondElementRange;
            params.secondElementRange = self.getElementRange(secondElementRange, $("#F2_15"), $("#F2_17"), $("#F2_18"));
            if (params.secondElementRange == null) return;

            params.thirdElementRange = {rangeLowerLimit: 1, rangeUpperLimit: 5, stepIncrement: null};
            
            block.invisible();
            service.createThreeDimentionWageTable(params).done(data => {
                if (!_.isEmpty(data)) {
                    let lst3rd: Array<any> = data.list3dElements;
                    self.listThirdDimension(lst3rd.map(i => {
                        return {value: i.frameNumber, name: i.frameLowerLimit};
                    }));
                    let lst2nd: Array<any> = data.list2dElements[0].listSecondDms;
                    self.listSecondDimension(lst2nd.map(i => {
                        if (i.masterCode) {
                            return {value: i.masterCode, name: i.masterName};
                        } else {
                            return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                        }
                    }));
                    self.elementRangeSetting().historyID(params.historyID);
                    self.wageTableContent(new model.WageTableContent(data));
                    self.wageTableContent2dData(data.list2dElements.map(item => new model.TwoDmsElementItem(item)));
                    $("#content-wrapper").unbind();
                    self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
                    $(".input-amount")[0].focus();
                }
            }).fail(error => {
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
        }
        
        getElementRange(elementRange, controlLower, controlUpper, controlStep): any {
            if (elementRange.rangeLowerLimit == null) {
                dialog.alertError({ messageId: 'MsgQ_3' }).then(() => {
                    controlLower.focus();
                });
                return null;
            }
            if (elementRange.rangeUpperLimit == null) {
                dialog.alertError({ messageId: 'MsgQ_3' }).then(() => {
                    controlUpper.focus();
                });
                return null;
            }
            if (elementRange.stepIncrement == null) {
                dialog.alertError({ messageId: 'MsgQ_3' }).then(() => {
                    controlStep.focus();
                });
                return null;
            }
            if (Number(elementRange.rangeLowerLimit) > Number(elementRange.rangeUpperLimit)) {
                dialog.alertError({ messageId: 'MsgQ_3' }).then(() => {
                    controlLower.focus();
                });
                return null;
            }
            return elementRange;
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

