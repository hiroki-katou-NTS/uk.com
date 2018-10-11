module nts.uk.pr.view.qmm007.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getPayrollUnitPriceHis: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHis/{0}",
            getPayrollUnitPriceHistoryByCidCode: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHistoryByCidCode/{0}",
            register: "core/wageprovision/companyuniformamount/register",
            getAllPayrollUnitPriceByCID: "core/wageprovision/companyuniformamount/getAllPayrollUnitPriceByCID",
            getPayrollUnitPriceSettingById: "core/wageprovision/companyuniformamount/getPayrollUnitPriceSettingById/{0}",
            getPayrollUnitPriceById: "core/wageprovision/companyuniformamount/getPayrollUnitPriceById/{0}",
            getPayrollUnitPriceHisById: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHisById/{0}/{1}",
            getAllHistoryById: "core/wageprovision/companyuniformamount/getAllHistoryById"
        };

        export  function getAllHistoryById() : JQueryPromise<any> {
            return nts.uk.request.ajax(path.getAllHistoryById);
        }
        export  function getPayrollUnitPriceHisById(code: string, hisId: string) : JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceHisById, code,hisId);
            return nts.uk.request.ajax("pr", _path);
        }

        export  function getPayrollUnitPriceById(param: any) : JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceById, param);
            return nts.uk.request.ajax("pr", _path);
        }

        export  function getPayrollUnitPriceSettingById(param: any) : JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceSettingById, param);
            return nts.uk.request.ajax("pr", _path);
        }

        export  function getAllPayrollUnitPriceByCID() : JQueryPromise<any> {
            return nts.uk.request.ajax(path.getAllPayrollUnitPriceByCID);
        }

        export  function getPayrollUnitPriceHistoryByCidCode(param: any) : JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getPayrollUnitPriceHistoryByCidCode, param);
            return nts.uk.request.ajax("pr", _path);
        }

        export  function register(data: any) : JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, data);
        }
    }
}