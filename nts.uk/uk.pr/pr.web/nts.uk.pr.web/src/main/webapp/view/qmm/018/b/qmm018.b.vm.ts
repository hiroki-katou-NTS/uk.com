module nts.uk.pr.view.qmm018.b {

    import model = qmm018.share.model;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewModel {
        export class ScreenModel {

            statementList: KnockoutObservableArray<IStatement> = ko.observableArray([]);
            statementListSelected: KnockoutObservableArray<IStatement> = ko.observableArray([]);
            categoryAtr: boolean;

            // define gridColumns
            gridColumns: KnockoutObservableArray<any>;

            constructor() {
                let self = this;

                self.gridColumns = ko.observableArray([
                    {headerText: '', key: 'salaryItemId', width: 0, formatter: _.escape, hidden: true},
                    {headerText: getText('QMM012_32'), key: 'itemNameCd', width: 60, formatter: _.escape},
                    {headerText: getText('QMM012_33'), key: 'name', width: 200, formatter: _.escape}
                ]);

            }//end constructor

            startPage(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                block.invisible();

                service.getStatementItemDataByCategory(0).done(function (data: Array<IStatement>) {
                    self.statementList(data);

                    block.clear();
                    deferred.resolve();
                }).fail(error => {
                    //self.statementItemDataSelected(null);

                    block.clear();
                    deferred.resolve();
                });

                return deferred.promise();
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