// TreeGrid Node
var qpp014;
(function (qpp014) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.b_stepList = [
                        { content: '.step-1' },
                        { content: '.step-2' }
                    ];
                    self.b_stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
                    self.nextScreen = ko.observable('../g/index.xhtml');
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp014.b || (qpp014.b = {}));
})(qpp014 || (qpp014 = {}));
;
