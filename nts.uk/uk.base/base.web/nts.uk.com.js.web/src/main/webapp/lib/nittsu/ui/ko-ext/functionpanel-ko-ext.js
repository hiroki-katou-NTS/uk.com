var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsFunctionPanelBindingHandler = (function () {
                    function NtsFunctionPanelBindingHandler() {
                    }
                    NtsFunctionPanelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var width = (data.width !== undefined) ? ko.unwrap(data.width) : 100;
                        var headerText = (data.headerText !== undefined) ? ko.unwrap(data.headerText) : "";
                        var items = (data.dataSource !== undefined) ? ko.unwrap(data.dataSource) : [];
                        var container = $(element);
                        if (nts.uk.util.isNullOrEmpty(container.attr("id"))) {
                            container.attr("id", nts.uk.util.randomId());
                        }
                        container.width(width);
                        container.addClass("ntsControl ntsFunctionPanel").on("click", function (e) {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
                        container.append("<div class='function-header' /><div class='function-items'/>");
                        var header = container.find('.function-header');
                        header.append("<div class='function-icon'/><div class='function-link'><a class='header-link function-item'>" + headerText + "</a></div>");
                        var itemAreas = container.find('.function-items');
                        header.find(".function-item").click(function (evt, ui) {
                            var current = $(this);
                            if ($(this).data("dbClick") === false) {
                                itemAreas.find(".function-item-container").hide("fast", function () {
                                    current.data("dbClick", true);
                                });
                            }
                            else {
                                itemAreas.find(".function-item-container").show("fast", "linear", function () {
                                    current.data("dbClick", false);
                                });
                            }
                        });
                        container.mouseleave(function (evt) {
                            var current = header.find(".function-item");
                            itemAreas.find(".function-item-container").hide("fast", function () {
                                current.data("dbClick", true);
                            });
                        });
                    };
                    NtsFunctionPanelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : true;
                        var width = (data.width !== undefined) ? ko.unwrap(data.width) : 100;
                        var headerText = (data.headerText !== undefined) ? ko.unwrap(data.headerText) : "";
                        var items = (data.dataSource !== undefined) ? ko.unwrap(data.dataSource) : [];
                        var container = $(element);
                        var itemAreas = container.find('.function-items');
                        var headerLink = container.find('.header-link');
                        var containerId = container.attr("id");
                        headerLink.text(headerText);
                        itemAreas.empty();
                        _.forEach(items, function (item, idx) {
                            var div = $("<div class='function-item-container' />");
                            div.attr("data-idx", idx);
                            div.width(width);
                            div.append("<div class='function-icon'/><div class='function-link'/>");
                            var itemLink = $("<a id='" + (containerId + '-' + idx) + "' class='function-item'>" + item["text"] + "</a>");
                            itemLink.click(item["action"]);
                            itemLink.appendTo(div.find(".function-link"));
                            var icon = $("<img class='ft-icon' src='" + item["icon"] + "'/>");
                            icon.appendTo(div.find(".function-icon"));
                            div.appendTo(itemAreas);
                        });
                        container.find(".function-item-container").hide();
                    };
                    return NtsFunctionPanelBindingHandler;
                }());
                ko.bindingHandlers['ntsFunctionPanel'] = new NtsFunctionPanelBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=functionpanel-ko-ext.js.map