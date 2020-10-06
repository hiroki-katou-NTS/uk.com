module test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        
        haveData  : KnockoutObservable<boolean>;
        visibleA31: KnockoutObservable<boolean>; // 年休の使用区分                             
        visibleA32: KnockoutObservable<boolean>; // 積立年休使用区分
        visibleA33: KnockoutObservable<boolean>; // 60H超休使用区分
        visibleA34: KnockoutObservable<boolean>; // 振休使用区分
        visibleA35: KnockoutObservable<boolean>; // 代休使用区分
        visibleA36: KnockoutObservable<boolean>;
        constructor() {
            let self = this;
            let dataShare = getShared('dataShareKCP015');
            
            self.haveData   = ko.observable(dataShare.haveData);
            self.visibleA31 = ko.observable(dataShare.checkedA3_1);
            self.visibleA32 = ko.observable(dataShare.checkedA3_2);
            self.visibleA33 = ko.observable(dataShare.checkedA3_3);
            self.visibleA34 = ko.observable(dataShare.checkedA3_4);
            self.visibleA35 = ko.observable(dataShare.checkedA3_5);
            self.visibleA36 = ko.observable(dataShare.checkedA3_6);
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
        
        OpenDialog0022(){
            var self = this;
        }
    }
}
