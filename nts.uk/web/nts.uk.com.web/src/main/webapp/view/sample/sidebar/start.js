__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.show = ko.observable(true);
            this.show.subscribe(function (newVal) {
                if (newVal)
                    $("#sidebar").ntsSideBar("show", 1);
                else
                    $("#sidebar").ntsSideBar("hide", 1);
            });
            this.enable = ko.observable(true);
            this.enable.subscribe(function (newVal) {
                if (newVal) {
                    $("#sidebar").ntsSideBar("enable", 1);
                    $("#sidebar").ntsSideBar("enable", 2);
                }
                else {
                    $("#sidebar").ntsSideBar("disable", 1);
                    $("#sidebar").ntsSideBar("disable", 2);
                }
            });
        }
        ScreenModel.prototype.testSideMenu = function () {
            alert("clicked");
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
