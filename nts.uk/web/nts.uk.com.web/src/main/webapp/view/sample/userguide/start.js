__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.dataList = ko.observableArray([]);
            for (var i = 0; i <= 10; i++) {
                self.dataList.push({ id: i, name: "Item " + i });
            }
            $(".userguide").ntsUserGuide();
            $(".userguide2").ntsUserGuide();
        }
        ScreenModel.prototype.showOverlay = function () {
            $(".userguide").ntsUserGuide("show");
        };
        ScreenModel.prototype.showOverlay2 = function () {
            $(".userguide2").ntsUserGuide("show");
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
