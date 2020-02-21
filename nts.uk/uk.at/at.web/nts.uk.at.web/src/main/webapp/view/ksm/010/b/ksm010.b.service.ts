module nts.uk.at.view.ksm010.b {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths = {
            getRankAndRiority: "at/schedule/employeeinfo/rank/getRankAndRiority",
            updateRankPriority: "at/schedule/employeeinfo/rank/updatePriority"
        }

        export function updatePriority(command): JQueryPromise<any> {
            return ajax('at', paths.updateRankPriority, command);
        }

        export function getRankAndRiority(): JQueryPromise<any> {
            return ajax('at', paths.getRankAndRiority);
        }

    }
}