__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.count = 4;
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(2, 'box 2'),
                new BoxModel(3, 'box 3')
            ]);
            self.selectedValues = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(3, 'box 3')
            ]);
            self.selectedIds = ko.observableArray([1, 2]);
            self.enable = ko.observable(true);
        }
        ScreenModel.prototype.addBoxes = function () {
            var self = this;
            self.itemList.push(new BoxModel(self.count, 'box ' + self.count));
            self.count++;
        };
        ScreenModel.prototype.removeBoxes = function () {
            var self = this;
            self.itemList.pop();
        };
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
