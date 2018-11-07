module nts.uk.pr.view.qmm020.d.viewmodel {

    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        to : KnockoutObservable<string> = ko.observable(' ～ ');
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentSelectedHis: KnockoutObservable<any> = ko.observable();
        index: number;
        currentSelectedDep:  any;
        singleSelectedCode: any;
        columns: any;
        selectedCodes2: any;
        list: any;
        button : string = '<button  data-bind = "click: $vm.viewmodelD.openTest"> Test </button>';
        mode: KnockoutObservable<number> = ko.observable(0);
        constructor(){
            block.invisible();
            let self = this;
            let firstHistory;
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
            service.getStateCorrelationHisDeparmentById().done((data)=>{
                if(data.length > 0){
                    _.forEach(data,(o)=>{
                        self.listStateCorrelationHis.push(new ItemModel(o.historyID, '', self.convertYearMonthToDisplayYearMonth(o.startYearMonth) + self.to() + self.convertYearMonthToDisplayYearMonth(o.endYearMonth)));
                    });
                    firstHistory = _.head(self.listStateCorrelationHis());
                    self.currentSelectedHis(firstHistory.code);
                }
            }).fail((err)=>{
                if(err) dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });


            self.items = ko.observableArray([]);
            self.createList();
            self.currentSelectedDep = ko.observableArray([]);
            self.currentSelectedDep.subscribe((depID)=>{
               console.dir(depID);
            });

            self.selectedCodes2 = ko.observable([]);
            self.index = 0;
            let template = '<button class="setting" onclick="openDlg()" style="margin-left: 20px;">設定</button>${custom}';

            self.columns = ko.observableArray([{ headerText: getText('QMM020_33'), width: "450px", key: 'code', dataType: "string" },
                /*{ headerText: getText('QMM020_20'), key: 'nodeText', width: "250px", dataType: "string", ntsControl: 'Button' },
                { headerText: getText('QMM020_23'), key: 'custom', width: "250px", dataType: "string", ntsControl: 'Button' }]),*/
                { headerText: getText('QMM020_23'), key: 'custom', width: '100px', template: template},
                { headerText: getText('QMM020_23'), key: 'custom', width: '100px', template: template}]);

        }

        createList(){
            let self = this;
            let node = new Node('0000' + 0, self.button + 0, []);
            self.createGrisList(self.list,0,node);
            self.items.push(node);
        }
        createGrisList(data:any, parrent_id:number, node: Node){
            let self = this;
            _.forEach(data,(o)=>{
                if(parrent_id === o.CD){
                    let level = new Node('0000' + o.DEP_ID, self.button + o.DEP_ID, []);
                    node.childs.push(level);
                    self.createGrisList(self.list,o.DEP_ID,level);
                }
            });
        }

        callconvertToCommand(){
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
                    startYearMonth: 201803,
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
                   historyID: hisID,
                    masterCode: o.code,
                    salaryCode: '01',
                    bonusCode: '02',
               };
                let temp = new StateLinkSettingMaster(i);
                list.push(temp);
                if(o.childs.length == 0) return;
                self.convertToCommand(hisID,o.childs,list);
            });
        }

        searchByDepID(data:any,depID:string,salaryCode:string,bonusCode:string){
            let self = this;
            _.forEach(data,(o)=>{
                if(o.code === depID){
                    o. nodeText = self.button + salaryCode;
                    o.custom = self.button + bonusCode;
                    return;
                }
                self.searchByDepID(o.childs,depID,salaryCode,bonusCode);
            });
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }



    }

    export class ItemModel {
        code: string;
        name: string;
        display: string;
        constructor(code: string, name: string, display: string) {
            this.code = code;
            this.name = name;
            this.display = display;
        }
    }

    class Node {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: Array<Node>;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.name;
            self.childs = childs;
            self.custom = self.name;
        }
    }

    export interface IStateLinkSettingMaster {
        historyID: string;
        masterCode: string;
        salaryCode: number;
        bonusCode: number;
    }
    export class StateLinkSettingMaster {
        historyID: string;
        masterCode: string;
        salaryCode: number;
        bonusCode: number;
        constructor(params: IStateLinkSettingMaster) {
            this.historyID = params ? params.historyID : null;
            this.masterCode = params ? params.masterCode : null;
            this.salaryCode = params ? params.salaryCode : null;
            this.bonusCode = params ? params.bonusCode : null;
        }
    }
    export enum MODE{
        NEW = 0,
        UPDATE = 1,
    }
}

let openDlg = function() {
    nts.uk.ui.windows.sub.modal("/view/qmm/020/m/index.xhtml").onClosed(() => {
        alert("OK");
    });
};