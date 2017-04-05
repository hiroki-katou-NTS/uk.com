var sample;
(function (sample) {
    var datepicker;
    (function (datepicker) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.date = ko.observable('');
                    self.date1 = ko.observable('20161201');
                    self.yearMonth = ko.observable('201601');
                    self.year = ko.observable('2016');
                    self.month = ko.observable('01');
                    self.day = ko.observable('01');
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = datepicker.viewmodel || (datepicker.viewmodel = {}));
    })(datepicker = sample.datepicker || (sample.datepicker = {}));
})(sample || (sample = {}));
