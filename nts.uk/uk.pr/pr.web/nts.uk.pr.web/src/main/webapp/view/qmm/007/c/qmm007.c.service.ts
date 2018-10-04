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
            let _path = nts.uk.text.format(path.getPayrollUnitPriceHis, data);
            return nts.uk.request.ajax('pr', _path);

        }
        export function submitPayrollUnitPriceHis(data :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.submitPayrollUnitPriceHis, data);
            return nts.uk.request.ajax('pr', _path);
        }

    }
}