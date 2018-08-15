module nts.uk.at.view.kmf004.g.service {
    import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;

    var paths: any = {
        findAll: "at/shared/relationship/findAllWithSetting/{0}",
        update: "shared/specialholiday/specialholidayevent/grant-day-per-relationship/save",
        remove: "shared/specialholiday/specialholidayevent/grant-day-per-relationship/delete/{0}/{1}",
        findByCode: "shared/specialholiday/specialholidayevent/grant-day-per-relationship/change-special-event/{0}/{1}"
    }

    export function findAll(sHENo) {
        return ajax(format(paths.findAll, sHENo));
    }
    export function update(command): JQueryPromise<void> {
        return ajax(paths.update, command);
    }
    export function remove(sHENo, relpCd): JQueryPromise<void> {
        return ajax(format(paths.remove, sHENo, relpCd));
    }

    export function findByCode(sHENo, relpCd): JQueryPromise<void> {
        return ajax(format(paths.findByCode, sHENo, relpCd));
    }
}   