module nts.uk.com.view.cdl025.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import model = nts.uk.com.view.ccg025.a.component.model;
    
    export interface Option {
        roleType?: number;
        multiple?: boolean;
    }
    export module viewmodel {
        export class ScreenModel {
            component: ccg.component.viewmodel.ComponentModel;
            listRole : KnockoutObservableArray<model.Role>;
            currentCode : KnockoutObservable<string>;
            currentCodes : KnockoutObservableArray<string>;
            roleType : number;
            multiple : boolean;
            //
            private defaultOption: Option = {
                multiple: true
            }
            private setting: Option;
            private searchMode: string;
            
            constructor(option: Option) {
                let self = this;
                //
                self.setting = $.extend({}, self.defaultOption, option);
                self.searchMode = (self.setting.multiple) ? "highlight" : "filter";
                //
//                self.roleType = 1;
//                self.multiple = true;
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: self.setting.roleType,
                    multiple: self.setting.multiple
                });
                self.listRole = ko.observableArray([]);
                self.currentCode = ko.observable('');
                self.currentCodes = ko.observableArray([]);
                //
                
            }

            /** Start page */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.component.startPage().done(function(){
                    self.listRole(self.component.listRole());
                    dfd.resolve();    
                });
                return dfd.promise();
            }//end start page
            
            /** btn decision*/
            decision(){
                let self = this;
                let param = {
                    currentCode : self.component.currentCode(),
                    multiple :  self.multiple
                };
                nts.uk.ui.windows.setShared("paramCdl025", param);
                nts.uk.ui.windows.close();
                //nts.uk.ui.windows.sub.modal("/view/kdw/001/g/index.xhtml");
                
            }
            
            /** btn cancel*/
            cancel(){
                nts.uk.ui.windows.close();
            }
            
        }//end screenModel
    }//end viewmodel
}//end module