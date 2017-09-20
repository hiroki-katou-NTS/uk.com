module ksu001.lx.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listTeam: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedTeam: KnockoutObservable<any> = ko.observable();
        columnsTeam: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1110"), key: 'code', width: 60 },
            { headerText: nts.uk.resource.getText("KSU001_1111"), key: 'name', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1112"), key: 'amountOfPeople', width: 60 },
        ]);

        constructor() {
            let self = this;
        }
        
        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }
} 