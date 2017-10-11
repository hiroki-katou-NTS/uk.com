module nts.uk.at.view.kdw006.c.service {
    let servicePath = {
        getDispRestric: 'at/record/workrecord/operationsetting/disp-rest',
        update: 'at/record/workrecord/operationsetting/register-disp-rest'
    };

    export function update(dispRestric: any): JQueryPromise<any>{
        return nts.uk.request.ajax(servicePath.update, dispRestric);
    }

    export function getDispRestric(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.getDispRestric);
    }
}
