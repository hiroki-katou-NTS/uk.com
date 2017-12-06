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
            items: KnockoutObservableArray<model.WorkPlaceFunction>;
            //table-right
            component: ccg.component.viewmodel.ComponentModel;
            
            //table-left
            //enum
            listEnumRoleType  :KnockoutObservableArray<any>;
            listEmployeeReferenceRange  :KnockoutObservableArray<any>;
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
                self.items = ko.observableArray([]);
                
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
                self.component.startPage().done(function(){
                    self.selectRoleCodeByIndex(0);  
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
                    self.items(data);
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
                self.selectRoleCodeByIndex(0);    
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
        }


    }//end module model

}//end module