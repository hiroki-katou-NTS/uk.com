module qpp014.j.service {
    var paths = {
        saveAsPdfA: "screen/pr/QPP014/saveAsPdfA"
    }

    export function saveAsPdf(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdfA, command);
    }
}