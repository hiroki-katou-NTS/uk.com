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
        startLastYearMonth: KnockoutObservable<number> = ko.observable(0);
        index: KnockoutObservable<number> = ko.observable(0);
        baseDateNew: KnockoutObservable<any> = ko.observable();
        baseDate: KnockoutObservable<any> = ko.observable();
        baseDateValue: KnockoutObservable<any> = ko.observable();
        end :KnockoutObservable<any> = ko.observable(999912);
        enableSearchButton:KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self.hisIdSelected.subscribe((data) => {
                error.clearAll();
                let self = this;
                if(data == null) {
                    return;
                }
                self.index(self.getIndex(data));
                if (data != '') {
                    if (self.transferMethod() == model.TRANSFER_MOTHOD.TRANSFER && self.hisIdSelected() == HIS_ID_TEMP) {
                        self.getStateLinkSettingMasterPosition(self.listStateCorrelationHisPosition()[FIRST + 1].hisId, self.listStateCorrelationHisPosition()[FIRST + 1].startYearMonth, self.baseDate());
                        self.baseDate(getText('QMM020_39', [self.baseDateNew()]));
                    } else if (self.transferMethod() == model.TRANSFER_MOTHOD.CREATE_NEW && self.hisIdSelected() == HIS_ID_TEMP) {
                        self.getStateLinkSettingMasterPosition(data, self.listStateCorrelationHisPosition()[self.index()].startYearMonth, self.baseDateValue());
                        self.baseDate(getText('QMM020_39', [self.baseDateNew()]));
                    } else {
                        self.getDateBase(data).done(() => {
                            self.getStateLinkSettingMasterPosition(data, self.listStateCorrelationHisPosition()[self.index()].startYearMonth, self.baseDateValue());
                        });
                        self.mode(model.MODE.UPDATE);
                    }
                }
            });

        }

        loadGird() {
            let self = this;
            $("#F3_1").ntsGrid({
                height: '344px',
                dataSource: self.listStateLinkSettingMaster(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {headerText: 'id', key: 'id', dataType: 'number', width: '100', hidden: true},
                    {headerText: getText('QMM020_26'), key: 'masterCode', dataType: 'string', width: '90'},
                    {headerText: getText('QMM020_27'), key: 'categoryName', dataType: 'string', width: '180'},
                    {headerText: getText('QMM020_20'), key: 'open', dataType: 'string', width: '75px', unbound: true, ntsControl: 'SalaryButton'},
                    {headerText: '', key: 'displayE3_4', dataType: 'string', width: '200'},
                    {headerText: getText('QMM020_22'), key: 'open1', dataType: 'string', width: '75px', unbound: true, ntsControl: 'BonusButton'},
                    {headerText: '', key: 'displayE3_5', dataType: 'string', width: '200'},

                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'masterCode', allowResizing: true, minimumWidth: 30
                        }, {
                            columnKey: 'categoryName', allowResizing: true, minimumWidth:30
                        }]
                    },
                    {name: 'Selection', mode: 'row', multipleSelection: true}],
                ntsControls: [
                    {name: 'SalaryButton', text: getText("QMM020_21"), click: function (item) {self.openMScreen(item, 1)}, controlType: 'Button'},
                    {name: 'BonusButton', text: getText("QMM020_21"), click: function (item) {self.openMScreen(item, 2)}, controlType: 'Button'}]
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
                        self.hisIdSelected(null);
                        self.index(FIRST);
                    }
                    self.hisIdSelected(self.listStateCorrelationHisPosition()[self.getIndex(hisId)].hisId);
                } else {
                    self.listStateCorrelationHisPosition([]);
                    self.listStateLinkSettingMaster([]);
                    self.mode(model.MODE.NO_REGIS);
                    self.loadGird();
                    self.enableSearchButton(false);
                }
            }).always(() => {
                block.clear();
                $("#F1_5_container").focus();
            });
        }

        registerPosition() {
            let self = this;
            if (self.mode() == model.MODE.NO_REGIS) {
                return;
            }
            let index = this.getIndex(self.hisIdSelected());
            let data: any = {
                stateLinkSettingMaster: self.listStateLinkSettingMaster(),
                mode: self.mode(),
                hisId: self.hisIdSelected(),
                startYearMonth: self.listStateCorrelationHisPosition()[index].startYearMonth,
                endYearMonth: self.listStateCorrelationHisPosition()[index].endYearMonth,
                baseDate: moment.utc(self.baseDateValue(), 'YYYY/MM/DD').toISOString()
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
            return (this.mode() == model.MODE.NO_REGIS || this.mode() == model.MODE.NO_EXIST);
        }

        enableNew() {
            let self = this;
            if (self.listStateCorrelationHisPosition().length > 0) {
                return !(self.mode() == model.MODE.NEW || (self.listStateCorrelationHisPosition()[FIRST].hisId == HIS_ID_TEMP));
            }
            return self.mode() != model.MODE.NEW && self.mode() != model.MODE.NO_EXIST;
        }

        enableEdit() {
            return this.mode() == model.MODE.UPDATE;
        }

        getStateLinkSettingMasterPosition(hisId: string, startYeaMonth: number, date: any) {
            block.invisible();
            let self = this;
            let data = {
                hisId: hisId,
                startYearMonth: startYeaMonth,
                date: moment.utc(date, 'YYYY/MM/DD').toISOString()
            };
            service.getStateLinkSettingMasterPosition(data).done((stateLinkSettingMaster: Array<model.StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(model.convertToDisplay(stateLinkSettingMaster));
                    self.mode(model.MODE.UPDATE);
                    if(self.hisIdSelected() == HIS_ID_TEMP ) {
                        self.mode(model.MODE.NEW);
                    }
                } else {
                    self.listStateLinkSettingMaster([]);
                    dialog.info({ messageId: "Msg_306" }).then(() => {
                        self.mode(model.MODE.NO_EXIST);
                    });
                }
                self.loadGird();
            }).fail(function(err) {
                dialog.alertError(err);
            }).always(() => {
                block.clear();
            });
        }

        getDateBase(hisId: string): JQueryPromise<any> {
            let dfd = $.Deferred();
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
            let self = this;
            let index = this.getIndex(self.hisIdSelected());
            let salaryCode = item.displayE3_4.split('    ')[0];
            let bonusCode = item.displayE3_5.split('    ')[0];
            setShared(model.PARAMETERS_SCREEN_M.INPUT, {
                startYearMonth: self.listStateCorrelationHisPosition()[index].startYearMonth,
                statementCode: code === 1 ? salaryCode : bonusCode,
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
        }

        openJScreen() {

            let self = this;
            let start = self.startLastYearMonth();
            if (self.listStateCorrelationHisPosition() && self.listStateCorrelationHisPosition().length > 0) {
                start = self.listStateCorrelationHisPosition()[FIRST].startYearMonth;
            }
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                startYearMonth: start,
                baseDate: self.baseDateValue(),
                isPerson: false,
                modeScreen: model.MODE_SCREEN.POSITION
            });
            modal("/view/qmm/020/j/index1.xhtml").onClosed(() => {
                let params = getShared(model.PARAMETERS_SCREEN_J.OUTPUT);
                if (params) {
                    self.transferMethod(params.transferMethod);
                    self.baseDateValue(params.baseDate);
                    self.baseDateNew(params.baseDate);
                    self.listStateCorrelationHisPosition.unshift(self.createStateCorrelationHisPosition(params.start, self.end()));
                    self.hisIdSelected(HIS_ID_TEMP);
                    self.enableSearchButton(true);
                }
                $("#F1_5_container").focus();
            });

        }

        openKScreen() {

            let self = this;
            let index = _.findIndex(self.listStateCorrelationHisPosition(), {hisId: self.hisIdSelected()});
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
                startYearMonth: self.listStateCorrelationHisPosition()[self.index()].startYearMonth,
                endYearMonth: self.listStateCorrelationHisPosition()[self.index()].endYearMonth,
                hisId: self.hisIdSelected(),
                startLastYearMonth: laststartYearMonth,
                baseDate: self.baseDateValue(),
                canDelete: canDelete,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.POSITION,
                isFirst: index === 0 && self.listStateCorrelationHisPosition().length > 1 ? true : false
            });
            modal("/view/qmm/020/k/index.xhtml").onClosed(function () {
                let params = getShared(model.PARAMETERS_SCREEN_K.OUTPUT);
                if (params && params.modeEditHistory == model.EDIT_METHOD.UPDATE) {
                    service.getStateCorrelationHisPosition().done((listStateCorrelationHisPosition: Array<StateCorrelationHisPosition>)=>{
                        if(listStateCorrelationHisPosition && listStateCorrelationHisPosition.length > 0){
                            self.listStateCorrelationHisPosition(StateCorrelationHisPosition.convertToDisplay(listStateCorrelationHisPosition));
                            self.getDateBase(self.hisIdSelected()).done(() => {
                                self.getStateLinkSettingMasterPosition(self.hisIdSelected(), self.listStateCorrelationHisPosition()[self.index()].startYearMonth, self.baseDateValue());
                            });
                        }

                    }).fail((err)=>{
                        if(err) dialog.alertError(err);
                    });
                }
                if (params && params.modeEditHistory == model.EDIT_METHOD.DELETE) {
                    self.initScreen(null);
                }

                $("#F1_5_container").focus();
            });

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