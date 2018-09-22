module nts.uk.pr.view.qmm008.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        editHealthInsuranceHistory: "ctx/core/socialinsurance/healthinsurance/editHistory",
        deleteHealthInsuranceHistory: "ctx/core/socialinsurance/healthinsurance/deleteHistory",
        editWelfareInsuranceHistory: "ctx/core/socialinsurance/welfarepensioninsurance/editHistory",
        deleteWelfareInsuranceHistory: "ctx/core/socialinsurance/welfarepensioninsurance/deleteHistory",

        editContributionHistory: "ctx/core/socialinsurance/contributionrate/editHistory",
        deleteContributionHistory: "ctx/core/socialinsurance/contributionrate/deleteHistory"
    }
    /**
     * get all
    */
    export function editHealthInsuranceHistory(command): JQueryPromise<any> {
        return ajax(paths.editHealthInsuranceHistory, command);
    }

    export function deleteHealthInsuranceHistory(command): JQueryPromise<any> {
        return ajax(paths.deleteHealthInsuranceHistory, command);
    }

    export function editWelfareInsuranceHistory(command): JQueryPromise<any> {
        return ajax(paths.editWelfareInsuranceHistory, command);
    }

    export function deleteWelfareInsuranceHistory(command): JQueryPromise<any> {
        return ajax(paths.deleteWelfareInsuranceHistory, command);
    }

    //Screen I
    export function editContributionHistory(command): JQueryPromise<any> {
        return ajax(paths.editContributionHistory, command);
    }

    export function deleteContributionHistory(command): JQueryPromise<any> {
        return ajax(paths.deleteContributionHistory, command);
    }
}