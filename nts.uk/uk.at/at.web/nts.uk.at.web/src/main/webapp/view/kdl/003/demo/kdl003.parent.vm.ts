module kdl003.parent.viewmodel {
    export class ScreenModel {
        //codes from parent screen
        canSelectWorkTypeCodes: KnockoutObservableArray<string>;
        selectWorkTypeCode: KnockoutObservable<string>;
        canSelectSiftCodes: KnockoutObservableArray<string>;
        selectSiftCode: KnockoutObservable<string>;

        childSelectWorkTypeCode: KnockoutObservable<string>;
        childSelectWorkTypeName: KnockoutObservable<string>;
        childSelectSiftCode: KnockoutObservable<string>;
        childSelectSiftName: KnockoutObservable<string>;
        constructor() {
            var self = this;
            //construct codes 
            self.canSelectWorkTypeCodes = ko.observableArray(['001', '002', '003', '004', '005', '006', '007', '008', '009', '010']);
            self.selectWorkTypeCode = ko.observable('002');
            self.canSelectSiftCodes = ko.observableArray(['AAA', 'AAD', 'AAG', 'AAI']);
            self.selectSiftCode = ko.observable('AAG');

            self.childSelectWorkTypeCode = ko.observable('');
            self.childSelectWorkTypeName = ko.observable('');
            self.childSelectSiftCode = ko.observable('');
            self.childSelectSiftName = ko.observable('');
        }
        //open dialog 003 
        OpenDialog003() {
            var self = this;
            nts.uk.ui.windows.setShared('parentCodes', {
                canSelectWorkTypeCodes: self.canSelectWorkTypeCodes(),
                selectWorkTypeCode: self.selectWorkTypeCode(),
                canSelectSiftCodes: self.canSelectSiftCodes(),
                selectSiftCode: self.selectSiftCode()
            }, true);

            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml', { title: '勤務就業選択', }).onClosed(function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared('childData');
                self.childSelectWorkTypeCode(childData.selectedWorkTimeCode);
                self.childSelectWorkTypeName(childData.selectedWorkTimeName);
                self.childSelectSiftCode(childData.selectedSiftCode);
                self.childSelectSiftName(childData.selectedSiftName);
            })
        }
    }
}
