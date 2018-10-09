module nts.uk.pr.view.qmm007.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            register: "core/wageprovision/companyuniformamount/addPayrollUnitPriceHis"
        };


        export function register(data :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.register, data);
            return nts.uk.request.ajax('pr', _path);
        }
    }
}