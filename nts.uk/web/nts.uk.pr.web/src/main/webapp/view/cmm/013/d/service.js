var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getPositionInfor: "basic/position/findallposition/",
                pathDeletePosition: "basic/position/deletedata",
                pathUpdatePosition: "basic/position/updatedata"
            };
            /**
             * Get list layout master new history
             */
            function getPosition(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findAllPosition + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPosition = getPosition;
            function deletePosition(position) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.pathDeleteLayout, position);
                nts.uk.request.ajax(paths.pathDeletePosition, position)
                    .done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deletePosition = deletePosition;
            function updatePosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.pathUpdatePosition, position).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updatePosition = updatePosition;
            /**
                    * Model namespace.
                 */
            var model;
            (function (model) {
                // layout
                var JobHistDto = (function () {
                    function JobHistDto() {
                    }
                    return JobHistDto;
                }());
                model.JobHistDto = JobHistDto;
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
