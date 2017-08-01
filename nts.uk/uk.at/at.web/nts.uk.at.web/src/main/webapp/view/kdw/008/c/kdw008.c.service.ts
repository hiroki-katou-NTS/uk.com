module nts.uk.at.view.kdw008.c {
    export module service {
        let servicePath = {
            update: ''
        };

        export function update(updateData: any) {
            var dfd = $.Deferred();
            request.ajax(servicePath.update, updateData).done(function(res) {
                dfd.resolve(res);
            });
            return dfd.promise();
        };
    }
}
