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
                 * RadioBoxGroup binding handler
                 */
                var NtsRadioBoxGroupBindingHandler = (function () {
                    function NtsRadioBoxGroupBindingHandler() {
                    }
                    /**
                     * Init
                     */
                    NtsRadioBoxGroupBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        $(element).addClass("ntsControl");
                    };
                    /**
                     * Update
                     */
                    NtsRadioBoxGroupBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var options = ko.unwrap(data.options);
                        var optionValue = ko.unwrap(data.optionsValue);
                        var optionText = ko.unwrap(data.optionsText);
                        var selectedValue = data.value;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        // Container
                        var container = $(element);
                        var getOptionValue = function (item) {
                            return (optionValue === undefined) ? item : item[optionValue];
                        };
                        // Render
                        if (!_.isEqual(container.data("options"), options)) {
                            var radioName = uk.util.randomId();
                            container.empty();
                            _.forEach(options, function (option) {
                                var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                                var radioBox = $('<input type="radio">').attr("name", radioName).data("value", getOptionValue(option)).on("change", function () {
                                    var self = this;
                                    if ($(self).is(":checked"))
                                        selectedValue($(self).data("value"));
                                }).appendTo(radioBoxLabel);
                                var box = $("<span class='box'></span>").appendTo(radioBoxLabel);
                                if (option[optionText] && option[optionText].length > 0)
                                    var label = $("<span class='label'></span>").text(option[optionText]).appendTo(radioBoxLabel);
                                radioBoxLabel.appendTo(container);
                            });
                            // Save a clone
                            container.data("options", options.slice());
                        }
                        // Checked
                        var checkedRadio = _.find(container.find("input[type='radio']"), function (item) {
                            return _.isEqual($(item).data("value"), selectedValue());
                        });
                        if (checkedRadio !== undefined)
                            $(checkedRadio).prop("checked", true);
                        // Enable
                        (enable === true) ? container.find("input[type='radio']").removeAttr("disabled") : container.find("input[type='radio']").attr("disabled", "disabled");
                    };
                    return NtsRadioBoxGroupBindingHandler;
                }());
                ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=radiobox-ko-ext.js.map
