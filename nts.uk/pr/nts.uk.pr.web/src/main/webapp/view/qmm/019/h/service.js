var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "pr/proto/personalwage/findPersonalWageName"
            };
            function getPersonalWageNames() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getLayoutInfor)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPersonalWageNames = getPersonalWageNames;
            var model;
            (function (model) {
                // layout
                var PersonalWageNameDto = (function () {
                    function PersonalWageNameDto() {
                    }
                    return PersonalWageNameDto;
                }());
                model.PersonalWageNameDto = PersonalWageNameDto;
            })(model = service.model || (service.model = {}));
        })(service = h.service || (h.service = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
