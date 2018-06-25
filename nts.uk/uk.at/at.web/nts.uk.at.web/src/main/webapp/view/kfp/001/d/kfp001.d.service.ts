module nts.uk.at.view.kfp001.d {
    export module service {

        var paths: any = {
            addOptionalAggrPeriod: "ctx/at/record/optionalaggr/findall"
        }

        export function addOptionalAggrPeriod(aggrFrameCode: any) {
            return nts.uk.request.ajax("at", paths.addOptionalAggrPeriod, aggrFrameCode);
        }
    }
}
