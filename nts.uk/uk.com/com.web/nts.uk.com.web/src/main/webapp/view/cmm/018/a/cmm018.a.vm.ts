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
            columns: KnockoutObservableArray<any>;
            listHistory: KnockoutObservableArray<vmbase.ListHistory> = ko.observableArray([]);
            currentCode : KnockoutObservable<number>;
            lstItems: KnockoutObservableArray<vmbase.ListApproval>;
            lstDelete: KnockoutObservableArray<vmbase.RootDeleteDto>
            historyStr: KnockoutObservable<string>;
            lstNameAppType: KnockoutObservableArray<vmbase.ApplicationType>;
            approver: KnockoutObservableArray<vmbase.ApproverDto>;
            //---list AppPhase---
            listAppPhase: KnockoutObservableArray<vmbase.ApprovalPhaseDto> = ko.observableArray([]);
            //---list data right---
            cpA: KnockoutObservableArray<vmbase.CompanyAppRootADto> = ko.observableArray([]);
            checkAAA: KnockoutObservable<number> = ko.observable(0);
            //----- list chua id history va approval id
            lstAppId: KnockoutObservableArray<string> = ko.observableArray([]);
            //-------data dialog I gui sang
            dataI: KnockoutObservable<vmbase.IData>;
            //param transfer to dialog K
            approverInfor : KnockoutObservableArray<vmbase.ApproverDtoK> = ko.observableArray([]);
            confirmedPerson : KnockoutObservable<string> = ko.observable("");
            selectTypeSet : KnockoutObservable<number> = ko.observable(0);
            //___________KCP009______________
            employeeInputList: KnockoutObservableArray<vmbase.EmployeeModel>;
            systemReference: KnockoutObservable<number>;
            isDisplayOrganizationName: KnockoutObservable<boolean>;
            targetBtnText: string;
            listComponentOption: vmbase.ComponentOption;
            selectedItem: KnockoutObservable<string>;
            tabindex: number;
            //__________Sidebar________
            tabSelected: KnockoutObservable<number> = ko.observable(0);
            lstCompany: KnockoutObservableArray<vmbase.DataDisplayComDto> = ko.observableArray([]);
            lstWorkplace: KnockoutObservableArray<vmbase.DataDisplayWpDto> = ko.observableArray([]);
            lstPerson: KnockoutObservableArray<vmbase.DataDisplayPsDto> = ko.observableArray([]);
            constructor() {
                var self = this;
                self.nameCompany  = ko.observable('Kakashi');
                self.historyStr  = ko.observable('');
                self.dataI = ko.observable(null);
                self.listMode = ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
                self.currentCode = ko.observable(null);
                self.columns = ko.observableArray([
                    { headerText: 'Id', key: 'id', hidden: true},
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' }
                ]);
                self.lstDelete = ko.observableArray([]);
                self.startPage();
                //---subscribe currentCode (list left)
                self.currentCode.subscribe(function(codeChanged) {
                    let history = self.findHistory(codeChanged);
                    self.historyStr(history.dateRange);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    //TH: tab company
                    if(self.tabSelected() == 0){
                        let lstCompany = self.findAppIdForCom(codeChanged);
                       _.each(lstCompany.lstCompanyRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                            item.company.applicationType, item.company.employmentRootAtr, item.lstAppPhase));
                        }); 
                    }
                    //TH: tab work place
                    else if(self.tabSelected() == 1){
                        let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(codeChanged);
                        _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                            item.workplace.applicationType, item.workplace.employmentRootAtr, item.lstAppPhase));
                        });
                    }
                    //TH: tab person
                    else{
                        let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(codeChanged);
                        _.each(lstPerson.lstPersonRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                            item.person.applicationType, item.person.employmentRootAtr, item.lstAppPhase));
                        });
                    }
                    self.cpA(self.checklistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                    //----delete dataI
                    self.dataI();
                });
                //---subscribe tab selected---
                self.tabSelected.subscribe(function(codeChanged) {
                    self.currentCode();
                    self.historyStr('');
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    //TH: tab company
                    if(codeChanged == 0){
                        let lstCompany = self.findAppIdForCom(self.currentCode());
                       _.each(lstCompany.lstCompanyRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                            item.company.applicationType, item.company.employmentRootAtr, item.lstAppPhase));
                        }); 
                    }
                    //TH: tab work place
                    else if(codeChanged == 1){
                        self.getDataWorkplace().done(function(){
                            self.convertHistForWp(self.lstWorkplace());
                            if(self.listHistory().length > 0){
                                self.currentCode(self.listHistory()[0].id);
                                let history = self.findHistory(self.currentCode());
                                if(history !== undefined){
                                    self.historyStr(history.dateRange);
                                }
                            }
                           let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(self.currentCode());
                            _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                                lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                                item.workplace.applicationType, item.workplace.employmentRootAtr, item.lstAppPhase));
                            }); 
                        });;
                    }
                    //TH: tab person
                    else{
                        self.getDataPerson().done(function(){
                            self.convertHistForPs(self.lstPerson());
                            if(self.listHistory().length > 0){
                                self.currentCode(self.listHistory()[0].id);
                                let history = self.findHistory(self.currentCode());
                                if(history !== undefined){
                                    self.historyStr(history.dateRange);
                                }
                            }
                            let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(self.currentCode());
                            _.each(lstPerson.lstPersonRoot, function(item){
                                lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                                item.person.applicationType, item.person.employmentRootAtr, item.lstAppPhase));
                            });
                        });;
                    }
                    self.cpA(self.checklistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                });
                self.lstNameAppType = ko.observableArray([]);
                self.selectedModeCode.subscribe(function(codeChanged) {
                    if(codeChanged==1){//private
                        self.checkAAA(0);
                    }else{//common
                        self.checkAAA(1);
                    }
                });
                //_______KCP009_______
                self.employeeInputList = ko.observableArray([
                    {id: 'a1', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name'}, 
                    {id: 'a2', code: 'A000000000004', businessName: '日通　純一郎4', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a3', code: 'A000000000005', businessName: '日通　純一郎5', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a4', code: 'A000000000006', businessName: '日通　純一郎6', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a5', code: 'A000000000007', businessName: '日通　純一郎7', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a6', code: 'A000000000008', businessName: '日通　純一郎8', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a7', code: 'A000000000009', businessName: '日通　純一郎9', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a8', code: 'A000000000010', businessName: '日通　純一郎10', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a9', code: 'A000000000011', businessName: '日通　純一郎11', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: 'a10', code: 'A000000000002', businessName: '日通　純一郎2', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: '03', code: 'A000000000003', businessName: '日通　純一郎3', workplaceName: '名古屋支店', depName: 'Dep Name'}]);
                    
                self.systemReference = ko.observable(vmbase.SystemType.EMPLOYMENT);
                self.isDisplayOrganizationName = ko.observable(true);
                self.targetBtnText = nts.uk.resource.getText("KCP009_3");
                self.selectedItem = ko.observable(null);
                self.tabindex = 1;
                // Initial listComponentOption
                self.listComponentOption = {
                    systemReference: self.systemReference(),
                    isDisplayOrganizationName: self.isDisplayOrganizationName(),
                    employeeInputList: self.employeeInputList,
                    targetBtnText: self.targetBtnText,
                    selectedItem: self.selectedItem,
                    tabIndex: self.tabindex
                };
                $('#emp-component').ntsLoadListComponent(self.listComponentOption);
                self.selectedItem.subscribe(function(codeChanged){
                    alert(codeChanged);
                });
                //____________sidebar tab_____________
                 $("#sidebar").ntsSideBar("init", {
                    active: 0,
                    activate: (event, info) => {
                        switch(info.newIndex) {
                            case 0:
                            self.tabSelected(0);
                                break;
                            case 1://workplace
                                self.tabSelected(1);
                                break;
                            default://employee
                                self.tabSelected(2);
                         }
                    }
                });
            }
            /**
             * startPage
             * get all data company
             */
            startPage(){
                var self = this;
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
                        self.lstCompany()
                        return;
                    } 
                    console.log(data);
                    //list company
                    self.lstCompany(data.lstCompany);
                    self.convertHistForCom(data.lstCompany);
                    self.nameCompany(data.lstCompany[0].companyName);
                    //get name application tyle
                    servicebase.getNameAppType().done(function(lstName: Array<vmbase.ApplicationType>){
                        _.each(lstName, function(item){
                             self.lstNameAppType.push(new vmbase.ApplicationType(item.value, item.localizedName));
                        });
                        if(self.listHistory().length > 0){
                            self.currentCode(self.listHistory()[0].id);
                        }
                    })
                    
                })
            }
            getDataWorkplace(): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(1,'000000000000000000000000000000000001','');
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
                        return;
                    } 
                    self.lstWorkplace(data.lstWorkplace);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            getDataPerson(): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(2,'000000000000000000000000000000000001','000426a2-181b-4c7f-abc8-6fff9f4f983a');
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
                        return;
                    } 
                    self.lstPerson(data.lstPerson);
                    dfd.resolve();
                });
                return dfd.promise();
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
            /**
             * Open dialog 履歴追加 : Add History
             */
            openDialogI(){
                var self = this;
                //最新の期間履歴を選択するかチェックする(check có đang chọn period history mới nhất hay không)
                if(self.currentCode()!=1){
                    //エラーメッセージ(Msg_181)(error message (Msg_181))
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_181" }).then(function(res){
//                            block.clear();
                        });
                        return;
                }
                let paramI: vmbase.IData_Param = {
                                name: "",
                                startDate: self.findHistory(self.currentCode()).startDate,
                                check: 0,
                                mode: 0
                                }
                setShared('CMM018I_PARAM', paramI);
                modal("/view/cmm/018/i/index.xhtml").onClosed(function(){
                    let data: vmbase.IData = getShared('CMM018I_DATA') || {startDate: '', startDateOld: '',
                                check: 1, mode: 0, copyDataFlag: false};
                    self.dataI(data);
                    //初めから作成するを選択した場合(tạo mới từ đầu)
                    if(!data.copyDataFlag){
                        self.lstCompany();
                    }
                    //履歴から引き継ぐから作成する場合(tao moi theo lich su cu)
                    else{
                        //重なるフラグがfalse
                        //重なるフラグがtrue
                        
                    }
                });
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
            //from A -> K
            openDialogK(obj: vmbase.ApprovalPhaseDto, approvalId: string, appTypeValue: number, int: number){
                var self = __viewContext.viewModel.viewmodelA;
                console.log(obj);
                self.approverInfor();
                _.each(obj.approver, function(item){
                    self.approverInfor.push(new vmbase.ApproverDtoK(item.approverId,item.approverId,'Kakashi'));
                })
                //確定者 code
                self.confirmedPerson(obj.approvalForm);
                setShared("CMM018K_PARAM", { 
                                        appType: "0", //設定する対象申請名 
                                        formSetting: 0,//0: 全員確認、１：誰か一人
                                        approverInfor: self.approverInfor(),//承認者一覧
                                        confirmedPerson: self.confirmedPerson, //確定者
                                        selectTypeSet: 0, //職位指定（1）、個人指定（0）
                                        tab: 0//０：会社、１：職場、２：個人
                                        });
                modal("/view/cmm/018/k/index.xhtml").onClosed(() => {
                    let data: vmbase.KData = getShared('CMM018K_DATA');
                    let data2: vmbase.CompanyAppRootADto = self.findRootAR(approvalId);
                    _.remove(self.cpA(), function(item: vmbase.CompanyAppRootADto) {
                        return item.approvalId == approvalId;
                    });
                   let a: vmbase.CompanyAppRootADto = null;
                    let approver: Array<vmbase.ApproverDto> = [];
                    _.each(data.approverInfor, function(item){
                        approver.push(new vmbase.ApproverDto(item.code,item.code, item.code,1,1,1));
                    });
                   let b: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto(approver,'','',0,0,0);
                switch(int){
                    case 1:
                         a = new vmbase.CompanyAppRootADto(data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,
                                b,data2.appPhase2,data2.appPhase3,data2.appPhase4,data2.appPhase5);
                        break;
                    case 2: 
                         a = new vmbase.CompanyAppRootADto(data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,
                                data2.appPhase1,b,data2.appPhase3,data2.appPhase4,data2.appPhase5);
                        break;
                    case 3: 
                         a = new vmbase.CompanyAppRootADto(data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,
                                data2.appPhase1,data2.appPhase2,b,data2.appPhase4,data2.appPhase5);
                        break;
                    case 4: 
                          a = new vmbase.CompanyAppRootADto(data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,
                                data2.appPhase1,data2.appPhase2,data2.appPhase3,b,data2.appPhase5); 
                        break;
                    default : 
                          a = new vmbase.CompanyAppRootADto(data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,
                                data2.appPhase1,data2.appPhase2,data2.appPhase3,data2.appPhase4,b);
                        break;
                    } 
                    let dataOld: Array<vmbase.CompanyAppRootADto> = self.cpA();
                    dataOld.push(a);
                    let dataNew = _.orderBy(dataOld, ["appTypeValue"], ["asc"]);
                    self.cpA(dataNew);
                    self.cpA.valueHasMutated();
                }); 
            }
            /**
             * delete row (root)
             */
            deleteRow(index){
                var self = this;
                let objSelected: vmbase.CompanyAppRootADto = self.findApprovalA(index);
                self.lstDelete.push(new vmbase.RootDeleteDto(objSelected.approvalId, objSelected.historyId));
                let lstNew = self.cpA();
                self.cpA([]);
                _.each(lstNew, function(item: vmbase.CompanyAppRootADto){
                    if(item.approvalId != index){
                        self.cpA.push(item);
                    }
                });
           }
            findApprovalA(value: string): vmbase.CompanyAppRootADto {
                let self = this;
                return _.find(self.cpA(), function(obj: vmbase.CompanyAppRootADto) {
                    return obj.approvalId == value;
                })
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
             * find appRootHist is selected of company
             */
            findAppIdForCom(value: number): vmbase.DataDisplayComDto {
                let self = this;
                return _.find(self.lstCompany(), function(obj) {
                    return obj.id == value;
                })
            }
            /**
             * find appRootHist is selected of work place
             */
            findAppIdForWp(value: number): vmbase.DataDisplayWpDto {
                let self = this;
                return _.find(self.lstWorkplace(), function(obj) {
                    return obj.id == value;
                })
            }
            /**
             * find appRootHist is selected of person
             */
            findAppIdForPs(value: number): vmbase.DataDisplayPsDto {
                let self = this;
                return _.find(self.lstPerson(), function(obj) {
                    return obj.id == value;
                })
            }
            
            /**
             * find root is selected
             */
            findRootAR(value: string): vmbase.CompanyAppRootADto {
                let self = this;
                return _.find(self.cpA(), function(obj) {
                    return obj.approvalId == value;
                });
            }
            
            /**
             * register
             * add history
             * add/update approver
             * delete root
             */
            register(){
                var self = this;
//                let dataResiter: vmbase.DataResigterDto = new vmbase.DataResigterDto(self.lstDelete(), self.dataI());
                console.log(self.lstDelete());
                
            }
            /**
             * convert to list history company
             */
            convertHistForCom(lstCom: Array<vmbase.DataDisplayComDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstCom, function(itemHist: vmbase.DataDisplayComDto){
                    let sDate = itemHist.lstCompanyRoot[0].company.startDate;
                    let eDate = itemHist.lstCompanyRoot[0].company.endDate;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, sDate + ' ~ ' + eDate, sDate, eDate));
                });
                 let a: Array<vmbase.ListHistory> =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
                self.listHistory(a);
            }
            /**
             * convert to list history work place
             */
            convertHistForWp(lstWp: Array<vmbase.DataDisplayWpDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstWp, function(itemHist: vmbase.DataDisplayWpDto){
                    let sDate = itemHist.lstWorkplaceRoot[0].workplace.startDate;
                    let eDate = itemHist.lstWorkplaceRoot[0].workplace.endDate;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, sDate + ' ~ ' + eDate, sDate, eDate));
                });
                 let a: Array<vmbase.ListHistory> =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
                self.listHistory(a);
            }
            /**
             * convert to list history for person
             */
            convertHistForPs(lstPs: Array<vmbase.DataDisplayPsDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstPs, function(itemHist: vmbase.DataDisplayPsDto){
                    let sDate = itemHist.lstPersonRoot[0].person.startDate;
                    let eDate = itemHist.lstPersonRoot[0].person.endDate;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, sDate + ' ~ ' + eDate, sDate, eDate));
                });
                 let a: Array<vmbase.ListHistory> =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
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
             * check lisr root (TH: <14)
             */
            checklistRoot(root: Array<vmbase.DataRootCheck>): Array<vmbase.CompanyAppRootADto>{
                 var self = this;
                let lstbyApp: Array<vmbase.CompanyAppRootADto> = [];
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0,0,0);
                //Them common type
                _.each(root, function(itemRoot){
                    self.listAppPhase(self.checklist(itemRoot.lstAppPhase));
                    //TH: co data
                    if(itemRoot.applicationType == null && itemRoot.employmentRootAtr ==0){
                        lstbyApp.push(new vmbase.CompanyAppRootADto(true, -1,' 共通ルート', itemRoot.approvalId,
                                        itemRoot.historyId,
                                        self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                        self.listAppPhase()[3],self.listAppPhase()[4]));
                    }
                });
                //TH: khong co data
                if(lstbyApp.length < 1){
                    lstbyApp.push(new vmbase.CompanyAppRootADto(true, -1,' 共通ルート', '', '', a, a, a, a, a));
                }
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType, index){
                    let check = false;
                    _.each(root, function(itemRoot){
                        if(item.value != 14 && item.value == itemRoot.applicationType){
                            lstbyApp.push(new vmbase.CompanyAppRootADto(false, item.value,item.localizedName, itemRoot.approvalId,
                                    itemRoot.historyId,
                                    self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                    self.listAppPhase()[3],self.listAppPhase()[4]));
                            check = true;
                        }
                    });
                    if(!check && item.value != 14){//chua co du lieu
                        lstbyApp.push(new vmbase.CompanyAppRootADto(false, item.value, item.localizedName,index,'',a,a,a,a,a));
                    }
                })
                return lstbyApp;
            }
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
            columns: KnockoutObservableArray<any> = ko.observableArray([{ headerText: "Item Code", width: "250px", key: 'approvalId', dataType: "number", hidden: true },
                                                    { headerText: "Item Text", key: 'nameAppType', width: "200px", dataType: "string" }]);
            //--------data right: company
            lstCompanyRoot: KnockoutObservableArray<vmbase.CompanyAppRootDto> = ko.observableArray([]);
            comRoot: KnockoutObservable<vmbase.CompanyAppRootADto> = ko.observable(null);
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
                let aaa = new vmbase.CompanyAppRootADto(true,0,'1','','',a, a, a, a, a);
                    self.cpA.push(aaa);
                //subcribe singleSelectedCode
                self.singleSelectedCode.subscribe(function(codeChanged) {//approvalId
                    let com = self.findRootB(codeChanged);
                    if(com != null && com !== undefined){
                        self.historyStr(com.company.startDate + ' ~ ' + com.company.endDate);
                        let name = self.findNameApp(com.company.applicationType, com.company.employmentRootAtr);
                        let checkCommon = false;
                        if(com.company.applicationType == null && com.company.employmentRootAtr ==0){//common
                            checkCommon = true;
                        }
                        self.comRoot(new vmbase.CompanyAppRootADto(checkCommon,com.company.applicationType,name,com.company.approvalId,com.company.historyId,com.lstAppPhase[0],com.lstAppPhase[1],com.lstAppPhase[2],com.lstAppPhase[3],com.lstAppPhase[4]));
                    }else{
                        self.historyStr('');
                        let value = self.findAppbyName(codeChanged);
                        let appTypeValue: number = -1;
                        let common: boolean = false;
                        if(codeChanged == '共通ルート'){
                            common = true;
                        }
                        if(value != null && value !== undefined){
                            appTypeValue = value.value;
                        }
                        let b = new vmbase.ApprovalPhaseDto([],'','',0,0,0);
                        let a = new vmbase.CompanyAppRootADto(common, appTypeValue, codeChanged, '', '', b, b, b, b, b)
                        self.comRoot(a);
                    }
//                    self.comRoot.valueHasMutated();
                });
            }
            getData(){
                var self = this;
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    self.dataSourceB(data);
                    self.lstCompanyRoot(data.lstCompanyRoot);
                    //get name application tyle
                    servicebase.getNameAppType().done(function(lstName: Array<vmbase.ApplicationType>){
                        _.each(lstName, function(item){
                             self.lstNameAppType.push(new vmbase.ApplicationType(item.value, item.localizedName));
                        });
                        console.log(lstName);
                        self.dataDisplay(self.convert(data.lstCompanyRoot));
                        self.singleSelectedCode('共通ルート');
                        console.log(self.dataDisplay());
                    });
                    
                })
            }
            /**
             * find appType name
             */
            findNameApp(applicationType: number, employmentRootAtr): string{
                let self = this;
                if(applicationType == null && employmentRootAtr ==0){//common
                    return '共通ルート';
                }
                let name = self.findAppbyValue(applicationType);
                return name.localizedName;
            }
            /**
             * find appType by value
             */
            findAppbyValue(applicationType: number): vmbase.ApplicationType{
                let self = this;
                return _.find( self.lstNameAppType(), function(obj: vmbase.ApplicationType) {
                    return obj.value == applicationType;
                });
            }
            /**
             * find appType by name
             */
            findAppbyName(name: string): vmbase.ApplicationType{
                let self = this;
                return _.find( self.lstNameAppType(), function(obj: vmbase.ApplicationType) {
                    return obj.localizedName == name;
                });
            }
            /**
             * find root is selected
             */
            findRootB(value: string): vmbase.CompanyAppRootDto {
                let self = this;
                return _.find( self.lstCompanyRoot(), function(obj: vmbase.CompanyAppRootDto) {
                    return obj.company.approvalId == value;
                });
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
                //lay history cua common
                _.each(root, function(itemRoot){
                        if(itemRoot.company.applicationType == null && itemRoot.company.employmentRootAtr ==0){
                            appCommon.push(new vmbase.DataTree(itemRoot.company.approvalId, itemRoot.company.startDate + '~' + itemRoot.company.endDate,[]));
                        }
                });
                aa.push(new vmbase.DataTreeB('common',' 共通ルート', appCommon));  
                //lay theo don  
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType){
                    let lstbyApp: Array<vmbase.Com> = [];
                    _.each(root, function(itemRoot){
                        if(item.value != 14 && item.value == itemRoot.company.applicationType){
                            lstbyApp.push(new vmbase.Com(itemRoot.company.approvalId, itemRoot.company.startDate + ' ~ ' + itemRoot.company.endDate));
                        }
                    });
                    if(item.value != 14){
                        bb.push(new vmbase.DataTree(item.localizedName, item.localizedName, lstbyApp));    
                    }
                })
                let str = nts.uk.resource.getText("CMM018_7");
                aa.push(new vmbase.DataTreeB('private' ,str,bb));
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