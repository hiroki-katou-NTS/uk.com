module nts.uk.at.view.ksm011.c.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {
        controlUseCls: KnockoutObservableArray<any>;
        selectedControlUse: KnockoutObservable<number>;
        workTypeList: KnockoutObservable<string>;
        openDialogEnable: KnockoutObservable<boolean>;
        workTypeListEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            
            self.controlUseCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);
            
            self.selectedControlUse = ko.observable(0);
            self.workTypeList = ko.observable("");
            self.openDialogEnable = ko.observable(true);
            self.workTypeListEnable = ko.observable(true);
            
            self.selectedControlUse.subscribe(function(value) {
                if(value == 0) {
                    self.openDialogEnable(true);
                    self.workTypeListEnable(true);
                } else {
                    self.openDialogEnable(false);
                    self.workTypeListEnable(false);
                }
            });
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            

            return dfd.promise();
        }
        
        /**
         * Registration function.
         */
        registration() {
            var self = this;
            
        }
        
        openDialog() {
            var self = this;
            
            nts.uk.ui.windows.sub.modal("/view/kdl/002/a/index.xhtml").onClosed(() => {
                
            });
        }
    }
}
