module nts.uk.at.view.kmk013.h {
    export module service {
        let paths: any = {
            findByCId : "at/record/calculation/findByCode",
            save:"at/record/calculation/add",
            findOutManageByCID: "at/shared/workrecord/goout/find",
            findManageEntryExitByCID: "at/shared/workrecord/entranceexit/find",
            saveOutManage: "at/shared/workrecord/goout/save",
            saveManageEntryExit: "at/shared/workrecord/entranceexit/save"
        }
        
        export function findManageEntryExitByCID(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findManageEntryExitByCID);
        }
        
        export function findOutManageByCID(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findOutManageByCID);
        }
        
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findByCId);
        }
        
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.save,obj);
        }
        
        export function saveOutManage(obj): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.saveOutManage,obj);
        }
        
        export function saveManageEntryExit(obj): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.saveManageEntryExit,obj);
        }

    }
}