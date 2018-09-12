module nts.uk.com.view.qmm011.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            updateEmpInsurHis: "core/monsalabonus/laborinsur/updateEmpInsurHis",
            updateWorkersCompenInsur: "core/monsalabonus/laborinsur/updateWorkersCompenInsur"
        };

        export function updateEmpInsurHis(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.updateEmpInsurHis, data);
        }
        
        export function updateWorkersCompenInsur(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.updateWorkersCompenInsur, data);
        }
    }
}