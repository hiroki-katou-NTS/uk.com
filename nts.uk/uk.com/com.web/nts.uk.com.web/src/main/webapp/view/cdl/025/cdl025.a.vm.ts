module nts.uk.com.view.cdl025.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import model = nts.uk.com.view.ccg025.a.component.model;
    
    export module viewmodel {
        export class ScreenModel {
            component: ccg.component.viewmodel.ComponentModel;
            listRole : KnockoutObservableArray<model.Role>;
            constructor() {
                let self = this;
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: 1,
                    multiple: true
                });
                self.listRole = ko.observableArray([]);
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
                
            }
            
            /** btn cancel*/
            cancel(){
                
            }
            
        }//end screenModel
    }//end viewmodel
}//end module