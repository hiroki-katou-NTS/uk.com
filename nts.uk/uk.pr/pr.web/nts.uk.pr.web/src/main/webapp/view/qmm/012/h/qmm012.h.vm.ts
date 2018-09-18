module nts.uk.pr.view.qmm012.h.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm012.share.model;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import errors = nts.uk.ui.errors;
    import info = nts.uk.ui.dialog.info;

    export class ScreenModel {
        currentSetting: KnockoutObservable<model.ValidityPeriodAndCycleSet> = ko.observable(null);
        validityPeriodAtrList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getValidityPeriodAtr());
        cycleSettingAtrList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getCycleSettingAtr());
        itemAtr: KnockoutObservable<string> = ko.observable("Test1");
        itemNameCode: KnockoutObservable<string> = ko.observable("Test2");
        itemName: KnockoutObservable<string> = ko.observable("Test3");
        salaryItemId: string = "1234567890";

        constructor() {

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            service.getValidityPeriodAndCycleSet(self.salaryItemId).done(function(data: model.IValidityPeriodAndCycleSet) {
                self.currentSetting(new model.ValidityPeriodAndCycleSet(data));
                self.currentSetting().salaryItemId(self.salaryItemId);
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        isEnableYearPeriod() {
            let self = this;
            return (self.currentSetting().periodAtr() == model.ValidityPeriodAtr.SETUP);
        }

        isEnableCycleSetting() {
            let self = this;
            return (self.currentSetting().cycleSettingAtr() == model.CycleSettingAtr.USE);
        }

        isValidForm() {
            let self = this;
            if (self.isEnableYearPeriod()) {
                if (self.currentSetting().yearPeriodStart() && self.currentSetting().yearPeriodEnd()
                    && self.currentSetting().yearPeriodStart() > self.currentSetting().yearPeriodEnd()) {
                    $('#H2_2').ntsError('set', { messageId: "MsgQ_3" });
                    return false;
                }
            }

            if (self.isEnableCycleSetting()) {
                if (self.currentSetting().january() == false && self.currentSetting().february() == false
                    && self.currentSetting().march() == false && self.currentSetting().april() == false
                    && self.currentSetting().may() == false && self.currentSetting().june() == false
                    && self.currentSetting().july() == false && self.currentSetting().august() == false
                    && self.currentSetting().september() == false && self.currentSetting().october() == false
                    && self.currentSetting().november() == false && self.currentSetting().december() == false) {
                    $('#H3_2').ntsError('set', { messageId: "MsgQ_4" });
                    return false;
                }
            }
            return true;
        }

        execution() {
            let self = this;
            errors.clearAll();
            $('.nts-input').trigger("validate");

            if (errors.hasError() === false && self.isValidForm()) {
                block.invisible();
                if (!self.isEnableYearPeriod()) {
                    self.currentSetting().yearPeriodStart(null);
                    self.currentSetting().yearPeriodEnd(null);
                }

                if (!self.isEnableCycleSetting()) {
                    self.currentSetting().january(null);
                    self.currentSetting().february(null);
                    self.currentSetting().march(null);
                    self.currentSetting().april(null);
                    self.currentSetting().may(null);
                    self.currentSetting().june(null);
                    self.currentSetting().july(null);
                    self.currentSetting().august(null);
                    self.currentSetting().september(null);
                    self.currentSetting().october(null);
                    self.currentSetting().november(null);
                    self.currentSetting().december(null);
                }
                service.registerValidityPeriodAndCycleSet(ko.toJS(self.currentSetting)).done(() => {
                    info({ messageId: "Msg_15" }).then(() => {
                        setShared('QMM012_PARAMS_FROM_H', { exitStatus: model.ExitStatus.EXECUTION });
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(error) {
                    alertError({ messageId: error.messageId });
                }).always(() => {
                    block.clear();
                });
            }
        }

        cancel() {
            setShared('QMM012_PARAMS_FROM_H', { exitStatus: model.ExitStatus.CANCEL });
            nts.uk.ui.windows.close();
        }
    }
}