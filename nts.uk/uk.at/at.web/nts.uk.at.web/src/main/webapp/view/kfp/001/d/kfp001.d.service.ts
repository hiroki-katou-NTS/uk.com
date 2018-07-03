module nts.uk.at.view.kfp001.d {
    export module service {

        var paths: any = {
            addOptionalAggrPeriod: "ctx/at/record/optionalaggr/save"
        }

        export function addOptionalAggrPeriod(addAggrPeriodCommand: any) {
            return nts.uk.request.ajax("at", paths.addOptionalAggrPeriod, addAggrPeriodCommand);
        }
    }
}
