var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsLetBindingHandler = (function () {
                    function NtsLetBindingHandler() {
                        this.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                            ko.applyBindingsToDescendants(bindingContext.extend(valueAccessor), element);
                            return { controlsDescendantBindings: true };
                        };
                        this.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) { };
                    }
                    return NtsLetBindingHandler;
                }());
                ko.virtualElements.allowedBindings.let = true;
                ko.bindingHandlers['let'] = new NtsLetBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=let-ko-ext.js.map