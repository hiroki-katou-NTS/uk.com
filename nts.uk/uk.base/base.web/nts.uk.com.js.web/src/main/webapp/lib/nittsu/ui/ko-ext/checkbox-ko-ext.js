var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsCheckboxBindingHandler = (function () {
                    function NtsCheckboxBindingHandler() {
                    }
                    NtsCheckboxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var style = "style-" + (data.style || "normal");
                        var checkBoxText;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        if (nts.uk.util.isNullOrUndefined(container.attr("tabindex")))
                            container.attr("tabindex", "0");
                        container.addClass("ntsControl ntsCheckBox").on("click", function (e) {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
                        container.addClass(style + " checkbox-wrapper");
                        container.data("tabindex", container.attr("tabindex"));
                        var wrapper = container.parent();
                        wrapper.addClass(style);
                        if (textId) {
                            checkBoxText = textId;
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        container.data("enable", enable);
                        var $checkBoxLabel = $("<label class='ntsCheckBox-label'></label>");
                        var $checkBox = $('<input type="checkbox">').on("change", function () {
                            if (typeof setChecked === "function")
                                setChecked($(this).is(":checked"));
                        }).appendTo($checkBoxLabel);
                        var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
                        if (checkBoxText && checkBoxText.length > 0)
                            var label = $("<span class='label'></span>").text(checkBoxText).appendTo($checkBoxLabel);
                        $checkBoxLabel.appendTo(container);
                        container.keypress(function (evt, ui) {
                            var code = evt.which || evt.keyCode;
                            if (code === 32) {
                                if (container.data("enable") !== false) {
                                    var checkbox = container.find("input[type='checkbox']:first");
                                    var checked = !checkbox.is(":checked");
                                    checkbox.prop("checked", checked);
                                    container[checked ? "addClass" : "removeClass"]("checked");
                                    setChecked(checked);
                                }
                                evt.preventDefault();
                            }
                        });
                    };
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.data("enable", enable);
                        container.data("readonly", readonly);
                        var $checkBox = $(element).find("input[type='checkbox']");
                        $checkBox.prop("checked", checked);
                        container[checked ? "addClass" : "removeClass"]("checked");
                        if (enable === true) {
                            $checkBox.removeAttr("disabled");
                            container.attr("tabindex", container.data("tabindex"));
                        }
                        else if (enable === false) {
                            $checkBox.attr("disabled", "disabled");
                            container.attr("tabindex", "-1");
                        }
                    };
                    return NtsCheckboxBindingHandler;
                }());
                var NtsMultiCheckBoxBindingHandler = (function () {
                    function NtsMultiCheckBoxBindingHandler() {
                    }
                    NtsMultiCheckBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        container.addClass("ntsControl").on("click", function (e) {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
                        container.wrap("<div class='multicheckbox-wrapper'/>");
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        container.data("enable", _.clone(enable));
                        container.data("init", true);
                        container.data("tabindex", container.attr("tabindex"));
                        container.removeAttr("tabindex");
                        new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        container.data("readonly", readonly);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        var selectedValues = JSON.parse(ko.toJSON(data.value));
                        if (!_.isEqual(container.data("options"), options)) {
                            container.empty();
                            _.forEach(options, function (option) {
                                var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var checkBox = $('<input type="checkbox">').data("option", option).data("value", getOptionValue(option)).on("change", function () {
                                    var self = $(this);
                                    if (self.is(":checked"))
                                        selectedValue.push(self.data("value"));
                                    else
                                        selectedValue.remove(_.find(selectedValue(), function (value) {
                                            return _.isEqual(JSON.parse(ko.toJSON(value)), self.data("value"));
                                        }));
                                });
                                var disableOption = option["enable"];
                                if (nts.uk.util.isNullOrUndefined(container.data("tabindex")))
                                    checkBoxLabel.attr("tabindex", "0");
                                else {
                                    checkBoxLabel.attr("tabindex", container.data("tabindex"));
                                }
                                checkBoxLabel.keypress(function (evt, ui) {
                                    var code = evt.which || evt.keyCode;
                                    if (code === 32) {
                                        if (container.data("enable") !== false && disableOption !== false) {
                                            var cb = checkBoxLabel.find("input[type='checkbox']:first");
                                            if (cb.is(":checked")) {
                                                cb.prop("checked", false);
                                                selectedValue.remove(_.find(selectedValue(), function (value) {
                                                    return _.isEqual(JSON.parse(ko.toJSON(value)), checkBox.data("value"));
                                                }));
                                            }
                                            else {
                                                if (!cb.is(":checked")) {
                                                    cb.prop("checked", true);
                                                    selectedValue.push(checkBox.data("value"));
                                                }
                                            }
                                        }
                                        evt.preventDefault();
                                    }
                                });
                                if (!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)) {
                                    checkBox.attr("disabled", "disabled");
                                    checkBox.attr("tabindex", "-1");
                                }
                                checkBox.appendTo(checkBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                                checkBoxLabel.appendTo(container);
                            });
                            container.data("options", _.cloneDeep(options));
                        }
                        container.find("input[type='checkbox']").prop("checked", function () {
                            var _this = this;
                            return (_.find(selectedValue(), function (value) {
                                return _.isEqual(JSON.parse(ko.toJSON(value)), $(_this).data("value"));
                            }) !== undefined);
                        });
                        container.data("enable", _.clone(enable));
                        if (enable === true) {
                            _.forEach(container.find("input[type='checkbox']"), function (checkbox) {
                                var dataOpion = $(checkbox).data("option");
                                if (dataOpion["enable"] === true) {
                                    $(checkbox).removeAttr("disabled");
                                }
                            });
                        }
                        else if (enable === false) {
                            container.find("input[type='checkbox']").attr("disabled", "disabled");
                            new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
                        }
                    };
                    return NtsMultiCheckBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=checkbox-ko-ext.js.map