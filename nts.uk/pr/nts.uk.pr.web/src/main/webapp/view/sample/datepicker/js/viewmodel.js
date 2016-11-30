var sample;
(function (sample) {
    var datepicker;
    (function (datepicker) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Constructor.
                 */
                function ScreenModel() {
                    var self = this;
                    self.date = ko.observable(new Date('2016/01/01'));
                    self.formatDate = ko.computed(function () {
                        return nts.uk.time.formatDate(self.date(), 'yyyy/MM/dd');
                    });
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = datepicker.viewmodel || (datepicker.viewmodel = {}));
    })(datepicker = sample.datepicker || (sample.datepicker = {}));
})(sample || (sample = {}));
