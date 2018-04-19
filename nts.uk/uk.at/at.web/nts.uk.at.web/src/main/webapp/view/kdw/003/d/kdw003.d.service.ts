module nts.uk.at.view.kdw003.d.service {
    
    var paths: any = {
        getdailylyErrorList: "screen/at/dailyperformance/correction/extraction/getErrorList",
        getMonthlyErrorList: "screen/at/monthlyperformance/getErrorList",
    }
    
    /**
     * 対応する「勤務実績のエラーアラーム」をすべて取得する
     */
    export function getErrorList(){
        return nts.uk.request.ajax(paths.getdailylyErrorList);    
    }
    /**
     * ドメインモデル「月別実績のエラーアラーム」を取得する
     */
    export function getMonthlyErrorList(){
        return nts.uk.request.ajax(paths.getMonthlyErrorList);    
    }
}