var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                /**
                 * TextBox
                 */
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
                }());
                /**
                 * Switch button binding handler
                 */
                var NtsSwitchButtonBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsSwitchButtonBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsSwitchButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    /**
                     * Update
                     */
                    NtsSwitchButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        var selectedCssClass = 'selected';
                        // Get options.
                        var options = ko.unwrap(data.options);
                        // Get options value.
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        // Container.
                        var container = $(element);
                        // Remove deleted button.
                        $('button', container).each(function (index, btn) {
                            var $btn = $(btn);
                            var btnValue = $(btn).data('swbtn');
                            // Check if btn is contained in options.
                            var foundFlag = _.findIndex(options, function (opt) {
                                return opt[optionValue] == btnValue;
                            }) != -1;
                            if (!foundFlag) {
                                $btn.remove();
                                return;
                            }
                        });
                        // Start binding new state.
                        _.forEach(options, function (opt) {
                            var value = opt[optionValue];
                            var text = opt[optionText];
                            // Find button.
                            var targetBtn;
                            $('button', container).each(function (index, btn) {
                                var btnValue = $(btn).data('swbtn');
                                if (btnValue == value) {
                                    targetBtn = $(btn);
                                }
                                if (btnValue == selectedValue) {
                                    $(btn).addClass(selectedCssClass);
                                }
                                else {
                                    $(btn).removeClass(selectedCssClass);
                                }
                            });
                            if (targetBtn) {
                            }
                            else {
                                // Recreate
                                var btn = $('<button>').text(text)
                                    .addClass('nts-switch-button')
                                    .data('swbtn', value)
                                    .on('click', function () {
                                    var selectedValue = $(this).data('swbtn');
                                    data.value(selectedValue);
                                    $('button', container).removeClass(selectedCssClass);
                                    $(this).addClass(selectedCssClass);
                                });
                                if (selectedValue == value) {
                                    btn.addClass(selectedCssClass);
                                }
                                container.append(btn);
                            }
                        });
                    };
                    return NtsSwitchButtonBindingHandler;
                }());
                var NtsCheckboxBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function NtsCheckboxBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsCheckboxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsCheckBox");
                    };
                    /**
                     * Update
                     */
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data.
                        var data = valueAccessor();
                        // Container.
                        var container = $(element);
                        // Get options.
                        var checked = ko.unwrap(data.checked);
                        var enable = ko.unwrap(data.enable);
                        var textId = data.text;
                        var checkBoxText;
                        if (textId) {
                            checkBoxText = (textId);
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        var checkBox;
                        if ($('input[type="checkbox"]', container).length == 0) {
                            // Init new.
                            checkBox = $('<input type="checkbox">').appendTo(container);
                            var checkBoxLabel = $("<label><span></span></label>").appendTo(container).append(checkBoxText);
                            $(container).on('click', "label", function () {
                                // Do nothing if disable.
                                if (container.hasClass('disabled')) {
                                    return;
                                }
                                // Change value.
                                checkBox.prop("checked", !checkBox.prop("checked"));
                                data.checked(checkBox.prop("checked"));
                            });
                        }
                        else {
                            checkBox = $('input[type="checkbox"]', container);
                        }
                        // Set state.
                        checkBox.prop("checked", checked);
                        // Disable.
                        if (enable == false) {
                            container.addClass('disabled');
                        }
                        else {
                            container.removeClass('disabled');
                        }
                    };
                    return NtsCheckboxBindingHandler;
                }());
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
                ko.bindingHandlers['ntsTextBox'] = new NtsTextBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
