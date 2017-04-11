/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    class DatePickerBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var value = data.value;
            var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
            var dateFormat: string = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
            var ISOFormat = text.getISOFormat(dateFormat);
            var hasDayofWeek: boolean = (ISOFormat.indexOf("ddd") !== -1);
            var dayofWeekFormat: string = ISOFormat.replace(/[^d]/g, "");
            ISOFormat = ISOFormat.replace(/d/g,"").trim();
            var valueFormat: string = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : "";
            var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
            var button: boolean = (data.button !== undefined) ? ko.unwrap(data.button) : false;
            var startDate: any = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
            var endDate: any = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;
            var autoHide: boolean = (data.autoHide !== undefined) ? ko.unwrap(data.autoHide) : true;
            var valueType = typeof value();
            if (valueType === "string") {
                valueFormat = (valueFormat) ? valueFormat : text.getISOFormat("ISO");
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
            var inputClass: string = (ISOFormat.length < 10) ? "yearmonth-picker" : "";
            var $input: any = $("<input id='" + container.attr("id") + "-input' class='ntsDatepicker nts-input' />").addClass(inputClass);
            container.append($input);
            if (hasDayofWeek) {
                var lengthClass: string = (dayofWeekFormat.length > 3) ? "long-day" : "short-day";
                var $label: any = $("<label id='" + container.attr("id") + "-label' for='" + container.attr("id") + "-input' class='dayofweek-label' />");
                $input.addClass(lengthClass);
                container.append($label);
            }
            
            // Init Datepicker
            $input.datepicker({
                language: 'ja-JP',
                format: ISOFormat,
                startDate: startDate,
                endDate: endDate,
                autoHide: autoHide,
            });

            var validator = new validation.TimeValidator(constraintName, {required: required, inputFormat: valueFormat, valueType: valueType});
            $input.on("change", (e) => {
                var newText = $input.val();
                var result = validator.validate(newText);
                $input.ntsError('clear');
                if (result.isValid) {
                    // Day of Week
                    if (hasDayofWeek)
                        $label.text("(" + time.formatPattern(newText, "", dayofWeekFormat) + ")");
                    value(result.parsedValue);
                }
                else {
                    $input.ntsError('set', result.errorMessage);
                    value(newText);
                }
            });

            $input.on('validate', (function(e: Event) {
                var newText = $input.val();
                var result = validator.validate(newText);
                $input.ntsError('clear');
                if (result.isValid) {
                    $input.ntsError('set', "Invalid format");
                }
            }));
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var data = valueAccessor();
            var value = data.value;
            var dateFormat: string = (data.dateFormat !== undefined) ? ko.unwrap(data.dateFormat) : "YYYY/MM/DD";
            var ISOFormat = text.getISOFormat(dateFormat);
            var hasDayofWeek: boolean = (ISOFormat.indexOf("ddd") !== -1);
            var dayofWeekFormat: string = ISOFormat.replace(/[^d]/g, "");
            ISOFormat = ISOFormat.replace(/d/g,"").trim();
            var valueFormat: string = (data.valueFormat !== undefined) ? ko.unwrap(data.valueFormat) : ISOFormat;
            var disabled: boolean = (data.disabled !== undefined) ? ko.unwrap(data.disabled) : false;
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : undefined;
            var startDate: any = (data.startDate !== undefined) ? ko.unwrap(data.startDate) : null;
            var endDate: any = (data.endDate !== undefined) ? ko.unwrap(data.endDate) : null;

            var container = $(element);
            var init = container.data("init");
            var $input: any = container.find(".nts-input");
            var $label: any = container.find(".dayofweek-label");
            
            // Value Binding
            var dateFormatValue = (value() !== "") ? time.formatPattern(value(), valueFormat, ISOFormat) : "";
            if (init === true || time.formatPattern($input.datepicker("getDate",true),"",ISOFormat) !== dateFormatValue) {
                if (dateFormatValue !== "" && dateFormatValue !== "Invalid date") {
                    $input.datepicker('setDate', dateFormatValue);
                    // Day of Week
                    if (hasDayofWeek)
                        $label.text("(" + moment.utc(value(), valueFormat).format(dayofWeekFormat) + ")");
                }
            }
            container.data("init", false);
            
            // Properties Binding
            $input.datepicker('setStartDate', startDate);
            $input.datepicker('setEndDate', endDate);
            if (enable !== undefined)
               $input.prop("disabled", !enable);
            else
                $input.prop("disabled", disabled);
            if (data.button)
                container.find('.datepicker-btn').prop("disabled", disabled);
        }
    }

    ko.bindingHandlers['ntsDatePicker'] = new DatePickerBindingHandler();
}