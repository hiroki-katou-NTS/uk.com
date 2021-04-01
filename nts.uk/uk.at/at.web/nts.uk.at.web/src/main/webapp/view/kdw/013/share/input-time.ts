module nts.uk.ui.at.kdp013.share {
    const { number2String, string2Number, validateNumb } = share;

    @handler({
        bindingName: 'input-time'
    })
    export class InputTimeBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLInputElement, valueAccessor: () => KnockoutObservable<number | null>, allBindingsAccessor: KnockoutAllBindingsAccessor): { controlsDescendantBindings: boolean; } => {
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

                if (validateNumb(numb)) {
                    subscribe(numb);

                    $el.css({ 'border': '' });
                } else {
                    $el.css({ 'border': '1px solid #ff6666' });
                }
            };

            // rebind value from model to input (view)
            ko.computed({
                read: () => {
                    const v = ko.unwrap(value);

                    if (!validateNumb(v)) {
                        $el.val('');
                    } else {
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
                });

            $el.removeAttr('data-bind');

            return { controlsDescendantBindings: true };
        }
    }
}