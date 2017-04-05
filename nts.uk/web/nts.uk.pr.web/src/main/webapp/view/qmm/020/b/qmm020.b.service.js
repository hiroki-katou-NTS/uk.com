var qmm020;
(function (qmm020) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            //duong dan   
            var paths = {
                getAllotCompanySettingList: "pr/core/allot/findallcompanyallot",
                getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}",
                getMaxDate: "pr/core/allot/findcompanyallotmaxdate",
                updateAllotCompanySetting: "pr/core/allot/update",
                insertAllotCompanySetting: "pr/core/allot/insert"
            };
            /**
             * Get list payment date processing.
             */
            function getAllotCompanyList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllotCompanySettingList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllotCompanyList = getAllotCompanyList;
            /**
             * Get layout master name
             */
            function getAllotLayoutName(stmtCode) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.getLayoutName, stmtCode);
                var options = {
                    dataType: 'text',
                    contentType: 'text/plain'
                };
                nts.uk.request.ajax(_path, undefined, options)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllotLayoutName = getAllotLayoutName;
            /**
             * get Data of Item with Max End date
             */
            function getAllotCompanyMaxDate() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getMaxDate)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllotCompanyMaxDate = getAllotCompanyMaxDate;
            //Update company code 
            function updateComAllot(updateAllotCompanyCommand) {
                var dfd = $.Deferred();
                var command = {};
                command.payStmtCode = updateAllotCompanyCommand.paymentDetailCode;
                command.bonusStmtCode = updateAllotCompanyCommand.bonusDetailCode;
                command.startDate = updateAllotCompanyCommand.startDate;
                command.endDate = updateAllotCompanyCommand.endDate;
                command.historyId = updateAllotCompanyCommand.historyId;
                nts.uk.request.ajax(paths.updateAllotCompanySetting, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateComAllot = updateComAllot;
            function insertComAllot(insertAllotCompanyCommand) {
                var dfd = $.Deferred();
                var command = {};
                command.payStmtCode = insertAllotCompanyCommand.paymentDetailCode;
                command.bonusStmtCode = insertAllotCompanyCommand.bonusDetailCode;
                command.startDate = insertAllotCompanyCommand.startDate;
                command.endDate = insertAllotCompanyCommand.endDate;
                command.historyId = insertAllotCompanyCommand.historyId;
                //command.isCopy = insertAllotCompanyCommand.isCopy;
                debugger;
                nts.uk.request.ajax(paths.insertAllotCompanySetting, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.insertComAllot = insertComAllot;
            /**
             *
             *
             */
            var model;
            (function (model) {
                var CompanyAllotSettingDto = (function () {
                    function CompanyAllotSettingDto() {
                    }
                    return CompanyAllotSettingDto;
                }());
                model.CompanyAllotSettingDto = CompanyAllotSettingDto;
                var LayoutDto = (function () {
                    function LayoutDto() {
                    }
                    return LayoutDto;
                }());
                model.LayoutDto = LayoutDto;
            })(model = service.model || (service.model = {}));
        })(service = b.service || (b.service = {}));
    })(b = qmm020.b || (qmm020.b = {}));
})(qmm020 || (qmm020 = {}));
