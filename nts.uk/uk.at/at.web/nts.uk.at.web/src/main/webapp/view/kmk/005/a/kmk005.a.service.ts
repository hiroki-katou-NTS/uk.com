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
        export function exportExcel(): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: 'SettingTimeZone', domainType: 'KMK005加給時間帯の登録', languageId: 'ja', reportType: 0});
        }
    }
}