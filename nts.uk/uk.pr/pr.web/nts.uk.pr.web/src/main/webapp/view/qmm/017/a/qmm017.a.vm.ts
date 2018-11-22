module nts.uk.pr.view.qmm017.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm017.share.model
    import service = nts.uk.pr.view.qmm017.a.service

    export class ScreenModel {
        // screen state
        isOnStartUp = true;
        isNewMode: KnockoutObservable<boolean> = ko.observable(false);
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(false);
        isAddHistoryMode: KnockoutObservable<boolean> = ko.observable(false);
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        // tabs variables
        tabs: any;
        selectedTab: KnockoutObservable<string>;

        // tree grid variables
        formulaList: KnockoutObservableArray<model.Formula> = ko.observableArray([]);
        selectedFormulaIdentifier: KnockoutObservable<string> = ko.observable(null);
        // setting item
        selectedFormula: KnockoutObservable<model.Formula> = ko.observable(new model.Formula(null));
        selectedHistory: KnockoutObservable<model.GenericHistoryYearMonthPeriod> = ko.observable(new model.GenericHistoryYearMonthPeriod(null));
        basicFormulaSetting: KnockoutObservable<model.BasicFormulaSetting> = ko.observable(new model.BasicFormulaSetting(null));
        detailFormulaSetting: KnockoutObservable<model.DetailFormulaSetting> = ko.observable(new model.DetailFormulaSetting(null));
        // for case screen C
        basicCalculationFormulaList: KnockoutObservableArray<model.BasicCalculationFormula> = ko.observableArray([]);
        // fixed formula
        fixedBasicCalculationFormula: KnockoutObservable<model.BasicCalculationFormula> = ko.observable(new model.BasicCalculationFormula(null));

        // tab 2, screen D
        screenDViewModel = new nts.uk.pr.view.qmm017.d.viewmodel.ScreenModel();
        // tabs variables
        screenDTabs: KnockoutObservableArray<any>;
        screenDSelectedTab: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.initFixedElement();
            self.initTabPanel();
            self.initComponents();
            self.doFirstFocus();
            self.doScreenDFocus();
            self.screenMode.subscribe(newValue => {
                self.isNewMode(newValue == model.SCREEN_MODE.NEW);
                self.isUpdateMode(newValue == model.SCREEN_MODE.UPDATE);
                self.isAddHistoryMode(newValue == model.SCREEN_MODE.ADD_HISTORY);
            })
            self.selectedFormulaIdentifier.subscribe(newValue => {
                if (newValue) {
                    self.showFormulaInfoByValue(newValue);
                } else {
                    self.changeToNewMode();
                }
            });
        }

        initFixedElement () {
            var self = this;
            // fixed formula, master use code: 0000000000
            self.fixedBasicCalculationFormula().masterUseCode("0000000000");
        }

        initComponents () {
            $('#C2_1').ntsFixedTable({});
            $('#C3_1').ntsFixedTable({});
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_6'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_7'), content: '.tab-content-2',enable: ko.computed(function() {return self.screenMode() != model.SCREEN_MODE.NEW;}, this), visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
            let self = this;
            self.screenDTabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_40'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_41'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: getText('QMM017_42'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-4', title: getText('QMM017_43'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-5', title: getText('QMM017_44'), content: '.tab-content-5', enable: ko.observable(false), visible: ko.observable(true)},
                {id: 'tab-6', title: getText('QMM017_45'), content: '.tab-content-6', enable: self.selectedFormula().isNotUseNestedAtr, visible: ko.observable(true)},
                {id: 'tab-7', title: getText('QMM017_46'), content: '.tab-content-7', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.screenDSelectedTab = ko.observable('tab-1');
        }

        doFirstFocus () {
            let self = this;
            self.selectedTab.subscribe(newTab => {
                let itemToBeFocus = "";
                if (newTab == 'tab-2') {
                    if (self.selectedFormula().settingMethod() == 0) {
                        if (self.basicFormulaSetting().masterBranchUse() == model.MASTER_BRANCH_USE.USE){
                            itemToBeFocus = '#B1_4';
                        } else {
                            itemToBeFocus = '#C2_7';
                        }
                    }
                    if (self.selectedFormula().settingMethod() == 1) {
                        itemToBeFocus = '#D1_4';
                    }
                } else {
                    if (self.screenMode() != model.SCREEN_MODE.NEW){ itemToBeFocus = '#A3_4'};
                }

                setTimeout(function(){
                    $(itemToBeFocus).focus();
                }, 100)
            })
        }

        register () {
            let self = this;
            let command = ko.toJS(self.selectedFormula);
            let formulaSettingCommand = {
                basicFormulaSettingCommand: ko.toJS(self.basicFormulaSetting),
                detailFormulaSettingCommand: ko.toJS(self.detailFormulaSetting),
                basicCalculationFormulaCommand: ko.toJS(self.basicCalculationFormulaList),
                yearMonth: ko.toJS(self.selectedHistory)
            }
            command.formulaSettingCommand = formulaSettingCommand;
            if (self.screenMode() == model.SCREEN_MODE.NEW) {
                self.addFormula(command);
            } else if (self.screenMode() == model.SCREEN_MODE.UPDATE) {
                self.updateFormulaSetting(command);
            } else {
                self.addFormulaHistory(command)
            }
        }

        // for case add formula
        updateHistoryID (formulaSettingCommand) {
            let historyID = nts.uk.util.randomId();
            formulaSettingCommand.yearMonth.historyID = historyID;
            formulaSettingCommand.basicFormulaSettingCommand.historyID = historyID;
            formulaSettingCommand.detailFormulaSettingCommand.historyID = historyID;
            formulaSettingCommand.basicCalculationFormulaCommand.historyID = historyID;
            return formulaSettingCommand;
        }

        addFormula (command) {
            let self = this;
            block.invisible();
            let lastYmValue = 999912;
            command.formulaSettingCommand.yearMonth.endMonth = lastYmValue;
            command.formulaSettingCommand = self.updateHistoryID(command.formulaSettingCommand);
            service.addFormula(command).done(function(data) {
                block.clear();
                self.getListFormula();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.selectedFormulaIdentifier(command.formulaCode + command.formulaSettingCommand.yearMonth.historyID);
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        updateFormulaSetting (command) {
            let self = this;
            block.invisible();
            service.updateFormulaSetting(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.selectedFormulaIdentifier.valueHasMutated();
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        addFormulaHistory (command) {
            let self = this;
            block.invisible();
            service.addFormulaHistory(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.selectedFormulaIdentifier.valueHasMutated();
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        doScreenDFocus () {
            let self = this;
            self.screenDSelectedTab.subscribe(newTab => {
                let itemToBeFocus = "";
                switch (newTab) {
                    case 'tab-1': {itemToBeFocus = '#D2_5'; break}
                    case 'tab-2': {itemToBeFocus = '#D5_5'; break}
                    case 'tab-3': {itemToBeFocus = '#D6_5'; break}
                    case 'tab-4': {itemToBeFocus = '#D7_5'; break}
                    case 'tab-5': {itemToBeFocus = '#D8_5'; break}
                    case 'tab-6': {itemToBeFocus = '#D9_5'; break}
                    case 'tab-7': {itemToBeFocus = '#D10_5'; break}
                }
                setTimeout(function(){
                    $(itemToBeFocus).focus();
                }, 100)
            })
        }

        convertToTreeList(formulaData) {
            let self = this;
            formulaData.map(function (item) {
                item.nodeText = item.formulaCode + " " + item.formulaName;
                item['identifier'] = item.formulaCode;
                item.history = item.history.map(function (historyItem) {
                    historyItem.nodeText = historyItem.startMonth + " ~ " + historyItem.endMonth;
                    historyItem['identifier'] = item.formulaCode + historyItem.historyID;
                    // prevent handler from null value exception when use search box
                    historyItem.formulaCode = "";
                    historyItem.formulaName = "";
                    return historyItem;
                });
                return item;
            });
            self.formulaList(formulaData);
            if (formulaData.length == 0)
                self.changeToNewMode();
            else {
                // selected first formula and history
                var identifier = formulaData[0].history.length > 0 ? formulaData[0].history[0].identifier : formulaData[0].identifier;
                self.selectedFormulaIdentifier(identifier);
                self.selectedFormulaIdentifier.valueHasMutated();
            }
        }

        initBasicCalculationFormula (){
            let self = this;
            let fixedData: any = {masterUseCode: '0000000000'}, fixedElement:any = new model.BasicCalculationFormula(fixedData);
            self.basicCalculationFormulaList.push(fixedElement);
            let data = [
                {
                    calculationFormulaClassification: 1,
                    masterUseCode: '0000000001',
                    historyID: nts.uk.util.randomId(),
                    basicCalculationFormula: 500,
                    standardAmountClassification: 0,
                    standardFixedValue: 600,
                    targetItemCodeList: ['0001', '0002'],
                    attendanceItem: '0001',
                    coefficientClassification: 0,
                    coefficientFixedValue: 700,
                    formulaType: 0,
                    roundingResult: 800,
                    adjustmentClassification: 0,
                    baseItemClassification: 9,
                    baseItemFixedValue: 900,
                    premiumRate: 910,
                    roundingMethod: 3
                },
                {
                    calculationFormulaClassification: 2,
                    masterUseCode: '0000000002',
                    historyID: nts.uk.util.randomId(),
                    basicCalculationFormula: 1500,
                    standardAmountClassification: 1,
                    standardFixedValue: 1600,
                    targetItemCodeList: ['0003', '0004'],
                    attendanceItem: '0002',
                    coefficientClassification: 1,
                    coefficientFixedValue: 1700,
                    formulaType: 1,
                    roundingResult: 1800,
                    adjustmentClassification: 1,
                    baseItemClassification: 8,
                    baseItemFixedValue: 1900,
                    premiumRate: 1910,
                    roundingMethod: 2
                },
                {
                    calculationFormulaClassification: 0,
                    masterUseCode: '0000000003',
                    historyID: nts.uk.util.randomId(),
                    basicCalculationFormula: 2500,
                    standardAmountClassification: 1,
                    standardFixedValue: 2600,
                    targetItemCodeList: ['0005', '0006'],
                    attendanceItem: '0003',
                    coefficientClassification: 2,
                    coefficientFixedValue: 2700,
                    formulaType: 2,
                    roundingResult: 2800,
                    adjustmentClassification: 1,
                    baseItemClassification: 7,
                    baseItemFixedValue: 2900,
                    premiumRate: 2910,
                    roundingMethod: 1
                },
                {
                    calculationFormulaClassification: 0,
                    masterUseCode: '0000000004',
                    historyID: nts.uk.util.randomId(),
                    basicCalculationFormula: 3500,
                    standardAmountClassification: 1,
                    standardFixedValue: 3600,
                    targetItemCodeList: ['0007', '0008'],
                    attendanceItem: '0004',
                    coefficientClassification: 2,
                    coefficientFixedValue: 3700,
                    formulaType: 2,
                    roundingResult: 3800,
                    adjustmentClassification: 1,
                    baseItemClassification: 6,
                    baseItemFixedValue: 3900,
                    premiumRate: 3910,
                    roundingMethod: 0
                }
            ]
            self.basicCalculationFormulaList.push.apply(self.basicCalculationFormulaList, data.map(item => new model.BasicCalculationFormula(item)));
        }

        changeToNewMode() {
            let self = this;
            self.screenMode(model.SCREEN_MODE.NEW);
            self.selectedFormulaIdentifier(null);
            self.selectedFormula(new model.Formula(null));
            self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
            self.selectedTab('tab-1');
            $('#A3_3').focus();
            nts.uk.ui.errors.clearAll();
        }

        changeToUpdateMode() {
            var self = this;
            self.screenMode(model.SCREEN_MODE.UPDATE);
            self.selectedTab('tab-2');
            nts.uk.ui.errors.clearAll();
        }

        changeToAddHistoryMode() {
            var self = this;
            self.screenMode(model.SCREEN_MODE.ADD_HISTORY);
            self.selectedTab('tab-2');
            nts.uk.ui.errors.clearAll();
        }

        showFormulaInfoByValue(identifier) {
            let self = this;
            let currentFormulaList = ko.toJS(self.formulaList), selectedFormulaCode, selectedHistoryID, selectedFormula,
                selectedHistory;
            selectedFormulaCode = identifier.substring(0, 3);
            selectedFormula = _.find(currentFormulaList, {formulaCode: selectedFormulaCode});
            self.selectedFormula(new model.Formula(selectedFormula));
            self.isSelectedHistory(false);
            // if select history
            if (identifier.length > 36) {
                selectedHistoryID = identifier.substring(3, identifier.length);
                selectedHistory = _.find(selectedFormula.history, {historyID: selectedHistoryID});
                self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                self.isSelectedHistory(true);
                self.showFormulaSettingByHistory(selectedHistoryID)
            }
            else {
                self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
            }
            self.changeToUpdateMode();
        };
        showFormulaSettingByHistory(historyID: string) {
            let self = this;
            block.invisible();
            service.getFormulaSettingByHistory(historyID).done(function (data) {
                block.clear();
            }).fail(function(err){
                block.clear();
                dialog.alertError(err.message);
            })
        }

        getListFormula(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getAllFormula().done(function(data){
                self.convertToTreeList(data);
                dfd.resolve();
            });
            return dfd.promise();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            return self.getListFormula();
        }

        createNewHistory () {
            let self = this;
            let selectedFormula = ko.toJS(self.selectedFormula);
            let history = selectedFormula.history;
            setShared("QMM017_H_PARAMS", { selectedFormula: selectedFormula, history: history });
            modal("/view/qmm/017/h/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_H_RES_PARAMS");
                if (params) {
                    let formulaList = ko.toJS(self.formulaList);
                    let historyID = nts.uk.util.randomId();
                    // update previous history
                    if (history.length > 0) {
                        let beforeLastMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                        history[0].endMonth = beforeLastMonth.format('YYYYMM');
                    }
                    // add new history
                    history.unshift({ historyID: historyID, startMonth: params.startMonth, endMonth: '999912' });
                    formulaList.forEach(function (formula) {
                        if (formula.formulaCode == selectedFormula.formulaCode) {
                            formula.history = history;
                            formula = new model.Formula(formula);
                        }
                    });
                    // update formula and tree grid
                    self.convertToTreeList(formulaList);
                    self.selectedFormula(selectedFormula.formulaCode + historyID);
                    // clone data
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_LAST_HISTORY && history.length > 1) {
                    }
                    else {
                    }
                    self.screenMode(model.SCREEN_MODE.ADD_HISTORY);
                }
            });
        };
        editHistory () {
            var self = this;
            var selectedFormula = ko.toJS(self.selectedFormula), selectedHistory = ko.toJS(self.selectedHistory);
            var history = selectedFormula.history;
            setShared("QMM017_I_PARAMS", { selectedFormula: selectedFormula, history: history, selectedHistory: selectedHistory });
            modal("/view/qmm/017/i/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_I_RES_PARAMS");
                if (params) {
                    service.getAllFormula().done(function(data){
                        self.convertToTreeList(data);
                        // change selected value
                        if (params.modifyMethod == model.MODIFY_METHOD.DELETE) {
                            if (history.length <= 1) {
                                self.selectedFormulaIdentifier(selectedFormula.formulaCode);
                            }
                            else {
                                self.selectedFormulaIdentifier(selectedFormula.formulaCode + history[1].historyID);
                            }
                        } else {
                            self.selectedFormulaIdentifier.valueHasMutated();
                        }
                    })

                }
            });
        };
        doMasterConfiguration () {
            let self = this;
            // unknown which item to be affect. temporary not link b to e
            setShared("QMM017_E_PARAMS", {basicCalculationFormula: ko.toJS(self.fixedBasicCalculationFormula), originalScreen: 'B'});
            modal("/view/qmm/017/e/index.xhtml").onClosed(function () {
                let params = getShared("QMM017_E_RES_PARAMS");
                if (params) self.fixedBasicCalculationFormula(new model.BasicCalculationFormula(params.basicCalculationFormula));
            });

        };
        doConfiguration (index) {
            let self = this;
            let self = this;
            // unknown which item to be affect. temporary not link b to e
            setShared("QMM017_E_PARAMS", {fixedBasicCalculationFormula: ko.toJS(self.fixedBasicCalculationFormula), basicCalculationFormula: ko.toJS(self.basicCalculationFormulaList[index]), originalScreen: 'C', basicCalculationFormula: this});
            modal("/view/qmm/017/e/index.xhtml").onClosed(function () {
                let params = getShared("QMM017_E_RES_PARAMS");
                if (params){
                    self.basicCalculationFormulaList.replace(self.basicCalculationFormulaList[index], new model.BasicCalculationFormula(params.basicCalculationFormula));
                }
            });

        };
        setAllCalculationFormula () {
            let self = this;
            let basicCalculationFormulaList = self.basicCalculationFormulaList();
            basicCalculationFormulaList = basicCalculationFormulaList.map(item => item['calculationFormulaClassification'](model.CALCULATION_FORMULA_CLS.FORMULA));
            self.basicCalculationFormulaList(basicCalculationFormulaList);
        }

        setAllFixedValue () {
            let self = this;
            let basicCalculationFormulaList = self.basicCalculationFormulaList();
            basicCalculationFormulaList = basicCalculationFormulaList.map(item => item['calculationFormulaClassification'](model.CALCULATION_FORMULA_CLS.FIXED_VALUE));
            self.basicCalculationFormulaList(basicCalculationFormulaList);

        }
    }

}