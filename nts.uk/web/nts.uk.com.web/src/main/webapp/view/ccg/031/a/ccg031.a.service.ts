module ccg031.a.service {
    import model = ccg.model;
    
    var paths = {
        active: "sys/portal/layout/active",
        registry: "sys/portal/layout/registry"
    }
    
    export function active(layoutID: string): JQueryPromise<model.LayoutDto> {
        if (nts.uk.text.isNullOrEmpty(layoutID))
            return nts.uk.request.ajax("com", paths.active);
        else
            return nts.uk.request.ajax("com", paths.active, layoutID);
    }
    
    export function registry(layoutID: string, pgType: number, placements: Array<model.Placement>): JQueryPromise<boolean> {
        var data = {
            portalLayoutCommand: {
                layoutID: layoutID,
                pgType: pgType
            },
            listPortalPlacementCommand: placements
        };
        return nts.uk.request.ajax("com", paths.registry, data);
    }
    
}