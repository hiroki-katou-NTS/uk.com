module kmk003.base.timerange {
    class ScreenModel {

        value: KnockoutObservable<TimePeriod>;
        startConstraint: string;
        endConstraint: string;
        startName: string;
        endName: string;
        required: boolean;
        enable: boolean;
        inputFormat: string;
        
        startTime: KnockoutObservable<string>;
        endTime: KnockoutObservable<string>;
        
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
            self.enable = ko.unwrap(input.enable);
            self.inputFormat = ko.unwrap(input.inputFormat);
            
            self.startTime = ko.observable(input.value().startTime);
            self.endTime = ko.observable(input.value().endTime);
            
            // subscribe
            self.startTime.subscribe((newValue) => {
                if (!self.validTimeRange(newValue, self.endTime())) {
                    return;
                }
                self.value().startTime = newValue;
                self.value().endTime = self.endTime();
            });
            self.endTime.subscribe((newValue) => {
                if (!self.validTimeRange(self.startTime(), newValue)) {
                    return;
                }
                self.value().startTime = self.startTime();
                self.value().endTime = newValue;
            });
        }

        /**
         * Binding data to screen items
         */
        public bindDataToScreen(value: KnockoutObservable<TimePeriod>) {
            let self = this;
            
            self.startTime(value().startTime ? value().startTime : null);
            self.endTime(value().endTime ? value().endTime : null);
        }

        /**
         * validTimeRange
         */
        private validTimeRange(startTime: string, endTime: string): boolean {
            let self = this;
            let startVal: any = nts.uk.time.parseTime(startTime).toValue();
            let endVal: any = nts.uk.time.parseTime(endTime).toValue();
            
            $('#time-range-editor').ntsError('clear');
            if (startVal >= endVal) {
                $('#time-range-editor').ntsError('set', '期間入力フォームの開始と終了が逆転しています');
                return false;
            }
            return true
        }
    }
    
    /**
     * TimePeriod
     */
    interface TimePeriod {
        startTime: string;
        endTime: string;
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
