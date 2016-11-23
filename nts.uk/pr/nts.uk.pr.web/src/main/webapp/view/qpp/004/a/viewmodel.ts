module qpp004.viewmodel {
    export class ScreenModel {
        
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymetnProcessing: any;
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymetnProcessing = ko.observable(null);
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
            $.when(service.getPaymentDateProcessingMaster()).done(function(data) {
                
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
            alert('aaa');
        }
    }
}