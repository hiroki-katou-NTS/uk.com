module nts.uk.at.view.kfp001.e {
     import format = nts.uk.text.format;
    export module service {

        var paths: any = {
            executeAggr: "ctx/at/record/optionalaggr/executeAggr/{0}"
        }

        
         export function executeAggr(excuteId: any): JQueryPromise<any> {
            let _path = format(paths.executeAggr, excuteId);
            return nts.uk.request.ajax("at", _path);
        }

    }
}
