module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class AlarmcheckTab {
        listFixedExtraMonFun: KnockoutObservableArray<model.FixedExtraMonFun> = ko.observableArray([]);
        isAllfixedCheck: KnockoutObservable<boolean> = ko.observable(false);

        constructor(listFixedExtraMonFun?: Array<model.FixedExtraMonFun>) {
            let self = this;
            $('.fixedextramonfun').addClass("limited-label");
            service.getAllFixedExtraItemMon().done((data: Array<any>) => {
                if (data && data.length) {
                    let _list: Array<model.FixedExtraMonFun> = _.map(data, acc => {
                        return new model.FixedExtraMonFun({ monAlarmCheckID: "", monAlarmCheckName: acc.fixedExtraItemMonName, fixedExtraItemMonNo: acc.fixedExtraItemMonNo, message: acc.message, useAtr: false });
                    });
                    self.listFixedExtraMonFun(_list);
                }
            });
            if (listFixedExtraMonFun) {
                self.listFixedExtraMonFun.removeAll();
                self.listFixedExtraMonFun(listFixedExtraMonFun);
                for (var i = 0; i < self.listFixedExtraMonFun().length; i++) {
                    self.listFixedExtraMonFun()[i].fixedExtraItemMonNo(i + 1);
                }
            }
    
           self.isAllfixedCheck = ko.pureComputed({
                read: function () {
                    let l = self.listFixedExtraMonFun().length;
                    if (self.listFixedExtraMonFun().filter((x) => {return x.useAtr()}).length == l && l > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                write: function (value) {
                    for (var i = 0; i < self.listFixedExtraMonFun().length; i++) {
                        self.listFixedExtraMonFun()[i].useAtr(value);
                    } 
                },
                owner: self
            });

            $("#fixed-table1").ntsFixedTable({ height: 192 });  
        }//end constructor
    }//end FixedCheckConditionTab
}//end tab