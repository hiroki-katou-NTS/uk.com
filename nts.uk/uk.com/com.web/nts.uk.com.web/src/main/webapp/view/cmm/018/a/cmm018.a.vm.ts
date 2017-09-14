module nts.uk.com.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import servicebase = cmm018.shr.servicebase;
    import vmbase = cmm018.shr.vmbase;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    export module viewmodelA {
        export class ScreenModel {
            nameCompany: KnockoutObservable<string>;
            modeCommon:KnockoutObservable<boolean> = ko.observable(true);
            isUpdate: KnockoutObservable<boolean> = ko.observable(true);
            listMode: KnockoutObservableArray<any>;
            selectedModeCode: KnockoutObservable<number> = ko.observable(1);
//            dataSource: KnockoutObservable<vmbase.CommonApprovalRootDto>;
            lstCompany: KnockoutObservableArray<vmbase.DataDisplayComDto>;
            columns: KnockoutObservableArray<any>;
            listHistory: KnockoutObservableArray<vmbase.ListHistory>;
            currentCode : KnockoutObservable<number>;
            lstItems: KnockoutObservableArray<vmbase.ListApproval>;
            lstDelete: KnockoutObservableArray<vmbase.CompanyAppRootADto>
            historyStr: KnockoutObservable<string>;
            lstNameAppType: KnockoutObservableArray<vmbase.ApplicationType>;
            approver: KnockoutObservableArray<vmbase.ApproverDto>;
            listAppPhase: KnockoutObservableArray<vmbase.ApprovalPhaseDto>;
            cpA: KnockoutObservableArray<vmbase.CompanyAppRootADto>;
            checkAAA: KnockoutObservable<number> = ko.observable(0);
            //----- list chua id history va approvalid
            lstAppId: KnockoutObservableArray<string> = ko.observableArray([]);
            constructor() {
                var self = this;
                let itemBonus: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],null,null,null,null,null);
                self.cpA = ko.observableArray([]);
                self.lstCompany = ko.observableArray([]);
                self.nameCompany  = ko.observable('Kakashi');
                self.listAppPhase = ko.observableArray([]);
                self.historyStr  = ko.observable('');
//                self.dataSource = ko.observable(null);
                self.listMode = ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
                self.currentCode = ko.observable(null);
                self.columns = ko.observableArray([
                    { headerText: 'Id', key: 'id', hidden: true},
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' }
                ]);
                self.listHistory = ko.observableArray([]);
                self.lstDelete = ko.observableArray([]);
                self.startPage();
                //subcribe
                self.currentCode.subscribe(function(codeChanged) {
//                    self.cpA([]);
                    let itemCurrent = self.findApproval(codeChanged);
                    let history = self.findHistory(codeChanged);
                    //gan cho appPhaseX
//                    self.listAppPhase(self.checklist(itemCurrent.lstAppPhase));
//                    let aaa = new vmbase.CompanyAppRootADto(itemCurrent.company.approvalId,self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],self.listAppPhase()[3],self.listAppPhase()[4]);
//                    self.cpA.push();
                    self.historyStr(history.dateRange);
                    let lstCompany = self.findAppId(codeChanged);
                    self.cpA(self.checklistRoot(lstCompany.lstCompanyRoot));
                });
                self.lstNameAppType = ko.observableArray([]);
                self.selectedModeCode.subscribe(function(codeChanged) {
                    if(codeChanged==1){//private
                        self.checkAAA(0);
                    }else{//common
                        self.checkAAA(1);
                    }
                });

            }
            /**
             * startPage
             * get all data
             */
            startPage(){
                var self = this;
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','',1);
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
//                        self.dataSource();
                        self.lstCompany()
                        return;
                    } 
