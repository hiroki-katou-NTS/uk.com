module nts.uk.at.view.kmk013.i {
    export module service {
        let paths: any = {
            findByCId : "shared/calculation/setting/find",
            save:"shared/calculation/setting/add",
            update:"shared/calculation/setting/update"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
        export function update(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
    }
}