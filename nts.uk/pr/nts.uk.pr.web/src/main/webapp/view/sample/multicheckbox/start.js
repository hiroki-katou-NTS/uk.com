__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.boxes = ko.observableArray([
                new BoxModel(1, 'box 1'), new BoxModel(2, 'box 2'), new BoxModel(3, 'box 3')
            ]);
            self.selectedBoxes = ko.observableArray([]);
        }
        return ScreenModel;
    }());
    var BoxModel = (function () {
        function BoxModel(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
        return BoxModel;
    }());
    this.bind(new ScreenModel());
});
