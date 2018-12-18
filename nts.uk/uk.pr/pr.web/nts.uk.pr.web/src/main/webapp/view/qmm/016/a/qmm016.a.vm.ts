module nts.uk.pr.view.qmm016.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import getText = nts.uk.resource.getText;
    import WageTableContent = nts.uk.pr.view.qmm016.share.model.WageTableContent;
    export class ScreenModel {

        // screen state
        isOnStartUp: boolean = true;
        updateMode: KnockoutObservable<boolean> = ko.observable(false);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);

        // tab panel
        tabs: any;
        selectedTab: any;

        // screen item
        selectedWageTableIdentifier: KnockoutObservable<string> = ko.observable("");
        selectedWageTable: KnockoutObservable<model.WageTable> = ko.observable(new model.WageTable(null));
        selectedHistory: KnockoutObservable<model.GenericHistoryYearMonthPeriod> = ko.observable(new model.GenericHistoryYearMonthPeriod({historyID: "", startMonth: null, endMonth: 99912}));
        wageTableTreeList: KnockoutObservableArray<Node> = ko.observableArray([]);
        elementRangeSetting: KnockoutObservable<model.ElementRangeSetting> = ko.observable(new model.ElementRangeSetting(null));
        wageTableContent: KnockoutObservable<model.WageTableContent> = ko.observable(new model.WageTableContent(null));

        // master data
        qualificationInformationData: Array<model.QualificationInformation>;
        qualificationGroupSettingData: Array<model.QualificationGroupSetting>;
        fakeSelectedValue: KnockoutObservable<any> = ko.observable(null);
        listSecondDimension: KnockoutObservableArray<any> = ko.observableArray([]);
        listThirdDimension: KnockoutObservableArray<any> = ko.observableArray([]);
        ignoreScrollEvents: boolean = false;
        wageTableContent2dData: KnockoutObservableArray<model.TwoDmsElementItem> = ko.observableArray([]);

        constructor() {
            let self = this;
            self.initTabPanel();
            self.selectedWageTableIdentifier.subscribe((newValue) => {
                nts.uk.ui.errors.clearAll();
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
                    self.fakeSelectedValue(null);
                    self.elementRangeSetting(new model.ElementRangeSetting(null));
                    self.wageTableContent(new model.WageTableContent(null));
                    self.showWageTableInfoByValue(newValue);
                    self.updateMode(true);
                    $("#A3_1").focus();
                }
            });
            self.initComponents();
            self.fakeSelectedValue.subscribe((oldValue) => {
                if (oldValue != null && !_.isEmpty(self.wageTableContent()) && !_.isEmpty(self.wageTableContent().payment())) {
                    let i3rdIndex = _.findIndex(self.wageTableContent().payment(), p => {return p.masterCode() == oldValue || p.frameNumber() == oldValue;} );
                    if (i3rdIndex >= 0) {
                        let data = _.cloneDeep(self.wageTableContent2dData());
                        if (!_.isEmpty(self.wageTableContent().payment()[i3rdIndex].listFirstDms))
                            self.wageTableContent().payment()[i3rdIndex].listFirstDms(data);
                    }
                }
            }, null, "beforeChange");
            self.fakeSelectedValue.subscribe(value => {
                nts.uk.ui.errors.clearAll();
                if (value != null && !_.isEmpty(self.wageTableContent()) && !_.isEmpty(self.wageTableContent().payment())) {
                    let i3rdIndex = _.findIndex(self.wageTableContent().payment(), p => {return p.masterCode() == value || p.frameNumber() == value;} );
                    if (i3rdIndex >= 0) {
                        let data = _.cloneDeep(self.wageTableContent().payment()[i3rdIndex].listFirstDms());
                        self.wageTableContent2dData(data);
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
                $('#E5_1').ntsFixedTable({ width: 750, height: 344 });
            } else {
                $('.fixed-table-body').ntsFixedTable({ width: 600, height: 204 });
                $('#E5_1').ntsFixedTable({ width: 750, height: 341 });
            }
        }

        convertToTreeList(wageTableData: Array<model.IWageTable>) {
            let self = this;
            let wageTableTreeData: Array<Node> = wageTableData.map(item => {
                return new Node(item);
            })
            self.wageTableTreeList(wageTableTreeData);
            if (wageTableData.length == 0)
                self.selectedWageTableIdentifier("");
            else {
                // selected first wage table and history
                let identifier = wageTableTreeData[0].histories.length > 0 ? wageTableTreeData[0].histories[0].identifier : wageTableTreeData[0].identifier;
                if (self.selectedWageTableIdentifier() == identifier)
                    self.selectedWageTableIdentifier.valueHasMutated();
                else
                    self.selectedWageTableIdentifier(identifier);
            }
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText("QMM016_11"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText("QMM016_12"), content: '.tab-content-2', enable: ko.computed(() => { return self.updateMode() && self.isSelectedHistory(); }), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-2');
        }
        
        syncScroll(source, follow1, follow2?) {
            let self = this;
            source.scroll((e) => {
                let ignore = self.ignoreScrollEvents;
                self.ignoreScrollEvents = false;
                if (ignore) return;
                self.ignoreScrollEvents = true;
                setTimeout(() => { 
                    follow1.scrollTop(source.scrollTop()); 
                }, 10);
                if (follow2) {
                    setTimeout(() => { 
                        follow2.scrollLeft(source.scrollLeft());
                    }, 10);
                }
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            self.wageTableTreeList([]);
            service.getAllWageTable().done((data: Array<any>) => {
                if (!_.isEmpty(data)) {
                    self.convertToTreeList(data);
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
                    });
                } else {
					$.when(service.getWageTableContent(selectedHistoryID, identifier.substring(0, 3)), service.getElemRangeSet(selectedHistoryID)).done((contentData, settingData) => {
						if (contentData != null && !_.isEmpty(contentData.list2dElements)) {
							let lst2nd: Array<any> = contentData.list2dElements[0].listSecondDms;
							self.listSecondDimension(lst2nd.map(i => {
								if (i.masterCode) {
									return {value: i.masterCode, name: i.masterName};
								} else {
									return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
								}
							}));
						}
						if (contentData != null && (!_.isEmpty(contentData.list3dElements) || !_.isEmpty(contentData.listWorkElements))) {
							let lst2nd: Array<any> = [];
                            if (!_.isEmpty(contentData.list3dElements)) {
                                lst2nd = contentData.list3dElements[0].listFirstDms[0].listSecondDms;
                                let lst3rd: Array<any> = contentData.list3dElements;
                                self.listThirdDimension(lst3rd.map(i => {
                                    if (i.masterCode) {
                                        return {value: i.masterCode, name: i.masterName};
                                    } else {
                                        return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                                    }
                                }));
                            } else {
                                lst2nd = contentData.listWorkElements[0].listFirstDms[0].listSecondDms;
                                let lst3rd: Array<any> = contentData.listWorkElements;
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
						}
						self.wageTableContent(new model.WageTableContent(contentData));
						if (contentData != null && (!_.isEmpty(contentData.list3dElements) || !_.isEmpty(contentData.listWorkElements))) {
							self.wageTableContent2dData(self.wageTableContent().payment()[0].listFirstDms());
						}
						self.elementRangeSetting(new model.ElementRangeSetting(settingData));
						if (!_.isEmpty(contentData)) {
							$("#elem1-wrapper").unbind();
							$("#content-wrapper").unbind();
                            self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
							self.syncScroll($("#elem1-wrapper"), $("#content-wrapper"));
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
//            $("#A7_4_1").trigger("validate");
//            $("#A7_4_2").trigger("validate");
            if ((self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.THREE_DIMENSION || self.selectedWageTable().elementSetting() == model.ELEMENT_SETTING.FINE_WORK) && self.updateMode()) {
                self.wageTableContent().payment().forEach(thirdDms => {
                    let inputEmptyAll = true; 
                    thirdDms.listFirstDms().forEach(firstDms => {
                        firstDms.listSecondDms().forEach(i => {
                            if (i.paymentAmount() == null) {
                                self.fakeSelectedValue(thirdDms.masterCode() == null ? thirdDms.frameNumber() : thirdDms.masterCode());
                                $(".nts-input").filter(":enabled").trigger("validate");
                                return;
                            }
                        });
                    });
                });
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
                    self.startPage().done(() => {
                        self.selectedWageTableIdentifier(command.wageTableCode + histId);
                    });
                });
            }).fail(error => {
                dialog.alertError(error);
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
                    ko.utils.extend(command.wageTableContent, {threeDimensionPayment: command.wageTableContent.payment});
                    break;
                case model.ELEMENT_SETTING.QUALIFICATION:
                    ko.utils.extend(command.wageTableContent, {wageTableQualifications: command.wageTableContent.qualificationGroupSetting});
                    break;
                case model.ELEMENT_SETTING.FINE_WORK:
                    ko.utils.extend(command.wageTableContent, {workLevelPayment: command.wageTableContent.payment});
                    break;
                default: break;
            }
            service.updateWageTable(command).done((historyId: string) => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    self.startPage().done(() => {
                        let identifier = command.wageTableCode + historyId;
                        self.selectedWageTableIdentifier(identifier);
                    });
                });
            }).fail(error => {
                dialog.alertError(error);
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
                    let selectedHistory = { 
                        historyID: "", 
                        startMonth: params.startMonth, 
                        endMonth: 999912 
                    };
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_LAST_HISTORY && history.length > 0) {
                        block.invisible();
                        let selectedHistoryID = history.length > 0 ? history[0].historyID : "";
                        $.when(service.getWageTableContent(selectedHistoryID, selectedWageTable.wageTableCode), service.getElemRangeSet(selectedHistoryID)).done((contentData, settingData) => {
                            self.wageTableContent(new model.WageTableContent(contentData));
                            self.elementRangeSetting(new model.ElementRangeSetting(settingData));
                            self.wageTableContent().historyID("");
                            self.elementRangeSetting().historyID("");
                        }).fail(error => {
                            dialog.alertError(error);
                        }).always(() => {
                            block.clear();
                        });
                    } else {
                        self.elementRangeSetting(new model.ElementRangeSetting(null));
                        self.wageTableContent(new WageTableContent(null));
                    }
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
                    self.startPage().done(() => {
                        if (params.historyId)
                            self.selectedWageTableIdentifier(self.selectedWageTable().wageTableCode() + params.historyId);
                    }).fail(error => {
                        dialog.alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
        }

        selectElement(dimension) {
            let self = this;
            setShared("QMM016_G_PARAMS", {});
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
                    $("#elem1-wrapper").unbind();
                    $("#content-wrapper").unbind();
                    self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
                    self.syncScroll($("#elem1-wrapper"), $("#content-wrapper"));
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
                    let lst2nd: Array<any> = data.list3dElements[0].listFirstDms[0].listSecondDms;
                    self.listSecondDimension(lst2nd.map(i => {
                        if (i.masterCode) {
                            return {value: i.masterCode, name: i.masterName};
                        } else {
                            return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                        }
                    }));
                    self.elementRangeSetting().historyID(params.historyID);
                    self.wageTableContent(new model.WageTableContent(data));
                    self.wageTableContent2dData(self.wageTableContent().payment()[0].listFirstDms());
                    $("#elem1-wrapper").unbind();
                    $("#content-wrapper").unbind();
                    self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
                    self.syncScroll($("#elem1-wrapper"), $("#content-wrapper"));
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
                    let lst2nd: Array<any> = data.list3dElements[0].listFirstDms[0].listSecondDms;
                    self.listSecondDimension(lst2nd.map(i => {
                        if (i.masterCode) {
                            return {value: i.masterCode, name: i.masterName};
                        } else {
                            return {value: i.frameNumber, name: i.frameLowerLimit + getText("QMM016_31") + i.frameUpperLimit};
                        }
                    }));
                    self.elementRangeSetting().historyID(params.historyID);
                    self.wageTableContent(new model.WageTableContent(data));
                    self.wageTableContent2dData(self.wageTableContent().payment()[0].listFirstDms());
                    $("#elem1-wrapper").unbind();
                    $("#content-wrapper").unbind();
                    self.syncScroll($("#content-wrapper"), $("#elem1-wrapper"), $("#elem2-wrapper"));
                    self.syncScroll($("#elem1-wrapper"), $("#content-wrapper"));
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