//                    self.dataSource(data.lstCompany);
                    console.log(data);
                    //list company
                    self.lstCompany(data.lstCompany);
                    self.convertHist(self.lstCompany());
                    if(self.listHistory().length > 0){
                        self.currentCode(self.listHistory()[0].id);
                    }
                    self.nameCompany(data.lstCompany[0].companyName);
                    //get name application tyle
                    servicebase.getNameAppType().done(function(lstName: Array<vmbase.ApplicationType>){
                        _.each(lstName, function(item){
                             self.lstNameAppType.push(new vmbase.ApplicationType(item.value, item.localizedName));
                        });
                        self.cpA(self.checklistRoot(self.lstCompany()[0].lstCompanyRoot));
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
            findApproval(value: number): vmbase.CompanyAppRootDto {
                let self = this;
//                return _.find(self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
//                    return obj.company.approvalId == value;
//                })
                return null;
            }
            /**
             * find appRootHist is selected
             */
            findHistory(value: number): vmbase.ListHistory {
                let self = this;
                return _.find(self.listHistory(), function(obj: vmbase.ListHistory) {
                    return obj.id == value;
                })
            }
            /**
             * find appRootHist is selected
             */
            findAppId(value: number): vmbase.DataDisplayComDto {
                let self = this;
                return _.find(self.lstCompany(), function(obj) {
                    return obj.id == value;
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
            convertHist(lstCom: Array<vmbase.DataDisplayComDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstCom, function(itemHist: vmbase.DataDisplayComDto){
                    let str = itemHist.lstCompanyRoot[0].company.startDate + ' ~ ' + itemHist.lstCompanyRoot[0].company.endDate;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, str));
                });
                 let a =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
                self.listHistory(a);
            }
            /**
             * check list app phase (TH: <5)
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
             * check lisr root (TH: <13)
             */
            checklistRoot(root: Array<vmbase.CompanyAppRootDto>): Array<vmbase.CompanyAppRootADto>{
                ///
                 var self = this;
                let lstbyApp: Array<vmbase.CompanyAppRootADto> = [];
//                let aa: Array<vmbase.DataTreeB> = [];//list tra ve
//                let bb: Array<vmbase.DataTree> = [];
//                let appCommon: Array<vmbase.CompanyAppRootADto> = [];
                _.each(root, function(itemRoot){
                    self.listAppPhase(self.checklist(itemRoot.lstAppPhase));
                    if(itemRoot.company.applicationType == null && itemRoot.company.employmentRootAtr ==0){
                        lstbyApp.push(new vmbase.CompanyAppRootADto(true, ' 共通ルート', itemRoot.company.approvalId,
                                        self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                        self.listAppPhase()[3],self.listAppPhase()[4]));
                    }
                });
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType){
//                    let lstbyApp: Array<vmbase.Com> = [];
                    let check = false;
                    _.each(root, function(itemRoot){
                        if(item.value != 14 && item.value == itemRoot.company.applicationType){
                            lstbyApp.push(new vmbase.CompanyAppRootADto(false, item.localizedName, itemRoot.company.approvalId,
                                    self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                        self.listAppPhase()[3],self.listAppPhase()[4]));
                            check = true;
                        }
                    });
                    if(!check && item.value != 14){//chua co du lieu
                    let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0,0,0);
                        lstbyApp.push(new vmbase.CompanyAppRootADto(false, item.localizedName,'',a,a,a,a,a));
                    }
//                    if(item.value != 14){
//                        bb.push(new vmbase.DataTree(item.localizedName, lstbyApp));    
//                    }
                })
//                let str = nts.uk.resource.getText("CMM018_7");
//                aa.push(new vmbase.DataTreeB(str,bb));
                return lstbyApp;
                ///
