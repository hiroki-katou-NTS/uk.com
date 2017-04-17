var cmm015;
(function (cmm015) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllPayClassification: "basic/payclassification/findAllPayClassification",
                addPayClassification: "basic/payclassification/add",
                updatePayClassification: "basic/payclassification/update",
                removePayClassification: "basic/payclassification/remove"
            };
            function getAllPayClassification() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllPayClassification)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllPayClassification = getAllPayClassification;
            function addPayClassification(payClassification) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addPayClassification, payClassification).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addPayClassification = addPayClassification;
            function updatePayClassification(payClassification) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatePayClassification, payClassification).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updatePayClassification = updatePayClassification;
            function removePayClassification(payClassification) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.removePayClassification, payClassification).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removePayClassification = removePayClassification;
        })(service = a.service || (a.service = {}));
    })(a = cmm015.a || (cmm015.a = {}));
})(cmm015 || (cmm015 = {}));
//# sourceMappingURL=cmm015.a.service.js.map