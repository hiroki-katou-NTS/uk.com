module nts.uk.com.view.cas005.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import errors = nts.uk.ui.errors;
    export module viewmodel {
        export class ScreenModel {
            //text
            roleType: KnockoutObservable<number>;
            roleName: KnockoutObservable<string>;
            roleCode: KnockoutObservable<string>;
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
            items: KnockoutObservableArray<model.ItemModel2>;
            //table-right
            component: ccg.component.viewmodel.ComponentModel;
            //table-left
            columnRoleType : KnockoutObservableArray<any>;
            currentType : KnockoutObservable<any>;
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
                self.columnRoleType = ko.observableArray([
                    { headerText: 'コード', key: 'value', width: 100 },
                    { headerText: '名称', key: 'name', width: 170 }
                ]);
                self.currentType = ko.observable(null);
                //text
                self.roleName = ko.observable('');
                self.roleType = ko.observable(123);
                self.roleCode = ko.observable('');
                self.roleType.subscribe((value) => {
                    let item = _.find(self.listEnumRoleType(), ['value', Number(value)]).name;
                    if(item !== undefined){
                        self.roleName(item);   
                    }else{
                        self.roleName('');
                    }
                });
                

                //switch
                self.categoryAssign = ko.observableArray([
                    { code: '1', name: getText('CAS005_35') },
                    { code: '2', name: getText('CAS005_36') }
                ]);
                self.referenceAuthority = ko.observableArray([
                    { code: '1', name: getText('CAS005_41') },
                    { code: '2', name: getText('CAS005_42') }
                ]);
                self.selectCategoryAssign = ko.observable(1);
                self.selectReferenceAuthority = ko.observable(1);
                //combobox
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                //table
                self.items = ko.observableArray([]);
                for (let i = 1; i < 100; i++) {
                    this.items.push(new model.ItemModel2('00' + i, '基本給 基本給', "description " + i, i % 3 === 0, "2010/1/1"));
                }
                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: '名称', key: 'name', width: 150, columnCssClass: "test" },
                    { headerText: '説明', key: 'description', width: 150 },
                    { headerText: '説明1', key: 'other1', width: 150 },
                    { headerText: '説明2', key: 'other2', width: 150, isDateColumn: true, format: 'YYYY/MM/DD' }
                ]);
                self.currentCodeList = ko.observableArray([]);
                //list
                self.listWebMenu = ko.observableArray([]);
                self.selectWebMenu = ko.observable(0);
                //enable
                self.isRegister = ko.observable(false);
                self.isDelete= ko.observable(false);

            }
            /** Select TitleMenu by Index: Start & Delete case */
            private selectRoleTypeByIndex(index: number) {
                var self = this;
                var selectRoleTypeByIndex = _.nth(self.listEnumRoleType(), index);
                if (selectRoleTypeByIndex !== undefined)
                    self.roleType(selectRoleTypeByIndex.value);
                else
                    self.roleType(null);
                
            }
            

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                
                let self = this;
                let dfd = $.Deferred();
                self.selectRoleTypeByIndex(0);
                self.isRegister(true);
                self.isDelete(true);
                let dfdGetWebMenu = self.getListWebMenu();
                $.when(dfdGetWebMenu).done((dfdGetWebMenuData) => {
                    dfd.resolve();
                });
                return dfd.promise();
            }//end start page
            
            /**
             * btnCreate
             */
            createButton(){
                let self = this;
                self.roleType(null);    
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
                self.selectRoleTypeByIndex(0);    
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
        

        export class ItemModel2 {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
                this.deletable = deletable;
            }
        }


    }//end module model

}//end module