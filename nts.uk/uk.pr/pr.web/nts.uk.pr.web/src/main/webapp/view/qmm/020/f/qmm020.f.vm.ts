module nts.uk.pr.view.qmm020.f.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {

        listStateCorrelationHisPosition: KnockoutObservableArray<StateCorrelationHisPosition> = ko.observableArray([]);
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        mode: KnockoutObservable<number> = ko.observable(2);
        listStateLinkSettingMaster: KnockoutObservableArray<model.StateLinkSettingMaster> = ko.observableArray([]);
        transferMethod: KnockoutObservable<number> = ko.observable();
        startYearMonth: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable(999912);
        startLastYearMonth: KnockoutObservable<number> = ko.observable(0);
        index: KnockoutObservable<number> = ko.observable(0);
        baseDateNew: KnockoutObservable<any> = ko.observable();
        baseDate: KnockoutObservable<any> = ko.observable();
        baseDateValue: KnockoutObservable<any> = ko.observable();

        constructor() {
            let self = this;
            self.initScreen(null);
            self.hisIdSelected.subscribe((data) => {
                error.clearAll();
                let self = this;
                self.index(self.getIndex(data));
                if (data != '') {
                    if (self.transferMethod() == model.TRANSFER_MOTHOD.TRANSFER && self.hisIdSelected() == HIS_ID_TEMP) {
                        self.getStateLinkSettingMasterPosition(self.listStateCorrelationHisPosition()[FIRST + 1].hisId, self.listStateCorrelationHisPosition()[FIRST + 1].startYearMonth, self.baseDate());
                        self.baseDate(getText('QMM020_39', [model.convertMonthYearToString(self.baseDateNew())]));
                    } else if (self.transferMethod() == model.TRANSFER_MOTHOD.CREATE_NEW && self.hisIdSelected() == HIS_ID_TEMP) {
                        self.getStateLinkSettingMasterPosition(data, self.listStateCorrelationHisPosition()[self.index()].startYearMonth, self.baseDateValue());
                        self.baseDate(getText('QMM020_39', [model.convertMonthYearToString(self.baseDateNew())]));
                    } else {
                        self.getDateBase(data).done(() => {
                            self.getStateLinkSettingMasterPosition(data, self.listStateCorrelationHisPosition()[self.index()].startYearMonth, self.baseDateValue());
                        });
                        self.startYearMonth(self.listStateCorrelationHisPosition()[self.index()].startYearMonth);
                        self.endYearMonth(self.listStateCorrelationHisPosition()[self.index()].endYearMonth);
                        self.mode(model.MODE.UPDATE);
                    }
                }
            });

        }

        loadGird() {
            let self = this;
            $("#F3_1").ntsGrid({
                height: '320px',
                dataSource: self.listStateLinkSettingMaster(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {headerText: getText('QMM020_26'), key: 'id', dataType: 'number', width: '100', hidden: true},
                    {headerText: getText('QMM020_26'), key: 'masterCode', dataType: 'string', width: '100'},
                    {headerText: getText('QMM020_27'), key: 'categoryName', dataType: 'string', width: '200'},
                    {
                        headerText: getText('QMM020_20'),
                        key: 'salary',
                        dataType: 'string',
                        width: '80px',
                        unbound: true,
                        ntsControl: 'Salary'
                    },
                    {headerText: '', key: 'displayE3_4', dataType: 'string', width: '170'},
                    {
                        headerText: getText('QMM020_22'),
                        key: 'bonus',
                        dataType: 'string',
                        width: '80px',
                        unbound: true,
                        ntsControl: 'Bonus'
                    },
                    {headerText: '', key: 'displayE3_5', dataType: 'string', width: '170'},

                ],
                features: [
                    {
                        name: 'Sorting',
                        type: 'local'
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }],
                ntsControls: [
                    {
                        name: 'Salary', text: getText("QMM020_21"), click: function (item) {
                            self.openMScreen(item, 1)
                        }, controlType: 'Button'
                    },
                    {
                        name: 'Bonus', text: getText("QMM020_21"), click: function (item) {
                            self.openMScreen(item, 2)
                        }, controlType: 'Button'
                    }]
            });
            $("#F3_1").setupSearchScroll("igGrid", true);
        }

        initScreen(hisId: string) {
            let self = this;
            block.invisible();
            service.getStateCorrelationHisPosition().done((listStateCorrelationHisPosition: Array<StateCorrelationHisPosition>) => {
                if (listStateCorrelationHisPosition && listStateCorrelationHisPosition.length > 0) {
                    self.listStateCorrelationHisPosition(StateCorrelationHisPosition.convertToDisplay(listStateCorrelationHisPosition));
                    if (hisId == null) {
                        self.index(FIRST);
                        self.hisIdSelected(self.listStateCorrelationHisPosition()[FIRST].hisId);
                    }
                    self.hisIdSelected(self.listStateCorrelationHisPosition()[self.getIndex(hisId)].hisId);
                } else {
                    self.mode(model.MODE.NO_REGIS);
                    self.loadGird();
                }
            }).always(() => {
                block.clear();
            });
            block.clear();

        }

        registerPosition() {
            let self = this;
            if (self.mode() == model.MODE.NO_REGIS) {
                return;
            }

            let data: any = {
                stateLinkSettingMaster: self.listStateLinkSettingMaster(),
                mode: self.mode(),
                hisId: self.hisIdSelected(),
                startYearMonth: self.startYearMonth(),
                endYearMonth: self.endYearMonth(),
                baseDate: self.baseDateValue
            }
            block.invisible();
            service.registerCorrelationHisPosition(data).done(() => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    self.transferMethod(null);
                    self.initScreen(self.hisIdSelected());
                });
            }).fail(function (res: any) {
                if (res)
                    dialog.alertError(res);
            }).always(() => {
                block.clear();
            });
            $("#F3_1").focus();

        }

        enableRegis() {
            return this.mode() == model.MODE.NO_REGIS;
        }

        enableNew() {
            return this.mode() == model.MODE.NEW;
        }

        enableEdit() {
            return this.mode() == model.MODE.UPDATE;
        }

        getStateLinkSettingMasterPosition(hisId: string, startYeaMonth: number, date: any) {
            block.invisible();
            let self = this;
            let data = {
                hisId: hisId,
                startYeaMonth: startYeaMonth,
                date: moment.utc(date, 'YYYY/MM/DD').toISOString()
            };
            service.getStateLinkSettingMasterPosition(data).done((stateLinkSettingMaster: Array<StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(model.convertToDisplay(stateLinkSettingMaster));
                    self.mode(model.MODE.UPDATE);
                    if (hisId == HIS_ID_TEMP) {
                        self.mode(model.MODE.NEW);
                    }
                } else {
                    self.mode(model.MODE.NO_REGIS);
                }
                self.loadGird();
            }).always(() => {
                block.clear();
            });
        }

        getDateBase(hisId: string): JQueryPromise<any> {
            dfd = $.Deferred();
            let self = this;
            service.getDateBase(hisId).done((item: any) => {
                if (item) {
                    self.baseDateValue(item.date);
                    self.baseDate(getText('QMM020_39', [item.date]));
                }
                dfd.resolve();
            }).fail(function (err) {
                dfd.reject();
                dialog.alertError(err);
            });
            return dfd.promise();
        }

        findItem(masterCode) {
            let self = this;
            let temp = _.findIndex(self.listStateLinkSettingMaster(), function (x) {
                return x.masterCode == masterCode;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        updateLinkSettingMaster(statementCode: string, statementName: string, position: number, code: number) {
            let self = this;
            if (code == 1) {
                self.listStateLinkSettingMaster()[position].salaryCode = statementCode;
                self.listStateLinkSettingMaster()[position].salaryName = statementName;
                self.listStateLinkSettingMaster()[position].displayE3_4 = model.displayCodeAndName(statementCode, statementName);
            } else {
                self.listStateLinkSettingMaster()[position].bonusCode = statementCode;
                self.listStateLinkSettingMaster()[position].bonusName = statementName;
                self.listStateLinkSettingMaster()[position].displayE3_5 = model.displayCodeAndName(statementCode, statementName);
            }
            self.loadGird();
        }

        openMScreen(item, code) {
            block.invisible();
            let self = this;
            setShared(model.PARAMETERS_SCREEN_M.INPUT, {
                startYearMonth: self.startYearMonth(),
                modeScreen: model.MODE_SCREEN.POSITION
            });
            modal("/view/qmm/020/m/index.xhtml").onClosed(() => {
                let params = getShared(model.PARAMETERS_SCREEN_M.OUTPUT);
                if (params) {
                    let index: number;
                    index = self.findItem(item.masterCode);
                    self.updateLinkSettingMaster(params.statementCode, params.statementName, index, code);
                }

            });
            block.clear();
        }

        openJScreen() {
            block.invisible();
            let self = this;
            let start = self.startLastYearMonth();
            if (self.listStateCorrelationHisPosition() && self.listStateCorrelationHisPosition().length > 0) {
                start = self.listStateCorrelationHisPosition()[FIRST].startYearMonth;
            }
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                startYearMonth: start,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.POSITION
            });
            modal("/view/qmm/020/j/index.xhtml").onClosed(() => {
                let params = getShared(model.PARAMETERS_SCREEN_J.OUTPUT);
                if (params) {
                    self.transferMethod(params.transferMethod);
                    self.baseDateValue(params.baseDate);
                    self.baseDateNew(params.baseDate);
                    self.listStateCorrelationHisPosition.unshift(self.createStateCorrelationHisPosition(params.start, params.end));
                    self.hisIdSelected(HIS_ID_TEMP);
                }

            });
            block.clear();
        }

        openKScreen() {
            let self = this;
            self.index(self.getIndex(self.hisIdSelected()));
            let laststartYearMonth: number = 0;
            if (self.listStateCorrelationHisPosition() && self.listStateCorrelationHisPosition().length != self.index() + 1) {
                laststartYearMonth = self.listStateCorrelationHisPosition().length > 1 ? self.listStateCorrelationHisPosition()[self.index() + 1].startYearMonth : 0;
            }
            let canDelete: boolean = false;
            if (self.listStateCorrelationHisPosition().length > 1 && self.hisIdSelected() == self.listStateCorrelationHisPosition()[FIRST].hisId) {
                canDelete = true;
            }

            setShared(model.PARAMETERS_SCREEN_K.INPUT, {
                startYearMonth: self.startYearMonth(),
                endYearMonth: self.endYearMonth(),
                hisId: self.hisIdSelected(),
                startLastYearMonth: laststartYearMonth,
                canDelete: canDelete
            });
            modal("/view/qmm/011/k/index.xhtml").onClosed(function () {
                let params = getShared(model.PARAMETERS_SCREEN_K.OUTPUT);
                if (params && params.methodEditing == 1) {
                    self.initScreen(self.hisIdSelected());
                }
                if (params && params.methodEditing == 0) {
                    self.initScreen(null);
                }
                $('#F2_1').focus();

            });
            block.clear();
        }

        getIndex(hisId: string) {
            let self = this;
            let temp = _.findIndex(self.listStateCorrelationHisPosition(), function (x) {
                return x.hisId == hisId;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        createStateCorrelationHisPosition(start: number, end: number) {
            let self = this;
            if (self.listStateCorrelationHisPosition() && self.listStateCorrelationHisPosition().length > 0) {
                let end = Number(start.toString().slice(4, 6)) == 1 ? (start - 89) : (start - 1);
                self.listStateCorrelationHisPosition()[FIRST].display = getText('QMM020_16',
                    [model.convertMonthYearToString(self.listStateCorrelationHisPosition()[FIRST].startYearMonth), model.convertMonthYearToString(end)]);
            }
            let stateCorrelationHisPosition: StateCorrelationHisPosition = new StateCorrelationHisPosition();
            stateCorrelationHisPosition.hisId = HIS_ID_TEMP;
            stateCorrelationHisPosition.startYearMonth = start;
            stateCorrelationHisPosition.endYearMonth = end;
            stateCorrelationHisPosition.display = getText('QMM020_16', [model.convertMonthYearToString(start), model.convertMonthYearToString(end)]);

            return stateCorrelationHisPosition;
        }

    }

    export class StateCorrelationHisPosition {
        hisId: string;
        startYearMonth: number;
        endYearMonth: number;
        display: string;

        constructor() {

        }

        static convertToDisplay(item) {
            let listPosition = [];
            _.each(item, (item) => {
                let dto: StateCorrelationHisPosition = new StateCorrelationHisPosition();
                dto.hisId = item.hisId;
                dto.startYearMonth = item.startYearMonth;
                dto.endYearMonth = item.endYearMonth;
                dto.display = getText('QMM020_16', [model.convertMonthYearToString(item.startYearMonth), model.convertMonthYearToString(item.endYearMonth)]);
                listPosition.push(dto);
            });
            return listPosition;
        }
    }
    export const FIRST = 0;

    export const HIS_ID_TEMP = "00000";

}