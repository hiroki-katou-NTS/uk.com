module nts.uk.pr.view.qmm036.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
        getStatemetItem: "ctx/pr/core/breakdownAmountHis/getStatemetItem",
        getBreakdownHis: "ctx/pr/core/breakdownAmountHis/getBreakdownHis/{0}/{1}/{2}/{3}",
        getBreakDownAmoun: "ctx/pr/core/breakdownAmountHis/getBreakDownAmoun/{0}/{1}/{2}",
        addBreakdownAmountHis: "ctx/pr/core/breakdownAmountHis/addBreakdownAmountHis",
    }

    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }

    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }

    export function getStatemetItem(): JQueryPromise<any> {
        return ajax("pr", format(paths.getStatemetItem));
    }

    export function getBreakdownHis(categoryAtr: number, itemNameCd: String, salaryBonusAtr: number, selectedItem: string) : JQueryPromise<any> {
        return ajax("pr", format(paths.getBreakdownHis,categoryAtr, itemNameCd, salaryBonusAtr, selectedItem));
    }

    export function getBreakDownAmoun(historyID: String, categoryAtr: number, itemNameCd: String): JQueryPromise<any> {
        return ajax("pr", format(paths.getBreakDownAmoun, historyID, categoryAtr, itemNameCd));
    }

    export function addBreakdownAmountHis(command): JQueryPromise<any> {
        return ajax(paths.addBreakdownAmountHis, command);
    }

}