module nts.uk.com.view.qmm011.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getOccAccInsurBus: "exio/monsalabonus/laborinsur/getOccAccInsurBus"
        };

        export function getOccAccInsurBus(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getOccAccInsurBus);
        }


    }
}