var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsPanelBindingHandler = (function () {
                    function NtsPanelBindingHandler() {
                    }
                    NtsPanelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var width = (data.width !== undefined) ? ko.unwrap(data.width) : null;
                        var height = (data.height !== undefined) ? ko.unwrap(data.height) : null;
                        var direction = (data.direction !== undefined) ? ko.unwrap(data.direction) : "right";
                        var showIcon = (data.showIcon !== undefined) ? ko.unwrap(data.showIcon) : false;
                        var visible = (data.visible !== undefined) ? ko.unwrap(data.visible) : true;
                        var container = $(element);
                        container.addClass("panel ntsPanel caret-background");
                        var caretClass = "caret-" + direction;
                        container.addClass(caretClass + " direction-" + direction);
                        if (showIcon === true) {
                            container.append("<i class='icon icon-searchbox'></i>");
                        }
                    };
                    NtsPanelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var width = (data.width !== undefined) ? ko.unwrap(data.width) : null;
                        var height = (data.height !== undefined) ? ko.unwrap(data.height) : null;
                        var direction = (data.direction !== undefined) ? ko.unwrap(data.direction) : "right";
                        var showIcon = (data.showIcon !== undefined) ? ko.unwrap(data.showIcon) : false;
                        var visible = (data.visible !== undefined) ? ko.unwrap(data.visible) : null;
                        var container = $(element);
                        if (!nts.uk.util.isNullOrEmpty(width))
                            container.width(width);
                        if (!nts.uk.util.isNullOrEmpty(height))
                            container.height(height);
                        if (!nts.uk.util.isNullOrEmpty(visible)) {
                            if (visible === true)
                                container.show();
                            else
                                container.hide();
                        }
                    };
                    return NtsPanelBindingHandler;
                }());
                ko.bindingHandlers['ntsPanel'] = new NtsPanelBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=panel-ko-ext.js.map