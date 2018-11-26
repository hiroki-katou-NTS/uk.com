module nts.uk.pr.view.qmm019.p.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

    export class ScreenModel {
        processingDate: KnockoutObservable<number> = ko.observable(null);
        statementLayouts: KnockoutObservableArray<any> = ko.observableArray([]);
        statementLayoutsSelected: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getCurrentProcessingDate().done(processingDate => {
                if(isNullOrUndefined(processingDate)){
                    self.processingDate(null);
                    block.clear();
                }else{
                    self.processingDate(processingDate);
                    service.getStatementLayoutByProcessingDate(processingDate).done((data: Array<IStatementLayoutDto>) => {
                        self.statementLayouts(data);
                        block.clear();
                    })
                }
            });
            dfd.resolve();
            return dfd.promise();
        }

        reflect() {
            let self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            let processingDate = moment(self.processingDate()).format("YYYYMM");
            service.getStatementLayoutByProcessingDate(processingDate).done((data: Array<IStatementLayoutDto>) => {
                self.statementLayoutsSelected.removeAll();
                self.statementLayouts(data);
                block.clear();
            })
        }

        output() {

        }

        cancel() {
            windows.close();
        }
    }

    interface IStatementLayoutDto {
        statementCode: string;
        statementName: string;
    }
}