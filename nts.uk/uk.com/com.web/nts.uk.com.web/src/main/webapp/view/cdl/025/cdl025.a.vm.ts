module nts.uk.com.view.cdl025.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import model = nts.uk.com.view.ccg025.a.component.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            component: ccg.component.viewmodel.ComponentModel;
            listRole : KnockoutObservableArray<model.Role>;
            currentCode : KnockoutObservable<string>;
            currentCodes : KnockoutObservableArray<string>;
            roleType : number;
            multiple : boolean;
            //
            private searchMode: string;
            
            constructor() {
                let self = this;
                let param = nts.uk.ui.windows.getShared("paramCdl025"); 
                if(param !=null){
                    self.roleType = param.roleType;
                    self.multiple = param.multiple;    
                }
                
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: self.roleType,
                    multiple: self.multiple
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
                nts.uk.ui.windows.setShared("dataCdl025", param);
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