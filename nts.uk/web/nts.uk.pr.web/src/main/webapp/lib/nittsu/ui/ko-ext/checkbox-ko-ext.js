/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
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
                        // Container
                        var container = $(element);
                        container.addClass("ntsControl");
                        if (textId) {
                            checkBoxText = textId;
                        }
                        else {
                            checkBoxText = container.text();
                            container.text('');
                        }
                        var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                        var checkBox = $('<input type="checkbox">').on("change", function () {
                            if (typeof setChecked === "function")
                                setChecked($(this).is(":checked"));
                        }).appendTo(checkBoxLabel);
                        var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                        if (checkBoxText && checkBoxText.length > 0)
                            var label = $("<span class='label'></span>").text(checkBoxText).appendTo(checkBoxLabel);
                        checkBoxLabel.appendTo(container);
                    };
                    /**
                     * Update
                     */
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        var checkBox = $(element).find("input[type='checkbox']");
                        // Checked
                        checkBox.prop("checked", checked);
                        // Enable
                        (enable === true) ? checkBox.removeAttr("disabled") : checkBox.attr("disabled", "disabled");
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
                        $(element).addClass("ntsControl");
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        // Get option or option[optionValue]
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            container.empty();
                            _.forEach(options, function (option) {
                                var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var checkBox = $('<input type="checkbox">').data("value", getOptionValue(option)).on("change", function () {
                                    var _this = this;
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue.push($(self).data("value"));
                                    else
                                        selectedValue.remove(_.find(selectedValue(), function (value) {
                                            return _.isEqual(value, $(_this).data("value"));
                                        }));
                                }).appendTo(checkBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(checkBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(checkBoxLabel);
                                checkBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", options.slice());
                        }
                        // Checked
                        container.find("input[type='checkbox']").prop("checked", function () {
                            var _this = this;
                            return (_.find(selectedValue(), function (value) {
                                return _.isEqual(value, $(_this).data("value"));
                            }) !== undefined);
                        });
                        // Enable
                        (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
                    };
                    return NtsMultiCheckBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
