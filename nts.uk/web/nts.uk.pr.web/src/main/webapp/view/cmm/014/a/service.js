var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllClassification: "pr/basic/organization/findAllClassification",
                addClassification: "pr/basic/organization/add",
                updateClassification: "pr/basic/organization/update",
                removeClassification: "pr/basic/organization/remove"
            };
            /**
             * Get list classification
             */
            function getAllClassification() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllClassification)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllClassification = getAllClassification;
            /**
            * update Classification
            */
            function addClassification(classification) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addClassification, classification).done()
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addClassification = addClassification;
            /**
             * update Classification
             */
            function updateClassification(classification) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateClassification, classification).done()
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateClassification = updateClassification;
            /**
            * remove Classification
            */
            function removeClassification(classification) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.removeClassification, classification).done()
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removeClassification = removeClassification;
        })(service = a.service || (a.service = {}));
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
