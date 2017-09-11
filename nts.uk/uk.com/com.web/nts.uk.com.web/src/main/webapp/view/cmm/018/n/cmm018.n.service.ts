module cmm018.n.service {
    let servicePath = {
        getRightList: 'workflow/approvermanagement/workroot/find/applicationType'
    };

    export function getRightList() {
        let dfd = $.Deferred();
        nts.uk.request.ajax(servicePath.getRightList).done(function(res) {
            dfd.resolve(res);
        });
        return dfd.promise();
    };
}
