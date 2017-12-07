module nts.uk.com.view.cas13.b {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.cas13.b;
    import model = nts.uk.com.view.cas13.b.component.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import com = nts.uk.com.view.cas13.b.component.viewmodel;
    
    export module viewmodel {
        export class ScreenModel {
            component: ccg.component.viewmodel.ComponentModel;
            roleType : number;
            multiple : boolean;
            
            private searchMode: string;
            
            constructor() {
                let self = this;
                let param = nts.uk.ui.windows.getShared("param"); 
                if(param !=null){
                    self.roleType = param.roleType;
                    self.multiple = param.multiple;    
                }
                
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: self.roleType,
                    multiple: self.multiple
                });
            }

            /** Start page */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.component.startPage().done(function(){
                    dfd.resolve();    
                });
                return dfd.promise();
            }//end start page
            
            /** btn decision*/
            decision(){
                let self = this;
                nts.uk.ui.windows.setShared("userId", self.component.currentCode());
                var list = self.component.listUser();
                self.findName(list,self.component.currentCode());
                nts.uk.ui.windows.setShared("userName", self.findName(list,self.component.currentCode()));
                
                nts.uk.ui.windows.close();
            }
            
            findName(list: Array<any>, id: string): string{
                let name = '';
                for (let item of list) {
                    if(item.userID == id){
                        return item.userName;
                        break;
                    }
                }   
                return name;
                
            }
            /** btn cancel*/
            cancel(){
                nts.uk.ui.windows.close();
            }
            
        }//end screenModel
    }//end viewmodel
}//end module