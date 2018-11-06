module nts.uk.pr.view.qmm031.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        lstInsuranceType: KnockoutObservableArray<IInsuranceType> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');
        currentInsuranceType: KnockoutObservable<IInsuranceType> = ko.observable(null);

        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        enableInsuranceTypeCode: KnockoutObservable<boolean> = ko.observable(true);

        insuranceTypeCode: KnockoutObservable<string> = ko.observable(null);
        insuranceTypeName: KnockoutObservable<string> = ko.observable(null);

        viewCode: KnockoutObservable<string> = ko.observable('');
        viewName: KnockoutObservable<string> = ko.observable('');

        atrOfInsuranceType: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectAtrOfInsuranceType: KnockoutObservable<number> = ko.observable(0);

        lifeInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        lifeInsuranceName: KnockoutObservable<string> = ko.observable(null);

        columns: KnockoutObservableArray<any>;
        constructor() {
            let self = this,
                dfd = $.Deferred();
            self.columns = ko.observableArray([
                {headerText: getText('QMM031_17'), key: 'atrOfInsuranceType', width: 90, formatter: getValueAtrOfInsuranceType},
                {headerText: getText('QMM031_12'), key: 'insuranceTypeCode', width: 40, formatter: _.escape},
                {headerText: getText('QMM031_13'), key: 'insuranceTypeName', width: 275, formatter: _.escape}
            ]);
            let data = getShared("QMM031_D");
            self.lifeInsuranceCode(data.code);
            self.lifeInsuranceName(data.name);
            self.isNewMode(false);
            self.atrOfInsuranceType(getAtrOfInsuranceType());
            self.currentCode.subscribe((item) => {
                if (item != '') {
                    let itemModel = _.find(self.lstInsuranceType(), function (x) {
                        return x.insuranceTypeCode == item
                    });
                    self.viewCode(self.lifeInsuranceCode());
                    self.viewName(self.lifeInsuranceName());
                    self.insuranceTypeCode(itemModel.insuranceTypeCode);
                    self.insuranceTypeName(itemModel.insuranceTypeName);
                    self.selectAtrOfInsuranceType(itemModel.atrOfInsuranceType);
                    self.isNewMode(false);
                    self.enableInsuranceTypeCode(false);
                    $("#insuranceTypeName").focus();
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.getInsuranceType();
        }

        getInsuranceType(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getInsuranceType(self.lifeInsuranceCode()).done(function (data: Array<IInsuranceType>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["insuranceTypeCode"]);
                    self.lstInsuranceType(dataSort);
                    self.currentCode(self.lstInsuranceType()[0].insuranceTypeCode);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstInsuranceType([]);
                    self.selectAtrOfInsuranceType(0);
                    self.insuranceTypeCode('');
                    self.insuranceTypeName('');
                    self.currentCode('');
                    self.isNewMode(true);
                    self.enableInsuranceTypeCode(true);
                    $("#insuranceTypeCode").focus();
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

        reloadInsuranceType(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getInsuranceType(self.lifeInsuranceCode()).done(function (data: Array<IInsuranceType>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["insuranceTypeCode"]);
                    self.lstInsuranceType(dataSort);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstInsuranceType([]);
                    self.selectAtrOfInsuranceType(0);
                    self.insuranceTypeCode('');
                    self.insuranceTypeName('');
                    self.currentCode('');
                    self.isNewMode(true);
                    self.enableInsuranceTypeCode(true);
                    $("#insuranceTypeCode").focus();
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

        createInsuranceType() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectAtrOfInsuranceType(0);
            self.insuranceTypeCode('');
            self.insuranceTypeName('');
            self.currentCode('');
            self.isNewMode(true);
            self.enableInsuranceTypeCode(true);
            $("#insuranceTypeCode").focus();
        }

        saveInsuranceType() {
            let self = this;
            let data = {
                lifeInsuranceCode: self.lifeInsuranceCode(),
                insuranceTypeCode: self.insuranceTypeCode(),
                insuranceTypeName: self.insuranceTypeName(),
                atrOfInsuranceType: self.selectAtrOfInsuranceType()
            };
            $("#insuranceTypeCode").trigger("validate");
            $("#insuranceTypeName").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create
                    service.addInsuranceType(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#insuranceTypeName").focus();
                            self.isNewMode(false);
                            self.reloadInsuranceType().done(() => {
                                _.delay(() => {
                                    self.currentCode(data.insuranceTypeCode);
                                }, 100, 'later');
                            });
                        });
                    }).fail(function (error) {
                        alertError(error).then(() => {
                            $("#insuranceTypeCode").focus();
                        });
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateInsuranceType(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#insuranceTypeName").focus();
                            self.isNewMode(false);
                            self.reloadInsuranceType().done(() => {
                                self.currentCode(data.insuranceTypeCode);
                            });
                        });
                    }).fail(function (error) {
                        alertError(error);
                    }).always(function () {
                        $("#insuranceTypeCode").focus();
                        block.clear();
                    });
                }
            }
        }

        deleteInsuranceType() {
            let self = this;
            let data = {
                lifeInsuranceCode: self.lifeInsuranceCode(),
                insuranceTypeCode: self.insuranceTypeCode(),
                insuranceTypeName: self.insuranceTypeName(),
                atrOfInsuranceType: self.selectAtrOfInsuranceType()
            };
            block.invisible();
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (data.insuranceTypeCode) {
                    let index: number = _.findIndex(self.lstInsuranceType(), function (x) {
                        return x.insuranceTypeCode == data.insuranceTypeCode
                    });
                    service.removeInsuranceType(ko.toJS(data)).done(function () {
                        dialog.info({messageId: "Msg_16"}).then(() => {
                            self.reloadInsuranceType().done(() => {
                                if (self.lstInsuranceType().length == 0) {
                                    self.createInsuranceType();
                                } else {
                                    let code = "";
                                    if (index == self.lstInsuranceType().length) {
                                        code = self.lstInsuranceType()[index - 1].insuranceTypeCode;
                                    } else {
                                        code = self.lstInsuranceType()[index].insuranceTypeCode;
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
        }

        close() {
            let self = this;
            setShared("QMM031_A", {
                lstdata: self.lstInsuranceType()
            });
            nts.uk.ui.windows.close();
        }
    }

    export interface IInsuranceType {
        lifeInsuranceCode: string;
        insuranceTypeCode: string;
        insuranceTypeName: string;
        atrOfInsuranceType: number;
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export enum AtrOfInsuranceType {
        GENERAL_LIFE_INSURANCE = 0,
        NURSING_MEDICAL_INSURANCE = 1,
        INDIVIDUAL_ANNUITY_INSURANCE = 2
    }

    export function getAtrOfInsuranceType(): Array<ItemModel> {
        return [
            new ItemModel(AtrOfInsuranceType.GENERAL_LIFE_INSURANCE.toString(), getText('Enum_GENERAL_LIFE_INSURANCE')),
            new ItemModel(AtrOfInsuranceType.NURSING_MEDICAL_INSURANCE.toString(), getText('Enum_NURSING_MEDICAL_INSURANCE')),
            new ItemModel(AtrOfInsuranceType.INDIVIDUAL_ANNUITY_INSURANCE.toString(), getText('Enum_INDIVIDUAL_ANNUITY_INSURANCE'))
        ];
    }

    export function getValueAtrOfInsuranceType(value) {
        switch (value) {
            case "0":
                return getText('Enum_GENERAL_LIFE_INSURANCE');
            case "1":
                return getText('Enum_NURSING_MEDICAL_INSURANCE');
            case "2":
                return getText('Enum_INDIVIDUAL_ANNUITY_INSURANCE');
            default:
                return "";
        }
    }
}