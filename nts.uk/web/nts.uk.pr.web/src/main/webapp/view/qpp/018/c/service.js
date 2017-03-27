var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp018;
                (function (qpp018) {
                    var c;
                    (function (c) {
                        var service;
                        (function (service) {
                            // Service paths.
                            var paths = {
                                findCheckListPrintSetting: "ctx/pr/report/insurance/checklist/find",
                                saveCheckListPrintSetting: "ctx/pr/report/insurance/checklist/save"
                            };
                            /**
                             * get All CheckList Print Setting
                             */
                            function findCheckListPrintSetting() {
                                return nts.uk.request.ajax(paths.findCheckListPrintSetting);
                            }
                            service.findCheckListPrintSetting = findCheckListPrintSetting;
                            function saveCheckListPrintSetting(command) {
                                return nts.uk.request.ajax(paths.saveCheckListPrintSetting, command);
                            }
                            service.saveCheckListPrintSetting = saveCheckListPrintSetting;
                            var model;
                            (function (model) {
                                var CheckListPrintSettingDto = (function () {
                                    function CheckListPrintSettingDto() {
                                    }
                                    return CheckListPrintSettingDto;
                                }());
                                model.CheckListPrintSettingDto = CheckListPrintSettingDto;
                            })(model = service.model || (service.model = {}));
                        })(service = c.service || (c.service = {}));
                    })(c = qpp018.c || (qpp018.c = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map