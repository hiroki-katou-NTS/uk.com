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

        constructor(){
            let self = this;
            self.initScreen();
/*            let select = getText("QMM020_21");
            select = "<button>" + select + "</button>";
            self.selectButton(select);
            for(let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('00' + i, '基本給', self.selectButton() + "  " + 'TamNX  '+ i,i + 'Demo'));
            }*/
            self.hisIdSelected.subscribe((data) => {
                nts.uk.ui.errors.clearAll();
                let self = this;
                self.getStateLinkSettingMaster(data);

            });

        }
        initScreen(){
            let self = this;
            block.invisible();
            service.getStateCorrelationHisClassification().done((listStateCorrelationHis: Array<StateCorrelationHisClassification>) => {
                if (listStateCorrelationHis && listStateCorrelationHis.length > 0) {
                    self.listStateCorrelationHisClassification(StateCorrelationHisClassification.convertToDisplay(listStateCorrelationHis));
                    self.hisIdSelected(self.listStateCorrelationHisClassification()[FIRST].hisId);
                } else {
                    self.mode(model.MODE.NEW);
                }
            }).always(() => {
                block.clear();
            });
            block.clear();

        }

        getStateLinkSettingMaster(hisId: string){
            let self = this;
            service.getStateLinkSettingMaster(hisId).done((stateLinkSettingMaster: Array<StateLinkSettingMaster>) => {
                if (stateLinkSettingMaster && stateLinkSettingMaster.length > 0) {
                    self.listStateLinkSettingMaster(stateLinkSettingMaster);
                    self.mode(model.MODE.UPDATE);
                } else {
                    self.mode(model.MODE.NEW);
                }
            }).always(() => {
                block.clear();
            });
        }

        openJscreen() {
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
        name:string;
        constructor(master: StateLinkSettingMaster) {
            this.hisId = master.hisId;
            this.masterCode = master.masterCode;
            this.salaryCode = master.salaryCode;
            this.bonusCode = master.bonusCode;
            this.name = master.name;
        }
    }

    export const FIRST = 0;

    export const HIS_ID_TEMP = "00000";

}