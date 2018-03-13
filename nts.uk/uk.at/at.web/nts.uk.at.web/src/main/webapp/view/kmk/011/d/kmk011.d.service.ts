module nts.uk.at.view.kmk011.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            saveAllSetting: "",
            findAllItemSetting:"",
            deleteHist: "",
            findListHistory: "at/record/divergence/time/history/companyDivergenceRefTime/findAll",
            findUseUnitSetting: ""
        };
        
        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.saveAllSetting);
        }
        
        export function getAllItemSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAllItemSetting);
        }
        
        export function getAllHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
        export function deleteHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.deleteHist)    
        }
        
        export function getUseUnitSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findUseUnitSetting);
        }
    }
}