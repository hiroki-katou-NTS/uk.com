module nts.uk.at.view.kmk013.d {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/flex/findByCid",
            save:"shared/caculation/holiday/flex/add"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
    }
}