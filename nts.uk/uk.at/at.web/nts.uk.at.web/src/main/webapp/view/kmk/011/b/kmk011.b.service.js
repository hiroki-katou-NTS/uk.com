var kmk011;
(function (kmk011) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                getAllDivReason: "at/record/divergencetime/getalldivreason/",
                addDivReason: "at/record/divergencetime/adddivreason",
                updateDivReason: "at/record/divergencetime/updatedivreason",
                deleteDivReason: "at/record/divergencetime/deletedivreason"
            };
            /**
            * get all divergence reason
            */
            function getAllDivReason(divTimeId) {
                return nts.uk.request.ajax("at", paths.getAllDivReason + divTimeId);
            }
            service.getAllDivReason = getAllDivReason;
            /**
            * add divergence reason
            */
            function addDivReason(divReason) {
                return nts.uk.request.ajax("at", paths.addDivReason, divReason);
            }
            service.addDivReason = addDivReason;
            /**
            * update divergence reason
            */
            function updateDivReason(divReason) {
                return nts.uk.request.ajax("at", paths.updateDivReason, divReason);
            }
            service.updateDivReason = updateDivReason;
            /**
            * delete divergence reason
            */
            function deleteDivReason(divReason) {
                return nts.uk.request.ajax("at", paths.deleteDivReason, divReason);
            }
            service.deleteDivReason = deleteDivReason;
        })(service = b.service || (b.service = {}));
    })(b = kmk011.b || (kmk011.b = {}));
})(kmk011 || (kmk011 = {}));
//# sourceMappingURL=kmk011.b.service.js.map