var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsSwitchButtonBindingHandler = (function () {
                    function NtsSwitchButtonBindingHandler() {
                    }
                    NtsSwitchButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        if (nts.uk.util.isNullOrUndefined(container.attr("tabindex")))
                            container.attr("tabindex", "0");
                        container.data("tabindex", container.attr("tabindex"));
                        container.keydown(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (code === 32) {
                                evt.preventDefault();
                            }
                        });
                        container.keyup(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (container.data("enable") !== false) {
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
                            }
                        });
                        var defVal = new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                        container.bind("validate", function () {
                            if (container.ctState("required", "get") && !container.ctState("selected", "get")) {
                                container.ntsError("set", uk.resource.getMessage("FND_E_REQ_SELECT", [container.ctState("name", "get")]), "FND_E_REQ_SELECT");
                            }
                            else {
                                container.ntsError("clear");
                            }
                        });
                        ui.bindErrorStyle.useDefaultErrorClass(container);
                    };
                    NtsSwitchButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var selectedCssClass = 'selected';
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = ko.unwrap(data.value);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.data("enable", enable);
                        container.addClass("ntsControl switchButton-wrapper");
                        container.ctState("required", "set", ko.unwrap(data.required) === true);
                        container.ctState("name", "set", ko.unwrap(data.name));
                        $('button', container).each(function (index, btn) {
                            var $btn = $(btn);
                            var btnValue = $(btn).data('swbtn');
                            var foundFlag = _.findIndex(options, function (opt) {
                                return opt[optionValue] == btnValue;
                            }) != -1;
                            if (!foundFlag) {
                                $btn.remove();
                                return;
                            }
                        });
                        _.forEach(options, function (opt) {
                            var value = opt[optionValue];
                            var text = opt[optionText];
                            var targetBtn = NtsSwitchButtonBindingHandler.setSelectedClass(container, selectedCssClass, selectedValue, value);
                            if (targetBtn) {
                            }
                            else {
                                var btn = $('<button>').text(text)
                                    .addClass('nts-switch-button')
                                    .data('swbtn', value)
                                    .attr('tabindex', "-1")
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
                        if (enable === true) {
                            $('button', container).prop("disabled", false);
                            container.attr("tabindex", container.data("tabindex"));
                        }
                        else {
                            $('button', container).prop("disabled", true);
                            new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
                            container.attr("tabindex", "-1");
                        }
                    };
                    NtsSwitchButtonBindingHandler.setSelectedClass = function ($container, selectedCssClass, selectedValue, optValue) {
                        var targetBtn;
                        $container.ctState("selected", "set", false);
                        $('button', $container).each(function (index, btn) {
                            var btnValue = $(btn).data('swbtn');
                            if (btnValue == optValue) {
                                targetBtn = $(btn);
                            }
                            if (btnValue == selectedValue) {
                                $(btn).addClass(selectedCssClass);
                                $container.ctState("selected", "set", true);
                                $container.ntsError("clear");
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