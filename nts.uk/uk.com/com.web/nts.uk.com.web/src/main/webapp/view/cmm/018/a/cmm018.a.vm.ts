module nts.uk.com.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import servicebase = cmm018.shr.servicebase;
    import vmbase = cmm018.shr.vmbase;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    //=========Mode A: まとめて登録モード==============
    export module viewmodelA {
        export class ScreenModel {
            nameCompany: KnockoutObservable<string>;
            modeCommon:KnockoutObservable<boolean> = ko.observable(true);
            isUpdate: KnockoutObservable<boolean> = ko.observable(true);
            listMode: KnockoutObservableArray<any>;
            selectedModeCode: KnockoutObservable<number> = ko.observable(0);
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
            checkAAA: KnockoutObservable<number> = ko.observable(1);
            //----- list chua id history va approval id
            lstAppId: KnockoutObservableArray<string> = ko.observableArray([]);
            //-------data dialog I gui sang
            dataI: KnockoutObservable<vmbase.IData>;
            checkAddHistory: KnockoutObservable<boolean> = ko.observable(false);
            idOld:  KnockoutObservable<number> = ko.observable(null);
//            listHistOld: KnockoutObservableArray<vmbase.ListHistory> = ko.observableArray([]);
            itemOld: KnockoutObservable<vmbase.DataDisplayComDto> = ko.observable(null);
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
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' },
                    { headerText: '', key: 'overLap', width: '20px'}
                ]);
                self.lstDelete = ko.observableArray([]);
                self.startPage();
                //---subscribe currentCode (list left)---
                self.currentCode.subscribe(function(codeChanged) {
                    if(codeChanged == -1){
                        return;
                    }
                    let history = self.findHistory(codeChanged);
                    self.historyStr(history.dateRange);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    //TH: tab company
                    if(self.tabSelected() == 0){
                        //check add history new
                        if(self.checkAddHistory()){
                            self.getDataCompany();
                        }
                        self.convertHistForCom(self.lstCompany());
                        let lstCompany = self.findAppIdForCom(codeChanged);
                       _.each(lstCompany.lstCompanyRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                            item.company.applicationType, item.company.employmentRootAtr, item.lstAppPhase));
                        }); 
                    }
                    //TH: tab work place
                    else if(self.tabSelected() == 1){
                        //check add history new
                        if(self.checkAddHistory()){
                            self.getDataWorkplace();
                        }
                        self.convertHistForWp(self.lstWorkplace());
                        let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(codeChanged);
                        _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                            item.workplace.applicationType, item.workplace.employmentRootAtr, item.lstAppPhase));
                        });
                    }
                    //TH: tab person
                    else{
                        //check add history new
                        if(self.checkAddHistory()){
                            let param: vmbase.ParamDto = new vmbase.ParamDto(2,'',self.selectedItem());
                            self.getDataPerson(param);
                        }
                        self.convertHistForPs(self.lstPerson());
                        let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(codeChanged);
                        if(lstPerson != undefined){
                           _.each(lstPerson.lstPersonRoot, function(item){
                                lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                                item.person.applicationType, item.person.employmentRootAtr, item.lstAppPhase));
                            }); 
                        }
                        
                    }
                    self.cpA(self.checklistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                    //----delete dataI---
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
                        self.cpA(self.checklistRoot(lstRoot));
                        self.cpA.valueHasMutated();
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
                        self.cpA(self.checklistRoot(lstRoot));
                        self.cpA.valueHasMutated();
                    }
                    //TH: tab person
                    else{
                        let param: vmbase.ParamDto = new vmbase.ParamDto(2,'',self.selectedItem());
                        self.getDataPerson(param);
                    }
                });
                self.lstNameAppType = ko.observableArray([]);
                //___subscribe selected mode code______
                self.selectedModeCode.subscribe(function(codeChanged) {
                    if(codeChanged==1){//private
                        self.checkAAA(0);
                        //TH: company
                        if(self.tabSelected()==0){
                             __viewContext.viewModel.viewmodelB.checkTabSelectedB(0,'');
                        }
                        //TH: work place
                        else if(self.tabSelected()==1){
                             __viewContext.viewModel.viewmodelB.checkTabSelectedB(1,'');
                        }
                        //TH: person
                        else{
                             __viewContext.viewModel.viewmodelB.checkTabSelectedB(2,self.selectedItem());
                        }
                    }else{//common
                        self.checkAAA(1);
                        //TH: company
                        if(self.tabSelected()==0){
                            
                        }
                        //TH: work place
                        else if(self.tabSelected()==1){
                            
                        }
                        //TH: person
                        else{
                            let param: vmbase.ParamDto = new vmbase.ParamDto(2,'',self.selectedItem());
                            self.getDataPerson(param);
                        }
                    }
                });
                //_______KCP009_______
                self.employeeInputList = ko.observableArray([
                    {id: 'a1', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name'}, 
                    {id: 'a2', code: 'A000000000004', businessName: '日通　純一郎4', workplaceName: '名古屋支店', depName: 'Dep Name'},
                    {id: '000426a2-181b-4c7f-abc8-6fff9f4f983a', code: 'A000000000005', businessName: '日通　純一郎5', workplaceName: '名古屋支店', depName: 'Dep Name'},
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
                //____subscribe selected item (return employee id)____
                self.selectedItem.subscribe(function(codeChanged){
                    //TH: mode A: まとめて登録モード
                    if(self.selectedModeCode()==0){
                        let param: vmbase.ParamDto = new vmbase.ParamDto(2,'',codeChanged);
                        self.getDataPerson(param);
                    }
                    //TH: mode B: 申請個別登録モード
                    else{
                        __viewContext.viewModel.viewmodelB.checkTabSelectedB(2,codeChanged);
                    }
                    
                });
                //____________sidebar tab_____________
                 $("#sidebar").ntsSideBar("init", {
                    active: 0,
                    activate: (event, info) => {
                        switch(info.newIndex) {
                            case 0:
                                if(self.selectedModeCode()==0){
                                    self.tabSelected(0);
                                }else{
                                    __viewContext.viewModel.viewmodelB.checkTabSelectedB(0,'');
                                }
                                break;
                            case 1://workplace
                                if(self.selectedModeCode()==0){
                                    self.tabSelected(1);
                                }else{
                                    __viewContext.viewModel.viewmodelB.checkTabSelectedB(1,'');
                                }
                                break;
                            default://employee
                                if(self.selectedModeCode()==0){
                                    self.tabSelected(2);
                                }else{
                                    __viewContext.viewModel.viewmodelB.checkTabSelectedB(2,self.selectedItem());
                                }
                         }
                    }
                });
            }
            /**
             * startPage
             * get all data company
             * mode A: まとめて登録モード
             */
            startPage(): JQueryPromise<any>{
                var self = this;
                 var dfd = $.Deferred();
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
                    dfd.resolve();
                })
                return dfd.promise();
            }
            /**
             * get data company 
             * mode A: まとめて登録モード
             */
            getDataCompany(): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'000000000000000000000000000000000001','');
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
                        return;
                    } 
                    self.lstCompany(data.lstCompany);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data work place 
             * mode A: まとめて登録モード
             */
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
            /**
             * get data person 
             * mode A: まとめて登録モード
             */
            getDataPerson(param: vmbase.ParamDto): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
