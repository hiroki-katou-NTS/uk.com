module nts.uk.at.view.kfp001.b {
    export module service {

        var paths: any = {
            findAllOptionalAggrPeriod: "ctx/at/record/optionalaggr/findall",
            findByAggrFrameCode: "ctx/at/record/optionalaggr/find/{}",
        }

        export function findAllOptionalAggrPeriod(): JQueryPromise<Array<viewmodel.model.IOptionalAggrPeriodDto>> {
            return nts.uk.request.ajax("at", paths.findAllOptionalAggrPeriod);
        }
        export function findByAggrFrameCode(aggrFrameCode : any ): JQueryPromise<Array<viewmodel.model.IOptionalAggrPeriodDto>> {
            return nts.uk.request.ajax("at", paths.findByAggrFrameCode + aggrFrameCode);
        }
    }
}
