var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllClassification: "basic/organization/findAllClassification",
                addClassification: "basic/organization/add",
                updateClassification: "basic/organization/update",
                removeClassification: "basic/organization/remove"
            };
            /**
             * Get list classification
             */
            function getAllClassification() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllClassification)
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
                nts.uk.request.ajax("com", paths.addClassification, classification).done(function (res) {
                    dfd.resolve(res);
                })
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
                nts.uk.request.ajax("com", paths.updateClassification, classification).done(function (res) {
                    dfd.resolve(res);
                })
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
                nts.uk.request.ajax("com", paths.removeClassification, classification).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removeClassification = removeClassification;
        })(service = a.service || (a.service = {}));
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
