module cps008.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getDetails: "ctx/bs/person/maintenance/findOne/{0}"
    };

    export function getDetails(lid) {
        return ajax(format(paths.getDetails, lid));
    }







}