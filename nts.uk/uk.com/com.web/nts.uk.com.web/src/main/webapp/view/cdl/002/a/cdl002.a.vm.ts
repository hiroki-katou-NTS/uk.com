module nts.uk.com.view.cdl002.a {
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            selectedCodes: KnockoutObservable<any>;
            isMultiSelect: KnockoutObservable<boolean>;
            isDisplayUnselect: KnockoutObservable<boolean>;
            selecType: KnockoutObservable<SelectType>;
            listComponentOption: any;
            
            constructor() {
                let self = this;
                var params = getShared('CDL002Params');
                self.isMultiSelect = ko.observable(params.isMultiSelect);
                if (!self.isMultiSelect() && params.selecType == SelectType.SELECT_ALL) {
                    self.selecType = ko.observable(SelectType.NO_SELECT);
                } else {
                    self.selecType = ko.observable(params.selecType);
                }
                self.selectedCodes = ko.observable(params.selectedCodes);
                
                // If Selection Mode is Multiple Then not show Unselected Row
                self.isDisplayUnselect = ko.observable(self.isMultiSelect() ? false : params.showNoSelection);
                
                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYMENT,
                    selectType: self.selecType(),
                    selectedCode: self.selectedCodes,
                    isDialog: true,
                    isShowNoSelectRow: self.isDisplayUnselect(),
                    maxRows: 10,
                };
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
                let self = this;
                var isNoSelectRowSelected = $("#jobtitle").isNoSelectRowSelected();
                if((self.isMultiSelect() && self.selectedCodes().length == 0) 
                    || (!self.selectedCodes()) && !isNoSelectRowSelected) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_641" }).then(() => nts.uk.ui.windows.close());
                        return;
                }
                setShared('CDL002Output', self.selectedCodes());
                close();
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