__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.linkText = ko.observable("Do something");
        }
        ScreenModel.prototype.doSomething = function (s) {
            var self = this;
            self.linkText(self.linkText() + "❤" + s);
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map