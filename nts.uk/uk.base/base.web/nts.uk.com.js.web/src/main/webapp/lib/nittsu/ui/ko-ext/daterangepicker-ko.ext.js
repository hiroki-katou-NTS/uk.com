var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsDateRangePickerBindingHandler = (function () {
                    function NtsDateRangePickerBindingHandler() {
                    }
                    NtsDateRangePickerBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var $container = $(element);
                        var dateType = ko.unwrap(data.type);
                        var maxRange = ko.unwrap(data.maxRange);
                        var value = data.value;
                        var rangeName = ko.unwrap(data.name);
                        var startName = ko.unwrap(data.startName);
                        var endName = ko.unwrap(data.endName);
                        var enable = data.enable === undefined ? true : ko.unwrap(data.enable);
                        var showNextPrevious = data.showNextPrevious === undefined ? false : ko.unwrap(data.showNextPrevious);
                        var jumpUnit = data.jumpUnit === undefined ? false : ko.unwrap(data.jumpUnit);
                        var required = ko.unwrap(data.required);
                        var id = nts.uk.util.randomId();
                        var tabIndex = nts.uk.util.isNullOrEmpty($container.attr("tabindex")) ? "0" : $container.attr("tabindex");
                        $container.data("tabindex", tabIndex);
                        $container.removeAttr("tabindex");
                        $container.append("<div class='ntsDateRange_Container' id='" + id + "' />");
                        var $datePickerArea = $container.find(".ntsDateRange_Container");
                        $datePickerArea.append("<div class='ntsDateRangeComponent ntsControl ntsDateRange'>" +
                            "<div class='ntsDateRangeComponent ntsStartDate ntsControl nts-datepicker-wrapper'/><div class='ntsDateRangeComponent ntsRangeLabel'><label>～</label></div>" +
                            "<div class='ntsDateRangeComponent ntsEndDate ntsControl nts-datepicker-wrapper' /></div>");
                        $datePickerArea.data("required", required);
                        var dateFormat;
                        if (dateType === 'year') {
                            dateFormat = 'YYYY';
                        }
                        else if (dateType === 'yearmonth') {
                            dateFormat = 'YYYY/MM';
                        }
                        else {
                            dateFormat = 'YYYY/MM/DD';
                        }
                        var ISOFormat = uk.text.getISOFormat(dateFormat);
                        ISOFormat = ISOFormat.replace(/d/g, "").trim();
                        if (showNextPrevious === true) {
                            $datePickerArea.append("<div class= 'ntsDateRangeComponent ntsDateNextButton_Container ntsRangeButton_Container'>" +
                                "<button class = 'ntsDateNextButton ntsButton ntsDateRangeButton ntsDateRange_Component auto-height'/></div>");
                            $datePickerArea.prepend("<div class='ntsDateRangeComponent ntsDatePreviousButton_Container ntsRangeButton_Container'>" +
                                "<button class = 'ntsDatePrevButton ntsButton ntsDateRangeButton ntsDateRange_Component auto-height'/></div>");
                            var $nextButton = $container.find(".ntsDateNextButton").text("▶").css("margin-left", "3px");
                            var $prevButton = $container.find(".ntsDatePrevButton").text("◀").css("margin-right", "3px");
                            $nextButton.click(function (evt, ui) {
                                jump(true);
                            });
                            $prevButton.click(function (evt, ui) {
                                jump(false);
                            });
                            var jump = function (isNext) {
                                var $startDate = $container.find(".ntsStartDatePicker");
                                var $endDate = $container.find(".ntsEndDatePicker");
                                var oldValue = value();
                                var currentStart = $startDate.val();
                                var currentEnd = $endDate.val();
                                if (!nts.uk.util.isNullOrEmpty(currentStart)) {
                                    var startDate = moment(currentStart, dateFormat);
                                    if (startDate.isValid()) {
                                        if (jumpUnit === "year") {
                                            startDate.year(startDate.year() + (isNext ? 1 : -1));
                                        }
                                        else {
                                            var isEndOfMonth = startDate.daysInMonth() === startDate.date();
                                            startDate.month(startDate.month() + (isNext ? 1 : -1));
                                            if (isEndOfMonth) {
                                                startDate.endOf("month");
                                            }
                                        }
                                        oldValue.startDate = startDate.format(dateFormat);
                                    }
                                }
                                if (!nts.uk.util.isNullOrEmpty(currentEnd)) {
                                    var endDate = moment(currentEnd, dateFormat);
                                    if (endDate.isValid()) {
                                        if (jumpUnit === "year") {
                                            endDate.year(endDate.year() + (isNext ? 1 : -1));
                                        }
                                        else {
                                            var isEndOfMonth = endDate.daysInMonth() === endDate.date();
                                            endDate.month(endDate.month() + (isNext ? 1 : -1));
                                            if (isEndOfMonth) {
                                                endDate.endOf("month");
                                            }
                                        }
                                        oldValue.endDate = endDate.format(dateFormat);
                                    }
                                }
                                value(oldValue);
                            };
                        }
                        var $startDateArea = $datePickerArea.find(".ntsStartDate");
                        var $endDateArea = $datePickerArea.find(".ntsEndDate");
                        $startDateArea.append("<input id='" + id + "-startInput'  class='ntsDatepicker nts-input ntsStartDatePicker ntsDateRange_Component' />");
                        $endDateArea.append("<input id='" + id + "-endInput' class='ntsDatepicker nts-input ntsEndDatePicker ntsDateRange_Component' />");
                        var $input = $container.find(".ntsDatepicker");
                        $input.datepicker({
                            language: 'ja-JP',
                            format: ISOFormat,
                            autoHide: true,
                            weekStart: 0
                        });
                        rangeName = nts.uk.util.isNullOrUndefined(rangeName) ? "期間入力フォーム" : nts.uk.resource.getControlName(rangeName);
                        startName = nts.uk.util.isNullOrUndefined(startName) ? "期間入力フォーム開始" : nts.uk.resource.getControlName(startName);
                        endName = nts.uk.util.isNullOrUndefined(endName) ? "期間入力フォーム終了" : nts.uk.resource.getControlName(endName);
                        var $ntsDateRange = $container.find(".ntsRangeLabel");
                        var getMessage = nts.uk.resource.getMessage;
                        var validateProcess = function (newText, $target, isStart, oldValue, result) {
                            if (nts.uk.util.isNullOrEmpty(newText) && $datePickerArea.data("required") === true) {
                                $target.ntsError('set', getMessage('FND_E_REQ_INPUT', [isStart ? startName : endName]), 'FND_E_REQ_INPUT');
                            }
                            else if (!result.isValid) {
                                $target.ntsError('set', result.errorMessage, result.errorCode);
                            }
                            else if (!nts.uk.util.isNullOrEmpty(newText)) {
                                var startDate = moment(oldValue.startDate, dateFormat);
                                var endDate = moment(oldValue.endDate, dateFormat);
                                if (endDate.isBefore(startDate)) {
                                    $ntsDateRange.ntsError('set', getMessage("FND_E_SPAN_REVERSED", [rangeName]), "FND_E_SPAN_REVERSED");
                                }
                                else if (dateFormat === "YYYY/MM/DD" && maxRange === "oneMonth") {
                                    var maxDate = startDate.add(31, "days");
                                    if (endDate.isSameOrAfter(maxDate)) {
                                        $ntsDateRange.ntsError('set', getMessage("FND_E_SPAN_OVER_MONTH", [rangeName]), "FND_E_SPAN_OVER_MONTH");
                                    }
                                }
                                else if (maxRange === "oneYear") {
                                    var maxDate = _.cloneDeep(startDate);
                                    if (dateFormat === "YYYY/MM/DD") {
                                        var currentDate = startDate.date();
                                        var isEndMonth = currentDate === startDate.endOf("months").date();
                                        var isStartMonth = currentDate === 1;
                                        maxDate = maxDate.date(1).add(1, 'year');
                                        if (isStartMonth) {
                                            maxDate = maxDate.month(maxDate.month() - 1).endOf("months");
                                        }
                                        else if (isEndMonth) {
                                            maxDate = maxDate.endOf("months").add(-1, "days");
                                        }
                                        else {
                                            maxDate = maxDate.date(currentDate - 1);
                                        }
                                    }
                                    else if (dateFormat === "YYYY/MM") {
                                        maxDate = maxDate.add(1, 'year').add(-1, "months");
                                    }
                                    else {
                                        maxDate = maxDate.add(1, 'year');
                                    }
                                    if (endDate.isAfter(maxDate)) {
                                        $ntsDateRange.ntsError('set', getMessage("FND_E_SPAN_OVER_YEAR", [rangeName]), "FND_E_SPAN_OVER_YEAR");
                                    }
                                }
                            }
                        };
                        $input.on("change", function (e) {
                            var $target = $(e.target);
                            var newText = $target.val();
                            $target.ntsError('clear');
                            $ntsDateRange.ntsError("clear");
                            var isStart = $target.hasClass("ntsStartDatePicker");
                            var validator = new ui_1.validation.TimeValidator(isStart ? startName : endName, "", { required: false, outputFormat: dateFormat, valueType: "string" });
                            var valueX = uk.time.formatPattern(newText, dateFormat, ISOFormat);
                            if (!nts.uk.util.isNullOrEmpty(valueX) && valueX !== "Invalid date") {
                                $target.val(valueX);
                                $target.datepicker("update");
                                newText = valueX;
                            }
                            var result = validator.validate(newText);
                            var oldValue = value();
                            if (isStart) {
                                oldValue.startDate = result.isValid ? result.parsedValue : newText;
                            }
                            else {
                                oldValue.endDate = result.isValid ? result.parsedValue : newText;
                            }
                            validateProcess(newText, $target, isStart, oldValue, result);
                            value(oldValue);
                        });
                        $input.on("blur", function (e) {
                            var isStart = $(e.target).hasClass("ntsStartDatePicker");
                            var newText = $(e.target).val();
                            if (nts.uk.util.isNullOrEmpty(newText) && $datePickerArea.data("required") === true) {
                                $(e.target).ntsError('set', getMessage('FND_E_REQ_INPUT', [isStart ? startName : endName]), 'FND_E_REQ_INPUT');
                            }
                            else {
                                var validator = new ui_1.validation.TimeValidator(isStart ? startName : endName, "", { required: false, outputFormat: dateFormat, valueType: "string" });
                                var result = validator.validate(newText);
                                if (!result.isValid) {
                                    $(e.target).ntsError('set', result.errorMessage, result.errorCode);
                                }
                            }
                        });
                        $input.on('validate', (function (e) {
                            var $target = $(e.target);
                            var newText = $target.val();
                            var isStart = $target.hasClass("ntsStartDatePicker");
                            var oldValue = value();
                            var validator = new ui_1.validation.TimeValidator(isStart ? startName : endName, "", { required: false, outputFormat: dateFormat, valueType: "string" });
                            var result = validator.validate(newText);
                            $target.ntsError('clear');
                            $ntsDateRange.ntsError("clear");
                            validateProcess(newText, $target, isStart, oldValue, result);
                        }));
                        $container.find(".ntsDateRange_Component").attr("tabindex", tabIndex);
                        $input.ntsDatepicker("bindFlip");
                    };
                    NtsDateRangePickerBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var $container = $(element);
                        var dateType = ko.unwrap(data.type);
                        var maxRange = ko.unwrap(data.maxRange);
                        var dataName = ko.unwrap(data.name);
                        var enable = data.enable === undefined ? true : ko.unwrap(data.enable);
                        var required = ko.unwrap(data.required);
                        var dateFormat;
                        if (dateType === 'year') {
                            dateFormat = 'YYYY';
                        }
                        else if (dateType === 'yearmonth') {
                            dateFormat = 'YYYY/MM';
                        }
                        else {
                            dateFormat = 'YYYY/MM/DD';
                        }
                        var ISOFormat = uk.text.getISOFormat(dateFormat);
                        ISOFormat = ISOFormat.replace(/d/g, "").trim();
                        var $input = $container.find(".ntsDatepicker");
                        var $startDate = $container.find(".ntsStartDatePicker");
                        var $endDate = $container.find(".ntsEndDatePicker");
                        if (!nts.uk.util.isNullOrUndefined(data.value())) {
                            var startDate = !nts.uk.util.isNullOrEmpty(data.value().startDate) ? uk.time.formatPattern(data.value().startDate, dateFormat, ISOFormat) : "";
                            var oldStart = !nts.uk.util.isNullOrEmpty($startDate.val()) ? uk.time.formatPattern($startDate.val(), dateFormat, ISOFormat) : $startDate.val();
                            if (startDate !== oldStart) {
                                if (startDate !== "" && startDate !== "Invalid date") {
                                    $startDate.datepicker('setDate', startDate);
                                }
                                else {
                                    $startDate.val("");
                                }
                            }
                            var endDate = !nts.uk.util.isNullOrEmpty(data.value().endDate) ? uk.time.formatPattern(data.value().endDate, dateFormat, ISOFormat) : "";
                            var oldEnd = !nts.uk.util.isNullOrEmpty($endDate.val()) ? uk.time.formatPattern($endDate.val(), dateFormat, ISOFormat) : $endDate.val();
                            if (endDate !== oldEnd) {
                                if (endDate !== "" && endDate !== "Invalid date") {
                                    $endDate.datepicker('setDate', endDate);
                                }
                                else {
                                    $endDate.val("");
                                }
                            }
                        }
                        if (enable === false) {
                            $container.find(".ntsDateRange_Component").removeAttr("tabindex");
                        }
                        else {
                            $container.find(".ntsDateRange_Component").attr("tabindex", $container.data("tabindex"));
                        }
                        $input.prop("disabled", !enable);
                        $container.find(".ntsDateRangeButton").prop("disabled", !enable);
                        var $datePickerArea = $container.find(".ntsDateRange_Container");
                        $datePickerArea.data("required", required);
                    };
                    return NtsDateRangePickerBindingHandler;
                }());
                ko.bindingHandlers['ntsDateRangePicker'] = new NtsDateRangePickerBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=daterangepicker-ko.ext.js.map