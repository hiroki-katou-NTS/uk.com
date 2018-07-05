module nts.uk.at.view.kfp001.e {
     import format = nts.uk.text.format;
    export module service {

        var paths: any = {
            executeAggr: "ctx/at/record/optionalaggr/executeAggr/{0}",
            getErrorMessageInfo: "ctx/at/record/optionalaggr/getErrorMessageInfo/{0}"
        }

        
         export function executeAggr(excuteId: any): JQueryPromise<any> {
            let _path = format(paths.executeAggr, excuteId);
            return nts.uk.request.ajax("at", _path);
        }
        export function getErrorMessageInfo(excuteId: any): JQueryPromise<any> {
            let _path = format(paths.getErrorMessageInfo, excuteId);
            return nts.uk.request.ajax("at", _path);
        }
        
        

    }
}
