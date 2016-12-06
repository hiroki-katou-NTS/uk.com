__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.constraint = 'LayoutCode';
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
