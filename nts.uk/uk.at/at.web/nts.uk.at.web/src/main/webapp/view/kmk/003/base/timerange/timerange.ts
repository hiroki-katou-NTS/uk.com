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
        startTimeNameId: string;
        endTimeNameId: string;
        startInputId: string;
        endInputId: string;
        elementId: string;
        paramId: string;
        
        /**
        * Constructor.
        */
        constructor(input: any) {
            let self = this;
            
            self.value = input.value;
            self.startConstraint = ko.unwrap(input.startConstraint);
            self.endConstraint = ko.unwrap(input.endConstraint);
            self.required = ko.unwrap(input.required);
            self.enable = ko.observable(input.enable ? input.enable() : true);
            self.inputFormat = ko.unwrap(input.inputFormat);
            self.tabindex = ko.unwrap(input.tabindex);
            self.elementId = nts.uk.util.randomId();
            
            self.startTime = ko.observable(input.value().startTime ? input.value().startTime : 0);
            self.endTime = ko.observable(input.value().endTime ? input.value().endTime : 0);
            self.startTimeNameId = input.startTimeNameId ? input.startTimeNameId : '';
            self.endTimeNameId = input.endTimeNameId ? input.endTimeNameId :'';
            self.startInputId = nts.uk.util.randomId();
            self.endInputId = nts.uk.util.randomId();
            self.paramId = input.paramId ? input.paramId : 'Need paramId for this message';
            // subscribe
            self.startTime.subscribe((newValue) => {
                if (!self.validTimeRange($('#' + self.elementId))) {
                    return;
                }
                self.value().startTime = newValue;
                self.value().endTime = self.endTime();
                
                // event callback
                self.value.valueHasMutated();
                self.fireEventChangeData();
            });
            self.endTime.subscribe((newValue) => {
                if (!self.validTimeRange($('#' + self.elementId))) {
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
        public validTimeRange(element: JQuery, isGlobalValidate?: boolean): boolean {
            if (nts.uk.util.isNullOrEmpty(element)) {
                return;
            }
            var self = this;
            var elementId = element.attr('id');
            var startInputId = element.data('start');
            var endInputId = element.data('end');
            var startTime = isGlobalValidate ? self.convertTimeInput(startInputId) : self.startTime();
            var endTime = isGlobalValidate ? self.convertTimeInput(endInputId) : self.endTime();
            var paramId = element.data('param');
            if($('#' + startInputId).prop('disabled') && $('#' + endInputId).prop('disabled')) {
                return true;
            }
            
            // clear error
            $('#' + startInputId).ntsError('clear');
            $('#' + endInputId).ntsError('clear');
            $('#' + elementId).ntsError('clear');
            
            // validate
            $('#' + startInputId).ntsEditor('validate');
            $('#' + endInputId).ntsEditor('validate');
            
            if ($('#' + startInputId).ntsError('hasError') || $('#' + endInputId).ntsError('hasError')) {
                return false;
            }
            if (startTime >= endTime) {
                $('#' + elementId).ntsError('set', {messageId:'Msg_770',messageParams:[nts.uk.resource.getText(paramId)]});
                if (!$('#' + startInputId).parent().hasClass('error')) {
                    _.defer(() =>$('#' + startInputId).parent().addClass('error'));
                }
                if (!$('#' + endInputId).parent().hasClass('error')) {
                    _.defer(() =>$('#' + endInputId).parent().addClass('error'));
                }
                return false;
            }
            return true;
        }
        
        private convertTimeInput(id: string): number {
            if (!$('#' + id).ntsError('hasError')) {
                var time: string = $('#' + id).val();
                var timeArray = time.split(":");
                var hours = parseInt(timeArray[0]) >= 0 ? parseInt(timeArray[0])
                    : - (24 + parseInt(timeArray[0]));
                
                return hours * 60 + parseInt(timeArray[1]);
            }
            return 0;
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
                input.enable.subscribe(function(val: any) {
                    screenModel.enable(val);
                });
            }
            $(element).load(webserviceLocator, function() {
                screenModel.element.find('#start-time').attr('id', screenModel.startInputId);
                screenModel.element.find('#end-time').attr('id', screenModel.endInputId);
                ko.cleanNode($(element)[0]);
                //screenModel.bindDataToScreen(value);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                
                $(element).data("screenModel", screenModel);
                $('.time-range-custom').css("float", "left");
                
                $.fn.validateTimeRange = function() {
                    screenModel.validTimeRange($(this), true);
                }
            });
        }

    }
    ko.bindingHandlers['ntsTimeRangeEditor'] = new TimeRangeBindingHandler();
}
interface JQuery {
        validateTimeRange(): void;
}
