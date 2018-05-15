var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsFormLabelBindingHandler = (function () {
                    function NtsFormLabelBindingHandler() {
                    }
                    NtsFormLabelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var primitiveValue = ko.unwrap(data.constraint);
                        var isRequired = ko.unwrap(data.required) === true;
                        var isInline = ko.unwrap(data.inline) === true;
                        var isEnable = ko.unwrap(data.enable) !== false;
                        var cssClass = data.cssClass !== undefined ? ko.unwrap(data.cssClass) : "";
                        var $formLabel = $(element).addClass('form-label');
                        $('<label/>').html($formLabel.html()).appendTo($formLabel.empty());
                        if (!isEnable) {
                            $formLabel.addClass('disabled');
                        }
                        else {
                            $formLabel.removeClass('disabled');
                        }
                        if (isRequired) {
                            $formLabel.addClass('required');
                        }
                        if (primitiveValue !== undefined) {
                            $formLabel.addClass(isInline ? 'inline' : 'broken');
                            var constraintText = uk.util.getConstraintMes(primitiveValue);
                            $('<i/>').text(constraintText).appendTo($formLabel);
                        }
                    };
                    NtsFormLabelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var text = (data.text !== undefined) ? ko.unwrap(data.text) : $(element).find('label').html();
                        var cssClass = data.cssClass !== undefined ? ko.unwrap(data.cssClass) : "";
                        var container = $(element);
                        var $label = container.find("label");
                        $label.removeClass($label.data("cssClass")).addClass(cssClass).html(text);
                        $label.data("cssClass", cssClass);
                    };
                    return NtsFormLabelBindingHandler;
                }());
                ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=formlabel-ko-ext.js.map