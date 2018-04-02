module nts.uk.at.view.kmk013.b {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/findByCid",
            save :"shared/caculation/holiday/add",
            getDomainSet: "shared/selection/func/loadAllSetting"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
        
        export function getDomainSet(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDomainSet);
        }

    }
}