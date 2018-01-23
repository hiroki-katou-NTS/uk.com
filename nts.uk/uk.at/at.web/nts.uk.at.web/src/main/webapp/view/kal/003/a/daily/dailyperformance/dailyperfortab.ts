module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class DailyPerformanceTab {
        listWorkRecordExtraCon: KnockoutObservableArray<model.DailyErrorAlarmCheck> = ko.observableArray([
            new model.DailyErrorAlarmCheck('S001', 'name S001', 'AL', 'message s001'),
            new model.DailyErrorAlarmCheck('S002', 'name S002', 'ER', 'message s002'),
            new model.DailyErrorAlarmCheck('S003', 'name S003', 'AL', 'message s003'),
            new model.DailyErrorAlarmCheck('S004', 'name S004', 'ER', 'message s004')
        ]);
        currentCodeList: KnockoutObservableArray<string>;
        addApplication: KnockoutObservable<boolean> = ko.observable(true);

        constructor(listWorkRecordExtraCon?: Array<model.DailyErrorAlarmCheck>) {
            let self = this;

            if (listWorkRecordExtraCon) {
                self.listWorkRecordExtraCon.removeAll();
                self.listWorkRecordExtraCon(listWorkRecordExtraCon);
            }

            self.listWorkRecordExtraCon.subscribe((data: Array<model.DailyErrorAlarmCheck>) => {
                for (var i = 1; i <= data.length; i++){
                    if ($( "table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)" ).text() == "ER") {
                        $( "table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)" ).css("background-color", "red");
                    }
                    if ($( "table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)" ).text() == "AL") {
                        $( "table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)" ).css("background-color", "yellow");
                    }
                }
            });
            this.currentCodeList = ko.observableArray([]);
        }//end constructor
    }//en            b
}//end tab



