module nts.uk.at.view.kdw003.c.service {
    
    var paths: any = {
        getAll: "screen/at/dailyperformance/correction/dailyPerfFormat/getFormatList",
    }
    
    /**
     * 対応するドメインモデル「会社の日別実績の修正のフォーマット」をすべて取得する
     */
    export function getFormatList() {
        return nts.uk.request.ajax(paths.getAll);
    }
}