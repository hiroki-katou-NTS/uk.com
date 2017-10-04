module nts.uk.at.view.kdw006.c.service {
    let servicePath = {
        getDispRestric: '',
        update: ''
    };

    export function update(dispRestric) {
        let dfd = $.Deferred();
        request.ajax(servicePath.update, dispRestric).done(function(res) {
            dfd.resolve(res);
        });
        return dfd.promise();
    }

    export function getDispRestric() {
        let dfd = $.Deferred();
        request.ajax(servicePath.getDispRestric).done(function(res) {
            dfd.resolve(res);
        });
        return dfd.promise();
    }
}
