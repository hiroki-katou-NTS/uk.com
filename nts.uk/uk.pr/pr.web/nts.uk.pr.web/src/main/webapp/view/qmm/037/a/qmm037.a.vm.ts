module nts.uk.pr.view.qmm037.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        lstSalIndAmountName: KnockoutObservableArray<ISalIndAmountName> = ko.observableArray([]);
        currentSalIndAmountName: KnockoutObservable<ISalIndAmountName> = ko.observable([]);
        currentCode: KnockoutObservable<string> = ko.observable('');

        cateIndicator: KnockoutObservable<number> = ko.observable(0);
        enableIndividualPriceCode: KnockoutObservable<boolean> = ko.observable(true);
        individualPriceCode: KnockoutObservable<string> = ko.observable(null);
        individualPriceName: KnockoutObservable<string> = ko.observable(null);


        constructor() {
            let self = this;
            self.currentCode.subscribe((item) => {
                if (item != '') {
                    let itemModel = _.find(self.lstSalIndAmountName(), function (x) {
                        return x.individualPriceCode == item
                    });
                    self.individualPriceCode(itemModel.individualPriceCode);
                    self.individualPriceName(itemModel.individualPriceName);
                    self.isNewMode(false);
                    self.enableIndividualPriceCode(false);
                    $("#individualPriceName").focus();
                    nts.uk.ui.errors.clearAll();
                }
            });

            self.getData();
        }

        onSelectTabA() {
            let self = this;
            self.cateIndicator(0);
            self.getData();
        };

        onSelectTabB() {
            let self = this;
            self.cateIndicator(1);
            self.getData();
        };

        newSalIndAmountName() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.individualPriceCode('');
            self.individualPriceName('');
            self.currentCode('');
            self.isNewMode(true);
            self.enableIndividualPriceCode(true);
            $("#individualPriceCode").focus();
        };

        saveSalIndAmountName() {
            let self = this;
            let data = {
                individualPriceCode: self.individualPriceCode(),
                individualPriceName: self.individualPriceName(),
                cateIndicator: self.cateIndicator()
            };
            $("#individualPriceCode").trigger("validate");
            $("#individualPriceName").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create
                    service.addSalIndAmountName(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#individualPriceName").focus();
                            self.isNewMode(false);
                            self.reloadData().done(() => {
                                _.delay(() => {
                                    self.currentCode(data.individualPriceCode);
                                }, 100, 'later');
                            });
                        });
                    }).fail(function (error) {
                        alertError(error).then(() => {
                            $("#individualPriceCode").focus();
                        });
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateSalIndAmountName(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#individualPriceName").focus();
                            self.isNewMode(false);
                            self.reloadData().done(() => {
                                self.currentCode(data.individualPriceCode);
                            });
                        });
                    }).fail(function (error) {
                        alertError(error);
                    }).always(function () {
                        $("#taxFreeamountCode").focus();
                        block.clear();
                    });
                }
            }
        };

        correctionLog() {
        };

        deleteSalIndAmountName() {
            let self = this;
            let data = {
                individualPriceCode: self.individualPriceCode(),
                individualPriceName: self.individualPriceName(),
                cateIndicator: self.cateIndicator()
            };
            block.invisible();
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (data.individualPriceCode) {
                    let index: number = _.findIndex(self.lstSalIndAmountName(), function (x) {
                        return x.individualPriceCode == data.individualPriceCode
                    });
                    service.removeSalIndAmountName(ko.toJS(data)).done(function () {
                        dialog.info({messageId: "Msg_16"}).then(() => {
                            self.reloadData().done(() => {
                                if (self.lstSalIndAmountName().length == 0) {
                                    self.newSalIndAmountName();
                                } else {
                                    let code = "";
                                    if (index == self.lstSalIndAmountName().length) {
                                        code = self.lstSalIndAmountName()[index - 1].individualPriceCode;
                                    } else {
                                        code = self.lstSalIndAmountName()[index].individualPriceCode;
                                    }
                                    _.delay(() => {
                                        self.currentCode(code);
                                    }, 100, 'later');
                                }
                            });
                        });
                    }).fail(function (error) {
                        dialog.alertError({messageId: error.messageId});
                        block.clear();
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    block.clear();
                }
            }).then(() => {
                $('.nts-input').ntsError('clear');
                nts.uk.ui.errors.clearAll();
                block.clear();
            });
        };

        getData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getPersonalMoneyName(self.cateIndicator()).done(function (data: Array<ISalIndAmountName>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["individualPriceCode"]);
                    self.lstSalIndAmountName(dataSort);
                    self.currentCode(self.lstSalIndAmountName()[0].individualPriceCode);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstSalIndAmountName([]);
                    self.individualPriceCode('');
                    self.individualPriceName('');
                    self.currentCode('');
                    self.isNewMode(true);
                    self.enableIndividualPriceCode(true);
                    $("#individualPriceCode").focus();
                }
                block.clear();
                dfd.resolve(self);
            }).fail(function (res) {
                alertError({messageId: res.messageId});
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        };

        reloadData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getPersonalMoneyName(self.cateIndicator()).done(function (data: Array<ISalIndAmountName>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["individualPriceCode"]);
                    self.lstSalIndAmountName(dataSort);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstSalIndAmountName([]);
                    self.individualPriceCode('');
                    self.individualPriceName('');
                    self.currentCode('');
                    self.isNewMode(false);
                }
                block.clear();
                dfd.resolve(self);
            }).fail(function (res) {
                alertError({messageId: res.messageId});
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        };

    }

    export interface ISalIndAmountName {
        cId: string;
        individualPriceCode: string;
        cateIndicator: number;
        individualPriceName: string;
    }
}