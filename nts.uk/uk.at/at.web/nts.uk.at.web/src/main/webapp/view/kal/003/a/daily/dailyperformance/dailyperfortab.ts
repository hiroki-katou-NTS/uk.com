module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class DailyPerformanceTab {
        listWorkRecordExtraCon: KnockoutObservableArray<model.WorkRecordExtraCon> = ko.observableArray([]);
        isAllDailyPerfor: KnockoutObservable<boolean> = ko.observable(false);

        constructor(listWorkRecordExtraCon?: Array<model.WorkRecordExtraCon>) {
            let self = this;

            if (listWorkRecordExtraCon) {
                self.listWorkRecordExtraCon.removeAll();
                self.listWorkRecordExtraCon(listWorkRecordExtraCon);
            }
            
            self.isAllDailyPerfor = ko.pureComputed({
                read: function () {
                    let l = self.listWorkRecordExtraCon().length;
                    if (self.listWorkRecordExtraCon().filter((x) => {return x.useAtr()}).length == l && l > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                write: function (value) {
                    for (var i = 0; i < self.listWorkRecordExtraCon().length; i++) {
                        self.listWorkRecordExtraCon()[i].useAtr(value);
                    }
                },
                owner: self
            });
        }//end constructor
    }//end DailyPerformanceTab
}//end tab



