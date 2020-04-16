module test.viewmodel {

    export class ScreenModel {
        options: Option;
        currentIds: KnockoutObservable<any> = ko.observable([]);
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        currentNames: KnockoutObservable<any> = ko.observable([]);
        alreadySettingList: KnockoutObservableArray<any> = ko.observable(['1']);
        constructor() {
            let self = this;
            self.options = {
                // neu muon lay code ra tu trong list thi bind gia tri nay vao
                currentCodes: self.currentCodes,
                currentNames: self.currentNames,
                // tuong tu voi id
                currentIds: self.currentIds,
                //
                multiple: true,
                tabindex:2,
                isAlreadySetting: true,
                alreadySettingList: self.alreadySettingList,
                // show o tim kiem
                showSearch: true,
                // show empty item
                showEmptyItem: true,
                // trigger reload lai data cua component
                reloadData: ko.observable(''),
                height: 400,
                // NONE = 0, FIRST = 1, ALL = 2
                selectedMode: 1
            };
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        testAlreadySetting() {
            let self = this;
            self.alreadySettingList(['random1', 'random2', 'random3', 'random4']);
            self.options.reloadData.valueHasMutated();
        }

    }
}