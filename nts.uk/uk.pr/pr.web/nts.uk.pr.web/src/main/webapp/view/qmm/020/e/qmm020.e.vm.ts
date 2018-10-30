module nts.uk.pr.view.qmm020.e.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {

        listStateCorrelationHisClassification: KnockoutObservableArray<StateCorrelationHisClassification> =  ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectButton: KnockoutObservable<string> = ko.observable("");
        mode: KnockoutObservable<number> = ko.observable();
        listStateLinkSettingMaster: KnockoutObservableArray<StateLinkSettingMaster> =  ko.observableArray([]);
        transferMethod: KnockoutObservable<number> = ko.observable();
        startYearMonth: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable(999912);
        startLastYearMonth: KnockoutObservable<number> = ko.observable();
        index: KnockoutObservable<number> = ko.observable(0);

        constructor(){
            let self = this;
            self.initScreen(null);
/*            let select = getText("QMM020_21");
            select = "<button>" + select + "</button>";
            self.selectButton(select);
            for(let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('00' + i, '基本給', self.selectButton() + "  " + 'TamNX  '+ i,i + 'Demo'));
            }*/
            self.hisIdSelected.subscribe((data) => {
                error.clearAll();
                let self = this;
                self.getStateLinkSettingMaster(data);

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
                    self.mode(model.MODE.NEW);
                }
            }).always(() => {
                block.clear();
            });
            block.clear();

        }

        registerClassification(){
            let self = this;
            if (self.mode() == model.MODE.NO_REGIS) {
                return;
            }

            let data: any = {
                listStateLinkSettingMaster: self.conVertToCommand(self.listStateLinkSettingMaster()),
                isNewMode: self.mode(),
                hisId: self.hisIdSelected(),
                startYearMonth: self.startYearMonth(),
                endYearMonth:  self.endYearMonth()
            }
            block.invisible();
            service.registerClassification(data).done(() => {
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

        getStateLinkSettingMaster(hisId: string){
            let self = this;
            service.getStateLinkMaster(hisId).done((stateLinkSettingMaster: Array<StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(stateLinkSettingMaster);
                    // self.getSpecName(hisId);
                    self.mode(model.MODE.UPDATE);
                } else {
                    self.mode(model.MODE.NO_REGIS);
                }
            }).always(() => {
                block.clear();
            });
        }

        openMScreen() {
            block.invisible();
            let self = this;
            let start;
                        setShared('QMM011_M_PARAMS_INPUT', {
                            startYearMonth: start
                        });
            modal("/view/qmm/020/m/index.xhtml").onClosed(() =>{
                let params = getShared('QMM020_M_PARAMS_OUTPUT');
                if (params) {
                    // set param to listStateLinkSettingMaster
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
                        self.listStateCorrelationHisClassification.unshift(self.createStateCorrelationHisClassification(params.start, params.end));
                    }
                }

            });
            block.clear();
        }

        openKScreen(){
            let self = this;
            self.index(self.getIndex(self.hisIdSelected()));
            let laststartYearMonth: number = 0;
            if (self.listStateCorrelationHisClassification() && self.listStateCorrelationHisClassification().length != self.index() + 1) {
                laststartYearMonth = self.listStateCorrelationHisClassification().length > 1 ? self.listStateCorrelationHisClassification()[self.index() + 1].startYearMonth : 0;
            }
            let canDelete: boolean = false;
            if (self.listStateCorrelationHisClassification().length > 1 && self.hisIdSelected() == self.listStateCorrelationHisClassification()[FIRST].hisId) {
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
            let temp = _.findIndex(self.listStateCorrelationHisClassification(), function(x) {
                return x.hisId == hisId;
            });
            if (temp && temp != -1) {
                return temp;
            }
            return 0;
        }

        createStateCorrelationHisClassification(start: number, end: number){
            let stateCorrelationHisClassification: StateCorrelationHisClassification = new StateCorrelationHisClassification();
            stateCorrelationHisClassification.hisId = HIS_ID_TEMP;
            stateCorrelationHisClassification.startYearMonth = start;
            stateCorrelationHisClassification.endYearMonth = end;
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
                    dto.display = getText('QMM020_9', [item.startYearMonth], [item.endYearMonth]);
                    listClassification.push(dto);
                });
                return listClassification;
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
        salaryCode: string;
        bonusCode: string;
        bonusName:string;
        salaryName: string;
        select: string;
        constructor() {

        }
        static convertToDisplay(item){
            let to = getText('QMM020_9');
            let listStateLinkSettingMaster = [];
            let select = getText("QMM020_21");
            select = "<button>" + select + "</button>";
            _.each(item, (item) => {
                let dto: StateLinkSettingMaster = new StateLinkSettingMaster();
                dto.hisId = item.hisId;
                dto.masterCode = item.masterCode;
                dto.salaryCode = item.salaryCode;
                dto.select = select;
                listStateLinkSettingMaster.push(dto);
            });
            return listStateLinkSettingMaster;
        }
    }

    export const FIRST = 0;

    export const HIS_ID_TEMP = "00000";

}