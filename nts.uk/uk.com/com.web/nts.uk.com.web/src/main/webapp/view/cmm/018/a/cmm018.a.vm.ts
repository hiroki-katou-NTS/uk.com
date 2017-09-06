module nts.uk.com.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import servicebase = cmm018.shr.servicebase;
    import vmbase = cmm018.shr.vmbase;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    export module viewmodel {
        export class ScreenModel {
            nameCompany: KnockoutObservable<string>;
            modeCommon:KnockoutObservable<boolean> = ko.observable(true);
            isUpdate: KnockoutObservable<boolean> = ko.observable(true);
            listMode: KnockoutObservableArray<any>;
            selectedModeCode: KnockoutObservable<number> = ko.observable(0);
            dataSource: KnockoutObservable<vmbase.CommonApprovalRootDto>;
            lstCompany: KnockoutObservableArray<vmbase.CompanyAppRootDto>;
            columns: KnockoutObservableArray<any>;
            listHistory: KnockoutObservableArray<vmbase.ListHistory>;
            currentCode : KnockoutObservable<string>;
            lstItems: KnockoutObservableArray<vmbase.ListApproval>;
            lstDelete: KnockoutObservableArray<vmbase.CompanyAppRootADto>
            historyStr: KnockoutObservable<string>;
            lstNameAppType: KnockoutObservableArray<any>;
            namebr:  KnockoutObservable<string>;
            approver: KnockoutObservableArray<vmbase.Approver>;
            listAppPhase: KnockoutObservableArray<vmbase.ApprovalPhaseDto>;
            cpA: KnockoutObservableArray<vmbase.CompanyAppRootADto>;
            checkAAA: KnockoutObservable<boolean> = ko.observable(true);
            //-----SCREEN B
            headers: any;
            index: number;
            items2: any;
            selectedCode: any;
            singleSelectedCode: any;
            constructor() {
                var self = this;
                let itemBonus: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],null,null,null,null,null);
                self.cpA = ko.observableArray([]);
                self.namebr =  ko.observable("'kakashi'+ <br/> + 'hatake'");
                self.lstCompany = ko.observableArray([]);
                self.nameCompany  = ko.observable('Kakashi');
                self.listAppPhase = ko.observableArray([]);
                self.historyStr  = ko.observable('');
                self.dataSource = ko.observable(null);
                self.listMode = ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
                self.currentCode = ko.observable(null);
                self.columns = ko.observableArray([
                    { headerText: 'Id', key: 'approvalId', hidden: true },
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' }
                ]);
                self.listHistory = ko.observableArray([]);
                self.lstDelete = ko.observableArray([]);
                self.startPage();
                //subcribe
                self.currentCode.subscribe(function(codeChanged) {
                    self.cpA([]);
                    let itemCurrent = self.findApproval(codeChanged);
                    let history = self.findHistory(codeChanged);
                    //gan cho appPhaseX
                    self.listAppPhase(self.checklist(itemCurrent.lstAppPhase));
                    let aaa = new vmbase.CompanyAppRootADto(itemCurrent.company.approvalId,self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],self.listAppPhase()[3],self.listAppPhase()[4]);
                    self.cpA.push(aaa);
                    console.log(aaa);
                    self.historyStr(history.dateRange);
                });
                self.lstNameAppType = ko.observableArray([]);
                self.selectedModeCode.subscribe(function(codeChanged) {
                    if(codeChanged==1){//private
                        self.checkAAA(false);
                    }else{//common
                        self.checkAAA(true);
                    }
                });
                //----SCREEN B
                self.headers = ko.observableArray(["Item","Text", "Auto generated Field"]);
                let a: Child = new Child('1','a');
                let a1: Child = new Child('2','a');
                let c = new Node('0001', 'サービス部', [ a,a1]);
                let b: Array<Child> = [];
                self.items2 = ko.observableArray([
                    new Node('0001', 'サービス部', [ c,c]), 
                    new Node('0002', '開発部', [c,c],
                    new Node('0003', '開発部', [c,c],
                    new Node('0004', '開発部', [c,c])
                ]);
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.index = 0;
            }
            /**
             * startPage
             * get all data
             */
            startPage(){
                var self = this;
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataCom(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    self.dataSource(data);
                    //list company
                    self.lstCompany(self.dataSource().lstCompanyRoot);
                    self.convertHist(self.lstCompany());
                    if(self.listHistory().length > 0){
                        self.currentCode(self.listHistory()[0].approvalId);
                    }
                    self.nameCompany(data.companyName);
                    console.log(data);
                    //get name application tyle
                    servicebase.getNameAppType().done(function(lstName){
                        self.lstNameAppType(lstName);
                        console.log(lstName);
                    })
                })
            }
            /*
                open Dialog C
            */
            openDialogC() {
                var self = this;
            }
            
            /*
                open Dialog D
            */
            openDialogD(value) {
//                nts.uk.ui.block.invisible()
                var self = this;
            }
            
            openDialogI(){
                let paramI: vmbase.IData_Param = {
                                name: "",
                                startDate: "",
                                startDateOld: "",
                                check: 0,
                                mode: 1
                                }
                setShared('CMM018I_PARAM', paramI);
                modal("/view/cmm/018/i/index.xhtml");
            }
            openDialogJ(){
                let lst: Array<vmbase.UpdateHistoryDto> = [];
                let paramJ: vmbase.JData_Param = {
                    name:"",
                    startDate: "",
                    endDate: "",
                    workplaceId: "",
                    employeeId: "",
                    check: 1,
                    mode: 0,
                    overlapFlag: true,
                    startDatePrevious: "",
                    lstUpdate: lst
                }
                setShared('CMM018J_PARAM', paramJ);
                modal("/view/cmm/018/j/index.xhtml");
                
            }
            deleteRow(index){
                var self = this;
                let objSelected: vmbase.CompanyAppRootADto = self.findApprovalA(index);
                self.lstDelete.push(objSelected);
                let lstNew = self.cpA();
                self.cpA([]);
                _.each(lstNew, function(item: vmbase.CompanyAppRootADto){
                    if(item.approvalId != index){
                        self.cpA.push(item);
                    }
                });
           }
            /**
             * find approval is selected
             */
