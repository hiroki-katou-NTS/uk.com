var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsCheckboxBindingHandler = (function () {
                    function NtsCheckboxBindingHandler() {
                    }
                    NtsCheckboxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var setChecked = data.checked;
                        var textId = data.text;
                        var checkBoxText;
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
                    NtsCheckboxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var checked = ko.unwrap(data.checked);
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        var checkBox = $(element).find("input[type='checkbox']");
                        checkBox.prop("checked", checked);
                        (enable === true) ? checkBox.removeAttr("disabled") : checkBox.attr("disabled", "disabled");
                    };
                    return NtsCheckboxBindingHandler;
                }());
                var NtsMultiCheckBoxBindingHandler = (function () {
                    function NtsMultiCheckBoxBindingHandler() {
                    }
                    NtsMultiCheckBoxBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        $(element).addClass("ntsControl");
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        $(element).data("enable", _.clone(enable));
                    };
                    NtsMultiCheckBoxBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var container = $(element);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
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
                                });
                                var disableOption = option["enable"];
                                if (!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)) {
                                    checkBox.attr("disabled", "disabled");
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
                                return _.isEqual(value, $(_this).data("value"));
                            }) !== undefined);
                        });
                        if (!_.isEqual(container.data("enable"), enable)) {
                            container.data("enable", _.clone(enable));
                            (enable === true) ? container.find("input[type='checkbox']").removeAttr("disabled") : container.find("input[type='checkbox']").attr("disabled", "disabled");
                            _.forEach(data.options(), function (option) {
                                if (typeof option["enable"] === "function") {
                                    option["enable"](enable);
                                }
                                else {
                                    option["enable"] = (enable);
                                }
                            });
                        }
                    };
                    return NtsMultiCheckBoxBindingHandler;
                }());
                ko.bindingHandlers['ntsCheckBox'] = new NtsCheckboxBindingHandler();
                ko.bindingHandlers['ntsMultiCheckBox'] = new NtsMultiCheckBoxBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=checkbox-ko-ext.js.map