module nts.uk.at.view.kdm001.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        updatePayout: "at/record/remainnumber/subhd/update",
        removePayout: "at/record/remainnumber/subhd/delete"
    }
    export function updatesubOfHD(command){
        return ajax(paths.updatePayout, command);
    }
    export function removeSubOfHD(command){
        return ajax(paths.removePayout, command);
    }
}