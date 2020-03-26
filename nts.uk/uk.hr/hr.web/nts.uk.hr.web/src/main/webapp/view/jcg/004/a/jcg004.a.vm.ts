module nts.uk.at.view.jcg004.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    export class ScreenModel {
        approvalOfApplication: KnockoutObservable<boolean>;
        businessApproval: KnockoutObservableArray<any>;
        check: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.approvalOfApplication = ko.observable(false); 
            self.businessApproval = ko.observableArray([]);
            self.check = ko.observable(false);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            new service.Service().start().done(function(data: any){
                console.log(data);
                self.approvalOfApplication(data.approvalOfApplication);
                self.businessApproval(data.businessApproval);
                _.forEach(data.businessApproval, (item) => {
                    if(item.check){
                        self.check(true);
                    }
                });
                dfd.resolve();
            }).always(() => {
                block.clear();
            });  
            return dfd.promise();
        }
        
        openJHN003A(): void{
            var self = this;
            block.invisible();
            let param = {
                appDate:{ startDate: moment.utc(new Date()).add(-1, 'M').toDate(), endDate: moment.utc(new Date()).add(1, 'M').toDate() },
                approvalReport: true,
                approvalStatus: '1'
            }
            nts.uk.characteristics.remove("JHN003").done(function() {
                parent.nts.uk.characteristics.save('JHN003', param).done(function() {
                    parent.nts.uk.ui.block.clear();
                    window.top.location = window.location.origin + '/nts.uk.hr.web/view/jhn/003/a/index.xhtml';
                });    
            }); 
        }
        
        openJHN007Z(): void{
            var self = this;
            window.top.location = window.location.origin + '/nts.uk.hr.web/view/jcm/007/z/index.xhtml';
        }
        
    }
    
}

