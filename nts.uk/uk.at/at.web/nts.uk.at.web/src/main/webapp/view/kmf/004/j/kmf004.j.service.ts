module nts.uk.at.view.kmf004.j.service {
    var paths: any = {
        findForScreenJ: "shared/specialholiday/findForScreenJ",
    }

    export function findForScreenJ(): JQueryPromise<Array<any>> {
        var path = paths.findForScreenJ;
        return nts.uk.request.ajax("at", path);
    }

}