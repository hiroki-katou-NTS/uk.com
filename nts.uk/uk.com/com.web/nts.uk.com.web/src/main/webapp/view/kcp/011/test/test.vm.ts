module test.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        options: Option;
        currentIds: KnockoutObservable<any> = ko.observable([]);
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        currentNames: KnockoutObservable<any> = ko.observable([]);
        alreadySettingList: KnockoutObservableArray<any> = ko.observable(['1']);
        multiple: KnockoutObservable<any> = ko.observable(true);
        isAlreadySetting: KnockoutObservable<any> = ko.observable(true);
        showEmptyItem: KnockoutObservable<any> = ko.observable(true);
        selectedMode: KnockoutObservable<any> = ko.observable(1);

        //selectType 
        listSelectionType: KnockoutObservableArray<any>;
        enable: KnockoutObservable<boolean> = ko.observable(true);
        selectedSelectionType: KnockoutObservable<number> = ko.observable(true);
        //TreeType
        selectedTreeType: KnockoutObservable<number> = ko.observable(1);
        selectedOther: KnockoutObservable<number> = ko.observable(1);
        selectedSetting: KnockoutObservable<number> = ko.observable(1);
        listSetting: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
            self.options = {
                // neu muon lay code ra tu trong list thi bind gia tri nay vao
                currentCodes: self.currentCodes,
                currentNames: self.currentNames,
                // tuong tu voi id
                currentIds: self.currentIds,
                //
                multiple: self.multiple(),
                tabindex: 2,
                isAlreadySetting: self.isAlreadySetting(),
                alreadySettingList: self.alreadySettingList,
                // show o tim kiem
                showSearch: true,
                // show empty item
                showEmptyItem: self.showEmptyItem(),
                // trigger reload lai data cua component
                reloadData: ko.observable(''),
                reloadComponent: ko.observable({}),
                height: 400,
                // NONE = 0, FIRST = 1, ALL = 2
                selectedMode: self.selectedMode()
            };
            self.listSelectionType = ko.observableArray([
                { code: 1, name: 'Select by selected code', enable: self.enable },
                { code: 2, name: 'Select all', enable: self.isMultipleTreeGrid },
                { code: 3, name: 'Select first item', enable: self.enable },
                { code: 4, name: 'No select', enable: self.enable }
            ]);
            self.listTreeType = ko.observableArray([
                { code: 0, name: 'Single tree select grid' },
                { code: 1, name: 'Multiple tree select grid' }
            ]);
            self.listOther = ko.observableArray([
                { code: 0, name: 'Single select' },
                { code: 1, name: 'Multiple select' }
            ]);
            self.listSetting = ko.observableArray([
                { code: 0, name: 'Yes' },
                { code: 1, name: 'No' }
            ]);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        testAlreadySetting() {
            let self = this;
            let data = {
                multiple: self.multiple(),
                isAlreadySetting: self.isAlreadySetting(),
                showEmptyItem: self.showEmptyItem(),
                selectedMode: self.selectedMode()
            }
            setShared('KCP011_TEST', data);
            modal("/view/kcp/011/test2/index.xhtml").onClosed(() => {

            });
        }

    }
}