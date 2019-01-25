module nts.uk.pr.view.qmm017.f.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm017.share.model;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm017.f.service;

    export class ScreenModel {
        basicCalculationFormula: model.IBasicCalculationFormula = null;
        targetItemCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        //mix of 3 case
        targetItemCodeListItem: KnockoutObservableArray<any> = ko.observableArray([]);
        startMonth = moment().year();
        standardAmountType: KnockoutObservable<string> = ko.observable(getText("QMM017_138"));
        constructor() {
            let self = this;
            let params = getShared("QMM017_F_PARAMS");
            if (params) {
                self.startMonth = params.startMonth;
                self.basicCalculationFormula = params.basicCalculationFormula;
                self.targetItemCodeList(params.basicCalculationFormula.targetItemCodeList);
            }
            let standardAmountCls = params.basicCalculationFormula.standardAmountClassification;
            if (standardAmountCls == model.STANDARD_AMOUNT_CLS.PAYMENT_ITEM) {
                self.standardAmountType(getText("QMM017_126"));
            } else if (standardAmountCls == model.STANDARD_AMOUNT_CLS.DEDUCTION_ITEM) {
                self.standardAmountType(getText("QMM017_130"));
            } else if (standardAmountCls == model.STANDARD_AMOUNT_CLS.INDIVIDUAL_UNIT_PRICE_ITEM) {
                self.standardAmountType(getText("QMM017_134"));
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            let standardAmountCls = self.basicCalculationFormula.standardAmountClassification;
            service.getTargetItemCodeList(standardAmountCls, self.startMonth).done(function(data) {
                if (standardAmountCls == model.STANDARD_AMOUNT_CLS.PAYMENT_ITEM || standardAmountCls == model.STANDARD_AMOUNT_CLS.DEDUCTION_ITEM)
                data = data.map(item => {return {code: item.itemNameCd, name: item.name}});
                self.targetItemCodeListItem(data);
                dfd.resolve();
                block.clear();
            }).fail(function(err) {
                block.clear();
                dfd.reject();
                dialog.alertError(err.message);
            });
            return dfd.promise();
        }
        decideChangeItemList() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            setShared('QMM017_F_RES_PARAMS', { targetItemCodeList: self.targetItemCodeList() });
            nts.uk.ui.windows.close();
        }
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}


