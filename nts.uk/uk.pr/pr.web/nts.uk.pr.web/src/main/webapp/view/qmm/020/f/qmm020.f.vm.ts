module nts.uk.pr.view.qmm020.e.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;

    export class ScreenModel {

        listStateCorrelationHisPosition: KnockoutObservableArray<StateCorrelationHisPosition> =  ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectButton: KnockoutObservable<string> = ko.observable("");
        mode: KnockoutObservable<number> = ko.observable();
        listStateLinkSettingMaster: KnockoutObservableArray<StateLinkSettingMaster> =  ko.observableArray([]);

        constructor(){
            let self = this;
            //self.initScreen();
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
            service.getStateCorrelationHisPosition().done((stateCorrelationHisPosition: Array<StateCorrelationHisPosition>) => {
                if (stateCorrelationHisPosition && stateCorrelationHisPosition.length > 0) {
                    self.listStateCorrelationHisPosition(StateCorrelationHisClassification.convertToDisplay(stateCorrelationHisPosition));
                    self.hisIdSelected(self.listStateCorrelationHisPosition()[FIRST].hisId);
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

    }
    export  class ItemModel {
        empCode: string;
        empName: string;
        display1: string;
        display2: string;
        constructor(empCode: string, empName: string, display1: string, display2: string) {
            this.empCode = empCode;
            this.empName = empName;
            this.display1 = display1;
            this.display2 = display2;
        }
    }

    export class StateCorrelationHisPosition {
        hisId: string;
        startYearMonth: string;
        endYearMonth: string;
        display: string;
        constructor() {

        }
        static convertToDisplay(item){
            let to = getText('QMM020_9');
            let listPosition = [];
            _.each(item, (item) => {
                let dto: StateCorrelationHisPosition = new StateCorrelationHisPosition();
                dto.hisId = item.hisId;
                dto.startYearMonth = item.startYearMonth;
                dto.endYearMonth = item.endYearMonth;
                dto.display = getText('QMM020_9', [item.startYearMonth], [item.endYearMonth]);
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

}