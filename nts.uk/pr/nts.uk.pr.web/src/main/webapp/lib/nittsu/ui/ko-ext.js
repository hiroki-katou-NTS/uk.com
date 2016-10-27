var nts;
(function (nts) {
    (function (uk) {
        (function (ui) {
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;

                var NtsTextBoxBindingHandler = (function () {
                    function NtsTextBoxBindingHandler() {
                    }
                    /**
                    * Init.
                    */
                    NtsTextBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setValue = data.value;
                        this.constraint = validation.getCharType(data.constraint);

                        var $input = $(element);

                        $input.change(function () {
                            var newText = $input.val();
                            setValue(newText);
                        });
                    };

                    /**
                    * Update
                    */
                    NtsTextBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var getValue = data.value;

                        var $input = $(element);
                        var newText = getValue();

                        $input.val(newText);
                    };
                    return NtsTextBoxBindingHandler;
                })();

                ko.bindingHandlers['ntsTextBox'] = new NtsTextBoxBindingHandler();
            })(ui.koExtentions || (ui.koExtentions = {}));
            var koExtentions = ui.koExtentions;
        })(uk.ui || (uk.ui = {}));
        var ui = uk.ui;
    })(nts.uk || (nts.uk = {}));
    var uk = nts.uk;
})(nts || (nts = {}));
