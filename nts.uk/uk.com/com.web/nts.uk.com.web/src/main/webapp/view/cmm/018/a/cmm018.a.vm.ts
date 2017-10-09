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
        export class ScreenModel{
            nameCompany: KnockoutObservable<string>= ko.observable('');
            modeCommon:KnockoutObservable<boolean> = ko.observable(true);
            listMode: KnockoutObservableArray<any>= ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
            selectedModeCode: KnockoutObservable<number> = ko.observable(0);
            columns: KnockoutObservableArray<any> = ko.observableArray([
                    { headerText: 'Id', key: 'id', hidden: true},
                    { headerText: nts.uk.resource.getText('CMM018_22'), key: 'dateRange' },
                    { headerText: '', key: 'overLap', width: '20px'}
                ]);
            listHistory: KnockoutObservableArray<vmbase.ListHistory> = ko.observableArray([]);
            currentCode : KnockoutObservable<number> = ko.observable(null);
            lstItems: KnockoutObservableArray<vmbase.ListApproval>;
            historyStr: KnockoutObservable<string>  = ko.observable('');
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
            dataI: KnockoutObservable<vmbase.IData> = ko.observable(null);
            checkAddHistory: KnockoutObservable<boolean> = ko.observable(false);
            idOld:  KnockoutObservable<number> = ko.observable(null);
            lstAppType: Array<Number>;
            itemOld: KnockoutObservable<any> = ko.observable(null);
            //_____button Edit History___
            enableDelete: KnockoutObservable<boolean> = ko.observable(true);
            //param transfer to dialog K
            approverInfor : KnockoutObservableArray<vmbase.ApproverDtoK> = ko.observableArray([]);
            confirmedPerson : KnockoutObservable<string> = ko.observable("");
            selectTypeSet : KnockoutObservable<number> = ko.observable(0);
            //___________KCP009______________
            employeeInputList: KnockoutObservableArray<vmbase.EmployeeKcp009> = ko.observableArray([]);
            systemReference: KnockoutObservable<number> = ko.observable(vmbase.SystemType.EMPLOYMENT);
            isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(true);
            targetBtnText: string = nts.uk.resource.getText("KCP009_3");
            listComponentOption: vmbase.ComponentOption;
            selectedItem: KnockoutObservable<string> = ko.observable(null);
            tabindex: number = 1;
            //__________Sidebar________
            tabSelected: KnockoutObservable<number> = ko.observable(null);
            lstCompany: KnockoutObservableArray<vmbase.DataDisplayComDto> = ko.observableArray([]);
            lstWorkplace: KnockoutObservableArray<vmbase.DataDisplayWpDto> = ko.observableArray([]);
            lstPerson: KnockoutObservableArray<vmbase.DataDisplayPsDto> = ko.observableArray([]);
            //_______Check che do hien thi____
            visibleTab: KnockoutObservable<boolean> = ko.observable(true);
            //_______CCG001____
            ccgcomponent: vmbase.GroupOption;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<vmbase.EmployeeSearchDto>;
            workplaceId: KnockoutObservable<string> = ko.observable("");
            employeeId: KnockoutObservable<string> = ko.observable("");
            constructor(transferData: any) {
                let self = this;
                //call method start page
                self.startPage(transferData);
                //---subscribe currentCode (list left)---
                self.currentCode.subscribe(function(codeChanged) {
                    if(codeChanged == -1){
                        return;
                    }
                    self.enableDelete(true);
                    let history = self.findHistory(codeChanged);
                    self.historyStr(history.dateRange);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    //TH: tab company
                    if(self.tabSelected() == vmbase.RootType.COMPANY){
                        //check add history new
                        if(self.checkAddHistory()){
                            self.getDataCompany();
                        }
                        self.convertHistForCom(self.lstCompany());
                        let lstCompany = self.findAppIdForCom(codeChanged);
                        if(lstCompany != undefined){
                            _.each(lstCompany.lstCompanyRoot, function(item){
                                lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                                item.company.applicationType, item.company.employmentRootAtr, item.company.branchId,item.lstAppPhase));
                            });
                        }
                        
                    }
                    //TH: tab work place
                    else if(self.tabSelected() == vmbase.RootType.WORKPLACE){
                        //check add history new
                        if(self.checkAddHistory()){
                            self.getDataWorkplace();
                        }
                        self.convertHistForWp(self.lstWorkplace());
                        let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(codeChanged);
                        _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                            item.workplace.applicationType, item.workplace.employmentRootAtr,item.workplace.branchId, item.lstAppPhase));
                        });
                    }
                    //TH: tab person: vmbase.RootType.PERSON
                    else{
                        //check add history new
                        if(self.checkAddHistory()){
                            self.getDataPerson();
                        }
                        self.convertHistForPs(self.lstPerson());
                        let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(codeChanged);
                        if(lstPerson != undefined){
                           _.each(lstPerson.lstPersonRoot, function(item){
                                lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                                item.person.applicationType, item.person.employmentRootAtr,item.person.branchId, item.lstAppPhase));
                            }); 
                        }
                    }
                    self.cpA(self.convertlistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                });
                //---subscribe tab selected---
                self.tabSelected.subscribe(function(codeChanged) {
                    self.currentCode();
                    self.historyStr('');
                    self.enableDelete(true);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    //TH: tab company
                    if(codeChanged == 0){
                        self.getDataCompany();
                    }
                    //TH: tab work place
                    else if(codeChanged == 1){
                        self.getDataWorkplace();
                    }
                    //TH: tab person
                    else{
                        self.getDataPerson();
                    }
                });
                self.lstNameAppType = ko.observableArray([]);
                //___subscribe selected mode code______
                self.selectedModeCode.subscribe(function(codeChanged) {
                    self.enableDelete(true);
                    if(codeChanged==1){//private
                        self.checkAAA(0);
                        //TH: company
                        if(self.tabSelected() == vmbase.RootType.COMPANY){
                             __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.COMPANY,'');
                        }
                        //TH: work place
                        else if(self.tabSelected() == vmbase.RootType.WORKPLACE){
                             __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.WORKPLACE,'');
                        }
                        //TH: person
                        else{
                             __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.PERSON,self.selectedItem());
                        }
                    }else{//common
                        self.checkAAA(1);
                        //TH: company
                        if(self.tabSelected() == vmbase.RootType.COMPANY){
                            self.getDataCompany();
                        }
                        //TH: work place
                        else if(self.tabSelected() == vmbase.RootType.WORKPLACE){
                            self.getDataWorkplace();
                        }
                        //TH: person
                        else{
                            self.getDataPerson();
                        }
                    }
                });
                //_______KCP009_______
                // Initial listComponentOption
                self.listComponentOption = {
                    systemReference: self.systemReference(),
                    isDisplayOrganizationName: self.isDisplayOrganizationName(),
                    employeeInputList: self.employeeInputList,
                    targetBtnText: self.targetBtnText,
                    selectedItem: self.selectedItem,
                    tabIndex: self.tabindex
                };
