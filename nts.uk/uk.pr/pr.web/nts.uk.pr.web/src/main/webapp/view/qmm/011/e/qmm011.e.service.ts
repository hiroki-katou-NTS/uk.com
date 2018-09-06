module nts.uk.com.view.qmm011.e {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmpInsHis: "exio/monsalabonus/laborinsur/getEmpInsurHis",
            getEmpInsurPreRate: "exio/monsalabonus/laborinsur/getEmpInsurPreRate"
        };

        export function getEmpInsHis(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getEmpInsHis);
        }
        
        export function getEmpInsurPreRate(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getEmpInsurPreRate);
        }
    }
}