//            findApproval(value: string): any {
//                let self = this;
//                return _.find(self.lstItems(), function(obj: vmbase.ListApproval) {
//                    return obj.approvalId == value;
//                })
//            }
            findApprovalA(value: string): vmbase.CompanyAppRootADto {
                let self = this;
                return _.find(self.cpA(), function(obj: vmbase.CompanyAppRootADto) {
                    return obj.approvalId == value;
                })
            }
            findApproval(value: string): vmbase.CompanyAppRootDto {
                let self = this;
                return _.find(self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
                    return obj.company.approvalId == value;
                })
            }
            /**
             * find appRootHist is selected
             */
            findHistory(value: string): vmbase.ListHistory {
                let self = this;
                return _.find(self.listHistory(), function(obj: vmbase.ListHistory) {
                    return obj.approvalId == value;
                })
            }
            /**
             * register
             */
            register(){
                var self = this;
                console.log(self.lstDelete());
            }
            /**
             * convert to lst history
             */
            convertHist(lstCom: Array<vmbase.CompanyAppRootDto>): void{
                var self = this;
                self.listHistory([]);
                _.each(lstCom, function(itemHist: vmbase.CompanyAppRootDto){
                    let str = itemHist.company.startDate + ' ~ ' + itemHist.company.endDate;
                    self.listHistory.push(new vmbase.ListHistory(itemHist.company.approvalId,str));
                });
            }
            /**
             * 
             */
            checklist(list: Array<vmbase.ApprovalPhaseDto>): Array<vmbase.ApprovalPhaseDto>{
                var self = this;
                let listFix: Array<vmbase.ApprovalPhaseDto> = [];
                let itemBonus: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],null,null,null,null,null);
                for(let i: number = 1; i<=5; i++){
                    let a = self.findAppPhase(i,list);
                    if( a != null){
                        listFix.push(a);
                    }else{
                        listFix.push(itemBonus);
                    }
                }
                return listFix;
            }
            /**
             * 
             */
//            checklistApprover(list: Array<vmbase.ApproverDto>): Array<vmbase.ApproverDto>{
//                var self = this;
//                let listFix: Array<vmbase.ApproverDto> = [];
//                let itemBonus: vmbase.ApproverDto = null;
//                for(let i: number =0; i<5; i++){
//                    let a = self.findAppPhase(i,list);
//                    if( a != null){
//                        listFix.push(a);
//                    }else{
//                        listFix.push(itemBonus);
//                    }
//                }
//                return listFix;
//            }
            /**
             * 
             */
            findAppPhase(orderNumber: number,list: Array<vmbase.ApprovalPhaseDto> ): vmbase.ApprovalPhaseDto{
             return _.find(list, function(obj: vmbase.ApprovalPhaseDto) {
                    return obj.orderNumber == orderNumber;
                 });
            }
            /**
             * 
             */
            findApprover(orderNumber: number,list: Array<vmbase.ApproverDto> ): vmbase.ApproverDto{
             return _.find(list, function(obj: vmbase.ApproverDto) {
                    return obj.orderNumber == orderNumber;
                 });
            }
            
        }
        class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: Array<any>;
            constructor(code: string, name: string, childs: any) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
                self.custom =  "Kakashi";
            }
        }
        class Child{
            code: string;
            nodeText: string;
            constructor(code: string, nodeText: string){
                this.code = code;
                this.nodeText = nodeText;
            }
        }
    }
}