__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.date = ko.observable(new Date('2016/12/01'));
            self.show = ko.observable(true);
            self.itemList = ko.observableArray([]);
            self.btnText = ko.computed(function () { if (self.show())
                return "-"; return "+"; });
            for (var i = 1; i <= 12; i++) {
                self.itemList.push({ index: i });
            }
        }
        ScreenModel.prototype.toggle = function () {
            this.show(!this.show());
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
