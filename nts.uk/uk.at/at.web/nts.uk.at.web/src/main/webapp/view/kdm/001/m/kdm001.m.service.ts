module nts.uk.at.view.kdm001.m.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        updateComDayOffMana: "at/record/remainnumber/submana/comdayoff/update",
        deleteComDayOffMana: "at/record/remainnumber/submana/comdayoff/delete"
    }

    export function updateComDayOffMana(command): JQueryPromise<any> {
        return ajax(paths.updateComDayOffMana, command);
    }

    export function deleteComDayOffMana(command): JQueryPromise<any> {
        return ajax(paths.deleteComDayOffMana, command);
    }
}