module nts.uk.com.view.cdl009.parent.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
//        selectedItem: KnockoutObservable<any>;
        isMultiSelect: KnockoutObservable<boolean>;
//        selectedCode: KnockoutObservable<string>;
        selectedIds: KnockoutObservableArray<string>;
        baseDate: KnockoutObservable<Date>;
        target: KnockoutObservable<number>;
        selectedEmployeeId: KnockoutObservable<string>;
        selectedEmps: KnockoutObservableArray<string>;

        selectionOption: KnockoutObservableArray<any>;
        selectedOption: KnockoutObservable<number>;
        selectionTypeList: KnockoutObservableArray<any>;
        selectedType: KnockoutObservable<number>;
        targetList: KnockoutObservableArray<any>;
        selectedTarget: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.isMultiSelect = ko.observable(true);
            self.selectedIds = ko.observableArray(['000000000000000000000000000000000006', '000000000000000000000000000000000009']);
//            self.selectedCode = ko.observable('000000000000000000000000000000000006');
            self.baseDate = ko.observable(moment(new Date()).toDate());
            self.target = ko.observable(TargetClassification.WORKPLACE);
//            self.selectedItem = ko.observable(self.selectedCodes());
//            self.isMultiSelect.subscribe(function(data) {
//                if (!data) {
//                    if (self.selectedType() == SelectType.SELECT_ALL) {
//                        self.selectedType(SelectType.SELECT_BY_SELECTED_CODE);
//                    }
//                    self.selectedItem(self.selectedCode());
//                }
//            });
            self.selectedEmployeeId = ko.observable('');
            self.selectedEmps = ko.observableArray([]);

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
            self.selectedType = ko.observable(SelectType.SELECT_BY_SELECTED_CODE);
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
            
            if (self.selectedType() == 4) {
                self.selectedIds([]);
            }
            // Set Param
            setShared('CDL009Params', {
                // isMultiSelect For Employee List Kcp005
                isMultiSelect: self.isMultiSelect(),
                // selecType For Workplace List Kcp004
                selecType: self.selectedType(),
                // For Workplace List Kcp004
                selectedIds: self.selectedIds(),
                // For Workplace List Kcp004
                baseDate: self.baseDate(),
                // Workplace or Department
                target: self.target()
            }, true);

            nts.uk.ui.windows.sub.modal("/view/cdl/009/a/index.xhtml").onClosed(function() {
                var isCancel = getShared('CDL009Cancel');
                if (isCancel) {
                    return;
                }
                var output = getShared('CDL009Output');
                if (self.isMultiSelect()) {
                    self.selectedEmps(output);
                } else {
                    self.selectedEmployeeId(output);
                }
                
            });
        }
        
        // Get Code of Selected Employee(s)
        private getSelectedEmp(): string {
            var self = this;
            if (self.isMultiSelect()) {
                    return self.selectedEmps().join(', ');
            } else {
                return self.selectedEmployeeId();
            }
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