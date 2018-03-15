module nts.uk.at.view.kmk011.g {
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    import CreateHistoryCommand = nts.uk.at.view.kmk011.g.model.CreateHistoryCommand;
    
    export module viewmodel {
        export class ScreenModel {
            //date range
            enableDateRange: KnockoutObservable<boolean>;
            requiredDateRange: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            
            constructor(){
                let _self = this;
                
                //date range
                _self.enableDateRange = ko.observable(true);
                _self.requiredDateRange = ko.observable(true);
                
                _self.startDateString = ko.observable("");
                _self.endDateString = ko.observable("");
                _self.dateValue = ko.observable({});
                
                _self.startDateString.subscribe(function(value){
                    _self.dateValue().startDate = value;
                    _self.dateValue.valueHasMutated();        
                });
                
                _self.endDateString.subscribe(function(value){
                    _self.dateValue().endDate = value;   
                    _self.dateValue.valueHasMutated();      
                });  
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                service.findByHistoryId(nts.uk.ui.windows.getShared('history')).done((res: CreateHistoryCommand) => {
                    _self.startDateString(res.startDate);
                    _self.endDateString(res.endDate);
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            public execution() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                var data = new CreateHistoryCommand(nts.uk.ui.windows.getShared('history'),_self.dateValue().startDate, _self.dateValue().endDate);
                
                service.save(data).done(() => {
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        dfd.resolve();
                     });
                });
              
                return dfd.promise();
            }
            
            // close modal
            public close() : void {
                nts.uk.ui.windows.close();    
            }
        }
    }
}