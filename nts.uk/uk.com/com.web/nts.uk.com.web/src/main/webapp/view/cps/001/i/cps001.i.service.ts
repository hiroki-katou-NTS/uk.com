module nts.uk.com.view.cps001.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getAllList': 'at/record/remainnumber/special/getall',
        'getAllListByCheckState': 'at/record/remainnumber/special/getall',
        'getDetail': 'at/record/remainnumber/special/getdetailemployeetodelete/{0}',
        'save': 'at/record/remainnumber/special/restoredata',
        lostFocus: "at/record/remainnumber/annlea/lostFocus",
        'delete': 'at/record/remainnumber/special/deleteemp/{0}'
    };

    export function getAllList() {
        return ajax('at',paths.getAllList);
    }

    export function getAllListByCheckState(employeeId: string, checkState: boolean) {
        return ajax(format(paths.getAllListByCheckState, employeeId, checkState));
    }

    export function save(command: any) {
        return ajax(paths.save, command);
    }

    export function getDetail(grantDate: Date) {
        return ajax(paths.getDetails, moment.utc(grantDate, "YYYY/MM/DD"));
    }

    export function remove(id: any) {
        return ajax(format(paths.delete, id));
    }
}