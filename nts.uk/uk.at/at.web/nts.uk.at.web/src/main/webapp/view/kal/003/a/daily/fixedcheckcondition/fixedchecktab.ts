module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class FixedCheckConditionTab {
        listFixedConditionWorkRecord: KnockoutObservableArray<model.FixedConditionWorkRecord> = ko.observableArray([
            new model.FixedConditionWorkRecord({ errorAlarmId: "00000000001", checkName: "name 1", fixConWorkRecordNo: 1, message: "xxxx", useAtr: false }), 
            new model.FixedConditionWorkRecord({ errorAlarmId: "00000000002", checkName: "name 2", fixConWorkRecordNo: 2, message: "xxxx", useAtr: false }),
            new model.FixedConditionWorkRecord({ errorAlarmId: "00000000003", checkName: "name 3", fixConWorkRecordNo: 3, message: "xxxx", useAtr: false }),
            new model.FixedConditionWorkRecord({ errorAlarmId: "00000000004", checkName: "name 4", fixConWorkRecordNo: 4, message: "xxxx", useAtr: false }),
            new model.FixedConditionWorkRecord({ errorAlarmId: "00000000005", checkName: "name 5", fixConWorkRecordNo: 5, message: "xxxx", useAtr: false })
        ]);
        isAllfixedCheck: KnockoutObservable<boolean> = ko.observable(false);

        constructor(listFixedConditionWorkRecord?: Array<model.FixedConditionWorkRecord>) {
            let self = this;

            if (listFixedConditionWorkRecord) {
                self.listFixedConditionWorkRecord.removeAll();
                self.listFixedConditionWorkRecord(listFixedConditionWorkRecord);
                for (var i = 0; i < self.listFixedConditionWorkRecord().length; i++) {
                    self.listFixedConditionWorkRecord()[i].fixConWorkRecordNo(i + 1);
                }
            }
            
            self.isAllfixedCheck = ko.pureComputed({
                read: function () {
                    let l = self.listFixedConditionWorkRecord().length;
                    if (self.listFixedConditionWorkRecord().filter((x) => {return x.useAtr()}).length == l && l > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                write: function (value) {
                    for (var i = 0; i < self.listFixedConditionWorkRecord().length; i++) {
                        self.listFixedConditionWorkRecord()[i].useAtr(value);
                    }
                },
                owner: self
            });
        }//end constructor
    }//end FixedCheckConditionTab
}//end tab



