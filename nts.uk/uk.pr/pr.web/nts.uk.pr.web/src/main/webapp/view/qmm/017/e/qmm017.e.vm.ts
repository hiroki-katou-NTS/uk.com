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
        baseAmountTargetItem: KnockoutObservable<string> = ko.observable('TEMPORARY CAN NOT DEFINED');
        conditionItemName: KnockoutObservable<string> = ko.observable('UNDEFINED');
        isShowConditionItem: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            let params = getShared("QMM017_E_PARAMS");
            if (params) {
                self.isShowConditionItem(params.originalScreen == 'C');
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            setTimeout(function(){
                $('#E5_1').focus();
            }, 100)
            dfd.resolve();
            return dfd.promise();
        }
        saveConfiguration () {
            let self = this;
            nts.uk.ui.windows.close();
        }
        cancel () {
            let self = this;
            nts.uk.ui.windows.close();
        }
        selectTargetItem () {
            let self = this;
            let basicCalculationFormula = ko.toJS(self.basicCalculationFormula);
            basicCalculationFormula.targetItemCodeList = self.basicCalculationFormula().targetItemCodeList;
            setShared("QMM017_F_PARAMS", { basicCalculationFormula: basicCalculationFormula});
            modal("/view/qmm/017/f/index.xhtml").onClosed(function () {
                var params = getShared("QMM017_F_RES_PARAMS");
                if (params) {
                    self.basicCalculationFormula().targetItemCodeList = params.targetItemCodeList;
                }
            });
        }
    }
}


