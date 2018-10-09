module nts.uk.pr.view.qmm007.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHis",
            submitPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/submitPayrollUnitPriceHis"
        };

        export function getPayrollUnitPriceHis(data :any): JQueryPromise<any> {

            return nts.uk.request.ajax(path.getPayrollUnitPriceHis, data);

        }
        export function submitPayrollUnitPriceHis(data :any): JQueryPromise<any> {

            return nts.uk.request.ajax(path.submitPayrollUnitPriceHis, data);
        }

    }
}