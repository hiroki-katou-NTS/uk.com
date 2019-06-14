module nts.uk.pr.view.qmm020.g.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {

        listStateCorrelationHisSalary: KnockoutObservableArray<StateCorrelationHisSalary> =  ko.observableArray([]);
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        mode: KnockoutObservable<number> = ko.observable(2);
        listStateLinkSettingMaster: KnockoutObservableArray<model.StateLinkSettingMaster> =  ko.observableArray([]);
        transferMethod: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable(999912);
        index: KnockoutObservable<number> = ko.observable(0);

        constructor(){
            let self = this;
            self.hisIdSelected.subscribe((data) => {
                error.clearAll();
                if(data == null) {
                    return;
                }
                let self = this;
                self.index(self.getIndex(data));
                if (data != '') {
                    if(self.transferMethod() == model.TRANSFER_MOTHOD.TRANSFER && self.hisIdSelected() == HIS_ID_TEMP) {
                        self.getStateLinkSettingMasterSalary(self.listStateCorrelationHisSalary()[FIRST + 1].hisId, self.listStateCorrelationHisSalary()[FIRST + 1].startYearMonth);
                    } else {
                        self.getStateLinkSettingMasterSalary(data, self.listStateCorrelationHisSalary()[self.index()].startYearMonth);
                    }
                }
            });

        }

        loadGird(){
            let self = this;
            $("#G3_1").ntsGrid({
                height: '340px',
                dataSource: self.listStateLinkSettingMaster(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('QMM020_26'), key: 'id', dataType: 'number', width: '100' , hidden: true},
                    { headerText: getText('QMM020_26'), key: 'masterCode', dataType: 'string', width: '90' },
                    { headerText: getText('QMM020_27'), key: 'categoryName',dataType: 'string', width: '180' },
                    { headerText: getText('QMM020_20'), key: 'selectSalary', dataType: 'string', width: '75px', unbound: true, ntsControl: 'SalaryButton' },
                    { headerText: '', key: 'displayE3_4', dataType: 'string', width: '200'},
                    { headerText: getText('QMM020_22'), key: 'selectBonus', dataType: 'string', width: '75px', unbound: true, ntsControl: 'BonusButton' },
                    { headerText: '', key: 'displayE3_5', dataType: 'string',width: '200' },

                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'masterCode', allowResizing: true, minimumWidth: 30
                        }, {
                            columnKey: 'categoryName', allowResizing: true, minimumWidth:30
                        }]
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }],
                ntsControls: [
                    { name: 'SalaryButton', text: getText("QMM020_21"), click: function(item) { self.openMScreen(item, 1) }, controlType: 'Button', enable:  true },
                    { name: 'BonusButton', text: getText("QMM020_21"), click: function(item) { self.openMScreen(item, 2) }, controlType: 'Button', enable: true }]
            });
            $("#G3_1").setupSearchScroll("igGrid", true);
        }

        enableSelectSalary(enable: boolean){
            if(enable) {
                $("#G3_1").ntsGrid("enableNtsControls", 'selectSalary', 'Button');
                $("#G3_1").ntsGrid("enableNtsControls", 'selectBonus', 'Button');
            } else {
                $("#G3_1").ntsGrid("disableNtsControls", 'selectSalary', 'Button');
                $("#G3_1").ntsGrid("disableNtsControls", 'selectBonus', 'Button');
            }
        }

        initScreen(hisId: string){
            let self = this;
            block.invisible();
            service.getStateCorrelationHisSalary().done((listStateCorrelationHisSalary: Array<StateCorrelationHisSalary>) => {
                if (listStateCorrelationHisSalary && listStateCorrelationHisSalary.length > 0) {
                    self.listStateCorrelationHisSalary(StateCorrelationHisSalary.convertToDisplay(listStateCorrelationHisSalary));
                    if (hisId == null) {
                        self.hisIdSelected(null);
                        self.index(FIRST);
                    }
                    self.hisIdSelected(self.listStateCorrelationHisSalary()[self.getIndex(hisId)].hisId);
                } else {
                    self.listStateCorrelationHisSalary([]);
                    self.getStateLinkSettingMasterSalary("0",0).done(()=> {
                        self.enableSelectSalary(false);
                        self.mode(model.MODE.NO_REGIS);
                    });
                }
            }).always(() => {
                block.clear();
                $("#G1_5_container").focus();
            });
        }

        registerSalary(){
            let self = this;
            if (self.mode() == model.MODE.NO_REGIS) {
                return;
            }

            let data: any = {
                stateLinkSettingMaster: self.listStateLinkSettingMaster(),
                mode: self.mode(),
                hisId: self.hisIdSelected(),
                startYearMonth: self.listStateCorrelationHisSalary()[self.index()].startYearMonth,
                endYearMonth: self.listStateCorrelationHisSalary()[self.index()].endYearMonth,
            }
            block.invisible();
            service.registerCorrelationHisSalary(data).done(() => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.transferMethod(null);
                    self.initScreen(self.hisIdSelected());
                });
            }).fail(function(res: any) {
                if (res)
                    dialog.alertError(res);
            }).always(() => {
                block.clear();
            });
            $("#G3_1").focus();

        }

        getStateLinkSettingMasterSalary(hisId: string, startYeaMonth: number): JQueryPromise<any>{
            block.invisible();
            let dfd = $.Deferred();
            let self = this;
            service.getStateLinkSettingMasterSalary(hisId, startYeaMonth).done((stateLinkSettingMaster: Array<model.StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(model.convertToDisplay(stateLinkSettingMaster));
                    self.mode(model.MODE.UPDATE);
                    if (self.hisIdSelected() == HIS_ID_TEMP) {
                        self.mode(model.MODE.NEW);
                    }
                } else {
                    dialog.info({messageId: "MsgQ_247"}).then(() => {
                        self.mode(model.MODE.NO_EXIST);
                    });
                }
                self.loadGird();
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        findItem(masterCode){
            let self = this;
            let temp = _.findIndex(self.listStateLinkSettingMaster(), function(x) {
                return x.masterCode == masterCode;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        enableRegis() {
            return (this.mode() == model.MODE.NO_REGIS || this.mode() == model.MODE.NO_EXIST);
        }

        enableNew() {
            let self = this;
            if (self.listStateCorrelationHisSalary().length > 0) {
                return !(self.mode() == model.MODE.NEW || (self.listStateCorrelationHisSalary()[FIRST].hisId == HIS_ID_TEMP));
            }
            return self.mode() != model.MODE.NEW && self.mode() != model.MODE.NO_EXIST;
        }

        enableEdit(){
            return this.mode() == model.MODE.UPDATE;
        }

        updateLinkSettingMaster(statementCode :string, statementName: string, position: number, code: number ){
            let self = this;
            if(code == 1) {
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
            let salaryCode = item.displayE3_4.split('    ')[0];
            let bonusCode = item.displayE3_5.split('    ')[0];
            let index = this.getIndex(self.hisIdSelected());
            setShared(model.PARAMETERS_SCREEN_M.INPUT, {
                startYearMonth: self.listStateCorrelationHisSalary()[index].startYearMonth,
                statementCode: code === 1 ? salaryCode : bonusCode,
                modeScreen: model.MODE_SCREEN.POSITION
            });
            modal("/view/qmm/020/m/index.xhtml").onClosed(() =>{
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
            let start = 0;
            if(self.listStateCorrelationHisSalary() && self.listStateCorrelationHisSalary().length > 0) {
                start = self.listStateCorrelationHisSalary()[FIRST].startYearMonth;
            }
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                startYearMonth: start,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.SALARY
            });
            modal("/view/qmm/020/j/index.xhtml").onClosed(() =>{
                let params = getShared(model.PARAMETERS_SCREEN_J.OUTPUT);
                if (params) {
                    self.transferMethod(params.transferMethod);
                    self.listStateCorrelationHisSalary.unshift(self.createStateCorrelationHisSalary(params.start, self.endYearMonth()));
                    self.hisIdSelected(HIS_ID_TEMP);
                    self.enableSelectSalary(true);
                }
                $("#G1_5_container").focus();
            });

        }

        openKScreen(){

            let self = this;
            let index = _.findIndex(self.listStateCorrelationHisSalary(), {hisId: self.hisIdSelected()});
            self.index(self.getIndex(self.hisIdSelected()));
            let laststartYearMonth: number = 0;
            if (self.listStateCorrelationHisSalary() && self.listStateCorrelationHisSalary().length != self.index() + 1) {
                laststartYearMonth = self.listStateCorrelationHisSalary().length > 1 ? self.listStateCorrelationHisSalary()[self.index() + 1].startYearMonth : 0;
            }
            let canDelete: boolean = false;
            if (self.listStateCorrelationHisSalary().length > 1 && self.hisIdSelected() == self.listStateCorrelationHisSalary()[FIRST].hisId) {
                canDelete = true;
            }

            setShared(model.PARAMETERS_SCREEN_K.INPUT, {
                startYearMonth: self.listStateCorrelationHisSalary()[self.index()].startYearMonth,
                endYearMonth: self.listStateCorrelationHisSalary()[self.index()].endYearMonth,
                hisId: self.hisIdSelected(),
                startLastYearMonth: laststartYearMonth,
                canDelete: canDelete,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.SALARY,
                isFirst: index === 0 && self.listStateCorrelationHisSalary().length > 1 ? true : false
            });
            modal("/view/qmm/020/k/index.xhtml").onClosed(function() {
                let params = getShared(model.PARAMETERS_SCREEN_K.OUTPUT);
                if(params && params.modeEditHistory == 1) {
                    service.getStateCorrelationHisSalary().done((listStateCorrelationHisSalary: Array<StateCorrelationHisSalary>)=>{
                        if (listStateCorrelationHisSalary && listStateCorrelationHisSalary.length > 0) {
                            self.listStateCorrelationHisSalary(StateCorrelationHisSalary.convertToDisplay(listStateCorrelationHisSalary));
                            self.index(self.getIndex(self.hisIdSelected()));
                            self.getStateLinkSettingMasterSalary(self.hisIdSelected(), self.listStateCorrelationHisSalary()[self.index()].startYearMonth);
                        }
                    });
                }
                if(params && params.modeEditHistory == 0) {
                    self.initScreen(null);

                }
                $("#G1_5_container").focus();

            });

        }

        getIndex(hisId: string) {
            let self = this;
            let temp = _.findIndex(self.listStateCorrelationHisSalary(), function(x) {
                return x.hisId == hisId;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        createStateCorrelationHisSalary(start: number, end: number){
            let self = this;
            if (self.listStateCorrelationHisSalary() && self.listStateCorrelationHisSalary().length > 0) {
                let end = Number(start.toString().slice(4, 6)) == 1 ? (start - 89) : (start - 1);
                self.listStateCorrelationHisSalary()[FIRST].display = getText('QMM020_16',
                    [model.convertMonthYearToString(self.listStateCorrelationHisSalary()[FIRST].startYearMonth), model.convertMonthYearToString(end)]);
            }
            let stateCorrelationHisSalary: StateCorrelationHisSalary = new StateCorrelationHisSalary();
            stateCorrelationHisSalary.hisId = HIS_ID_TEMP;
            stateCorrelationHisSalary.startYearMonth = start;
            stateCorrelationHisSalary.endYearMonth = end;
            stateCorrelationHisSalary.display = getText('QMM020_16', [model.convertMonthYearToString(start),model.convertMonthYearToString(end)]);
            return stateCorrelationHisSalary;
        }

    }

    export class StateCorrelationHisSalary {
        hisId: string;
        startYearMonth: number;
        endYearMonth: number;
        display: string;
        constructor() {

        }
        static convertToDisplay(item){
            let listSalary = [];
            _.each(item, (item) => {
                let dto: StateCorrelationHisSalary = new StateCorrelationHisSalary();
                dto.hisId = item.hisId;
                dto.startYearMonth = item.startYearMonth;
                dto.endYearMonth = item.endYearMonth;
                dto.display = getText('QMM020_16', [model.convertMonthYearToString(item.startYearMonth),model.convertMonthYearToString(item.endYearMonth)]);
                listSalary.push(dto);
            });
            return listSalary;
        }

    }

    export const FIRST = 0;

    export const HIS_ID_TEMP = "00000";

}