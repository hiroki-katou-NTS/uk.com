module qpp014.h.service {
    var paths = {
        saveAsPdfC: "screen/pr/QPP014/saveAsPdfC",
    }

    export function saveAsPdfC(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdfC, command);
    }
}