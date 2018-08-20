module nts.uk.at.view.kmk013.l {
    
    import service = nts.uk.at.view.kmk013.l.service;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            
            valueL2_7: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            valueL2_9: KnockoutObservable<number>;
              
            
            constructor() {
                let self = this;
                self.valueL2_7 = ko.observable(0);
                self.valueL2_9 = ko.observable(0);
            }
            
            loadData(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                
                $.when(service.find()).done(function(data: any) {
                    if (!_.isUndefined(data)) {
                        self.valueL2_7(data.maxUsage);
                        self.valueL2_9(data.timeTreatTemporarySame);    
                    }
                    dfd.resolve();
                });
                    
                return dfd.promise();
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                
                $.when(self.loadData()).done(function() {
                    dfd.resolve();
                });
                    
                return dfd.promise();
            }
            
            save(): JQueryPromise<any> {
                var dfd = $.Deferred();
                let self = this;
                let obj = <any>{};
                obj.maxUsage = self.valueL2_7();
                obj.timeTreatTemporarySame = self.valueL2_9();
                if (nts.uk.ui.errors.hasError()) {
                    blockUI.clear();
                    return;
                }
                
                $.when(service.save(obj)).done(function(data: any) {
                    nts.uk.ui.dialog.info({messageId: 'Msg_15'});
                    $.when(self.loadData()).done(function() {
                        blockUI.clear();
                        dfd.resolve();
                    });
                    $( "#l2_7" ).focus();
                }).always(() => {
                });
                    
                return dfd.promise();
            }
        }
    }
}