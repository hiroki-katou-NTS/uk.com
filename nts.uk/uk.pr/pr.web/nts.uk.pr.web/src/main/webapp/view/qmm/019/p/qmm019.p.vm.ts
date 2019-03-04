module nts.uk.pr.view.qmm019.p.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        processingDate: KnockoutObservable<number> = ko.observable(null);
        statementLayouts: KnockoutObservableArray<any> = ko.observableArray([]);
        statementLayoutsSelected: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this;
            self.statementLayoutsSelected.subscribe(value => {
                if (_.isEmpty(value)) {
                    $('#P1_6_container').ntsError('set', {messageId: "FND_E_REQ_INPUT", messageParams: [getText("QMM019_13")]});
                }else{
                    $('#P1_6_container').ntsError('clear');
                }
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getCurrentProcessingDate().done(processingDate => {
                if (isNullOrUndefined(processingDate)) {
                    self.processingDate(null);
                    block.clear();
                } else {
                    self.processingDate(processingDate);
                    service.getStatementLayoutByProcessingDate(processingDate).done((data: Array<IStatementLayoutDto>) => {
                        let dataList : Array<any> = data.map(i => {
                            return {statementCode: i.statementCode, statementName: i.statementCode + "　" + i.statementName};
                        });
                        self.statementLayouts(dataList);

                        block.clear();
                    })
                }
            });
            dfd.resolve();
            return dfd.promise();   
        }

        reflect() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('#P1_3').ntsError('check');
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            let processingDate = moment(self.processingDate(), "YYYY/MM").format("YYYYMM");
            service.getStatementLayoutByProcessingDate(processingDate).done((data: Array<IStatementLayoutDto>) => {
                let dataList : Array<any> = data.map(i => {
                    return {statementCode: i.statementCode, statementName: i.statementCode + "　" + i.statementName};
                });
                self.statementLayouts(dataList);

                block.clear();
            })
        }

        output() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('#P1_3').ntsError('check');
            if (_.isEmpty(self.statementLayoutsSelected())) {
                $('#P1_6_container').ntsError('set', {messageId: "FND_E_REQ_INPUT", messageParams: [getText("QMM019_13")]});
            }
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            let processingDate = moment(self.processingDate(), "YYYY/MM").format("YYYYMM");
            let dto = {
                processingDate: processingDate,
                statementCodes: self.statementLayoutsSelected()
            };
            service.exportExcel(dto).done((data: Array<IStatementLayoutDto>) => {
                block.clear();
                windows.close();
            }).fail(err => {
                alertError(err);
                block.clear();
            })
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