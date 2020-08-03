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

        constructor() {
            let self = this;
            this.listShiftComboBox = ko.observableArray([
                { 'code': 'Code1', 'name': 'Code 1' },
                { 'code': 'Code2', 'name': 'Code 2' },
            ]);
        }
        
        addDay(): void {
            this.listShift.push({ content: this.selectedShift()
            
            
            });
        }
        
        deleteDay(item): void {
            console.log("Deteled");
            this.listShift.remove(item);
        }

        /** Clear data in table */
        clearData(): void {
            let self = this;
            self.dataSource([]);
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