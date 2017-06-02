module qpp014.i.service {
    var paths: any = {
        saveAsPdfI: "screen/pr/QPP014/saveAsPdfI",
    }

    export function saveAsPdfI(command: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.saveAsPdfI, command);
    }
}







