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
        ScreenModel.prototype.openSubWindow = function () {
            nts.uk.ui.windows.sub.modeless("/view/sample/sidebar/sidebar-sub.xhtml");
        };
        ScreenModel.prototype.openNewTab = function () {
            window.open("/nts.uk.com.web/view/sample/sidebar/sidebar-sub.xhtml", "_blank").focus();
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map