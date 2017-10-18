module demo.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        selectedItem: KnockoutObservable<any>;
        isMultiSelect: KnockoutObservable<boolean>;
        isDisplayUnselect: KnockoutObservable<boolean>;
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        
        selectionOption: KnockoutObservableArray<any>;
        selectedOption: KnockoutObservable<number>;
        selectionTypeList: KnockoutObservableArray<any>;
        selectedType: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.isMultiSelect = ko.observable(true);
            self.selectedCodes = ko.observableArray(['01', '04']);
            self.selectedCode = ko.observable('02');
            self.selectedItem = ko.observable(self.isMultiSelect() ? self.selectedCodes() : self.selectedCode());
            self.isDisplayUnselect = ko.observable(false);
            self.isMultiSelect.subscribe(function(data) {
                if (data) {
                    if (self.isDisplayUnselect()) {
                        self.isDisplayUnselect(false);
                    }
                    self.selectedItem(self.selectedCodes());
                } else {
                    if (self.selectedType() == SelectType.SELECT_ALL) {
                        self.selectedType(SelectType.SELECT_BY_SELECTED_CODE);
                    }
                    self.selectedItem(self.selectedCode());
                }
            });
            
            self.isDisplayUnselect.subscribe(function(data) {
                if (data && self.isMultiSelect()) {
                    nts.uk.ui.dialog.alert("Displaying Unselect Item is not available for Multiple Selection!");
                    self.isDisplayUnselect(false);
                }
            })
            
            
            self.selectionOption = ko.observableArray([
                {code : 0, name: 'Single Selection'},
                {code : 1, name: 'Multiple Selection'},
            ]);
            self.selectedOption = ko.observable(self.isMultiSelect() ? 1 : 0);
            self.selectedOption.subscribe(function(data: number) {
                if (data == 0) {
                    self.isMultiSelect(false);
                }
                else {
                    self.isMultiSelect(true);
                }
            });
            self.selectionTypeList = ko.observableArray([
                { code: 1, name: 'By Selected Code' },
                { code: 2, name: 'Select All Items' },
                { code: 3, name: 'Select First Item' },
                { code: 4, name: 'Select None' }
            ]);
            self.selectedType = ko.observable(1);
            self.selectedType.subscribe(function(data: number) {
                if (data == SelectType.SELECT_ALL && !self.isMultiSelect()) {
                    nts.uk.ui.dialog.alert("Select All is not available for Single Selection!");
                    self.selectedType(SelectType.SELECT_BY_SELECTED_CODE);
                }
            });
        }
        // Open Dialog CDL002
        private openDialog() {
            let self = this;
            setShared('CDL002Params', {
                isMultiSelect: self.isMultiSelect(),
                selecType: self.selectedType(),
                selectedCodes: self.selectedItem(),
                showNoSelection: self.isDisplayUnselect(),
            }, true);
            
            nts.uk.ui.windows.sub.modal("/view/cdl/002/a/index.xhtml").onClosed(function() {
                var output = getShared('CDL002Output');
                self.selectedItem(output);
                self.selectedType(SelectType.SELECT_BY_SELECTED_CODE);
            });
        }
        
    }
    /**
     * Class SelectType
     */
    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
}