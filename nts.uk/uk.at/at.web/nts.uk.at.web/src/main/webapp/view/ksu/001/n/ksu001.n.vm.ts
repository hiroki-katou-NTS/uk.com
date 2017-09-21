module ksu001.n.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listRank: KnockoutObservableArray<any> = ko.observableArray([]);
        singleSelectedCode: KnockoutObservable<any> = ko.observable();

        constructor() {
            let self = this;
            for (let i = 1; i < 20; i++) {
                self.listRank.push(new RankItems('00' + i, '基本給', 'S'));
            }

            self.initGrid();
        }

        initGrid(): void {
            let self = this;
            var comboItems = [new ItemModel('1', 'S'),
                new ItemModel('2', 'A'),
                new ItemModel('3', 'B')];

            var comboColumns = [{ prop: 'name', length: 2 }];

            $("#grid2").ntsGrid({
                width: '420px',
                height: '315px',
                dataSource: self.listRank(),
                primaryKey: 'rankCode',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: nts.uk.resource.getText("KSU001_1322"), key: 'rankCode', dataType: 'string', width: '150px' },
                    { headerText: nts.uk.resource.getText("KSU001_1323"), key: 'rankName', dataType: 'string', width: '150px' },
                    { headerText: nts.uk.resource.getText("KSU001_1324"), key: 'rank', dataType: 'string', width: '100px', ntsControl: 'Combobox' },
                ],
                features: [{ name: 'Sorting', type: 'local' }],
                ntsControls: [
                    { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true }]
            });
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

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class RankItems {
        rankCode: string;
        rankName: string;
        rank: string;

        constructor(rankCode: string, rankName: string, rank: string) {
            this.rankCode = rankCode;
            this.rankName = rankName;
            this.rank = rank;
        }
    }
}