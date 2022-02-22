/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module test.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    @bean()
    export class ScreenModel extends ko.ViewModel {
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
        selectedSelectionType: KnockoutObservable<number> = ko.observable(0);
        //TreeType
        selectedTreeType: KnockoutObservable<number> = ko.observable(0);
        selectedOther: KnockoutObservable<number> = ko.observable(0);
        selectedSetting: KnockoutObservable<number> = ko.observable(0);
        listSetting: KnockoutObservableArray<any>;
        // panel
        listPanelSetting: KnockoutObservableArray<any>;
        selectedPanel: KnockoutObservable<number> = ko.observable(1);
        constructor() {
            super();
            let self = this;
            self.listSelectionType = ko.observableArray([
                { code: 2, name: 'Select all', enable: self.isMultipleTreeGrid },
                { code: 1, name: 'Select first item', enable: self.enable },
                { code: 0, name: 'No select', enable: self.enable },
                { code: 3, name: 'Select follow ID', enable: self.enable }
            ]);
            self.listTreeType = ko.observableArray([
                { code: 0, name: 'Single tree select grid' },
                { code: 1, name: 'Multiple tree select grid' }
            ]);
            self.listOther = ko.observableArray([
                { code: 1, name: 'Show empty iteam' },
                { code: 0, name: 'Not show' }
            ]);
            self.listSetting = ko.observableArray([
                { code: 1, name: 'Yes' },
                { code: 0, name: 'No' }
            ]);

            self.listPanelSetting = ko.observableArray([
                { code: 1, name: 'Yes' },
                { code: 0, name: 'No' }
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
                multiple: self.selectedTreeType() == 1 ? true : false,
                isAlreadySetting: self.selectedSetting() == 1 ? true : false,
                showEmptyItem: self.selectedOther() == 1 ? true : false,
                selectedMode: self.selectedSelectionType(),
                alreadySettingList: self.currentIds(),
                panelSetting: self.selectedPanel() == 1 ? true : false
            }
            setShared('KCP011_TEST', data);
            modal("/view/cdl/014/a/index.xhtml").onClosed(() => {
                
            });
        }

    }
}