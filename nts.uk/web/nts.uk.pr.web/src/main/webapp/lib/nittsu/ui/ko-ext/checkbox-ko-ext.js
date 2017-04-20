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
                class NtsCheckboxBindingHandler {
                    /**
                     * Constructor.
                     */
                    constructor() {
                    }
                    /**
                     * Init.
                     */
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var checkBoxText;
                        // Container
                        var container = $(element);
                        container.addClass("ntsControl").on("click", (e) => {
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
                        var $checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                        var $checkBox = $('<input type="checkbox">').on("change", function () {
                            if (typeof setChecked === "function")
                                setChecked($(this).is(":checked"));
                        }).appendTo($checkBoxLabel);
                        var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
                        if (checkBoxText && checkBoxText.length > 0)
                            var label = $("<span class='label'></span>").text(checkBoxText).appendTo($checkBoxLabel);
                        $checkBoxLabel.appendTo(container);
                    }
                    /**
                     * Update
                     */
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        container.data("readonly", readonly);
                        var $checkBox = $(element).find("input[type='checkbox']");
                        // Checked
                        $checkBox.prop("checked", checked);
                        // Enable
                        (enable === true) ? $checkBox.removeAttr("disabled") : $checkBox.attr("disabled", "disabled");
                    }
                }
                /**
                 * MultiCheckbox binding handler
                 */
                class NtsMultiCheckBoxBindingHandler {
                    constructor() {
                    }
                    init(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var container = $(element);
                        container.addClass("ntsControl").on("click", (e) => {
                            if (container.data("readonly") === true)
                                e.preventDefault();
                        });
                        let enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        container.data("enable", _.clone(enable));
                    }
                    update(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
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
                        var getOptionValue = (item) => {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            container.empty();
                            _.forEach(options, (option) => {
                                var checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var checkBox = $('<input type="checkbox">').data("value", getOptionValue(option)).on("change", function () {
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue.push($(self).data("value"));
                                    else
                                        selectedValue.remove(_.find(selectedValue(), (value) => {
                                            return _.isEqual(value, $(this).data("value"));
                                        }));
                                });
                                let disableOption = option["enable"];
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
                            return (_.find(selectedValue(), (value) => {
                                return _.isEqual(value, $(this).data("value"));
                            }) !== undefined);
                        });
                        // Enable
                        if (!_.isEqual(container.data("enable"), enable)) {
                            container.data("enable", _.clone(enable));
                            (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
                            _.forEach(data.options(), (option) => {
                                if (typeof option["enable"] === "function") {
                                    option["enable"](enable);
                                }
                                else {
                                    option["enable"] = (enable);
                                }
                            });
                        }
                    }
                }
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
