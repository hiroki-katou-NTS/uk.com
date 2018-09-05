module nts.uk.com.view.qmm011.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getListOccAccIsHis: "exio/monsalabonus/laborinsur/getListOccAccIsHis",
            getOccAccIsPrRate: "exio/monsalabonus/laborinsur/getOccAccIsPrRate/{0}"
        };

        export function getListOccAccIsHis(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getListOccAccIsHis);
        }
        export function getOccAccIsPrRate(param :any): JQueryPromise<any> {
            let _path = nts.uk.text.format(path.getOccAccIsPrRate, param);
            return nts.uk.request.ajax("com", _path);
        }

    }
}