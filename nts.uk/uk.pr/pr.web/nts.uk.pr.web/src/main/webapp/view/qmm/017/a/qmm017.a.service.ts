module nts.uk.pr.view.qmm017.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var parentPath = "ctx/pr/core/wageprovision/formula";
    var paths = {
        getAllFormula: parentPath + "/getAllFormula",
        getFormulaSettingByHistoryID: parentPath + "/getFormulaSettingByHistoryID/{0}",
        addFormula: parentPath + "/addFormula",
        addFormulaHistory: parentPath + "/addFormulaHistory",
        updateFormulaSetting: parentPath + "/updateFormulaSetting",
    }
    /**
     * get all
     */
    export function getAllFormula(): JQueryPromise<any> {
        return ajax(paths.getAllFormula);
    }

    export function getFormulaSettingByHistory (historyID: string): JQueryPromise<any> {
        return ajax(format(paths.getFormulaSettingByHistoryID, historyID));
    }

    export function addFormula(command): JQueryPromise<any> {
        return ajax(paths.addFormula, command);
    }

    export function addFormulaHistory(command): JQueryPromise<any> {
        return ajax(paths.addFormulaHistory, command);
    }

    export function updateFormulaSetting(command): JQueryPromise<any> {
        return ajax(paths.updateFormulaSetting, command);
    }
}