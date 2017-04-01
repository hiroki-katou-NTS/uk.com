var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var k;
                    (function (k) {
                        var service;
                        (function (service) {
                            var pathService = {
                                loadDemensionSelectionList: "pr/proto/wagetable/demensions"
                            };
                            //fuction connection service load demension
                            function loadDemensionSelectionList() {
                                return nts.uk.request.ajax(pathService.loadDemensionSelectionList);
                            }
                            service.loadDemensionSelectionList = loadDemensionSelectionList;
                            var model;
                            (function (model) {
                                var DemensionItemDto = (function () {
                                    function DemensionItemDto() {
                                    }
                                    return DemensionItemDto;
                                }());
                                model.DemensionItemDto = DemensionItemDto;
                            })(model = service.model || (service.model = {}));
                        })(service = k.service || (k.service = {}));
                    })(k = qmm016.k || (qmm016.k = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm016.k.service.js.map