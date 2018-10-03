module nts.uk.pr.view.qmm007.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/getPayUnitPriceHist/{0}"
        };

        export function getPayUnitPriceHist(hisId :string): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceHis, hisId);
            //return nts.uk.request.ajax('pr', _path);
            return nts.uk.request.ajax('pr',nts.uk.text.format("core/wageprovision/companyuniformamount/getPayUnitPriceHist/{0}", 'ssss'));
        }
    }
}