module nts.uk.at.view.ksm010.b {

    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;

    export module viewmodel {

        export class ScreenModel {
            rankItemColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
                { headerText: getText("KSM010_6"), key: 'rankCd', width: 80 },
                { headerText: getText("KSM010_7"), key: 'rankSymbol', width: 140, formatter: _.escape }
            ]);
            rankItems: KnockoutObservableArray<a.Rank> = ko.observableArray([]);
            items: KnockoutObservableArray<a.Rank> = ko.observableArray([]);
            selectedCodes: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor() {
                let self = this;
            }

            startPage(): JQueryPromise<any> {
                let self = this, dfd = $.Deferred();

                self.getRankPriority().done(() => {
                    dfd.resolve();
                });

                return dfd.promise();
            }

            getRankPriority(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                blockUI.grayout();

                service.getRankAndRiority().done(function(data) {

                    for (let i = 0; i < data.listRankCodeSorted.length; i++) {
                        self.items.push(_.find(data.lstRankDto, ['rankCd', data.listRankCodeSorted[i]]));
                    }

                    self.rankItems(self.items());

                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                }).always(() => {
                    blockUI.clear();
                });

                return dfd.promise();
            }

            decision(): void {
                let self = this;

                let command = {
                    listRankCode: _.map(self.rankItems(), 'rankCd')
                };

                service.updatePriority(command).done(() => {
                    info({ messageId: "Msg_15" }).then(() => {
                        self.cancel();
                    });
                });
            }

            cancel(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
}