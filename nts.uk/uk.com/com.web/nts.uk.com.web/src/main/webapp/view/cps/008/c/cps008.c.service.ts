module cps008.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getDetails: "ctx/pereg/person/maintenance/findOne/{0}"
    };

    export function getDetails(lid) {
        return ajax(format(paths.getDetails, lid));
    }
}