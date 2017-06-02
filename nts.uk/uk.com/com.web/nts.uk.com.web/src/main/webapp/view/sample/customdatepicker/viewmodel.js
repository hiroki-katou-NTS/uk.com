var sample;
(function (sample) {
    var datepicker;
    (function (datepicker) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dateString = ko.observable('20000101');
                    self.yearMonth = ko.observable("200002");
                    // Define styles
                    self.cssRangerYM = { 2000: [{ 1: "round-green" }, { 5: "round-yellow" }],
                        2002: [1, { 5: "round-gray" }] };
                    self.cssRangerYMD = {
                        2000: { 1: [{ 11: "round-green" }, { 12: "round-orange" }, { 15: "rect-pink" }], 3: [{ 1: "round-green" }, { 2: "round-purple" }, 3] },
                        2002: { 1: [{ 11: "round-green" }, { 12: "round-green" }, { 15: "round-green" }], 3: [{ 1: "round-green" }, { 2: "round-green" }, { 3: "round-green" }] }
                    };
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = datepicker.viewmodel || (datepicker.viewmodel = {}));
    })(datepicker = sample.datepicker || (sample.datepicker = {}));
})(sample || (sample = {}));
//# sourceMappingURL=viewmodel.js.map