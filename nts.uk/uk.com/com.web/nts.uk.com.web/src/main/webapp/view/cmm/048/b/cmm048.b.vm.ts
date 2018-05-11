module nts.uk.com.view.cmm048.b {

    import MainDto = nts.uk.com.view.cmm048.b.model.MainDto;
    import MailFunctionDto = nts.uk.com.view.cmm048.b.model.MailFunctionDto;
    import FunctionSettingDto = nts.uk.com.view.cmm048.b.model.FunctionSettingDto;
    
    export module viewmodel {  
        
        export class ScreenModel {        
            
            userInfoItemName: KnockoutObservable<string>;
            items: KnockoutObservableArray<FunctionSettingModel>;
            selectedItem: KnockoutObservable<FunctionSettingModel>;
            columnSetting: KnockoutObservableArray<any>;
            
            constructor() {
                let _self = this;
                
                _self.userInfoItemName = ko.observable("");
                _self.items = ko.observableArray([]);                
                _self.selectedItem = ko.observable(null);
                
                _self.columnSetting = ko.observableArray([
                    { headerText: "", key: 'functionId', width: 150,  hidden: true },
                    { headerText: nts.uk.resource.getText("CMM048_52"), key: 'functionName', width: 120 },
                    { headerText: nts.uk.resource.getText("CMM048_53"), key: 'sendSettingName', width: 120 }
                ]);
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                let dataObject: any = nts.uk.ui.windows.getShared("CMM048_DIALOG_B_INPUT_DATA");
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
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_46"));
                        service.findCompanyPcMail().done((data) => {
                            _self.items(_.map(data, (item) => new FunctionSettingModel(item)));
                        });
                    break;
                    case UserInfoItem.PERSONAL_PC_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_47"));
                        service.findPersonalPcMail().done((data) => {
                            _self.items(_.map(data, (item) => new FunctionSettingModel(item)));
                        });
                    break;
                    case UserInfoItem.COMPANY_MOBILE_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_48"));
                        service.findCompanyMobileMail().done((data) => {
                            _self.items(_.map(data, (item) => new FunctionSettingModel(item)));
                        });
                    break;
                    case UserInfoItem.PERSONAL_MOBILE_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_49"));
                        service.findPersonalMobileMail().done((data) => {
                            _self.items(_.map(data, (item) => new FunctionSettingModel(item)));
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
                             
        }
            
        export class MainModel {
            listFunctionSetting: Array<FunctionSettingModel>;
            
            constructor() {
                let _self = this;
                _self.listFunctionSetting = [];
            }
        }
        
        export class FunctionSettingModel {
            functionId: number;
            functionName: string;
            sendSettingName: string;
            
            constructor(dto: FunctionSettingDto) {
                let _self = this;
                _self.functionId = dto.functionId;
                _self.functionName = dto.functionName;
                if (dto.sendSetting) {
                    _self.sendSettingName = nts.uk.resource.getText("CMM048_55");
                } else {
                    _self.sendSettingName = nts.uk.resource.getText("CMM048_56");
                }      
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