//                $('#emp-component').ntsLoadListComponent(self.listComponentOption);
                //____subscribe selected item (return employee id)____
                self.selectedItem.subscribe(function(codeChanged){
                    //TH: mode A: まとめて登録モード
                    if(self.selectedModeCode()==0){
                        self.getDataPerson();
                    }
                    //TH: mode B: 申請個別登録モード
                    else{
                        __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.PERSON,codeChanged); 
                    }
                });
                //____________sidebar tab_____________
                 $("#sidebar").ntsSideBar("init", {
                    active: self.tabSelected(),
                    activate: (event, info) => {
                        switch(info.newIndex) {
                            case 0:
                                if(self.selectedModeCode()==0){
                                    self.tabSelected(vmbase.RootType.COMPANY);
                                }else{
                                    __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.COMPANY,'');
                                }
                                break;
                            case 1://workplace
                                if(self.selectedModeCode()==0){
                                    self.tabSelected(vmbase.RootType.WORKPLACE);
                                }else{
                                    __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.WORKPLACE,'');
                                }
                                break;
                            default://employee
                                if(self.selectedModeCode()==0){
                                    self.tabSelected(vmbase.RootType.PERSON);
                                }else{
                                    __viewContext.viewModel.viewmodelB.checkTabSelectedB(vmbase.RootType.PERSON,self.selectedItem());
                                }
                         }
                    }
                });
                //_____CCG001________
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());
                self.ccgcomponent = {
                   baseDate: self.baseDate,
                   //Show/hide options
                   isQuickSearchTab: true,
                   isAdvancedSearchTab: true,
                   isAllReferableEmployee: true,
                   isOnlyMe: true,
                   isEmployeeOfWorkplace: true,
                   isEmployeeWorkplaceFollow: true,
                   isMutipleCheck: true,
                   isSelectAllEmployee: true,
                   /**  
                   * @param dataList: list employee returned from component.
                   * Define how to use this list employee by yourself in the function's body.
                   */
                   onSearchAllClicked: function(dataList: vmbase.EmployeeSearchDto[]) {
                       self.showinfoSelectedEmployee(true);
                       self.selectedEmployee(dataList);
                        __viewContext.viewModel.viewmodelA.convertEmployeeCcg01ToKcp009(dataList);
                       
                   },
                   onSearchOnlyClicked: function(data: vmbase.EmployeeSearchDto) {
                       self.showinfoSelectedEmployee(true);
                       let dataEmployee: vmbase.EmployeeSearchDto[] = [];
                       dataEmployee.push(data);
                       self.selectedEmployee(dataEmployee);
                       let dataList: vmbase.EmployeeSearchDto[];
                       dataList.push(data);
                       self.convertEmployeeCcg01ToKcp009(dataList);
                   },
                   onSearchOfWorkplaceClicked: function(dataList: vmbase.EmployeeSearchDto[]) {
                       self.showinfoSelectedEmployee(true);
                       self.selectedEmployee(dataList);
                       self.convertEmployeeCcg01ToKcp009(dataList);
                   },
                   onSearchWorkplaceChildClicked: function(dataList: vmbase.EmployeeSearchDto[]) {
                       self.showinfoSelectedEmployee(true);
                       self.selectedEmployee(dataList);
                       self.convertEmployeeCcg01ToKcp009(dataList);
                   },
                   onApplyEmployee: function(dataEmployee: vmbase.EmployeeSearchDto[]) {
                       self.showinfoSelectedEmployee(true);
                       self.selectedEmployee(dataEmployee);
                       self.convertEmployeeCcg01ToKcp009(dataEmployee);
                   }
                } 
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
                let dataList: vmbase.EmployeeSearchDto[] = self.ccgcomponent.onSearchOnlyClicked;
                console.log(dataList);
            }
            convertEmployeeCcg01ToKcp009(dataList : vmbase.EmployeeSearchDto[]) : void{
                let self = this;    
                self.employeeInputList([]);
                _.each(dataList, function(item){
                        self.employeeInputList.push(new vmbase.EmployeeKcp009(item.employeeId, item.employeeCode,item.employeeName,item.workplaceName,""));
                    });
                $('#emp-component').ntsLoadListComponent(self.listComponentOption);
            }
            /**
             * open dialog CDL008
             * chose work place
             * @param baseDate, isMultiple, workplaceId
             * @return workplaceId
             */
            openDialogCDL008(){
                let self = this;
                setShared('inputCDL008', {baseDate: new Date(), isMultiple: false,canSelected: self.workplaceId()});
                modal("/view/cdl/008/a/index.xhtml").onClosed(function(){
                    self.workplaceId(getShared('outputCDL008').selectedCode);
                    console.log(self.workplaceId());
                    self.getDataWorkplace();
                });    
            }
            /**
             * startPage
             * get all data company
             * mode A: まとめて登録モード
             */
            startPage(transferData: any): JQueryPromise<any>{
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto;
                if(transferData.screen == 'Application'){//screen Application
                    self.visibleTab(false);
                    self.tabSelected(2);
                    self.employeeId(transferData.employeeId);
                    param = new vmbase.ParamDto(2,'',self.employeeId());
                    servicebase.getInfoEmployee(transferData.employeeId).done(function(employeeInfo){
                        self.employeeInputList.push(new vmbase.EmployeeKcp009(employeeInfo.sid,
                                    employeeInfo.employeeCode, employeeInfo.employeeName, '', ''));
                    });
                }else{//menu
                    self.visibleTab(true);
                    self.tabSelected(0);
                    param = new vmbase.ParamDto(0,'','');
                    servicebase.getInfoEmLogin().done(function(employeeInfo){
                        self.employeeInputList.push(new vmbase.EmployeeKcp009(employeeInfo.sid,
                                    employeeInfo.employeeCode, employeeInfo.employeeName, '', ''));
                    });
                }
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined){
                        self.lstCompany();
                        self.nameCompany('');
                        return;
                    } 
                    console.log(data);
                    if(transferData.screen == 'Application'){//screen Application
                        //list person
                        self.lstPerson(data.lstPerson);
                        self.convertHistForPs(data.lstPerson);
                        $('#emp-component').ntsLoadListComponent(self.listComponentOption);
                    }else{
                        if(data.lstCompany.length == 0){
                            self.lstCompany([]);
                            self.cpA([]);
                            self.listHistory([]);
                        }else{
                            //list company
                        self.lstCompany(data.lstCompany);
                        self.convertHistForCom(data.lstCompany);
                        }
                        let name = data.lstCompany.length > 1 ? data.lstCompany[0].companyName : '';
                        self.nameCompany(name);  
                    }
                    
                    //get name application tyle
                    servicebase.getNameAppType().done(function(lstName: Array<vmbase.ApplicationType>){
                        _.each(lstName, function(item){
                             self.lstNameAppType.push(new vmbase.ApplicationType(item.value, item.localizedName));
                        });
                        if(self.listHistory().length > 0){
                            self.currentCode(self.listHistory()[0].id);
                        }
                    });
                    dfd.resolve();
                })
                return dfd.promise();
            }
            /**
             * get data company 
             * mode A: まとめて登録モード
             */
            getDataCompany(): JQueryPromise<any>{
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined || data.lstCompany.length == 0){
                        self.historyStr('');
                        self.listHistory([]);
                        self.cpA([]);
                        dfd.resolve();
                        return dfd.promise();
                    } 
                    self.checkAddHistory(false);
                    self.lstCompany(data.lstCompany);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    self.convertHistForCom(self.lstCompany());
                    if(self.listHistory().length > 0){
                        let history = self.findHistory(self.currentCode());
                        if(history !== undefined){
                            self.historyStr(history.dateRange);
                        }
                    }
                    let lstCompany = self.findAppIdForCom(self.currentCode());
                    if(lstCompany != null || lstCompany !== undefined){
                        _.each(lstCompany.lstCompanyRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                            item.company.applicationType, item.company.employmentRootAtr,item.company.branchId, item.lstAppPhase));
                        }); 
                    }
                    self.cpA(self.convertlistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                    if(self.dataI() != null){
                        self.currentCode(self.listHistory()[0].id);
                    }
                    self.dataI(null);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data work place 
             * mode A: まとめて登録モード
             */
            getDataWorkplace(): JQueryPromise<any>{
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(1,self.workplaceId(),'');
                self.lstWorkplace([]);
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined || data.lstWorkplace.length == 0){
                        self.historyStr('');
                        self.listHistory([]);
                        self.cpA([]);
                        dfd.resolve();
                        return dfd.promise();
                    } 
                    self.checkAddHistory(false);
                    self.lstWorkplace(data.lstWorkplace);
                    if(data.lstWorkplace.length > 0){
                       self.workplaceId(data.lstWorkplace[0].lstWorkplaceRoot[0].workplace.workplaceId); 
                    }
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                    self.convertHistForWp(self.lstWorkplace());
                    if(self.listHistory().length > 0){
                        self.currentCode(self.listHistory()[0].id);
                        let history = self.findHistory(self.currentCode());
                        if(history !== undefined){
                            self.historyStr(history.dateRange);
                        }
                    }
                   let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(self.currentCode());
                    if(lstWorkplace != undefined){
                       _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                            lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                            item.workplace.applicationType, item.workplace.employmentRootAtr,item.workplace.branchId, item.lstAppPhase));
                        }); 
                    }
                    self.cpA(self.convertlistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                    if(self.dataI() != null){
                        self.currentCode(self.listHistory()[0].id);
                    }
                    self.dataI(null);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data person 
             * mode A: まとめて登録モード
             */
            getDataPerson(): JQueryPromise<any>{
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(vmbase.RootType.PERSON,'',self.selectedItem());
                servicebase.getAllDataCom(param).done(function(data: vmbase.DataFullDto) {   
                    if(data == null || data === undefined || data.lstPerson.length == 0){
                        self.historyStr('');
                        self.listHistory([]);
                        self.cpA([]);
                        dfd.resolve();
                        return dfd.promise();
                    } 
                    self.checkAddHistory(false);
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
                                        item.person.applicationType, item.person.employmentRootAtr,item.person.branchId, item.lstAppPhase));
                        });
                    }
                    self.cpA(self.convertlistRoot(lstRoot));
                    self.cpA.valueHasMutated();
                    if(self.dataI() != null){
                        self.currentCode(self.listHistory()[0].id);
                    }
                    self.dataI(null);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * Open dialog 履歴追加 : Add History
             * mode A: まとめて登録モード
             */
            openDialogI(){
                let self = this;
                let checkReload = false;
                let itemCurrent = null;
                 let paramI: vmbase.IData_Param = null;
                self.checkAddHistory(false);
                if(self.listHistory() == null || self.listHistory().length == 0 ){
                    let lstAppType = [null];
                    _.each(self.lstNameAppType(), function(appType){
                        lstAppType.push(appType.value);
                    });
                    paramI = {
                                name: "",
                                startDate: '',
                                check: self.tabSelected(),
                                mode: 0,
                                lstAppType: lstAppType
                                }
                }else{
                    self.enableDelete(false);
                    //item is selected
                    itemCurrent = self.findHistory(self.currentCode());
                       //最新の期間履歴を選択するかチェックする(check có đang chọn period history mới nhất hay không)
                    if(itemCurrent.endDate != '9999/12/31'){
                        //エラーメッセージ(Msg_181)(error message (Msg_181))
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_181" }).then(function(res){
    //                            block.clear();
                        });
                        return;
                    } 
                    let appType = null;
                    let startDate = ''
                    let lstAppType: Array<Number> = []; 
                    
                    //TH: tab company
                    if(self.tabSelected() == vmbase.RootType.COMPANY){
                        //Check dang chon item vua moi them
                        if(self.currentCode() == -1){
                            checkReload = true;
                            appType = self.itemOld().lstCompanyRoot[0].company.applicationType;
                            lstAppType = self.lstAppType;
                        }else{
                            self.itemOld(self.findComRoot(self.currentCode()));
                            appType = self.itemOld().lstCompanyRoot[0].company.applicationType; 
                            lstAppType = self.findAppTypeHistory(self.tabSelected());
                        }
                        let histLAst = self.findHistByEdateCom('9999/12/31', appType);
                        startDate = histLAst.lstCompanyRoot[0].company.startDate
                    }
                    //TH: tab work place
                    else if(self.tabSelected() == vmbase.RootType.WORKPLACE){
                        //Check dang chon item vua moi them
                        if(self.currentCode() == -1){
                            checkReload = true;
                            appType = self.itemOld().lstWorkplaceRoot[0].workplace.applicationType;
                            lstAppType = self.lstAppType;
                        }else{
                            self.itemOld(self.findWpRoot(self.currentCode()));
                            appType = self.itemOld().lstWorkplaceRoot[0].workplace.applicationType;
                            lstAppType = self.findAppTypeHistory(self.tabSelected());
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
                            lstAppType = self.lstAppType;
                        }else{
                            self.itemOld(self.findPsRoot(self.currentCode()));
                            appType = self.itemOld().lstPersonRoot[0].person.applicationType;
                            lstAppType = self.findAppTypeHistory(self.tabSelected());
                        }
                        let histLAst = self.findHistByEdatePs('9999/12/31', appType);
                        startDate = histLAst.lstPersonRoot[0].person.startDate
                    }
                     paramI = {name: "",
                                startDate: startDate,
                                check: self.tabSelected(),
                                mode: 0,
                                lstAppType: lstAppType
                    } 
                }
                setShared('CMM018I_PARAM', paramI);
                modal("/view/cmm/018/i/index.xhtml").onClosed(function(){
                    //Xu ly sau khi dong dialog I
                    let data: vmbase.IData = getShared('CMM018I_DATA');
                    if(data == null){
                        return;
                    }
                    self.lstAppType = data.lstAppType;
                    self.checkAddHistory(true);
                    let add: vmbase.ListHistory = new vmbase.ListHistory(-1, 
                        data.startDate + ' ~ ' + '9999/12/31', data.startDate,'9999/12/31','');
                    self.historyStr(add.dateRange);
                    let lstRoot: Array<vmbase.DataRootCheck> = [];
                     //display history new in screen main
                    let tmp: Array<vmbase.ListHistory> = [];
                    self.dataI(data);
                    if(self.listHistory() == null || self.listHistory().length == 0 ){//tao moi hoan toan
                        //TH: list left
                        //TH: list right
                        self.cpA(self.createNew(data.lstAppType));
                    }else{
                        //TH: list left
                        if(checkReload){
                            _.remove( self.listHistory(), function(n) {
                              return n.id == -1;
                            });
                            itemCurrent = self.findHistory(self.idOld());
                        }
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
                        //TH: list right
                        //初めから作成するを選択した場合(tạo mới từ đầu)
                        if(!data.copyDataFlag){
                            let tmp: Array<vmbase.DataRootCheck> = [];
                            let check = self.createNew(data.lstAppType);
                            self.cpA(check);
                        }
                        //履歴から引き継ぐから作成する場合(tao moi theo lich su cu)
                        else{
                            //重なるフラグがfalse
                            //重なるフラグがtrue
                            //TH: tab company
                            if(self.tabSelected() == vmbase.RootType.COMPANY){
                                //check add history new
                                if(self.checkAddHistory()){
//                                    self.getDataCompany();
                                }
                                let lstCompany;
                                if(self.currentCode() == -1){
                                    lstCompany = self.findAppIdForCom(self.idOld());
                                }else{
                                    lstCompany = self.findAppIdForCom(self.currentCode());    
                                }
                                
                               _.each(lstCompany.lstCompanyRoot, function(item){
                                    lstRoot.push(new vmbase.DataRootCheck(item.company.approvalId, item.company.historyId,
                                                    item.company.applicationType, item.company.employmentRootAtr,item.company.branchId, item.lstAppPhase));
                                }); 
                            }
                            //TH: tab work place
                            else if(self.tabSelected() == vmbase.RootType.WORKPLACE){
//                                let lstWorkplace: vmbase.DataDisplayWpDto = self.findAppIdForWp(self.idOld());
                                let lstWorkplace;
                                if(self.currentCode() == -1){
                                    lstWorkplace = self.findAppIdForWp(self.idOld());
                                }else{
                                    lstWorkplace = self.findAppIdForWp(self.currentCode());    
                                }
                                _.each(lstWorkplace.lstWorkplaceRoot, function(item){
                                    lstRoot.push(new vmbase.DataRootCheck(item.workplace.approvalId, item.workplace.historyId,
                                                    item.workplace.applicationType, item.workplace.employmentRootAtr, item.workplace.branchId, item.lstAppPhase));
                                });
                            }
                            //TH: tab person
                            else{
                                let lstPerson: vmbase.DataDisplayPsDto = self.findAppIdForPs(self.idOld());
                                if(lstPerson != undefined){
                                   _.each(lstPerson.lstPersonRoot, function(item){
                                        lstRoot.push(new vmbase.DataRootCheck(item.person.approvalId, item.person.historyId,
                                                        item.person.applicationType, item.person.employmentRootAtr,item.person.branchId, item.lstAppPhase));
                                    }); 
                                }
                            }
                                self.cpA(self.convertlistRoot(lstRoot));
                        }
                        tmp.push(old);
                    }
                    tmp.push(add);
                    let lstHistNew: Array<vmbase.ListHistory> = _.orderBy(tmp, ["dateRange"], ["desc"]);
                    self.listHistory(lstHistNew);
                    //luu id
                    if(self.currentCode() != -1){
                        self.idOld(self.currentCode());
                    }
                    self.listHistory.valueHasMutated()
                    self.cpA.valueHasMutated();
                    self.currentCode(-1);
                });
            }
            /**
             * find all app type of history is selected in db
             */
            findAppTypeHistory(rootType: number): Array<number>{
                let self = this;
                let lstApp = [];
                if(rootType == vmbase.RootType.COMPANY){
                    let obj: vmbase.DataDisplayComDto = self.findAppIdForCom(self.currentCode());
                    if(obj != undefined){
                        _.each(obj.lstCompanyRoot, function(item){
                            lstApp.push(item.company.applicationType);
                        });
                    }
                }else if(rootType == vmbase.RootType.WORKPLACE){
                    let obj: vmbase.DataDisplayWpDto = self.findAppIdForWp(self.currentCode());
                    if(obj != undefined){
                        _.each(obj.lstWorkplaceRoot, function(item){
                            lstApp.push(item.workplace.applicationType);
                        });
                    }
                }else{
                    let obj: vmbase.DataDisplayPsDto = self.findAppIdForPs(self.currentCode());
                    if(obj != undefined){
                        _.each(obj.lstPersonRoot, function(item){
                            lstApp.push(item.person.applicationType);
                        });
                    }
                }
                return lstApp;
            }
            findListUpdate(): Array<vmbase.UpdateHistoryDto>{
                let self = this;
                let lstApp: Array<vmbase.UpdateHistoryDto> = [];
                _.each(self.cpA(),function(item){
                    lstApp.push(new vmbase.UpdateHistoryDto(item.approvalId,item.historyId));
                });
                return lstApp;
            }
            /**
             * open dialog J
             * mode A: まとめて登録モード
             */
            openDialogJ(){
                let self = this;
                let history: vmbase.ListHistory = self.findHistory(self.currentCode());
                //編集する期間が最新なのかチェックする(Check xem có phải ls mới nhất k?)
                if(history.endDate != '9999/12/31'){
                    //エラーメッセージ(Msg_154) (Msg_154: Chỉ ls mới nhất mới được chỉnh sửa)
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_154" });
                    return;
                }
                //編集対象期間履歴が重なっているかチェックする(Ktr ls thay đổi có bị chồng chéo k?)
                if(history.overLap){
                    //エラーメッセージ(Msg_319)(error message (Msg_319))
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_319" });
                    return;
                }
                //list approvalId + historyId
                let lst: Array<vmbase.UpdateHistoryDto> = self.findListUpdate();
                let paramJ: vmbase.JData_Param = {
                    startDate: history.startDate,
                    endDate: history.endDate,
                    workplaceId: self.workplaceId(),
                    employeeId: self.selectedItem(),
                    check: self.tabSelected(),
                    mode: 0,//まとめて設定モード(0) - 申請個別設定モード(1)
                    overlapFlag: history.overLap,
                    lstUpdate: lst
                }
                setShared('CMM018J_PARAM', paramJ);
                modal("/view/cmm/018/j/index.xhtml").onClosed(function(){
                    //load data
                    if(self.tabSelected() == 0){//company
                        self.getDataCompany()
                    }else if(self.tabSelected() == 1){//work place
                        self.getDataWorkplace();
                    }else{//person
                        self.getDataPerson();
                    }    
                });
            }
            /**
             * open dialog K
             * mode A: まとめて登録モード
             */
            openDialogK(obj: vmbase.ApprovalPhaseDto, approvalId: string, appTypeValue: number, int: number){
                let self = __viewContext.viewModel.viewmodelA;
                console.log(obj);
                self.approverInfor([]);
                let approvalAtr = obj.approver[0] == null ? 0 : obj.approver[0].approvalAtr;
                if(approvalAtr == 0){//person
                    _.each(obj.approver, function(item){
                        self.approverInfor.push(item.employeeId);
                    });
                }else{//job title
                    _.each(obj.approver, function(item){
                        self.approverInfor.push(item.jobTitleId);
                    });
                }
                
                //確定者 code
                self.confirmedPerson(obj.approvalForm);
                setShared("CMM018K_PARAM", { 
                                        appTypeName: "0", //設定する対象申請名 
                                        formSetting: 2,//1: 全員確認、2：誰か一人
                                        approverInfor: self.approverInfor(),//承認者一覧
                                        confirmedPerson: self.confirmedPerson, //確定者
                                        selectTypeSet: approvalAtr, //職位指定（1）、個人指定（0）
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
                    let approvalAtr = data.selectTypeSet; 
                    let length = data.approverInfor.length
                    let lstAPhase = [data2.appPhase1,data2.appPhase2,data2.appPhase3,data2.appPhase4,data2.appPhase5]
                    let color: boolean = length > 0 ? true : self.checkColor(lstAPhase,int);
                    _.each(data.approverInfor, function(item, index){
                        let confirmedPerson = (data.formSetting == 2)&&(item.id == data.confirmedPerson) ? 1 : 0;
                        approver.push(new vmbase.ApproverDto('',approvalAtr == 1 ? item.id : null, approvalAtr == 0 ? item.id : null,item.name,index,approvalAtr,confirmedPerson));
                    });
                   let b: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto(approver,'','',length == 0 ? 0 : data.formSetting,length == 0 ? '' : data.approvalFormName, 0,int);
                switch(int){
                    case 1:
                         a = new vmbase.CompanyAppRootADto(color, data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,data2.branchId,
                                b,data2.appPhase2,data2.appPhase3,data2.appPhase4,data2.appPhase5);
                        break;
                    case 2: 
                         a = new vmbase.CompanyAppRootADto(color, data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,data2.branchId,
                                data2.appPhase1,b,data2.appPhase3,data2.appPhase4,data2.appPhase5);
                        break;
                    case 3: 
                         a = new vmbase.CompanyAppRootADto(color, data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,data2.branchId,
                                data2.appPhase1,data2.appPhase2,b,data2.appPhase4,data2.appPhase5);
                        break;
                    case 4: 
                          a = new vmbase.CompanyAppRootADto(color, data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,data2.branchId,
                                data2.appPhase1,data2.appPhase2,data2.appPhase3,b,data2.appPhase5); 
                        break;
                    default : 
                          a = new vmbase.CompanyAppRootADto(color, data2.common, appTypeValue, data2.appTypeName, approvalId, data2.historyId,data2.branchId,
                                data2.appPhase1,data2.appPhase2,data2.appPhase3,data2.appPhase4,b);
                        break;
                    } 
                    let dataOld: Array<vmbase.CompanyAppRootADto> = self.cpA();
                    dataOld.push(a);
                    let listHistoryNew = [];
                    let tmp = [];
                    _.each(dataOld, function(root){
                        if(root.appTypeValue == null && root.common == true){//common
                            listHistoryNew.push(root);
                        }else{
                            tmp.push(root);
                        }
                    });
                    let lstSort = _.orderBy(tmp, ["appTypeValue"], ["asc"]);
                    _.each(lstSort, function(obj){
                        listHistoryNew.push(obj);
                    });
                    self.cpA(listHistoryNew);
                    self.cpA.valueHasMutated();
                }); 
            }
            /**
             * delete row (root)
             * mode A: まとめて登録モード
             */
            deleteRow(index){
                let self = this;
                let objSelected: vmbase.CompanyAppRootADto = self.findApprovalA(index);
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
            register(rootType: number){
                let self = this;
                let checkAddHist = false;
                let root: Array<vmbase.CompanyAppRootADto> = [];
                if(self.dataI() != null){
                    checkAddHist = true;
                }
                if(self.selectedModeCode() == 0){//common
                    root = self.cpA();
                }else{//private
                __viewContext.viewModel.viewmodelB.registerB();
                    return;
                }
                let history = self.findHistory(self.currentCode());
                let listType = self.findAppTypeHistory(self.tabSelected());
                let data: vmbase.DataResigterDto = new vmbase.DataResigterDto(self.tabSelected(),
                                    checkAddHist,self.workplaceId(), self.selectedItem(),
                                    history.startDate, history.endDate,self.dataI(), listType, root);
                servicebase.updateRoot(data).done(function(){
                    self.enableDelete(true);
                    if(self.tabSelected() == vmbase.RootType.COMPANY){
                       self.getDataCompany();
                    }else if(self.tabSelected() == vmbase.RootType.WORKPLACE){
                       self.getDataWorkplace();
                    }else{
                       self.getDataPerson();
                    }
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }
            /**
             * convert to list history company
             * mode A: まとめて登録モード
             */
            convertHistForCom(lstCom: Array<vmbase.DataDisplayComDto>): void{
                let self = this;
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
                self.listHistory.valueHasMutated();
            }
            /**
             * convert to list history work place
             * mode A: まとめて登録モード
             */
            convertHistForWp(lstWp: Array<vmbase.DataDisplayWpDto>): void{
                let self = this;
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
                self.listHistory.valueHasMutated();
            }
            /**
             * convert to list history for person
             * mode A: まとめて登録モード
             */
            convertHistForPs(lstPs: Array<vmbase.DataDisplayPsDto>): void{
                let self = this;
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
                self.listHistory.valueHasMutated();
            }
            /**
             * check list app phase (TH: <5)
             * mode A: まとめて登録モード
             */
            checklist(list: Array<vmbase.ApprovalPhaseDto>): Array<vmbase.ApprovalPhaseDto>{
                let self = this;
                let listFix: Array<vmbase.ApprovalPhaseDto> = [];
                let itemBonus: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],null,null,0,null,null,null);
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
             * check list root (TH: <14)
             * mode A: まとめて登録モード
             */
            checklistRoot(root: Array<vmbase.DataRootCheck>): Array<vmbase.CompanyAppRootADto>{
                 let self = this;
                let lstbyApp: Array<vmbase.CompanyAppRootADto> = [];
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0, '',0,0);
                let color: boolean;
                //Them common type
                _.each(root, function(itemRoot){
                    color = itemRoot.lstAppPhase.length > 0 ? true : false;
                    self.listAppPhase(self.checklist(itemRoot.lstAppPhase));
                    //TH: co data
                    if(itemRoot.applicationType == null && itemRoot.employmentRootAtr ==0){
                        lstbyApp.push(new vmbase.CompanyAppRootADto(color, true, null,' 共通ルート', itemRoot.approvalId,
                                        itemRoot.historyId,itemRoot.branchId,
                                        self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                        self.listAppPhase()[3],self.listAppPhase()[4]));
                    }
                });
                //TH: khong co data
                if(lstbyApp.length < 1){
                    lstbyApp.push(new vmbase.CompanyAppRootADto(false, true, null,' 共通ルート', '', '', '', a, a, a, a, a));
                }
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType, index){
                    let check = false;
                    _.each(root, function(itemRoot){
                        color = itemRoot.lstAppPhase.length > 0 ? true : false;
                        if(item.value != 14 && item.value == itemRoot.applicationType){
                            lstbyApp.push(new vmbase.CompanyAppRootADto(color, false, item.value,item.localizedName, itemRoot.approvalId,
                                    itemRoot.historyId,itemRoot.branchId,
                                    self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                    self.listAppPhase()[3],self.listAppPhase()[4]));
                            check = true;
                        }
                    });
                    if(!check && item.value != 14){//chua co du lieu
                        lstbyApp.push(new vmbase.CompanyAppRootADto(false, false, item.value, item.localizedName,index,'','',a,a,a,a,a));
                    }
                })
                return lstbyApp;
            }
             /**
             * convert list root (TH: <14)
             * mode A: まとめて登録モード
             */
            convertlistRoot(root: Array<vmbase.DataRootCheck>): Array<vmbase.CompanyAppRootADto>{
                 let self = this;
                let lstbyApp: Array<vmbase.CompanyAppRootADto> = [];
                let tmp: Array<vmbase.CompanyAppRootADto> = [];
                let common = false;
                let appName = '';
                let color: boolean;
                _.each(root, function(itemRoot){
                    color = itemRoot.lstAppPhase.length > 0 ? true : false;
                    self.listAppPhase(self.checklist(itemRoot.lstAppPhase));
                    if(itemRoot.applicationType == null && itemRoot.employmentRootAtr ==0){
                        common = true;
                        appName = ' 共通ルート';
                        tmp.push(new vmbase.CompanyAppRootADto(color, common, itemRoot.applicationType,appName, itemRoot.approvalId,
                                        itemRoot.historyId,itemRoot.branchId,
                                        self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                        self.listAppPhase()[3],self.listAppPhase()[4]));
                    }else{
                        common = false;
                        let appType = __viewContext.viewModel.viewmodelB.findAppbyValue(itemRoot.applicationType);
                        appName = appType.localizedName;
                        lstbyApp.push(new vmbase.CompanyAppRootADto(color, common, itemRoot.applicationType,appName, itemRoot.approvalId,
                                        itemRoot.historyId,itemRoot.branchId,
                                        self.listAppPhase()[0], self.listAppPhase()[1],self.listAppPhase()[2],
                                        self.listAppPhase()[3],self.listAppPhase()[4]));
                    }
                });
                let sortBy =  _.orderBy(lstbyApp, ["appTypeValue"], ["asc"]);
                _.each(sortBy, function(obj){
                    tmp.push(obj);
                });
                return tmp;
            }
            /**
             * create root new after add history new
             */
            createNew(lstAppType: Array<Number>): Array<vmbase.CompanyAppRootADto>{
                let self = this;
                let lstbyApp: Array<vmbase.CompanyAppRootADto> = [];
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'','',0, '',0,0);
                _.each(lstAppType, function(appType, index){
                    if(appType == null){//common
                        lstbyApp.push(new vmbase.CompanyAppRootADto(false, true, null,' 共通ルート', index.toString(), '','', a, a, a, a, a)); 
                    }else{
                        let objType: vmbase.ApplicationType = __viewContext.viewModel.viewmodelB.findAppbyValue(appType);
                        lstbyApp.push(new vmbase.CompanyAppRootADto(false, false, objType.value, objType.localizedName, index.toString(), '','', a, a, a, a, a)); 
                    }
                });
                return lstbyApp
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
            /**
             * open dialog L: 未登録社員リスト
             */
            openDialogL(){
               modal("/view/cmm/018/l/index.xhtml");   
            }
            /**
             * open dialog M: マスタリスト
             */
            openDialogM(){
               modal("/view/cmm/018/m/index.xhtml");
            }
            /**
             * open dialog N: 承認者一覧
             */
            openDialogN(){
               modal("/view/cmm/018/n/index.xhtml");
            }
            checkColor(lstAppPhase: Array<vmbase.ApprovalPhaseDto>, int: number): boolean{
                _.each(lstAppPhase, function(appPhase, index){
                    if(index != int){
                        if(appPhase.approvalForm != 0) return true;
                    }
                });
                return false;
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
            approverInfor : KnockoutObservableArray<any> = ko.observableArray([]);
            confirmedPerson : KnockoutObservable<number> = ko.observable(null);
            selectTypeSet : KnockoutObservable<number> = ko.observable(0);
            //---display screen B
            cpA: KnockoutObservableArray<vmbase.CompanyAppRootADto> = ko.observableArray([]);
            historyStr: KnockoutObservable<string> = ko.observable('');
            nameCompany: KnockoutObservable<string> = ko.observable('');
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
            //_____________dialog I____
            dataIB: KnockoutObservable<vmbase.IData> = ko.observable(null);
            //_______CDL008_____
            workplaceIdB: KnockoutObservable<string> = ko.observable('');
            constructor(){
                let self = this;
                //----SCREEN B
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable('共通ルート');
                self.lstNameAppType = ko.observableArray([]);
                self.dataSourceB = ko.observable(null);
                self.dataDisplay = ko.observableArray([]);
                self.getDataCompanyPr();
                let a: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto([],'abc','123',0, '',0,0);
                let aaa = new vmbase.CompanyAppRootADto(false, true,0,'1','','','',a, a, a, a, a);
                    self.cpA.push(aaa);
                //_____subcribe singleSelectedCode________
                self.singleSelectedCode.subscribe(function(codeChanged) {//approvalId
                    if(codeChanged == '-1'){
                        return;
                    }
                    //TH: company
                    if(self.tabSelectedB()==0){
                        self.getDataCompanyPr();
                        let com = self.findRootComB(codeChanged);
                        //TH: item data
                        if(com != null && com !== undefined){
                            self.historyStr(com.company.startDate + ' ~ ' + com.company.endDate);
                            let name = self.findNameApp(com.company.applicationType, com.company.employmentRootAtr);
                            let checkCommon = false;
                            if(com.company.applicationType == null && com.company.employmentRootAtr ==0){//common
                                checkCommon = true;
                            }
                            let appPhase = __viewContext.viewModel.viewmodelA.checklist(com.lstAppPhase);
                            let color: boolean = com.lstAppPhase.length > 0 ? true : false;
                            let aaa = new vmbase.CompanyAppRootADto(color,checkCommon,com.company.applicationType,name,com.company.approvalId,com.company.historyId,com.company.branchId,appPhase[0],appPhase[1],appPhase[2],appPhase[3],appPhase[4]);
                            self.comRoot(aaa);
                        }
                        //TH: 1,2,appName
                        else{
                            
                            self.comRoot(null);
//                            self.showItem(codeChanged);
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
                            let appPhase = __viewContext.viewModel.viewmodelA.checklist(wp.lstAppPhase);
                            let color: boolean = wp.lstAppPhase.length > 0 ? true : false;
                            let aaa = new vmbase.CompanyAppRootADto(color,checkCommon,wp.workplace.applicationType,name,wp.workplace.approvalId,wp.workplace.historyId,wp.workplace.branchId,appPhase[0],appPhase[1],appPhase[2],appPhase[3],appPhase[4]);
                            self.comRoot(aaa);
                        }
                        //TH: 1,2,appName
                        else{
                            self.comRoot(null);
//                            self.showItem(codeChanged);
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
                            let appPhase = __viewContext.viewModel.viewmodelA.checklist(ps.lstAppPhase);
                            let color: boolean = ps.lstAppPhase.length > 0 ? true : false;
                            let aaa = new vmbase.CompanyAppRootADto(color,checkCommon,ps.person.applicationType,name,ps.person.approvalId,ps.person.historyId,ps.person.branchId,appPhase[0],appPhase[1],appPhase[2],appPhase[3],appPhase[4]);
                            self.comRoot(aaa);
                        }
                        //TH: 1,2,appName
                        else{
                            self.comRoot(null);
//                            self.showItem(codeChanged);
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
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(0,'','');
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    console.log(data);
                    self.dataSourceB(data);
                    self.lstCompany(data.lstCompanyRoot);
                     let lstRoot: Array<vmbase.DataCheckModeB> = [];
                    //list left
                    _.each(self.lstCompany(), function(item){
                        lstRoot.push(new vmbase.DataCheckModeB(item.company.approvalId, item.company.startDate,
                            item.company.endDate, item.company.applicationType, item.company.employmentRootAtr));
                    });
                    //list right
                            
                    self.dataDisplay(self.convert(lstRoot));
                   let a:any = null;
                   if(self.dataIB()==null){
                       a = self.findRootComB(self.singleSelectedCode());
                   }else{
                       a = self.findHistByType(self.dataIB().lstAppType[0], self.dataIB().startDate, self.tabSelectedB());
                   }
                   if(a != undefined){
                       self.singleSelectedCode(a.company.approvalId);
                   }
                    self.dataIB(null);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data work place mode 申請個別
             */
            getDataWorkplacePr(): JQueryPromise<any>{
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(1,self.workplaceIdB(),'');
                servicebase.getAllDataPr(param).done(function(data: vmbase.CommonApprovalRootDto) {    
                    if(data == null || data === undefined){
                        self.dataSourceB();
                        return;
                    }
                    self.dataSourceB(data);
                    self.lstWorkplace(data.lstWorkplaceRoot);
                    let lstRoot: Array<vmbase.DataCheckModeB> = [];
                    //list left
                    _.each(self.lstWorkplace(), function(item){
                        lstRoot.push(new vmbase.DataCheckModeB(item.workplace.approvalId, item.workplace.startDate,
                            item.workplace.endDate, item.workplace.applicationType, item.workplace.employmentRootAtr));
                    });
                    //list right
                     let com = self.findRootWpD(self.singleSelectedCode());
                    self.dataDisplay(self.convert(lstRoot));
                   let a:any = null;
                   if(self.dataIB()==null){
                       a = self.findRootWpD(self.singleSelectedCode());
                   }else{
                       a = self.findHistByType(self.dataIB().lstAppType[0], self.dataIB().startDate, self.tabSelectedB());
                   }
                    if(a != undefined){
                        self.singleSelectedCode(a.workplace.approvalId);
                    }
                    self.dataIB(null); 
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * get data person mode 申請個別
             */
            getDataPersonPr(): JQueryPromise<any>{
                let self = this;
                let dfd = $.Deferred();
                let param: vmbase.ParamDto = new vmbase.ParamDto(2,'',self.employeeId());
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
                    let a:any = null;
                    if(self.dataIB()==null){
                        a = self.findRootPsF(self.singleSelectedCode());
                    }else{
                        a = self.findHistByType(self.dataIB().lstAppType[0], self.dataIB().startDate, self.tabSelectedB());
                    }
                    if(a != null && a != undefined){
                        self.singleSelectedCode(a.person.approvalId);
                    }
                    self.dataIB(null);
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
            findRootComByType(value: number): vmbase.CompanyAppRootDto {
                let self = this;
                return _.find( self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
                    return obj.company.applicationType == value;
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
             * find history is selected
             * mode B: 申請個別登録モード
             */
            findHist(approvalId: string, rootType: number): any {
                let self = this;
                if(approvalId == '-1'){
                    return self.dataIB();
                }
                //TH: company
                if(rootType == 0){
                    return _.find( self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
                        return obj.company.approvalId == approvalId;
                    });
                }
                //TH: work place
                else if(rootType == 1){
                    return _.find( self.lstWorkplace(), function(obj) {
                        return obj.workplace.approvalId == approvalId;
                    });
                }
                //TH: person
                else{
                    return _.find( self.lstPerson(), function(obj: vmbase.PersonAppRootDto) {
                        return obj.person.approvalId == approvalId;
                    });
                }
                
            }
            findHistByType(appType: number,startDate: string, rootType: number): any {
                let self = this;
                //TH: company
                if(rootType == 0){
                    return _.find( self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
                        return obj.company.applicationType == appType && obj.company.startDate == startDate;
                    });
                }
                //TH: work place
                else if(rootType == 1){
                    return _.find( self.lstWorkplace(), function(obj) {
                        return obj.workplace.applicationType == appType && obj.workplace.startDate == startDate;
                    });
                }
                //TH: person
                else{
                    return _.find( self.lstPerson(), function(obj: vmbase.PersonAppRootDto) {
                        return obj.person.applicationType == appType && obj.person.startDate == startDate;
                    });
                }
                
            }
            findHistByEDate(appType: number,endDate: string, rootType: number): any {
                let self = this;
                //TH: company
                if(rootType == 0){
                    return _.find( self.lstCompany(), function(obj: vmbase.CompanyAppRootDto) {
                        return obj.company.applicationType == appType && obj.company.endDate == endDate;
                    });
                }
                //TH: work place
                else if(rootType == 1){
                    return _.find( self.lstWorkplace(), function(obj) {
                        return obj.workplace.applicationType == appType && obj.workplace.endDate == endDate;
                    });
                }
                //TH: person
                else{
                    return _.find( self.lstPerson(), function(obj: vmbase.PersonAppRootDto) {
                        return obj.person.applicationType == appType && obj.person.endDate == endDate;
                    });
                }
                
            }
            /**
             * convert data db to data display
             * mode B: 申請個別登録モード
             */
            convert(root: Array<vmbase.DataCheckModeB>): Array<vmbase.DataTreeB>{
                let self = this;
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
                aa.push(new vmbase.DataTreeB('共通ルート',' 共通ルート', _.orderBy(appCommon, ["nameAppType"], ["desc"])));  
                //lay theo don  
                _.each(self.lstNameAppType(), function(item: vmbase.ApplicationType){
                    let lstbyApp: Array<vmbase.Com> = [];
                    _.each(root, function(itemRoot){
                        if(item.value != 14 && item.value == itemRoot.applicationType){
                            lstbyApp.push(new vmbase.Com(itemRoot.approvalId, itemRoot.startDate + ' ~ ' + itemRoot.endDate));
                        }
                    });
                    if(item.value != 14){
                        bb.push(new vmbase.DataTree(item.localizedName, item.localizedName, _.orderBy(lstbyApp,["nameAppType"], ["desc"])));    
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
                let self = this;
                self.approverInfor([]);
                let approvalAtr = obj.approver[0] == null ? 0 : obj.approver[0].approvalAtr;
                if(approvalAtr == 0){//person
                    _.each(obj.approver, function(item){
                        self.approverInfor.push(item.employeeId);
                    });
                }else{//job title
                    _.each(obj.approver, function(item){
                        self.approverInfor.push(item.jobTitleId);
                    });
                }
                //確定者 code
                self.confirmedPerson(obj.approvalForm);
                setShared("CMM018K_PARAM", { 
                                        appType: "0", //設定する対象申請名 
                                        formSetting: 1,//1: 全員確認、2：誰か一人
                                        approverInfor: self.approverInfor(),//承認者一覧
                                        confirmedPerson: self.confirmedPerson, //確定者
                                        selectTypeSet: approvalAtr, //職位指定（1）、個人指定（0）
                                        tab: 0//０：会社、１：職場、２：個人
                                        });
                modal("/view/cmm/018/k/index.xhtml").onClosed(() => {
                    self.approverInfor([]);
                    let data: vmbase.KData = getShared('CMM018K_DATA');
                    if(data == null){
                        return;
                    }
                    let tmp: vmbase.CompanyAppRootADto = self.comRoot();
                    let a: vmbase.CompanyAppRootADto = null;
                    let approver: Array<vmbase.ApproverDto> = [];
                    let approvalAtr = data.selectTypeSet;
                    let length = data.approverInfor.length;
                    _.each(data.approverInfor, function(item){
                        let confirmedPerson = (data.formSetting == 2)&&(item.id == data.confirmedPerson) ? 1 : 0;
                        approver.push(new vmbase.ApproverDto('',approvalAtr == 1 ? item.id : null, approvalAtr == 0 ? item.id : null,item.name,1,approvalAtr,confirmedPerson));
                    });
                   let b: vmbase.ApprovalPhaseDto = new vmbase.ApprovalPhaseDto(approver,'','',length == 0 ? 0 : data.formSetting,length == 0 ? '' : data.approvalFormName,0,int);
                    let color: boolean = length > 0 ? true : tmp.color;
                    switch(int){
                    case 1:
                         a = new vmbase.CompanyAppRootADto(color,tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.branchId,b,tmp.appPhase2,tmp.appPhase3,tmp.appPhase4,tmp.appPhase5);
                        break;
                    case 2: 
                         a = new vmbase.CompanyAppRootADto(color,tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.branchId,tmp.appPhase1,b,tmp.appPhase3,tmp.appPhase4,tmp.appPhase5);
                        break;
                    case 3: 
                         a = new vmbase.CompanyAppRootADto(color,tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.branchId,tmp.appPhase1,tmp.appPhase2,b,tmp.appPhase4,tmp.appPhase5);
                        break;
                    case 4: 
                          a = new vmbase.CompanyAppRootADto(color,tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.branchId,tmp.appPhase1,tmp.appPhase2,tmp.appPhase3,b,tmp.appPhase5); 
                        break;
                    default : 
                          a = new vmbase.CompanyAppRootADto(color,tmp.common, appTypeValue, tmp.appTypeName, approvalId, tmp.historyId,
                                tmp.branchId,tmp.appPhase1,tmp.appPhase2,tmp.appPhase3,tmp.appPhase4,b);
                        break;
                    } 
                    self.comRoot(a);
                }); 
            }
            /**
             * register
             * mode B: 申請個別登録モード
             */
            registerB(){
//                nts.uk.ui.block.invisible();
                let self = this;
                if(self.comRoot() == null){//TH: data right null: xoa het or k co
                    let app = self.findAppbyName(self.singleSelectedCode());
                    //TH: select not item history
                    if(app != undefined ||self.singleSelectedCode() == '共通ルート' || self.singleSelectedCode() == nts.uk.resource.getText("CMM018_7")){//don
                        return null;
                    }
                }
                let checkAddHist = false;
                let root: Array<vmbase.CompanyAppRootADto> = [];
                if(self.dataIB() != null){
                    checkAddHist = true;
                }
                root.push(self.comRoot());
                let history = self.findHist(self.singleSelectedCode(),self.tabSelectedB());
                let listType = [];
                let startDate = ''
                let endDate = '';
                if(self.tabSelectedB() == 0){
                    if(self.singleSelectedCode() == -1){
                        endDate = '9999/12/31';
                        startDate = history.startDate;
                        listType.push(history.lstAppType[0]);
                    }else{
                        endDate = history.company.endDate;
                        startDate = history.company.startDate;
                        listType.push(history.company.applicationType);
                    }
                }else if(self.tabSelectedB() == 1){
                    if(self.singleSelectedCode() == -1){
                        endDate = '9999/12/31';
                        startDate = history.startDate;
                        listType.push(history.lstAppType[0]);
                    }else{
                        endDate = history.workplace.endDate;
                        startDate = history.workplace.startDate;
                        listType.push(history.workplace.applicationType);
                    }
                }else{
                    if(self.singleSelectedCode() == -1){
                        endDate = '9999/12/31';
                        startDate = history.startDate;
                        listType.push(history.lstAppType[0]);
                    }else{
                        endDate = history.person.endDate;
                        startDate = history.person.startDate;
                        listType.push(history.person.applicationType);
                    }
                }
                let data: vmbase.DataResigterDto = new vmbase.DataResigterDto(self.tabSelectedB(),
                                    checkAddHist, self.workplaceIdB(),
                                    self.employeeId(),startDate, endDate,self.dataIB(), listType, root);
                servicebase.updateRoot(data).done(function(){
//                    nts.uk.ui.block.clear();
                    let lstRoot: Array<vmbase.DataCheckModeB> = [];
                   if(self.tabSelectedB() == 0){//company
                       self.getDataCompanyPr();
                   }else if(self.tabSelectedB() == 1){
                       self.getDataWorkplacePr();
                   }else{
                       self.getDataPersonPr();
                   }
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(function(){
//                    nts.uk.ui.block.clear();    
                });
            }
            /**
             * open dialog I
             * mode B: 申請個別登録モード
             */
            openDialogI(){
                let self = this;
                let sDate = '';
                //履歴変更対象を選択しているチェックする
                let name = '';
                let typeApp = 0;
                let lstAppType = [];
                if(self.singleSelectedCode() == nts.uk.resource.getText("CMM018_7")){//2
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_181" });
                    return;
                }
                let itemCurrent = self.findHist(self.singleSelectedCode(), self.tabSelectedB());
                if(itemCurrent == undefined){//TH: chon name
                    let obj = self.findAppbyName(self.singleSelectedCode());
                    typeApp = obj == undefined ? null : obj.value;
                    let itemLast = self.findHistByEDate(typeApp, '9999/12/31', self.tabSelectedB());
                    if(itemLast != undefined){
                        if(self.tabSelectedB() == 0){
                            sDate = itemLast.company.startDate;
                        }else if(self.tabSelectedB() == 1){
                            sDate = itemLast.workplace.startDate;
                        }else{
                            sDate = itemLast.person.startDate;
                        }
                    }
//                    if(self.comRoot == null){//hien thi message 181
//                        nts.uk.ui.dialog.alertError({ messageId: "Msg_181" });
//                        return;
//                    }else{
//                    
//                        typeApp = obj == undefined ? null : obj.value;
//                    }
                }else{
                    if(self.tabSelectedB() == 0){
                        if(itemCurrent !== undefined){
                            let itemLast = self.findHistByEDate(itemCurrent.company.applicationType, '9999/12/31', self.tabSelectedB());
                            sDate = itemLast.company.startDate;
                            typeApp = itemCurrent.company.applicationType;
                            name = typeApp == null ? '共通ルート' : nts.uk.resource.getText("CMM018_7");
                        }
                    }
                    else if(self.tabSelectedB() == 1){
                        if(itemCurrent !== undefined){
                            let itemLast = self.findHistByEDate(itemCurrent.workplace.applicationType, '9999/12/31', self.tabSelectedB());
                            sDate = itemLast.workplace.startDate;
                            typeApp = itemCurrent.workplace.applicationType
                            name = typeApp == null ? '共通ルート' : nts.uk.resource.getText("CMM018_7");
                        }
                    }
                    else{
                        if(itemCurrent !== undefined){
                            let itemLast = self.findHistByEDate(itemCurrent.person.applicationType, '9999/12/31', self.tabSelectedB());
                            sDate = itemLast.person.startDate;
                            typeApp = itemCurrent.person.applicationType;
                            name = typeApp == null ? '共通ルート' : nts.uk.resource.getText("CMM018_7");
                        }
                    }
                }
                lstAppType.push(typeApp);
                let paramI: vmbase.IData_Param = {
                                name: name,
                                startDate: sDate,
                                check: self.tabSelectedB(),
                                mode: 1,
                                lstAppType: lstAppType
                                }
                setShared('CMM018I_PARAM', paramI);
                modal("/view/cmm/018/i/index.xhtml").onClosed(function(){
                    let data: vmbase.IData = getShared('CMM018I_DATA');
                    if(data == null){
                        return;
                    }
                    self.dataIB(data);
                    let lst = data.lstAppType;
                    let data2 = [];
                    let rangeDate = data.startDate + ' ~ ' + '9999/12/31';
                    self.historyStr(rangeDate);
                    let startDate = new Date(data.startDate);
                    startDate.setDate(startDate.getDate() - 1);
                    let month =   __viewContext.viewModel.viewmodelA.checkDate(startDate.getMonth() + 1);
                    let day =  __viewContext.viewModel.viewmodelA.checkDate(startDate.getDate());
                    let endDateNew = startDate.getFullYear() + '/' + month +  '/' + day;
                    
                    if(self.tabSelectedB() == 0){//company
                        let a = null;
                        _.each(self.lstCompany(), function(item){
                            if(item.company.approvalId != self.singleSelectedCode()){//bo them ls cua cai dang sua
                                data2.push(new vmbase.DataCheckModeB(item.company.approvalId, item.company.startDate, item.company.endDate, 
                                            item.company.applicationType, item.company.employmentRootAtr));
                            }else{
                               a = new vmbase.DataCheckModeB(item.company.approvalId, item.company.startDate, item.company.endDate, 
                                            item.company.applicationType, item.company.employmentRootAtr);
                            }
                        });
                        if(a == null){//loai don chua co lich su : them moi tu dau
                            let appType = data.lstAppType[0];
                            let app = self.findAppbyValue(appType);
                            let add = new vmbase.DataCheckModeB('-1',data.startDate,'9999/12/31',appType,appType == null ? 0 : 1);
                            data2.push(add);
                            self.dataDisplay(self.convert(data2));
                            let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                            self.comRoot(new vmbase.CompanyAppRootADto(false, appType == null ? true : false, 
                                    appType, appType == null ? '共通ルート' : app.localizedName, '-1', '',
                                    '',b,b,b,b,b));
                        }else{
                            let add = new vmbase.DataCheckModeB('-1',data.startDate,'9999/12/31',a.applicationType,a.employmentRootAtr);
                            let old = new vmbase.DataCheckModeB(a.approvalId, a.startDate, endDateNew, 
                                                a.applicationType, a.employmentRootAtr);
                            data2.push(add);
                            data2.push(old);
                            self.dataDisplay(self.convert(data2));
                            self.dataDisplay.valueHasMutated();
                            if(!data.copyDataFlag){//create new
                                let appTypeValue = data.lstAppType[0];
                                let app = self.findAppbyValue(appTypeValue);
                                let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                                self.comRoot(new vmbase.CompanyAppRootADto(false, appTypeValue == null ? true : false, 
                                    appTypeValue, app == undefined ? '共通ルート' : app.localizedName, '-1', '',
                                    '',b,b,b,b,b));
                            }
                        }
                        
                        self.singleSelectedCode('-1');
                    }else if(self.tabSelectedB() == 1){//workplace
                        let a = null;
                        _.each(self.lstWorkplace(), function(item){
                            if(item.workplace.approvalId != self.singleSelectedCode()){//bo them ls cua cai dang sua
                                data2.push(new vmbase.DataCheckModeB(item.workplace.approvalId, item.workplace.startDate, item.workplace.endDate, 
                                            item.workplace.applicationType, item.workplace.employmentRootAtr));
                            }else{
                               a = new vmbase.DataCheckModeB(item.workplace.approvalId, item.workplace.startDate, item.workplace.endDate, 
                                            item.workplace.applicationType, item.workplace.employmentRootAtr);
                            }
                        });
                        if(a == null){//loai don chua co lich su : them moi tu dau
                            let appType = data.lstAppType[0];
                            let app = self.findAppbyValue(appType);
                            let add = new vmbase.DataCheckModeB('-1',data.startDate,'9999/12/31',appType,appType == null ? 0 : 1);
                            data2.push(add);
                            self.dataDisplay(self.convert(data2));
                            let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                            self.comRoot(new vmbase.CompanyAppRootADto(false, appType == null ? true : false, 
                                    appType, appType == null ? '共通ルート' : app.localizedName, '-1', '',
                                    '',b,b,b,b,b));
                        }else{
                            let add = new vmbase.DataCheckModeB('-1',data.startDate,'9999/12/31',a.applicationType,a.employmentRootAtr);
                            let old = new vmbase.DataCheckModeB(a.approvalId, a.startDate, endDateNew, 
                                                a.applicationType, a.employmentRootAtr);
                            data2.push(add);
                            data2.push(old);
                            self.dataDisplay(self.convert(data2));
                            self.dataDisplay.valueHasMutated();
                            if(!data.copyDataFlag){//create new
                                let appTypeValue = data.lstAppType[0];
                                let app = self.findAppbyValue(appTypeValue);
                                let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                                self.comRoot(new vmbase.CompanyAppRootADto(false, appTypeValue == null ? true : false, 
                                    appTypeValue, app == undefined ? '共通ルート' : app.localizedName, '-1', '',
                                    '',b,b,b,b,b));
                        }
                        }
                        self.singleSelectedCode('-1');
                    }else{//person
                        let a = null;
                        _.each(self.lstPerson(), function(item){
                            if(item.person.approvalId != self.singleSelectedCode()){//bo them ls cua cai dang sua
                                data2.push(new vmbase.DataCheckModeB(item.person.approvalId, item.person.startDate, item.person.endDate, 
                                            item.person.applicationType, item.person.employmentRootAtr));
                            }else{
                               a = new vmbase.DataCheckModeB(item.person.approvalId, item.person.startDate, item.person.endDate, 
                                            item.person.applicationType, item.person.employmentRootAtr);
                            }
                        });
                        if(a == null){//loai don chua co lich su : them moi tu dau
                            let appType = data.lstAppType[0];
                            let app = self.findAppbyValue(appType);
                            let add = new vmbase.DataCheckModeB('-1',data.startDate,'9999/12/31',appType,appType == null ? 0 : 1);
                            data2.push(add);
                            self.dataDisplay(self.convert(data2));
                            let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                            self.comRoot(new vmbase.CompanyAppRootADto(false, appType == null ? true : false, 
                                    appType, appType == null ? '共通ルート' : app.localizedName, '-1', '',
                                    '',b,b,b,b,b));
                        }else{
                            let add = new vmbase.DataCheckModeB('-1',data.startDate,'9999/12/31',a.applicationType,a.employmentRootAtr);
                            let old = new vmbase.DataCheckModeB(a.approvalId, a.startDate, endDateNew, 
                                                a.applicationType, a.employmentRootAtr);
                            data2.push(add);
                            data2.push(old);
                            self.dataDisplay(self.convert(data2));
                            self.dataDisplay.valueHasMutated();
                            if(!data.copyDataFlag){//create new
                                let appTypeValue = data.lstAppType[0];
                                let app = self.findAppbyValue(appTypeValue);
                                let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                                self.comRoot(new vmbase.CompanyAppRootADto(false,appTypeValue == null ? true : false, 
                                    appTypeValue, app == undefined ? '共通ルート' : app.localizedName, '-1', '',
                                    '',b,b,b,b,b));
                            }
                        }
                        self.singleSelectedCode('-1');
                    }
                    
                });
            }
            /**
             * open dialog J
             * mode B: 申請個別登録モード
             */
            openDialogJ(){
                let self = this;
                let history;
                let startDate = '';
                let endDate = '';
                let name = '';
                let historyId = '';
                let approvalId = '';
                if(self.tabSelectedB() == 0){//company
                    history = self.findRootComB(self.singleSelectedCode());
                    if(history != undefined){
                        historyId = history.company.historyId;
                        approvalId = history.company.approvalId;
                        startDate = history.company.startDate;
                        endDate = history.company.endDate;
                        name = history.company.applicationType == null ? '共通ルート' : nts.uk.resource.getText("CMM018_7");
                    }
                }else if(self.tabSelectedB() == 1){
                    history = self.findRootWpD(self.singleSelectedCode());
                    if(history != undefined){
                        historyId = history.workplace.historyId;
                        approvalId = history.workplace.approvalId;
                        startDate = history.workplace.startDate;
                        endDate = history.workplace.endDate;
                        name = history.workplace.applicationType == null ? '共通ルート' : nts.uk.resource.getText("CMM018_7");
                    }
                }else{
                    history = self.findRootPsF(self.singleSelectedCode());
                    if(history != undefined){
                        historyId = history.person.historyId;
                        approvalId = history.person.approvalId;
                        startDate = history.person.startDate;
                        endDate = history.person.endDate;
                        name = history.person.applicationType == null ? '共通ルート' : nts.uk.resource.getText("CMM018_7");
                    }
                }
                
                //履歴変更対象を選択しているチェックする(check có chọn đối tượng sửa lịch sử hay không ?)
                //対象未選択、申請ごとのルートを選択している場合(chưa chọn đối tượng, hoặc đang chọn 申請ごとのルート)
                if(history == undefined){
                    //エラーメッセージ(Msg_181)(error message (Msg_181))
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_181" });
                    return;
                }
                //編集する期間が最新なのかチェックする(check lịch sử đang sửa có phải lịch sử mới nhất hay không)
                //編集する履歴が最新履歴じゃない(lịch sử đang sửa không phải là lịch sử mới nhất)
                if(endDate != '9999/12/31'){
                    //エラーメッセージ(Msg_154)(error message (Msg_154))
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_154" });
                    return;
                }
                let lst: Array<vmbase.UpdateHistoryDto> = [];
                lst.push(new vmbase.UpdateHistoryDto(approvalId, historyId));
                let paramJ: vmbase.JData_Param = {
                    name: name,
                    startDate: startDate,
                    endDate: endDate,
                    workplaceId: self.workplaceIdB(),
                    employeeId: self.employeeId(),
                    check: self.tabSelectedB(),
                    mode: 1,
                    lstUpdate: lst
                }
                setShared('CMM018J_PARAM', paramJ);
                modal("/view/cmm/018/j/index.xhtml").onClosed(function(){
                    if(self.tabSelectedB()==0){
                        self.getDataCompanyPr();
                    }else if(self.tabSelectedB()==1){
                        self.getDataWorkplacePr();
                    }else{
                        self.getDataPersonPr();
                    }
                    
                });
                
            }
            /**
             * subscribe tab selected 
             * mode B: 申請個別登録モード
             */
            checkTabSelectedB(codeChanged: number, id: string){
                let self = this;
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
                        self.getDataPersonPr();
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
                let self = this;
                self.historyStr('');
                let common: boolean = false;
                let b = new vmbase.ApprovalPhaseDto([],'','',0,'',0,0);
                if(codeChanged == '共通ルート'){//1
                    let a = new vmbase.CompanyAppRootADto(false, true, 0, codeChanged, '', '','', b, b, b, b, b);
                    self.comRoot(a);
                }
                else if(codeChanged == nts.uk.resource.getText("CMM018_7")){//2
                    self.comRoot(null);
                }else{//appName
                   //find item current
                    let value = self.findAppbyName(codeChanged);
                    let a1 = new vmbase.CompanyAppRootADto(false, common, value.value, value.localizedName, '', '','', b, b, b, b, b);
                    self.comRoot(a1); 
                }
            }
        }
    }
}