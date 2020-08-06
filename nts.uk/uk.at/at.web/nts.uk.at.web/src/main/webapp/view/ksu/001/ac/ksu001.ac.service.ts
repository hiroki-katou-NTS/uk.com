module nts.uk.at.view.ksu001.ac.service {
    var paths: any = {
        getShiftPallets: "screen/at/schedule/getShiftPallets",
    }

    export function getShiftPallets(param : any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftPallets, param);
    }

}