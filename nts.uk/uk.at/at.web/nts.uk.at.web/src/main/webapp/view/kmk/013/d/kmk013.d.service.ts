module nts.uk.at.view.kmk013.d {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/flex/findByCid",
            findRefreshByCId : "shared/caculation/holiday/flex/findInsuffByCid",
            save:"shared/caculation/holiday/flex/add",
            saveRefresh:"shared/caculation/holiday/flex/addInsuff"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function findRefreshByCId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findRefreshByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
        export function saveRefresh(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveRefresh,obj);
        }
    }
}