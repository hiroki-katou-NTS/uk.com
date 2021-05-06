module nts.uk.at.view.kdw006.c.service {
    let servicePath = {
        start: 'at/record/workrecord/manhourrecordusesetting/start',
    };

    export function start(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.start);
    }
}
