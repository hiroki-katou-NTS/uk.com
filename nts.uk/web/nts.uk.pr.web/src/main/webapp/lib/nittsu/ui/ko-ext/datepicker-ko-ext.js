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
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        dateFormat = uk.text.getISO8601Format(dateFormat);
                        var hasDayofWeek = (dateFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = dateFormat.replace(/[^d]/g, "");
                        dateFormat = dateFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : dateFormat;
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
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
                        var inputClass = "";
                        if (dateFormat.length < 10)
                            inputClass = "yearmonth-picker";
                        var $input = $("<input id='" + container.attr("id") + "-input' class='ntsDatepicker nts-input' />")
                            .addClass(inputClass);
                        container.append($input);
                        if (hasDayofWeek) {
                            var lengthClass = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                            var $label = $("<label id='" + container.attr("id") + "-label' for='" + container.attr("id") + "-input' class='dayofweek-label' />");
                            $input.addClass(lengthClass);
                            container.append($label);
                        }
                        $input.datepicker({
                            date: value(),
                            language: 'ja-JP',
                            format: dateFormat,
                            startDate: startDate,
                            endDate: endDate,
                            autoHide: autoHide,
                        });
                        $input.on("change", function (e) {
                            var newText = $input.val();
                            var isValid = moment(newText, valueFormat).isValid();
                            $input.ntsError('clear');
                            if (isValid) {
                                var dateFormatValue = moment(newText, valueFormat).format(dateFormat);
                                var valueFormatValue = moment(newText, valueFormat).format(valueFormat);
                                $input.val(dateFormatValue);
                                if (hasDayofWeek)
                                    $label.text("(" + moment(newText, valueFormat).format(dayofWeekFormat) + ")");
                                value(valueFormatValue);
                            }
                            else {
                                $input.ntsError('set', "Invalid format");
                                value(newText);
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var isValid = moment(newText, valueFormat).isValid();
                            $input.ntsError('clear');
                            if (!isValid) {
                                $input.ntsError('set', "Invalid format");
                            }
                        }));
                        var length = 10, atomWidth = 9.5;
                    };
                    DatePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var value = data.value;
                        var dateFormat = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
                        dateFormat = uk.text.getISO8601Format(dateFormat);
                        var hasDayofWeek = (dateFormat.indexOf("ddd") !== -1);
                        var dayofWeekFormat = dateFormat.replace(/[^d]/g, "");
                        dateFormat = dateFormat.replace(/d/g, "").trim();
                        var valueFormat = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : dateFormat;
                        var disabled = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
                        var button = (data.button !== undefined) ? ko.unwrap(data.button) : false;
                        var startDate = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
                        var endDate = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
                        var autoHide = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
                        var container = $(element);
                        var init = container.data("init");
                        container.data("init", false);
                        var $input = container.find(".nts-input");
                        var $label = container.find(".dayofweek-label");
                        var isValid = moment(value(), valueFormat).isValid();
                        if (isValid) {
                            var dateFormatValue = moment(value(), valueFormat).format(dateFormat);
                            if (init === true || (moment($input.datepicker('getDate')).format(dateFormat) !== dateFormatValue)) {
                                $input.datepicker('setDate', dateFormatValue);
                                if (hasDayofWeek)
                                    $label.text("(" + moment(value(), valueFormat).format(dayofWeekFormat) + ")");
                            }
                        }
                        else {
                            $input.ntsError('set', "Invalid format");
                            $input.val(value());
                        }
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