module nts.uk.at.view.kmf004.j.service {
    var paths: any = {
        findForScreenJ: "shared/specialholiday/findForScreenJ",
    }

    export function findForScreenJ(param): JQueryPromise<Array<any>> {
        var path = nts.uk.text.format(paths.findForScreenJ);
        return nts.uk.request.ajax("at", path, param);
    }

}