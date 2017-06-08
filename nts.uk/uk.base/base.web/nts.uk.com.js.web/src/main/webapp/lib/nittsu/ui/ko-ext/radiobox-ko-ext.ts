/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * RadioBoxGroup binding handler
     */
    class NtsRadioBoxGroupBindingHandler implements KnockoutBindingHandler {

        constructor() { }
        
        /**
         * Init
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            var data = valueAccessor();
            var optionValue: string = ko.unwrap(data.optionsValue);
            let enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            
            
            var container = $(element);
            container.addClass("ntsControl radio-wrapper");
            container.data("enable", enable);
            
            if(nts.uk.util.isNullOrUndefined(container.attr("tabindex"))){
                container.attr("tabindex", "0");    
            }
            container.keydown(function (evt, ui) {
                let code = evt.which || evt.keyCode;
                if (code === 32) {
                    evt.preventDefault(); 
                }        
            });
            
            container.keyup(function (evt, ui) {
                if(container.data("enable") !== false) {
                    let code = evt.which || evt.keyCode;
                    let checkitem;
                    if (code === 32) {
                        checkitem = $(_.find(container.find("input[type='radio']"), function (radio, idx){
                            return  $(radio).attr("disabled") !== "disabled";     
                        }));   
                    } else if (code === 37 || code === 38) {
                        let inputList = _.filter(container.find("input[type='radio']"), function (radio, idx){
                            return  $(radio).attr("disabled") !== "disabled";     
                        });
//                        let inputList = container.find("input[type='radio']");
                        let currentSelect = _.findIndex(inputList, function (item){
                            return $(item).is(":checked");
                        });   
                        checkitem =  $(inputList[currentSelect - 1]);
                    } else if (code === 39 || code === 40) {
                        let inputList = _.filter(container.find("input[type='radio']"), function (radio, idx){
                            return  $(radio).attr("disabled") !== "disabled";     
                        });
                        let currentSelect = _.findIndex(inputList, function (item){
                            return $(item).is(":checked");
                        });   
                        checkitem = $(inputList[currentSelect + 1])  
                    }     
                    if(checkitem !== undefined && checkitem.length > 0){
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
        }
        
        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
            var options: any = data.options === undefined ? [] : JSON.parse(ko.toJSON(data.options));
            var optionValue: string = ko.unwrap(data.optionsValue);
            var optionText: string = ko.unwrap(data.optionsText);
            var selectedValue: any = data.value;
            var enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;

            // Container
            var container = $(element);

            var getOptionValue = (item) => {
                return (optionValue === undefined) ? item : item[optionValue];
            };

            container.data("enable", enable);
            // Render
            if (!_.isEqual(container.data("options"), options)) {
                var radioName = util.randomId();
                container.empty();
                _.forEach(options, (option) => {
                    var radioBoxLabel = $("<label class='ntsRadioBox'></label>");
                    var radioBox = $('<input type="radio">').data("option", option).attr("name", radioName).data("value", getOptionValue(option)).on("change", function() {
                        var self = $(this);
                        if (self.is(":checked"))
                            selectedValue(self.data("value"));
                    });
                    let disableOption = option["enable"];
                    if(!nts.uk.util.isNullOrUndefined(disableOption) && (disableOption === false)){
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
            var checkedRadio = _.find(container.find("input[type='radio']"), (item) => {
                return _.isEqual(JSON.parse(ko.toJSON(selectedValue())), $(item).data("value"));
            });
            if (checkedRadio !== undefined)
                $(checkedRadio).prop("checked", true);

            // Enable
            if(enable === true) {
                _.forEach(container.find("input[type='radio']"), function (radio){
                    let dataOpion = $(radio).data("option");
                    if(dataOpion["enable"] !== false){
                        $(radio).removeAttr("disabled");        
                    }        
                }); 
            } else if (enable === false){
                container.find("input[type='radio']").attr("disabled", "disabled");
                new nts.uk.util.value.DefaultValue().applyReset(container, data.value);
            }    
//            }
        }
    }
    
    ko.bindingHandlers['ntsRadioBoxGroup'] = new NtsRadioBoxGroupBindingHandler();
}