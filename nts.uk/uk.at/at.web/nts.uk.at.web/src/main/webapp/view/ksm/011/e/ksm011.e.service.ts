module nts.uk.at.view.ksm011.e {

    export module service {
        let paths = {
            buildTreeShiftCondition: "at/schedule/shift/shiftCondition/shiftCondition/buildTreeShiftCondition"
        }
        
        export function buildTreeShiftCondition(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.buildTreeShiftCondition);
        }
    }
}