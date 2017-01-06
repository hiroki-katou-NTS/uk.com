__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.dataList = ko.observableArray([]);
            for (var i = 0; i <= 10; i++) {
                self.dataList.push({ id: i, name: "Item " + i });
            }
        }
        ScreenModel.prototype.showOverlay = function () {
            $(".userguide-overlay").show();
        };
        ScreenModel.prototype.hideOverlay = function () {
            $(".userguide-overlay").hide();
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
