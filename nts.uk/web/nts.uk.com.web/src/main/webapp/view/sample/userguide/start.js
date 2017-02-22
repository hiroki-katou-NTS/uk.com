__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            // Input Text
            self.inputText = ko.observable("a");
            self.inputText.subscribe(function (value) {
                if (value.length > 0)
                    $(".userguide-right").ntsUserGuide("hide");
                else
                    $(".userguide-right").ntsUserGuide("show");
            });
            // CheckBox
            self.checked = ko.observable(true);
            self.checked.subscribe(function (value) {
                (value) ? $(".userguide-left").ntsUserGuide("hide") : $(".userguide-left").ntsUserGuide("show");
            });
            // Init UserGuide
            $("[data-toggle='userguide']").ntsUserGuide();
        }
        ScreenModel.prototype.showOverlayTop = function () {
            $(".userguide-top").ntsUserGuide("toggle");
        };
        ScreenModel.prototype.showOverlayBottom = function () {
            $(".userguide-bottom").ntsUserGuide("toggle");
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
