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
        selectedWageTableIdentifier: KnockoutObservable<string> = ko.observable(null);
        selectedWageTable: KnockoutObservable<model.WageTable> = ko.observable(new model.WageTable(null));
        selectedHistory: KnockoutObservable<model.GenericHistoryYearMonthPeriod> = ko.observable(new model.GenericHistoryYearMonthPeriod(null));
        wageTableTreeList: KnockoutObservableArray<Node> = ko.observableArray([]);
        elementRangeSetting: KnockoutObservable<model.ElementRangeSetting> = ko.observable(new model.ElementRangeSetting(null));
        wageTableContent: KnockoutObservable<model.WageTableContent> = ko.observable(new model.WageTableContent(null));

        // master data
        qualificationInformationData: Array<model.QualificationInformation>;
        qualificationGroupSettingData: Array<model.QualificationGroupSetting>;
        fakeCombobox: KnockoutObservableArray<model.EnumModel> = ko.observableArray([new model.EnumModel(0, 'Item 1'), new model.EnumModel(1, 'Item 2')]);
        fakeSelectedValue: KnockoutObservable<string> = ko.observable(null);

        constructor() {
            let self = this;
            self.initTabPanel();
            self.selectedWageTableIdentifier.subscribe((newValue) => {
                nts.uk.ui.errors.clearAll();
                if (_.isEmpty(newValue)) {
                    self.selectedWageTable(new model.WageTable(null));
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
                    self.wageTableContent(new model.WageTableContent(null));
                    self.elementRangeSetting(new model.ElementRangeSetting(null));
                    self.selectedTab('tab-1');
                    self.updateMode(false);
                } else {
                    self.showWageTableInfoByValue(newValue);
                    self.showSettingDataByValue(newValue)
                    self.updateMode(true);
//                    self.selectedTab('tab-2');
                }
            });
            self.initComponents();
        }

        initComponents() {
            let self = this;
            $('#A8_2').ntsFixedTable({ width: 300 });
            $('.normal-fixed-table').ntsFixedTable({ width: 600 });
            $('.fixed-table-top').ntsFixedTable({ width: 300, height: 34 });
            if (/Chrome/.test(navigator.userAgent)) {
                $('.fixed-table-top').ntsFixedTable({ width: 300, height: 34 });
                $('.fixed-table-body').ntsFixedTable({ width: 600, height: 207 });
                $('#E5_1').ntsFixedTable({ width: 700, height: 207 });
            } else {
                $('.fixed-table-body').ntsFixedTable({ width: 600, height: 204 });
                $('#E5_1').ntsFixedTable({ width: 700, height: 204 });
            }
        }

        convertToTreeList(wageTableData: Array<model.IWageTable>) {
            let self = this;
            let wageTableTreeData: Array<Node> = wageTableData.map(item => {
                return new Node(item);
            })
            self.wageTableTreeList(wageTableTreeData);
            if (wageTableData.length == 0)
                self.selectedWageTableIdentifier(null);
            else {
                // selected first wage table and history
                let identifier = wageTableTreeData[0].histories.length > 0 ? wageTableTreeData[0].histories[0].identifier : wageTableTreeData[0].identifier;
                self.selectedWageTableIdentifier(identifier);
            }
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText("QMM016_11"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText("QMM016_12"), content: '.tab-content-2', enable: self.updateMode, visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllWageTable().done((data: Array<any>) => {
                if (!_.isEmpty(data)) {
                    self.convertToTreeList(data);
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
            if (selectedWageTableCode == self.selectedWageTable().wageTableCode()) {
                if (identifier.length > 36) {
                    let selectedWageTable = ko.toJS(self.selectedWageTable);
                    let selectedHistoryID = identifier.substring(3, identifier.length);
                    let selectedHistory: any = _.find(selectedWageTable.histories, { historyID: selectedHistoryID });
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                    self.isSelectedHistory(true);
                } else {
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
                }
            } else {
                block.invisible();
                service.getWageTableByCode(selectedWageTableCode).done((selectedWageTable: model.IWageTable) => {
                    self.selectedWageTable(new model.WageTable(selectedWageTable));
                    self.isSelectedHistory(false);
                    // if select history
                    if (identifier.length > 36) {
                        let selectedHistoryID = identifier.substring(3, identifier.length);
                        let selectedHistory = _.find(selectedWageTable.histories, { historyID: selectedHistoryID });
                        self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                        self.isSelectedHistory(true);
                    } else {
                        self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
                    }
                }).fail(error => {
                    dialog.alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

        showSettingDataByValue(identifier: string) {
            let self = this;
            self.wageTableContent(new model.WageTableContent(null));
            self.elementRangeSetting(new model.ElementRangeSetting(null));
        }

        createNewWageTable() {
            let self = this;
            if (_.isEmpty(self.selectedWageTableIdentifier()))
                self.selectedWageTableIdentifier.valueHasMutated();
            else
                self.selectedWageTableIdentifier(null);
        }

        registerWageTable() {
            let self = this;
            $(".nts-input").trigger("validate");
//            $("#A7_4_1").trigger("validate");
//            $("#A7_4_2").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                if (self.updateMode()) {
                    let command = {
                        wageTableCode: self.selectedWageTable().wageTableCode(),
                        wageTableName: self.selectedWageTable().wageTableName(),
                        remarkInformation: self.selectedWageTable().remarkInformation()
                    }
                    service.updateWageTable(command).done(() => {
                        self.startPage();
                    }).fail(error => {
                        dialog.alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                } else {
                    let command = {
                        wageTableCode: self.selectedWageTable().wageTableCode(),
                        wageTableName: self.selectedWageTable().wageTableName(),
                        elementInformation: ko.toJS(self.selectedWageTable().elementInformation),
                        elementSetting: self.selectedWageTable().elementSetting(),
                        remarkInformation: self.selectedWageTable().remarkInformation(),
                        history: {
                            historyID: "", 
                            startMonth: self.selectedHistory().startMonth(), 
                            endMonth: 999912
                        }
                    }
                    service.addNewWageTable(command).done((histId: string) => {
                        self.startPage().done(() => {
                            self.selectedWageTableIdentifier(command.wageTableCode + histId);
                        });
                    }).fail(error => {
                        dialog.alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            }
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
                    let historyID = nts.uk.util.randomId();
                    // update previous history
                    if (history.length > 0) {
                        let beforeLastMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                        history[0].endMonth = beforeLastMonth.format('YYYYMM');
                    }
                    // add new history
                    history.unshift({ historyID: historyID, startMonth: params.startMonth, endMonth: '999912' });
                    wageTableTreeList.forEach(wageTable => {
                        if (wageTable.wageTableCode == selectedWageTable.wageTableCode) {
                            wageTable.histories = history;
                            wageTable = new model.WageTable(wageTable);
                        }
                    });
                    // update wage table and tree grid
                    self.convertToTreeList(wageTableTreeList);
                    self.selectedWageTableIdentifier(selectedWageTable.wageTableCode + historyID);
                    // clone data
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_LAST_HISTORY && history.length > 1) {

                    } else {

                    }
                    self.updateMode(false);
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
                    //                    self.getWageTableList();
                    // change selected value
                    if (params.modifyMethod == model.MODIFY_METHOD.DELETE) {
                        if (history.length <= 1) {
                            self.selectedWageTableIdentifier(selectedWageTable.wageTableCode);

                        } else {
                            self.selectedWageTableIdentifier(selectedWageTable.wageTableCode + history[1].historyID)
                        }
                    }
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
            let firstElementRange = ko.toJS(self.elementRangeSetting).firstElementRange;
            if (Number(firstElementRange.rangeLowerLimit) > Number(firstElementRange.rangeUpperLimit)) dialog.alertError({ messageId: 'MsgQ_3' });
            self.wageTableContent(new WageTableContent(self.getSampleData()));

        }
        
        createTwoDimensionWageTable() {
            let self = this;
            self.wageTableContent(new WageTableContent(self.getSampleData()));
        }

        createThreeDimensionWageTable() {
            let self = this;
            self.wageTableContent(new WageTableContent(self.getSampleData()));
        }

        createWageTable() {
            let self = this;
            self.getQualificationGroupSettingContentData();
            let wageTableContent: any = self.getSampleData();
            wageTableContent.qualificationGroupSetting = self.createDisplayQualificationGroupSettingData(wageTableContent.qualificationGroupSetting);
            self.wageTableContent(new WageTableContent(wageTableContent));
        }

        prepareAllWageTable() {
            let self = this;
            self.wageTableContent(new WageTableContent(self.getSampleData()));
        }

        getSampleData(): any {
            let self = this;
            let paymentData: Array<any> = [
                {
                    wageTablePaymentAmount: 5,
                    elementAttribute: {
                        firstElementItem: {
                            masterCode: 1, frameNumber: 1, frameLowerLimit: 1, frameUpperLimit: 5
                        },
                        secondElementItem: {
                            masterCode: 1, frameNumber: 1, frameLowerLimit: 1, frameUpperLimit: 5
                        },
                        thirdElementItem: {
                            masterCode: 1, frameNumber: 1, frameLowerLimit: 1, frameUpperLimit: 5
                        },
                    }
                }
            ];
            let groupSettingData: Array<any> = [
                {
                    paymentMethod: 0,
                    qualificationGroupCode: '01',
                    eligibleQualificationCode: ['001', '002', '003']
                },
                {
                    paymentMethod: 1,
                    qualificationGroupCode: '02',
                    eligibleQualificationCode: ['001', '002', '003', '005']
                },
                {
                    paymentMethod: 0,
                    qualificationGroupCode: '03',
                    eligibleQualificationCode: ['001', '004']
                }
            ]
            let wageTableContent = {
                historyID: nts.uk.util.randomId(),
                payment: paymentData,
                qualificationGroupSetting: groupSettingData
            };
            return wageTableContent;
        }
        getQualificationGroupSettingContentData() {
            let self = this;
            let qualificationGroupData: any = [
                {
                    qualificationGroupCode: '01',
                    paymentMethod: 0,
                    eligibleQualificationCode: ['001', '002'],
                    qualificationGroupName: 'Group Name 1'
                },
                {
                    qualificationGroupCode: '02',
                    paymentMethod: 0,
                    eligibleQualificationCode: ['001', '002'],
                    qualificationGroupName: 'Group Name 2'
                },
                {
                    qualificationGroupCode: '03',
                    paymentMethod: 0,
                    eligibleQualificationCode: ['004', '005'],
                    qualificationGroupName: 'Group Name 3'
                }
            ];
            self.qualificationGroupSettingData = qualificationGroupData;
            let qualificationInformationData = [];
            for (var i = 1; i < 20; i++) {
                qualificationInformationData.push({ qualificationCode: nts.uk.text.padLeft(i + "", '0', 3), qualificationName: 'Name ' + i })
            }
            self.qualificationInformationData = qualificationInformationData;
        }
        createDisplayQualificationGroupSettingData(groupSettingData: Array<any>) {
            let self = this;
            groupSettingData.map(function(item) {
                item.qualificationGroupName = _.find(self.qualificationGroupSettingData, { qualificationGroupCode: item.qualificationGroupCode }).qualificationGroupName;
                item.eligibleQualification = self.qualificationInformationData.filter(informationItem =>
                    item.eligibleQualificationCode.indexOf(informationItem.qualificationCode) >= 0
                );
                return item;
            })
            return groupSettingData;
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
                historyItem.nodeText = historyItem.startMonth + " ~ " + historyItem.endMonth;
                historyItem.identifier = params.wageTableCode + historyItem.historyID;
                // prevent handler from null value exception when use search box
                historyItem.wageTableCode = "";
                historyItem.wageTableName = "";
                return historyItem;
            });
            this.identifier = params.wageTableCode;
            this.nodeText = params.wageTableCode + " " + params.wageTableName;
        }
    }
}

