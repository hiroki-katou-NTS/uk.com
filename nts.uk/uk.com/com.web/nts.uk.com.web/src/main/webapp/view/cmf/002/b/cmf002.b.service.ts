module nts.uk.com.view.cmf002.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getCndSet: "exio/exo/condset/getCndSet",
            deleteCnd: "exio/exo/condset/delete",
            getOutItem: "exio/exo/condset/getOutItem",
            register: "exio/exo/condset/register"
        };

        export function getCndSet(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getCndSet);
        }
        
        export function getOutItem(cndSetcd: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getOutItem,cndSetcd);
        }
        
        export function deleteCnd(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.deleteCnd, command);
        }
        
        export function register(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, command);
        }
    }
}