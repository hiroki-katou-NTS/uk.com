module nts.uk.at.view.kmk003.a {
    export module service {

        /**
        *  Service paths
        */
        let servicePath: any = {
            getPredByCode: "at/shared/pred/findByCode",
            savePred: "at/shared/pred/save",
        };

        export function findWorkTimeSetByCode(worktimeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getPredByCode + "/" + worktimeCode);
        }

        export function savePred(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.savePred, data);
        }

        /**
         * Data Model
         */
        export module model {

        }
    }
}
