module kmk003.base.timerange {
    class ScreenModel {

        value: KnockoutObservable<TimePeriod>;
        startConstraint: string;
        endConstraint: string;
        startName: string;
        endName: string;
        required: boolean;
        enable: KnockoutObservable<boolean>;
        inputFormat: string;
        tabindex: number;
        
        startTime: KnockoutObservable<number>;
        endTime: KnockoutObservable<number>;
        element: JQuery;
        
        /**
        * Constructor.
        */
        constructor(input: any) {
            let self = this;
            
            self.value = input.value;
            self.startConstraint = ko.unwrap(input.startConstraint);
            self.endConstraint = ko.unwrap(input.endConstraint);
            self.startName = ko.unwrap(input.endConstraint);
            self.endName = ko.unwrap(input.endConstraint);
            self.required = ko.unwrap(input.required);
            self.enable = ko.observable(input.enable ? input.enable() : true);
            self.inputFormat = ko.unwrap(input.inputFormat);
            self.tabindex = ko.unwrap(input.tabindex);
            
            self.startTime = ko.observable(0);
            self.endTime = ko.observable(0);
            
            // subscribe
            self.startTime.subscribe((newValue) => {
                if (!self.validTimeRange(newValue, self.endTime())) {
                    return;
                }
                self.value().startTime = newValue;
                self.value().endTime = self.endTime();
                
                // event callback
                self.value.valueHasMutated();
                self.fireEventChangeData();
            });
            self.endTime.subscribe((newValue) => {
                if (!self.validTimeRange(self.startTime(), newValue)) {
                    return;
                }
                self.value().startTime = self.startTime();
                self.value().endTime = newValue;
                
                // event callback
                self.value.valueHasMutated();
                self.fireEventChangeData();
            });
        }
        
        public fireEventChangeData() {
            // Create event changed.
            var changedEvent = new CustomEvent("timerangedatachange", {
                detail: {},
                bubbles: true,
                cancelable: true
            });
            var self = this;
            var htmlElement = self.element.parents('.kmk003-table-component').first().parent();
            if (htmlElement.length > 0 && htmlElement[0].id) {
                document.getElementById(htmlElement[0].id).dispatchEvent(changedEvent);
            }
        }

        /**
         * Binding data to screen items
         */
        public bindDataToScreen(value: KnockoutObservable<TimePeriod>) {
            let self = this;
            
            self.startTime(value().startTime ? value().startTime : 0);
            self.endTime(value().endTime ? value().endTime : 0);
        }

        /**
         * validTimeRange
         */
        private validTimeRange(startTime: number, endTime: number): boolean {
            let self = this;
            
            // clear error
            $('#start-time').ntsEditor('clear');
            $('#end-time').ntsEditor('clear');
            
            // validate
            $('#start-time').ntsEditor('validate');
            $('#end-time').ntsEditor('validate');
            
            $('#time-range-editor').ntsError('clear');
            if (startTime >= endTime) {
                $('#time-range-editor').ntsError('set', {messageId:'Msg_770',messageParams:[]});
                return false;
            }
            return true
        }
    }
    
    /**
     * TimePeriod
     */
    interface TimePeriod {
        startTime: number;
        endTime: number;
    }
    
    class TimeRangeBindingHandler implements KnockoutBindingHandler {
        
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            
            let webserviceLocator: any = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/base/timerange/time-range.xhtml').serialize();
            
            //get data
            let input = valueAccessor();
            let value: any = input.value;

            let screenModel = new ScreenModel(input);
            screenModel.element = $(element);
            if (input.enable) {
                input.enable.subscribe(function(val) {
                    screenModel.enable(val);
                });
            }
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.bindDataToScreen(value);
                $('.time-range-custom').css("float", "left");
            });
        }

    }
    ko.bindingHandlers['ntsTimeRangeEditor'] = new TimeRangeBindingHandler();

}
