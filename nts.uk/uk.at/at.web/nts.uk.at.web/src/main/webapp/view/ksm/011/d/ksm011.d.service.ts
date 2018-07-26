module nts.uk.at.view.ksm011.d.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findAll: "ctx/at/schedule/setting/schemodifydeadline/find/",
        findDes: "ctx/at/schedule/setting/schemodifydeadline/findDes",
        add: "ctx/at/schedule/setting/schemodifydeadline/add",
        update: "ctx/at/schedule/setting/schemodifydeadline/update"
    }
    
     export function add(worktypedisp: any) {
        return nts.uk.request.ajax("at", paths.add, worktypedisp);
    }
    
     export function update(worktypedisp: any) {
        return nts.uk.request.ajax("at", paths.update, worktypedisp);
    }
     export function findAll(roleId : String): JQueryPromise<Array<viewmodel.IPermissonDto>> {
        return nts.uk.request.ajax("at",paths.findAll+roleId);
    }
    export function findDes(): JQueryPromise<any> {
        return nts.uk.request.ajax("at",paths.findDes);
    }
    
}
