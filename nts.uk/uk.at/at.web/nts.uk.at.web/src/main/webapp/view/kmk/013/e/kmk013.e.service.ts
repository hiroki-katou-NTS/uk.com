module nts.uk.at.view.kmk013.e {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/rounding/findByCid",
            save:"shared/caculation/holiday/rounding/add"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }

    }
}