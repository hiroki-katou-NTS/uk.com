module qpp004.viewmodel {
    export class ScreenModel {
        
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymetnProcessing: KnockoutObservable<any>;
        displayCurrentYearMonthProcessing: KnockoutObservable<string>;
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymetnProcessing = ko.observable(null);
            self.displayCurrentYearMonthProcessing = ko.observable(null);
            
            self.selectedPaymetnProcessing.subscribe(function(newValue) {
                var currentDateMaster = _.find(self.paymentDateProcessingList(), function(item) {
                    return item.processingNo == newValue;
                });
//                var year = currentDateMaster.currentProcessingYm.substring(0, 3);
//                var month = currentDateMaster.currentProcessingYm.substring(4, 5);
                self.displayCurrentYearMonthProcessing(currentDateMaster.currentProcessingYm);
            });
        }
        
        /**
         * Start page.
         * Load all data which is need for binding data.
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            
            // Page load dfd.
            var dfd = $.Deferred();
            
            // Resolve start page dfd after load all data.
            $.when(qpp004.service.getPaymentDateProcessingMaster()).done(function(data) {
                
                self.paymentDateProcessingList(data);
                
                dfd.resolve();
                
            }).fail(function(res) {
                
            });
            
            return dfd.promise();
        }
        
        /**
         * Redirect to page process create data
         */
        redirectToCreateData(): any {
            nts.uk.request.jump("view/qmm/qpp/004/b/index.xhtml");
        }
    }
}