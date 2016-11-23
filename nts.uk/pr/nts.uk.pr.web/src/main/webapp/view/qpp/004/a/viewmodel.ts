module qpp004.a.viewmodel {
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
                var currentYearMonth = currentDateMaster.currentProcessingYm + "";
                var year = currentYearMonth.substring(0, 4);
                var month = currentYearMonth.substring(4, 6);
                self.displayCurrentYearMonthProcessing(year + "/" + month);
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
            $.when(qpp004.a.service.getPaymentDateProcessingMaster()).done(function(data) {
                
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
            nts.uk.request.jump("/view/qpp/004/b/index.xhtml");
        }
    }
}