module nts.uk.com.view.cdl009.parent.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        selectedItem: KnockoutObservable<any>;
        isMultiSelect: KnockoutObservable<boolean>;
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        referenceDate: KnockoutObservable<Date>;
        target: KnockoutObservable<number>;

        selectionOption: KnockoutObservableArray<any>;
        selectedOption: KnockoutObservable<number>;
        selectionTypeList: KnockoutObservableArray<any>;
        selectedType: KnockoutObservable<number>;
        targetList: KnockoutObservableArray<any>;
        selectedTarget: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.isMultiSelect = ko.observable(true);
            self.selectedCodes = ko.observableArray(['0000000006', '0000000009']);
            self.selectedCode = ko.observable('0000000006');
            self.referenceDate = ko.observable(new Date());
            self.target = ko.observable(TargetClassification.WORKPLACE);
            self.selectedItem = ko.observable(self.isMultiSelect() ? self.selectedCodes() : self.selectedCode());
            self.isMultiSelect.subscribe(function(data) {
                if (!data) {
                    if (self.selectedType() == SelectType.SELECT_ALL) {
                        self.selectedType(SelectType.SELECT_BY_SELECTED_CODE);
                    }
                    self.selectedItem(self.selectedCode());
                }
            });


            self.selectionOption = ko.observableArray([
                { code: 0, name: 'Single' },
                { code: 1, name: 'Multiple' },
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
            self.targetList = ko.observableArray([
                { code: 1, name: 'WorkPlace' },
                { code: 2, name: 'Department' },
            ]);
            self.selectedType = ko.observable(1);
            self.selectedTarget = ko.observable(self.target());
            self.selectedType.subscribe(function(data: number) {
                if (data == SelectType.SELECT_ALL && !self.isMultiSelect()) {
                    nts.uk.ui.dialog.alert("Select All is not available for Single Selection!");
                    self.selectedType(SelectType.SELECT_BY_SELECTED_CODE);
                }
            });
            self.selectedTarget.subscribe(function(data: number) {
                if (data == TargetClassification.DEPARTMENT) {
                    nts.uk.ui.dialog.alert("Department Target is not covered this time!");
                    self.selectedTarget(TargetClassification.WORKPLACE);
                }
            });
        }
        // Open Dialog CDL009
        private openDialog() {
            let self = this;
            // Set Param
            setShared('CDL009Params', {
                isMultiSelect: self.isMultiSelect(),
                selecType: self.selectedType(),
                selectedCodes: self.selectedItem(),
                referenceDate: self.referenceDate(),
                target: self.target()
            }, true);

            nts.uk.ui.windows.sub.modal("/view/cdl/009/a/index.xhtml").onClosed(function() {
                var isCancel = getShared('CDL009Cancel');
                if (isCancel) {
                    return;
                }
                var output = getShared('CDL009Output');
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

    /**
     * Class TargetClassification
     */
    export class TargetClassification {
        static WORKPLACE = 1;
        static DEPARTMENT = 2;
    }
}