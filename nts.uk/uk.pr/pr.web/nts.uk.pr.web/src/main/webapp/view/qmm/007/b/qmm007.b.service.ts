module nts.uk.pr.view.qmm007.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            addPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/addPayrollUnitPriceHis"
        };


        export function addPayrollUnitPriceHis(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.addPayrollUnitPriceHis, data);
        }
    }
}