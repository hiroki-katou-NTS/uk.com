var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var tabpanel;
            (function (tabpanel) {
                var viewmodel;
                (function (viewmodel) {
                    var ScreenModel = (function () {
                        function ScreenModel() {
                            var self = this;
                            self.tabs = ko.observableArray([
                                { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                                { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
                            ]);
                            self.selectedTab = ko.observable('tab-2');
                        }
                        return ScreenModel;
                    }());
                    viewmodel.ScreenModel = ScreenModel;
                })(viewmodel = tabpanel.viewmodel || (tabpanel.viewmodel = {}));
            })(tabpanel = ui.tabpanel || (ui.tabpanel = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
