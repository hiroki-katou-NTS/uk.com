module nts.uk.at.view.jcg004.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    export class ScreenModel {
        approvalOfApplication: KnockoutObservable<boolean>;
        businessApproval: KnockoutObservableArray<boolean>;
        constructor() {
            var self = this;
            self.approvalOfApplication = ko.observable(false); 
            self.businessApproval = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            new service.Service().start().done(function(data: any){
                console.log(data);
                self.approvalOfApplication(data.approvalOfApplication);
                self.businessApproval(data.businessApproval);
                block.clear();
                dfd.resolve();
            });  
            return dfd.promise();
        }
        
        openJHN003A(): void{
            var self = this;
            block.invisible();
            window.top.location = window.location.origin + '/nts.uk.hr.web/view/jhn/003/a/index.xhtml';
            block.clear();
        }
        
        openJHN007B(): void{
            var self = this;
            block.invisible();
            window.top.location = window.location.origin + '/nts.uk.hr.web/view/jcm/007/b/index.xhtml';
            block.clear();
        }
        
    }
    
}

