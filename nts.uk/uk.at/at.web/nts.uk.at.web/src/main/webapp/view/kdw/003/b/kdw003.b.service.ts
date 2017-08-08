module nts.uk.at.view.kdw003.b {
    export module service {
        var paths: any = {
            getListBonusPayTimeItem: "at/share/bonusPayTimeItem/getListBonusPayTimeItem",
            checkInit: "at/share/bonusPayTimeItem/checkInit"
        }
        
        export function getListBonusPTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListBonusPayTimeItem);
        }
    }
}
