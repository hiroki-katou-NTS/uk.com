__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.dataList = ko.observableArray([]);
            for (var i = 0; i <= 10; i++) {
                self.dataList.push({ id: i, name: "Item " + i });
            }
            // Init UserGuide
            $("[data-toggle='userguide']").ntsUserGuide();
        }
        ScreenModel.prototype.showOverlayLeft = function () {
            $(".userguide-left").ntsUserGuide("show");
        };
        ScreenModel.prototype.showOverlayRight = function () {
            $(".userguide-right").ntsUserGuide("show");
        };
        ScreenModel.prototype.showOverlayTop = function () {
            $(".userguide-top").ntsUserGuide("show");
        };
        ScreenModel.prototype.showOverlayBottom = function () {
            $(".userguide-bottom").ntsUserGuide("show");
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
