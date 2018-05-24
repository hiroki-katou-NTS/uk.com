/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
	 * TabPanel Binding Handler
	 */
    class TabPanelBindingHandler implements KnockoutBindingHandler {
        /**
		 * Constructor.
		 */
        constructor() {
        }

        /**
		 * Init.
		 */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            var tabs: Array<any> = ko.unwrap(data.dataSource);
            var direction: string = ko.unwrap(data.direction || "horizontal");

            // Container.
            var container = $(element);
            if (nts.uk.util.isNullOrUndefined(container.attr("tabindex")))
                container.attr("tabindex", "0");
            
            container.data("tabindex", container.attr("tabindex"));
            // Create title.
            container.prepend('<ul></ul>');
            var titleContainer = container.children('ul');
            for (var i = 0; i < tabs.length; i++) {
                var id = tabs[i].id;
                var title = tabs[i].title;
                titleContainer.append('<li><a href="#' + id + '">' + title + '</a></li>');

                // Wrap content.
                var content = tabs[i].content;
                container.children(content).wrap('<div id="' + id + '"></div>')
            }
            container.bind("parentactived", function(evt, dataX) {
                dataX.child.find("div[role='tabpanel'][aria-hidden='false']:first").removeClass("disappear");
//                data.active.valueHasMutated();
            });
            
            container.bind("change-tab", function(e, newTabId) {
                data.active(newTabId);
                
                // nested tabの場合にpropagationすると困る。tabIdは別なので。
                e.stopPropagation();
            });
            
            container.tabs({
                create: function(event: Event, ui: any) {
                    container.find('.ui-tabs-panel').addClass('disappear');
                    ui.panel.removeClass('disappear'); 	
                },
                activate: function(evt: Event, ui: any) {
                    data.active(ui.newPanel[0].id);
                    container.find('.ui-tabs-panel').addClass('disappear');
                    ui.newPanel.removeClass('disappear');
                    container.children('ul').children('.ui-tabs-active').addClass('active');
                    container.children('ul').children('li').not('.ui-tabs-active').removeClass('active');
                    container.children('ul').children('.ui-state-disabled').addClass('disabled');
                    container.children('ul').children('li').not('.ui-state-disabled').removeClass('disabled');
                    let child = ui.newPanel.children().find(".ui-tabs:first");
                    child.trigger("parentactived", {child: child});
                }
            }).addClass(direction);
        }

        /**
		 * Update
		 */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data.
            var data = valueAccessor();
            // Get tab list.
            var tabs: Array<any> = ko.unwrap(data.dataSource);
            // Container.
            var container = $(element);
            // Select tab.
            var activeTab = _.find(tabs, function(tab) {
                return tab.id == data.active();
            });
            var indexActive = tabs.indexOf(activeTab);
            let oldIndexActive = container.tabs("option", "active");
            if (oldIndexActive !== indexActive) {
                container.tabs("option", "active", indexActive);
            }

            if (!activeTab.enable() || !activeTab.visible()) {
                let firstActiveTab = _.find(tabs, function(tab) {
                    return tab.enable() && tab.visible();
                });
                if (!nts.uk.util.isNullOrUndefined(firstActiveTab)) {
                    data.active(firstActiveTab.id);
                    var firstIndexActive = tabs.indexOf(firstActiveTab);
                    container.tabs("option", "active", firstIndexActive);
                }
            }

            // Disable & visible tab.
            tabs.forEach(tab => {
                if (tab.enable()) {
                    container.tabs("enable", '#' + tab.id);
                    container.children('#' + tab.id).children('div').show();
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').removeClass('disabled');
                } else {
                    container.tabs("disable", '#' + tab.id);
                    container.children('#' + tab.id).children('div').hide();
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').addClass('disabled');
                }
                if (!tab.visible()) {
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').hide();
                } else {
                    container.children('ul').children('li[aria-controls="' + tab.id + '"]').show();
                }
            });
		
		    container.attr('tabindex', container.data("tabindex"));	
            _.defer(() => { container.children('ul').children('li').attr("tabindex", container.data("tabindex")); });
        }
    }

    ko.bindingHandlers['ntsTabPanel'] = new TabPanelBindingHandler();
}
