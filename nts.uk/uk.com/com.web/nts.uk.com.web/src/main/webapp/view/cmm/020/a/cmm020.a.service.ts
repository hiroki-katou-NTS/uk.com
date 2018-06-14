module nts.uk.com.view.cmm020.a {
    export module service {
        /**
         * define path to service
         */
        var paths: any = {
            getEraList: "",
            createEraName: "",
            deleteEraName: ""
        };

        export function getEraList() {
            return nts.uk.request.ajax(paths.getEraList);
        }

        export function createEraName(date: any): JQueryPromise<void> {
            return nts.uk.request.ajax(paths.createEraName, date);
        }

        export function deleteEraName(date: any): JQueryPromise<void> {
            return nts.uk.request.ajax(paths.deleteEraName, date);
        }
    }

 
}