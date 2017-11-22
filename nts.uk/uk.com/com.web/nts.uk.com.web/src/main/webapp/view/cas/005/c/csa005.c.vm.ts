module nts.uk.com.view.csa005.c {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
           
            constructor() {
                let self = this;
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