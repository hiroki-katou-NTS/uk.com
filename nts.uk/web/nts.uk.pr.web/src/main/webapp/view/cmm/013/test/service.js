var cmm013;
(function (cmm013) {
    var test;
    (function (test) {
        var service;
        (function (service) {
            var paths = {
                findAllPosition: "basic/position/findallposition/",
                addPosition: "basic/position/addPosition",
                deletePosition: "basic/position/deletePosition",
                updatePosition: "basic/position/updatePosition",
                getAllHistory: "basic/position/getallhist"
            };
            function findAllPosition(historyId) {
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
            service.findAllPosition = findAllPosition;
            /**
             * add Position
             */
            function addPosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addPosition, position)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addPosition = addPosition;
            /**
             * update Position
             */
            function updatePosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatePosition, position).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updatePosition = updatePosition;
            /**
            * delete Position
            */
            function deletePosition(position) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deletePosition, position).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deletePosition = deletePosition;
            /**
            * get all history
            */
            function getAllHistory() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllHistory)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllHistory = getAllHistory;
            //model
            var model;
            (function (model) {
                var ListHistoryDto = (function () {
                    function ListHistoryDto(startDate, endDate, historyId) {
                        var self = this;
                        self.startDate = startDate;
                        self.endDate = endDate;
                        self.historyId = historyId;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
                var ListPositionDto = (function () {
                    function ListPositionDto(code, name, presenceCheckScopeSet, memo) {
                        var self = this;
                        self.jobCode = code;
                        self.jobName = name;
                        self.presenceCheckScopeSet = presenceCheckScopeSet;
                        self.memo = memo;
                    }
                    return ListPositionDto;
                }());
                model.ListPositionDto = ListPositionDto;
                var DeletePositionCommand = (function () {
                    function DeletePositionCommand(jobCode, historyId) {
                        this.jobCode = jobCode;
                        this.historyId = historyId;
                    }
                    return DeletePositionCommand;
                }());
                model.DeletePositionCommand = DeletePositionCommand;
            })(model = service.model || (service.model = {}));
        })(service = test.service || (test.service = {}));
    })(test = cmm013.test || (cmm013.test = {}));
})(cmm013 || (cmm013 = {}));
