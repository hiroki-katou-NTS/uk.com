module qpp004.l.viewmodel {
    export class ScreenModel {
        completeList: KnockoutObservableArray<any>;
        errorList: KnockoutObservableArray<any>;
        countError: KnockoutObservable<number>;
        buttonText: KnockoutObservable<string>;
        visibleErrorList: KnockoutObservable<boolean>;
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            
            self.completeList = ko.observableArray([]);
            self.errorList = ko.observableArray([]);
            self.countError = ko.computed(function(){
               return self.errorList.length; 
            });
            self.buttonText = ko.observable(null);
            
            self.visibleErrorList = ko.observable(false);
        }
        
        /**
         * Start page.
         * Load all data which is need for binding data.
         */
        startPage(data): JQueryPromise<any> {
            var self = this;
            
            // Page load dfd.
            var dfd = $.Deferred();
            var index = 0;            
            _.forEach(data.personIdList, function(personId){
                index = index  +1;
                
                self.buttonText("中止");
                
                var parameter = {
                    personId: personId.id,
                    processingNo: data.processingNo,
                    processingYearMonth: data.processingYearMonth
                };
                
                // Resolve start page dfd after load all data.
                $.when(qpp004.l.service.processCreatePaymentData(parameter)).done(function(data) {
                    self.completeList.push(personId);
                }).fail(function(res) {
                    self.visibleErrorList(true);
                    self.errorList.push({
                       personId: personId.id,
                       personName: personId.name, 
                       errorMessage: res.message
                    });
                });
               
                if (index == data.personIdList.length) {
                    self.buttonText("閉じる");    
                } 
                
                dfd.resolve();
            });
            
            
            return dfd.promise();
        }
                
        
    }
}