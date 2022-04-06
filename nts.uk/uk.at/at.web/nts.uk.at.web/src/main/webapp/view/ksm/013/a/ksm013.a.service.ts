module nts.uk.at.view.ksm013.a {
    export module service {
        var paths = {
            findAll: "ctx/at/nurse/classification/findAll",
            findDetail: "ctx/at/nurse/classification/find/{0}",
            register: "ctx/at/nurse/classification/register",
            update: "ctx/at/nurse/classification/update",
            remove: "ctx/at/nurse/classification/delete"
        }
        // file webservice NurseClassificationWebService
        /**
        * get all Patt Calender
        */
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findAll);
        }

        export function findDetail(nurseClCode: string): JQueryPromise<any> {
            let path = nts.uk.text.format(paths.findDetail, nurseClCode);
            return nts.uk.request.ajax("at", path);
        }

        export function register(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.register, param);
        }

        export function update(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.update, param);
        }

        export function remove(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.remove, param);
        }
    }
}