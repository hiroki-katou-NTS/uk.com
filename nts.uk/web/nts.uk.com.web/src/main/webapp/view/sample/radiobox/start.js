__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.enable = ko.observable(true);
            self.items = ko.observableArray([
                { value: 1, text: 'One' },
                { value: 2, text: 'Two' },
                { value: 3, text: 'Three' }
            ]);
            self.selectedValue = ko.observable(1);
        }
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map