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
                 * RadioBoxGroup binding handler
                 */
                var NtsRadioBoxGroupBindingHandler = (function () {
                    function NtsRadioBoxGroupBindingHandler() {
                    }
                    /**
                     * Init
                     */
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
                                    //                        let inputList = container.find("input[type='radio']");
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
                        //            container.find(".ntsRadioBox").focus(function (evt, ui){
                        //                console.log(evt);            
                        //            });
                        // Default value
                        new nts.uk.util.value.DefaultValue().onReset(container, data.value);
                    };
                    /**
                     * Update
                     */
                    NtsRadioBoxGroupBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        container.data("enable", enable);
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            var radioName = uk.util.randomId();
                            container.empty();
                            _.forEach(options, function (option) {
                                var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                                var radioBox = $('<input type="radio">').data("option", option).attr("name", radioName).data("value", getOptionValue(option)).on("change", function () {
                                    var self = $(this);
                                    if (self.is(":checked"))
                                        selectedValue(self.data("value"));
                                });
                                var disableOption = option["enable"];
                                if (!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)) {
                                    radioBox.attr("disabled", "disabled");
                                }
                                radioBox.appendTo(radioBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(radioBoxLabel);
                                radioBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", _.cloneDeep(options));
                        }
                        // Checked
                        var checkedRadio = _.find(container.find("input[type='radio']"), function (item) {
                            return _.isEqualWith($(item).data("value"), selectedValue(), function (objVal, othVal, key) { return key === "enable" ? true : undefined; });
                        });
                        if (checkedRadio !== undefined)
                            $(checkedRadio).prop("checked", true);
                        // Enable
                        if (enable === true) {
                            _.forEach(container.find("input[type='radio']"), function (radio) {
                                var dataOpion = $(radio).data("option");
                                if (dataOpion["enable"] === true) {
                                    $(radio).removeAttr("disabled");
                                }
                            });
                        }
                        else if (enable === false) {
                            container.find("input[type='radio']").attr("disabled", "disabled");
                            new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
                        }
                        //            }
                    };
                    return NtsRadioBoxGroupBindingHandler;
                }());
                ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=radiobox-ko-ext.js.map