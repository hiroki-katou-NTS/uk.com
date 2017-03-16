module nts.uk.pr.view.qpp007.c {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            save: "ctx/pr/report/salary/outputsetting/save",
            remove: "ctx/pr/report/salary/outputsetting/create",
            find: "ctx/pr/report/salary/outputsetting/find",
        };

        /**
         *  Update
         */
        export function save(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save, data);
        }
        /**
         *  Delete
         */
        export function remove(id: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.remove, id);
        }
        /**
         *  Find
         */
        export function find(id: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.find, id);
        }

    }
}
