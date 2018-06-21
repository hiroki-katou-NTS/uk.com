module nts.uk.at.view.kdm001.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        updatePayout: "at/record/remainnumber/update",
        removePayout: "at/record/remainnumber/delete"
    }
    export function updatePayout(command): JQueryPromise<any>{
        return ajax(paths.updatePayout, command);
    }
    export function removePayout(command): JQueryPromise<any>{
        return ajax(paths.removePayout, command);
    }
}