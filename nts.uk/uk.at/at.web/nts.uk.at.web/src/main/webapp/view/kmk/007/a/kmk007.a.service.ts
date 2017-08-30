module nts.uk.at.view.kmk007.a.service {

    var paths: any = {
        findAll: "at/share/worktype/findAll",
        addWorkType: "at/share/worktype/add",
        removeWorkType: "at/share/worktype/remove",
        findWorkTypeSet: "at/share/worktype/find/{0}"
    }

    export function loadWorkType(): JQueryPromise<Array<any>> {
        var path = paths.findAll;
        return nts.uk.request.ajax(path);
    }

    export function addWebMenu(workType: any): JQueryPromise<any> {
        var path = paths.addWorkType;
        return nts.uk.request.ajax("at", path, workType);
    }

    export function deleteWebMenu(workTypeCd): JQueryPromise<any> {
        var path = paths.removeWorkType;
        var obj = {
            workTypeCd: workTypeCd
        };
        return nts.uk.request.ajax("at", path, obj);
    }

    export function findWorkTypeSet(workTypeCd: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findWorkTypeSet, workTypeCd);
        return nts.uk.request.ajax(path);
    }
}