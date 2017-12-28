module nts.uk.at.view.kmk013.h {
    export module service {
        let paths: any = {
            findByCId : "at/record/calculation/findByCode",
            save:"at/record/calculation/add"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }

    }
}