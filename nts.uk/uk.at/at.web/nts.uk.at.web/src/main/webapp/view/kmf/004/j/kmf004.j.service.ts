module nts.uk.at.view.kmf004.j.service {
    var paths: any = {
        findForScreenJ: "shared/specialholiday/findForScreenJ/{0}",
    }

    export function findForScreenJ(selectedCode): JQueryPromise<Array<any>> {
        var path = nts.uk.text.format(paths.findForScreenJ,selectedCode);
        return nts.uk.request.ajax("at", path);
    }

}