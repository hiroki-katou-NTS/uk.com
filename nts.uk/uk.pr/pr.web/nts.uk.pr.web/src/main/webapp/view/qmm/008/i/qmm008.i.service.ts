module nts.uk.pr.view.qmm008.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findAllOfficeAndHistory: "ctx/core/socialinsurance/contributionrate/getAll",
        findContributionRateByHistoryId: "ctx/core/socialinsurance/contributionrate/getByHistoryId/{0}",
        addContributionRateHis: "ctx/core/socialinsurance/contributionrate/addContributionRateHistory"
    }
    /**
     * get all
    */
    export function findAllOfficeAndHistory(): JQueryPromise<any> {
        return ajax(paths.findAllOfficeAndHistory);
    }
    
    export function findContributionRateByHistoryId (historyId: string): JQueryPromise<any> {
        return ajax(format(paths.findContributionRateByHistoryId, historyId));
    }
    
    export function addContributionRateHis(command): JQueryPromise<any> {
        return ajax(paths.addContributionRateHis, command);
    }
}