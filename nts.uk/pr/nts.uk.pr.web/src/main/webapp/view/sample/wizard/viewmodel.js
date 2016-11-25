var sample;
(function (sample) {
    var wizard;
    (function (wizard) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Constructor.
                 */
                function ScreenModel() {
                    var self = this;
                    self.stepList = [
                        new Step('step-1', '.step-1'),
                        new Step('step-2', '.step-2'),
                        new Step('step-3', '.step-3'),
                        new Step('step-4', '.step-4'),
                        new Step('step-5', '.step-5'),
                        new Step('step-6', '.step-6')
                    ];
                    self.stepSelected = ko.observable(new Step('step-1', '.step-1'));
                    self.user = ko.observable(new User('U1', 'User 1'));
                }
                ScreenModel.prototype.begin = function () {
                    $('#wizard').begin();
                };
                ScreenModel.prototype.end = function () {
                    $('#wizard').end();
                };
                ScreenModel.prototype.next = function () {
                    $('#wizard').steps('next');
                };
                ScreenModel.prototype.previous = function () {
                    $('#wizard').steps('previous');
                };
                ScreenModel.prototype.getCurrentStep = function () {
                    alert($('#wizard').steps('getCurrentIndex'));
                };
                ScreenModel.prototype.goto = function () {
                    var self = this;
                    var index = self.stepList.indexOf(self.stepSelected());
                    $('#wizard').setStep(index);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
             * Class Step model.
             */
            var Step = (function () {
                function Step(id, content) {
                    this.id = id;
                    this.content = content;
                }
                return Step;
            }());
            viewmodel.Step = Step;
            var User = (function () {
                function User(code, name) {
                    this.code = ko.observable(code);
                    this.name = ko.observable(name);
                    this.name.subscribe(function (val) { alert(val); });
                }
                return User;
            }());
            viewmodel.User = User;
        })(viewmodel = wizard.viewmodel || (wizard.viewmodel = {}));
    })(wizard = sample.wizard || (sample.wizard = {}));
})(sample || (sample = {}));
