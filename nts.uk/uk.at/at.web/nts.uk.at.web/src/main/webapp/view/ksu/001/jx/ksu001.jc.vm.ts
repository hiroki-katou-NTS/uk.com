module nts.uk.at.view.ksu001.jc.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        shiftName: KnockoutObservable<string> = ko.observable('');
        listShiftComboBox: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedShift: KnockoutObservable<string> = ko.observable('');
        listShift: KnockoutObservableArray<any> = ko.observableArray([]);
        isListShiftFull: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            this.listShiftComboBox = ko.observableArray([
                { 'code': 'Code1', 'name': 'Code 1' },
                { 'code': 'Code2', 'name': 'Code 2' },
            ]);
            this.isListShiftFull = ko.computed(function() {
                return self.listShift().length >= 31;
            }); 
        }
        
        addDay(): void {
            if (!this.isListShiftFull())
                this.listShift.push({ content: this.selectedShift() });
        }
        
        deleteDay(item): void {
            this.listShift.remove(item);
        }

        /** Clear data in table */
        clearData(): void {
            this.dataSource([]);
        }

        /** Decision */
        decision(): void {
            nts.uk.ui.windows.close();
        }

        /** Close dialog */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}