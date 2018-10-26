module nts.uk.pr.view.qmm023.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        enableCode: KnockoutObservable<boolean> = ko.observable(true);
        lstTaxExemptLimit: KnockoutObservableArray<ITaxExemptLimit> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');

        taxFreeamountCode: KnockoutObservable<string> = ko.observable('');
        taxExemptionName: KnockoutObservable<string> = ko.observable('');
        taxExemption: KnockoutObservable<number> = ko.observable(0);
        columns: KnockoutObservableArray<NtsGridListColumn>;


        constructor() {
            let self = this;
            this.columns = ko.observableArray([
                {headerText: getText('QMM023_7'), key: 'taxFreeamountCode', width: 50, formatter: _.escape},
                {headerText: getText('QMM023_8'), key: 'taxExemptionName', width: 180, formatter: _.escape},
                {headerText: getText('QMM023_9'), key: 'taxExemptionDisp', width: 170, formatter: _.escape}
            ]);
            self.currentCode.subscribe((item) => {
                if (item != '') {
                    let itemModel = _.find(self.lstTaxExemptLimit(), function (x) {
                        return x.taxFreeamountCode == item
                    });
                    self.taxFreeamountCode(itemModel.taxFreeamountCode);
                    self.taxExemptionName(itemModel.taxExemptionName);
                    self.taxExemption(itemModel.taxExemption);
                    self.isNewMode(false);
                    self.enableCode(true);
                    $("#taxExemptionName").focus();
                    nts.uk.ui.errors.clearAll();
                }
            });
            block.invisible();
            service.getAllTaxAmountByCompanyId().done(function (data: Array<TaxExemptLimit>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["taxFreeamountCode"])
                    dataSort.forEach(x => x.taxExemptionDisp = nts.uk.ntsNumber.formatNumber(x.taxExemption, new nts.uk.ui.option.NumberEditorOption({grouplength: 3})) + "¥");
                    self.lstTaxExemptLimit(dataSort);
                    self.currentCode(self.lstTaxExemptLimit()[0].taxFreeamountCode);
                    self.isNewMode(false);
                }
                else {
                    self.createTaxExe();
                }
            }).fail(function (res) {
                alertError({messageId: res.messageId});
            });
            block.clear();
        }

        createTaxExe() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.taxFreeamountCode('');
            self.taxExemptionName('');
            self.taxExemption(null);
            self.currentCode('');
            self.isNewMode(true);
            self.enableCode(false);
            $("#taxFreeamountCode").focus();
        };

        saveTaxExe() {
            let self = this;
            let data = {
                taxFreeamountCode: self.taxFreeamountCode(),
                taxExemptionName: self.taxExemptionName(),
                taxExemption: self.taxExemption()
            };
            $("#taxFreeamountCode").trigger("validate");
            $("#taxExemptionName").trigger("validate");
            $("#taxExemption").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create
                    service.addTaxExemptLimit(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#taxExemptionName").focus();
                            self.isNewMode(false);
                            self.getAllData().done(() => {
                                _.delay(() => {
                                    self.currentCode(data.taxFreeamountCode);
                                }, 100, 'later');
                            });
                        });
                    }).fail(function (error) {
                        alertError(error).then(() => {
                            $("#taxFreeamountCode").focus();
                        });
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateTaxExemptLimit(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#taxExemptionName").focus();
                            self.isNewMode(false);
                            self.getAllData().done(() => {
                                self.currentCode(data.taxFreeamountCode);
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

        printPdf() {
        };

        correctionLog() {
        };

        deleteTaxExe() {
            let self = this;
            let data = {
                taxFreeamountCode: self.taxFreeamountCode(),
                taxExemptionName: self.taxExemptionName(),
                taxExemption: self.taxExemption()
            };
            block.invisible();
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (data.taxFreeamountCode) {
                    let index: number = _.findIndex(self.lstTaxExemptLimit(), function (x) {
                        return x.taxFreeamountCode == data.taxFreeamountCode
                    });
                    service.removeTaxExemptLimit(ko.toJS(data)).done(function () {
                        dialog.info({messageId: "Msg_16"}).then(() => {
                            self.getAllData().done(() => {
                                if (self.lstTaxExemptLimit().length == 0) {
                                    self.createTaxExe();
                                } else {
                                    let code = "";
                                    if (index == self.lstTaxExemptLimit().length) {
                                        code = self.lstTaxExemptLimit()[index - 1].taxFreeamountCode;
                                    } else {
                                        code = self.lstTaxExemptLimit()[index].taxFreeamountCode;
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

        getAllData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.lstTaxExemptLimit.removeAll();
            service.getAllTaxAmountByCompanyId().done(function (data: Array<TaxExemptLimit>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["taxFreeamountCode"])
                    dataSort.forEach(x => x.taxExemptionDisp = nts.uk.ntsNumber.formatNumber(x.taxExemption, new nts.uk.ui.option.NumberEditorOption({grouplength: 3})) + "¥");
                    self.lstTaxExemptLimit(dataSort);
                    self.isNewMode(false);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.taxFreeamountCode('');
                    self.taxExemptionName('');
                    self.taxExemption(null);
                    self.isNewMode(true);
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

    export interface ITaxExemptLimit {
        taxFreeamountCode: string;
        taxExemptionName: string;
        taxExemption: number;
    }

    export class TaxExemptLimit {
        taxFreeamountCode: KnockoutObservable<string> = ko.observable('');
        taxExemptionName: KnockoutObservable<string> = ko.observable('');
        taxExemption: KnockoutObservable<number> = ko.observable(0);

        constructor(params: ITaxExemptLimit) {
            let self = this;
            self.taxFreeamountCode(params ? params.taxFreeamountCode : '');
            self.taxExemptionName(params ? params.taxExemptionName : '');
            self.taxExemption(params ? params.taxExemption : 0);
        }
    }
}