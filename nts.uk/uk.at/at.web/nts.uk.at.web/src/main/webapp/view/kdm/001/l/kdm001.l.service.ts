module nts.uk.at.view.kdm001.l.service {
    import ajax    = nts.uk.request.ajax;
    import format  = nts.uk.text.format;
    let paths: any = {
        updateHolidaySetting : "at/record/remainnumber/submana/holidaysetting/update",
        deleteHolidaySetting : "at/record/remainnumber/submana/holidaysetting/delete",
        checkValidate        : "at/record/remainnumber/submana/holidaysetting/checkValidate"
    }

    export function updateHolidaySetting(command): JQueryPromise<any> {
        return ajax(paths.updateHolidaySetting, command);
    }

    export function deleteHolidaySetting(command): JQueryPromise<any> {
        return ajax(paths.deleteHolidaySetting, command);
     }

    export function checkValidate(command): JQueryPromise<any> {
        return ajax(paths.checkValidate, command);
    }
}