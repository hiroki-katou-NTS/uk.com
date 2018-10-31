module nts.uk.pr.view.qmm019.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {
        yearMonth: KnockoutObservable<number>;
        yearMonthJapan: KnockoutObservable<string>;

        // ItemHistoryDivision  switch button
        itemHistoryDivisionList: KnockoutObservableArray<shareModel.BoxModel>;
        itemHistoryDivision: KnockoutObservable<number>;

        gridValue: Array<GridItem>;

        constructor() {
            let self = this;

            self.yearMonth = ko.observable(200001);
            self.yearMonthJapan = ko.observable("yearMonthJapan");
            self.yearMonth.subscribe(x => {
                // convert yearMonthJapan from yearMonth
            });

            self.itemHistoryDivisionList = ko.observableArray([
                new shareModel.BoxModel(0, getText('QMM019_46')),
                new shareModel.BoxModel(1, getText('QMM019_47'))
            ]);
            self.itemHistoryDivision = ko.observable(0);

            let list = [];
            for (let i = 0; i < 500; i++) {
                list.push(new GridItem(i));
            }
            self.gridValue = list;

            $("#grid").ntsGrid({
                width: '600px',
                height: '235px',
                dataSource: self.gridValue,
                primaryKey: 'id',
                virtualization: false,
                columns: [
                    {headerText: 'column1', key: 'id', dataType: 'string', width: '50px'},
                    {headerText: 'column2', key: 'column2', dataType: 'string', width: '100px'},
                    {headerText: 'column3',key: 'column3',dataType: 'string',width: '100px'},
                    {headerText: 'column4', key: 'column4', dataType: 'string', width: '100px'},
                    {headerText: 'column5',key: 'column5',dataType: 'string',width: '100px'},
                    {headerText: 'column6',key: 'column6',dataType: 'string',width: '100px'}
                ],
                features: [{name: 'Sorting', type: 'local'}]
            })
        }


        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

        decide(){
            nts.uk.ui.windows.close();
        }

        cancel(){
            nts.uk.ui.windows.close();
        }
    }

    class GridItem {
        id: string;
        column2: string;
        column3: string;
        column4: string;
        column5: string;
        column6: string;
        constructor(index: number) {
            this.id = index.toString();
            this.column2 = index.toString();
            this.column3 = index.toString();
            this.column4 = index.toString();
            this.column5 = index.toString();
            this.column6 = index.toString();
        }
    }
}