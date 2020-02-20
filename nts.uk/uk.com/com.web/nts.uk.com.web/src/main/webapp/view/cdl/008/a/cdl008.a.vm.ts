module nts.uk.com.view.cdl008.a {

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import StartMode = kcp.share.tree.StartMode;
    import getText = nts.uk.resource.getText;

    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ScreenModel {
            selectedMulWorkplace: KnockoutObservableArray<string>;
            selectedSelWorkplace: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            workplaces: TreeComponentOption;
            isMultipleSelect: boolean;
            isMultipleUse: boolean;
            selectedSystemType: KnockoutObservable<number>;
            restrictionOfReferenceRange: boolean;
            isDisplayUnselect: KnockoutObservable<boolean>;

            // 部門対応 #106784
            startMode: StartMode;

            constructor() {
                var self = this;
                self.baseDate = ko.observable(new Date());
                self.selectedMulWorkplace = ko.observableArray([]);
                self.selectedSelWorkplace = ko.observable('');
                self.isMultipleSelect = false;
                self.isMultipleUse = false;
                self.selectedSystemType = ko.observable(5);
                self.restrictionOfReferenceRange = true;

                var inputCDL008 = nts.uk.ui.windows.getShared('inputCDL008');
                if (inputCDL008) {
                    self.baseDate(inputCDL008.baseDate);
                    self.isMultipleSelect = inputCDL008.isMultiple;
                    let currentDialog = nts.uk.ui.windows.getSelf();
                    // currentDialog.setSize(400, 500);
                    if(self.isMultipleSelect) {
                        currentDialog.setHeight(570);
                    } else {
                        currentDialog.setWidth(500);
                    }
                    if (_.isNil(inputCDL008.isShowBaseDate)) {
                        self.isMultipleUse = false;
                    } else {
                        self.isMultipleUse = inputCDL008.isShowBaseDate ? false : true;
                    }
                    if (self.isMultipleSelect) {
                        self.selectedMulWorkplace(inputCDL008.selectedCodes);
                    }
                    else {
                        self.selectedSelWorkplace(inputCDL008.selectedCodes);
                    }
                    self.selectedSystemType = inputCDL008.selectedSystemType;
                    self.restrictionOfReferenceRange = _.isNil(inputCDL008.isrestrictionOfReferenceRange) ?
                        true : inputCDL008.isrestrictionOfReferenceRange;

                    // If Selection Mode is Multiple Then not show Unselected Row
                    self.isDisplayUnselect = ko.observable(self.isMultipleSelect ? false : inputCDL008.showNoSelection);

                    // 部門対応 #106784
                    self.startMode = _.isNil(inputCDL008.startMode) ? StartMode.WORKPLACE : inputCDL008.startMode; // default workplace
                }

                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: self.isMultipleSelect,
                    isMultipleUse: self.isMultipleUse,
                    startMode: self.startMode,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowSelectButton: true,
                    baseDate: self.baseDate,
                    isDialog: true,
                    selectedId: null,
                    maxRows: 12,
                    tabindex: 1,
                    systemType: self.selectedSystemType,
                    restrictionOfReferenceRange: self.restrictionOfReferenceRange,
                    isShowNoSelectRow: self.isDisplayUnselect()
                };
                if (self.isMultipleSelect) {
                    self.workplaces.selectedId = self.selectedMulWorkplace;
                }
                else {
                    self.workplaces.selectedId = self.selectedSelWorkplace;
                }

                if (self.startMode == StartMode.DEPARTMENT) {
                    nts.uk.ui.windows.getSelf().setTitle(getText("CDL008_5"));
                }
            }

            /**
             * function on click button selected workplace
             */
            private selectedWorkplace(): void {
                var self = this;
                if (self.isMultipleSelect) {
                    if (!self.selectedMulWorkplace() || self.selectedMulWorkplace().length == 0) {
                        if(self.startMode == StartMode.WORKPLACE) {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_643" });
                        } else {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_1532" });
                        }
                        return;
                    }
                } else {
                    if (!self.isDisplayUnselect() && (!self.selectedSelWorkplace || !self.selectedSelWorkplace())) {
                        if(self.startMode == StartMode.WORKPLACE) {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_643" });
                        } else {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_1532" });
                        }
                        return;
                    }
                }

                var selectedCode: any = self.selectedMulWorkplace();
                if (!self.isMultipleSelect) {
                    selectedCode = self.selectedSelWorkplace();
                }
                nts.uk.ui.windows.setShared('outputCDL008', selectedCode);
                nts.uk.ui.windows.setShared('baseDateCDL008', self.baseDate());
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