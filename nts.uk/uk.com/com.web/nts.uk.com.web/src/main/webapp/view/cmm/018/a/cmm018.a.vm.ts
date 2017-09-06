module nts.uk.com.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import servicebase = cmm018.shr.servicebase;
    import vmbase = cmm018.shr.vmbase;
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
//                self.approver = ko.observableArray([new vmbase.Approver('1','1','htk kks'),
//                                                    new vmbase.Approver('2','2','hoa da'),
//                                                    new vmbase.Approver('3','3','kakashi'),
//                                                    new vmbase.Approver('4','4','hoa ddd'),
//                                                    new vmbase.Approver('5','5','dfgfd')
//                    ]);
//                self.lstItems = ko.observableArray([new vmbase.ListApproval('000','Kakashi0',self.approver()),
//                                                    new vmbase.ListApproval('111','Kakashi1', self.approver()),
//                                                    new vmbase.ListApproval('222','Kakashi2',self.approver()),
//                                                    new vmbase.ListApproval('333','Kakashi3',self.approver()),
//                                                    new vmbase.ListApproval('444','Kakashi4',self.approver())
//                    ]);
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
                    self.historyStr(history.dateRange);
                });
                self.lstNameAppType = ko.observableArray([]);
                
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
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/i/index.xhtml");
            }
            openDialogJ(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/j/index.xhtml");
                
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
                for(let i: number = 1; i<6; i++){
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
    }
}