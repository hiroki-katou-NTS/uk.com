module nts.uk.at.view.kdl049.a.viewmodel {
    
    import invisible = nts.uk.ui.block.invisible;
    import alertError = nts.uk.ui.dialog.alertError;
    import clear = nts.uk.ui.block.clear;
    import getText = nts.uk.resource.getText;
    
    export class ScreenModel {

        targetDate:  KnockoutObservable<string> = ko.observable("");
        comEventName:  KnockoutObservable<string> = ko.observable("");
        isSuperior:  KnockoutObservable<boolean> = ko.observable(false);
        targetWorkplace:  KnockoutObservable<string> = ko.observable("");
        wplEventName:  KnockoutObservable<string> = ko.observable("");
    
        constructor() {
            var self = this;
           
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;

            let data = nts.uk.ui.windows.getShared('KDL049');
            if (data.workplace != null) {
                self.targetWorkplace(data.workplace.workPlaceName);
            }
            var dfd = $.Deferred();
            if(self.targetWorkplace().length > 0) {
                $('head').append("<script>var dialogSize = { width: 350, height: 320 };<\/script>"); 
            } else {
                $('head').append("<script>var dialogSize = { width: 350, height: 250 };<\/script>");
            }
            
            dfd.resolve();
           
            return dfd.promise();
        }
        
        closeDialog() {
            nts.uk.ui.windows.close();
          }
    }
}