var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var TabPanelBindingHandler = (function () {
                    function TabPanelBindingHandler() {
                    }
                    TabPanelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var tabs = ko.unwrap(data.dataSource);
                        var direction = ko.unwrap(data.direction || "horizontal");
                        var container = $(element);
                        if (nts.uk.util.isNullOrUndefined(container.attr("tabindex")))
                            container.attr("tabindex", "0");
                        container.data("tabindex", container.attr("tabindex"));
                        container.prepend('<ul></ul>');
                        var titleContainer = container.children('ul');
                        for (var i = 0; i < tabs.length; i++) {
                            var id = tabs[i].id;
                            var title = tabs[i].title;
                            titleContainer.append('<li><a href="#' + id + '">' + title + '</a></li>');
                            var content = tabs[i].content;
                            container.children(content).wrap('<div id="' + id + '"></div>');
                        }
                        container.bind("parentactived", function (evt, dataX) {
                            dataX.child.find("div[role='tabpanel'][aria-hidden='false']:first").removeClass("disappear");
                        });
                        container.bind("change-tab", function (e, newTabId) {
                            data.active(newTabId);
                            e.stopPropagation();
                        });
                        container.tabs({
                            create: function (event, ui) {
                                container.find('.ui-tabs-panel').addClass('disappear');
                                ui.panel.removeClass('disappear');
                            },
                            activate: function (evt, ui) {
                                data.active(ui.newPanel[0].id);
                                container.find('.ui-tabs-panel').addClass('disappear');
                                ui.newPanel.removeClass('disappear');
                                container.children('ul').children('.ui-tabs-active').addClass('active');
                                container.children('ul').children('li').not('.ui-tabs-active').removeClass('active');
                                container.children('ul').children('.ui-state-disabled').addClass('disabled');
                                container.children('ul').children('li').not('.ui-state-disabled').removeClass('disabled');
                                var child = ui.newPanel.children().find(".ui-tabs:first");
                                child.trigger("parentactived", { child: child });
                            }
                        }).addClass(direction);
                    };
                    TabPanelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var tabs = ko.unwrap(data.dataSource);
                        var container = $(element);
                        var activeTab = _.find(tabs, function (tab) {
                            return tab.id == data.active();
                        });
                        var indexActive = tabs.indexOf(activeTab);
                        var oldIndexActive = container.tabs("option", "active");
                        if (oldIndexActive !== indexActive) {
                            container.tabs("option", "active", indexActive);
                        }
                        if (!activeTab.enable() || !activeTab.visible()) {
                            var firstActiveTab = _.find(tabs, function (tab) {
                                return tab.enable() && tab.visible();
                            });
                            if (!nts.uk.util.isNullOrUndefined(firstActiveTab)) {
                                data.active(firstActiveTab.id);
                                var firstIndexActive = tabs.indexOf(firstActiveTab);
                                container.tabs("option", "active", firstIndexActive);
                            }
                        }
                        tabs.forEach(function (tab) {
                            if (tab.enable()) {
                                container.tabs("enable", '#' + tab.id);
                                container.children('#' + tab.id).children('div').show();
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').removeClass('disabled');
                            }
                            else {
                                container.tabs("disable", '#' + tab.id);
                                container.children('#' + tab.id).children('div').hide();
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').addClass('disabled');
                            }
                            if (!tab.visible()) {
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').hide();
                            }
                            else {
                                container.children('ul').children('li[aria-controls="' + tab.id + '"]').show();
                            }
                        });
                        container.attr('tabindex', container.data("tabindex"));
                        _.defer(function () { container.children('ul').children('li').attr("tabindex", container.data("tabindex")); });
                    };
                    return TabPanelBindingHandler;
                }());
                ko.bindingHandlers['ntsTabPanel'] = new TabPanelBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=tabpanel-ko-ext.js.map