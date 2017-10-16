module nts.uk.com.view.ksu001.n.viewmodel {

    export class ViewModel {
        listRank: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<any> = ko.observable();
        comboItems = [new ItemModel('1', 'A'),
            new ItemModel('2', 'B'),
            new ItemModel('3', 'C')];
        comboColumns = [{ prop: 'name', length: 8 }];

        constructor() {
            let self = this;
             self.currentCodeList = ko.observableArray([]);
            for (let i = 1; i < 20; i++) {
                self.listRank.push(new RankItems(i));
            }
        }
        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.initGrid();
            dfd.resolve();
            return dfd.promise();
        }
        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        /**
         * init grid 
         */
        initGrid(): void {
            let self = this;
            $("#grid2").ntsGrid({
                width: '360px',
                height: '310px',
                dataSource: self.listRank(),
                primaryKey: 'rankCode',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: nts.uk.resource.getText("KSU001_1322"), key: 'rankCode', dataType: 'string', width: '100px' },
                    { headerText: nts.uk.resource.getText("KSU001_1323"), key: 'rankName', dataType: 'string', width: '100px' },
                    { headerText: nts.uk.resource.getText("KSU001_1324"), key: 'rank', dataType: 'string', width: '150px', ntsControl: 'Combobox' },
                ],
                features: [{ name: 'Selection', mode: 'row', multipleSelection: true }],
                ntsFeatures: [],
                ntsControls: [
                    { name: 'Combobox', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true }
                ]
            });
        }
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class RankItems {
        rankCode: string;
        rankName: string;
        rank: string;
        constructor(index: number) {
            this.rankCode = '00000000' + index;
            this.rankName = 'ABC';
            this.rank = String(index % 3 + 1);
        }
    }
}