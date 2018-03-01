module nts.uk.at.view.ksm011.c.service {
    var paths: any = {
        findAll: "ctx/at/schedule/setting/worktypedisp/find",
        add: "ctx/at/schedule/setting/worktypedisp/add",
        findWorkType: "at/screen/worktype/findAllDisp"
    }
    
    export function add(worktypedisp: any) {
        worktypedisp.workTypeList = _.map(worktypedisp.workTypeList, function(x){
            return { workTypeCode: x};
        });
        return nts.uk.request.ajax("at", paths.add, worktypedisp);
    }
    
    export function findWorkType(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findWorkType);
    }
     export function findAll(): JQueryPromise<Array<viewmodel.IWorktypeDisplayDto>> {
        return nts.uk.request.ajax("at",paths.findAll);
    }
}
