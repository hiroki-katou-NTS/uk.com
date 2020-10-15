module nts.uk.at.view.ksu001.ac.service {
    var paths: any = {
        getShiftPallets: "screen/at/schedule/getShiftPallets",
        getShiftPalletWhenChangePage: "screen/at/schedule/change-page",
    }

    export function getShiftPallets(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftPallets, param);
    }

    export function getShiftPalletWhenChangePage(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftPalletWhenChangePage, param);
    }

}