module nts.uk.com.view.csa005.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            value1 : any;
            value2 : any;
            //checked
            checked: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            
            roleCode : KnockoutObservable<string>;
            roleName : KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.value1 = ko.observable("02");
                self.value2 = ko.observable("name");
                //checker
                self.checked = ko.observable(true);
                self.enable = ko.observable(true)
                self.roleCode = ko.observable("");
                self.roleName = ko.observable("");
                let param = nts.uk.ui.windows.getShared("openB");
                if (param != null) {
                    self.roleCode(param.roleCode);
                    self.roleName(param.roleName);    
                }
                
                
            }

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
            
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            

            
            
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        
        /**
         * class Role
         */
        export class Role {
            roleId : string;
            roleCode : string;
            roleType : number;
            employeeReferenceRange: number;
            name: string;
            contractCode : string;
            assignAtr :number;
            companyId : string;
            constructor(roleId : string,roleCode : string,
            roleType : number,employeeReferenceRange: number,name: string,
            contractCode : string,assignAtr :number,companyId : string) {
                this.roleId = roleId;
                this.roleCode = roleCode;
                this.roleType = roleType;
                this.employeeReferenceRange = employeeReferenceRange;
                this.name = name;
                this.contractCode = contractCode;
                this.assignAtr = assignAtr;
                this.companyId = companyId;
                
                       
            }
        }//end class Role
        

    }//end module model

}//end module