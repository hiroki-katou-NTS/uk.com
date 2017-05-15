module previewWidget.service {
    import model = ccg.model;

    var paths = {
        active: "sys/portal/layout/active",
    }

    export function active(layoutID: string): JQueryPromise<model.LayoutDto> {
        if (nts.uk.text.isNullOrEmpty(layoutID))
            return nts.uk.request.ajax("com", paths.active);
        else
            return nts.uk.request.ajax("com", paths.active, layoutID);
    }
}