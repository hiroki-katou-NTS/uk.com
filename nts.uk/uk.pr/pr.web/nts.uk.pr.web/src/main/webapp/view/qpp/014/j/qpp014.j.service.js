var qpp014;
(function (qpp014) {
    var j;
    (function (j) {
        var service;
        (function (service) {
            var paths = {
                saveAsPdfA: "screen/pr/QPP014/saveAsPdfA",
                saveAsPdfB: "screen/pr/QPP014/saveAsPdfB"
            };
            function saveAsPdfA(command) {
                return nts.uk.request.exportFile(paths.saveAsPdfA, command);
            }
            service.saveAsPdfA = saveAsPdfA;
            function saveAsPdfB(command) {
                return nts.uk.request.exportFile(paths.saveAsPdfB, command);
            }
            service.saveAsPdfB = saveAsPdfB;
        })(service = j.service || (j.service = {}));
    })(j = qpp014.j || (qpp014.j = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.j.service.js.map