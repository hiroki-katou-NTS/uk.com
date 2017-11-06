module nts.uk.at.view.kdw003.a.service {
    var paths: any = {
        startScreen: "screen/at/correctionofdailyperformance/startScreen",
        saveColumnWidth: "screen/at/correctionofdailyperformance/updatecolumnwidth"
    }
    
    export function startScreen(param){
        return nts.uk.request.ajax(paths.startScreen, param);
    }
    
    export function saveColumnWidth(param) {
        return nts.uk.request.ajax(paths.saveColumnWidth, param);
    }
    
}