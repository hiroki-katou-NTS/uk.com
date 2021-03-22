module kdl003.parent.viewmodel {
    export class ScreenModel {
        //codes from parent screen
        canSelectWorkTypeCodes: KnockoutObservable<string>;
        selectWorkTypeCode: KnockoutObservable<string>;
        canSelectSiftCodes: KnockoutObservable<string>;
        selectSiftCode: KnockoutObservable<string>;
        showNone: KnockoutObservable<boolean>;

        childSelectWorkTypeCode: KnockoutObservable<string>;
        childSelectWorkTypeName: KnockoutObservable<string>;
        childSelectSiftCode: KnockoutObservable<string>;
        childSelectSiftName: KnockoutObservable<string>;
        firstStartTime: KnockoutObservable<number>;
        firstEndTime : KnockoutObservable<number>;
        secondStartTime: KnockoutObservable<number>;
        secondEndTime: KnockoutObservable<number>;
        first: time;
        second: time;
        remark:KnockoutObservable<string>;
        workPlaceId: KnockoutObservable<string> = ko.observable(null);
        baseDate: KnockoutObservable<string> = ko.observable(null);
        workTimeSetting: KnockoutObservable<number> = ko.observable(null);

        constructor() {
            var self = this;
            //construct codes 
            self.canSelectWorkTypeCodes = ko.observable('001,002,003,004,005,006,007,008,009,010');
            self.selectWorkTypeCode = ko.observable('002');
            self.canSelectSiftCodes = ko.observable('');
            self.selectSiftCode = ko.observable('324');
            self.showNone = ko.observable(true);

            self.childSelectWorkTypeCode = ko.observable('');
            self.childSelectWorkTypeName = ko.observable('');
            self.childSelectSiftCode = ko.observable('');
            self.childSelectSiftName = ko.observable('');
            self.firstStartTime = ko.observable(null);
            self.firstEndTime = ko.observable(null);
            self.secondStartTime = ko.observable(null);
            self.secondEndTime = ko.observable(null);
            self.remark = ko.observable(null);
            self.first = <time>{};
            self.second = <time>{};
        }
        //open dialog 003 
        OpenDialog003() {
            let self = this;
            let workTypeCodes = self.canSelectWorkTypeCodes() ? self.canSelectWorkTypeCodes().split(',') : [];
            let workTimeCodes = self.canSelectSiftCodes() ? self.canSelectSiftCodes().split(',') : [];
            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: self.selectWorkTypeCode(),
                workTimeCodes: workTimeCodes,
                selectedWorkTimeCode: self.selectSiftCode(),
                showNone: self.showNone(),
                workPlaceId: self.workPlaceId(),
                baseDate: self.baseDate()
            }, true);

            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
                    self.childSelectWorkTypeCode(childData.selectedWorkTypeCode);
                    self.childSelectWorkTypeName(childData.selectedWorkTypeName);
                    self.childSelectSiftCode(childData.selectedWorkTimeCode);
                    self.childSelectSiftName(childData.selectedWorkTimeName);
                    self.firstStartTime(childData.first.start);
                    self.firstEndTime(childData.first.end);
                    self.secondStartTime(childData.second.start);
                    self.secondEndTime(childData.second.end);
                    self.remark(childData.remark);
                    self.workTimeSetting(childData.workTimeSetting);
                }
            })
        }
        
    }
     interface time {
        start: number;
        end: number;
    }
}
