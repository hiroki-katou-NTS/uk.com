module nts.uk.pr.view.qmm007.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHis",
            updatePayrollUnitPriceHis: "core/wageprovision/companyuniformamount/updatePayrollUnitPriceHis/{0}",
            deletePayrollUnitPriceHis: "core/wageprovision/companyuniformamount/updatePayrollUnitPriceHis/{0}"
        };

        export function getPayrollUnitPriceHis(data :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceHis, data);
            return nts.uk.request.ajax('pr', _path);

        }
        export function updatePayrollUnitPriceHis(hisId :string): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.updatePayrollUnitPriceHis, hisId);
            return nts.uk.request.ajax('pr', _path);
        }
        export function deletePayrollUnitPriceHis(hisId :string): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.deletePayrollUnitPriceHis, hisId);
            return nts.uk.request.ajax('pr', _path);
        }
    }
}