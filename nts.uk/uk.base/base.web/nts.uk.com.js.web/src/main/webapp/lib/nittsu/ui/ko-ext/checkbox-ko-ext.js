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
                 * CheckBox binding handler
                 */
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
                        // Get data
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var checkBoxText;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        container.addClass("ntsControl ntsCheckBox").attr("tabindex", "0").on("click", function (e) {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
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
                                    if (checkbox.is(":checked")) {
                                        checkbox.prop("checked", false);
                                        setChecked(false);
                                    }
                                    else {
                                        checkbox.prop("checked", true);
                                        setChecked(true);
                                    }
                                }
                                evt.preventDefault();
                            }
                        });
                    };
                    /**
                     * Update
                     */
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        container.data("enable", enable);
                        container.data("readonly", readonly);
                        var $checkBox = $(element).find("input[type='checkbox']");
                        // Checked
                        $checkBox.prop("checked", checked);
                        // Enable
                        (enable === true) ? $checkBox.removeAttr("disabled") : $checkBox.attr("disabled", "disabled");
                    };
                    return NtsCheckboxBindingHandler;
                }());
                /**
                 * MultiCheckbox binding handler
                 */
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
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        container.data("enable", _.clone(enable));
                        container.data("init", true);
                        // Default value
                        new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        container.data("readonly", readonly);
                        // Get option or option[optionValue]
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        var selectedValues = JSON.parse(ko.toJSON(data.value));
                        // Render
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
                                checkBoxLabel.attr("tabindex", "0");
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
                                }
                                checkBox.appendTo(checkBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                                checkBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", _.cloneDeep(options));
                        }
                        // Checked  
                        container.find("input[type='checkbox']").prop("checked", function () {
                            var _this = this;
                            return (_.find(selectedValue(), function (value) {
                                return _.isEqual(JSON.parse(ko.toJSON(value)), $(_this).data("value"));
                            }) !== undefined);
                        });
                        // Enable
                        //            if((container.data("init") && enable !== true) || !_.isEqual(container.data("enable"), enable)){
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