module nts.uk.ui.at.kdp013.share {
    const { number2String, string2Number, validateNumb } = share;

    @handler({
        bindingName: 'input-time-raw'
    })
    export class InputTimeRawBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLInputElement, valueAccessor: () => KnockoutObservable<number | null>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } => {
            if (element.tagName !== 'INPUT') {
                element.innerHTML = 'Use this binding only for [input] tag.';

                return { controlsDescendantBindings: true };
            }

            const $el = $(element);
            const value = valueAccessor();

            // trigger value for rebind view
            const subscribe = (curent: number | null) => {
                const old = ko.unwrap(value);

                if (old !== curent) {
                    value(curent);
                } else {
                    value.valueHasMutated();
                }
            };
            const valueChange = () => {
                const { value } = element;

                if (_.isEmpty(value)) {
                    subscribe(null);

                    return;
                }

                const numb = string2Number(value);

                subscribe(numb);
            };

            // rebind value from model to input (view)
            ko.computed({
                read: () => {
                    const v = ko.unwrap(value);

                    if (validateNumb(v)) {
                        $el.val(number2String(v));
                    }
                },
                disposeWhenNodeIsRemoved: element
            });

            $el
                // convert or trigger value from string to number
                .on('blur', valueChange)
                .on('keydown', (evt: JQueryEventObject) => {
                    if (evt.keyCode === 13) {
                        valueChange();
                    }
                })
                .addClass('nts-input');

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'input-time'
    })
    export class InputTimeBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLInputElement, valueAccessor: () => KnockoutObservable<number | null>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } => {
            if (element.tagName !== 'INPUT') {
                element.innerHTML = 'Use this binding only for [input] tag.';

                return { controlsDescendantBindings: true };
            }

            // two step binding for keep subscribe on real model
            const value = valueAccessor();
            const binding = ko.observable(null);
            const subscribe = (c: number | null) => {
                if (c !== binding()) {
                    binding(c);
                }
            };

            value
                .subscribe(subscribe);

            subscribe(value());

            binding
                .subscribe((c: number) => {
                    if (validateNumb(c)) {
                        if (c !== value()) {
                            value(c);
                        }

                        element.style.border = '';
                    } else {
                        element.style.border = '1px solid #ff6666';
                    }
                });

            ko.applyBindingsToNode(element, { 'input-time-raw': binding }, bindingContext);

            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }
}