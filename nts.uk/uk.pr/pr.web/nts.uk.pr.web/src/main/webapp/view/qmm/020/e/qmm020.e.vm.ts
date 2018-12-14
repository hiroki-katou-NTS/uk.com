module nts.uk.pr.view.qmm020.e.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;
    import modal = nts.uk.ui.windows.sub.modal;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        listStateCorrelationHisClassification: KnockoutObservableArray<StateCorrelationHisClassification> =  ko.observableArray([]);
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        currentCode: KnockoutObservable<any> = ko.observable();
        mode: KnockoutObservable<number> = ko.observable();
        listStateLinkSettingMaster: KnockoutObservableArray<model.StateLinkSettingMaster> =  ko.observableArray([]);
        transferMethod: KnockoutObservable<number> = ko.observable();
        startYearMonth: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable(999912);
        startLastYearMonth: KnockoutObservable<number> = ko.observable(0);
        index: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            let self = this;
            self.hisIdSelected.subscribe((data) => {
                error.clearAll();
                let self = this;
                self.index(self.getIndex(data));
                if (data != '') {
                    if (self.transferMethod() == model.TRANSFER_MOTHOD.TRANSFER && self.hisIdSelected() == HIS_ID_TEMP) {
                        self.getStateLinkSettingMaster(self.listStateCorrelationHisClassification()[FIRST + 1].hisId, self.listStateCorrelationHisClassification()[FIRST + 1].startYearMonth);
                    } else {
                        self.getStateLinkSettingMaster(data, self.listStateCorrelationHisClassification()[self.index()].startYearMonth);
                        self.startYearMonth(self.listStateCorrelationHisClassification()[self.index()].startYearMonth);
                        self.endYearMonth(self.listStateCorrelationHisClassification()[self.index()].endYearMonth);
                    }
                }
            });
        }

       enableRegis() {
            return this.mode() == model.MODE.NO_REGIS;
       }

        enableNew() {
            let self = this;
            if (self.listStateCorrelationHisClassification().length > 0) {
                return (self.mode() == model.MODE.NEW || (self.listStateCorrelationHisClassification()[FIRST].hisId == HIS_ID_TEMP));
            }
            return self.mode() == model.MODE.NEW;
        }

       enableEdit(){
            return this.mode() == model.MODE.UPDATE;
       }

        loadGird(){
            let self = this;
            $("#E3_1").ntsGrid({
                height: '320px',
                dataSource: self.listStateLinkSettingMaster(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('QMM020_26'), key: 'id', dataType: 'number', width: '100' , hidden: true},
                    { headerText: getText('QMM020_26'), key: 'masterCode', dataType: 'string', width: '90' },
                    { headerText: getText('QMM020_27'), key: 'categoryName',dataType: 'string', width: '180' },
                    { headerText: getText('QMM020_20'), key: 'salary', dataType: 'string', width: '75', unbound: true, ntsControl: '' },
                    { headerText: '', key: 'displayE3_4', dataType: 'string', width: '200'},
                    { headerText: getText('QMM020_22'), key: 'bonus', dataType: 'string', width: '75', unbound: true, ntsControl: 'Bonus' },
                    { headerText: '', key: 'displayE3_5', dataType: 'string',width: '200' },

                ],
                features: [
                    { name: 'Sorting',
                        type: 'local'
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }],
                ntsControls: [
                    { name: '', text: getText("QMM020_21"), click: function(item) { self.openMScreen(item, 1) }, controlType: 'Button' },
                    { name: 'Bonus', text: getText("QMM020_21"), click: function(item) { self.openMScreen(item, 2) }, controlType: 'Button' }]
            });
            $("#E3_1").setupSearchScroll("igGrid", true);
        }

        openLScreen(){
            let self = this;
            modal("/view/qmm/020/l/index.xhtml").onClosed(()=>{
                let params = getShared(model.PARAMETERS_SCREEN_L.OUTPUT);
                if(params && params.isSubmit) location.reload();

            });
        }

        initScreen(hisId: string){
            let self = this;
            block.invisible();
            service.getStateCorrelationHisClassification().done((listStateCorrelationHis: Array<StateCorrelationHisClassification>) => {
                if (listStateCorrelationHis && listStateCorrelationHis.length > 0) {
                    self.listStateCorrelationHisClassification(StateCorrelationHisClassification.convertToDisplay(listStateCorrelationHis));
                    if (hisId == null) {
                        self.index(FIRST);
                        self.hisIdSelected(self.listStateCorrelationHisClassification()[FIRST].hisId);
                    }
                    self.hisIdSelected(self.listStateCorrelationHisClassification()[self.getIndex(hisId)].hisId);
                } else {
                    self.mode(model.MODE.NO_REGIS);
                    self.loadGird();
                }
            }).always(() => {
                block.clear();
            });
        }

        registerClassification(){
            let self = this;
            if (self.mode() == model.MODE.NO_REGIS) {
                return;
            }

            let data: any = {
                listStateLinkSettingMaster: self.listStateLinkSettingMaster(),
                isNewMode: self.mode(),
                hisId: self.hisIdSelected(),
                startYearMonth: self.startYearMonth(),
                endYearMonth:  self.endYearMonth()
            }
            block.invisible();
            service.registerClassification(data).done(() => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.transferMethod(null);
                    self.mode(model.MODE.UPDATE);
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

        getStateLinkSettingMaster(hisId: string, startYearMonth: number){
            block.invisible();
            let self = this;
            service.getStateLinkMasterClassification(hisId, startYearMonth).done((stateLinkSettingMaster: Array<model.StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(model.convertToDisplay(stateLinkSettingMaster));
                    self.mode(model.MODE.UPDATE);
                }
                if(self.hisIdSelected() == HIS_ID_TEMP ) {
                    self.mode(model.MODE.NEW);
                }
                self.loadGird();
            }).always(() => {
                block.clear();
            });
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
            setShared(model.PARAMETERS_SCREEN_M.INPUT, {
                startYearMonth: self.startYearMonth(),
                statementCode: code === 1 ? salaryCode : bonusCode,
                modeScreen: model.MODE_SCREEN.CLASSIFICATION
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
            let start = self.startLastYearMonth();
            if(self.listStateCorrelationHisClassification() && self.listStateCorrelationHisClassification().length > 0) {
                start = self.listStateCorrelationHisClassification()[FIRST].startYearMonth;
            }
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                startYearMonth: start,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.CLASSIFICATION
            });
            modal("/view/qmm/020/j/index.xhtml").onClosed(() =>{
                let params = getShared(model.PARAMETERS_SCREEN_J.OUTPUT);
                if (params) {
                    self.transferMethod(params.transferMethod);
                    self.listStateCorrelationHisClassification.unshift(self.createStateCorrelationHisClassification(params.start, params.end));
                    self.hisIdSelected(HIS_ID_TEMP);
                }

            });

        }

        openKScreen(){
            let self = this;
            let index = _.findIndex(self.listStateCorrelationHisClassification(), {hisId: self.hisIdSelected()});
            self.index(self.getIndex(self.hisIdSelected()));
            let laststartYearMonth: number = 0;
            if (self.listStateCorrelationHisClassification() && self.listStateCorrelationHisClassification().length != self.index() + 1) {
                laststartYearMonth = self.listStateCorrelationHisClassification().length > 1 ? self.listStateCorrelationHisClassification()[self.index() + 1].startYearMonth : 0;
            }
            let canDelete: boolean = false;
            if (self.listStateCorrelationHisClassification().length > 1 && self.hisIdSelected() == self.listStateCorrelationHisClassification()[FIRST].hisId) {
                canDelete = true;
            }

            setShared(model.PARAMETERS_SCREEN_K.INPUT, {
                startYearMonth: self.startYearMonth(),
                endYearMonth: self.endYearMonth(),
                hisId: self.hisIdSelected(),
                startLastYearMonth: laststartYearMonth,
                canDelete: canDelete,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.CLASSIFICATION,
                isFirst: index === 0 && self.listStateCorrelationHisClassification().length > 1 ? true : false
            });
            modal("/view/qmm/011/k/index.xhtml").onClosed(function() {
                let params = getShared(model.PARAMETERS_SCREEN_K.OUTPUT);
                if(params && params.modeEditHistory == 1) {
                    self.initScreen(self.hisIdSelected());
                }
                if(params && params.modeEditHistory == 0) {
                    self.initScreen(null);
                }
                $('#E2_1').focus();

            });
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

        getIndex(hisId: string) {
            let self = this;
            let temp = _.findIndex(self.listStateCorrelationHisClassification(), function(x) {
                return x.hisId == hisId;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        createStateCorrelationHisClassification(start: number, end: number){
            let self = this;
            if (self.listStateCorrelationHisClassification() && self.listStateCorrelationHisClassification().length > 0) {
                let end = Number(start.toString().slice(4, 6)) == 1 ? (start - 89) : (start - 1);
                self.listStateCorrelationHisClassification()[FIRST].display = getText('QMM020_16',
                    [model.convertMonthYearToString(self.listStateCorrelationHisClassification()[FIRST].startYearMonth), model.convertMonthYearToString(end)]);
            }
            let stateCorrelationHisClassification: StateCorrelationHisClassification = new StateCorrelationHisClassification();
            stateCorrelationHisClassification.hisId = HIS_ID_TEMP;
            stateCorrelationHisClassification.startYearMonth = start;
            stateCorrelationHisClassification.endYearMonth = end;
            stateCorrelationHisClassification.display = getText('QMM020_16', [model.convertMonthYearToString(start),model.convertMonthYearToString(end)]);
            return stateCorrelationHisClassification;
        }

    }

    export class StateCorrelationHisClassification {
        hisId: string;
        startYearMonth: number;
        endYearMonth: number;
        display: string;
        constructor() {

        }
        static convertToDisplay(item){
                let to = getText('QMM020_9');
                let listClassification = [];
                _.each(item, (item) => {
                    let dto: StateCorrelationHisClassification = new StateCorrelationHisClassification();
                    dto.hisId = item.hisId;
                    dto.startYearMonth = item.startYearMonth;
                    dto.endYearMonth = item.endYearMonth;
                    dto.display = getText('QMM020_16', [model.convertMonthYearToString(item.startYearMonth),model.convertMonthYearToString(item.endYearMonth)]);
                    listClassification.push(dto);
                });
                return listClassification;
        }
    }


    export const FIRST = 0;

    export const HIS_ID_TEMP = "00000";

}