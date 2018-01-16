module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class FixedCheckConditionTab {
        listFixedConditionWorkRecord: KnockoutObservableArray<model.FixedConditionWorkRecord> = ko.observableArray([]);
        isAllfixedCheck: KnockoutObservable<boolean> = ko.observable(false);
        category: KnockoutObservable<number>;

        constructor(category: number, listFixedConditionWorkRecord?: Array<model.FixedConditionWorkRecord>) {
            let self = this;
            self.category = ko.observable(category);

            if (listFixedConditionWorkRecord) {
                self.listFixedConditionWorkRecord.removeAll();
                self.listFixedConditionWorkRecord(listFixedConditionWorkRecord);
                for (var i = 0; i < self.listFixedConditionWorkRecord().length; i++) {
                    self.listFixedConditionWorkRecord()[i].rowId(i + 1);
                }
            }
            self.isAllfixedCheck.subscribe(function(value){
                 _.each(self.listFixedConditionWorkRecord(), (fixedConditionWorkRecord) => {
                    fixedConditionWorkRecord.useAtr(value);
                });   
            });
        }//end constructor
    }//end FixedCheckConditionTab
}//end tab



