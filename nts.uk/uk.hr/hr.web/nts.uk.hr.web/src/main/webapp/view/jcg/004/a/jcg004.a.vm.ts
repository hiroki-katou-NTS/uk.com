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
                block.clear();
                dfd.resolve();
            });  
            return dfd.promise();
        }
        
        getInfor(): void{
            var self = this;
            block.invisible();
            new service.Service().getOptionalWidgetInfo(param).done(function(data: any){
                block.clear();
            });           
        }
        
        openJHN003A(): void{
            var self = this;
            block.invisible();
            block.clear();
        }
        
        openJHN007B(): void{
            var self = this;
            block.invisible();
            block.clear();
        }
        
        
    }
    
}

