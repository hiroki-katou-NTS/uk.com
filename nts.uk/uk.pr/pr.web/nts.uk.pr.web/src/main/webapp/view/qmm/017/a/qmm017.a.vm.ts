module nts.uk.pr.view.qmm017.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm017.share.model

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
        constructor() {
            var self = this;
            self.initTabPanel();
            self.screenMode.subscribe(newValue => {
                self.isNewMode(newValue == model.SCREEN_MODE.NEW);
                self.isUpdateMode(newValue == model.SCREEN_MODE.UPDATE);
                self.isAddHistoryMode(newValue == model.SCREEN_MODE.ADD_HISTORY);
            })
            self.selectedFormulaIdentifier.subscribe(newValue => {
                if (newValue) {
                    self.showWageTableInfoByValue(newValue);
                } else {
                    self.changeToNewMode();
                }
            });
            self.initFormulaData();
        }

        initTabPanel() {
            let self = this;
            self.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: getText('QMM017_6'),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: getText('QMM017_7'),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }

        initFormulaData() {
            let self = this;
            let formulaData = [{
                formulaCode: '001',
                formulaName: 'Formula 1',
                settingMethod: 0,
                nestedAtr: 0,
                history: [
                    {startMonth: '201711', endMonth: '999912', historyID: nts.uk.util.randomId()},
                    {startMonth: '201705', endMonth: '201710', historyID: nts.uk.util.randomId()},
                    {startMonth: '201701', endMonth: '201704', historyID: nts.uk.util.randomId()}
                ]
            },
                {
                    formulaCode: '002',
                    formulaName: 'Formula 2',
                    settingMethod: 1,
                    nestedAtr: 1,
                    history: [
                        {startMonth: '201811', endMonth: '999912', historyID: nts.uk.util.randomId()},
                        {startMonth: '201805', endMonth: '201810', historyID: nts.uk.util.randomId()},
                        {startMonth: '201881', endMonth: '201804', historyID: nts.uk.util.randomId()}
                    ]
                },
                {
                    formulaCode: '003',
                    formulaName: 'Formula 3',
                    settingMethod: 0,
                    nestedAtr: 1,
                    history: [
                        {startMonth: '201911', endMonth: '999912', historyID: nts.uk.util.randomId()},
                        {startMonth: '201905', endMonth: '201910', historyID: nts.uk.util.randomId()},
                        {startMonth: '201901', endMonth: '201904', historyID: nts.uk.util.randomId()}
                    ]
                },
                {
                    formulaCode: '004',
                    formulaName: 'Formula 4',
                    settingMethod: 1,
                    nestedAtr: 0,
                    history: [
                        {startMonth: '202011', endMonth: '999912', historyID: nts.uk.util.randomId()},
                        {startMonth: '202005', endMonth: '202010', historyID: nts.uk.util.randomId()},
                        {startMonth: '202001', endMonth: '202004', historyID: nts.uk.util.randomId()}
                    ]
                }
            ];
            self.convertToTreeList(formulaData);
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
                // selected first wage table and history
                var identifier = formulaData[0].history.length > 0 ? formulaData[0].history[0].identifier : formulaData[0].identifier;
                self.selectedFormulaIdentifier(identifier);
                self.selectedFormulaIdentifier.valueHasMutated();
            }
        }

        changeToNewMode() {
            let self = this;
            self.screenMode(model.SCREEN_MODE.NEW);
            self.selectedFormulaIdentifier(null);
            self.selectedFormula(new model.Formula(null));
            self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
            self.selectedTab('tab-1');
            nts.uk.ui.errors.clearAll();
        }

        changeToUpdateMode() {
            var self = this;
            self.screenMode(model.SCREEN_MODE.UPDATE);
            self.selectedTab('tab-1');
            nts.uk.ui.errors.clearAll();
        }

        changeToAddHistoryMode() {
            var self = this;
            self.screenMode(model.SCREEN_MODE.ADD_HISTORY);
            self.selectedTab('tab-2');
            nts.uk.ui.errors.clearAll();
        }

        showWageTableInfoByValue(identifier) {
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
            }
            else {
                self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
            }
            self.changeToUpdateMode();
        };

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
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
                    // update wage table and tree grid
                    self.convertToTreeList(formulaList);
                    self.selectedFormula(selectedFormula.wageTableCode + historyID);
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
                    // reload data
                    // change selected value
                    if (params.modifyMethod == model.MODIFY_METHOD.DELETE) {
                        if (history.length <= 1) {
                            self.selectedFormulaIdentifier(selectedFormula.wageTableCode);
                        }
                        else {
                            self.selectedFormulaIdentifier(selectedFormula.wageTableCode + history[1].historyID);
                        }
                    }
                }
            });
        };
    }

}