/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * SwitchButton binding handler
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
                        var data = valueAccessor();
                        var container = $(element);
                        container.keydown(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (code === 32) {
                                evt.preventDefault();
                            }
                        });
                        container.keyup(function (evt, ui) {
                            if (container.data("enable") !== false) {
                                var code = evt.which || evt.keyCode;
                                if (code === 32) {
                                    var selectedCode = container.find(".nts-switch-button:first").data('swbtn');
                                    data.value(selectedCode);
                                }
                                else if (code === 37 || code === 38) {
                                    var inputList = container.find(".nts-switch-button");
                                    var currentSelect = _.findIndex(inputList, function (item) {
                                        return $(item).data('swbtn') === data.value();
                                    });
                                    var selectedCode = $(inputList[currentSelect - 1]).data('swbtn');
                                    if (!nts.uk.util.isNullOrUndefined(selectedCode)) {
                                        data.value(selectedCode);
                                    }
                                }
                                else if (code === 39 || code === 40) {
                                    var inputList = container.find(".nts-switch-button");
                                    var currentSelect = _.findIndex(inputList, function (item) {
                                        return $(item).data('swbtn') === data.value();
                                    });
                                    var selectedCode = $(inputList[currentSelect + 1]).data('swbtn');
                                    if (!nts.uk.util.isNullOrUndefined(selectedCode)) {
                                        data.value(selectedCode);
                                    }
                                }
                                container.focus();
                            }
                        });
                        // Default value.
                        var defVal = new nts.uk.util.value.DefaultValue().onReset(container, data.value);
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
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container.
                        var container = $(element);
                        container.data("enable", enable);
                        container.addClass("switchButton-wrapper").attr("tabindex", "0");
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
                            var targetBtn = NtsSwitchButtonBindingHandler.setSelectedClass(container, selectedCssClass, selectedValue, value);
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
                        // Enable
                        container.find(".nts-switch-button").focus(function (evt) {
                            container.focus();
                        });
                        if (enable === true) {
                            $('button', container).prop("disabled", false);
                        }
                        else {
                            $('button', container).prop("disabled", true);
                            new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
                        }
                    };
                    NtsSwitchButtonBindingHandler.setSelectedClass = function ($container, selectedCssClass, selectedValue, optValue) {
                        var targetBtn;
                        $('button', $container).each(function (index, btn) {
                            var btnValue = $(btn).data('swbtn');
                            if (btnValue == optValue) {
                                targetBtn = $(btn);
                            }
                            if (btnValue == selectedValue) {
                                $(btn).addClass(selectedCssClass);
                            }
                            else {
                                $(btn).removeClass(selectedCssClass);
                            }
                        });
                        return targetBtn;
                    };
                    return NtsSwitchButtonBindingHandler;
                }());
                ko.bindingHandlers['ntsSwitchButton'] = new NtsSwitchButtonBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=switch-button-ko-ext.js.map