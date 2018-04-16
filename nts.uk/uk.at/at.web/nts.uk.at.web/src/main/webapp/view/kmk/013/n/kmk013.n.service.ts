module nts.uk.at.view.kmk013.n {
    export module service {
        let paths: any = {
            findByCId : "at/record/daily/night/find",
            save:"at/record/daily/night/add",
            update:"at/record/daily/night/update"
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