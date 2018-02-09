module cmm045.a.service {
     var paths = {
         getApplicationList: "at/request/application/applist/getapplist",
         getApplicationDisplayAtr: "at/request/application/applist/get/appdisplayatr",
                 startScreen: "screen/at/correctionofdailyperformance/startScreen",
        saveColumnWidth: "screen/at/correctionofdailyperformance/updatecolumnwidth",
        selectErrorCode: "screen/at/correctionofdailyperformance/errorCode",
        selectFormatCode: "screen/at/correctionofdailyperformance/selectCode",
        findCodeName: "screen/at/correctionofdailyperformance/findCodeName",
        findAllCodeName: "screen/at/correctionofdailyperformance/findAllCodeName",
        addAndUpdate: "screen/at/correctionofdailyperformance/addAndUpdate"
    }

    /**
    * get List Application
    */
    export function getApplicationList(param: any): JQueryPromise<Array<viewmodel.model.Item>>{
        return nts.uk.request.ajax("at", paths.getApplicationList,param);
    }  
    /**
     * get list Application display atr
     */
    export function getApplicationDisplayAtr(): JQueryPromise<Array<viewmodel.model.Item>>{
        return nts.uk.request.ajax("at", paths.getApplicationDisplayAtr);
    }
    
    export function startScreen(param){
        return nts.uk.request.ajax(paths.startScreen, param);
    }
    
    export function saveColumnWidth(param) {
        return nts.uk.request.ajax(paths.saveColumnWidth, param);
    }
    
     export function selectErrorCode(param) {
        return nts.uk.request.ajax(paths.selectErrorCode, param);
    }
    
     export function selectFormatCode(param) {
        return nts.uk.request.ajax(paths.selectFormatCode, param);
         
    }
    
    export function findCodeName(param) {
        return nts.uk.request.ajax(paths.findCodeName, param);
         
    }
    
     export function findAllCodeName(param) {
        return nts.uk.request.ajax(paths.findAllCodeName, param);
         
    }
    
     export function addAndUpdate(param) {
        return nts.uk.request.ajax(paths.addAndUpdate, param);
         
    }
    
}