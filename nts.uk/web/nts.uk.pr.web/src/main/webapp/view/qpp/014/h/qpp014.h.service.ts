module qpp014.h.service {
    var paths = {
        saveAsPdfB: "screen/pr/QPP014/saveAsPdfB"
    }

    export function saveAsPdf(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdfB, command);
    }
}