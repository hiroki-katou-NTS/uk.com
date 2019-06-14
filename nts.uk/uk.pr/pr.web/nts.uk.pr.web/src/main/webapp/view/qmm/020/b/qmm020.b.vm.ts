module nts.uk.pr.view.qmm020.b.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        currentSelectedHis: KnockoutObservable<any> = ko.observable();

        salaryCode: KnockoutObservable<string> = ko.observable();
        salaryLayoutName: KnockoutObservable<string> = ko.observable();
        bonusCode: KnockoutObservable<string> = ko.observable();
        bonusLayoutName: KnockoutObservable<string> = ko.observable();

        startYearMonth: KnockoutObservable<string> = ko.observable();

        mode: KnockoutObservable<number> = ko.observable(0);
        isModeAddHistory: KnockoutObservable<boolean> = ko.observable();
        transferMode: KnockoutObservable<number> = ko.observable();

        enableAddHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        enableEditHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        newHistoryId: KnockoutObservable<string> = ko.observable();
        enableRegisterButton: KnockoutObservable<boolean> = ko.observable(true);

        constructor(){
            block.invisible();
            let self = this;
            self.watchDataChanged();
            block.clear();
        }

        startPage (): JQueryPromise<any>{
            block.invisible();
            let self = this;
            let firstHistory, dfd = $.Deferred();
            let listStateCorrelationHis = [];
            service.getStateCorrelationHisCompanyById().done((data) =>{
                if(data.length > 0){
                    _.forEach(data,(o)=>{
                        listStateCorrelationHis.push(new ItemModel(o.historyID,  o.startYearMonth , o.endYearMonth));
                    });
                    self.listStateCorrelationHis(listStateCorrelationHis);
                    firstHistory = _.head(self.listStateCorrelationHis());
                    if(firstHistory.hisId === self.currentSelectedHis()){
                        let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
                        let startYearMonth = rs ? rs.startYearMonth : 0;
                        service.getStateLinkSettingCompanyById(firstHistory.hisId,startYearMonth).done((data)=>{
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
                            block.clear();
                        });
                    }else{
                        self.currentSelectedHis(firstHistory.hisId);
                    }
                }else{
                    self.listStateCorrelationHis([]);
                    self.salaryCode('');
                    self.salaryLayoutName('');
                    self.bonusCode('');
                    self.bonusLayoutName('');
                    self.mode(model.MODE.NO_REGIS);
                }
                dfd.resolve();
            }).fail((err)=>{
                dfd.reject();
                if(err)
                    dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });

            return dfd.promise();
        }

        watchDataChanged(){
            let self = this;
            self.currentSelectedHis.subscribe((hisId)=>{
                block.invisible();
                let firstHistory = null;
                if(hisId != self.newHistoryId()){
                    self.mode(model.MODE.UPDATE);
                }else{
                    self.mode(model.MODE.NEW);
                }

                if(self.isModeAddHistory() && self.mode() === model.MODE.UPDATE){
                    self.enableEditHisButton(true);
                }else if(self.isModeAddHistory() && self.mode() === model.MODE.NEW) {
                    self.enableEditHisButton(false);
                    self.enableAddHisButton(false);
                }

                if(self.mode() === model.MODE.NEW ){
                    if(self.transferMode() === model.TRANSFER_MOTHOD.TRANSFER && self.listStateCorrelationHis().length > 1){
                        firstHistory = self.listStateCorrelationHis()[1];
                        hisId = firstHistory.hisId;
                    }
                }

                let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
                let startYearMonth = rs ? rs.startYearMonth : 0;
                service.getStateLinkSettingCompanyById(hisId,startYearMonth).done((data)=>{
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
                    block.clear();
                });
            });
        }

        registerStateLinkSettingCompany(){
            block.invisible();
            let self = this;
            let historyID;

            if(self.mode() === model.MODE.NEW){
                historyID = self.newHistoryId();
            }else if(self.mode() === model.MODE.UPDATE){
                historyID = self.currentSelectedHis();
            }

            service.getStateCorrelationHisCompanyById().done((result)=>{

                let listStateCorrelationHis = self.convertToList(result);
                let rs = _.find(listStateCorrelationHis,{hisId: self.currentSelectedHis()});
                let data: any = {
                    stateCorrelationHisCompanyCommand: {
                        cid: '',
                        historyID: historyID,
                        startYearMonth : self.mode() === model.MODE.NEW ? self.startYearMonth() : rs.startYearMonth,
                        endYearMonth:  self.mode() === model.MODE.NEW ? 999912 : rs.endYearMonth ,
                    },
                    stateLinkSettingCompanyCommand : {
                        historyID: historyID,
                        salaryCode: self.salaryCode() === '' ? null : self.salaryCode(),
                        bonusCode: self.bonusCode() === '' ? null : self.bonusCode()
                    },
                    mode: self.mode()

                }
                service.registerStateLinkSettingCompany(data).done(()=>{
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        service.getStateCorrelationHisCompanyById().done((data)=>{
                            self.listStateCorrelationHis(self.convertToList(data));
                            if(self.mode() === model.MODE.NEW){
                                self.currentSelectedHis(historyID);
                            }else{
                                self.currentSelectedHis(self.currentSelectedHis());
                            }
                        });
                        self.mode(model.MODE.UPDATE);
                        self.newHistoryId(null);
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

            }).fail((err)=>{
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
                list.push(new ItemModel(o.historyID,  o.startYearMonth , o.endYearMonth));
            });

            return list;
        }

        openScreenJ(){
            let self = this;
            let hisId,startYearMonth, endYearMonth,temp;
            let rs = _.head(self.listStateCorrelationHis());
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                startYearMonth : rs ? rs.startYearMonth : 0,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.EMPLOYEE,
            });
            modal("/view/qmm/020/j/index.xhtml").onClosed(()=>{
                block.invisible();
                let params = getShared(model.PARAMETERS_SCREEN_J.OUTPUT);
                if(params){
                    self.enableRegisterButton(true);
                    hisId = nts.uk.util.randomId();
                    self.newHistoryId(hisId);
                    startYearMonth = params.start;
                    self.startYearMonth(startYearMonth);
                    endYearMonth = Number(startYearMonth.toString().slice(4, 6)) == 1 ? (startYearMonth - 89) : (startYearMonth - 1);

                    if(self.listStateCorrelationHis().length > 0){
                        temp = _.head(self.listStateCorrelationHis());
                        temp.endYearMonth = endYearMonth;
                        temp.changeDisplay();
                        self.listStateCorrelationHis.unshift(new ItemModel(hisId,  startYearMonth , 999912));
                        self.listStateCorrelationHis(self.listStateCorrelationHis());
                        self.isModeAddHistory(true);
                    }else{
                        self.listStateCorrelationHis.push(new ItemModel(hisId,  startYearMonth, 999912));
                        self.isModeAddHistory(true);
                        self.listStateCorrelationHis(self.listStateCorrelationHis());
                    }

                    self.transferMode(params.transferMethod);
                    self.currentSelectedHis(self.newHistoryId());
                } else if(!params && self.listStateCorrelationHis().length === 0){
                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                }
                block.clear();
                $("#B1_5_container").focus();
            });
        }

        openScreenK(){
            let self = this;
            let listStateCorrelationHis = [];
            service.getStateCorrelationHisCompanyById().done((data)=>{
                listStateCorrelationHis = self.convertToList(data);
                let rs = _.find(listStateCorrelationHis,{hisId: self.currentSelectedHis()});
                let index = _.findIndex(self.listStateCorrelationHis(), {hisId: self.currentSelectedHis()});
                setShared(model.PARAMETERS_SCREEN_K.INPUT, {
                    startLastYearMonth : index != self.listStateCorrelationHis().length-1 ? self.listStateCorrelationHis()[index+1].startYearMonth : 0,
                    startYearMonth : rs ? rs.startYearMonth : 0,
                    endYearMonth:  rs ? rs.endYearMonth : 999912,
                    hisId: self.currentSelectedHis(),
                    modeScreen: model.MODE_SCREEN.COMPANY,
                    masterCode: null,
                    isFirst: index === 0 && self.listStateCorrelationHis().length > 1 ? true : false,
                });
            }).fail((err)=>{
                if(err) dialog.alertError(err);
            }).always(()=>{
            });

            modal("/view/qmm/020/k/index.xhtml").onClosed(()=>{
                block.invisible();
                let params = getShared(model.PARAMETERS_SCREEN_K.OUTPUT);
                if(params){
                    service.getStateCorrelationHisCompanyById().done((data)=>{

                        if(params.modeEditHistory === EDIT_METHOD.DELETE){
                            //case delete history => focus first history
                            self.listStateCorrelationHis(self.convertToList(data));
                            self.currentSelectedHis(_.head(self.listStateCorrelationHis()).hisId);
                        }else{
                            //case update history => focus current history
                            self.listStateCorrelationHis(self.convertToList(data));
                            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
                            let startYearMonth = rs ? rs.startYearMonth : 0;
                            service.getStateLinkSettingCompanyById(self.currentSelectedHis(),startYearMonth).done((data)=>{
                                self.salaryCode(data.salaryCode);
                                self.salaryLayoutName(data.salaryLayoutName);
                                self.bonusCode(data.bonusCode);
                                self.bonusLayoutName(data.bonusLayoutName);
                            }).fail((err) =>{
                                if(err)
                                    dialog.alertError(err);
                            }).always(()=>{
                                block.clear();
                            });
                        }
                        self.enableAddHisButton(true);
                        self.enableEditHisButton(true);

                    });
                }else{
                    block.clear();
                }

                $("#B1_5_container").focus();
            });
        }


        openScreenM(item){
            let self = this;
            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});

            setShared(model.PARAMETERS_SCREEN_M.INPUT,{
                startYearMonth: rs ? rs.startYearMonth : 0,
                statementCode : self.salaryCode()
            });

            modal("/view/qmm/020/m/index.xhtml").onClosed(()=>{
                let params = getShared(model.PARAMETERS_SCREEN_M.OUTPUT);
                if(params){
                    self.salaryCode(params.statementCode);
                    self.salaryLayoutName(params.statementName);
                }

            });

        }

        openScreenM1(item){
            let self = this;
            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
            setShared(model.PARAMETERS_SCREEN_M.INPUT,{
                startYearMonth: rs ? rs.startYearMonth : 0,
                statementCode : self.bonusCode()
            });
            modal("/view/qmm/020/m/index.xhtml").onClosed(()=>{
                let params = getShared(model.PARAMETERS_SCREEN_M.OUTPUT);
                if(params){
                    if(params){
                        self.bonusCode(params.statementCode);
                        self.bonusLayoutName(params.statementName);
                    }
                }

            });

        }

    }

    export class ItemModel {
        hisId: string;
        display: string;
        startYearMonth: number;
        endYearMonth: number;
        constructor(hisId: string, startYearMonth: number,endYearMonth: number) {
            this.hisId = hisId;
            this.startYearMonth = startYearMonth;
            this.endYearMonth = endYearMonth;
            this.display = getText('QMM020_16',[this.convertYearMonthToDisplayYearMonth(startYearMonth),this.convertYearMonthToDisplayYearMonth(endYearMonth)]);
        }
        changeDisplay(){
            this.display = getText('QMM020_16',[this.convertYearMonthToDisplayYearMonth(this.startYearMonth),this.convertYearMonthToDisplayYearMonth(this.endYearMonth)]);
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

}