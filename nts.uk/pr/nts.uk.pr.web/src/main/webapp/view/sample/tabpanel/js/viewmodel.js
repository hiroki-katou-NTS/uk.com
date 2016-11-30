var sample;
(function (sample) {
    var tabpanel;
    (function (tabpanel) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Constructor.
                 */
                function ScreenModel() {
                    var self = this;
                    self.tabs = ko.observableArray([new TabModel('tab-1', 'Tab Title 1', '.tab-content-1'),
                        new TabModel('tab-2', 'Tab Title 2', '.tab-content-2'),
                        new TabModel('tab-3', 'Tab Title 3', '.tab-content-3'),
                        new TabModel('tab-4', 'Tab Title 4', '.tab-content-4')]);
                    self.user = ko.observable(new User(''));
                    self.selectedTab = ko.observable('tab-2');
                    // Force update tabs when update element.
                    self.tabs().forEach(function (tab) {
                        tab.enable.subscribe(function (val) {
                            self.tabs.valueHasMutated();
                        });
                        tab.visible.subscribe(function (val) {
                            self.tabs.valueHasMutated();
                        });
                    });
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
             * Class Item model.
             */
            var TabModel = (function () {
                function TabModel(id, title, content) {
                    this.id = id;
                    this.title = title;
                    this.content = content;
                    this.visible = ko.observable(true);
                    this.enable = ko.observable(true);
                }
                return TabModel;
            }());
            viewmodel.TabModel = TabModel;
            var User = (function () {
                function User(name) {
                    this.name = ko.observable(name);
                }
                return User;
            }());
            viewmodel.User = User;
        })(viewmodel = tabpanel.viewmodel || (tabpanel.viewmodel = {}));
    })(tabpanel = sample.tabpanel || (sample.tabpanel = {}));
})(sample || (sample = {}));
