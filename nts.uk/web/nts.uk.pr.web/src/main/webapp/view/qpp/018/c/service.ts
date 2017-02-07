module qpp018.c {
    export module service {

        // Service paths.
        var servicePath = {
           getallCheckListPrintSetting: "ctx/pr/report/insurance/findAll"
        };
        /**
         * get All CheckList Print Setting
         */
        export function getallCheckListPrintSetting(): JQueryPromise<model.CheckListPrintSetting[]>{
            return nts.uk.request.ajax(servicePath.getallCheckListPrintSetting);
         }
        
        export module model{
            export class CheckListPrintSetting{
                showCategoryInsuranceItem: boolean;
                showDeliveryNoticeAmount: boolean;
                showDetail: boolean;
                showOffice: boolean;
            }
        }
    }
}
