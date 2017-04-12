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
                    // Un-comment to see diffirent between Date and UTC Date 
                    //self.date = ko.observable(new Date(2000,1,2));
                    self.date = ko.observable(new Date(Date.UTC(2000, 0, 1)));
                    self.yearMonth = ko.observable(200001);
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = datepicker.viewmodel || (datepicker.viewmodel = {}));
    })(datepicker = sample.datepicker || (sample.datepicker = {}));
})(sample || (sample = {}));
