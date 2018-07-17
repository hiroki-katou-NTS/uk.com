module nts.uk.com.view.cmf002.x {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getExOutCondSetAndExecHist: "exio/exo/exechist/getExOutCondSetAndExecHist",
            useDeleteFile: "exio/exo/execlog/useDeleteFile/{0}",
        }

        export function getExOutCondSetAndExecHist(): JQueryPromise<any> {
            return ajax('com', paths.getExOutCondSetAndExecHist);
        };

        export function useDeleteFile(outProcessId): JQueryPromise<any> {
            let path = format(paths.useDeleteFile, outProcessId);
            return ajax('com', path);
        };
    }
}