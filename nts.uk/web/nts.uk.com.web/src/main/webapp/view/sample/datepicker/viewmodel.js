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
                    self.yearMonth = ko.observable(200001);
                    self.date = ko.observable(nts.uk.time.UTCDate(2000, 0, 1));
                }
                ScreenModel.prototype.pushDataUp = function () {
                    var self = this;
                    var data = new TestDto("0002", "Test Dto 0002", self.date(), self.date());
                    nts.uk.request.ajax("com", "ctx/proto/test/find", data).done(function (res) {
                        console.log(res.dateTime);
                        var dto = new TestDto(res.code, res.name, res.date, res.dateTime);
                        self.dateString(moment.utc(dto.dateTime).format());
                    });
                };
                ScreenModel.prototype.pushDataDown = function () {
                    var self = this;
                    var data = new TestDto("0001", "Test Dto 0001", self.dateString(), self.dateString());
                    nts.uk.request.ajax("com", "ctx/proto/test/find", data).done(function (res) {
                        console.log(res.dateTime);
                        var dto = new TestDto(res.code, res.name, res.date, res.dateTime);
                        self.date(dto.dateTime);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var TestDto = (function () {
                function TestDto(code, name, date, dateTime) {
                    this.code = code;
                    this.name = name;
                    this.date = date;
                    this.dateTime = moment.utc(dateTime).toDate();
                }
                return TestDto;
            }());
        })(viewmodel = datepicker.viewmodel || (datepicker.viewmodel = {}));
    })(datepicker = sample.datepicker || (sample.datepicker = {}));
})(sample || (sample = {}));
//# sourceMappingURL=viewmodel.js.map