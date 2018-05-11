module nts.uk.com.view.cmm049.b {


    export module viewmodel {

        export class ScreenModel {

            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;
             userInfoItemName: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                _self.items = ko.observableArray([]);
                _self.userInfoItemName = ko.observable("");

                _self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText(""), key: 'code', width: 250, hidden: true },
                    { headerText:  nts.uk.resource.getText("CMM049_16"), key: 'name', width: 250 }
                ]);

                _self.currentCodeList = ko.observableArray([]);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                 let dataObject: any = nts.uk.ui.windows.getShared("CMM049_DIALOG_B_INPUT_DATA");
                _self.bindingData(dataObject);

                dfd.resolve();
                return dfd.promise();
            }

             /**
             * Binding data
             */
            private bindingData(dataObject: any) {
                let _self = this;
                
                if (nts.uk.util.isNullOrUndefined(dataObject)) {                                   
                    return;
                }
                switch (dataObject.userInfo) {
                    case UserInfoItem.COMPANY_PC_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM049_16"));
                       service.getPCMailCompany().done((data:any)=>{
                           if (data) {
                               data.mailFunctionDto.forEach((item: any, index: any) => {
                                   _self.items.push(new ItemModel(item.functionId, item.functionName));
                               });
                           }
                       });
                    break;
                    case UserInfoItem.PERSONAL_PC_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM049_17"));
                       service.getPCMailPerson().done((data:any)=>{
                           if (data) {
                               data.mailFunctionDto.forEach((item: any, index: any) => {
                                   _self.items.push(new ItemModel(item.functionId, item.functionName));
                               });
                           }
                       });
                    break;
                    case UserInfoItem.COMPANY_MOBILE_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM049_18"));
                         service.getMobileMailCompany().done((data:any)=>{
                           if (data) {
                               data.mailFunctionDto.forEach((item: any, index: any) => {
                                   _self.items.push(new ItemModel(item.functionId, item.functionName));
                               });
                           }
                       });
                        
                    break;
                    case UserInfoItem.PERSONAL_MOBILE_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM049_19"));
                        service.getMobileMailPerson().done((data:any)=>{
                           if (data) {
                               data.mailFunctionDto.forEach((item: any, index: any) => {
                                   _self.items.push(new ItemModel(item.functionId, item.functionName));
                               });
                           }
                       });
                    break;
                    default:
                        _self.userInfoItemName("");
                }               
                
            }   

            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
            
            
            /**
             * save
             */
            public save(): void {
               
            }

        }

        class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
         export enum UserInfoItem {
            COMPANY_PC_MAIL,
            PERSONAL_PC_MAIL,
            COMPANY_MOBILE_MAIL,
            PERSONAL_MOBILE_MAIL,
            COMPANY_MOBILE_PHONE,
            PERSONAL_MOBILE_PHONE,
            PASSWORD
        }
    }
}