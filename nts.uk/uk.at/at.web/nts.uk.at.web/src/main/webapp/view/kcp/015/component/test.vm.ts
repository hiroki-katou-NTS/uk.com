module test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        hasParams: KnockoutObservable<boolean> = ko.observable(true);
        visibleA31: KnockoutObservable<boolean> = ko.observable(true);
        visibleA32: KnockoutObservable<boolean> = ko.observable(true);
        visibleA33: KnockoutObservable<boolean> = ko.observable(true);
        visibleA34: KnockoutObservable<boolean> = ko.observable(true);
        visibleA35: KnockoutObservable<boolean> = ko.observable(true);
        visibleA36: KnockoutObservable<boolean> = ko.observable(true);
        baseDate: KnockoutObservable<string> = ko.observable('');
        sids: KnockoutObservableArray<any> = ko.observableArray([]);


        constructor() {
            let self = this;

            let dataShare = getShared('dataShareKCP015');

            self.hasParams(dataShare.hasParams);
            self.visibleA31(dataShare.checkedA3_1);
            self.visibleA32(dataShare.checkedA3_2);
            self.visibleA33(dataShare.checkedA3_3);
            self.visibleA34(dataShare.checkedA3_4);
            self.visibleA35(dataShare.checkedA3_5);
            self.visibleA36(dataShare.checkedA3_6);
            self.sids(dataShare.listEmp);
            self.baseDate(dataShare.baseDate);

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        close() {
            nts.uk.ui.windows.close();
        }

    }
}
