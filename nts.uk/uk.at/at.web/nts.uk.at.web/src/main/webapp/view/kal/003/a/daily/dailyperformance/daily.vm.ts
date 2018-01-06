module nts.uk.at.view.kal003.a.daily {
    import getText = nts.uk.resource.getText;


    export module viewmodel {
        export class DailyModel {
            /** functiton start page */
            startPage(){
                let self = this;
            }

        }//end screenModel
    }//end viewmodel

    //module model
    export module model {

        export interface IRole {
            roleId: string;
            roleCode: string;
            roleType: number;
            employeeReferenceRange: number;
            name: string;
            contractCode: string;
            assignAtr: number;
            companyId: string;
        }
        
       


    }//end module model

}//end module