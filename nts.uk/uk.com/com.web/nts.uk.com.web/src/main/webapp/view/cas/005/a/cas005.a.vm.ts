module nts.uk.com.view.cas005.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import errors = nts.uk.ui.errors;

    export module viewmodel {
        export class ScreenModel {
            //text
            roleName: KnockoutObservable<string>;
            roleCode: KnockoutObservable<string>;
            assignAtr : KnockoutObservable<number>;
            employeeReferenceRange : KnockoutObservable<number>;
            //switch
            categoryAssign: KnockoutObservableArray<any>;
            referenceAuthority: KnockoutObservableArray<any>;
            selectCategoryAssign: any;
            selectReferenceAuthority: any;
            //combobox
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            //table
            currentCodeList: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            listWorkPlaceFunction: KnockoutObservableArray<model.WorkPlaceFunction>;
            listWorkPlaceAuthority : KnockoutObservableArray<any>; 
            listWpkAuthoritySelect : KnockoutObservableArray<any>;
            //table-right
            component: ccg.component.viewmodel.ComponentModel;
            
            //table-left
            //enum
            listEnumRoleType  :KnockoutObservableArray<any>;
            listEmployeeReferenceRange  :KnockoutObservableArray<any>; //row 6
            listEmployeeRefRange  :KnockoutObservableArray<any>; //row 2 8
            listScheduleEmployeeRef :KnockoutObservableArray<any>; //row 4
            
            selectedEmployeeReferenceRange: KnockoutObservable<string>;
            bookingScreen :  KnockoutObservable<string>;
            scheduleScreen :  KnockoutObservable<string>;
            registeredInquiries :  KnockoutObservable<string>;
            specifyingAgent :  KnockoutObservable<string>;
            //list 
            listWebMenu : KnockoutObservableArray<any>;
            selectWebMenu : any;
            listRole : KnockoutObservableArray<any>;
            //enable
            isRegister :KnockoutObservable<boolean>;
            isDelete :KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                //table enum RoleType,EmployeeReferenceRange
                self.listEnumRoleType = ko.observableArray(__viewContext.enums.RoleType);
                self.listEmployeeReferenceRange = ko.observableArray(__viewContext.enums.EmployeeReferenceRange);
//                self.listEmployeeRefRange = ko.observableArray(__viewContext.enums.EmployeeRefRange);
//                self.listScheduleEmployeeRef = ko.observableArray(__viewContext.enums.EmployeeRefRange);
                self.selectedEmployeeReferenceRange = ko.observable("");
                self.bookingScreen = ko.observable("");
                self.scheduleScreen = ko.observable("");
                self.registeredInquiries = ko.observable("");
                self.specifyingAgent = ko.observable("");
              
                //text
                self.roleName = ko.observable('');
                self.roleCode = ko.observable('');
                self.assignAtr = ko.observable(0);
                self.employeeReferenceRange = ko.observable(0);
                

                //switch
                self.categoryAssign = ko.observableArray([
                    { code: '0', name: getText('CAS005_35') },
                    { code: '1', name: getText('CAS005_36') }
                ]);
                self.referenceAuthority = ko.observableArray([
                    { code: '0', name: getText('CAS005_41') },
                    { code: '1', name: getText('CAS005_42') }
                ]);
                self.selectCategoryAssign = ko.observable(1);
                self.selectReferenceAuthority = ko.observable(1);
                //combobox
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                //table
                self.listWorkPlaceFunction = ko.observableArray([]);
                self.listWorkPlaceAuthority =  ko.observableArray([]);
                self.listWpkAuthoritySelect = ko.observableArray([]);
                
                self.columns = ko.observableArray([
                    { headerText: 'Name', key: 'functionNo', width: 100, hidden: true },
                    { headerText: 'Name', key: 'displayName', width: 150 },
                    { headerText: 'Description', key: 'description', width: 400 },
                    
                ]);
                self.currentCodeList = ko.observableArray([]);
                //list
                self.listWebMenu = ko.observableArray([]);
                self.selectWebMenu = ko.observable(0);
                self.listRole = ko.observableArray([]);
                //enable
                self.isRegister = ko.observable(false);
                self.isDelete= ko.observable(false);
                //table right
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: 3,
                    multiple: false
                });
                self.component.currentCode.subscribe((value) => {
                    
                    let item = _.find(self.listRole(), ['roleId', value]);
                    if(item !== undefined){
                        self.roleName(item.name);
                        self.assignAtr(item.assignAtr);   
                        self.employeeReferenceRange(item.employeeReferenceRange);  
                        self.roleCode(item.roleCode); 
                        self.listWpkAuthoritySelect([]);
                        for(let i = 0;i< self.listWorkPlaceAuthority().length;i++){
                            if(self.listWorkPlaceAuthority()[i].roleId ==  value)
                                self.listWpkAuthoritySelect.push(self.listWorkPlaceAuthority()[i]); 
                        }
                        //web menu
                        self.getRoleByRoleTiesById(value);
                        
                    }else{
                        self.roleName('');
                        self.assignAtr(1);
                        self.employeeReferenceRange(1);
                        self.roleCode('');
                    }
                });
                

            }
            /** Select TitleMenu by Index: Start & Delete case */
            private selectRoleCodeByIndex(index: number) {
                var self = this;
                var selectRoleCodeByIndex = _.nth(self.component.listRole(), index);
                if (selectRoleCodeByIndex !== undefined){
                    self.roleCode(selectRoleCodeByIndex.roleCode);
                    self.roleName(selectRoleCodeByIndex.name);
                    self.assignAtr(selectRoleCodeByIndex.assignAtr);
                    self.employeeReferenceRange(selectRoleCodeByIndex.employeeReferenceRange);
                    self.listWpkAuthoritySelect([]);
                    for(let i = 0;i< self.listWorkPlaceAuthority().length;i++){
                            if(self.listWorkPlaceAuthority()[i].roleId ==  selectRoleCodeByIndex.roleId)
                                self.listWpkAuthoritySelect.push(self.listWorkPlaceAuthority()[i]); 
                        }
                    self.getRoleByRoleTiesById(selectRoleCodeByIndex.roleId);
                }
                else{
                    self.roleCode(null);
                    self.roleName(null);
                    self.assignAtr(1);
                    self.employeeReferenceRange(1);
                }
                
            }
            

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                
                let self = this;
                let dfd = $.Deferred();
                
                self.isRegister(true);
                self.isDelete(true);
                self.getAllWorkPlaceFunction();
                self.getAllWorkPlaceAuthority();
                self.component.startPage().done(function(){
                    self.listRole(self.component.listRole());
                    self.getListWebMenu();
                    dfd.resolve();    
                });
                return dfd.promise();
            }//end start page
            
            /**
             * function get AllWorkPlace Function
             */
            getAllWorkPlaceFunction(){
                let self = this;
                let dfd = $.Deferred();
                service.getAllWorkPlaceFunction().done(function(data){
                    self.listWorkPlaceFunction(data);
                    dfd.resolve(data);  
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                dfd.resolve(); 
            }
            /**
             * function getRoleByRoleTiesById
             */
            getRoleByRoleTiesById(roleId : string){
                let self = this;
                let dfd = $.Deferred();            
                service.getRoleByRoleTiesById(roleId).done(function(data){
                    self.selectWebMenu(data.webMenuCd);   
                    dfd.resolve(data);    
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                dfd.resolve(); 
            }
            
            /**
             * function  get AllWorkPlace Authority
             */
            getAllWorkPlaceAuthority(){
                let self = this;
                let dfd = $.Deferred();
                service.getAllWorkPlaceAuthority().done(function(data){
                    self.listWorkPlaceAuthority(data);
                    self.selectRoleCodeByIndex(0);
                    dfd.resolve(data);  
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                dfd.resolve(); 
            }
            
            /**
             * btnCreate
             */
            createButton(){
                let self = this;
                self.roleName(null);
                errors.clearAll();
                $("#roleTypeCd").focus()
                self.isRegister(true);
                self.isDelete(false);
            }
            
            /**
             * btn register
             */
            registerButton(){
                let self =this;
                self.isRegister(true);
                self.isDelete(true);
                if (!$(".nts-input").ntsError("hasError")){
                    self.selectRoleCodeByIndex(0);    
                }
                    
            }
            /**
             * btn delete
             */
            deleteButton(){
                let self = this;
                self.isRegister(true);
                self.isDelete(true);
                
            }
            /**
             * get list  web menu 
             */
            getListWebMenu(){
                let self = this;
                let dfd = $.Deferred<any>();
                service.getListWebMenu().done(function(data){
                    self.listWebMenu(data);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            
            openDialogB() {
                let self = this;
                let param = {
                    roleName: self.roleName(),
                    roleCode :self.roleCode()
                };
                nts.uk.ui.windows.setShared("openB", param);
                nts.uk.ui.windows.sub.modal("/view/cas/005/b/index.xhtml");
            }



        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        //class WorkPlaceFunction
        export class WorkPlaceFunction {
            functionNo: number;
            displayName: string;
            displayOrder: number;
            description: string;
            initialValue: boolean;
            constructor(functionNo: number, displayName: string, displayOrder: number, description: string, initialValue: boolean) {
                this.functionNo = functionNo;
                this.displayName = displayName;
                this.displayOrder = displayOrder;
                this.description = description;
                this.initialValue = initialValue;
            }
                
        }//end class WorkPlaceFunction
                
        //class RoleCas005Command
        export class RoleCas005Command{
            roleId : string;
            roleCode : string;
            roleType : number;
            employeeReferenceRange : number;
            name : string;
            contractCode : string;
            assignAtr :number;
            companyId: string;
            //RoleByRoleTies
            webMenuCd : string;
            // class :就業ロール
            scheduleEmployeeRef : number;
            bookEmployeeRef : number;
            employeeRefSpecAgent : number;
            presentInqEmployeeRef : number;
            futureDateRefPermit : number;
            //WorkPlaceAuthority
            listWorkPlaceAuthority : Array<WorkPlaceAuthorityCommand>;
            constructor(
                roleCode : string,
                roleType : number,
                employeeReferenceRange : number,
                name : string,
                assignAtr :number,
                //RoleByRoleTies
                webMenuCd : string,
                // class :就業ロール
                scheduleEmployeeRef : number,
                bookEmployeeRef : number,
                employeeRefSpecAgent : number,
                presentInqEmployeeRef : number,
                futureDateRefPermit : number,
                //WorkPlaceAuthority
                listWorkPlaceAuthority : Array<WorkPlaceAuthorityCommand>){
                    this.roleCode = roleCode;
                    this.roleType = roleType;
                    this.employeeReferenceRange = employeeReferenceRange;
                    this.name = name;
                    this.assignAtr = assignAtr;
                    this.webMenuCd = webMenuCd;
                    this.scheduleEmployeeRef = scheduleEmployeeRef;
                    this.bookEmployeeRef = bookEmployeeRef;
                    this.employeeRefSpecAgent = employeeRefSpecAgent;
                    this.presentInqEmployeeRef = presentInqEmployeeRef;
                    this.futureDateRefPermit = futureDateRefPermit;
                    this.listWorkPlaceAuthority = listWorkPlaceAuthority;
            }
            
        }//end class RoleCas005Command      
        
                
        //class WorkPlaceAuthorityCommand
        export class WorkPlaceAuthorityCommand{
            roleId : string;
            companyId : string;
            functionNo : number;
            availability : boolean; 
            constructor(roleId : string,
                companyId : string,
                functionNo : number,
                availability : boolean){
                    this.roleId = roleId;
                    this.companyId = companyId;
                    this.functionNo = functionNo;
                    this.availability = availability;
            }
        }//end class WorkPlaceAuthorityCommand        
        
    }//end module model

}//end module