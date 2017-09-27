module demo.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        selectedItem: KnockoutObservable<any>;
        isMultiSelect: KnockoutObservable<boolean>;
        isDisplayUnselect: KnockoutObservable<boolean>;
        selecType: SelectType;
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        
        selectionOption: KnockoutObservableArray<any>;
        selectedOption: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.isMultiSelect = ko.observable(true);
            self.isDisplayUnselect = ko.observable(true);
            self.selecType = SelectType.SELECT_BY_SELECTED_CODE;
            self.selectedCodes = ko.observableArray(['11', '01']);
            self.selectedCode = ko.observable('02');
            self.selectedItem = ko.observable(self.isMultiSelect() ? self.selectedCodes() : self.selectedCode());
            self.selectionOption = ko.observableArray([
                {code : 0, name: 'Single Selection'},
                {code : 1, name: 'Multiple Selection'},
            ]);
            self.selectedOption = ko.observable(1);
            self.selectedOption.subscribe(function(data: number) {
                if (data == 0) {
                    self.isMultiSelect(false);
                    self.selectedItem(self.selectedCode());
                }
                else {
                    self.isMultiSelect(true);
                    self.selectedItem(self.selectedCodes());
                }
            });
        }
        // Open Dialog CDL002
        private openDialog() {
            let self = this;
            setShared('CDL002Params', {
                isMultiSelect: self.isMultiSelect(),
                selecType: self.selecType,
                selectedCodes: self.selectedItem(),
                showNoSelection: self.isDisplayUnselect(),
            }, true);
            
            nts.uk.ui.windows.sub.modal("/view/cdl/002/a/index.xhtml").onClosed(function() {
                var output = getShared('CDL002Output');
                if (output) {
                    self.selectedItem(output);
                }
            });
        }
        
        // Get Code of Selected Item(s)
        private getSelectedItemCode(): string {
            var self = this;
            if (self.isMultiSelect()) {
                return self.selectedItem().join(', ');
            } else {
                return self.selectedItem();
            }
        }
        
    }
    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
}