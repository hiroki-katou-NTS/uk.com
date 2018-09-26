module nts.uk.ui.koExtentions {
    const originalClick = ko.bindingHandlers.click;

    // override click binding with timeClick (default: 500)
    class SafeClickBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let lastPreventTime: number = new Date().getTime(),
                originalFunction = valueAccessor(),
                newValueAccesssor = function() {
                    return function() {
                        let currentPreventTime: number = new Date().getTime(),
                            time: number = currentPreventTime - lastPreventTime,
                            timeClick: number | undefined = ko.toJS(allBindingsAccessor().timeClick),
                            _timeClick = _.isNumber(timeClick) ? timeClick : 500;

                        if (time > _timeClick) {
                            //pass through the arguments
                            originalFunction.apply(viewModel, arguments);
                        }

                        lastPreventTime = new Date().getTime();
                    }
                };

            // call originalClick init
            originalClick.init(element, newValueAccesssor, allBindingsAccessor, viewModel, bindingContext);
        }
    }

    ko.bindingHandlers['click'] = new SafeClickBindingHandler();
}