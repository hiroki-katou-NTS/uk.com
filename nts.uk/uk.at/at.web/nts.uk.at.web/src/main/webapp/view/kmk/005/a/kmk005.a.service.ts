module nts.uk.at.view.kmk005.a {
    export module service {
        var paths: any = {
            getListBonusPayTimeItem: "at/share/bonusPayTimeItem/getListBonusPayTimeItem",
            checkInit: "at/share/bonusPayTimeItem/checkInit"
        }
        
        export function getListBonusPTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListBonusPayTimeItem);
        }
        
        export function checkInit(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.checkInit);
        }
    }
}