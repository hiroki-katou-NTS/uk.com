module nts.uk.pr.view.qmm011.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getListOccAccIsHis: "core/monsalabonus/laborinsur/getListOccAccIsHis",
            getOccAccInsurBus: "core/monsalabonus/laborinsur/getOccAccInsurBus",
            getAccInsurPreRate: "core/monsalabonus/laborinsur/getAccInsurPreRate/{0}",
            register: "core/monsalabonus/laborinsur/registerOccAccInsur",
            addOccAccIsHis: "core/monsalabonus/laborinsur/addOccAccIsHis",
            getOccAccIsPrRate: "core/monsalabonus/laborinsur/getOccAccIsPrRate/{0}"
        };

        export function getListOccAccIsHis(): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getListOccAccIsHis);
            return nts.uk.request.ajax("pr", _path);
        }
        export function getOccAccIsPrRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getOccAccIsPrRate, param);
            return nts.uk.request.ajax("pr", _path);
        }
        export function getOccAccInsurBus(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getOccAccInsurBus);
        }
        export function getAccInsurPreRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getAccInsurPreRate, param);
            return nts.uk.request.ajax("pr", _path);
        }
        export function register(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, data);
        }

        export function addOccAccIsHis(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.addOccAccIsHis, data);
        }

    }
}