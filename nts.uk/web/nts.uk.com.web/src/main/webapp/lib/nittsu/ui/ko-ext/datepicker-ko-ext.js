var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var DatePickerBindingHandler = (function () {
                    function DatePickerBindingHandler() {
                    }
                    DatePickerBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        var ISOFormat = uk.text.getISOFormat(dateFormat);
                        var hasDayofWeek = (ISOFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = ISOFormat.replace(/[^d]/g, "");
                        ISOFormat = ISOFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : "";
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var button = (data.button !== undefined) ? ko.unwrap(data.button) : false;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var autoHide = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
                        var valueType = typeof value();
                        if (valueType === "string") {
                            valueFormat = (valueFormat) ? valueFormat : uk.text.getISOFormat("ISO");
                        }
                        else if (valueType === "number") {
                            valueFormat = (valueFormat) ? valueFormat : ISOFormat;
                        }
                        else if (valueType === "object") {
                            if (moment.isDate(value())) {
                                valueType = "date";
                            }
                            else if (moment.isMoment(value())) {
                                valueType = "moment";
                            }
                        }
                        var container = $(element);
                        if (!container.attr("id")) {
                            var idString = nts.uk.util.randomId();
                            container.attr("id", idString);
                        }
                        container.addClass("ntsControl nts-datepicker-wrapper").data("init", true);
                        var inputClass = (ISOFormat.length < 10) ? "yearmonth-picker" : "";
                        var $input = $("<input id='" + container.attr("id") + "-input' class='ntsDatepicker nts-input' />").addClass(inputClass);
                        container.append($input);
                        if (hasDayofWeek) {
                            var lengthClass = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                            var $label = $("<label id='" + container.attr("id") + "-label' for='" + container.attr("id") + "-input' class='dayofweek-label' />");
                            $input.addClass(lengthClass);
                            container.append($label);
                        }
                        $input.datepicker({
                            language: 'ja-JP',
                            format: ISOFormat,
                            startDate: startDate,
                            endDate: endDate,
                            autoHide: autoHide,
                        });
                        var validator = new ui.validation.TimeValidator(constraintName, { required: required, inputFormat: valueFormat, valueType: valueType });
                        $input.on("change", function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                if (hasDayofWeek)
                                    $label.text("(" + uk.time.formatPattern(newText, "", dayofWeekFormat) + ")");
                                value(result.parsedValue);
                            }
                            else {
                                $input.ntsError('set', result.errorMessage);
                                value(newText);
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                $input.ntsError('set', "Invalid format");
                            }
                        }));
                    };
                    DatePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        var ISOFormat = uk.text.getISOFormat(dateFormat);
                        var hasDayofWeek = (ISOFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = ISOFormat.replace(/[^d]/g, "");
                        ISOFormat = ISOFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : ISOFormat;
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : undefined;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var container = $(element);
                        var init = container.data("init");
                        var $input = container.find(".nts-input");
                        var $label = container.find(".dayofweek-label");
                        var dateFormatValue = (value() !== "") ? uk.time.formatPattern(value(), valueFormat, ISOFormat) : "";
                        if (init === true || uk.time.formatPattern($input.datepicker("getDate", true), "", ISOFormat) !== dateFormatValue) {
                            if (dateFormatValue !== "" && dateFormatValue !== "Invalid date") {
                                $input.datepicker('setDate', dateFormatValue);
                                if (hasDayofWeek)
                                    $label.text("(" + moment.utc(value(), valueFormat).format(dayofWeekFormat) + ")");
                            }
                        }
                        container.data("init", false);
                        $input.datepicker('setStartDate', startDate);
                        $input.datepicker('setEndDate', endDate);
                        if (enable !== undefined)
                            $input.prop("disabled", !enable);
                        else
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
