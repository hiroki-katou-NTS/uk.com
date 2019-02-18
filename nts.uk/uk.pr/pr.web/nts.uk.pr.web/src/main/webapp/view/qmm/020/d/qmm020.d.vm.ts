module nts.uk.pr.view.qmm020.d.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;

    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentSelectedHis: KnockoutObservable<any> = ko.observable();
        date: KnockoutObservable<string> = ko.observable();
        textBaseDate: KnockoutObservable<string> = ko.observable();
        index: number;
        currentSelectedDep:  any;
        columns: any;
        selectedCodes2: any;
        list: any;
        startYearMonth: KnockoutObservable<string> = ko.observable();
        mode: KnockoutObservable<number> = ko.observable(0);

        shared: KnockoutObservable<any> = ko.observable();
        setShared: KnockoutObservable<any> = ko.observable();
        getShared: KnockoutObservable<any> = ko.observable();
        modal: KnockoutObservable<any> = ko.observable();

        transferMode: KnockoutObservable<number> = ko.observable();
        enableAddHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        enableEditHisButton:  KnockoutObservable<boolean> = ko.observable(true);
        enableRegisterButton:KnockoutObservable<boolean> = ko.observable(true);
        newHistoryId: KnockoutObservable<string> = ko.observable();
        isModeAddHistory: KnockoutObservable<boolean> = ko.observable();
        constructor(){
            block.invisible();
            let self = this;
            self.shared(model);
            self.setShared(setShared);
            self.getShared(getShared);
            self.modal(modal);

            //TODO
            self.list = [
                {
                    CID: '000000000000-0001',
                    HIST_ID: 1,
                    DEP_ID: 1,
                    CD: 0,
                    NAME: 'Ngon Ngu Lap Trinh'
                },
                {
                    CID: '000000000000-0001',
                    HIST_ID: 2,
                    DEP_ID: 2,
                    CD: 0,
                    NAME: 'PHP'
                },
                {
                    CID: '000000000000-0001',
                    HIST_ID: 3,
                    DEP_ID: 3,
                    CD: 0,
                    NAME: 'Java'
                },
                {
                    CID: '000000000000-0001',
                    HIST_ID: 4,
                    DEP_ID: 4,
                    CD: 2,
                    NAME: 'C#'
                },
            ]

            self.items = ko.observableArray([]);
            //self.createList();
            self.currentSelectedDep = ko.observableArray([]);
            //TODO
            self.currentSelectedHis.subscribe((hisId)=>{
               console.dir(hisId);
            });

            self.selectedCodes2 = ko.observable([]);
            self.index = 0;
            let template = '<button class="setting" onclick="openScreenM(\'${departmentID}\')" >設定</button>${salaryCode} ${salaryName} ';
            let template1 = '<button class="setting" onclick="openScreenM1(\'${departmentID}\')" >設定</button>${bonusCode} ${bonusName} ';

            self.columns = ko.observableArray([{ headerText: getText('QMM020_33'), width: "450px", key: 'departmentID', dataType: "string" },
                { headerText: getText('QMM020_20'), key: 'salaryCode', fields: ['salaryCode', 'salaryName'],  width: '160px', template: template},
                { headerText: '', key: 'salaryName', width: '100px', hidden: true},
                { headerText: getText('QMM020_22'), key: 'bonusCode', fields: ['bonusCode', 'bonusName'], width: '160px', template: template1},
                { headerText: '', key: 'bonusName', width: '100px', hidden: true},
                ]);
            block.clear();
        }

        startPage(): JQueryPromise<any>{
            block.invisible();
            let self = this;
            let firstHistory, dfd = $.Deferred();
            let listStateCorrelationHis = [];

            service.getStateCorrelationHisDeparmentById().done((data)=>{
                if(data.length > 0){
                    _.forEach(data,(o)=>{
                        listStateCorrelationHis.push(new ItemModel(o.historyID,  o.startYearMonth , o.endYearMonth));
                    });
                    self.listStateCorrelationHis(listStateCorrelationHis);
                    firstHistory = _.head(self.listStateCorrelationHis());
                    self.currentSelectedHis(firstHistory.hisId);
                }
                dfd.resolve();
            }).fail((err)=>{
                dfd.reject();
                if(err) dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });

            return dfd.promise();

        }


        openScreenJ(){

            let self = this;
            let hisId,startYearMonth, endYearMonth,temp;
            let rs = _.head(self.listStateCorrelationHis());
            setShared(model.PARAMETERS_SCREEN_J.INPUT, {
                startYearMonth : rs ? rs.startYearMonth : 0,
                isPerson: false,
                modeScreen: model.MODE_SCREEN.DEPARMENT,
            });

            modal("/view/qmm/020/j/index1.xhtml").onClosed(()=>{
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
                }else if(!params && self.listStateCorrelationHis().length === 0){
                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                }
                block.clear();
                $("#D1_5_container").focus();
            });
        }

        openScreenK(){
            let self = this;
            let listStateCorrelationHis = [];
            service.getStateCorrelationHisDeparmentById().done((data)=>{
                listStateCorrelationHis = self.convertToList(data);
                let rs = _.find(listStateCorrelationHis,{hisId: self.currentSelectedHis()});
                let index = _.findIndex(self.listStateCorrelationHis(), {hisId: self.currentSelectedHis()});
                setShared(model.PARAMETERS_SCREEN_K.INPUT, {
                    startLastYearMonth : index != self.listStateCorrelationHis().length-1 ? self.listStateCorrelationHis()[index+1].startYearMonth : 0,
                    startYearMonth : rs ? rs.startYearMonth : 0,
                    endYearMonth:  rs ? rs.endYearMonth : 999912,
                    hisId: self.currentSelectedHis(),
                    modeScreen: model.MODE_SCREEN.DEPARMENT,
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

                        }
                        self.enableAddHisButton(true);
                        self.enableEditHisButton(true);
                    }).fail((err)=>{
                        if(err) dialog.alertError(err);
                    }).always(()=>{
                        block.clear();
                    });
                }else{
                    block.clear();
                }
                $("#D1_5_container").focus();
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

        createList(){
            let self = this;
            let node = new Node('0000' + 0, 'TaiTT', '01','aaaaaaa','02','ffffffff',[]);
            self.createGrisList(self.list,0,node);
            self.items.push(node);
        }

        createGrisList(data:any, parrent_id:number, node: Node){
            let self = this;
            _.forEach(data,(o)=>{
                if(parrent_id === o.CD){
                    let level = new Node('0000' + o.DEP_ID, 'Tai',o.DEP_ID, null,null,null,[]);
                    node.childs.push(level);
                    self.createGrisList(self.list,o.DEP_ID,level);
                }
            });
        }

        registerStateCorrelationHisDeparment(){
            block.invisible();
            let self = this;
            let historyID;
            if(self.mode() === MODE.NEW){
                historyID = nts.uk.util.randomId();
            }else if(self.mode() === MODE.UPDATE){
                historyID = self.currentSelectedHis();
            }
            let list1: Array<IStateLinkSettingMaster> = [];
            self.convertToCommand(historyID,self.items(),list1);

            let data = {
                listStateLinkSettingMasterCommand : list1,
                stateLinkSettingDateCommand : {
                    historyID: historyID ,
                    date: '2018/03/15'
                },
                stateCorrelationHisDeparmentCommand :{
                    cid: '',
                    historyID: historyID,
                    startYearMonth: self.startYearMonth(),
                    endYearMonth: 999912                },
                mode: self.mode()
            };
            service.registerStateCorrelationHisDeparment(data).done(()=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {

                });
            }).fail((err)=>{
                if(err) dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });

        }
        convertToCommand(hisID:string,data:any,list: Array<IStateLinkSettingMaster>){
           let self = this;
            _.forEach(data,(o) =>{

               let i = {
                   hisId: hisID,
                    masterCode: o.departmentID,
                    salaryCode: '01',
                    bonusCode: '02',
               };
                let temp = new StateLinkSettingMaster(i);
                list.push(temp);
                if(o.childs.length == 0) return;
                self.convertToCommand(hisID,o.childs,list);
            });
        }

        searchByDepID(data:any,depID:string,code:string,name:string, isSalaryCd){
            let self = this;
            _.forEach(data,(o)=>{
                if(o.departmentID === depID){
                    if (isSalaryCd) {
                        o.salaryCode = code;
                        o.salaryName = name;
                    } else {
                        o.bonusCode = code;
                        o.bonusName = name;
                    }
                    return;
                }
                self.searchByDepID(o.childs,depID,code,name,isSalaryCd);
            });
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
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

    class Node {
        departmentID: string;
        departmentName: string;
        display: string;
        salaryCode: string;
        salaryName: string;
        bonusCode: string;
        bonusName: string;
        childs: Array<Node>;
        constructor(departmentID: string,
                    departmentName: string,
                    salaryCode:string,
                    salaryName: string,
                    bonusCode:string,
                    bonusName:string,
                    childs: Array<Node>) {
            this.departmentID = departmentID;
            this.departmentName = departmentName;
            this.salaryCode = salaryCode;
            this.salaryName = salaryName;
            this.bonusCode = bonusCode;
            this.bonusName = bonusName;
            this.childs = childs;
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
    export enum MODE{
        NEW = 0,
        UPDATE = 1,
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }
    export enum HISTORY{
        NEW = '0'
    }
}



let openScreenM = function(id: string) {

    let model = __viewContext.viewModel.viewmodelD;
    let rs = _.find(model.listStateCorrelationHis(),{hisId: model.currentSelectedHis()});

    nts.uk.ui.windows.setShared('PARAM_INPUT_SCREEN_M',{
        startYearMonth: rs.startYearMonth,
    });
    nts.uk.ui.windows.sub.modal("/view/qmm/020/m/index.xhtml").onClosed(()=>{
        let params = nts.uk.ui.windows.getShared('PARAM_OUTPUT_SCREEN_M');
        if(params){
            model.searchByDepID(model.items(),id,params.statementCode,params.statementName,true);
            model.items(model.items());
        }
    });

};

let openScreenM1 = function(id: string) {
    let model = __viewContext.viewModel.viewmodelD;
    let rs = _.find(model.listStateCorrelationHis(),{hisId: model.currentSelectedHis()});
    nts.uk.ui.windows.setShared('PARAM_INPUT_SCREEN_M',{
        startYearMonth: rs.startYearMonth,
    });
    nts.uk.ui.windows.sub.modal("/view/qmm/020/m/index.xhtml").onClosed(()=>{
        let params = nts.uk.ui.windows.getShared('PARAM_OUTPUT_SCREEN_M');
        if(params){
            model.searchByDepID(model.items(),id,params.statementCode,params.statementName,false);
            model.items(model.items());
        }
    });
};