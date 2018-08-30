module nts.uk.com.view.qmm011.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmpInsHis: "exio/monsalabonus/laborinsur/getEmpInsurHis",
            getEmpInsurPreRate: "exio/monsalabonus/laborinsur/getEmpInsurPreRate/{0}"
        };

        export function getEmpInsHis(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getEmpInsHis);
        }
        
        export function getEmpInsurPreRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getEmpInsurPreRate, param);
            return nts.uk.request.ajax("com", _path);
        }
    }
}