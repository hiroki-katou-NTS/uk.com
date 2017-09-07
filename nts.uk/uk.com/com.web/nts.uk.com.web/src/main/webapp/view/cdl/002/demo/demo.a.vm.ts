module demo.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        selectedCodes: KnockoutObservable<any>;
        isMultiSelect: KnockoutObservable<boolean>;
        isDisplayUnselect: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.selectedCodes = ko.observableArray(['11']);
            self.isMultiSelect = ko.observable(true);
            self.isDisplayUnselect = ko.observable(false);
        }
        // Open Dialog CDL002
        private openDialog() {
            let self = this;
            setShared('selectedCodes', self.selectedCodes());
            setShared('isMultipleSelection', self.isMultiSelect());
            setShared('isDisplayUnselect', self.isDisplayUnselect());
            nts.uk.ui.windows.sub.modal("/view/cdl/002/a/index.xhtml").onClosed(() => {
                var selected = getShared('selectedCodes');
                self.selectedCodes(selected);
            });
        }
        
        // Get Code of Selected Item(s)
        private getSelectedItemCode(): string {
            var self = this;
            if (self.isMultiSelect()) {
                return self.selectedCodes().join(', ');
            } else {
                return self.selectedCodes();
            }
        }

    }
}