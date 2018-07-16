module nts.uk.com.view.cdl008.parent.viewmodel {
    
    export class ScreenModel {
        //codes from parent screen
        canSelectWorkplaceIds: KnockoutObservable<string>;
        selectWorkplaceIds: KnockoutObservable<string>;
        selectMode: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        listSystemType: KnockoutObservableArray<any>;        
        selectedSystemType: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        restrictionOfReferenceRange: boolean;
        isDisplayUnselect: KnockoutObservable<boolean>;
        isShowBaseDate: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            //construct codes 
            self.canSelectWorkplaceIds = ko.observable('000000000000000000000000000000000002,000000000000000000000000000000000003');
            self.selectMode = ko.observable(true);
            self.selectWorkplaceIds = ko.observable('');
            self.baseDate = ko.observable(new Date());
            self.enable = ko.observable(true);
            self.listSystemType = ko.observableArray([
                {code : 1, name: '個人情報', enable: self.enable},
                {code : 2, name: '就業', enable: self.enable},
                {code : 3, name: '給与', enable: self.enable},
                {code : 4, name: '人事', enable: self.enable},
                {code : 5, name: '管理者', enable: self.enable}
            ]);   
            self.selectedSystemType = ko.observable(5);         
            self.restrictionOfReferenceRange = true;
            self.isDisplayUnselect = ko.observable(false);
            self.isShowBaseDate = ko.observable(false);

            self.isDisplayUnselect.subscribe(function(data) {
                if (data && self.selectMode()) {
                    nts.uk.ui.dialog.alert("Displaying Unselect Item is not available for Multiple Selection!");
                    self.isDisplayUnselect(false);
                }
            })
        }

        /**
         * open dialog cdl008
         */
        public openDialogCDL008(): void {
            let self = this;
            let canSelected = self.canSelectWorkplaceIds() ? self.canSelectWorkplaceIds().split(',') : [];
            nts.uk.ui.windows.setShared('inputCDL008', {
                selectedCodes: self.selectMode() ? canSelected : canSelected[0],
                baseDate: self.baseDate(),
                isMultiple: self.selectMode(),
                selectedSystemType: self.selectedSystemType(),
                isrestrictionOfReferenceRange: self.restrictionOfReferenceRange,
                showNoSelection: self.isDisplayUnselect(),
                isShowBaseDate: self.isShowBaseDate()
            }, true);

            nts.uk.ui.windows.sub.modal('/view/cdl/008/a/index.xhtml').onClosed(function(): any {
                // Check is cancel.
                if (nts.uk.ui.windows.getShared('CDL008Cancel')) {
                    return;
                }
                //view all code of selected item 
                var output = nts.uk.ui.windows.getShared('outputCDL008');
                self.selectWorkplaceIds(output);
            })
        }
    }
}
