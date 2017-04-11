var screenQmm019;
var fadeVisibleCustome = (function () {
    function fadeVisibleCustome() {
    }
    fadeVisibleCustome.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        var shouldDisplay = valueAccessor();
        $(element).toggle(shouldDisplay);
    };
    fadeVisibleCustome.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        var shouldDisplay = valueAccessor();
        shouldDisplay ? $(element).fadeIn() : $(element).fadeOut();
    };
    return fadeVisibleCustome;
}());
ko.bindingHandlers["fadeVisible"] = new fadeVisibleCustome();
var qmm019;
(function (qmm019) {
    var z;
    (function (z) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.survey = new SurveyViewModel("Binh chon Qua bong vang 2017", 10, ["Messi", "Ronaldo", "Neymar"]);
            }
            return ScreenModel;
        }());
        z.ScreenModel = ScreenModel;
        var Answer = (function () {
            function Answer(text) {
                this.text = text;
                this.points = ko.observable(1);
            }
            return Answer;
        }());
        var SurveyViewModel = (function () {
            function SurveyViewModel(question, pointsBudget, answers) {
                var self = this;
                self.question = question;
                self.pointsBudget = pointsBudget;
                self.answers = $.map(answers, function (text) {
                    return new Answer(text);
                });
                self.pointsUsed = ko.computed(function () {
                    var total = 0;
                    for (var _i = 0, _a = self.answers; _i < _a.length; _i++) {
                        var answer = _a[_i];
                        total += answer.points();
                    }
                    return total;
                }, self);
                self.enableSave = ko.computed(function () {
                    return self.pointsUsed() - self.pointsBudget <= 0;
                }, this);
            }
            SurveyViewModel.prototype.save = function () {
                alert("to do");
            };
            return SurveyViewModel;
        }());
    })(z = qmm019.z || (qmm019.z = {}));
})(qmm019 || (qmm019 = {}));
;
