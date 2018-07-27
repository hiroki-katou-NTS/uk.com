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
            let value = data.value; 
            let rangeName = ko.unwrap(data.name);
            let startName = ko.unwrap(data.startName);
            let endName = ko.unwrap(data.endName);
            let enable = data.enable === undefined ? true : ko.unwrap(data.enable);
            let showNextPrevious = data.showNextPrevious === undefined ? false : ko.unwrap(data.showNextPrevious);
            let jumpUnit = data.jumpUnit === undefined ? false : ko.unwrap(data.jumpUnit);
            let required = ko.unwrap(data.required);
            
            let id = nts.uk.util.randomId();
            let tabIndex = nts.uk.util.isNullOrEmpty($container.attr("tabindex")) ? "0" : $container.attr("tabindex");
            $container.data("tabindex", tabIndex);
            $container.removeAttr("tabindex");
            
            $container.append("<div class='ntsDateRange_Container' id='"+ id +"' />");
            
            let $datePickerArea = $container.find(".ntsDateRange_Container"); 
            
            $datePickerArea.append("<div class='ntsDateRangeComponent ntsControl ntsDateRange'>" + 
                "<div class='ntsDateRangeComponent ntsStartDate ntsControl nts-datepicker-wrapper'/><div class='ntsDateRangeComponent ntsRangeLabel'><label>～</label></div>" +
                "<div class='ntsDateRangeComponent ntsEndDate ntsControl nts-datepicker-wrapper' /></div>");
            
            $datePickerArea.data("required", required);
            
            let dateFormat: string;
            if(dateType === 'year') {
                dateFormat = 'YYYY'; 
            } else if(dateType === 'yearmonth') {
                dateFormat = 'YYYY/MM'; 
            } else {
                dateFormat = 'YYYY/MM/DD'; 
            }
            var ISOFormat = text.getISOFormat(dateFormat);
            ISOFormat = ISOFormat.replace(/d/g,"").trim();
            
            if (showNextPrevious === true) {
                $datePickerArea.append("<div class= 'ntsDateRangeComponent ntsDateNextButton_Container ntsRangeButton_Container'>" + 
                    "<button class = 'ntsDateNextButton ntsButton ntsDateRangeButton ntsDateRange_Component auto-height'/></div>");        
                $datePickerArea.prepend("<div class='ntsDateRangeComponent ntsDatePreviousButton_Container ntsRangeButton_Container'>" + 
                    "<button class = 'ntsDatePrevButton ntsButton ntsDateRangeButton ntsDateRange_Component auto-height'/></div>"); 
                
                let $nextButton = $container.find(".ntsDateNextButton").text("▶").css("margin-left", "3px");
                let $prevButton = $container.find(".ntsDatePrevButton").text("◀").css("margin-right", "3px");
                
                $nextButton.click(function (evt, ui){
                    jump(true);      
                });
                
                $prevButton.click(function (evt, ui){
                    jump(false);     
                });
                
                var jump = function(isNext: boolean){
                    let $startDate = $container.find(".ntsStartDatePicker");
                    let $endDate = $container.find(".ntsEndDatePicker");
                    
                    let oldValue = value(); 
                    let currentStart = $startDate.val();
                    let currentEnd = $endDate.val();   
                    if (!nts.uk.util.isNullOrEmpty(currentStart)){
                        let startDate = moment(currentStart, dateFormat);   
                        if(startDate.isValid()) {
                            if(jumpUnit === "year"){
                                startDate.year(startDate.year() + (isNext ? 1 : -1));   
                            } else {
                                let isEndOfMonth = startDate.daysInMonth() === startDate.date();
                                startDate.month(startDate.month() + (isNext ? 1 : -1));    
                                if(isEndOfMonth){
                                    startDate.endOf("month");           
                                }      
                            } 
                            oldValue.startDate = startDate.format(dateFormat);         
                        }     
                    }    
                    
                    if (!nts.uk.util.isNullOrEmpty(currentEnd)){
                        let endDate = moment(currentEnd, dateFormat);   
                        if(endDate.isValid()) {
                            if(jumpUnit === "year"){
                                endDate.year(endDate.year() + (isNext ? 1 : -1));   
                            } else {
                                let isEndOfMonth = endDate.daysInMonth() === endDate.date();
                                endDate.month(endDate.month() + (isNext ? 1 : -1));    
                                if(isEndOfMonth){
                                    endDate.endOf("month");           
                                }   
                            }
                            oldValue.endDate = endDate.format(dateFormat);         
                        }     
                    } 
                    value(oldValue);    
                }
            }
              
            let $startDateArea = $datePickerArea.find(".ntsStartDate");
            let $endDateArea = $datePickerArea.find(".ntsEndDate");
            
            $startDateArea.append("<input id='" + id + "-startInput'  class='ntsDatepicker nts-input ntsStartDatePicker ntsDateRange_Component' />");
            $endDateArea.append("<input id='" + id + "-endInput' class='ntsDatepicker nts-input ntsEndDatePicker ntsDateRange_Component' />");
            
            let $input = $container.find(".ntsDatepicker");
            // Init Datepicker
            $input.datepicker({
                language: 'ja-JP',
                format: ISOFormat,
                autoHide: true,
                weekStart: 0
            });
            
            rangeName = nts.uk.util.isNullOrUndefined(rangeName) ? "期間入力フォーム" : nts.uk.resource.getControlName(rangeName);
            startName = nts.uk.util.isNullOrUndefined(startName) ? "期間入力フォーム開始" : nts.uk.resource.getControlName(startName);
            endName = nts.uk.util.isNullOrUndefined(endName) ? "期間入力フォーム終了" : nts.uk.resource.getControlName(endName);
            
            let $ntsDateRange = $container.find(".ntsRangeLabel");
            
            let getMessage = nts.uk.resource.getMessage;
            
            let validateProcess = function (newText: string, $target: JQuery, isStart: boolean, oldValue: any, result: any){
                if(nts.uk.util.isNullOrEmpty(newText) && $datePickerArea.data("required") === true){
                    $target.ntsError('set', getMessage('FND_E_REQ_INPUT', [ isStart ? startName : endName ]), 'FND_E_REQ_INPUT'); 
                } else if (!result.isValid) {
                    $target.ntsError('set', result.errorMessage, result.errorCode);
                } else if (!nts.uk.util.isNullOrEmpty(newText)) {
                    let startDate = moment(oldValue.startDate, dateFormat);
                    let endDate = moment(oldValue.endDate, dateFormat);
                    if (endDate.isBefore(startDate)) {
                        $ntsDateRange.ntsError('set', getMessage("FND_E_SPAN_REVERSED", [rangeName]), "FND_E_SPAN_REVERSED");    
                    } else if(dateFormat === "YYYY/MM/DD" && maxRange === "oneMonth"){
                        let maxDate = startDate.add(31, "days");
                        if(endDate.isSameOrAfter(maxDate)){
                            $ntsDateRange.ntsError('set', getMessage("FND_E_SPAN_OVER_MONTH", [rangeName]), "FND_E_SPAN_OVER_MONTH");         
                        }
                    } else if (maxRange === "oneYear"){
                        let maxDate = _.cloneDeep(startDate);
                        if(dateFormat === "YYYY/MM/DD"){
                            let currentDate = startDate.date();
                            let isEndMonth = currentDate === startDate.endOf("months").date();
                            let isStartMonth = currentDate === 1;
    //                        maxDate = maxDate.add(1, 'year').add(-1, "months");
                            maxDate = maxDate.date(1).add(1, 'year');
                            if(isStartMonth){
                                maxDate = maxDate.month(maxDate.month() - 1).endOf("months");
                            } else if(isEndMonth){
                                maxDate = maxDate.endOf("months").add(-1, "days");        
                            } else {
                                maxDate = maxDate.date(currentDate - 1);    
                            }    
                        } else if(dateFormat === "YYYY/MM"){
                            maxDate = maxDate.add(1, 'year').add(-1, "months");   
                        } else {
                            maxDate = maxDate.add(1, 'year');
                        }
                        if (endDate.isAfter(maxDate)) {
                            $ntsDateRange.ntsError('set', getMessage("FND_E_SPAN_OVER_YEAR", [rangeName]), "FND_E_SPAN_OVER_YEAR");        
                        }
                    }  
                }    
            }
            
            $input.on("change", (e) => {
                let $target = $(e.target);
                var newText = $target.val();
                $target.ntsError('clear');
                $ntsDateRange.ntsError("clear");
                
                let isStart = $target.hasClass("ntsStartDatePicker");
                
                var validator = new validation.TimeValidator(isStart ? startName : endName, "", 
                    {required: false, outputFormat: dateFormat, valueType: "string"});
                
                var valueX = time.formatPattern(newText, dateFormat, ISOFormat);
                if(!nts.uk.util.isNullOrEmpty(valueX) && valueX !== "Invalid date"){
                    $target.val(valueX);
                    $target.datepicker("update");
                    newText = valueX;
                }

                let result = validator.validate(newText);
                let oldValue = value();
                if (isStart) {
                    oldValue.startDate = result.isValid ? result.parsedValue : newText;            
                } else {
                    oldValue.endDate = result.isValid ? result.parsedValue : newText;    
                }
                
                validateProcess(newText, $target, isStart, oldValue, result);
                value(oldValue);
            });
            
            $input.on("blur", (e) => {
                let isStart = $(e.target).hasClass("ntsStartDatePicker");
                var newText = $(e.target).val();
                if(nts.uk.util.isNullOrEmpty(newText) && $datePickerArea.data("required") === true){
                    $(e.target).ntsError('set', getMessage('FND_E_REQ_INPUT', [ isStart ? startName : endName ]), 'FND_E_REQ_INPUT'); 
                } else {
                    
                    var validator = new validation.TimeValidator(isStart ? startName : endName, "", 
                        {required: false, outputFormat: dateFormat, valueType: "string"});
                    
                    var result = validator.validate(newText);
                    if (!result.isValid) {
                        $(e.target).ntsError('set', result.errorMessage, result.errorCode);
                    }    
                }
            });


            $input.on('validate', (function(e: Event) {
                let $target = $(e.target);
                var newText = $target.val(); 
                let isStart = $target.hasClass("ntsStartDatePicker");
                let oldValue = value();
                
                var validator = new validation.TimeValidator(isStart ? startName : endName, "", 
                    {required: false, outputFormat: dateFormat, valueType: "string"});
                
                var result = validator.validate(newText);
                
                $target.ntsError('clear');
                $ntsDateRange.ntsError("clear");
                
                validateProcess(newText, $target, isStart, oldValue, result);
            }));
            
            $container.find(".ntsDateRange_Component").attr("tabindex", tabIndex);
            $input.ntsDatepicker("bindFlip");
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let $container = $(element);
            let dateType = ko.unwrap(data.type);
            let maxRange = ko.unwrap(data.maxRange);
            let dataName = ko.unwrap(data.name);
            let enable = data.enable === undefined ? true : ko.unwrap(data.enable);
            let required = ko.unwrap(data.required);
            
            let dateFormat: string;
            if(dateType === 'year') {
                dateFormat = 'YYYY'; 
            } else if(dateType === 'yearmonth') {
                dateFormat = 'YYYY/MM'; 
            } else {
                dateFormat = 'YYYY/MM/DD'; 
            }
            var ISOFormat = text.getISOFormat(dateFormat);
            ISOFormat = ISOFormat.replace(/d/g,"").trim();
            
            let $input = $container.find(".ntsDatepicker");
            let $startDate = $container.find(".ntsStartDatePicker");
            let $endDate = $container.find(".ntsEndDatePicker");
            
            if(!nts.uk.util.isNullOrUndefined(data.value())){
                let startDate = !nts.uk.util.isNullOrEmpty(data.value().startDate) ? time.formatPattern(data.value().startDate, dateFormat, ISOFormat) : "";
                let oldStart = !nts.uk.util.isNullOrEmpty($startDate.val()) ? time.formatPattern($startDate.val(), dateFormat, ISOFormat) : $startDate.val(); 
                if(startDate !== oldStart){
                    if (startDate !== "" && startDate !== "Invalid date") {
                    // Check equals to avoid multi datepicker with same value
                        $startDate.datepicker('setDate', startDate);
                    } else {
                        $startDate.val("");
                    }     
                } 
                var endDate = !nts.uk.util.isNullOrEmpty(data.value().endDate) ? time.formatPattern(data.value().endDate, dateFormat, ISOFormat) : "";
                let oldEnd = !nts.uk.util.isNullOrEmpty($endDate.val()) ? time.formatPattern($endDate.val(), dateFormat, ISOFormat) : $endDate.val();
                if (endDate !== oldEnd){
                    if (endDate !== "" && endDate !== "Invalid date") {
                        // Check equals to avoid multi datepicker with same value
                        $endDate.datepicker('setDate', endDate);
                    } else {
                        $endDate.val("");
                    }       
                }
            }
            if(enable === false){
                $container.find(".ntsDateRange_Component").removeAttr("tabindex");    
            } else {
                $container.find(".ntsDateRange_Component").attr("tabindex", $container.data("tabindex"));         
            } 
            $input.prop("disabled", !enable);
            $container.find(".ntsDateRangeButton").prop("disabled", !enable);
            let $datePickerArea = $container.find(".ntsDateRange_Container"); 
            $datePickerArea.data("required", required);
        }
    }

    ko.bindingHandlers['ntsDateRangePicker'] = new NtsDateRangePickerBindingHandler();
}
