module nts.uk.com.view.qmm011.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getListOccAccIsHis: "exio/monsalabonus/laborinsur/getListOccAccIsHis",
            getOccAccInsurBus: "exio/monsalabonus/laborinsur/getOccAccInsurBus",
            getAccInsurPreRate: "exio/monsalabonus/laborinsur/getAccInsurPreRate/{0}",
            register: "exio/monsalabonus/laborinsur/register",
            adđOccAccIsHis: "exio/monsalabonus/laborinsur/adđOccAccIsHis",
            getOccAccIsPrRate: "exio/monsalabonus/laborinsur/getOccAccIsPrRate/{0}"
        };

        export function getListOccAccIsHis(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getListOccAccIsHis);
        }
        export function getOccAccIsPrRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getOccAccIsPrRate, param);
            return nts.uk.request.ajax("pr", _path);
        }
        export function getOccAccInsurBus(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getOccAccInsurBus, param);
            return nts.uk.request.ajax("pr", _path);
        }
        export function getAccInsurPreRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getAccInsurPreRate, param);
            return nts.uk.request.ajax("pr", _path);
        }
        export function register(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.register, data);
        }

        export function adđOccAccIsHis(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.adđOccAccIsHis, data);
        }

    }
}