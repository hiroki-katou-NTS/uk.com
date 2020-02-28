module nts.uk.pr.view.qmm017.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        calculate: "ctx/pr/core/wageprovision/formula/calculate",
        getEmbeddedFormulaDisplayContent: "ctx/pr/core/wageprovision/formula/getEmbeddedFormulaDisplayContent"
    };
    export function calculate(formula): JQueryPromise<any> {
        return ajax(paths.calculate, formula);
    }
    export function getEmbeddedFormulaDisplayContent(dto): JQueryPromise<any> {
        return ajax(paths.getEmbeddedFormulaDisplayContent, dto);
    }
}
