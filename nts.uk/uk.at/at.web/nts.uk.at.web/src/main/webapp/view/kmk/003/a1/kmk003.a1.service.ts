module nts.uk.at.view.kmk003.a1 {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
            getWorkTimeSetByCode: "at/shared/pred/findByCode"
        };

        export function findWorkTimeSetByCode(worktimeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getWorkTimeSetByCode + "/" + worktimeCode);
        }

        /**
         * Data Model
         */
        export module model {

        }
    }
}
