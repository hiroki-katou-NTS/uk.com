module nts.uk.pr.view.qmm018.b {

    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewModel {
        export class ScreenModel {

            statementList: KnockoutObservableArray<IStatement> = ko.observableArray([]);
            statementListSelected: KnockoutObservableArray<IStatement> = ko.observableArray([]);
            categoryAtr: number;

            // define gridColumns
            gridColumns: KnockoutObservableArray<any>;

            constructor() {
                let self = this;

                let params = getShared("QMM018_A_SETTING");

                if(params) {
                    self.categoryAtr = params.categoryAtr;
                    self.statementListSelected(params.statementListSelected);
                }

                self.gridColumns = ko.observableArray([
                    {headerText: '', key: 'salaryItemId', width: 0, formatter: _.escape, hidden: true},
                    {headerText: getText('QMM018_23'), key: 'itemNameCd', width: 60, formatter: _.escape},
                    {headerText: getText('QMM018_24'), key: 'name', width: 200, formatter: _.escape}
                ]);

            }//end constructor

            startPage(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                block.invisible();

                service.getStatementItemDataByCategory(self.categoryAtr).done(function (data: Array<IStatement>) {
                    self.statementList(data);

                    block.clear();
                    deferred.resolve();
                }).fail(error => {
                    //self.statementItemDataSelected(null);
                    console.log(error);

                    block.clear();
                    deferred.resolve();
                });

                return deferred.promise();
            }

            public register(): void {
                let self = this;

                setShared('QMM018_B_SETTING', {isSetting: true, statementListSelected: self.statementListSelected()});
                nts.uk.ui.windows.close();
            }

            public cancel(): void {
                setShared('QMM018_B_SETTING', {isSetting: false});
                nts.uk.ui.windows.close();
            }
        }

        export interface IStatement {
            salaryItemId: string;
            categoryAtr: number;
            itemNameCd: string;
            name: string;
        }
    }
}