//                let param: vmbase.ParamDto = new vmbase.ParamDto(2,'000000000000000000000000000000000001','000426a2-181b-4c7f-abc8-6fff9f4f983a');
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
                        return;
                    } 
                    self.lstPerson(data.lstPerson);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    self.convertHistForPs(self.lstPerson());
                    if(self.listHistory().length > 0){
                        self.currentCode(self.listHistory()[0].id);
                        let history = self.findHistory(self.currentCode());
                        if(history !== undefined){
                            self.historyStr(history.dateRange);
                        }
                    }
                    let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(self.currentCode());
                    if(lstPerson != undefined){
                        _.each(lstPerson.lstPersonRoot, function(item){
                        lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                        item.person.applicationType, item.person.employmentRootAtr, item.lstAppPhase));
                        });
                    }
                    self.cpA(self.checklistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * Open dialog 履歴追加 : Add History
             * mode A: まとめて登録モード
             */
            openDialogI(){
                var self = this;
                self.checkAddHistory(false);
                //item is selected
                let itemCurrent = self.findHistory(self.currentCode());
                if(itemCurrent.overLap == '※'){//TH: over lap
                   //最新の期間履歴を選択するかチェックする(check có đang chọn period history mới nhất hay không)
                    if(itemCurrent.endDate != '9999/12/31'){
                        //エラーメッセージ(Msg_181)(error message (Msg_181))
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_181" }).then(function(res){
    //                            block.clear();
                        });
                        return;
                    } 
                }
                let appType = null;
                let checkReload = false;
                let startDate = ''
                //TH: tab company
                if(self.tabSelected() == 0){
                    //Check dang chon item vua moi them
                    if(self.currentCode() == -1){
                        checkReload = true;
                        appType = self.itemOld().lstCompanyRoot[0].company.applicationType;
                    }else{
                        self.itemOld(self.findComRoot(self.currentCode()));
                        appType = self.itemOld().lstCompanyRoot[0].company.applicationType;
                    }
                    let histLAst = self.findHistByEdateCom('9999/12/31', appType);
                    startDate = histLAst.lstCompanyRoot[0].company.startDate
                }
                //TH: tab work place
                else if(self.tabSelected() == 1){
                    //Check dang chon item vua moi them
                    if(self.currentCode() == -1){
                        checkReload = true;
                        appType = self.itemOld().lstWorkplaceRoot[0].workplace.applicationType;
                    }else{
                        self.itemOld(self.findWpRoot(self.currentCode()));
                        appType = self.itemOld().lstWorkplaceRoot[0].workplace.applicationType;
                    }
                    let histLAst = self.findHistByEdateWp('9999/12/31', appType);
                    startDate = histLAst.lstWorkplaceRoot[0].workplace.startDate
                }
                //TH: tab person
                else{
                    //Check dang chon item vua moi them
                    if(self.currentCode() == -1){
                        checkReload = true;
                        appType = self.itemOld().lstPersonRoot[0].person.applicationType;
                    }else{
                        self.itemOld(self.findPsRoot(self.currentCode()));
                        appType = self.itemOld().lstPersonRoot[0].person.applicationType;
                    }
                    let histLAst = self.findHistByEdatePs('9999/12/31', appType);
                    startDate = histLAst.lstPersonRoot[0].person.startDate
                }
                
                let paramI: vmbase.IData_Param = {
                                name: "",
                                startDate: startDate,
                                check: self.tabSelected(),
                                mode: 0
                                }
                setShared('CMM018I_PARAM', paramI);
                modal("/view/cmm/018/i/index.xhtml").onClosed(function(){
                    //Xu ly sau khi dong dialog I
                    let data: vmbase.IData = getShared('CMM018I_DATA');
                    if(data == null){
                        return;
                    }
                    self.checkAddHistory(true);
                    if(checkReload){
                        _.remove( self.listHistory(), function(n) {
                          return n.id == -1;
                        });
                    itemCurrent = self.findHistory(self.idOld());
                    }
                    self.dataI(data);
                    //display history new in screen main
                    let tmp: Array<vmbase.ListHistory> = [];
                    let add: vmbase.ListHistory = new vmbase.ListHistory(-1, 
                        data.startDate + ' ~ ' + '9999/12/31', data.startDate,'9999/12/31','');
                    let startDate = new Date(data.startDate);
                    startDate.setDate(startDate.getDate() - 1);
                    let month =  self.checkDate(startDate.getMonth() + 1);
                    let day =  self.checkDate(startDate.getDate());
                    let endDateNew = startDate.getFullYear() + '/' + month +  '/' + day;
                    let old: vmbase.ListHistory = new vmbase.ListHistory(itemCurrent.id, 
                        itemCurrent.startDate + ' ~ ' + endDateNew, itemCurrent.startDate, endDateNew, itemCurrent.overLap);
                    if(checkReload){
                        _.each(self.listHistory(), function(item){
                            if(item.id != self.idOld()){
                              tmp.push(item);  
                            }
                        });
                    }else{
                        _.each(self.listHistory(), function(item){
                            if(item.id != self.currentCode()){
                              tmp.push(item);  
                            }
                        });
                    }
                    tmp.push(old);
                    tmp.push(add);
                    let lstHistNew: Array<vmbase.ListHistory> = _.orderBy(tmp, ["dateRange"], ["desc"]);
                    self.listHistory(lstHistNew);
                    //luu id
                    if(self.currentCode() != -1){
                        self.idOld(self.currentCode());
                    }
                    self.listHistory.valueHasMutated()
                    
                    //初めから作成するを選択した場合(tạo mới từ đầu)
                    if(!data.copyDataFlag){
                        let tmp: Array<vmbase.DataRootCheck> = [];
                        let check = self.checklistRoot(tmp);
                        self.cpA(check);
                    }
                    //履歴から引き継ぐから作成する場合(tao moi theo lich su cu)
                    else{
                        //重なるフラグがfalse
                        //重なるフラグがtrue
                    self.historyStr(add.dateRange);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    //TH: tab company
                    if(self.tabSelected() == 0){
                        //check add history new
                        if(self.checkAddHistory()){
                            self.getDataCompany();
                        }
                        let lstCompany = self.findAppIdForCom(self.idOld());
                       _.each(lstCompany.lstCompanyRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                            item.company.applicationType, item.company.employmentRootAtr, item.lstAppPhase));
                        }); 
                    }
                    //TH: tab work place
                    else if(self.tabSelected() == 1){
                        let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(self.idOld());
                        _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                            item.workplace.applicationType, item.workplace.employmentRootAtr, item.lstAppPhase));
                        });
                    }
                    //TH: tab person
                    else{
                        let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(self.idOld());
                        if(lstPerson != undefined){
                           _.each(lstPerson.lstPersonRoot, function(item){
                                lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                                item.person.applicationType, item.person.employmentRootAtr, item.lstAppPhase));
                            }); 
                        }
                        
                    }
                    self.cpA(self.checklistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                        self.currentCode(-1);
                    }
                });
            }
            /**
             * open dialog J
             * mode A: まとめて登録モード
             */
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
            /**
             * open dialog K
             * mode A: まとめて登録モード
             */
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
                    self.approverInfor([]);
                    let data: vmbase.KData = getShared('CMM018K_DATA');
                    if(data == null){
                        return;
                    }
                    let data2: vmbase.CompanyAppRootADto = self.findRootAR(approvalId);
                    _.remove(self.cpA(), function(item: vmbase.CompanyAppRootADto) {
                        return item.approvalId == approvalId;
                    });
                   let a: vmbase.CompanyAppRootADto = null;
                    let approver: Array<vmbase.ApproverDto> = [];
                    _.each(data.approverInfor, function(item){
                        approver.push(new vmbase.ApproverDto(item.code,item.code, item.code,1,1,1));
                    });
                   let b: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto(approver,'','',0,'', 0,0);
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
             * mode A: まとめて登録モード
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
             * mode A: まとめて登録モード
             */
            findHistory(value: number): vmbase.ListHistory {
                let self = this;
                return _.find(self.listHistory(), function(obj: vmbase.ListHistory) {
                    return obj.id == value;
                })
            }
            /**
             * find appRootHist by endDate and appType
             * domain: Company
             * mode A: まとめて登録モード
             */
            findHistByEdateCom(endDate: string, appType: number): vmbase.DataDisplayComDto {
                let self = this;
                return _.find(self.lstCompany(), function(obj){
                   return _.find(obj.lstCompanyRoot, function(item){
                        return (item.company.endDate == endDate && item.company.applicationType == appType);
                    });
                });
            }
            /**
             * find appRootHist by endDate and appType
             * domain: Work place
             * mode A: まとめて登録モード
             */
            findHistByEdateWp(endDate: string, appType: number): vmbase.DataDisplayWpDto {
                let self = this;
                return _.find(self.lstWorkplace(), function(obj){
                   return _.find(obj.lstWorkplaceRoot, function(item){
                        return (item.workplace.endDate == endDate && item.workplace.applicationType == appType);
                    });
                });
            }
            /**
             * find appRootHist by endDate and appType
             * domain: Person
             * mode A: まとめて登録モード
             */
            findHistByEdatePs(endDate: string, appType: number): vmbase.DataDisplayPsDto {
                let self = this;
                return _.find(self.lstPerson(), function(obj){
                   return _.find(obj.lstPersonRoot, function(item){
                        return (item.person.endDate == endDate && item.person.applicationType == appType);
                    });
                });
            }
            /**
             * find appRootHist is selected of company
             * mode A: まとめて登録モード
             */
            findAppIdForCom(value: number): vmbase.DataDisplayComDto {
                let self = this;
                return _.find(self.lstCompany(), function(obj) {
                    return obj.id == value;
                })
            }
            /**
             * find appRootHist is selected of work place
             * mode A: まとめて登録モード
             */
            findAppIdForWp(value: number): vmbase.DataDisplayWpDto {
                let self = this;
                return _.find(self.lstWorkplace(), function(obj) {
                    return obj.id == value;
                })
            }
            /**
             * find appRootHist is selected of person
             * mode A: まとめて登録モード
             */
            findAppIdForPs(value: number): vmbase.DataDisplayPsDto {
                let self = this;
                return _.find(self.lstPerson(), function(obj) {
                    return obj.id == value;
                })
            }
            
            /**
             * find root is selected
             * mode A: まとめて登録モード
             */
            findRootAR(value: string): vmbase.CompanyAppRootADto {
                let self = this;
                return _.find(self.cpA(), function(obj) {
                    return obj.approvalId == value;
                });
            }
            /**
             * find company root is selected
             * mode A: まとめて登録モード
             */
            findComRoot(value: number): vmbase.DataDisplayComDto {
                let self = this;
                return _.find(self.lstCompany(), function(obj) {
                    return obj.id == value;
                });
            }
            /**
             * find work place root is selected
             * mode A: まとめて登録モード
             */
            findWpRoot(value: number): vmbase.DataDisplayWpDto {
                let self = this;
                return _.find(self.lstWorkplace(), function(obj) {
                    return obj.id == value;
                });
            }
            /**
             * find person root is selected
             * mode A: まとめて登録モード
             */
            findPsRoot(value: number): vmbase.DataDisplayPsDto {
                let self = this;
                return _.find(self.lstPerson(), function(obj) {
                    return obj.id == value;
                });
            }
            /**
             * register
             * add history
             * add/update approver
             * delete root
             * mode A: まとめて登録モード
             */
            register(){
                var self = this;
//                let dataResiter: vmbase.DataResigterDto = new vmbase.DataResigterDto(self.lstDelete(), self.dataI());
                console.log(self.lstDelete());
                
            }
            /**
             * convert to list history company
             * mode A: まとめて登録モード
             */
            convertHistForCom(lstCom: Array<vmbase.DataDisplayComDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstCom, function(itemHist: vmbase.DataDisplayComDto){
                    let sDate = itemHist.lstCompanyRoot[0].company.startDate;
                    let eDate = itemHist.lstCompanyRoot[0].company.endDate;
                    let overLap = itemHist.overLap;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, sDate + ' ~ ' + eDate, sDate, eDate, overLap == true ? '※' : '' ));
                });
                 let a: Array<vmbase.ListHistory> =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
                self.listHistory(a);
            }
            /**
             * convert to list history work place
             * mode A: まとめて登録モード
             */
            convertHistForWp(lstWp: Array<vmbase.DataDisplayWpDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstWp, function(itemHist: vmbase.DataDisplayWpDto){
                    let sDate = itemHist.lstWorkplaceRoot[0].workplace.startDate;
                    let eDate = itemHist.lstWorkplaceRoot[0].workplace.endDate;
                    let overLap = itemHist.overLap;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, sDate + ' ~ ' + eDate, sDate, eDate, overLap == true ? '※' : '' ));
                });
                 let a: Array<vmbase.ListHistory> =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
                self.listHistory(a);
            }
            /**
             * convert to list history for person
             * mode A: まとめて登録モード
             */
            convertHistForPs(lstPs: Array<vmbase.DataDisplayPsDto>): void{
                var self = this;
                let lstHist: Array<vmbase.ListHistory> = [];
                self.listHistory([]);
                _.each(lstPs, function(itemHist: vmbase.DataDisplayPsDto){
                    let sDate = itemHist.lstPersonRoot[0].person.startDate;
                    let eDate = itemHist.lstPersonRoot[0].person.endDate;
                    let overLap = itemHist.overLap;
                    lstHist.push(new vmbase.ListHistory(itemHist.id, sDate + ' ~ ' + eDate, sDate, eDate, overLap == true ? '※' : '' ));
                });
                 let a: Array<vmbase.ListHistory> =  _.orderBy(lstHist, ["dateRange"], ["desc"]);
                self.listHistory(a);
            }
            /**
             * check list app phase (TH: <5)
             * mode A: まとめて登録モード
             */
            checklist(list: Array<vmbase.ApprovalPhaseDto>): Array<vmbase.ApprovalPhaseDto>{
                var self = this;
                let listFix: Array<vmbase.ApprovalPhaseDto> = [];
                let itemBonus: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],null,null,null,null,null,null);
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
             * mode A: まとめて登録モード
             */
            checklistRoot(root: Array<vmbase.DataRootCheck>): Array<vmbase.CompanyAppRootADto>{
                 var self = this;
                let lstbyApp: Array<vmbase.CompanyAppRootADto> = [];
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0, '',0,0);
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
             * mode A: まとめて登録モード
             */
            findAppPhase(orderNumber: number,list: Array<vmbase.ApprovalPhaseDto> ): vmbase.ApprovalPhaseDto{
             return _.find(list, function(obj: vmbase.ApprovalPhaseDto) {
                    return obj.orderNumber == orderNumber;
                 });
            }
            /**
             * find approver
             * mode A: まとめて登録モード
             */
            findApprover(orderNumber: number,list: Array<vmbase.ApproverDto> ): vmbase.ApproverDto{
             return _.find(list, function(obj: vmbase.ApproverDto) {
                    return obj.orderNumber == orderNumber;
                 });
            } 
            /**
             * find root
             * mode A: まとめて登録モード
             */
            findRoot(orderNumber: number,list: Array<vmbase.CompanyAppRootDto> ): vmbase.CompanyAppRootDto{
             return _.find(list, function(obj: vmbase.CompanyAppRootDto) {
                    return obj.company.applicationType == orderNumber;
                 });
            }
            /**
             * check Month/Day <10: insert 0
             */
            checkDate(value: number) : string{
                if(value < 10){
                    return '0' + value;
                }
                return value.toString();
            }
        }

    }
    /**
     * =============mode B: 申請個別登録モード===========
     * -------------viewmodelB---------------
     * 申請個別登録モード
     * Screen B,D,F
     */
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
            confirmedPerson : KnockoutObservable<number> = ko.observable(null);
            selectTypeSet : KnockoutObservable<number> = ko.observable(0);
            //---display screen B
            cpA: KnockoutObservableArray<vmbase.CompanyAppRootADto> = ko.observableArray([]);
            historyStr: KnockoutObservable<string> = ko.observable('');
            nameCompany: KnockoutObservable<string> = ko.observable('Kakashi');
            columns: KnockoutObservableArray<any> = ko.observableArray([{ headerText: "Item Code", width: "250px", key: 'approvalId', dataType: "number", hidden: true },
                                                    { headerText: "Item Text", key: 'nameAppType', width: "200px", dataType: "string" }]);
            //--------data right: company
            comRoot: KnockoutObservable<vmbase.CompanyAppRootADto> = ko.observable(null);
            //__________Sidebar________
            tabSelectedB: KnockoutObservable<number> = ko.observable(0);
            lstCompany: KnockoutObservableArray<vmbase.CompanyAppRootDto> = ko.observableArray([]);
            lstWorkplace: KnockoutObservableArray<vmbase.WorkPlaceAppRootDto> = ko.observableArray([]);
            lstPerson: KnockoutObservableArray<vmbase.PersonAppRootDto> = ko.observableArray([]);
            //__________KCP009: 申請個別登録モード_____
            employeeId: KnockoutObservable<string> = ko.observable('');
            constructor(){
                var self = this;
                //----SCREEN B
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.lstNameAppType = ko.observableArray([]);
                self.dataSourceB = ko.observable(null);
                self.dataDisplay = ko.observableArray([]);
                self.getDataCompanyPr();
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0, '',0,0);
                let aaa = new vmbase.CompanyAppRootADto(true,0,'1','','',a, a, a, a, a);
                    self.cpA.push(aaa);
                //_____subcribe singleSelectedCode________
                self.singleSelectedCode.subscribe(function(codeChanged) {//approvalId
                    //TH: company
                    if(self.tabSelectedB()==0){
                        let com = self.findRootComB(codeChanged);
                        //TH: item data
                        if(com != null && com !== undefined){
                            self.historyStr(com.company.startDate + ' ~ ' + com.company.endDate);
                            let name = self.findNameApp(com.company.applicationType, com.company.employmentRootAtr);
                            let checkCommon = false;
                            if(com.company.applicationType == null && com.company.employmentRootAtr ==0){//common
                                checkCommon = true;
                            }
                            self.comRoot(new vmbase.CompanyAppRootADto(checkCommon,com.company.applicationType,name,com.company.approvalId,com.company.historyId,com.lstAppPhase[0],com.lstAppPhase[1],com.lstAppPhase[2],com.lstAppPhase[3],com.lstAppPhase[4]));
                        }
                        //TH: 1,2,appName
                        else{
                            self.showItem(codeChanged);
                        }
                    }
                    //TH: work place
                    else if(self.tabSelectedB()==1){
                        let wp = self.findRootWpD(codeChanged);
                        if(wp != null && wp !== undefined){
                            self.historyStr(wp.workplace.startDate + ' ~ ' + wp.workplace.endDate);
                            let name = self.findNameApp(wp.workplace.applicationType, wp.workplace.employmentRootAtr);
                            let checkCommon = false;
                            if(wp.workplace.applicationType == null && wp.workplace.employmentRootAtr ==0){//common
                                checkCommon = true;
                            }
                            self.comRoot(new vmbase.CompanyAppRootADto(checkCommon,wp.workplace.applicationType,name,wp.workplace.approvalId,wp.workplace.historyId,wp.lstAppPhase[0],wp.lstAppPhase[1],wp.lstAppPhase[2],wp.lstAppPhase[3],wp.lstAppPhase[4]));
                        }
                        //TH: 1,2,appName
                        else{
                            self.showItem(codeChanged);
                        }
                    }
                    //TH: person
                    else{
                        let ps = self.findRootPsF(codeChanged);
                        if(ps != null && ps !== undefined){
                            self.historyStr(ps.person.startDate + ' ~ ' + ps.person.endDate);
                            let name = self.findNameApp(ps.person.applicationType, ps.person.employmentRootAtr);
                            let checkCommon = false;
                            if(ps.person.applicationType == null && ps.person.employmentRootAtr ==0){//common
                                checkCommon = true;
                            }
                            self.comRoot(new vmbase.CompanyAppRootADto(checkCommon,ps.person.applicationType,name,ps.person.approvalId,ps.person.historyId,ps.lstAppPhase[0],ps.lstAppPhase[1],ps.lstAppPhase[2],ps.lstAppPhase[3],ps.lstAppPhase[4]));
                        }
                        //TH: 1,2,appName
                        else{
                            self.showItem(codeChanged);
                        }
                    }
                    self.comRoot.valueHasMutated();
                });
                //____get name application type___
                servicebase.getNameAppType().done(function(lstName: Array<vmbase.ApplicationType>){
                    _.each(lstName, function(item){
                         self.lstNameAppType.push(new vmbase.ApplicationType(item.value, item.localizedName));
                    });
                });
            }
            /**
             * get data company mode 申請個別
             */
            getDataCompanyPr(): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    self.dataSourceB(data);
                    self.lstCompany(data.lstCompanyRoot);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data work place mode 申請個別
             */
            getDataWorkplacePr(): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(1,'000000000000000000000000000000000001','');
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    self.dataSourceB(data);
                    self.lstWorkplace(data.lstWorkplaceRoot);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data person mode 申請個別
             */
            getDataPersonPr(param: vmbase.ParamDto): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    self.dataSourceB(data);
                    self.lstPerson(data.lstPersonRoot);
                    let lstRoot: Array<vmbase.DataCheckModeB> = [];
                    //list left
                    _.each(self.lstPerson(), function(item){
                        lstRoot.push(new vmbase.DataCheckModeB(item.person.approvalId, item.person.startDate,
                            item.person.endDate, item.person.applicationType, item.person.employmentRootAtr));
                    });
                    //list right
                     let com = self.findRootWpD(self.singleSelectedCode());
                    self.dataDisplay(self.convert(lstRoot));
                    dfd.resolve();
                    });
                return dfd.promise();
            }
            /**
             * find appType name
             * mode B: 申請個別登録モード
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
             * mode B: 申請個別登録モード
             */
            findAppbyValue(applicationType: number): vmbase.ApplicationType{
                let self = this;
                return _.find( self.lstNameAppType(), function(obj: vmbase.ApplicationType) {
                    return obj.value == applicationType;
                });
            }
            /**
             * find appType by name
             * mode B: 申請個別登録モード
             */
            findAppbyName(name: string): vmbase.ApplicationType{
                let self = this;
                return _.find( self.lstNameAppType(), function(obj: vmbase.ApplicationType) {
                    return obj.localizedName == name;
                });
            }
            /**
             * find company root is selected
             * mode B: 申請個別登録モード
             */
            findRootComB(value: string): vmbase.CompanyAppRootDto {
                let self = this;
                return _.find( self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
                    return obj.company.approvalId == value;
                });
            }
            /**
             * find work place root is selected
             * mode B: 申請個別登録モード
             */
            findRootWpD(value: string): vmbase.WorkPlaceAppRootDto {
                let self = this;
                return _.find( self.lstWorkplace(), function(obj: vmbase.WorkPlaceAppRootDto) {
                    return obj.workplace.approvalId == value;
                });
            }
            /**
             * find person root is selected
             * mode B: 申請個別登録モード
             */
            findRootPsF(value: string): vmbase.PersonAppRootDto {
                let self = this;
                return _.find( self.lstPerson(), function(obj: vmbase.PersonAppRootDto) {
                    return obj.person.approvalId == value;
                });
            }
            /**
             * convert data db to data display
             * mode B: 申請個別登録モード
             */
            convert(root: Array<vmbase.DataCheckModeB>): Array<vmbase.DataTreeB>{
                var self = this;
                let lstbyApp: Array<vmbase.Com> = [];
                let aa: Array<vmbase.DataTreeB> = [];//list tra ve
                let bb: Array<vmbase.DataTree> = [];
                let appCommon: Array<vmbase.DataTree> = [];
                //lay history cua common
                _.each(root, function(itemRoot){
                    if(itemRoot.applicationType == null && itemRoot.employmentRootAtr ==0){
                        appCommon.push(new vmbase.DataTree(itemRoot.approvalId, itemRoot.startDate + '~' + itemRoot.endDate,[]));
                    }
                });
                aa.push(new vmbase.DataTreeB('共通ルート',' 共通ルート', appCommon));  
                //lay theo don  
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType){
                    let lstbyApp: Array<vmbase.Com> = [];
                    _.each(root, function(itemRoot){
                        if(item.value != 14 && item.value == itemRoot.applicationType){
                            lstbyApp.push(new vmbase.Com(itemRoot.approvalId, itemRoot.startDate + ' ~ ' + itemRoot.endDate));
                        }
                    });
                    if(item.value != 14){
                        bb.push(new vmbase.DataTree(item.localizedName, item.localizedName, lstbyApp));    
                    }
                })
                let str = nts.uk.resource.getText("CMM018_7");
                aa.push(new vmbase.DataTreeB(str ,str,bb));
                return aa;
            }
            /**
             * open dialog K
             * mode B: 申請個別登録モード
             */
            openDialogKB(obj: vmbase.ApprovalPhaseDto, approvalId: string, appTypeValue: number, int: number){
                var self = this;
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
                    self.approverInfor([]);
                    let data: vmbase.KData = getShared('CMM018K_DATA');
                    if(data == null){
                        return;
                    }
                    let tmp: vmbase.CompanyAppRootADto = self.comRoot();
//                    let data2: vmbase.CompanyAppRootADto = self.findRootAR(approvalId);
//                    _.remove(self.cpA(), function(item: vmbase.CompanyAppRootADto) {
//                        return item.approvalId == approvalId;
//                    });
                   let a: vmbase.CompanyAppRootADto = null;
                    let approver: Array<vmbase.ApproverDto> = [];
                    _.each(data.approverInfor, function(item){
                        approver.push(new vmbase.ApproverDto(item.code,item.code, item.code,1,1,1));
                    });
                   let b: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto(approver,'','',0,'',0,0);
                switch(int){
                    case 1:
                         a = new vmbase.CompanyAppRootADto(tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                b,tmp.appPhase2,tmp.appPhase3,tmp.appPhase4,tmp.appPhase5);
                        break;
                    case 2: 
                         a = new vmbase.CompanyAppRootADto(tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.appPhase1,b,tmp.appPhase3,tmp.appPhase4,tmp.appPhase5);
                        break;
                    case 3: 
                         a = new vmbase.CompanyAppRootADto(tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.appPhase1,tmp.appPhase2,b,tmp.appPhase4,tmp.appPhase5);
                        break;
                    case 4: 
                          a = new vmbase.CompanyAppRootADto(tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.appPhase1,tmp.appPhase2,tmp.appPhase3,b,tmp.appPhase5); 
                        break;
                    default : 
                          a = new vmbase.CompanyAppRootADto(tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.appPhase1,tmp.appPhase2,tmp.appPhase3,tmp.appPhase4,b);
                        break;
                    } 
                    self.comRoot(a);
//                    let dataOld: Array<vmbase.CompanyAppRootADto> = self.cpA();
//                    dataOld.push(a);
//                    let dataNew = _.orderBy(dataOld, ["appTypeValue"], ["asc"]);
//                    self.cpA(dataNew);
//                    self.cpA.valueHasMutated();
                }); 
            }
            /**
             * register
             * mode B: 申請個別登録モード
             */
            register(){
                var self = this;
            }
            /**
             * open dialog I
             * mode B: 申請個別登録モード
             */
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
            /**
             * open dialog J
             * mode B: 申請個別登録モード
             */
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
            /**
             * subscribe tab selected 
             * mode B: 申請個別登録モード
             */
            checkTabSelectedB(codeChanged: number, id: string){
                var self = this;
                let mode = __viewContext.viewModel.viewmodelA.selectedModeCode();
                    if(mode == 0){//まとめて登録モード
                        return;
                    }
                    self.singleSelectedCode();
                    self.historyStr('');
                    let lstRoot: Array<vmbase.DataCheckModeB> = [];
                    //TH: tab company
                    if(codeChanged == 0){
                        self.tabSelectedB(0);
                        self.getDataCompanyPr().done(function(data){
                            //list left
                            _.each(self.lstCompany(), function(item){
                                lstRoot.push(new vmbase.DataCheckModeB(item.company.approvalId, item.company.startDate,
                                    item.company.endDate, item.company.applicationType, item.company.employmentRootAtr));
                            });
                            //list right
                            let com = self.findRootComB(self.singleSelectedCode());
                            self.dataDisplay(self.convert(lstRoot));
                        });;
                    }
                    //TH: tab work place
                    else if(codeChanged == 1){
                        self.tabSelectedB(1);
                        self.getDataWorkplacePr().done(function(data){
                            //list left
                            _.each(self.lstWorkplace(), function(item){
                                lstRoot.push(new vmbase.DataCheckModeB(item.workplace.approvalId, item.workplace.startDate,
                                    item.workplace.endDate, item.workplace.applicationType, item.workplace.employmentRootAtr));
                            });
                            //list right
                             let com = self.findRootWpD(self.singleSelectedCode());
                            self.dataDisplay(self.convert(lstRoot));
                        });;
                    }
                    //TH: tab person
                    else{
                        self.tabSelectedB(2);
                        self.employeeId(id);
                        let param: vmbase.ParamDto = new vmbase.ParamDto(2,'',id);
                        self.getDataPersonPr(param);
                    }
                    //list left
                    self.singleSelectedCode('共通ルート');
                    console.log(self.dataDisplay());
                    //list right
            }
            /**
             * display item right TH 1,2,appName
             */
            showItem(codeChanged: string){
                var self = this;
                self.historyStr('');
                let common: boolean = false;
                let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                if(codeChanged == '共通ルート'){//1
                    let a = new vmbase.CompanyAppRootADto(true, 0, codeChanged, '', '', b, b, b, b, b);
                    self.comRoot(a);
                }
                else if(codeChanged == nts.uk.resource.getText("CMM018_7")){//2
                    self.comRoot(null);
                }else{//appName
                   //find item current
                    let value = self.findAppbyName(codeChanged);
                    let a1 = new vmbase.CompanyAppRootADto(common, value.value, value.localizedName, '', '', b, b, b, b, b);
                    self.comRoot(a1); 
                }
            }
        }
    }
}