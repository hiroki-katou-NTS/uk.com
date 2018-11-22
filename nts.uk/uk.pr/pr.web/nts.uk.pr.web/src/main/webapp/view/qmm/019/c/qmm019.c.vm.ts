module nts.uk.pr.view.qmm019.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import IStatementLayout = nts.uk.pr.view.qmm019.share.model.IStatementLayout;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        statementCode: KnockoutObservable<string>;
        statementName: KnockoutObservable<string>;
        histId: KnockoutObservable<string>;
        startMonth: KnockoutObservable<string>;
        endMonth: KnockoutObservable<string>;
        newStartMonth: KnockoutObservable<number>;

        // ItemHistoryDivision  switch button
        itemHistoryEditList: KnockoutObservableArray<shareModel.BoxModel>;
        itemHistoryEdit: KnockoutObservable<number>;

        constructor() {
            let self = this;
            let params = getShared("QMM019_A_TO_C_PARAMS");

            self.statementCode = ko.observable(params ? params.code : null);
            self.statementName = ko.observable(null);
            self.histId = ko.observable(params ? params.histId : null);
            self.startMonth = ko.observable(null);
            self.endMonth = ko.observable(null);
            self.newStartMonth = ko.observable(null);

            if(params) {
                self.statementCode(params.code);
                self.histId(params.histId);
            }

            self.itemHistoryEditList = ko.observableArray([
                new shareModel.BoxModel(0, getText('QMM019_46')),
                new shareModel.BoxModel(1, getText('QMM019_47'))
            ]);
            self.itemHistoryEdit = ko.observable(0);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();

            service.getStatementLayoutAndHistById(self.statementCode(), self.histId()).done(function(data: IStatementLayout) {
                if(data) {
                    self.statementName(data.statementName);

                    if (data.history.length > 0) {
                        self.startMonth(nts.uk.time.parseYearMonth(data.history[0].startMonth).format());
                        self.endMonth(nts.uk.time.parseYearMonth(data.history[0].endMonth).format());
                        self.newStartMonth(data.history[0].startMonth);
                    }
                }

                block.clear();
                dfd.resolve();
            }).fail(() => {
                block.clear();
                dfd.resolve();
            });

            return dfd.promise();
        }

        decide(){
            let self = this;
            if(self.itemHistoryEdit() == 0) {
                let command = new StatementLayoutHistCommand(self.statementCode(), self.histId(), null, null);
                dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    block.invisible();
                    service.deleteStatementLayoutHist(command).done(function() {
                        block.clear();

                        dialog.info({ messageId: "Msg_16" }).then(() => {
                            setShared("QMM019_C_TO_A_PARAMS", { isRegistered: true, isEdit: false});
                            nts.uk.ui.windows.close();
                        });
                    }).fail(() => {
                        block.clear();
                    });
                })
            } else {
                let newStartDate = nts.uk.time.parseYearMonth(self.newStartMonth()).toValue();
                let command = new StatementLayoutHistCommand(self.statementCode(), self.histId(), newStartDate, 999912);

                if(!nts.uk.ui.errors.hasError()) {
                    service.updateStatementLayoutHist(command).done(function() {
                        setShared("QMM019_C_TO_A_PARAMS", { isRegistered: true, isEdit: true});
                        nts.uk.ui.windows.close();
                    }).fail(() => {
                        //TODO get message err and execute
                    })
                }
            }
        }

        cancel(){
            nts.uk.ui.windows.close();
        }
    }

    export class StatementLayoutHistCommand {
        statementCode: string;
        historyId: string;
        startMonth: number;
        endMonth: number;

        constructor(statementCode: string, historyId: string, startMonth: number, endMonth: number) {
            this.statementCode = statementCode;
            this.historyId = historyId;
            this.startMonth = startMonth;
            this.endMonth = endMonth;
        }
    }
}