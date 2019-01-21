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
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = program[1] != null ? "KMK005" + program[1] : "";
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: 'SettingTimeZone', domainType: domainType, languageId: 'ja', reportType: 0});
        }
    }
}