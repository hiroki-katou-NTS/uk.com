__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
        }
    }
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map
