module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class DailyPerformanceTab {
        listWorkRecordExtraCon: KnockoutObservableArray<model.WorkRecordExtraCon> = ko.observableArray([]);
        isAllDailyPerfor: KnockoutObservable<boolean> = ko.observable(false);
        category: KnockoutObservable<number>;

        constructor(category: number, listWorkRecordExtraCon?: Array<model.WorkRecordExtraCon>) {
            let self = this;
            self.category = ko.observable(category);

            if (listWorkRecordExtraCon) {
                self.listWorkRecordExtraCon.removeAll();
                self.listWorkRecordExtraCon(listWorkRecordExtraCon);
                for (var i = 0; i < self.listWorkRecordExtraCon().length; i++) {
                    self.listWorkRecordExtraCon()[i].rowId(i + 1);
                }
            }
            self.isAllDailyPerfor.subscribe(function(value){
                 _.each(self.listWorkRecordExtraCon(), (workRecordExtraCon) => {
                    workRecordExtraCon.useAtr(value);
                });   
            });
        }//end constructor
    }//end DailyPerformanceTab
}//end tab



