module nts.uk.pr.view.qmm016.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        // screen state
        isOnStartUp: boolean = true;
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(false);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);

        // tab panel
        tabs: any;
        selectedTab: any;

        // screen item
        // left panel
        selectedWageTableIdentifier: KnockoutObservable<string> = ko.observable(null);
        selectedWageTable: KnockoutObservable<model.WageTable> = ko.observable(new model.WageTable(null));
        selectedHistory: KnockoutObservable<model.GenericHistoryYearMonthPeriod> = ko.observable(new model.GenericHistoryYearMonthPeriod(null));
        wageTableTreeList: any = ko.observable();
        constructor() {
            let self = this;
            self.initTabPanel();
            self.getWageTableList();
            self.selectedWageTableIdentifier.subscribe(function (newValue){
                if (newValue) self.showWageTableInfoByValue(newValue);
            });
            self.screenMode.subscribe(function (newValue){
                self.isUpdateMode(newValue == model.SCREEN_MODE.UPDATE);
            })
            $('#A8_2').ntsFixedTable({width: 300});
        }

        getWageTableList () {
            let self = this;
            let wageTableData: Array<any> = [
                {
                    wageTableCode: '001',
                    wageTableName: 'Wage Table 1',
                    elementInformation: null,
                    elementSetting: 0,
                    remarkInformation: 'Nothing to write here 1',
                    history: [
                        {startMonth: '201811', endMonth: '999912', historyID: nts.uk.util.randomId()},
                        {startMonth: '201805', endMonth: '201810', historyID: nts.uk.util.randomId()},
                        {startMonth: '201801', endMonth: '201804', historyID: nts.uk.util.randomId()}
                    ]
                },
                {
                    wageTableCode: '002',
                    wageTableName: 'Wage Table 2',
                    elementInformation: {
                        firstDimensionElement: {
                            masterNumericClassification: 0,
                            fixedElement: 6,
                            optionalAdditionalElement: 'BBB'
                        },
                        secondDimensionElement: null,
                        firstDimensionElement: null
                    },
                    elementSetting: 1,
                    remarkInformation: 'Nothing to write here 2',
                    history: [
                        {startMonth: '201911', endMonth: '999912', historyID: nts.uk.util.randomId()},
                        {startMonth: '201905', endMonth: '201910', historyID: nts.uk.util.randomId()},
                        {startMonth: '201901', endMonth: '201904', historyID: nts.uk.util.randomId()}
                    ]
                },
                {
                    wageTableCode: '003',
                    wageTableName: 'Wage Table 3',
                    elementInformation: {
                        firstDimensionElement: {
                            masterNumericClassification: 1,
                            fixedElement: 2,
                            optionalAdditionalElement: 'CCC'
                        },
                        secondDimensionElement: null,
                        firstDimensionElement: null
                    },
                    elementSetting: 2,
                    remarkInformation: 'Nothing to write here 3',
                    history: [
                        {startMonth: '202011', endMonth: '999912', historyID: nts.uk.util.randomId()},
                        {startMonth: '202005', endMonth: '202010', historyID: nts.uk.util.randomId()},
                        {startMonth: '202001', endMonth: '202004', historyID: nts.uk.util.randomId()}
                    ]
                }
            ]
            if (wageTableData.length == 0) self.changeToNewMode();
            self.convertToTreeList(wageTableData)
        }

        convertToTreeList (wageTableData) {
            let self = this;
            let wageTableTreeData = wageTableData.map(function (item){
                item.nodeText = item.wageTableCode + " " + item.wageTableName;
                item.identifier = item.wageTableCode;
                item.history = item.history.map(function (historyItem){
                    historyItem.nodeText = historyItem.startMonth + " ~ " + historyItem.endMonth;
                    historyItem.identifier = item.wageTableCode + historyItem.historyID;
                    // prevent handler from null value exception when use search box
                    historyItem.wageTableCode = "";
                    historyItem.wageTableName = "";
                    return historyItem;
                })
                return item;
            })
            self.wageTableTreeList(wageTableTreeData);
        }

        initTabPanel () {
            let self = this;
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: getText("QMM016_11"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText("QMM016_12"), content: '.tab-content-2', enable: self.isUpdateMode, visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        showWageTableInfoByValue (identifier: string) {
            let self = this;
            let currentWageTableList = ko.toJS(self.wageTableTreeList), selectedWageTableCode, selectedHistoryID, selectedWageTable, selectedHistory;
            selectedWageTableCode = identifier.substring(0, 3);
            selectedWageTable = _.find(currentWageTableList, {wageTableCode: selectedWageTableCode});
            self.selectedWageTable(new model.WageTable(selectedWageTable));
            self.isSelectedHistory(false);
            // if select history
            if (identifier.length > 36) {
                selectedHistoryID = identifier.substring(3, identifier.length);
                selectedHistory = _.find(selectedWageTable.history, {historyID: selectedHistoryID});
                self.selectedHistory(new model.GenericHistoryYearMonthPeriod(selectedHistory));
                self.isSelectedHistory(true);
            }
            self.changeToUpdateMode();
        }

        showSettingDataByValue (identifier: string) {
            let self = this;
            // TODO
        }

        createNewWageTable () {
            let self = this;
            self.changeToNewMode();
        }
        registerWageTable () {

        }
        copy () {
            // TODO
        }
        outputExcel () {
            // TODO
        }
        settingQualificationGroup () {
            // TODO
        }
        correctMasterDialog () {
            // TODO
        }
        changeToNewMode () {
            let self = this;
            self.screenMode(model.SCREEN_MODE.NEW);
            self.selectedWageTableIdentifier(null);
            self.selectedWageTable(new model.WageTable(null));
            self.selectedHistory(new model.GenericHistoryYearMonthPeriod(null));
            self.selectedTab('tab-1');
            nts.uk.ui.errors.clearAll();
        }
        changeToUpdateMode () {
            let self = this;
            self.screenMode(model.SCREEN_MODE.UPDATE);
            nts.uk.ui.errors.clearAll();
        }

        createNewHistory () {
            let self = this;
            let selectedWageTable = ko.toJS(self.selectedWageTable);
            let history = selectedWageTable.history;
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
                            wageTable.history = history;
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
                    self.isUpdateMode(false);
                }
            });
        }
        editHistory () {
            let self = this;
            let selectedWageTable = ko.toJS(self.selectedWageTable), selectedHistory = ko.toJS(self.selectedHistory);
            let history = selectedWageTable.history;
            setShared("QMM016_J_PARAMS", { selectedWageTable: selectedWageTable, history: history, selectedHistory: selectedHistory });
            modal("/view/qmm/016/j/index.xhtml").onClosed(() => {
                let params = getShared("QMM016_J_RES_PARAMS");
                if (params) {
                    self.getWageTableList();
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

        selectElement (dimension) {
            let self = this;
            setShared("QMM016_G_PARAMS", {});
            modal("/view/qmm/016/g/index.xhtml").onClosed(() => {
                let params = getShared("QMM016_G_RES_PARAMS");
                if (params) {
                    console.log(params);
                }
            });
        }

    }
}

