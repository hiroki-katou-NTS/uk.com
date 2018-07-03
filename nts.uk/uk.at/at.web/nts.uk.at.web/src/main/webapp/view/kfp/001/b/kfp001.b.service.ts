module nts.uk.at.view.kfp001.b {
    import format = nts.uk.text.format;
    export module service {

        var paths: any = {
            findAllOptionalAggrPeriod: "ctx/at/record/optionalaggr/findall",
            findByAggrFrameCode: "ctx/at/record/optionalaggr/find/{}",
            deleteOptionalAggr: "ctx/at/record/optionalaggr/delete",
            findExecAggr: "ctx/at/record/optionalaggr/findExecAggr/{0}",
            findTargetPeriod: "ctx/at/record/optionalaggr/findTargetPeriod/{0}",
            findStatus: "ctx/at/record/optionalaggr/findStatus/{0}/{1}",
            findAggrCode: "ctx/at/record/optionalaggr/findAggrCode/{0}"
        }

        export function findAllOptionalAggrPeriod(): JQueryPromise<Array<viewmodel.model.IOptionalAggrPeriodDto>> {
            return nts.uk.request.ajax("at", paths.findAllOptionalAggrPeriod);
        }
        export function findByAggrFrameCode(aggrFrameCode: any): JQueryPromise<Array<viewmodel.model.IOptionalAggrPeriodDto>> {
            return nts.uk.request.ajax("at", paths.findByAggrFrameCode + aggrFrameCode);
        }
        export function findExecAggr(aggrFrameCode: any): JQueryPromise<any> {
            let _path = format(paths.findExecAggr, aggrFrameCode);
            return nts.uk.request.ajax("at", _path);
        }
        export function findAggrCode(aggrFrameCode: any): JQueryPromise<any> {
            let _path = format(paths.findAggrCode, aggrFrameCode);
            return nts.uk.request.ajax("at", _path);
        }
        export function deleteOptionalAggr(aggrFrameCode: any) {
            return nts.uk.request.ajax("at", paths.deleteOptionalAggr, { 'aggrFrameCode': aggrFrameCode });
        }
        export function findTargetPeriod(aggrId: any): JQueryPromise<any> {
            let _path = format(paths.findTargetPeriod, aggrId);
            return nts.uk.request.ajax("at", _path);
        }
        export function findStatus(aggrFrameCode: any, executionStatus: any): JQueryPromise<any> {
            let _path = format(paths.findStatus, aggrFrameCode, executionStatus);
            return nts.uk.request.ajax("at", _path);
        }
    }
}
