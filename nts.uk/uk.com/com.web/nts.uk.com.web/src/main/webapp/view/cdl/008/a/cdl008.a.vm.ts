module nts.uk.com.view.cdl008.a {

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;

    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ScreenModel {
            selectedMulWorkplace: KnockoutObservableArray<string>;
            selectedSelWorkplace: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            workplaces: TreeComponentOption;
            isMultiple: boolean;
            selectedSystemType: KnockoutObservable<number>;
            restrictionOfReferenceRange: boolean;
            constructor() {
                var self = this;
                self.baseDate = ko.observable(new Date());
                self.selectedMulWorkplace = ko.observableArray([]);
                self.selectedSelWorkplace = ko.observable('');
                self.isMultiple = false;
                self.selectedSystemType = ko.observable(5);
                self.restrictionOfReferenceRange = false;
                var inputCDL008 = nts.uk.ui.windows.getShared('inputCDL008');
                if (inputCDL008) {
                    self.baseDate(inputCDL008.baseDate);
                    self.isMultiple = inputCDL008.isMultiple;
                    if (self.isMultiple) {
                        self.selectedMulWorkplace(inputCDL008.selectedCodes);
                    }
                    else {
                        self.selectedSelWorkplace(inputCDL008.selectedCodes);
                    }
                    self.selectedSystemType = inputCDL008.selectedSystemType;
                    if (!inputCDL008.isrestrictionOfReferenceRange) {
                        self.restrictionOfReferenceRange = true;
                    } else {
                        self.restrictionOfReferenceRange = inputCDL008.isrestrictionOfReferenceRange;
                    }
                }

                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: self.isMultiple,
                    treeType: TreeType.WORK_PLACE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowSelectButton: true,
                    baseDate: self.baseDate,
                    isDialog: true,
                    selectedWorkplaceId: null,
                    maxRows: 12,
                    tabindex: 1,
                    systemType: self.selectedSystemType,
                    restrictionOfReferenceRange: self.restrictionOfReferenceRange
                }
                if (self.isMultiple) {
                    self.workplaces.selectedWorkplaceId = self.selectedMulWorkplace;
                }
                else {
                    self.workplaces.selectedWorkplaceId = self.selectedSelWorkplace;
                }
            }

            /**
             * function on click button selected workplace
             */
            private selectedWorkplace(): void {
                var self = this;
                if (self.isMultiple) {
                    if (!self.selectedMulWorkplace() || self.selectedMulWorkplace().length == 0) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_643" }).then(() => nts.uk.ui.windows.close());
                        return;
                    }
                } else {
                    if (!self.selectedSelWorkplace || !self.selectedSelWorkplace()) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_643" }).then(() => nts.uk.ui.windows.close());
                        return;
                    }
                }

                var selectedCode: any = self.selectedMulWorkplace();
                if (!self.isMultiple) {
                    selectedCode = self.selectedSelWorkplace();
                }
                nts.uk.ui.windows.setShared('outputCDL008', selectedCode);
                nts.uk.ui.windows.close();
            }
            /**
             * close windows
             */
            private closeWindows(): void {
                nts.uk.ui.windows.setShared('CDL008Cancel', true);
                nts.uk.ui.windows.close();
            }
        }
    }
}