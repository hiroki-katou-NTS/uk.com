module nts.uk.com.view.cmm048.b {

    import FunctionSettingDto = nts.uk.com.view.cmm048.b.model.FunctionSettingDto;
    
    export module viewmodel {  
        
        export class ScreenModel {        
                        
            columnSetting: KnockoutObservableArray<any>;
            
            mainModel: MainModel;
            
            constructor() {
                let _self = this;
                
                _self.mainModel = new MainModel();            
                
                _self.columnSetting = ko.observableArray([
                    { headerText: "", key: 'functionId', width: 150,  hidden: true },
                    { headerText: nts.uk.resource.getText("CMM048_52"), key: 'functionName', width: 150 },
                    { headerText: nts.uk.resource.getText("CMM048_53"), key: 'sendSettingName', width: 150 }
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
                _self.mainModel.updateData(dataObject);                          
            }   
            
            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
                             
        }
            
        export class MainModel {
            userInfoItemName: KnockoutObservable<string>;
            listFunctionSetting: KnockoutObservableArray<FunctionSettingModel>;
            selectedItem: KnockoutObservable<FunctionSettingModel>;
            
            constructor() {
                let _self = this;
                _self.userInfoItemName = ko.observable("");
                _self.listFunctionSetting = ko.observableArray([]);
                _self.selectedItem = ko.observable(null);
            }
            
            updateData(dataObject: any) {
                let _self = this;
                let data:any = dataObject.data; 
                switch (dataObject.userInfo) {
                    case UserInfoItem.COMPANY_PC_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_46"));
                        _self.listFunctionSetting(_.map(data, (item:any) => new FunctionSettingModel(item)));
                        break;
                    case UserInfoItem.PERSONAL_PC_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_47"));
                        _self.listFunctionSetting(_.map(data, (item:any) => new FunctionSettingModel(item)));
                        break;
                    case UserInfoItem.COMPANY_MOBILE_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_48"));
                        _self.listFunctionSetting(_.map(data, (item:any) => new FunctionSettingModel(item)));
                        break;
                    case UserInfoItem.PERSONAL_MOBILE_MAIL:
                        _self.userInfoItemName(nts.uk.resource.getText("CMM048_49"));
                        _self.listFunctionSetting(_.map(data, (item:any) => new FunctionSettingModel(item)));
                        break;
                    default:
                        _self.userInfoItemName("");
                }   
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