module ksu001.l.viewmodel {
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

        listEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        columnsSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1119"), key: 'code', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1120"), key: 'name', width: 90 },
            { headerText: nts.uk.resource.getText("KSU001_1121"), key: 'amountOfPeople', width: 100 },
        ]);

        constructor() {
            let self = this;

            for (let i = 1; i < 20; i++) {
                self.listTeam.push(new ItemModel('00' + i, '基本給', i + '人'));
                self.listEmployeeSwap.push(new ItemModel('00' + i, '基本給', i + '人'));
            }
            self.selectedEmployeeSwap = ko.observableArray([]);
        }

        /**
         * open dialog LX
         */
        openDialogLX(): void {
            nts.uk.ui.windows.sub.modal("/view/ksu/001/lx/index.xhtml");
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }

    class ItemModel {
        code: string;
        name: string;
        amountOfPeople: string;
        constructor(code: string, name: string, amountOfPeople: string) {
            this.code = code;
            this.name = name;
            this.amountOfPeople = amountOfPeople;
        }
    }
}