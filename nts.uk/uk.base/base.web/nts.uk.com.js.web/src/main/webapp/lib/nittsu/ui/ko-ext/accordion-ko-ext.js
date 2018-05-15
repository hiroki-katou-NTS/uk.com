var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsAccordionBindingHandler = (function () {
                    function NtsAccordionBindingHandler() {
                    }
                    NtsAccordionBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var active = (data.active !== undefined) ? (data.active) : ko.observable(false);
                        var realActive = (nts.uk.ntsNumber.isNumber(ko.unwrap(active))) ? Number(ko.unwrap(active)) : ko.unwrap(active);
                        var animate = (data.animate !== undefined) ? ko.unwrap(data.animate) : {};
                        var collapsible = (data.collapsible !== undefined) ? ko.unwrap(data.collapsible) : true;
                        var event = (data.event !== undefined) ? ko.unwrap(data.event) : "click";
                        var header = (data.header !== undefined) ? ko.unwrap(data.header) : "> li > :first-child,> :not(li):even";
                        var heightStyle = (data.heightStyle !== undefined) ? ko.unwrap(data.heightStyle) : "content";
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var activate = (data.activate !== undefined) ? (data.activate) : function (event, ui) { };
                        var create = (data.create !== undefined) ? (data.create) : function (event, ui) { };
                        var container = $(element);
                        container.accordion({
                            active: realActive,
                            animate: animate,
                            collapsible: collapsible,
                            event: event,
                            header: header,
                            heightStyle: heightStyle,
                            disabled: !enable,
                            beforeActivate: function (event, ui) {
                                ui.newPanel.removeClass("disappear");
                                ui.newPanel.stop(false, false);
                            },
                            activate: function (event, ui) {
                                if (ko.isObservable(active))
                                    active(container.accordion("option", "active"));
                                ui.oldPanel.addClass("disappear");
                                ui.newPanel.removeClass("disappear");
                                activate.call(this, event, ui);
                            },
                            create: function (event, ui) {
                                container.find(".nts-accordion-content").addClass("disappear");
                                ui.panel.removeClass("disappear");
                                create.call(this, event, ui);
                            },
                            icons: { "header": "ui-icon-caret-1-s", "activeHeader": "ui-icon-caret-1-n" },
                            classes: {
                                "ui-accordion": "ntsAccordion",
                                "ui-accordion-content": "ui-corner-bottom nts-accordion-content"
                            }
                        });
                    };
                    NtsAccordionBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var active = (data.active !== undefined) ? (data.active) : ko.observable(false);
                        var animate = (data.animate !== undefined) ? ko.unwrap(data.animate) : {};
                        var collapsible = (data.collapsible !== undefined) ? ko.unwrap(data.collapsible) : true;
                        var event = (data.event !== undefined) ? ko.unwrap(data.event) : "click";
                        var heightStyle = (data.heightStyle !== undefined) ? ko.unwrap(data.heightStyle) : "content";
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        var currentOption = container.accordion("option");
                        if (ko.isObservable(active) && currentOption.active !== ko.unwrap(active))
                            container.accordion("option", "active", Number(ko.unwrap(active)));
                        if (currentOption.animate != animate)
                            container.accordion("option", "animate", animate);
                        if (currentOption.collapsible != collapsible)
                            container.accordion("option", "collapsible", collapsible);
                        if (currentOption.event != event)
                            container.accordion("option", "event", event);
                        if (currentOption.heightStyle != heightStyle)
                            container.accordion("option", "heightStyle", heightStyle);
                        if (currentOption.disabled != !enable)
                            container.accordion("option", "disabled", !enable);
                    };
                    return NtsAccordionBindingHandler;
                }());
                ko.bindingHandlers['ntsAccordion'] = new NtsAccordionBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=accordion-ko-ext.js.map