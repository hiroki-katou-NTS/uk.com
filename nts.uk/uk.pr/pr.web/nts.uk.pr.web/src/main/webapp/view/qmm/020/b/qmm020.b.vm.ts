module nts.uk.pr.view.qmm020.b.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        salaryCode: KnockoutObservable<string> = ko.observable();
        salaryLayoutName: KnockoutObservable<string> = ko.observable();
        bonusCode: KnockoutObservable<string> = ko.observable();
        bonusLayoutName: KnockoutObservable<string> = ko.observable();
        to : KnockoutObservable<string> = ko.observable(' ～ ');
        startYearMonth: KnockoutObservable<string> = ko.observable();

        mode: KnockoutObservable<number> = ko.observable(0);
        transferMode: KnockoutObservable<number> = ko.observable();
        enableAddHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        enableEditHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        isModeAddHistory: KnockoutObservable<boolean> = ko.observable();
        constructor(){
            block.invisible()
            let self = this;
            let firstHistory;
            service.getStateCorrelationHisCompanyById().done((data) =>{
                if(data.length > 0){
                    _.forEach(data,(o)=>{
                        self.listStateCorrelationHis.push(new ItemModel(o.historyID, '', o.startYearMonth , o.endYearMonth,self.to()));
                    });
                    firstHistory = _.head(self.listStateCorrelationHis());
                    self.currentSelect(firstHistory.hisId);
                }
            }).fail((err)=>{
                if(err)
                    dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
            self.currentSelect.subscribe((hisId)=>{
                let firstHistory = null;
                if(hisId != HISTORY.NEW){
                    self.mode(MODE.UPDATE);
                }else{
                    self.mode(MODE.NEW);
                }

                if(self.isModeAddHistory() && self.mode() === MODE.UPDATE){
                    self.enableEditHisButton(true);
                }else if(self.isModeAddHistory() && self.mode() === MODE.NEW) {
                    self.enableEditHisButton(false);
                    self.enableAddHisButton(false);
                }

                if(self.mode() === MODE.NEW ){
                    if(self.transferMode() === model.TRANSFER_MOTHOD.TRANSFER && self.listStateCorrelationHis().length > 1){
                        firstHistory = self.listStateCorrelationHis()[1];
                        hisId = firstHistory.hisId;
                    }
                }
                service.getStateLinkSettingCompanyById(hisId).done((data)=>{
                    if(data){
                        self.salaryCode(data.salaryCode);
                        self.salaryLayoutName(data.salaryLayoutName);
                        self.bonusCode(data.bonusCode);
                        self.bonusLayoutName(data.bonusLayoutName);
                    }else{
                        self.salaryCode(null);
                        self.salaryLayoutName(null);
                        self.bonusCode(null);
                        self.bonusLayoutName(null);
                    }
                }).fail((err) =>{
                    if(err)
                        dialog.alertError(err);
                }).always(()=>{

                });
            });
        }

        register(){
            block.invisible();
            let self = this;
            let historyID;

            if(self.mode() === MODE.NEW){
                historyID = nts.uk.util.randomId();
            }else if(self.mode() === MODE.UPDATE){
                historyID = self.currentSelect();
            }
            let data: any = {
                stateCorrelationHisCompanyCommand: {
                    cid: '',
                    historyID: historyID,
                    startYearMonth: self.startYearMonth(),
                    endYearMonth: 999912
                },
                stateLinkSettingCompanyCommand : {
                    historyID: historyID,
                    salaryCode: self.salaryCode() === '' ? null : self.salaryCode(),
                    bonusCode: self.bonusCode() === '' ? null : self.bonusCode()
                },
                mode: self.mode()

            }
            service.register(data).done((data)=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    service.getStateCorrelationHisCompanyById().done((data)=>{
                        self.listStateCorrelationHis(self.convertToList(data));
                        if(self.mode() === MODE.NEW){
                            self.currentSelect(historyID);
                        }else{
                            self.currentSelect(self.currentSelect());
                        }
                    });
                    self.enableEditHisButton(true);
                    self.enableAddHisButton(true);
                });
            }).fail((err) =>{
                if(err){
                    dialog.alertError(err);
                }
            }).always(()=>{
                block.clear();
            });

        }
        convertToList(data: any){
            let self = this;
            let list = [];
            _.forEach(data,(o)=>{
                list.push(new ItemModel(o.historyID, '', o.startYearMonth , o.endYearMonth,self.to()));
            });

            return list;
        }
        openScreenJ(){
            let self = this;
            let hisId,startYearMonth, endYearMonth,temp;
            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelect()});
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                endYearMonth : rs.startYearMonth,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.EMPLOYEE,
            });
            modal("/view/qmm/020/j/index.xhtml").onClosed(()=>{
                let params = getShared('PARAMESE_SCREENJ_OUTPUT');
                if(params){
                    hisId = HISTORY.NEW;
                    startYearMonth = params.startYearMonth;
                    self.startYearMonth(startYearMonth);
                    endYearMonth = Number(startYearMonth.toString().slice(4, 6)) == 1 ? (startYearMonth - 89) : (startYearMonth - 1);

                    if(self.listStateCorrelationHis().length > 0){
                        temp = _.head(self.listStateCorrelationHis());
                        temp.endYearMonth = endYearMonth;
                        temp.changeDisplay();
                        self.listStateCorrelationHis.unshift(new ItemModel(hisId, '', startYearMonth , 999912,self.to()));
                        self.listStateCorrelationHis(self.listStateCorrelationHis());
                        self.isModeAddHistory(true);
                    }else{
                        self.listStateCorrelationHis.push(new ItemModel(hisId, '', startYearMonth, 999912,self.to()));
                        self.isModeAddHistory(true);
                        self.listStateCorrelationHis(self.listStateCorrelationHis());
                    }

                    self.transferMode(params.methodEditing);
                    self.currentSelect(HISTORY.NEW);
                }
            });
        }
        openScreenL(){
            modal("/view/qmm/020/l/index.xhtml");
        }

        openScreenM(){
            modal("/view/qmm/020/m/index.xhtml");
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }

    }
    export class ItemModel {
        hisId: string;
        name: string;
        display: string;
        startYearMonth: number;
        endYearMonth: number;
        to: string;
        constructor(hisId: string, name: string, startYearMonth: number,endYearMonth: number, to: string) {
            this.hisId = hisId;
            this.name = name;
            this.startYearMonth = startYearMonth;
            this.endYearMonth = endYearMonth;
            this.to = to;
            this.display = this.convertYearMonthToDisplayYearMonth(startYearMonth)+ this.to + this.convertYearMonthToDisplayYearMonth(endYearMonth);
        }
        changeDisplay(){
            this.display = this.convertYearMonthToDisplayYearMonth(this.startYearMonth)+ this.to + this.convertYearMonthToDisplayYearMonth(this.endYearMonth);
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }
    }

    export enum MODE{
        NEW = 0,
        UPDATE = 1,
    }
    export enum HISTORY{
        NEW = '0'
    }
}