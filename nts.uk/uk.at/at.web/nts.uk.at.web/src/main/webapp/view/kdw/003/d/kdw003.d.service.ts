module nts.uk.at.view.kdw003.d.service {
    
    var paths: any = {
        getAll: "screen/at/dailyperformance/correction/extraction/getErrorList",
    }
    
    /**
     * 対応する「勤務実績のエラーアラーム」をすべて取得する
     */
    export function getErrorList(){
        return nts.uk.request.ajax(paths.getAll);    
    }
}