module cmm018.n.service {
    let servicePath = {
        getRightList: 'workflow/approvermanagement/workroot/find/applicationType'
    };

    export function getRightList() {
        let dfd = $.Deferred();
        nts.uk.request.ajax(servicePath.getRightList);
        return dfd.promise();
    };
}
