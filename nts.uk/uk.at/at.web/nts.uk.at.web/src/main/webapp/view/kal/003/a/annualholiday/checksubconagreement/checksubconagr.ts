module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import dialog = nts.uk.ui.dialog;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class AnnualHolidaySubCon {
        
        narrowUntilNext: KnockoutObservable<boolean> = ko.observable(false);
        narrowLastDay: KnockoutObservable<boolean> = ko.observable(false);
        numberDayAward: KnockoutObservable<number> = ko.observable(null);
        periodUntilNext: KnockoutObservable<number> = ko.observable(null);
        
        constructor(alarmCheckSubConAg?: model.IAlarmCheckSubConAgr) {
            let self = this;
            if(alarmCheckSubConAg){
                this.narrowUntilNext(alarmCheckSubConAg.narrowUntilNext);
                this.narrowLastDay(alarmCheckSubConAg.narrowLastDay);
                this.numberDayAward(alarmCheckSubConAg.numberDayAward);
                this.periodUntilNext(alarmCheckSubConAg.periodUntilNext);
            }
        }

        loadData(alarmCheckSubConAg?: model.IAlarmCheckSubConAgr){
            let self = this;
            if(alarmCheckSubConAg){
                self.narrowUntilNext(alarmCheckSubConAg.narrowUntilNext);
                self.narrowLastDay(alarmCheckSubConAg.narrowLastDay);
                self.numberDayAward(alarmCheckSubConAg.numberDayAward);
                self.periodUntilNext(alarmCheckSubConAg.periodUntilNext);
            }else{
                self.narrowUntilNext(false);
                self.narrowLastDay(false);
                self.numberDayAward(null);
                self.periodUntilNext(null);
            }
        }
        
        createNewLine2(): void {
            let self = this;
        }

        deleteLine2(): void {
            let self = this;
        }
    }

}//end tab



