var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllClassification: "basic/organization/classification/findAllClassification",
                addClassification: "basic/organization/classification/add",
                updateClassification: "basic/organization/classification/update",
                removeClassification: "basic/organization/classification/remove"
            };
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
//# sourceMappingURL=cmm014.a.service.js.map