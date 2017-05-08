module qpp014.j.service {
    var paths = {
        saveAsPdf: "screen/pr/QPP014/saveAsPdf"
    }

    export function saveAsPdf(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdf, command);
    }
}