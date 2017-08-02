module nts.uk.at.view.kdw008.c {
    export module service {
        let servicePath = {
            findAll: '',
            update: ''
        };

        export function findAll(idList: any) {
            var dfd = $.Deferred();
            request.ajax(servicePath.findAll + idList).done(function(res) {
                dfd.resolve(res);
            });
            return dfd.promise();
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
