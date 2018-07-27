module nts.uk.at.view.kdw003.c.service {
    
    var paths: any = {
        getDailyFormatList: "screen/at/dailyperformance/correction/dailyPerfFormat/getFormatList",
        getMonthlyFormatList: "screen/at/monthlyperformance/getFormatCodeList",
    }
    
    /**
     * 対応するドメインモデル「会社の日別実績の修正のフォーマット」をすべて取得する
     */
    export function getDailyFormatList() {
        return nts.uk.request.ajax(paths.getDailyFormatList);
    }
    /**
     * ドメインモデル「会社の月別実績の修正フォーマット」を取得する
     */
    export function getMonthlyFormatList() {
        return nts.uk.request.ajax(paths.getMonthlyFormatList);
    }
}