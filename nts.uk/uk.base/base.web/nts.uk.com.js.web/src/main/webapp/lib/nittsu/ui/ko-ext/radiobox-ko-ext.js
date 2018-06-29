var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsRadioBoxBindingHandler = (function () {
                    function NtsRadioBoxBindingHandler() {
                    }
                    NtsRadioBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var optionValue = ko.unwrap(data.optionValue);
                        var optionText = ko.unwrap(data.optionText);
                        var dataName = ko.unwrap(data.name);
                        var option = ko.unwrap(data.option);
                        var group = ko.unwrap(data.group);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var selectedValue = ko.unwrap(data.checked);
                        var container = $(element);
                        container.addClass("ntsControl radio-wrapper");
                        container.data("enable", enable);
                        if (nts.uk.util.isNullOrUndefined(container.attr("tabindex"))) {
                            container.attr("tabindex", "0");
                        }
                        container.data("tabindex", container.attr("tabindex"));
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
                                    var checkitem = container.find("input[type='radio']");
                                    if (!container.find("input[type='radio']").is(":checked")) {
                                        checkitem.prop("checked", true);
                                        data.checked(container.find("input[type='radio']").data("value"));
                                    }
                                    else {
                                        checkitem.prop("checked", false);
                                        data.checked(undefined);
                                    }
                                    container.focus();
                                }
                            }
                        });
                        var radioBoxLabel = drawRadio(data.checked, option, dataName, optionValue, enable, optionText, false);
                        radioBoxLabel.appendTo(container);
                        var radio = container.find("input[type='radio']");
                        radio.attr("name", group).bind('selectionchanged', function () {
                            data.checked(radio.data("value"));
                        });
                        new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                    };
                    NtsRadioBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var option = data.option === undefined ? [] : ko.unwrap(data.option);
                        var optionValue = ko.unwrap(data.optionValue);
                        var optionText = ko.unwrap(data.optionText);
                        var selectedValue = data.checked;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.data("enable", enable);
                        container.find(".label").text(nts.uk.util.isNullOrUndefined(option) ? optionText : option[optionText]);
                        if (selectedValue() === getOptionValue(option, optionValue)) {
                            container.find("input[type='radio']").prop("checked", true);
                        }
                        else {
                            container.find("input[type='radio']").prop("checked", false);
                        }
                        if (enable === true) {
                            container.find("input[type='radio']").removeAttr("disabled");
                            container.attr("tabindex", container.data("tabindex"));
                        }
                        else if (enable === false) {
                            container.find("input[type='radio']").attr("disabled", "disabled");
                            if (!nts.uk.util.isNullOrUndefined(data.value)) {
                                new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
                            }
                            container.attr("tabindex", "-1");
                        }
                    };
                    return NtsRadioBoxBindingHandler;
                }());
                var NtsRadioBoxGroupBindingHandler = (function () {
                    function NtsRadioBoxGroupBindingHandler() {
                    }
                    NtsRadioBoxGroupBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var optionValue = ko.unwrap(data.optionsValue);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.addClass("ntsControl radio-wrapper");
                        container.data("enable", enable);
                        if (nts.uk.util.isNullOrUndefined(container.attr("tabindex"))) {
                            container.attr("tabindex", "0");
                        }
                        container.data("tabindex", container.attr("tabindex"));
                        container.keydown(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (code === 32) {
                                evt.preventDefault();
                            }
                        });
                        container.keyup(function (evt, ui) {
                            if (container.data("enable") !== false) {
                                var code = evt.which || evt.keyCode;
                                var checkitem = void 0;
                                if (code === 32) {
                                    checkitem = $(_.find(container.find("input[type='radio']"), function (radio, idx) {
                                        return $(radio).attr("disabled") !== "disabled";
                                    }));
                                }
                                else if (code === 37 || code === 38) {
                                    var inputList = _.filter(container.find("input[type='radio']"), function (radio, idx) {
                                        return $(radio).attr("disabled") !== "disabled";
                                    });
                                    var currentSelect = _.findIndex(inputList, function (item) {
                                        return $(item).is(":checked");
                                    });
                                    checkitem = $(inputList[currentSelect - 1]);
                                }
                                else if (code === 39 || code === 40) {
                                    var inputList = _.filter(container.find("input[type='radio']"), function (radio, idx) {
                                        return $(radio).attr("disabled") !== "disabled";
                                    });
                                    var currentSelect = _.findIndex(inputList, function (item) {
                                        return $(item).is(":checked");
                                    });
                                    checkitem = $(inputList[currentSelect + 1]);
                                }
                                if (checkitem !== undefined && checkitem.length > 0) {
                                    checkitem.prop("checked", true);
                                    data.value(optionValue === undefined ? checkitem.data("option") : checkitem.data("option")[optionValue]);
                                }
                                container.focus();
                            }
                        });
                        new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                    };
                    NtsRadioBoxGroupBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.data("enable", enable);
                        if (!_.isEqual(container.data("options"), options)) {
                            var radioName = uk.util.randomId();
                            container.empty();
                            _.forEach(options, function (option) {
                                var radioBoxLabel = drawRadio(selectedValue, option, radioName, optionValue, option["enable"], optionText, false);
                                radioBoxLabel.appendTo(container);
                            });
                            container.data("options", _.cloneDeep(options));
                        }
                        if (!nts.uk.util.isNullOrUndefined(selectedValue())) {
                            var checkedRadio = _.find(container.find("input[type='radio']"), function (item) {
                                return _.isEqual(JSON.parse(ko.toJSON(selectedValue())), $(item).data("value"));
                            });
                            if (checkedRadio !== undefined)
                                $(checkedRadio).prop("checked", true);
                        }
                        if (enable === true) {
                            _.forEach(container.find("input[type='radio']"), function (radio) {
                                var dataOpion = $(radio).data("option");
                                if (dataOpion["enable"] !== false) {
                                    $(radio).removeAttr("disabled");
                                }
                            });
                            container.attr("tabindex", container.data("tabindex"));
                        }
                        else if (enable === false) {
                            container.find("input[type='radio']").attr("disabled", "disabled");
                            new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
                            container.attr("tabindex", "-1");
                        }
                    };
                    return NtsRadioBoxGroupBindingHandler;
                }());
                function getOptionValue(item, optionValue) {
                    if (nts.uk.util.isNullOrUndefined(item)) {
                        return nts.uk.util.isNullOrUndefined(optionValue) ? true : optionValue;
                    }
                    return (optionValue === undefined) ? item : item[optionValue];
                }
                ;
                function drawRadio(selectedValue, option, radioName, optionValue, disableOption, optionText, booleanValue) {
                    var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                    var radioBox = $('<input type="radio">').data("option", option).attr("name", radioName).data("value", getOptionValue(option, optionValue)).on("change", function () {
                        var self = $(this);
                        if (self.is(":checked") && !booleanValue) {
                            selectedValue(self.data("value"));
                        }
                        else if (booleanValue) {
                            var name_1 = self.attr("name");
                            if (nts.uk.util.isNullOrUndefined(name_1)) {
                                selectedValue(self.is(":checked"));
                            }
                            else {
                                var selector = 'input[name=' + name_1 + ']';
                                $(selector).each(function (idx, e) {
                                    $(e).triggerHandler('selectionchanged');
                                });
                            }
                        }
                    });
                    if (!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)) {
                        radioBox.attr("disabled", "disabled");
                    }
                    radioBox.appendTo(radioBoxLabel);
                    var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                    var label = $("<span class='label'></span>").text(nts.uk.util.isNullOrUndefined(option) ? optionText : option[optionText]).appendTo(radioBoxLabel);
                    return radioBoxLabel;
                }
                ko.bindingHandlers['ntsRadioButton'] = new NtsRadioBoxBindingHandler();
                ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=radiobox-ko-ext.js.map