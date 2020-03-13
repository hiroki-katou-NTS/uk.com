module nts.uk.at.view.ksm010.a {

    import blockUI = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import confirm = nts.uk.ui.dialog.confirm;
    import getText = nts.uk.resource.getText;
    import getMessage = nts.uk.resource.getMessage;

    export module viewmodel {

        export class ScreenModel {
            rankItemColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
                { headerText: getText("KSM010_6"), key: 'rankCd', width: 80 },
                { headerText: getText("KSM010_7"), key: 'rankSymbol', width: 140, formatter: _.escape }
            ]);
            rankItems: KnockoutObservableArray<Rank> = ko.observableArray([]);
            currentCode: KnockoutObservable<string> = ko.observable(null);
            rankCd: KnockoutObservable<string> = ko.observable(null);
            rankSymbol: KnockoutObservable<string> = ko.observable(null);
            isCreate: KnockoutObservable<boolean> = ko.observable(false);
            isStartPage: boolean = true;

            constructor() {
                let self = this;

                self.currentCode.subscribe((newValue) => {
                    nts.uk.ui.errors.clearAll();

                    if (newValue == null) {
                        self.rankCd(null);
                        self.rankSymbol(null);
                        $('#rankCode').focus();
                    } else if (newValue == "") {
                        // khi ctrl+click select vao item da duoc select
                        self.create();
                    } else {
                        self.isCreate(false);
                        self.getRank(newValue);
                        $('#rankSymbol').focus();
                    }
                });
            }

            startPage(): JQueryPromise<any> {
                let self = this, dfd = $.Deferred();

                self.getListRank().done(() => {
                    dfd.resolve();
                }).fail(() => {
                    dfd.reject();
                });

                return dfd.promise();
            }

            getListRank(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();

                blockUI.grayout();
                service.getListRank().done(function(data) {

                    if (data.length > 0) {
                        self.rankItems(data);
                        if (self.isStartPage) {
                            self.isStartPage = false;
                            self.currentCode(self.rankItems()[0].rankCd);
                        }
                    } else {
                        self.rankItems([]);
                        self.create();
                    }

                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                }).always(() => {
                    blockUI.clear();
                });
                return dfd.promise();
            }

            getRank(rankCd: string): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();

                service.getRank(rankCd).done(function(data) {

                    if (data) {
                        self.rankCd(data.rankCd);
                        self.rankSymbol(data.rankSymbol);
                    }

                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });

                return dfd.promise();
            }


            create(): void {
                let self = this;

                self.isCreate(true);
                self.currentCode(null);
                self.currentCode.valueHasMutated();
            }

            saveData(): void {
                let self = this;
                self.rankSymbol($.trim(self.rankSymbol()));
                $("#rankCode").trigger("validate");
                $("#rankSymbol").trigger("validate");
                _.map(self.rankItems() , 'rankCd');
                //_.map(self.rankItems() , 'rankCd').indexOf(self.rankCd) = -1.Check trùng lặp Rank Code
                if (_.map(self.rankItems() , 'rankCd').indexOf(self.rankCd()) != -1){
                    $('#rankCode').ntsError('set', {messageId:"Msg_3"});}
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
               

                let command: any = {
                    rankCd: self.rankCd(),
                    rankSymbol: self.rankSymbol()
                };

                blockUI.grayout();
                if (self.isCreate()) {
                    service.insert(command).done(() => {
                        info({ messageId: "Msg_15" });
                        self.getListRank();
                        self.currentCode(self.rankCd());
                    }).fail(function(error: any) {
                        alertError({ messageId: error.messageId });
                    }).always(() => {
                        blockUI.clear();
                    });
                } else {
                    service.updateRank(command).done(() => {
                        info({ messageId: "Msg_15" });
                        self.getListRank();
                    }).fail(function(error: any) {
                        alertError({ messageId: error.messageId });
                    }).always(() => {
                        blockUI.clear();
                    });
                }

            }

            remove(): void {
                let self = this;

                let command = { rankCd: self.rankCd() };
     
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    blockUI.grayout();
                    service.deleteRank(command).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        self.setCurrentCodeAfterDelete();
                        self.getListRank();
                    }).fail(function(error) {
                        alertError(error.message);
                    }).always(function() {
                        blockUI.clear();
                    });
                }).ifNo(() => {
                    blockUI.clear();
                });
            }

            setCurrentCodeAfterDelete(): void {
                let self = this,
                    size = self.rankItems().length,
                    indexCurrentCode = _.findIndex(self.rankItems(), ['rankCd', self.currentCode()]);

                if (size == 1) {
                    // list chi co 1 item
                    return;
                } else if (indexCurrentCode < size - 1) {
                    self.currentCode(self.rankItems()[indexCurrentCode + 1].rankCd);
                } else {
                    // list co nhieu item, item can xoa dung cuoi
                    self.currentCode(self.rankItems()[indexCurrentCode - 1].rankCd);
                }
            }

            openB(): void {
                let self = this;
                let transferObj: any = {};
                nts.uk.ui.windows.sub.modal('/view/ksm/010/b/index.xhtml');
            }
        }

        interface IRank {
            rankCd: string;
            rankSymbol: string;
        }

        class Rank {
            rankCd: string;
            rankSymbol: string;

            constructor(param: IRank) {
                this.rankCd = param.rankCd;
                this.rankSymbol = param.rankSymbol;
            }
        }
    }

}