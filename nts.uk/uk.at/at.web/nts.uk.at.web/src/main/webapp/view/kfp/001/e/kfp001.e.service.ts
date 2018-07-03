module nts.uk.at.view.kfp001.e {
    export module service {

        var paths: any = {
            findExecAggr: "ctx/at/record/optionalaggr/findExecAggr/{}",
            findTargetPeriod: "ctx/at/record/optionalaggr/findTargetPeriod/{}"
        }

        export function findExecAggr(aggrFrameCode: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findExecAggr + aggrFrameCode);
        }

        export function findTargetPeriod(aggrId: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findTargetPeriod + aggrId);
        }
    }
}
