module nts.uk.at.view.kmr005.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllWorkLocation: "at/record/worklocation/findall"
    }
        
    export function exportFile(): JQueryPromise<any> {
        nts.uk.ui.block.invisible();
        nts.uk.request.exportFile("/bento/report/reservation/month", {} ).done(function() {
            nts.uk.ui.block.clear();
        });
    }
}