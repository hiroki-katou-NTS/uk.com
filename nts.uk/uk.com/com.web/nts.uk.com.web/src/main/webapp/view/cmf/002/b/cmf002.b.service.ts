module nts.uk.com.view.cmf002.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getCndSet: "exio/exo/condset/getCndSet",
            deleteCnd: "exio/exo/condset/delete"
        };

        export function getCndSet(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getCndSet);
        }
    }
}