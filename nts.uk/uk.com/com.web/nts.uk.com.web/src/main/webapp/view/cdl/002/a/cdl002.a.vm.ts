module nts.uk.com.view.cdl002.a {
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            selectedMulEmployment: KnockoutObservableArray<string>;
            selectedSelEmployment: KnockoutObservable<string>;
            isMultiSelect: KnockoutObservable<boolean>;
            isDisplayUnselect: KnockoutObservable<boolean>;
            selecType: KnockoutObservable<SelectType>;
            listComponentOption: any;
            
            constructor() {
                let self = this;
                var params = getShared('CDL002Params');
                self.selectedMulEmployment = ko.observableArray([]);
                self.selectedSelEmployment = ko.observable('');
                self.isMultiSelect = ko.observable(params.isMultiSelect);
                if (!self.isMultiSelect() && params.selecType == SelectType.SELECT_ALL) {
                    self.selecType = ko.observable(SelectType.NO_SELECT);
                } else {
                    self.selecType = ko.observable(params.selecType);
                }
                if (self.isMultiSelect()) {
                    self.selectedMulEmployment(params.selectedCodes);
                }
                else {
                    self.selectedSelEmployment(params.selectedCodes);
                }
                
                // If Selection Mode is Multiple Then not show Unselected Row
                self.isDisplayUnselect = ko.observable(self.isMultiSelect() ? false : params.showNoSelection);
                
                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYMENT,
                    selectType: self.selecType(),
                    selectedCode: null,
                    isDialog: true,
                    isShowNoSelectRow: self.isDisplayUnselect(),
                    maxRows: 10,
                    tabindex: 1
                };
                if (self.isMultiSelect()) {
                    self.listComponentOption.selectedCode = self.selectedMulEmployment;
                }
                else {
                    self.listComponentOption.selectedCode = self.selectedSelEmployment;
                }
            }

            /**
             * Close dialog.
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            /**
             * Decide Employment
             */
            decideData = () => {
                var self = this;
                var selectedCode: any = null;
                if (self.isMultiSelect()) {
                    var dataSelected: string[] = [];
                    for (var code of self.selectedMulEmployment()) {
                        if (self.checkExistEmployment(code, $("#emp-component").getDataList())) {
                            dataSelected.push(code);
                        }
                    }
                    self.selectedMulEmployment(dataSelected);
                    selectedCode = self.selectedMulEmployment();
                    if (!selectedCode || selectedCode.length == 0) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_640" }).then(() => nts.uk.ui.windows.close());
                        return;
                    }
                } else {
                    selectedCode = self.selectedSelEmployment();
                    if (!self.checkExistEmployment(selectedCode, $("#emp-component").getDataList())) {
                        selectedCode = '';
                    }
                    if (!selectedCode) {
                        // Check if selected No select Row.
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_640" }).then(() => nts.uk.ui.windows.close());
                        return;
                    }
                }
                setShared('CDL002Output', selectedCode);
                close();
            }
            
            /**
             * function check employment
             */
            private checkExistEmployment(code: string, data: UnitModel[]): boolean {
                for (var item of data) {
                    if (code === item.code) {
                        return true;
                    }
                }
                return false;
            }
        }
        
        /**
        * List Type
        */
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }
        
        /**
         * class SelectType
         */
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
    }
}