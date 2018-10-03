module nts.uk.pr.view.qmm007.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHis/{0}"
        };

        export function getPayrollUnitPriceHis(data :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceHis, data);
            return nts.uk.request.ajax("pr", _path);
        }
    }
}