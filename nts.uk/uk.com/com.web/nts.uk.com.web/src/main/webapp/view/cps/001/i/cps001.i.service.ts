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

    export function getData() {
        return ajax(paths.getData);
    }

    export function getDetail(id: any) {
        return ajax(format(paths.getDetail, id));
    }

    export function save(command: any) {
        return ajax(paths.save, command);
    }

    export function remove(id: any) {
        return ajax(format(paths.delete, id));
    }
}