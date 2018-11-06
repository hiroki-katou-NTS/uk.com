module nts.uk.pr.view.qmm020.f.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import PARAMETERS_SCREEN_M = nts.uk.pr.view.qmm020.share.model.PARAMETERS_SCREEN_M;

    export class ScreenModel {

        listStateCorrelationHisPosition: KnockoutObservableArray<StateCorrelationHisPosition> =  ko.observableArray([]);
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        mode: KnockoutObservable<number> = ko.observable(2);
        listStateLinkSettingMaster: KnockoutObservableArray<StateLinkSettingMaster> =  ko.observableArray([]);
        transferMethod: KnockoutObservable<number> = ko.observable();
        startYearMonth: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable(999912);
        startLastYearMonth: KnockoutObservable<number> = ko.observable();
        index: KnockoutObservable<number> = ko.observable(0);
        baseDate: KnockoutObservable<any> = ko.observable();

        constructor(){
            let self = this;
            self.initScreen(null);
            self.hisIdSelected.subscribe((data) => {
                error.clearAll();
                let self = this;
                self.index(self.getIndex(data));
                if (data != '') {
                    self.getStateLinkSettingMasterPosition(data, self.listStateCorrelationHisPosition()[self.index()].startYearMonth);
                    self.getDateBase(data);
                    self.startYearMonth(self.listStateCorrelationHisPosition()[self.index()].startYearMonth);
                    self.endYearMonth(self.listStateCorrelationHisPosition()[self.index()].endYearMonth);
                }
            });

        }

        loadGird(){
            let self = this;
            $("#F3_1").ntsGrid({
                height: '320px',
                dataSource: self.listStateLinkSettingMaster(),
                primaryKey: 'masterCode',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('QMM020_26'), key: 'masterCode', dataType: 'string', width: '100' },
                    { headerText: getText('QMM020_27'), key: 'categoryName',dataType: 'string', width: '200' },
                    { headerText: getText('QMM020_20'), key: 'salaryCode', dataType: 'string', width: '80px', unbound: true, ntsControl: 'Salary' },
                    { headerText: '', key: 'displayE3_4', dataType: 'string', width: '180'},
                    { headerText: getText('QMM020_22'), key: 'bonusCode', dataType: 'string', width: '80px', unbound: true, ntsControl: 'Bonus' },
                    { headerText: '', key: 'displayE3_5', dataType: 'string',width: '180' },

                ],
                features: [{ name: 'Sorting', type: 'local' }],
                ntsControls: [
                    { name: 'Salary', text: getText("QMM020_21"), click: function(item) { self.openMScreen(item, 1) }, controlType: 'Button' },
                    { name: 'Bonus', text: getText("QMM020_21"), click: function(item) { self.openMScreen(item, 2) }, controlType: 'Button' }]
            });
        }

        initScreen(hisId: string){
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
                }
            }).always(() => {
                block.clear();
            });
            block.clear();

        }

        registerPosition(){
            let self = this;
            if (self.mode() == model.MODE.NO_REGIS) {
                return;
            }

            let data: any = {
                stateLinkSettingMaster: self.listStateLinkSettingMaster(),
                mode: self.mode(),
                hisId: self.hisIdSelected(),
                startYearMonth: self.startYearMonth(),
                endYearMonth:  self.endYearMonth()
            }
            block.invisible();
            service.registerCorrelationHisPosition(data).done(() => {
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
            $("#B3_1").focus();

        }

        getStateLinkSettingMasterPosition(hisId: string, startYeaMonth: number){
            block.invisible();
            let self = this;
            service.getStateLinkSettingMasterPosition(hisId, startYeaMonth).done((stateLinkSettingMaster: Array<StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(StateLinkSettingMaster.convertToDisplay(stateLinkSettingMaster));
                    self.mode(model.MODE.UPDATE);
                } else {
                    self.mode(model.MODE.NO_REGIS);
                }
                self.loadGird();
            }).always(() => {
                block.clear();
            });
        }

        getDateBase(hisId: string){
            let self = this;
            service.getDateBase(hisId).done((item: any) => {
                if (item) {
                    self.baseDate(getText('QMM020_39', [item.date]));
                }
            });
        }

        openMScreen(item, code) {
            block.invisible();
            let self = this;
            console.log(item);
            setShared(PARAMETERS_SCREEN_M.INPUT, {
                startYearMonth: self.startYearMonth(),
                modeScreen: model.MODE_SCREEN.POSITION
            });
            modal("/view/qmm/020/m/index.xhtml").onClosed(() =>{
                let params = getShared(PARAMETERS_SCREEN_M.OUTPUT);
                if (params) {
                    if(code == 1) {
                        item.salaryCode = params.statementCode;
                        item.salaryName = params.statementName;
                        item.displayE3_4 = StateLinkSettingMaster.displayCodeAndName(params.statementCode, params.statementName);
                    } else {
                        item.bonusCode = params.statementCode;
                        item.bonusName = params.statementName;
                        item.displayE3_5 = StateLinkSettingMaster.displayCodeAndName(params.statementCode, params.statementName);
                        $("#F3_1").ntsGrid("updateRow", item.masterCode, item);
                        console.log(self.listStateLinkSettingMaster());
                    }
                }

            });
            block.clear();
        }

        openJScreen() {
            block.invisible();
            let self = this;
            let start;
            /*            setShared('QMM011_J_PARAMS_INPUT', {
                            startYearMonth: start
                        });*/
            modal("/view/qmm/020/j/index.xhtml").onClosed(() =>{
                let params = getShared('QMM020_J_PARAMS_OUTPUT');
                if (params) {
                    self.mode(model.MODE.NEW);
                    self.transferMethod(params.transferMethod);
                    if(self.transferMethod == model.TRANSFER_MOTHOD.CREATE_NEW) {
                        self.listStateCorrelationHisPosition.unshift(self.createStateCorrelationHisPosition(params.start, params.end));
                    }
                }

            });
            block.clear();
        }

        openKScreen(){
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

            setShared('QMM011_K_PARAMS_INPUT', {
                startYearMonth: self.startYearMonth(),
                endYearMonth: self.endYearMonth(),
                hisId: self.hisIdSelected(),
                startLastYearMonth: laststartYearMonth,
                canDelete: canDelete
            });
            modal("/view/qmm/011/k/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_K_PARAMS_OUTPUT');
                if(params && params.methodEditing == 1) {
                    self.initScreen(self.hisIdSelected());
                }
                if(params && params.methodEditing == 0) {
                    self.initScreen(null);
                }
                $('#E2_1').focus();

            });
            block.clear();
        }

        conVertToCommand(item){

        }

        getIndex(hisId: string) {
            let self = this;
            let temp = _.findIndex(self.listStateCorrelationHisPosition(), function(x) {
                return x.hisId == hisId;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        createStateCorrelationHisPosition(start: number, end: number){
            let stateCorrelationHisPosition: StateCorrelationHisPosition = new StateCorrelationHisPosition();
            stateCorrelationHisPosition.hisId = HIS_ID_TEMP;
            stateCorrelationHisPosition.startYearMonth = start;
            stateCorrelationHisPosition.endYearMonth = end;
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
        static convertToDisplay(item){
            let listPosition = [];
            _.each(item, (item) => {
                let dto: StateCorrelationHisPosition = new StateCorrelationHisPosition();
                dto.hisId = item.hisId;
                dto.startYearMonth = item.startYearMonth;
                dto.endYearMonth = item.endYearMonth;
                dto.display = getText('QMM020_16', [item.startYearMonth,item.endYearMonth]);
                listPosition.push(dto);
            });
            return listPosition;
        }
        static convertMonthYearToString(yearMonth: any) {
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }
    }
    export class StateLinkSettingMaster {
        hisId: string;
        masterCode: string;
        categoryName: string;
        salaryCode: string;
        bonusCode: string;
        bonusName:string;
        salaryName: string;
        displayE3_4: string;
        displayE3_5: string;
        constructor() {

        }
        static convertToDisplay(item){
            let listStateLinkSettingMaster = [];
            _.each(item, (item) => {
                let dto: StateLinkSettingMaster = new StateLinkSettingMaster();
                dto.hisId = item.hisId;
                dto.masterCode = item.masterCode;
                dto.categoryName = item.categoryName;
                dto.salaryCode = item.salaryCode;
                dto.bonusCode = item.bonusCode;
                dto.bonusName = item.bonusName;
                dto.salaryName = item.salaryName;
                dto.displayE3_4 = StateLinkSettingMaster.displayCodeAndName(item.salaryCode, item.salaryName);
                dto.displayE3_5 = StateLinkSettingMaster.displayCodeAndName(item.bonusCode, item.bonusName);
                listStateLinkSettingMaster.push(dto);
            });
            return listStateLinkSettingMaster;
        }
        static displayCodeAndName(code: number, name: string){
            let display : string;
            display = code == null ? " " : code.toString();
            if(name != null) {
                display = display + "      " + name;
            }
            return display;
        }
    }

    export const FIRST = 0;

    export const HIS_ID_TEMP = "00000";

}