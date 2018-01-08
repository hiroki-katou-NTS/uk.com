module nts.uk.at.view.ksm011.d.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
//    import ccg = nts.uk.com.view.ccg025.a;
//    import model = nts.uk.com.view.ccg025.a.component.viewmodel;

    export class ScreenModel {
        component: ccg.component.viewmodel.ComponentModel;
        listRole : KnockoutObservableArray<model.Role>;

        constructor() {
            let self = this;
//            self.component = new ccg.component.viewmodel.ComponentModel({ 
//                roleType: 1,
//                multiple: true
//            });
//            self.listRole = ko.observableArray([]);
            
            
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
//            self.component.startPage().done(() => {
//                self.listRole(self.component.listRole());
//                dfd.resolve();    
//            });
            
            return dfd.promise();
        }
        
        /**
         * Registration function.
         */
        registration() {
            var self = this;
            
        }
    }
}
