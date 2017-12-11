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
            listWpkAuthoritySelect : KnockoutObservableArray<model.WpkAuthoritySelect>;
            
            //table-right
            component: ccg.component.viewmodel.ComponentModel;
            
            //table-left
            //enum
            listEnumRoleType  :KnockoutObservableArray<any>;
            listEmployeeReferenceRange  :KnockoutObservableArray<any>; //row 6
            listEmployeeRefRange  :KnockoutObservableArray<any>; //row 2 8
            listScheduleEmployeeRef :KnockoutObservableArray<any>; //row 4
            
            selectedEmployeeReferenceRange: KnockoutObservable<number>;
            bookingScreen :  KnockoutObservable<number>;
            scheduleScreen :  KnockoutObservable<number>;
            registeredInquiries :  KnockoutObservable<number>;
            specifyingAgent :  KnockoutObservable<number>;
            //list 
            listWebMenu : KnockoutObservableArray<any>;
            selectWebMenu : any;
            listRole : KnockoutObservableArray<any>;
            //enable
            isRegister :KnockoutObservable<boolean>;
            isDelete :KnockoutObservable<boolean>;
            enableRoleCode : KnockoutObservable<boolean>;
            visibleWebmenu : KnockoutObservable<boolean>;
            //obj roleCas005Command
            roleCas005Command  :KnockoutObservable<model.RoleCas005Command>;
            listWorkPlaceSelect : KnockoutObservableArray<model.WorkPlaceAuthorityCommand>;
            listWorkPlaceAuthorityCommand : KnockoutObservableArray<model.WorkPlaceAuthorityCommand>;
            listWorkPlaceAuthorityParam : KnockoutObservableArray<model.WorkPlaceAuthorityCommand>;
            //obj DeleteRoleCas005Command
            deleteRoleCas005Command : KnockoutObservable<model.DeleteRoleCas005Command>;
            //obj to screen dialog B
            objCommandScreenB : KnockoutObservable<model.RoleCas005Command>;
            constructor() {
                let self = this;
                //table enum RoleType,EmployeeReferenceRange
                self.listEnumRoleType = ko.observableArray(__viewContext.enums.RoleType);
                self.listEmployeeReferenceRange = ko.observableArray(__viewContext.enums.EmployeeReferenceRange);
                self.listEmployeeRefRange = ko.observableArray(__viewContext.enums.EmployeeRefRange);
                self.listScheduleEmployeeRef = ko.observableArray(__viewContext.enums.ScheduleEmployeeRef);
                self.selectedEmployeeReferenceRange = ko.observable(0);
                self.bookingScreen = ko.observable(0);
                self.scheduleScreen = ko.observable(0);
                self.registeredInquiries = ko.observable(0);
                self.specifyingAgent = ko.observable(0);
              
                //text
                self.roleName = ko.observable('');
                self.roleCode = ko.observable('');
                self.assignAtr = ko.observable(0);
                self.assignAtr.subscribe(function(value){
                    if(value == 0){
                        self.visibleWebmenu(true);
                    }else{
                        self.visibleWebmenu(false);
                    }
                    
                });
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
                self.listWpkAuthoritySelect = ko.observableArray([]);
                
                self.columns = ko.observableArray([
                    { headerText: 'Name', key: 'functionNo', width: 100, hidden: true },
                    { headerText: 'Name', key: 'displayName', width: 150 },
                    { headerText: 'Description', key: 'description', width: 400 },
                    
                ]);
                $("#listWpkAuthoritySelect").bind("rowavailabilitychanged", function(evt){
                    self.listWorkPlaceAuthorityCommand([]);
                    for(let i = 0;i< self.listWpkAuthoritySelect().length;i++){ //sucribe
                        let tempCommand = new model.WorkPlaceAuthorityCommand(
                            self.listWpkAuthoritySelect()[i].functionNo,self.listWpkAuthoritySelect()[i].availability());
                        
                        self.listWorkPlaceAuthorityCommand().push(tempCommand);  
                    }
                    
                });
                self.currentCodeList = ko.observableArray([]);
                //list
                self.listWebMenu = ko.observableArray([]);
                self.selectWebMenu = ko.observable(0);
                self.listRole = ko.observableArray([]);
                //enable
                self.isRegister = ko.observable(false);
                self.isDelete= ko.observable(false);
                self.enableRoleCode = ko.observable(false);
                self.visibleWebmenu = ko.observable(true);
                //table right
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: 3,
                    multiple: false
                });
                //obj roleCas005Command
                self.roleCas005Command = ko.observable(null); 
                self.listWorkPlaceSelect = ko.observableArray([]);
                self.listWorkPlaceAuthorityCommand = ko.observableArray([]);
                self.listWorkPlaceAuthorityParam = ko.observableArray([]);
                
                //obj deleteRoleCas005Command
                self.deleteRoleCas005Command = ko.observable(null);
                //obj command cas005 a 
                self.objCommandScreenB = ko.observable(null);
                //current code
                self.component.currentCode(null);
                self.component.currentCode.subscribe((value) => {
                    self.enableRoleCode(false);
                    errors.clearAll();
                    let item = _.find(self.listRole(), ['roleId', value]);
                    
                    if(item !== undefined){
                        self.roleName(item.name);
                        self.assignAtr(item.assignAtr);   
                        self.employeeReferenceRange(item.employeeReferenceRange);  
                        self.roleCode(item.roleCode); 
                        self.listWpkAuthoritySelect([]);
                        self.listWorkPlaceAuthorityCommand([]);
                        
                        //web menu
                        if(item.assignAtr == 0){
                            self.getRoleByRoleTiesById(value);
                        }
                        self.getEmploymentRoleById(value);
                        self.getAllWorkPlaceAuthorityById(value);
                        //obj command cas
                        
                        self.objCommandScreenB(new model.RoleCas005Command(
                            self.component.currentCode(),
                            item.roleCode,
                            3, //roletype
                            item.employeeReferenceRange,
                            item.name,
                            item.assignAtr,
                            self.selectWebMenu(),
                            self.scheduleScreen(),
                            self.bookingScreen(),
                            self.specifyingAgent(),
                            self.registeredInquiries(),
                            self.selectReferenceAuthority(),
                            self.listWorkPlaceAuthorityCommand()
                        ));
                        
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
                    self.component.currentCode(selectRoleCodeByIndex.roleId);
                }
                
            }
            /**
             * select by roleID
             */
            private selectRoleByRoleId(roleid :string){
                let self = this;
                let item = _.find(self.listRole(), ['roleId', roleid]);
                self.component.currentCode((item) ? item.roleId : "");
            }
            
            /**
             * select currentCode
             */
            private selectCurrentCode(currentCode){
                let self = this;
                let item = _.find(self.listRole(), ['roleId', currentCode]);
                if(item !== undefined){
                    self.roleName(item.name);
                    self.assignAtr(item.assignAtr);   
                    self.employeeReferenceRange(item.employeeReferenceRange);  
                    self.roleCode(item.roleCode); 
                    //web menu
                    if(item.assignAtr == 0){
                        self.getRoleByRoleTiesById(currentCode);
                    }
                    self.getEmploymentRoleById(currentCode);
                    self.getAllWorkPlaceAuthorityById(currentCode);
                    
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
                
                self.getData().done(function(){
                    self.selectRoleByRoleId(self.component.currentCode());
                    dfd.resolve();  
                });
                return dfd.promise();
            }//end start page
            
            /**
             * get data
             */
            
            private getData(){
                let self = this;
                let dfd = $.Deferred();
                self.component.startPage().done(function(){
                    self.listRole(self.component.listRole());
                    self.getListWebMenu();
                    self.getAllWorkPlaceAuthorityById(self.component.currentCode());
                    dfd.resolve();    
                });    
                return dfd.promise();
            }
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
                service.getAllWorkPlaceAuthority().done((data) => {
                    dfd.resolve(data);  
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                dfd.resolve(); 
            }
            /**
             * 
             */
            getAllWorkPlaceAuthorityById(roleId : string){
                let self = this;
                let dfd = $.Deferred();
                service.getAllWorkPlaceAuthorityById(roleId).done((data) => {
                    self.listWorkPlaceAuthorityParam([]);
                    self.listWpkAuthoritySelect([]);
                    for(let i = 0;i< data.length;i++){
                        let temp = new model.WpkAuthoritySelect(
                            data[i].functionNo,
                            data[i].availability,
                            _.find(self.listWorkPlaceFunction(), ['functionNo', data[i].functionNo]).displayName,
                            _.find(self.listWorkPlaceFunction(), ['functionNo', data[i].functionNo]).description
                            );
                        self.listWpkAuthoritySelect.push(temp);
                        let workPlaceAuthorityCommand = new  model.WorkPlaceAuthorityCommand(
                                data[i].functionNo,
                                data[i].availability
                            );
                        self.listWorkPlaceAuthorityParam.push(workPlaceAuthorityCommand);
                    }
                    
                    let listWorkPlaceAuthority = _.map(data, (item) => {
                    });
                    dfd.resolve(data);  
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                dfd.resolve(); 
            }
            /**
             * getEmploymentRoleById
             */
            getEmploymentRoleById(roleId : string){
                let self = this;
                let dfd = $.Deferred();            
                service.getEmploymentRoleById(roleId).done(function(data){
                    self.scheduleScreen(data.scheduleEmployeeRef);
                    self.bookingScreen(data.bookEmployeeRef);
                    self.specifyingAgent(data.employeeRefSpecAgent);
                    self.registeredInquiries(data.presentInqEmployeeRef);
                    self.selectReferenceAuthority(data.futureDateRefPermit);
                    dfd.resolve(data);    
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                dfd.resolve(); 
            }
            //20Oct1993778343
            /**
             * btnCreate
             */
            createButton(){
                
                let self = this;
                self.listWpkAuthoritySelect();
                self.enableRoleCode(true);
                self.roleName(null);
                self.roleCode("");
                errors.clearAll();
                self.isRegister(true);
                self.isDelete(false);
                self.assignAtr(0);
                self.selectWebMenu(0);
                self.scheduleScreen(0);
                self.bookingScreen(0);
                self.specifyingAgent(0);
                self.registeredInquiries(0);
                self.selectReferenceAuthority(0);
                self.listWpkAuthoritySelect([]);
                self.listWorkPlaceAuthorityCommand([]);
                for(let i = 0;i< self.listWorkPlaceFunction().length;i++){
                    let temp = new model.WpkAuthoritySelect(
                    self.listWorkPlaceFunction()[i].functionNo,
                        false,
                        _.find(self.listWorkPlaceFunction(), ['functionNo', self.listWorkPlaceFunction()[i].functionNo]).displayName,
                        _.find(self.listWorkPlaceFunction(), ['functionNo', self.listWorkPlaceFunction()[i].functionNo]).description
                        )
                    
                    self.listWpkAuthoritySelect.push(temp);
                }            
                $("#roleTypeCd").focus();   
            }
            
            /**
             * btn register
             */
            registerButton(){
                let self =this;
                self.isRegister(true);
                self.isDelete(true);
                self.listWorkPlaceAuthorityCommand([]);
                for(let i = 0;i< self.listWpkAuthoritySelect().length;i++){ //sucribe
                        let tempCommand = new model.WorkPlaceAuthorityCommand(
                            self.listWpkAuthoritySelect()[i].functionNo,self.listWpkAuthoritySelect()[i].availability());
                        
                        self.listWorkPlaceAuthorityCommand().push(tempCommand);  
                 }
                if (!$(".nts-input").ntsError("hasError")){
                    
                    self.roleCas005Command(new model.RoleCas005Command(
                        "",
                        self.roleCode(),
                        3, //roletype
                        self.employeeReferenceRange(),
                        self.roleName(),
                        self.assignAtr(),
                        self.selectWebMenu(),
                        self.scheduleScreen(),
                        self.bookingScreen(),
                        self.specifyingAgent(),
                        self.registeredInquiries(),
                        self.selectReferenceAuthority(),
                        self.listWorkPlaceAuthorityCommand()
                        ));
                    if(self.enableRoleCode()){
                        self.addRoleCas005(self.roleCas005Command()).done(function(){
                            self.getData().done(function(){
                                let roleId = "";
                                for(let i= 0;i<self.component.listRole().length;i++){
                                    if(self.component.listRole()[i].roleCode == self.roleCas005Command().roleCode){
                                       roleId = self.component.listRole()[i].roleId;    
                                    }
                                }
                                self.selectRoleByRoleId(roleId);    
                            });
                        });
                        
                        
                    }else{
                        self.roleCas005Command().roleId = self.component.currentCode();
                        self.updateRoleCas005(self.roleCas005Command());
                        let index = 0;
                        for(let i= 0;i<self.component.listRole().length;i++){
                            if(self.component.currentCode() == self.component.listRole()[i].roleId ){
                                index = i;
                            }
                        };
                        self.getData().done(function(){
                            self.selectRoleByRoleId(self.roleCas005Command().roleId);    
                        });
                        
                        
                    }
                    
                    //self.selectRoleCodeByIndex(0);    
                }
                    
            }
            /**
             * btn delete
             */
            deleteButton(){
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    let temp = new model.DeleteRoleCas005Command(self.component.currentCode());
                    let index = 0;
                    for(let i= 0;i<self.component.listRole().length;i++){
                        if(self.component.currentCode() == self.component.listRole()[i].roleId ){
                            index = i;
                        }
                    };
                    if(index ==self.component.listRole().length){
                        index = index-1;    
                    }
                    
                    self.deleteRoleCas005(temp).done(function(){
                        self.getData().done(function(){
                            if(index !=-1){
                                self.selectRoleCodeByIndex(index);
                            }else{
                                self.createButton();    
                            }    
                        });    
                    });
                                                
                });
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
            /**
             * add Role screen Cas005
             */
            addRoleCas005(command : model.RoleCas005Command){
                let self = this;
                let dfd = $.Deferred<any>();
                service.addRoleCas005(command).done(function(){
                    self.enableRoleCode(false);
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise(); 
            }
            
             /**
             * 
 Role screen Cas005
             */
            updateRoleCas005(command : model.RoleCas005Command){
                let self = this;
                let dfd = $.Deferred<any>();
                service.updateRoleCas005(command).done(function(){
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise(); 
            }
            
            /**
             * delete Role screen Cas005
             */
            deleteRoleCas005(command : model.DeleteRoleCas005Command){
                let self = this;
                let dfd = $.Deferred<any>();
                service.deleteRoleCas005(command).done(function(){
                    alert("xoa thanh cong");
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise(); 
            }
            
            /**
             * open dialog B
             */
            openDialogB() {
                let self = this;
                let dfd = $.Deferred();
                let item = _.find(self.listRole(), ['roleId', self.component.currentCode()]);
                // get all CaseSpecExeContent
                let dfdEmploymentRoleById = self.getEmploymentRoleById(self.component.currentCode());
                let dfdAllWorkPlaceAuthorityById = self.getAllWorkPlaceAuthorityById(self.component.currentCode());
                let dfdRoleByRoleTiesById = self.getRoleByRoleTiesById(self.component.currentCode());
                $.when(dfdEmploymentRoleById,dfdAllWorkPlaceAuthorityById,dfdRoleByRoleTiesById).done((
                    dfdEmploymentRoleByIdData,dfdAllWorkPlaceAuthorityByIdData,dfdRoleByRoleTiesByIdData) => {
                    self.objCommandScreenB(new model.RoleCas005Command(
                            self.component.currentCode(),
                            item.roleCode,
                            3, //roletype
                            item.employeeReferenceRange,
                            item.name,
                            item.assignAtr,
                            self.selectWebMenu(),
                            self.scheduleScreen(),
                            self.bookingScreen(),
                            self.specifyingAgent(),
                            self.registeredInquiries(),
                            self.selectReferenceAuthority(),
                            self.listWorkPlaceAuthorityParam()
                        ));
                    let param = {
                    roleName: self.roleName(),
                    roleCode :self.roleCode()
                    };
                    nts.uk.ui.windows.setShared("openB", self.objCommandScreenB());
                    nts.uk.ui.windows.sub.modal("/view/cas/005/b/index.xhtml").onClosed(() => {
                        let roleCode = nts.uk.ui.windows.getShared("closeB");
                        if (roleCode != null) {
                            self.getData().done(()=>{
                                let item = _.find(self.listRole(), ['roleCode', roleCode]);
                                self.selectRoleByRoleId(item.roleId);
                                  
                            });
                        }
                    });
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            //open dialog C 
            openDialogC() {
                nts.uk.ui.windows.sub.modal("/view/cas/005/c/index.xhtml");
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
            roleCode : string; //row 1
            roleType : number; //
            employeeReferenceRange : number; //row 3
            name : string; //row 1
            contractCode : string;
            assignAtr :number; //row 2
            companyId: string;
            //RoleByRoleTies
            webMenuCd : string; //row 5
            // class :就業ロール
            scheduleEmployeeRef : number; //A3_034 row 7
            bookEmployeeRef : number;   //A3_032 row 6
            employeeRefSpecAgent : number;//A3_038  row 9
            presentInqEmployeeRef : number;//A3_036 row 8
            futureDateRefPermit : number;//A3_12,13 row 4
            //WorkPlaceAuthority
            listWorkPlaceAuthority : Array<WorkPlaceAuthorityCommand>;
            constructor(
                roleId : string,
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
                    this.roleId = roleId;
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
            constructor(
                functionNo : number,
                availability : boolean){
                    this.functionNo = functionNo;
                    this.availability = availability;
            }
        }//end class WorkPlaceAuthorityCommand   
        
        export interface IWpkAuthoritySelect {
            roleId : string;
            functionNo : number;
            availability :KnockoutObservable<boolean>;
            displayName : string;
            description : string;
        }
        
        export class WpkAuthoritySelect{
            roleId : string;
            functionNo : number;
            availability :KnockoutObservable<boolean>;
            displayName : string;
            description : string;
            constructor(
            functionNo : number,
            availability :boolean,
            displayName : string,
            description : string){
                this.functionNo = functionNo;   
                this.availability = ko.observable(availability);
                this.displayName = displayName;
                this.description = description;
                this.availability.subscribe(function(x){
                    $("#listWpkAuthoritySelect").trigger("rowavailabilitychanged");
                });
            }
        }
        export class DeleteRoleCas005Command{
           roleId : string; 
           constructor(roleId : string){
               this.roleId = roleId;
           }
        }
    }//end module model
}//end module