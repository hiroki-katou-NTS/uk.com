__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.count = 10;
            var self = this;
            self.itemList = ko.observableArray([]);
            for (var i = 1; i < 10; i++) {
                self.itemList.push(new BoxModel(i, 'box ' + i));
            }
            self.selectedValue = ko.observable(new BoxModel(3, 'box 3'));
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            self.value = ko.observable(0);
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
        ScreenModel.prototype.enableCheckBox = function () {
            var self = this;
            if (self.value() < self.itemList().length - 1) {
                self.itemList()[self.value()].enable(true);
            }
        };
        return ScreenModel;
    }());
    var BoxModel = (function () {
        function BoxModel(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
            self.enable = ko.observable(id % 3 === 0);
        }
        return BoxModel;
    }());
    this.bind(new ScreenModel());
});
