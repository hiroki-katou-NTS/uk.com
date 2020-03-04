module nts.uk.pr.view.qmm020.c.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentSelectedHis: KnockoutObservable<any> = ko.observable();
        items:  KnockoutObservableArray<GridItem> = ko.observableArray([]);
        startYearMonth: KnockoutObservable<string> = ko.observable();

        mode: KnockoutObservable<number> = ko.observable(0);
        transferMode: KnockoutObservable<number> = ko.observable();
        enableAddHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        enableEditHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        enableRegisterButton:KnockoutObservable<boolean> = ko.observable(true);
        enableSearchButton:KnockoutObservable<boolean> = ko.observable(true);
        isModeAddHistory: KnockoutObservable<boolean> = ko.observable();
        newHistoryId: KnockoutObservable<string> = ko.observable();

        constructor() {
            block.invisible();
            let self = this;
            self.watchDataChanged();
            block.clear();
        }

        startPage(): JQueryPromise<any>{
            block.invisible();
            let self = this;
            let firstHistory, dfd = $.Deferred();
            let listStateCorrelationHis = [];

            service.getStateCorrelationHisEmployeeById().done((data)=>{
                if(data.length > 0){
                    _.forEach(data,(o)=>{
                        listStateCorrelationHis.push(new ItemModel(o.hisId, o.startYearMonth , o.endYearMonth));
                    });
                    self.listStateCorrelationHis(listStateCorrelationHis);
                    firstHistory = _.head(self.listStateCorrelationHis());
                    if(firstHistory.hisId === self.currentSelectedHis()){
                        let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
                        let startYearMonth = rs ? rs.startYearMonth : 999912;
                        service.getStateLinkSettingMasterByHisId(firstHistory.hisId,startYearMonth).done((data)=>{
                            if(data.length > 0){
                                self.items(self.convertToGridItem(data));
                                self.loadGrid();
                            }else{
                                self.loadGrid();
                            }
                        }).fail((err) =>{
                            if(err) dialog.alertError(err);
                        }).always(()=>{
                            block.clear();
                        });
                    }else{
                        self.currentSelectedHis(firstHistory.hisId);
                    }
                    self.enableSearchButton(true);
                }else{
                    self.listStateCorrelationHis([]);
                    service.getStateLinkSettingMasterByHisId("0",0).done((data)=>{
                        if(data && data.length > 0) {
                            self.items(self.convertToGridItem(data));
                        } else {
                            self.items([]);
                            dialog.info({ messageId: "Msg_303" }).then(()=>{
                                self.enableAddHisButton(false);
                                self.enableEditHisButton(false);
                                self.enableRegisterButton(false);
                            });
                        }
                        self.loadGrid();
                        self.enableAll();
                        self.mode(model.MODE.NO_REGIS);
                    });
                }
                dfd.resolve();
            }).fail((err) =>{
                dfd.reject();
                if(err) dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
            return dfd.promise();

        }

        enableAll(){
            let self = this;
            self.enableAddHisButton(true);
            self.enableEditHisButton(false);
            self.enableRegisterButton(false);
            self.enableSearchButton(false);
            $("#grid2").ntsGrid("disableNtsControls", 'open1', 'Button');
            $("#grid2").ntsGrid("disableNtsControls", 'open', 'Button');
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
                let startYearMonth = rs ? rs.startYearMonth : 999912;
                service.getStateLinkSettingMasterByHisId(hisId,startYearMonth).done((data)=>{
                    if(data.length > 0){
                        self.items(self.convertToGridItem(data));
                        self.loadGrid();
                    }else{
                        self.loadGrid();
                    }
                }).fail((err) =>{
                    if(err) dialog.alertError(err);
                }).always(()=>{
                    block.clear();
                });
            });
        }

        convertToGridItem(data: any){
            let listGrid = [];
            _.forEach(data,(o,i)=>{
                listGrid.push(new GridItem(i,o.code,o.name,o.salaryCode,o.salaryName,o.bonusCode,o.bonusName));
            });

            return listGrid;
        }

        registerStateCorrelationHisDeparment(){
            block.invisible();
            let self = this;
            let historyID;
            if(self.mode() === model.MODE.NEW){
                historyID = self.newHistoryId();
            }else if(self.mode() === model.MODE.UPDATE){
                historyID = self.currentSelectedHis();
            }
            service.getStateCorrelationHisEmployeeById().done((result)=>{
                let listStateCorrelationHis = self.convertToList(result);
                let rs = _.find(listStateCorrelationHis,{hisId: self.currentSelectedHis()});
                let listStateLinkSettingMaster: Array<IStateLinkSettingMaster> = [];
                self.convertToCommand(historyID,self.items(),listStateLinkSettingMaster);
                let data = {
                    listStateLinkSettingMasterCommand: listStateLinkSettingMaster,
                    stateCorrelationHisEmployeeCommand:{
                        cid: '',
                        hisId: historyID,
                        startYearMonth : self.mode() === model.MODE.NEW ? self.startYearMonth() :  rs.startYearMonth,
                        endYearMonth:  self.mode() === model.MODE.NEW ? 999912 : rs.endYearMonth ,
                    },
                    mode: self.mode(),
                }
                service.registerStateCorrelationHisEmployee(data).done(()=>{
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        service.getStateCorrelationHisEmployeeById().done((data)=>{
                            self.listStateCorrelationHis(self.convertToList(data));
                            if(self.mode() === model.MODE.NEW){
                                self.currentSelectedHis(historyID);
                            }else{
                                self.currentSelectedHis(self.currentSelectedHis());
                            }
                        }).fail((err)=>{
                            if(err)
                                dialog.alertError(err);
                        }).always(()=>{
                            block.clear();
                        });

                        self.mode(model.MODE.UPDATE);
                        self.newHistoryId(null);
                        self.enableEditHisButton(true);
                        self.enableAddHisButton(true);
                    });
                }).fail((err)=>{
                    if(err) dialog.alertError(err);
                }).always(()=>{
                    block.clear();
                });

            }).fail((err)=>{
                if(err) dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
        }

        convertToList(data: any){
            let self = this;
            let list = [];
            _.forEach(data,(o)=>{
                list.push(new ItemModel(o.hisId,  o.startYearMonth , o.endYearMonth));
            });

            return list;
        }

        convertToCommand(historyID: string, data:any,list: Array<IStateLinkSettingMaster>){
            _.forEach(data,(o)=>{
                let i = {
                    hisId: historyID,
                    masterCode: o.employeeCode,
                    salaryCode: o.salaryCode == '' ? null : o.salaryCode,
                    bonusCode:  o.bonusCode == '' ? null : o.bonusCode,
                };
                let temp = new StateLinkSettingMaster(i);
                list.push(temp);
            });
        }

        loadGrid(){
            let self = this;
            block.invisible();
            $("#grid2").ntsGrid({
                height: '344px',
                dataSource: self.items(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: '', key: 'id', dataType: 'number', ntsControl: 'Label',hidden: true },
                    { headerText: getText('QMM020_26'),key: 'employeeCode', dataType: 'string', width: '90px'},
                    { headerText: getText('QMM020_27'),key: 'employeeName', dataType: 'string', width: '180px',  },
                    { headerText: getText('QMM020_20'), key: 'open', dataType: 'string', width: '75px', unbound: true, ntsControl: 'ButtonSalary' },
                    { headerText: '',template: '<div>${salaryCode}</div>', key: 'salaryCode', dataType: 'string', width: '30px' },
                    { headerText: '',template: '<div style="text-overflow: ellipsis; ">${salaryLayoutName}</div>', key: 'salaryLayoutName', dataType: 'string', width: '170px' },
                    { headerText: getText('QMM020_22'), key: 'open1', dataType: 'string', width: '75px', unbound: true, ntsControl: 'ButtonBonus' },
                    { headerText: '',template: '<div>${bonusCode}</div>', key: 'bonusCode', dataType: 'string', width: '30px' },
                    { headerText: '',template: '<div>${bonusLayoutName}</div>', key: 'bonusLayoutName', dataType: 'string', width: '170px' },

                ],
                features: [
                    { name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'employeeCode', allowResizing: true, minimumWidth: 30
                        }, {
                            columnKey: 'employeeName', allowResizing: true, minimumWidth:30
                        }]
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsControls: [
                    { name: 'ButtonSalary', text: getText('QMM020_21'), click: function(item) { self.openScreenM(item); }, controlType: 'Button' },
                    { name: 'ButtonBonus', text: getText('QMM020_21'), click: function(item) { self.openScreenM1(item); }, controlType: 'Button' },
                ]
            });
            $("#grid2").setupSearchScroll("igGrid", true);
            block.clear();
        }


        openScreenM(item){

            let self = this;
            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
            setShared(model.PARAMETERS_SCREEN_M.INPUT,{
                startYearMonth: rs ? rs.startYearMonth : 0,
                statementCode: item.salaryCode
            });
            modal("/view/qmm/020/m/index.xhtml").onClosed(()=>{
                let params = getShared(model.PARAMETERS_SCREEN_M.OUTPUT);
                if(params){
                    $("#grid2").ntsGrid("updateRow", item.id, {salaryCode: params.statementCode,salaryLayoutName: _.escape(params.statementName)});
                }

            });

        }

        openScreenM1(item){
            let self = this;
            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
            setShared(model.PARAMETERS_SCREEN_M.INPUT,{
                startYearMonth: rs ? rs.startYearMonth : 0,
                statementCode: item.bonusCode
            });
            modal("/view/qmm/020/m/index.xhtml").onClosed(()=>{
                let params = getShared(model.PARAMETERS_SCREEN_M.OUTPUT);
                if(params){
                    $("#grid2").ntsGrid("updateRow", item.id, {bonusCode: params.statementCode,bonusLayoutName: params.statementName});
                }

            });

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
                        self.isModeAddHistory(true);
                        self.listStateCorrelationHis(self.listStateCorrelationHis());
                    }else{
                        self.listStateCorrelationHis.push(new ItemModel(hisId,  startYearMonth, 999912));
                        self.isModeAddHistory(true);
                        self.listStateCorrelationHis(self.listStateCorrelationHis());
                    }
                    self.enableRegisterButton(true);
                    self.transferMode(params.transferMethod);
                    self.currentSelectedHis(self.newHistoryId());
                    self.enableSearchButton(true);
                    $("#grid2").ntsGrid("enableNtsControls", 'open1', 'Button');
                    $("#grid2").ntsGrid("enableNtsControls", 'open', 'Button');
                }else if(!params && self.listStateCorrelationHis().length === 0){
                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                }
                block.clear();
                $("#C1_5_container").focus();
            });
        }

        openScreenK(){

            let self = this;
            let listStateCorrelationHis = [];
            service.getStateCorrelationHisEmployeeById().done((data)=>{
                listStateCorrelationHis = self.convertToList(data);
                let rs = _.find(listStateCorrelationHis,{hisId: self.currentSelectedHis()});
                let index = _.findIndex(self.listStateCorrelationHis(), {hisId: self.currentSelectedHis()});
                setShared(model.PARAMETERS_SCREEN_K.INPUT, {
                    startLastYearMonth : index != self.listStateCorrelationHis().length-1 ? self.listStateCorrelationHis()[index+1].startYearMonth : 0,
                    startYearMonth : rs ? rs.startYearMonth : 0,
                    endYearMonth:  rs ? rs.endYearMonth : 999912,
                    hisId: self.currentSelectedHis(),
                    modeScreen: model.MODE_SCREEN.EMPLOYEE,
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
                    service.getStateCorrelationHisEmployeeById().done((data)=>{
                        if(params.modeEditHistory === EDIT_METHOD.DELETE){
                            //case delete history => focus first history
                            self.listStateCorrelationHis(self.convertToList(data));
                            self.currentSelectedHis(_.head(self.listStateCorrelationHis()).hisId);
                        }else{
                            //case update history => focus current history
                            self.listStateCorrelationHis(self.convertToList(data));
                            let rs = _.find(self.listStateCorrelationHis(),{hisId: self.currentSelectedHis()});
                            let startYearMonth = rs ? rs.startYearMonth : 0;
                            service.getStateLinkSettingMasterByHisId(self.currentSelectedHis(),startYearMonth).done((data)=>{
                                if(data.length > 0){
                                    self.items(self.convertToGridItem(data));
                                    self.loadGrid();
                                }else{
                                    self.loadGrid();
                                }
                            }).fail((err) =>{
                                if(err) dialog.alertError(err);
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
                $("#C1_5_container").focus();
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

    class GridItem {
        id: number;
        employeeCode: string;
        employeeName: string;
        salaryCode: string;
        salaryLayoutName: string;
        bonusCode: string;
        bonusLayoutName: string;

        constructor(id: number,employeeCode: string, employeeName: string,salaryCode: string, salaryLayoutName: string, bonusCode: string,bonusLayoutName: string  ) {
            this.id = id;
            this.employeeCode = employeeCode;
            this.employeeName = _.escape(employeeName);
            this.salaryCode = salaryCode;
            this.salaryLayoutName = salaryLayoutName;
            this.bonusCode = bonusCode;
            this.bonusLayoutName = bonusLayoutName;
        }
    }

    export interface IStateLinkSettingMaster {
        hisId: string;
        masterCode: string;
        salaryCode: number;
        bonusCode: number;
    }
    export class StateLinkSettingMaster {
        hisId: string;
        masterCode: string;
        salaryCode: number;
        bonusCode: number;
        constructor(params: IStateLinkSettingMaster) {
            this.hisId = params ? params.hisId : null;
            this.masterCode = params ? params.masterCode : null;
            this.salaryCode = params ? params.salaryCode : null;
            this.bonusCode = params ? params.bonusCode : null;
        }
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

}