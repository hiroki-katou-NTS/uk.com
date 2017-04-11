/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var DatePickerBindingHandler = (function () {
                    /**
                     * Constructor.
                     */
                    function DatePickerBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    DatePickerBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        dateFormat = uk.text.getISOFormat(dateFormat);
                        var hasDayofWeek = (dateFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = dateFormat.replace(/[^d]/g, "");
                        dateFormat = dateFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : "";
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var button = (data.button !== undefined) ? ko.unwrap(data.button) : false;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var autoHide = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
                        var container = $(element);
                        if (!container.attr("id")) {
                            var idString = nts.uk.util.randomId();
                            container.attr("id", idString);
                        }
                        container.addClass("ntsControl nts-datepicker-wrapper").data("init", true);
                        var inputClass = (dateFormat.length < 10) ? "yearmonth-picker" : "";
                        var $input = $("<input id='" + container.attr("id") + "-input' class='ntsDatepicker nts-input' />").addClass(inputClass);
                        container.append($input);
                        if (hasDayofWeek) {
                            var lengthClass = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                            var $label = $("<label id='" + container.attr("id") + "-label' for='" + container.attr("id") + "-input' class='dayofweek-label' />");
                            $input.addClass(lengthClass);
                            container.append($label);
                        }
                        // Init Datepicker
                        $input.datepicker({
                            language: 'ja-JP',
                            format: dateFormat,
                            startDate: startDate,
                            endDate: endDate,
                            autoHide: autoHide,
                        });
                        $input.on("change", function (e) {
                            var newText = $input.val();
                            var isValid = moment.utc(newText, valueFormat).isValid();
                            $input.ntsError('clear');
                            if (isValid) {
                                var dateFormatValue = moment.utc(newText, valueFormat).format(dateFormat);
                                var valueFormatValue = (valueFormat) ? moment.utc(newText, valueFormat).format(valueFormat)
                                    : moment.utc(newText, valueFormat).toISOString();
                                $input.val(dateFormatValue);
                                if (hasDayofWeek)
                                    $label.text("(" + moment.utc(newText, valueFormat).format(dayofWeekFormat) + ")");
                                value(valueFormatValue);
                            }
                            else {
                                $input.ntsError('set', "Invalid format");
                                value(newText);
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var isValid = moment.utc(newText, valueFormat).isValid();
                            $input.ntsError('clear');
                            if (!isValid) {
                                $input.ntsError('set', "Invalid format");
                            }
                        }));
                    };
                    /**
                     * Update
                     */
                    DatePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        dateFormat = uk.text.getISOFormat(dateFormat);
                        var hasDayofWeek = (dateFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = dateFormat.replace(/[^d]/g, "");
                        dateFormat = dateFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : dateFormat;
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var button = (data.button !== undefined) ? ko.unwrap(data.button) : false;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var autoHide = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
                        var container = $(element);
                        var init = container.data("init");
                        var $input = container.find(".nts-input");
                        var $label = container.find(".dayofweek-label");
                        // Value Binding
                        var dateFormatValue = (value() !== "") ? moment.utc(value(), valueFormat).format(dateFormat) : "";
                        if (init === true || (moment.utc($input.datepicker('getDate')).format(dateFormat) !== dateFormatValue)) {
                            if (dateFormatValue !== "")
                                $input.datepicker('setDate', dateFormatValue);
                            else
                                $input.datepicker('setDate', null);
                            if (hasDayofWeek)
                                $label.text("(" + moment.utc(value(), valueFormat).format(dayofWeekFormat) + ")");
                        }
                        container.data("init", false);
                        // Properties Binding
                        $input.datepicker('setStartDate', startDate);
                        $input.datepicker('setEndDate', endDate);
                        $input.prop("disabled", disabled);
                        if (data.button)
                            container.find('.datepicker-btn').prop("disabled", disabled);
                    };
                    return DatePickerBindingHandler;
                }());
                ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=datepicker-ko-ext.js.map