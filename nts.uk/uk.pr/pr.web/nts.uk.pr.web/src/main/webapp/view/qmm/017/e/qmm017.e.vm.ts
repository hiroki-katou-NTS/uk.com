module nts.uk.pr.view.qmm017.e.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm017.share.model

    export class ScreenModel {
        basicCalculationFormula: KnockoutObservable<model.BasicCalculationFormula> = ko.observable(new model.BasicCalculationFormula(null));
        baseAmountTargetItem: KnockoutObservable<string> = ko.observable(null);
        conditionItemName: KnockoutObservable<string> = ko.observable(null);
        isShowConditionItem: KnockoutObservable<boolean> = ko.observable(true);
        startMonth = moment().year();

        constructor() {
            let self = this;
            let params = getShared("QMM017_E_PARAMS");
            if (params) {
                if (params.basicCalculationFormula.roundingMethod == null) params.basicCalculationFormula.roundingMethod = model.ROUNDING_METHOD.ROUND_OFF;
                if (params.basicCalculationFormula.roundingResult == null) params.basicCalculationFormula.roundingResult = model.ROUNDING_RESULT.ROUND_OFF;
                self.basicCalculationFormula(new model.BasicCalculationFormula(params.basicCalculationFormula));
                self.conditionItemName(params.basicCalculationFormula.masterUseName)
                self.isShowConditionItem(params.originalScreen == 'C');
                self.startMonth = params.yearMonth.startMonth;
            }
            self.basicCalculationFormula().standardAmountClassification.subscribe(newValue => {
                $('#E2_6').ntsError('clear');
            });
            self.basicCalculationFormula().coefficientClassification.subscribe(newValue => {
                $('#E6_4').ntsError('clear');
            });
            self.basicCalculationFormula().baseItemClassification.subscribe(newValue => {
                $('#E3_4').ntsError('clear');
            });
            self.basicCalculationFormula().standardAmountClassification.subscribe(newValue => {
                self.baseAmountTargetItem("");
                if (newValue) self.basicCalculationFormula().targetItemCodeList([]);
            });
            self.computeBaseAmountTargetItem(self.basicCalculationFormula().standardAmountClassification());
        }

        saveConfiguration() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').filter(':enabled').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            setShared("QMM017_E_RES_PARAMS", {basicCalculationFormula: self.fromKnockoutObservableToJS(self.basicCalculationFormula)});
            nts.uk.ui.windows.close();
        }

        cancel() {
            let self = this;
            nts.uk.ui.windows.close();
        }

        selectTargetItem() {
            let self = this;
            let basicCalculationFormula = self.fromKnockoutObservableToJS(self.basicCalculationFormula),
                targetItemCodeList = basicCalculationFormula.targetItemCodeList;
            setShared("QMM017_F_PARAMS", {
                startMonth: self.startMonth,
                basicCalculationFormula: basicCalculationFormula
            });
            modal("/view/qmm/017/f/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_F_RES_PARAMS");
                if (params) {
                    self.basicCalculationFormula().targetItemCodeList(params.targetItemCodeList);
                    self.computeBaseAmountTargetItem(self.basicCalculationFormula().standardAmountClassification());
                }
                $('#E2_4').focus();
            });
        }

        fromKnockoutObservableToJS(basicCalculationFormula: KnockoutObservable<any>) {
            basicCalculationFormula = ko.toJS(basicCalculationFormula);
            basicCalculationFormula.targetItemCodeList = Object.keys(basicCalculationFormula.targetItemCodeList).map(key => basicCalculationFormula.targetItemCodeList[key]);
            return basicCalculationFormula;
        }

        computeBaseAmountTargetItem(standardAmountCls) {
            let self = this;
            service.getTargetItemCodeList(standardAmountCls, self.startMonth).done(function (data) {
                if (standardAmountCls == model.STANDARD_AMOUNT_CLS.PAYMENT_ITEM || standardAmountCls == model.STANDARD_AMOUNT_CLS.DEDUCTION_ITEM)
                    data = data.map(item => {
                        return {code: item.itemNameCd, name: item.name}
                    })
                let targetItemCodeList = self.fromKnockoutObservableToJS(self.basicCalculationFormula).targetItemCodeList;
                let targetItem = data.filter(item => targetItemCodeList.indexOf(item.code) > -1).map(item => item.name);
                if (targetItem) self.baseAmountTargetItem(targetItem.join(" ＋ "));
                block.clear();
            }).fail(function (err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }
    }
}


