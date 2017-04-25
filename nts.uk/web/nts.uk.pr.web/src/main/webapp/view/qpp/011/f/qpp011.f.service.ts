module qpp011.f.service {
    var paths = {
        findAllResidential: "pr/core/residential/findallresidential",
        getlistLocation: "pr/core/residential/getlistLocation",
        saveAsPdf: "screen/pr/QPP011/saveAsPdfC"
    }

    /**
     * Get list payment date processing.
     */

    export function findAllResidential(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.findAllResidential)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function getlistLocation(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.getlistLocation)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
     export function saveAsPdf(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdf, command);
    }
}