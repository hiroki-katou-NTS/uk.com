module nts.uk.at.view.kmf022.l.viewmodel {
    export class ScreenModel {
        listComponentOption: any;
        selectedCode: KnockoutObservable<string> = ko.observable('');
        alreadySettingList: KnockoutObservableArray<any>;
        textValue: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
            self.alreadySettingList = ko.observableArray([
                                                        {code: '1', isAlreadySetting: true},
                                                        {code: '2', isAlreadySetting: true}
                                                    ]);
            self.listComponentOption = {
                isShowAlreadySet: true,
                isMultiSelect: false,
                listType: 1,
                selectType: 1,
                selectedCode: self.selectedCode,
                isDialog: false,
                isShowNoSelectRow: true,
                alreadySettingList: self.alreadySettingList,
                maxRows: 12
            };
        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
            
            dfd.resolve();
            
            return dfd.promise();
        }
        
    }

}