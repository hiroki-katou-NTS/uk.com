var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsLinkButtonBindingHandler = (function () {
                    function NtsLinkButtonBindingHandler() {
                    }
                    NtsLinkButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var jump = ko.unwrap(data.jump);
                        var action = data.action;
                        var linkText = $(element).text();
                        var $linkButton = $(element).wrap('<div class="ntsControl"/>')
                            .addClass('link-button')
                            .click(function () {
                            event.preventDefault();
                            if (!nts.uk.util.isNullOrUndefined(action))
                                action.call(viewModel);
                            else if (!nts.uk.util.isNullOrUndefined(jump))
                                nts.uk.request.jump(jump);
                        });
                    };
                    NtsLinkButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return NtsLinkButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsLinkButton'] = new NtsLinkButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=linkbutton-ko-ext.js.map