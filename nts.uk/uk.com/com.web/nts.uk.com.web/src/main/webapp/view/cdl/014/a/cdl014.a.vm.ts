/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cdl014.a {
    import getShared = nts.uk.ui.windows.getShared;
    import Option = nts.uk.com.view.kcp011.share.Option;
    @bean()
    export class ScreenModel  extends ko.ViewModel  {
        options: Option;
        currentIds: KnockoutObservable<any> = ko.observable([]);
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        currentNames: KnockoutObservable<any> = ko.observable([]);
        alreadySettingList: KnockoutObservableArray<any> = ko.observableArray(['1']);
        constructor() {
            super();
            let self = this;
            let dataShare = getShared('KCP011_TEST')
            self.options = {
                // neu muon lay code ra tu trong list thi bind gia tri nay vao
                currentCodes: self.currentCodes,
                currentNames: self.currentNames,
                // tuong tu voi id
                currentIds: self.currentIds,
                //
                multiple: dataShare.multiple,
                tabindex:2,
                isAlreadySetting: dataShare.isAlreadySetting,
                alreadySettingList: ko.observableArray(dataShare.alreadySettingList),
                // show o tim kiem
                showSearch: true,
                showPanel: dataShare.panelSetting,
                // show empty item
                showEmptyItem: dataShare.showEmptyItem,
                // trigger reload lai data cua component
                reloadData: ko.observable(''),
                reloadComponent: ko.observable({}),
                height: 370,
                // NONE = 0, FIRST = 1, ALL = 2
                selectedMode: dataShare.selectedMode
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
            self.options.reloadComponent({
                // neu muon lay code ra tu trong list thi bind gia tri nay vao
                currentCodes: self.currentCodes,
                currentNames: self.currentNames,
                // tuong tu voi id
                currentIds: self.currentIds,
                //
                multiple: false,
                tabindex:2,
                isAlreadySetting: true,
                alreadySettingList: self.alreadySettingList,
                // show o tim kiem
                showSearch: true,
                // show empty item
                showEmptyItem: false,
                // trigger reload lai data cua component
                reloadData: ko.observable(''),
                height: 395,
                // NONE = 0, FIRST = 1, ALL = 2
                selectedMode: 1
            });
            self.options.reloadComponent.valueHasMutated();
        }

        /**
         * cancel
         */
        cancel(){
            nts.uk.ui.windows.close();
        };

    }
}