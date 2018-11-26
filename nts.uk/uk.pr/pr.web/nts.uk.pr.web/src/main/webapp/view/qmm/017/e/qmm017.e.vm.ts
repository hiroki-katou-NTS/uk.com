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
        conditionItemName: KnockoutObservable<string> = ko.observable('UNDEFINED');
        isShowConditionItem: KnockoutObservable<boolean> = ko.observable(true);
        //mix of 3 case
        targetItemCodeListItem: KnockoutObservableArray<any> = ko.observableArray([]);
        allowToDirectiveChange: boolean = false;
        constructor() {
            let self = this;
            let params = getShared("QMM017_E_PARAMS");
            if (params) {
                self.basicCalculationFormula(new model.BasicCalculationFormula(params.basicCalculationFormula));
                self.isShowConditionItem(params.originalScreen == 'C');
            }
            // ko.computed(function(){
            //     self.getTargetItemCodeList(newValue);
            // }, this)
            self.basicCalculationFormula().standardAmountClassification.subscribe(newValue => {
                if (newValue && self.allowToDirectiveChange) self.getTargetItemCodeList(newValue);
            })
        }
        getTargetItemCodeList(standardAmountCls): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getTargetItemCodeList(standardAmountCls).done(function(data) {
                if (standardAmountCls == model.STANDARD_AMOUNT_CLS.PAYMENT_ITEM || standardAmountCls == model.STANDARD_AMOUNT_CLS.DEDUCTION_ITEM)
                    data = data.map(item => {return {code: item.itemNameCd, name: item.name}})
                self.targetItemCodeListItem(data);
                self.computeBaseAmountTargetItem();
                self.allowToDirectiveChange = true;
                block.clear();
                dfd.resolve();
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
            return dfd.promise();
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            block.invisible();
            let standardAmountCls = self.basicCalculationFormula().standardAmountClassification();
            return self.getTargetItemCodeList(standardAmountCls).done(function() {
                $('#E5_1').focus();
                block.clear();
            });
        }
        saveConfiguration () {
            let self = this;
            // unknown which item to be affect. temporary not link b to e
            setShared("QMM017_E_RES_PARAMS", {basicCalculationFormula: self.fromKnockoutObservableToJS(self.basicCalculationFormula)});
            nts.uk.ui.windows.close();
        }
        cancel () {
            let self = this;
            nts.uk.ui.windows.close();
        }
        selectTargetItem () {
            let self = this;
            let basicCalculationFormula = self.fromKnockoutObservableToJS(self.basicCalculationFormula), targetItemCodeListItem = ko.toJS(self.targetItemCodeListItem), targetItemCodeList = basicCalculationFormula.targetItemCodeList;;
            setShared("QMM017_F_PARAMS", {targetItemCodeListItem: targetItemCodeListItem, basicCalculationFormula: basicCalculationFormula});
            modal("/view/qmm/017/f/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_F_RES_PARAMS");
                if (params) {
                    self.basicCalculationFormula().targetItemCodeList(params.targetItemCodeList);
                    self.computeBaseAmountTargetItem();
                }
            });
        }
        fromKnockoutObservableToJS (basicCalculationFormula: KnockoutObservable<any>) {
            basicCalculationFormula = ko.toJS(basicCalculationFormula);
            basicCalculationFormula.targetItemCodeList =  Object.keys(basicCalculationFormula.targetItemCodeList).map(key => basicCalculationFormula.targetItemCodeList[key]);
            return basicCalculationFormula;
        }
        computeBaseAmountTargetItem () {
            let self = this, targetItemCodeList = self.fromKnockoutObservableToJS(self.basicCalculationFormula).targetItemCodeList;
            let targetItem = ko.toJS(self.targetItemCodeListItem).filter(item => targetItemCodeList.indexOf(item.code) > -1).map(item => item.code + item.name);
            if (targetItem) self.baseAmountTargetItem(targetItem.join(" "));
        }
    }
}


