module nts.uk.com.view.cdl002.a {
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            selectedCodes: KnockoutObservable<any>;
            isMultiSelect: KnockoutObservable<boolean>;
            isDisplayUnselect: KnockoutObservable<boolean>;
            listComponentOption: any;
            
            constructor() {
                let self = this,
                isMultiple: boolean = getShared('isMultipleSelection');
                self.selectedCodes = ko.observable(getShared('selectedCodes'));
                self.isMultiSelect = ko.observable(isMultiple);
                
                // If Selection Mode is Multiple Then not show Unselected Row
                self.isDisplayUnselect = ko.observable(isMultiple ? false : getShared('isDisplayUnselect'));
                
                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYMENT,
                    selectType: 1,
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
                setShared('selectedCodes', self.selectedCodes());
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
    }
}