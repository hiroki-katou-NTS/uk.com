
module nts.uk.ui.components.fullcalendar {
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
            const min: KnockoutObservable<number> = allBindingsAccessor.get('min');
            const max: KnockoutObservable<number> = allBindingsAccessor.get('max');
            const slotDuration: KnockoutObservable<number> = allBindingsAccessor.get('slotDuration');

            // validate (model) value range
            const validateNumb = (value: number) => {
                return _.isNumber(value) && 0 <= value && value <= 1440;
            };
            // convert value from model to view
            const number2String = (value: number) => {
                const hour = Math.floor(value / 60);
                const minute = Math.floor(value % 60);

                return `${hour}:${_.padStart(`${minute}`, 2, '0')}`;
            };
            // convert value from view to model
            const string2Number = (value: string) => {
                if (value === '') {
                    return -1;
                }

                const numb = Number(value.replace(/:/, ''));

                if (_.isNaN(numb)) {
                    return -1;
                }

                return Math.floor(numb / 100) * 60 + Math.floor(numb % 100);
            };
            // trigger value for rebind view
            const subscribe = (curent: number) => {
                const old = ko.unwrap(value);

                if (old !== curent) {
                    value(curent);
                } else {
                    value.valueHasMutated();
                }
            };
            const valueChange = () => {
                const numb = string2Number(element.value);

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
                        $el.val('0:00');
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