module nts.uk.at.view.kdm001.l.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        updateHolidaySetting: "at/record/remainnumber/submana/holidaysetting/update",
        deleteHolidaySetting: "at/record/remainnumber/submana/holidaysetting/delete"
    }

    export function updateHolidaySetting(command): JQueryPromise<any> {
        return ajax(paths.updateHolidaySetting, command);
    }

    export function deleteHolidaySetting(command): JQueryPromise<any> {
        return ajax(paths.deleteHolidaySetting, command);
    }
}