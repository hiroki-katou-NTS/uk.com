module nts.uk.at.view.kdw006.b.service {
    let servicePath = {
        getDailyPerform: 'at/record/workrecord/operationsetting/find',
        update: 'at/record/workrecord/operationsetting/register'
    };

    export function update(perform) : JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.update, perform);
    }

    export function getDailyPerform() : JQueryPromise<any>{
        return nts.uk.request.ajax(servicePath.getDailyPerform);
    }
}
