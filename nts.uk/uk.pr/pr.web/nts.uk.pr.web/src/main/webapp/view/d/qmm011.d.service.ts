module nts.uk.com.view.qmm011.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getOccAccInsurBus: "exio/monsalabonus/laborinsur/getOccAccInsurBus",
            removeOccAccInsurBus: "exio/monsalabonus/laborinsur/removeOccAccInsurBus",
        };

        export function getOccAccInsurBus(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getOccAccInsurBus);
        }

        export function removeOccAccInsurBus(data :any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.removeOccAccInsurBus, data);
        }
    }
}