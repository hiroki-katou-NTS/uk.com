module nts.uk.at.view.kmk005.d {
    export module viewmodel {
        export class ScreenModel {
            useWorkPlace: KnockoutObservable<boolean>;
            usePerson: KnockoutObservable<boolean>;
            useWorkTimeSheet: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                 self.useWorkPlace=ko.observable(true);
                    self.usePerson=ko.observable(true);
                    self.useWorkTimeSheet=ko.observable(true);
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
              service.getSetting().done(function(item){
                  if (item !== undefined || item.length != 0){
                      self.useWorkPlace(item.workplaceUseAtr);
                      self.usePerson(item.personalUseAtr);
                      self.useWorkTimeSheet(item.workingTimesheetUseAtr); 
                  }
              });
             
                
                return dfd.promise();
            }
            
            submitAndCloseDialog(): void {
                var self = this;
                
                service.updateSetting({
                    workplaceUseAtr: self.useWorkPlace()?1:0,
                    personalUseAtr: self.usePerson()?1:0,
                    workingTimesheetUseAtr: self.useWorkTimeSheet()?1:0
                });
                
                self.closeDialog();
                }
             closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            
        }      
    }
}