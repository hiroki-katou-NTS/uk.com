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
                        var name = data.name !== undefined ? ko.unwrap(data.name) : "";
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
                        var focus = (data.focus !== undefined) ? ko.unwrap(data.focus) : false;
                        var autoHide = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
                        var acceptJapaneseCalendar = (data.acceptJapaneseCalendar !== undefined) ? ko.unwrap(data.acceptJapaneseCalendar) : true;
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
                        var idString;
                        if (!container.attr("id")) {
                            idString = nts.uk.util.randomId();
                        }
                        else {
                            idString = container.attr("id");
                            container.removeAttr("id");
                        }
                        var tabIndex = nts.uk.util.isNullOrEmpty(container.attr("tabindex")) ? "0" : container.attr("tabindex");
                        container.removeAttr("tabindex");
                        var containerClass = container.attr('class');
                        container.removeClass(containerClass);
                        container.addClass("ntsControl nts-datepicker-wrapper").data("init", true);
                        var inputClass = (ISOFormat.length < 10) ? "yearmonth-picker" : "";
                        var $input = $("<input id='" + container.attr("id") + "' class='ntsDatepicker nts-input reset-element' tabindex='" + tabIndex + "'/>").addClass(inputClass);
                        $input.addClass(containerClass).attr("id", idString).attr("data-name", container.data("name"));
                        container.append($input);
                        $input.data("required", required);
                        var jumpButtonsDisplay = data.showJumpButtons !== undefined ? ko.unwrap(data.showJumpButtons) : false;
                        var fiscalYear = data.fiscalYear !== undefined ? ko.unwrap(data.fiscalYear) : false;
                        var $prevButton, $nextButton;
                        if (jumpButtonsDisplay) {
                            $prevButton = $("<button/>").addClass("ntsDateNextButton ntsButton ntsDatePickerButton ntsDatePicker_Component auto-height")
                                .text("◀").css("margin-right", "3px").attr("tabIndex", tabIndex);
                            $nextButton = $("<button/>").addClass("ntsDatePrevButton ntsButton ntsDatePickerButton ntsDatePicker_Component auto-height")
                                .text("▶").css("margin-left", "3px").attr("tabIndex", tabIndex);
                            $input.before($prevButton).after($nextButton);
                        }
                        if (data.dateFormat === "YYYY") {
                            var $yearType = $("<label/>").attr("for", idString)
                                .css({ "position": "absolute",
                                "line-height": "30px",
                                "right": jumpButtonsDisplay ? "40px" : "5px" });
                            var labelText = fiscalYear ? "年度" : "年";
                            $yearType.text(labelText);
                            container.append($yearType);
                        }
                        if (hasDayofWeek) {
                            var lengthClass = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                            var $label = $("<label id='" + idString + "-label' for='" + idString + "' class='dayofweek-label' />");
                            $input.addClass(lengthClass);
                            container.append($label);
                        }
                        $input.datepicker({
                            language: 'ja-JP',
                            format: ISOFormat,
                            startDate: startDate,
                            endDate: endDate,
                            autoHide: autoHide,
                            weekStart: 0,
                        }).data("dateNormalizer", DatePickerNormalizer.getInstance($input, $prevButton, $nextButton).setCssRanger(data.cssRanger)
                            .fiscalMonthsMode(data.fiscalMonthsMode)
                            .setDefaultCss(data.defaultClass || ""));
                        name = nts.uk.resource.getControlName(name);
                        $input.on("change", function (e) {
                            var newText = $input.val();
                            var validator = new ui.validation.TimeValidator(name, constraintName, { required: $input.data("required"),
                                outputFormat: nts.uk.util.isNullOrEmpty(valueFormat) ? ISOFormat : valueFormat,
                                valueType: valueType, acceptJapaneseCalendar: acceptJapaneseCalendar });
                            var result = validator.validate(newText);
                            $input.ntsError('clear');
                            if (result.isValid) {
                                if (!validateMinMax(result.parsedValue)) {
                                    return;
                                }
                                if (hasDayofWeek) {
                                    if (uk.util.isNullOrEmpty(result.parsedValue))
                                        $label.text("");
                                    else
                                        $label.text("(" + uk.time.formatPattern(newText, "", dayofWeekFormat) + ")");
                                }
                                value(result.parsedValue);
                            }
                            else {
                                $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                value(newText);
                            }
                        });
                        $input.on("blur", function () {
                            var newText = $input.val();
                            var validator = new ui.validation.TimeValidator(name, constraintName, { required: $input.data("required"),
                                outputFormat: nts.uk.util.isNullOrEmpty(valueFormat) ? ISOFormat : valueFormat,
                                valueType: valueType, acceptJapaneseCalendar: acceptJapaneseCalendar });
                            var result = validator.validate(newText);
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage, result.errorCode, false);
                            }
                            else if (acceptJapaneseCalendar) {
                                if (hasDayofWeek) {
                                    if (uk.util.isNullOrEmpty(result.parsedValue))
                                        $label.text("");
                                    else
                                        $label.text("(" + uk.time.formatPattern(newText, "", dayofWeekFormat) + ")");
                                }
                            }
                        });
                        var validateMinMax = function (parsedValue) {
                            if (nts.uk.util.isNullOrEmpty(parsedValue)) {
                                return true;
                            }
                            var mmRs = new nts.uk.time.MomentResult();
                            var otFormat = nts.uk.util.isNullOrEmpty(valueFormat) ? ISOFormat : valueFormat;
                            var minDate = !nts.uk.util.isNullOrUndefined($input.data('startDate')) ? moment($input.data('startDate'), otFormat) : mmRs.systemMin();
                            var maxDate = !nts.uk.util.isNullOrUndefined($input.data('endDate')) ? moment($input.data('endDate'), otFormat) : mmRs.systemMax();
                            var momentCurrent = moment(parsedValue, otFormat);
                            var error = false;
                            if (momentCurrent.isBefore(minDate, 'day')) {
                                error = true;
                            }
                            else if (momentCurrent.isAfter(maxDate, 'day')) {
                                error = true;
                            }
                            if (error) {
                                var isHasYear = (nts.uk.util.isNullOrEmpty(otFormat) ? false : otFormat.indexOf("Y") >= 0) || otFormat.indexOf("Y") >= 0;
                                var isHasMonth = (nts.uk.util.isNullOrEmpty(otFormat) ? false : otFormat.indexOf("M") >= 0) || otFormat.indexOf("M") >= 0;
                                var isHasDay = (nts.uk.util.isNullOrEmpty(otFormat) ? false : otFormat.indexOf("D") >= 0) || otFormat.indexOf("D") >= 0;
                                var mesId = "FND_E_DATE_Y";
                                var fm = "YYYY";
                                if (isHasDay && isHasMonth && isHasYear) {
                                    mesId = "FND_E_DATE_YMD", fm = "YYYY/MM/DD";
                                }
                                else if (isHasMonth && isHasYear) {
                                    mesId = "FND_E_DATE_YM", fm = "YYYY/MM";
                                }
                                $input.ntsError('set', { messageId: mesId, messageParams: [name, minDate.format(fm), maxDate.format(fm)] }, mesId, false);
                                if (hasDayofWeek) {
                                    $label.text("");
                                }
                                return false;
                            }
                            return true;
                        };
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var validator = new ui.validation.TimeValidator(name, constraintName, { required: $input.data("required"),
                                outputFormat: nts.uk.util.isNullOrEmpty(valueFormat) ? ISOFormat : valueFormat,
                                valueType: valueType, acceptJapaneseCalendar: acceptJapaneseCalendar });
                            var result = validator.validate(newText);
                            $input.ntsError('clearKibanError');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage, result.errorCode, false);
                            }
                            else if (acceptJapaneseCalendar) {
                                if (hasDayofWeek) {
                                    if (uk.util.isNullOrEmpty(result.parsedValue))
                                        $label.text("");
                                    else
                                        $label.text("(" + uk.time.formatPattern(newText, "", dayofWeekFormat) + ")");
                                }
                            }
                        }));
                        new nts.uk.util.value.DefaultValue().onReset($input, data.value);
                        container.data("init", false);
                        $input.ntsDatepicker("bindFlip");
                        $input.data('startDate', startDate);
                        $input.data('endDate', endDate);
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
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var focus = (data.focus !== undefined) ? ko.unwrap(data.focus) : false;
                        var container = $(element);
                        var dateNormalizer = container.find("input").data("dateNormalizer");
                        if (dateNormalizer) {
                            if (data.cssRanger) {
                                dateNormalizer.setCssRanger(ko.unwrap(data.cssRanger));
                            }
                        }
                        var init = container.data("init");
                        var $input = container.find(".nts-input");
                        var $label = container.find(".dayofweek-label");
                        $input.datepicker('setStartDate', startDate);
                        $input.datepicker('setEndDate', endDate);
                        $input.data('startDate', startDate);
                        $input.data('endDate', endDate);
                        if (value() !== $input.val()) {
                            var dateFormatValue = (value() !== "") ? uk.text.removeFromStart(uk.time.formatPattern(value(), valueFormat, ISOFormat), "0") : "";
                            if (dateFormatValue !== "" && dateFormatValue !== "Invalid date") {
                                $input.datepicker('setDate', new Date(dateFormatValue.replace(/\//g, "-")));
                                $label.text("(" + uk.time.formatPattern(value(), valueFormat, dayofWeekFormat) + ")");
                            }
                            else {
                                $input.val("");
                                $label.text("");
                            }
                        }
                        $input.data("required", required);
                        if (enable !== undefined) {
                            $input.prop("disabled", !enable);
                            container.find(".ntsDatePickerButton").prop("disabled", !enable);
                        }
                        else {
                            $input.prop("disabled", disabled);
                            container.find(".ntsDatePickerButton").prop("disabled", disabled);
                        }
                        if ($input.prop("disabled") === true) {
                            new nts.uk.util.value.DefaultValue().applyReset($input, value);
                        }
                        if (data.button)
                            container.find('.datepicker-btn').prop("disabled", disabled);
                        if (focus) {
                            $input.focus();
                        }
                    };
                    return DatePickerBindingHandler;
                }());
                ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
                var ViewLocation;
                (function (ViewLocation) {
                    ViewLocation[ViewLocation["PREV"] = 0] = "PREV";
                    ViewLocation[ViewLocation["CURRENT"] = 1] = "CURRENT";
                    ViewLocation[ViewLocation["NEXT"] = 2] = "NEXT";
                })(ViewLocation || (ViewLocation = {}));
                var DatePickerNormalizer = (function () {
                    function DatePickerNormalizer() {
                        this.fiscalMonth = 1;
                        this.NAMESPACE = "datepicker";
                        this.YEARS = "years";
                        this.MONTHS = "months";
                        this.DAYS = "days";
                        this.WEEK = "week";
                        this.PICKER = " picker";
                        this.YEAR = "year";
                        this.MONTH = "month";
                        this.DAY = "day";
                        this.YEAR_TEXT = "年";
                        this.MONTH_TEXT = "月";
                        this.PERIOD_TEXT = "度";
                        this.structure = { 0: this.YEARS, 1: this.MONTHS, 2: this.DAYS };
                        this.EVENT_SHOW = "show." + this.NAMESPACE;
                        this.EVENT_KEYUP = "keyup." + this.NAMESPACE;
                        this.EVENT_PICK = "pick." + this.NAMESPACE;
                        this.EVENT_CLICK = "click";
                        this.Y_FORMAT = "YYYY";
                        this.YM_FORMAT = "YYYY/MM";
                        this.YMD_FORMAT = "YYYY/MM/DD";
                        this.DATE_SPLITTER = "/";
                    }
                    DatePickerNormalizer.getInstance = function ($input, $prev, $next) {
                        var instance = new DatePickerNormalizer();
                        instance.$input = $input;
                        instance.$prev = $prev;
                        instance.$next = $next;
                        return instance.onShow().onKeyup().onPick().onJump();
                    };
                    DatePickerNormalizer.prototype.setCssRanger = function (range) {
                        this.cssRanger = range;
                        return this;
                    };
                    DatePickerNormalizer.prototype.setFiscalMonth = function (month) {
                        this.fiscalMonth = month;
                        return this;
                    };
                    DatePickerNormalizer.prototype.setDefaultCss = function (clazz) {
                        this.defaultCss = clazz;
                        return this;
                    };
                    DatePickerNormalizer.prototype.fiscalMonthsMode = function (value) {
                        if (value === true)
                            this.setFiscalMonth(4);
                        return this;
                    };
                    DatePickerNormalizer.prototype.getPicker = function () {
                        return this.$input.data(this.NAMESPACE).$picker;
                    };
                    DatePickerNormalizer.prototype.getYearsPicker = function () {
                        return this.$input.data(this.NAMESPACE).$yearsPicker;
                    };
                    DatePickerNormalizer.prototype.getMonthsPicker = function () {
                        return this.$input.data(this.NAMESPACE).$monthsPicker;
                    };
                    DatePickerNormalizer.prototype.getYearsBoard = function () {
                        return this.$input.data(this.NAMESPACE).$years;
                    };
                    DatePickerNormalizer.prototype.getMonthsBoard = function () {
                        return this.$input.data(this.NAMESPACE).$months;
                    };
                    DatePickerNormalizer.prototype.getCurrentYear = function () {
                        return this.$input.data(this.NAMESPACE).$yearCurrent;
                    };
                    DatePickerNormalizer.prototype.getView = function (view, isCurrentView) {
                        var pickerView, viewPart, currentViewPart;
                        var viewName = this.structure[view];
                        switch (viewName) {
                            case this.YEARS:
                                pickerView = this.YEARS + this.PICKER;
                                viewPart = this.YEARS;
                                currentViewPart = "current year";
                                break;
                            case this.MONTHS:
                                pickerView = this.MONTHS + this.PICKER;
                                viewPart = this.MONTHS;
                                currentViewPart = "current month";
                                break;
                            case this.DAYS:
                                pickerView = this.DAYS + this.PICKER;
                                viewPart = this.DAYS;
                                break;
                            case this.WEEK:
                                pickerView = this.DAYS + this.PICKER;
                                viewPart = this.WEEK;
                                break;
                        }
                        return $(this.getPicker()[0]).children().filter(function (idx, elm) {
                            return $(elm).data("view") === pickerView;
                        }).find("ul").filter(function (idx, elm) {
                            if (isCurrentView === true)
                                return idx === 0;
                            else
                                return $(elm).data("view") === viewPart;
                        });
                    };
                    DatePickerNormalizer.prototype.getMutedClass = function () {
                        return this.options !== undefined ? this.options.mutedClass : "";
                    };
                    DatePickerNormalizer.prototype.getPickedClass = function () {
                        return this.options !== undefined ? this.options.pickedClass : "";
                    };
                    DatePickerNormalizer.prototype.setColorLevel = function () {
                        if (this.options.format === this.Y_FORMAT)
                            this.colorLevel = this.YEARS;
                        else if (this.options.format === this.YM_FORMAT)
                            this.colorLevel = this.MONTHS;
                        else if (this.options.format === this.YMD_FORMAT)
                            this.colorLevel = this.DAYS;
                        if (this.selectedView === undefined)
                            this.selectedView = this.colorLevel;
                    };
                    DatePickerNormalizer.prototype.color = function () {
                        var _this = this;
                        if (this.cssRanger === undefined)
                            return;
                        if (this.cssRanger.constructor === Array) {
                            _.each(this.cssRanger, function (cell) { return _this.colorCell(cell, ViewLocation.CURRENT, -1); });
                            return;
                        }
                        this.colorNode(this.cssRanger, ViewLocation.CURRENT, 0);
                        this.colorNode(this.cssRanger, ViewLocation.NEXT, 0);
                        this.colorNode(this.cssRanger, ViewLocation.PREV, 0);
                    };
                    DatePickerNormalizer.prototype.colorNode = function (holders, location, currentLayer) {
                        var _this = this;
                        var holder;
                        var handledYear = location === ViewLocation.CURRENT ? this.viewYear : this.viewYear + 1;
                        if (this.colorLevel === this.structure[currentLayer + 1]) {
                            switch (currentLayer) {
                                case 0:
                                    holder = handledYear;
                                    break;
                                case 1:
                                    if (location === ViewLocation.CURRENT)
                                        holder = this.viewMonth;
                                    else if (location === ViewLocation.PREV)
                                        holder = this.viewMonth - 1;
                                    else
                                        holder = this.viewMonth + 1;
                                    break;
                                case 2:
                                    holder = this.date;
                                    break;
                                default:
                                    holder = handledYear;
                                    currentLayer = 0;
                                    break;
                            }
                        }
                        else {
                            switch (currentLayer) {
                                case 0:
                                    holder = this.viewYear;
                                    break;
                                case 1:
                                    holder = this.viewMonth;
                                    break;
                                case 2:
                                    holder = this.date;
                                    break;
                                default:
                                    holder = this.viewYear;
                                    currentLayer = 0;
                                    break;
                            }
                        }
                        if (holders.hasOwnProperty(holder)) {
                            if (holders[holder].constructor === Array) {
                                _.each(holders[holder], function (cell) { return _this.colorCell(cell, location, currentLayer); });
                                return;
                            }
                            currentLayer++;
                            this.colorNode(holders[holder], location, currentLayer);
                        }
                    };
                    DatePickerNormalizer.prototype.colorCell = function (cell, location, layer) {
                        var self = this;
                        var data = typeof cell === "object" ? Object.keys(cell)[0] : cell;
                        var $target = this.$view.children().filter(function (idx, elm) {
                            if (self.structure[layer] === self.YEARS) {
                                return $(elm).text() === self.defaultMonths[data - 1]
                                    && ((location === ViewLocation.PREV && $(elm).data("view").indexOf("prev") !== -1)
                                        || (location === ViewLocation.NEXT && $(elm).data("view").indexOf("next") !== -1)
                                        || location === ViewLocation.CURRENT && $(elm).data("view").indexOf("prev") === -1
                                            && $(elm).data("view").indexOf("next") === -1);
                            }
                            else if (self.structure[layer] === self.MONTHS) {
                                return $(elm).text() === data.toString()
                                    && ((location === ViewLocation.PREV && $(elm).data("view").indexOf("prev") !== -1)
                                        || (location === ViewLocation.NEXT && $(elm).data("view").indexOf("next") !== -1)
                                        || location === ViewLocation.CURRENT && $(elm).data("view").indexOf("prev") === -1
                                            && $(elm).data("view").indexOf("next") === -1);
                            }
                            else if (layer === -1) {
                                return $(elm).text() === data.toString();
                            }
                        });
                        if ($target.length > 0) {
                            $target.addClass((typeof cell === "object" && cell[data] !== undefined) ? cell[data] : this.defaultCss);
                        }
                    };
                    DatePickerNormalizer.prototype.fillFiscalMonthsInYear = function () {
                        var self = this;
                        if (this.fiscalMonth === 1)
                            return;
                        var nextYearMonths = this.defaultMonths.slice(0, this.fiscalMonth - 1);
                        var currentYearMonths = this.defaultMonths.slice(this.fiscalMonth - 1);
                        var newMonths = $.merge(currentYearMonths, nextYearMonths);
                        var nextYearMark = 12 - this.fiscalMonth;
                        this.getMonthsBoard().children().each(function (idx, elm) {
                            $(elm).text(newMonths[idx]);
                            if (idx > nextYearMark)
                                $(elm).addClass(self.getMutedClass()).attr("data-view", "fiscalMonth next")
                                    .data("view", "fiscalMonth next").css("font-size", "inherit");
                        });
                        var $currentYear = this.getCurrentYear();
                        if ($currentYear.length > 0)
                            $currentYear.text(this.viewYear + this.yearText());
                    };
                    DatePickerNormalizer.prototype.allowPickMonth = function () {
                        return (this.viewMonth < this.fiscalMonth && this.viewYear === this.year - 1)
                            || (this.viewMonth >= this.fiscalMonth && this.viewYear === this.year);
                    };
                    DatePickerNormalizer.prototype.allowPickDate = function () {
                        return this.viewYear === this.year && this.viewMonth === this.month;
                    };
                    DatePickerNormalizer.prototype.pickMonth = function () {
                        var self = this;
                        if (self.fiscalMonth === 1)
                            return;
                        var month = self.month + self.MONTH_TEXT;
                        this.getMonthsBoard().children().each(function (idx, elm) {
                            var view;
                            if ($(elm).text() === month.toString()) {
                                view = "month picked";
                                $(elm).addClass(self.getPickedClass()).attr("data-view", view).data("view", view);
                            }
                            else if ($(elm).hasClass(self.getPickedClass())) {
                                view = $(elm).data("view").split(" ")[0];
                                $(elm).removeClass(self.getPickedClass()).attr("data-view", view).data("view", view);
                            }
                        });
                    };
                    DatePickerNormalizer.prototype.pickDate = function () {
                        var self = this;
                        if (self.colorLevel !== self.DAYS || self.fiscalMonth === 1)
                            return;
                        var date = self.date;
                        this.$view.children().each(function (idx, elm) {
                            if ($(elm).text() === date.toString() && $(elm).data("view").indexOf("prev") === -1
                                && $(elm).data("view").indexOf("next") === -1) {
                                $(elm).addClass(self.getPickedClass()).attr("data-view", "day picked").data("view", "day picked");
                            }
                            else if ($(elm).hasClass(self.getPickedClass())) {
                                $(elm).removeClass(self.getPickedClass()).attr("data-view", "day").data("view", "day");
                            }
                        });
                    };
                    DatePickerNormalizer.prototype.clearPicked = function () {
                        var self = this;
                        var view = self.colorLevel === self.MONTHS ? "month" : "day";
                        var $selectedBoard;
                        if (this.selectedView === this.MONTHS) {
                            $selectedBoard = this.getMonthsBoard();
                        }
                        else if (this.selectedView === this.DAYS) {
                            $selectedBoard = this.getYearsBoard();
                        }
                        if ($selectedBoard === undefined)
                            return;
                        $selectedBoard.children().filter(function (idx, elm) {
                            return $(elm).data("view").indexOf("picked") !== -1;
                        }).removeClass(self.getPickedClass()).attr("data-view", view).data("view", view);
                    };
                    DatePickerNormalizer.prototype.yearText = function () {
                        return this.fiscalMonth !== 1 ? this.YEAR_TEXT + this.PERIOD_TEXT : this.YEAR_TEXT;
                    };
                    DatePickerNormalizer.prototype.onClick = function () {
                        var self = this;
                        var picker = this.getPicker();
                        picker.off("click", this._click);
                        picker.on("click", $.proxy(this._click, this));
                    };
                    DatePickerNormalizer.prototype._click = function (evt) {
                        var $target = $(evt.target);
                        var view = $target.data("view");
                        switch (view) {
                            case "years prev":
                            case "years next":
                                this.updateYearsView();
                                break;
                            case "year prev":
                                this.viewYear--;
                                this.updateMonthsView();
                                break;
                            case "year next":
                                this.viewYear++;
                                this.updateMonthsView();
                                break;
                            case "month prev":
                                if (this.viewMonth == 1) {
                                    this.viewMonth = 12;
                                    this.viewYear--;
                                }
                                else
                                    this.viewMonth--;
                                this.updateDaysView();
                                break;
                            case "month next":
                                if (this.viewMonth == 12) {
                                    this.viewMonth = 1;
                                    this.viewYear++;
                                }
                                else
                                    this.viewMonth++;
                                this.updateDaysView();
                                break;
                            case "day prev":
                                this.updateDaysView();
                                break;
                            case "day next":
                                this.updateDaysView();
                                break;
                            case "fiscalMonth next":
                                if ($target.hasClass(this.getPickedClass()))
                                    return;
                                var pickedMonth = this.defaultMonths.indexOf($target.text());
                                this._clickFiscalNextMonth(pickedMonth);
                                this.$input.datepicker("hide");
                                if (this.colorLevel === this.DAYS) {
                                    this.$input.datepicker("show");
                                }
                                break;
                            case "year current":
                                this.selectedView = this.YEARS;
                                break;
                            case "month current":
                                this.selectedView = this.MONTHS;
                                if (this.viewMonth < this.fiscalMonth)
                                    this.viewYear--;
                                this.updateMonthsView();
                                break;
                        }
                    };
                    DatePickerNormalizer.prototype.updateYearsView = function () {
                        this.color();
                    };
                    DatePickerNormalizer.prototype.updateMonthsView = function () {
                        if (this.fiscalMonth !== 1) {
                            this.fillFiscalMonthsInYear();
                        }
                        if (this.colorLevel === this.MONTHS) {
                            this.color();
                        }
                        if (this.allowPickMonth())
                            this.pickMonth();
                        if (this.viewMonth < this.fiscalMonth && this.viewYear === this.year)
                            this.clearPicked();
                    };
                    DatePickerNormalizer.prototype.updateDaysView = function () {
                        if (this.colorLevel === this.DAYS) {
                            this.color();
                        }
                        if (this.allowPickDate())
                            this.pickDate();
                    };
                    DatePickerNormalizer.prototype._beforeShow = function () {
                        this.options = this.$input.data(this.NAMESPACE).options;
                        this.setColorLevel();
                        this.defaultMonths = this.options.monthsShort;
                        var text = this.$input.val();
                        var parsedTextTime = this.parseDate(text);
                        if (parsedTextTime !== undefined && parsedTextTime.month === 2) {
                            this.viewYear = this.year = parsedTextTime.year;
                            this.viewMonth = this.month = parsedTextTime.month;
                            this.date = parsedTextTime.date;
                        }
                        else {
                            var initValue = this.$input.datepicker("getDate", true);
                            var viewTime = this.$input.data(this.NAMESPACE).viewDate;
                            this.viewYear = viewTime.getFullYear();
                            this.viewMonth = viewTime.getMonth() + 1;
                            var parsedTime = this.parseDate(initValue);
                            if (parsedTime !== undefined) {
                                this.year = parsedTime.year;
                                this.month = parsedTime.month;
                                this.date = parsedTime.date;
                            }
                            else
                                return;
                        }
                        var colorLevel = this.colorLevel;
                        var layer;
                        if (colorLevel === this.YEARS) {
                            layer = 0;
                        }
                        else if (colorLevel === this.MONTHS) {
                            layer = 1;
                        }
                        else if (colorLevel === this.DAYS) {
                            layer = 2;
                        }
                        this.$view = this.getView(layer);
                        this.$currentView = this.getView(layer, true);
                        if (this.selectedView === this.MONTHS) {
                            if (this.viewMonth < this.fiscalMonth)
                                this.viewYear--;
                            this.fillFiscalMonthsInYear();
                        }
                        this.color();
                        if (this.selectedView === this.MONTHS && this.allowPickMonth()) {
                            if (this.viewMonth < this.fiscalMonth && this.viewYear === this.year)
                                this.clearPicked();
                            this.pickMonth();
                        }
                        else if (this.selectedView === this.DAYS && this.allowPickDate()) {
                            this.pickDate();
                        }
                    };
                    DatePickerNormalizer.prototype.parseDate = function (date) {
                        var exp = /\d+(\/\d+)?(\/\d+)?/;
                        if (exp.test(date) === false)
                            return;
                        var dateParts = date.split(this.DATE_SPLITTER);
                        return {
                            year: parseInt(dateParts[0]),
                            month: parseInt(dateParts[1]),
                            date: dateParts[2] !== undefined ? parseInt(dateParts[2]) : undefined
                        };
                    };
                    DatePickerNormalizer.prototype.onShow = function () {
                        var self = this;
                        this.$input.on(this.EVENT_SHOW, function (evt) {
                            var _self = self;
                            setTimeout(function () {
                                _self._beforeShow.call(_self);
                                _self.onClick.call(_self);
                            }, 0);
                        });
                        return self;
                    };
                    DatePickerNormalizer.prototype.onKeyup = function () {
                        return this;
                    };
                    DatePickerNormalizer.prototype.onPick = function () {
                        var self = this;
                        this.$input.on(this.EVENT_PICK, function (evt) {
                            var view = evt.view;
                            if (view === self.DAY) {
                                self.date = evt.date.getDate();
                                self.month = evt.date.getMonth() + 1;
                                self.viewMonth = self.month;
                                self.year = evt.date.getFullYear();
                                self.viewYear = self.year;
                            }
                            else if (view === self.MONTH) {
                                self._clickFiscalNextMonth.call(self, evt.date.getMonth());
                            }
                            else if (view === self.YEAR) {
                                var _self = self;
                                setTimeout(function () {
                                    _self.year = evt.date.getFullYear();
                                    _self.viewYear = _self.year;
                                    _self.month = _self.viewMonth;
                                    if (_self.viewMonth < _self.fiscalMonth)
                                        _self.viewYear--;
                                    _self.updateMonthsView.call(_self);
                                }, 0);
                            }
                        });
                        return self;
                    };
                    DatePickerNormalizer.prototype._clickFiscalNextMonth = function (pickedMonth) {
                        var self = this;
                        self.month = pickedMonth + 1;
                        self.viewMonth = self.month;
                        if (self.fiscalMonth !== 1) {
                            self.year = self.month >= self.fiscalMonth ? self.viewYear : (self.viewYear + 1);
                            self.viewYear = self.year;
                            self.$input.datepicker("setDate", new Date(self.year, self.month - 1, self.date || 1));
                        }
                    };
                    DatePickerNormalizer.prototype.onJump = function () {
                        var self = this;
                        if (uk.util.isNullOrUndefined(self.$prev) || uk.util.isNullOrUndefined(self.$next))
                            return self;
                        this.$prev.on(this.EVENT_CLICK, function (evt) {
                            self.addTime(-1);
                        });
                        this.$next.on(this.EVENT_CLICK, function (evt) {
                            self.addTime(1);
                        });
                        return self;
                    };
                    DatePickerNormalizer.prototype.addTime = function (value) {
                        var self = this;
                        var year, month, date;
                        if (self.options === undefined)
                            self.options = self.$input.data(self.NAMESPACE).options;
                        var time = self.$input.datepicker("getDate", true);
                        var parsedTime = self.parseDate(time);
                        if (parsedTime !== undefined) {
                            if (self.options.format === self.YMD_FORMAT) {
                                year = parsedTime.year;
                                month = parsedTime.month - 1;
                                date = parsedTime.date + value;
                            }
                            else if (self.options.format === self.YM_FORMAT) {
                                var postCalcVal = parsedTime.month + value;
                                date = 1;
                                if (postCalcVal < 1) {
                                    year = parsedTime.year - 1;
                                    month = 11;
                                }
                                else if (postCalcVal > 12) {
                                    year = parsedTime.year + 1;
                                    month = postCalcVal - 13;
                                }
                                else {
                                    year = parsedTime.year;
                                    month = postCalcVal - 1;
                                }
                            }
                            else if (self.options.format === self.Y_FORMAT) {
                                var postCalcVal = parsedTime.year + value;
                                if (postCalcVal < 1900) {
                                    year = 9999;
                                }
                                else if (postCalcVal > 9999) {
                                    year = 1900;
                                }
                                else
                                    year = postCalcVal;
                                month = 1;
                                date = 1;
                            }
                        }
                        self.$input.datepicker("setDate", new Date(year, month, date));
                    };
                    return DatePickerNormalizer;
                }());
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=datepicker-ko-ext.js.map