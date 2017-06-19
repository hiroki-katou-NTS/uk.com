/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    
    /**
     * Dialog binding handler
     */
    class NtsDateRangePickerBindingHandler implements KnockoutBindingHandler {

        /**
         * Init. sssss 
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let $container = $(element);
                        
            let dateType = ko.unwrap(data.type);
            let maxRange = ko.unwrap(data.maxRange);
            let color = ko.unwrap(data.value);
            let dataName = ko.unwrap(data.name);
            let enable = data.enable === undefined ? true : ko.unwrap(data.enable);
            let showNextPrevious = data.showNextPrevious === undefined ? false : ko.unwrap(data.showNextPrevious);
            
            let id = nts.uk.util.randomId();
            
            $container.append("<div class='ntsDateRange_Container' id='"+ id +"' />");
            
            let $datePickerArea = $container.find(".ntsDateRange_Container"); 
            
            $datePickerArea.append("<div class='ntsStartDate ntsControl nts-datepicker-wrapper'/><div class='ntsRangeLabel'><label>～</label></div>" +
                "<div class='ntsEndDate ntsControl nts-datepicker-wrapper' />");
            
            if (showNextPrevious === true) {
                $datePickerArea.before("<div class= 'ntsDateNextButton'/>");        
                $datePickerArea.after("<div class='ntsDatePreviousButton'/>"); 
            }
              
            let $startDateArea = $datePickerArea.find(".ntsStartDate");
            let $endDateArea = $datePickerArea.find(".ntsEndDate");
            
            $startDateArea.append("<input id='" + id + "-startInput'  class='ntsDatepicker nts-input ntsStartDatePicker' />");
            $endDateArea.append("<input id='" + id + "-endInput' class='ntsDatepicker nts-input ntsEndDatePicker' />");
            
            let dateFormat: string = (dateType !== 'yearmonth') ? "YYYY/MM/DD" : 'YYYY/MM';
            var ISOFormat = text.getISOFormat(dateFormat);
            ISOFormat = ISOFormat.replace(/d/g,"").trim();
            
            let $input = $container.find(".ntsDatepicker");
            // Init Datepicker
            $input.datepicker({
                language: 'ja-JP',
                format: ISOFormat,
                autoHide: true, 
            });
            
            name = nts.uk.resource.getControlName(name);
            $input.on("change", (e) => {
                var newText = $input.val();
            });
            
            $input.on("blur", () => {
                var newText = $input.val();
            });


            $input.on('validate', (function(e: Event) {
                var newText = $input.val();
            }));
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let $container = $(element);
        }
    }

    ko.bindingHandlers['ntsDateRangePicker'] = new NtsDateRangePickerBindingHandler();
}