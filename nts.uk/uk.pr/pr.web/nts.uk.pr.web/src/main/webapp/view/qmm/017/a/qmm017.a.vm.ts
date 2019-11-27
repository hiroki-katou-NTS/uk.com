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
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(false);
        isAddHistoryMode: KnockoutObservable<boolean> = ko.observable(false);
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        isCompleteChangeMode: boolean = true;
        isCompleteStartScreen: boolean = false;
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
        // tab 2, screen C, D
        // for case screen C
        basicCalculationFormulaList: KnockoutObservableArray<model.BasicCalculationFormula> = ko.observableArray([]);

        // screen B, master formula
        masterBasicCalculationFormula: KnockoutObservable<model.BasicCalculationFormula> = ko.observable(new model.BasicCalculationFormula(null));

        // tab 2, screen D
        screenDViewModel = new nts.uk.pr.view.qmm017.d.viewmodel.ScreenModel();
        // tabs variables
        screenDTabs: KnockoutObservableArray<any>;
        screenDSelectedTab: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.initFixedElement();
            self.initTabPanel();
            self.initComponents();
            self.doFirstFocus();
            self.screenMode.subscribe(newValue => {
                self.isNewMode(newValue == model.SCREEN_MODE.NEW);
                self.isUpdateMode(newValue == model.SCREEN_MODE.UPDATE);
                self.isAddHistoryMode(newValue == model.SCREEN_MODE.ADD_HISTORY);
            })
            self.selectedFormulaIdentifier.subscribe(newValue => {
                nts.uk.ui.errors.clearAll();
                if (self.screenMode() == model.SCREEN_MODE.ADD_HISTORY && self.isCompleteChangeMode){
                    self.getListFormula(null);
                }
                if (newValue) {
                    self.showFormulaInfoByValue(newValue);
                } else {
                    self.changeToNewMode();
                }
            });
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
                    service.exportExcel(data).done(function() {
                        //nts.uk.ui.windows.close();
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            });
        }

        initFixedElement () {
            var self = this;
            // fixed formula, master use code: empty string => 10 space digits in db
            self.masterBasicCalculationFormula().masterUseCode("");
        }

        initComponents () {
            $('#C2_1').ntsFixedTable({});
            $('#C3_1').ntsFixedTable({height: 370});
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_6'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_7'), content: '.tab-content-2',enable: ko.computed(function() {
                    return (self.screenMode() == model.SCREEN_MODE.UPDATE || self.screenMode() == model.SCREEN_MODE.ADD_HISTORY);}, this),
                    visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
            let self = this;
            self.screenDTabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_40'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_41'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: getText('QMM017_42'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-4', title: getText('QMM017_43'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-5', title: getText('QMM017_44'), content: '.tab-content-5', enable: ko.observable(false), visible: ko.observable(true)},
                {id: 'tab-6', title: getText('QMM017_45'), content: '.tab-content-6', enable: ko.observable(true), visible: ko.computed(function() {
                        return (self.selectedFormula().nestedAtr() == model.NESTED_USE_CLS.NOT_USE)}, this)},
                {id: 'tab-7', title: getText('QMM017_46'), content: '.tab-content-7', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.screenDSelectedTab = ko.observable('tab-1');
        }

        doFirstFocus () {
            let self = this;
            self.selectedTab.subscribe(newTab => {
                let itemToBeFocus = "";
                if (newTab == 'tab-2') {
                    if (self.selectedFormula().settingMethod() == model.FORMULA_SETTING_METHOD.BASIC_SETTING) {
                        if (self.basicFormulaSetting().masterBranchUse() == model.MASTER_BRANCH_USE.USE) itemToBeFocus = '#C2_7';
                        else itemToBeFocus = '#B1_4';
                    } else itemToBeFocus = '#D1_4';
                } else if (self.screenMode() != model.SCREEN_MODE.NEW){ itemToBeFocus = '#formula-tree_container'}
                setTimeout (function(){
                    if (itemToBeFocus) $(itemToBeFocus).focus();
                }, 50);
            });
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
                }, 50)
            })
        }

        register () {
            let self = this;
            self.validateError();
            if (nts.uk.ui.errors.hasError()) return;
            let command = self.combineData();
            if (self.screenMode() == model.SCREEN_MODE.NEW) {
                self.addFormula(command);
            } else if (self.screenMode() == model.SCREEN_MODE.UPDATE) {
                self.updateFormulaSetting(command);
            } else if (self.screenMode() == model.SCREEN_MODE.ADD_HISTORY){
                self.addFormulaHistory(command)
            } else {
                self.updateFormula(command);
            }
        }

        validateError () {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.tab-content-1 .nts-input').filter(':enabled').trigger("validate");
            if (self.screenMode() == model.SCREEN_MODE.UPDATE || self.screenMode() == model.SCREEN_MODE.ADD_HISTORY ){
                if (self.selectedFormula().settingMethod() == model.FORMULA_SETTING_METHOD.BASIC_SETTING){
                    $('.basic .nts-input').filter(':enabled').trigger("validate");
                }
                else {
                    $('.detail .nts-input').filter(':enabled').trigger("validate");
                    self.screenDViewModel.validateSyntax();
                }
            }
        }

        combineData () {
            let self = this;
            let command = ko.toJS(self.selectedFormula);
            let formulaSettingCommand = {
                basicFormulaSettingCommand: ko.toJS(self.basicFormulaSetting),
                detailFormulaSettingCommand: ko.toJS(self.detailFormulaSetting),
                basicCalculationFormulaCommand: [],
                yearMonth: ko.toJS(self.selectedHistory)
            };
            let basicCalculationFormula = [];
            if (command.settingMethod == model.FORMULA_SETTING_METHOD.BASIC_SETTING) {
                let fixedMasterUseCode = "";
                basicCalculationFormula.push(ko.toJS(self.masterBasicCalculationFormula));
                basicCalculationFormula[0].masterUseCode = fixedMasterUseCode;
                if (formulaSettingCommand.basicFormulaSettingCommand.masterBranchUse == model.MASTER_BRANCH_USE.USE) {
                    basicCalculationFormula.push.apply(basicCalculationFormula, ko.toJS(self.basicCalculationFormulaList));
                }
            } else {
                formulaSettingCommand.detailFormulaSettingCommand.detailCalculationFormula = self.screenDViewModel.extractFormulaElement();
            }
            formulaSettingCommand.basicCalculationFormulaCommand = basicCalculationFormula;
            if (self.screenMode() == model.SCREEN_MODE.NEW) {
                formulaSettingCommand.yearMonth.historyID = nts.uk.util.randomId();
            }
            command.formulaSettingCommand = formulaSettingCommand;
            return self.formatData(command);
        }
        formatData (command) {
            command.formulaSettingCommand.basicFormulaSettingCommand.formulaCode = command.formulaCode;
            command.formulaSettingCommand.detailFormulaSettingCommand.formulaCode = command.formulaCode;
            command.formulaSettingCommand.basicCalculationFormulaCommand.map(item => {
                item.formulaCode = command.formulaCode;
            });
            return command;
        }

        addFormula (command) {
            let self = this;
            block.invisible();
            let lastYmValue = 999912;
            command.formulaSettingCommand.yearMonth.endMonth = lastYmValue;
            service.addFormula(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.getListFormula(command.formulaCode + command.formulaSettingCommand.yearMonth.historyID);
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError({messageId: err.messageId});
            });
        }

        updateFormula (command) {
            let self = this;
            block.invisible();
            service.updateFormula(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.getListFormula(null).done(function(){
                        self.selectedFormulaIdentifier.valueHasMutated();
                    });
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError({messageId: err.messageId});
            });
        }

        updateFormulaSetting (command) {
            let self = this;
            block.invisible();
            service.updateFormulaSetting(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.getListFormula(null).done(function(){
                        self.selectedFormulaIdentifier.valueHasMutated();
                    });
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError({messageId: err.messageId});
            });
        }

        addFormulaHistory (command) {
            let self = this;
            block.invisible();
            service.addFormulaHistory(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.getListFormula(null).done(function(){
                        self.selectedFormulaIdentifier.valueHasMutated();
                    });
                });
            }).fail(function(err) {
                block.clear();
                dialog.alertError({messageId: err.messageId});
            });
        }

        convertToTreeList(formulaData, itemToBeSelect) {
            let self = this;
            formulaData = formulaData.map(function (item) {
                item.nodeText =  item.formulaCode + " " + _.escape(item.formulaName);
                item['identifier'] = item.formulaCode;
                item.history = item.history.map(function (historyItem) {
                    historyItem['identifier'] = item.formulaCode + historyItem.historyID;
                    historyItem.nodeText = moment(historyItem.startMonth, "YYYYMM").format("YYYY/MM") + " ～ " + moment(historyItem.endMonth, "YYYYMM").format("YYYY/MM");
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
            else if (!self.isCompleteStartScreen){
                // selected first formula and history
                var identifier = formulaData[0].history.length > 0 ? formulaData[0].history[0].identifier : formulaData[0].identifier;
                self.selectedFormulaIdentifier(identifier);
                self.isCompleteStartScreen = true;
            } else if (itemToBeSelect) {
                self.selectedFormulaIdentifier(itemToBeSelect);
            }
        }

        changeToNewMode() {
            let self = this;
            self.selectedFormulaIdentifier(null);
            self.selectedFormula(new model.Formula(null));
            self.basicFormulaSetting(new model.BasicFormulaSetting(null));
            self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
            if (self.screenMode()!= model.SCREEN_MODE.NEW) {
                self.getListFormula(null).done(function () {
                })
            }
            self.screenMode(model.SCREEN_MODE.NEW);
            if (self.selectedTab() == 'tab-1'){
                setTimeout(function(){
                    $('#A3_3').focus();
                    // for ie
                    setTimeout(function(){
                        nts.uk.ui.errors.clearAll();
                    }, 50);
                    setTimeout(function(){
                        nts.uk.ui.errors.clearAll();
                    }, 100);
                    setTimeout(function(){
                        nts.uk.ui.errors.clearAll();
                    }, 200);
                    setTimeout(function(){
                        nts.uk.ui.errors.clearAll();
                    }, 300);
                }, 100)
            }
            else self.selectedTab('tab-1');
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
            selectedFormula.formulaName = _.unescape(selectedFormula.formulaName);
            self.selectedFormula(new model.Formula(selectedFormula));
            self.isSelectedHistory(false);
            // if select history
            if (identifier.length > 36) {
                selectedHistoryID = identifier.substring(3, identifier.length);
                selectedHistory = _.find(selectedFormula.history, {historyID: selectedHistoryID});
                if ((self.screenMode() == model.SCREEN_MODE.ADD_HISTORY && selectedFormula.history.length > 1 && selectedFormula.history[1].historyID == selectedHistoryID) || selectedFormula.history.length <= 1) selectedHistory.endMonth = 999912;
                self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                self.isSelectedHistory(true);
                if (self.isCompleteChangeMode) self.showFormulaSettingByHistory(selectedHistory, true, null, selectedHistory.startMonth);
                self.changeToUpdateMode();
            }
            else {
                self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
                if (identifier.length > 0 ){
                    self.screenMode(model.SCREEN_MODE.UPDATE_FORMULA);
                    $("#formula-tree_container").focus();
                    self.basicFormulaSetting(new model.BasicFormulaSetting(null));
                }
            }
        };
        showFormulaSettingByHistory(history, withSetting: boolean, masterUse: number, startMonth: string) {
            let self = this;
            let setting = {historyID: history.historyID, withSetting: withSetting, masterUse: masterUse};
            block.invisible();
            service.getFormulaSettingByHistory(setting).done(function (data) {
                block.clear();
                if (withSetting) {
                    self.basicFormulaSetting(new model.BasicFormulaSetting(data.basicFormulaSettingDto));
                    self.detailFormulaSetting(new model.DetailFormulaSetting(data.detailFormulaSettingDto));
                }
                if (self.selectedFormula().settingMethod() == model.FORMULA_SETTING_METHOD.DETAIL_SETTING)
                    self.screenDViewModel.getFormulaElements(startMonth);
                self.mapListCalculationToMasterUseItem(data.masterUseDto, data.basicCalculationFormulaDto);
                self.selectedTab.valueHasMutated();
            }).fail(function (err) {
                block.clear();
                dialog.alertError({messageId: err.messageId});
            })
        }

        mapListCalculationToMasterUseItem(masterUseItem, basicCalculationFormula) {
            let self = this;
            let fixedMasterUseCode = "";
            self.masterBasicCalculationFormula(new model.BasicCalculationFormula(_.find(basicCalculationFormula, {masterUseCode: fixedMasterUseCode})));
            let formulas = masterUseItem.map(item => {
                let currentFormula = _.find(basicCalculationFormula, {masterUseCode: item.code});
                if (currentFormula){
                    currentFormula.masterUseName = item.name;
                    return new model.BasicCalculationFormula(currentFormula);
                }
                let newFormula = new model.BasicCalculationFormula(null);
                newFormula.masterUseCode(item.code);
                newFormula.masterUseName(item.name);
                return newFormula;
            });

            self.basicCalculationFormulaList(formulas);
        }

        getListFormula(itemToBeSelect): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllFormula().done(function(data){
                block.clear();
                self.convertToTreeList(data, itemToBeSelect);
                dfd.resolve();
                if (self.screenMode() == model.SCREEN_MODE.ADD_HISTORY) {
                    let selectedHistoryID = self.selectedFormulaIdentifier().substring(3, self.selectedFormulaIdentifier().length);
                    let selectedHistory = _.find(ko.toJS(self.selectedFormula).history, {historyID: selectedHistoryID});
                    self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                }
            });
            return dfd.promise();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            return self.getListFormula(null);
        }

        createNewHistory () {
            let self = this;
            let selectedFormula = ko.toJS(self.selectedFormula), formulaList = ko.toJS(self.formulaList);
            let history = _.find(formulaList, {formulaCode: selectedFormula.formulaCode}).history;
            setShared("QMM017_H_PARAMS", { selectedFormula: selectedFormula, history: history });
            modal("/view/qmm/017/h/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_H_RES_PARAMS");
                if (params) {
                    let formulaList = ko.toJS(self.formulaList);
                    let historyID = nts.uk.util.randomId();
                    if (history.length > 0) {
                        let beforeLastMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                        history[0].endMonth = beforeLastMonth.format('YYYYMM');
                    }
                    history.unshift({ historyID: historyID, startMonth: params.startMonth, endMonth: '999912' });
                    formulaList.forEach(function (formula) {
                        if (formula.formulaCode == selectedFormula.formulaCode) {
                            formula.history = history;
                            formula = new model.Formula(formula);
                        }
                    });
                    self.isCompleteChangeMode = false;
                    // to prevent call service for new history
                    // update formula and tree grid
                    self.convertToTreeList(formulaList, selectedFormula.formulaCode + historyID);
                    // clone data
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_LAST_HISTORY && history.length > 1) {
                        self.showFormulaSettingByHistory(history[1], true, null, params.startMonth);
                    } else {
                        self.showFormulaSettingByHistory(history[1], false, params.masterUse, params.startMonth);
                        self.basicFormulaSetting(new model.BasicFormulaSetting({
                            masterUse: params.masterUse,
                            masterBranchUse: params.masterBranchUse,
                            historyID: historyID
                        }));
                        self.detailFormulaSetting(new model.DetailFormulaSetting({
                            roundingMethod: 0,
                            roundingPosition: 0,
                            referenceMonth: 0,
                            detailCalculationFormula: [],
                            historyId: historyID
                        }));
                    }
                    self.isCompleteChangeMode = true;
                    self.screenMode(model.SCREEN_MODE.ADD_HISTORY);
                }
            });
        };
        editHistory () {
            var self = this;
            var selectedFormula = ko.toJS(self.selectedFormula), selectedHistory = ko.toJS(self.selectedHistory), formulaList = ko.toJS(self.formulaList);
            let currentFormula = _.find(formulaList, {formulaCode: selectedFormula.formulaCode});
            let history = currentFormula.history;
            setShared("QMM017_I_PARAMS", { selectedFormula: selectedFormula, history: history, selectedHistory: selectedHistory });
            modal("/view/qmm/017/i/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_I_RES_PARAMS");

                if (params) {
                    if (params.modifyMethod == model.MODIFY_METHOD.DELETE) {
                        if (history.length <= 1) {
                            let currentIndex = _.findIndex(formulaList, currentFormula), formulaToSelect;
                            if (currentIndex == formulaList.length -1 && formulaList.length > 1) formulaToSelect = formulaList[currentIndex-1];
                            else if (currentIndex < formulaList.length -1  && formulaList.length > 1) formulaToSelect = formulaList[currentIndex+1];
                            self.getListFormula(formulaToSelect ? formulaToSelect.formulaCode + formulaToSelect.history[0].historyID : null);
                        }
                        else {
                            self.selectedFormulaIdentifier(selectedFormula ? selectedFormula.formulaCode + history[1].historyID: null);
                            self.getListFormula(null);
                        }
                    } else {
                        self.getListFormula(null).done(function(){
                            self.selectedFormulaIdentifier.valueHasMutated();
                        });
                    }
                }
            });
        };

        doMasterConfiguration () {
            let self = this;
            setShared("QMM017_E_PARAMS", {yearMonth: ko.toJS(self.selectedHistory), basicCalculationFormula: ko.toJS(self.masterBasicCalculationFormula), originalScreen: 'B'});
            modal("/view/qmm/017/e/index.xhtml").onClosed(function () {
                let params = getShared("QMM017_E_RES_PARAMS");
                if (params){
                    params.basicCalculationFormula.calculationFormulaClassification = model.CALCULATION_FORMULA_CLS.FORMULA;
                    self.masterBasicCalculationFormula(new model.BasicCalculationFormula(params.basicCalculationFormula));
                }
                $('.master-config-button').focus();
            });

        };
        // screen C
        doConfiguration (index) {
            let self = this;
            setShared("QMM017_E_PARAMS", {yearMonth: ko.toJS(self.selectedHistory), masterBasicCalculationFormula: ko.toJS(self.masterBasicCalculationFormula), basicCalculationFormula: ko.toJS(self.basicCalculationFormulaList)[index], originalScreen: 'C'});
            modal("/view/qmm/017/e/index.xhtml").onClosed(function () {
                let params = getShared("QMM017_E_RES_PARAMS");
                if (params){
                    let newModel: any = new model.BasicCalculationFormula(params.basicCalculationFormula);
                    self.basicCalculationFormulaList.replace(self.basicCalculationFormulaList()[index], newModel);
                }
                $('#C3_1 tr').eq(index).find('button.small').focus();
            });
        };
        setAllCalculationFormula () {
            let self = this;
            let basicCalculationFormulaList = self.basicCalculationFormulaList();
            basicCalculationFormulaList = basicCalculationFormulaList.map(item => item['calculationFormulaClassification'](model.CALCULATION_FORMULA_CLS.FORMULA));
            self.basicCalculationFormulaList(basicCalculationFormulaList);
            $('#C3_1 .nts-input').ntsError('clear');
        }

        setAllFixedValue () {
            let self = this;
            let basicCalculationFormulaList = self.basicCalculationFormulaList();
            basicCalculationFormulaList = basicCalculationFormulaList.map(item => item['calculationFormulaClassification'](model.CALCULATION_FORMULA_CLS.FIXED_VALUE));
            self.basicCalculationFormulaList(basicCalculationFormulaList);
            $('#C3_1 .nts-input').ntsError('clear');
        }

        initScreenDTabData () {
            let self = this;
            this.screenDViewModel.getFormulaElements(self.selectedHistory().startMonth());
            if (this.screenDViewModel.selectedFormulaCode() == null && this.screenDViewModel.formulaList().length > 0) this.screenDViewModel.selectedFormulaCode(this.screenDViewModel.formulaList()[0]['formulaCode']);
        }
        closeScreenM () {
            nts.uk.ui.windows.close();
        }
    }

}