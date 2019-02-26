module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import dialog = nts.uk.ui.dialog;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class AnnualHolidayCon {
        
        distByPeriod: KnockoutObservable<boolean> = ko.observable(false);
        displayMessage: KnockoutObservable<string> = ko.observable("");
        usageObliDay: KnockoutObservable<number> = ko.observable(null);
        
        constructor() {
            let self = this;
        }

        loadData(alarmCheckConAg?: model.IAlarmCheckConAgr){
            let self = this;
            if(alarmCheckConAg){
                self.distByPeriod(alarmCheckConAg.distByPeriod);
                self.displayMessage(alarmCheckConAg.displayMessage);
                self.usageObliDay(alarmCheckConAg.usageObliDay);
            }else{
                self.distByPeriod(false);
                self.displayMessage("");
                self.usageObliDay(null);
            }
        }
        
        createNewLine1(): void {
            let self = this;
        }

        deleteLine1(): void {
            let self = this;
        }
    }

}//end tab



