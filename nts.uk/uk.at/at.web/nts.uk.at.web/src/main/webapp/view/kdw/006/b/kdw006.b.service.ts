module nts.uk.at.view.kdw006.b.service {
    let servicePath = {
        getDailyPerform: '',
        update: ''
    };

    export function update(perform) {
        let dfd = $.Deferred();
        request.ajax(servicePath.update, perform).done(function(res) {
            dfd.resolve(res);
        });
        return dfd.promise();
    }

    export function getDailyPerform() {
        let dfd = $.Deferred();
        request.ajax(servicePath.getDailyPerform).done(function(res) {
            dfd.resolve(res);
        });
        return dfd.promise();
    }
}
