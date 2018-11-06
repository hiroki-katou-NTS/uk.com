module nts.uk.pr.view.qmm031.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        lstLifeInsurance: KnockoutObservableArray<ILifeInsurance> = ko.observableArray([]);
        selectLifeInsurance: KnockoutObservable<ILifeInsurance> = ko.observable([]);
        currentCodeLifeInsurance: KnockoutObservable<string> = ko.observable('');
        enableLifeInsuranceCode: KnockoutObservable<boolean> = ko.observable(true);
        check: KnockoutObservable<boolean> = ko.observable(false);


        lifeInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        lifeInsuranceName: KnockoutObservable<string> = ko.observable(null);

        lstInsuranceType: KnockoutObservableArray<IInsuranceType> = ko.observableArray([]);
        currentCodeInsuranceType: KnockoutObservable<string> = ko.observable('');

        isNewMode2: KnockoutObservable<boolean> = ko.observable(true);
        lstEarthquakeInsurance: KnockoutObservableArray<IEarthquakeInsurance> = ko.observableArray([]);
        selectEarthquakeInsurance: KnockoutObservable<IEarthquakeInsurance> = ko.observable([]);
        currentCodeEarthquakeInsurance: KnockoutObservable<string> = ko.observable('');
        enableEarthquake: KnockoutObservable<boolean> = ko.observable(true);

        earthquakeInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        earthquakeInsuranceName: KnockoutObservable<string> = ko.observable(null);

        columns: KnockoutObservableArray<any>;

        constructor() {
            let self = this,
                dfd = $.Deferred();
            self.check(true);
            self.columns = ko.observableArray([
                { headerText: getText('QMM031_17'), key: 'atrOfInsuranceType', width: 150, formatter: getAtrOfInsuranceType },
                { headerText: getText('QMM031_12'), key: 'insuranceTypeCode', width: 40, formatter: _.escape },
                { headerText: getText('QMM031_13'), key: 'insuranceTypeName', width: 240, formatter: _.escape }
            ]);

            self.currentCodeLifeInsurance.subscribe((item) => {
                if (item != '') {
                    let itemModel = _.find(self.lstLifeInsurance(), function (x) {
                        return x.lifeInsuranceCode == item
                    });
                    self.check(false);
                    self.lifeInsuranceCode(itemModel.lifeInsuranceCode);
                    self.lifeInsuranceName(itemModel.lifeInsuranceName);
                    self.isNewMode(false);
                    self.enableLifeInsuranceCode(false);
                    $("#lifeInsuranceName").focus();
                    nts.uk.ui.errors.clearAll();

                    block.invisible();
                    service.getInsuranceType(self.lifeInsuranceCode()).done(function (data: Array<IInsuranceType>) {
                        if (data && data.length > 0) {
                            let dataSort = _.sortBy(data, ["insuranceTypeCode"]);
                            self.lstInsuranceType(dataSort);
                            self.currentCodeInsuranceType(self.lstInsuranceType()[0].insuranceTypeCode);
                        }
                        else {
                            nts.uk.ui.errors.clearAll();
                            self.lstInsuranceType([]);
                        }
                        block.clear();
                        dfd.resolve(self);
                    }).fail(function (res) {
                        alertError({messageId: res.messageId});
                        block.clear();
                        dfd.reject();
                    });
                    return dfd.promise();

                }
            });
            self.getLifeInsuranceData();
            self.currentCodeEarthquakeInsurance.subscribe((item) => {
                if (item != '') {
                    let itemModel = _.find(self.lstEarthquakeInsurance(), function (x) {
                        return x.earthquakeInsuranceCode == item
                    });
                    self.earthquakeInsuranceCode(itemModel.earthquakeInsuranceCode);
                    self.earthquakeInsuranceName(itemModel.earthquakeInsuranceName);
                    self.isNewMode2(false);
                    self.enableEarthquake(false);
                    $("#earthquakeInsuranceName").focus();
                    nts.uk.ui.errors.clearAll();
                }
            });

        }

        onSelectTabA() {
            let self = this;
            self.getLifeInsuranceData();
        };

        onSelectTabB() {
            let self = this;
            self.getEarthquakeData();
        };

        getLifeInsuranceData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getLifeInsuranceData().done(function (data: Array<ILifeInsurance>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["lifeInsuranceCode"]);
                    self.lstLifeInsurance(dataSort);
                    self.currentCodeLifeInsurance('');
                    self.currentCodeLifeInsurance(self.lstLifeInsurance()[0].lifeInsuranceCode);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstLifeInsurance([]);
                    self.lifeInsuranceCode('');
                    self.lifeInsuranceName('');
                    self.currentCodeLifeInsurance('');
                    self.isNewMode(true);
                    self.enableLifeInsuranceCode(true);
                    $("#lifeInsuranceCode").focus();
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

        reloadLifeInsuranceData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getLifeInsuranceData().done(function (data: Array<ILifeInsurance>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["lifeInsuranceCode"]);
                    self.lstLifeInsurance(dataSort);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstLifeInsurance([]);
                    self.lifeInsuranceCode('');
                    self.lifeInsuranceName('');
                    self.currentCodeLifeInsurance('');
                    self.isNewMode(true);
                    self.enableLifeInsuranceCode(true);
                    $("#lifeInsuranceCode").focus();
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

        newLifeInsurance() {
            let self = this;
            self.check(true);
            nts.uk.ui.errors.clearAll();
            self.lifeInsuranceCode('');
            self.lifeInsuranceName('');
            self.currentCodeLifeInsurance('');
            self.isNewMode(true);
            self.enableLifeInsuranceCode(true);
            $("#lifeInsuranceCode").focus();
        };

        saveLifeInsurance() {
            let self = this;
            let data = {
                lifeInsuranceCode: self.lifeInsuranceCode(),
                lifeInsuranceName: self.lifeInsuranceName()
            };
            $("#lifeInsuranceCode").trigger("validate");
            $("#lifeInsuranceName").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create
                    service.addLifeInsurance(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#lifeInsuranceName").focus();
                            self.isNewMode(false);
                            self.reloadLifeInsuranceData().done(() => {
                                _.delay(() => {
                                    self.currentCodeLifeInsurance(data.lifeInsuranceCode);
                                }, 100, 'later');
                            });
                        });
                    }).fail(function (error) {
                        alertError(error).then(() => {
                            $("#lifeInsuranceCode").focus();
                        });
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateLifeInsurance(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#lifeInsuranceName").focus();
                            self.isNewMode(false);
                            self.reloadLifeInsuranceData().done(() => {
                                self.currentCodeLifeInsurance(data.lifeInsuranceCode);
                            });
                        });
                    }).fail(function (error) {
                        alertError(error);
                    }).always(function () {
                        $("#lifeInsuranceCode").focus();
                        block.clear();
                    });
                }
            }
        };

        deleteLifeInsurance() {
            let self = this;
            let data = {
                lifeInsuranceCode: self.lifeInsuranceCode(),
                lifeInsuranceName: self.lifeInsuranceName()
            };
            block.invisible();
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (data.lifeInsuranceCode) {
                    let index: number = _.findIndex(self.lstLifeInsurance(), function (x) {
                        return x.lifeInsuranceCode == data.lifeInsuranceCode
                    });
                    service.removeLifeInsurance(ko.toJS(data)).done(function () {
                        dialog.info({messageId: "Msg_16"}).then(() => {
                            self.reloadLifeInsuranceData().done(() => {
                                if (self.lstLifeInsurance().length == 0) {
                                    self.newLifeInsurance();
                                } else {
                                    let code = "";
                                    if (index == self.lstLifeInsurance().length) {
                                        code = self.lstLifeInsurance()[index - 1].lifeInsuranceCode;
                                    } else {
                                        code = self.lstLifeInsurance()[index].lifeInsuranceCode;
                                    }
                                    _.delay(() => {
                                        self.currentCodeLifeInsurance(code);
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

        newEarthquakeInsurance() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.earthquakeInsuranceCode('');
            self.earthquakeInsuranceName('');
            self.currentCodeEarthquakeInsurance('');
            self.isNewMode2(true);
            self.enableEarthquake(true);
            $("#earthquakeInsuranceCode").focus();
        };

        saveEarthquakeInsurance() {
            let self = this;
            let data = {
                earthquakeInsuranceCode: self.earthquakeInsuranceCode(),
                earthquakeInsuranceName: self.earthquakeInsuranceName()
            };
            $("#earthquakeInsuranceCode").trigger("validate");
            $("#earthquakeInsuranceName").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode2()) {
                    // create
                    service.addEarthquake(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#earthquakeInsuranceName").focus();
                            self.isNewMode2(false);
                            self.reloadEarthquakeData().done(() => {
                                _.delay(() => {
                                    self.currentCodeEarthquakeInsurance(data.earthquakeInsuranceCode);
                                }, 100, 'later');
                            });
                        });
                    }).fail(function (error) {
                        alertError(error).then(() => {
                            $("#earthquakeInsuranceCode").focus();
                        });
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateEarthquake(ko.toJS(data)).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            $("#earthquakeInsuranceName").focus();
                            self.isNewMode2(false);
                            self.reloadEarthquakeData().done(() => {
                                self.currentCodeEarthquakeInsurance(data.earthquakeInsuranceCode);
                            });
                        });
                    }).fail(function (error) {
                        alertError(error);
                    }).always(function () {
                        $("#earthquakeInsuranceCode").focus();
                        block.clear();
                    });
                }
            }
        };

        deleteEarthquakeInsurance() {
            let self = this;
            let data = {
                earthquakeInsuranceCode: self.earthquakeInsuranceCode(),
                earthquakeInsuranceName: self.earthquakeInsuranceName()
            };
            block.invisible();
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (data.earthquakeInsuranceCode) {
                    let index: number = _.findIndex(self.lstEarthquakeInsurance(), function (x) {
                        return x.earthquakeInsuranceCode == data.earthquakeInsuranceCode
                    });
                    service.removeEarthquake(ko.toJS(data)).done(function () {
                        dialog.info({messageId: "Msg_16"}).then(() => {
                            self.reloadEarthquakeData().done(() => {
                                if (self.lstEarthquakeInsurance().length == 0) {
                                    self.newEarthquakeInsurance();
                                } else {
                                    let code = "";
                                    if (index == self.lstEarthquakeInsurance().length) {
                                        code = self.lstEarthquakeInsurance()[index - 1].earthquakeInsuranceCode;
                                    } else {
                                        code = self.lstEarthquakeInsurance()[index].earthquakeInsuranceCode;
                                    }
                                    _.delay(() => {
                                        self.currentCodeEarthquakeInsurance(code);
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

        getEarthquakeData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getEarthquake().done(function (data: Array<IEarthquakeInsurance>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["earthquakeInsuranceCode"]);
                    self.lstEarthquakeInsurance(dataSort);
                    self.currentCodeEarthquakeInsurance('');
                    self.currentCodeEarthquakeInsurance(self.lstEarthquakeInsurance()[0].earthquakeInsuranceCode);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstEarthquakeInsurance([]);
                    self.earthquakeInsuranceCode('');
                    self.earthquakeInsuranceName('');
                    self.currentCodeEarthquakeInsurance('');
                    self.isNewMode2(true);
                    self.enableEarthquake(true);
                    $("#earthquakeInsuranceCode").focus();
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

        reloadEarthquakeData(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getEarthquake().done(function (data: Array<IEarthquakeInsurance>) {
                if (data && data.length > 0) {
                    let dataSort = _.sortBy(data, ["earthquakeInsuranceCode"]);
                    self.lstEarthquakeInsurance(dataSort);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstEarthquakeInsurance([]);
                    self.earthquakeInsuranceCode('');
                    self.earthquakeInsuranceName('');
                    self.currentCodeEarthquakeInsurance('');
                    self.isNewMode2(true);
                    self.enableEarthquake(true);
                    $("#earthquakeInsuranceCode").focus();
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

        openD(){
            let self = this;
            nts.uk.ui.errors.clearAll();
            setShared("QMM031_D", {
                code: self.lifeInsuranceCode(),
                name: self.lifeInsuranceName()
            });
            nts.uk.ui.windows.sub.modal('../d/index.xhtml').onClosed(() => {
                self.lstInsuranceType([]);
                let data = getShared("QMM031_A");
                self.lstInsuranceType(data.lstdata);
                if(self.lstInsuranceType().length > 0) {
                    self.currentCodeInsuranceType(self.lstInsuranceType()[0].insuranceTypeCode);
                }
                $("#lifeInsuranceName").focus();
            });

        };

        pdf(){};

        correctionLog() {};

        earthquakeInsurance(){
            let self = this;

            nts.uk.ui.errors.clearAll();

            nts.uk.ui.windows.sub.modal('../e/index.xhtml').onClosed(() => {

            });
        };



    }
    export interface ILifeInsurance {
        lifeInsuranceCode: string;
        lifeInsuranceName: string;
    }

    export interface IInsuranceType {
        lifeInsuranceCode: string;
        insuranceTypeCode: string;
        insuranceTypeName: string;
        atrOfInsuranceType: number;
    }
    export interface IEarthquakeInsurance {
        earthquakeInsuranceCode: string;
        earthquakeInsuranceName: string;
    }

    export function getAtrOfInsuranceType(value)  {
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