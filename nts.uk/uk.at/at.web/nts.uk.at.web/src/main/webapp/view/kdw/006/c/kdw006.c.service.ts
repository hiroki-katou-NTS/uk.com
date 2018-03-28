module nts.uk.at.view.kdw006.c.service {
    let servicePath = {
        getFuncRestric: 'at/record/workrecord/operationsetting/getIdentity',
        update: 'at/record/workrecord/operationsetting/register-func-rest'
    };

    export function update(dispRestric: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.update, dispRestric);
    }

    export function getFuncRestric(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.getFuncRestric);
    }
}
