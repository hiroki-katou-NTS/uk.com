module nts.uk.pr.view.qmm011.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmpInsHis: "core/monsalabonus/laborinsur/getEmpInsurHis",
            getEmpInsurPreRate: "core/monsalabonus/laborinsur/getEmpInsurPreRate/{0}",
            register: "core/monsalabonus/laborinsur/register"
        };

        export function getEmpInsHis(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getEmpInsHis);
        }
        
        export function getEmpInsurPreRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getEmpInsurPreRate, param);
            return nts.uk.request.ajax("pr", _path);
        }
        
        export function register(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, data);
        }
    }
}