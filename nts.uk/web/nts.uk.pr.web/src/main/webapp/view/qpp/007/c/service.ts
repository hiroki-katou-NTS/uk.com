module nts.uk.pr.view.qpp007.c {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            update: "ctx/pr/report/salary/outputsetting/update",
            create: "ctx/pr/report/salary/outputsetting/create",
            remove: "ctx/pr/report/salary/outputsetting/create",
            find: "ctx/pr/report/salary/outputsetting/find",
        };

        /**
         *  Update
         */
        export function update(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data);
        }
        /**
         *  Create
         */
        export function create(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data);
        }
        /**
         *  Delete
         */
        export function remove(id: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, id);
        }
        /**
         *  Find
         */
        export function find(id: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, id);
        }

    }
}