//                var self = this;
//                let listFix: Array<vmbase.ComRootDto> = [];
////                let itemBonus: vmbase.ComRootDto = new vmbase.ComRootDto([],null,null,null,null,null);
//                for(let i: number = 0; i<=14; i++){
//                    let a = self.findRoot(i,list);
//                    if( a != null){
//                        listFix.push(a); 
//                    }else{
//                        listFix.push(new vmbase.ComRootDto());
//                    }
//                }
//                return listFix;
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
             * find app phase
             */
            findAppPhase(orderNumber: number,list: Array<vmbase.ApprovalPhaseDto> ): vmbase.ApprovalPhaseDto{
             return _.find(list, function(obj: vmbase.ApprovalPhaseDto) {
                    return obj.orderNumber == orderNumber;
                 });
            }
            /**
             * find approver
             */
            findApprover(orderNumber: number,list: Array<vmbase.ApproverDto> ): vmbase.ApproverDto{
             return _.find(list, function(obj: vmbase.ApproverDto) {
                    return obj.orderNumber == orderNumber;
                 });
            } 
            /**
             * find root
             */
            findRoot(orderNumber: number,list: Array<vmbase.CompanyAppRootDto> ): vmbase.CompanyAppRootDto{
             return _.find(list, function(obj: vmbase.CompanyAppRootDto) {
                    return obj.company.applicationType == orderNumber;
                 });
            }
            
        }

    }
    
    export module viewmodelB{
        export class ScreenModel {
        //-----SCREEN B
            selectedCode: any;
            singleSelectedCode: any;
            lstNameAppType: KnockoutObservableArray<vmbase.ApplicationType>;
            dataSourceB: KnockoutObservable<vmbase.CommonApprovalRootDto>;
            dataDisplay: KnockoutObservableArray<vmbase.DataTreeB>;
            //param transfer to dialog K
            approverInfor : KnockoutObservableArray<vmbase.ApproverDtoK> = ko.observableArray([]);
            confirmedPerson : KnockoutObservable<string> = ko.observable("");
            selectTypeSet : KnockoutObservable<number> = ko.observable(0);
            //---display screen B
            cpA: KnockoutObservableArray<vmbase.CompanyAppRootADto> = ko.observableArray([]);
            historyStr: KnockoutObservable<string> = ko.observable('');
            nameCompany: KnockoutObservable<string> = ko.observable('Kakashi');
            constructor(){
                var self = this;
                //----SCREEN B
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.lstNameAppType = ko.observableArray([]);
                self.dataSourceB = ko.observable(null);
                self.dataDisplay = ko.observableArray([]);
                self.getData();
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0,0,0);
                let aaa = new vmbase.CompanyAppRootADto(true,'','1',a, a, a, a, a);
                    self.cpA.push(aaa);
                self.singleSelectedCode.subscribe(function(codeChanged) {
                    alert(codeChanged);
                });
            }
            getData(){
                var self = this;
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','',0);
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    self.dataSourceB(data);
                    //get name application tyle
                    servicebase.getNameAppType().done(function(lstName: Array<vmbase.ApplicationType>){
                        _.each(lstName, function(item){
                             self.lstNameAppType.push(new vmbase.ApplicationType(item.value, item.localizedName));
                        });
                        console.log(lstName);
                        self.dataDisplay(self.convert(data.lstCompanyRoot));
                        console.log(self.dataDisplay());
                    });
                    
                })
            }
            /**
             * convert data db to data display
             */
            convert(root: Array<vmbase.CompanyAppRootDto>): Array<vmbase.DataTreeB>{
                var self = this;
                let lstbyApp: Array<vmbase.Com> = [];
                let aa: Array<vmbase.DataTreeB> = [];//list tra ve
                let bb: Array<vmbase.DataTree> = [];
                let appCommon: Array<vmbase.DataTree> = [];
                _.each(root, function(itemRoot){
                        if(itemRoot.company.applicationType == null && itemRoot.company.employmentRootAtr ==0){
                            appCommon.push(new vmbase.DataTree(itemRoot.company.startDate + '~' + itemRoot.company.endDate,[]));
                        }
                });
                aa.push(new vmbase.DataTreeB(' 共通ルート', appCommon));    
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType){
                    let lstbyApp: Array<vmbase.Com> = [];
                    _.each(root, function(itemRoot){
                        if(item.value != 14 && item.value == itemRoot.company.applicationType){
                            lstbyApp.push(new vmbase.Com(itemRoot.company.approvalId, itemRoot.company.startDate + ' ~ ' + itemRoot.company.endDate));
                        }
                    });
                    if(item.value != 14){
                        bb.push(new vmbase.DataTree(item.localizedName, lstbyApp));    
                    }
                })
                let str = nts.uk.resource.getText("CMM018_7");
                aa.push(new vmbase.DataTreeB(str,bb));
                return aa;
            }
            openDialogK(approvalId: string){
                console.log(approvalId);
                var self = __viewContext.viewModel.viewmodelB;
                self.approverInfor.push(new vmbase.ApproverDtoK('1','A00000000001','Kakashi'));
                self.approverInfor.push(new vmbase.ApproverDtoK('2','A00000000002','HoaDa'));
                self.approverInfor.push(new vmbase.ApproverDtoK('3','A00000000003','Hatake_Kakashi'));
                self.approverInfor.push(new vmbase.ApproverDtoK('4','A00000000004','Vegeta'));
                self.approverInfor.push(new vmbase.ApproverDtoK('5','A00000000005','Songoku'));
                //確定者 code
                self.confirmedPerson('A00000000001');
                setShared("CMM018K_PARAM", { 
                                        appType: "0", //設定する対象申請名 
                                        formSetting: 0,//0: 全員確認、１：誰か一人
                                        approverInfor: self.approverInfor(),//承認者一覧
                                        confirmedPerson: self.confirmedPerson, //確定者
                                        selectTypeSet: 0, //職位指定（1）、個人指定（0）
                                        tab: 0//０：会社、１：職場、２：個人
                                        });
                modal("/view/cmm/018/k/index.xhtml").onClosed(() => {
                    let data = getShared('CMM018K_DATA');
                }); 
            }
            /**
             * register
             */
            register(){
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
        }
    }
}