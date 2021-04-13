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

    export type TimeRange = {
        start: number | null;
        end: number | null;
    };

    @handler({
        bindingName: 'kdw-timerange',
        validatable: true,
        virtual: false
    })
    export class KDW013TimeRangeBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: () => KnockoutObservable<TimeRange>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) => {
            const $start = document.createElement('input');
            const $end = document.createElement('input');
            const $space = document.createElement('span');
            const $wtime = document.createElement('span');
            const $value = document.createElement('span');
            const $error = document.createElement('div');
            const update: () => void = allBindingsAccessor.get('update');
            const hasError: KnockoutObservable<boolean> = allBindingsAccessor.get('hasError');
            const value = valueAccessor();

            const errorId = ko.observable('');
            const errorParams = ko.observableArray(['']);
            const startTime: KnockoutObservable<number | null> = ko.observable(null);
            const endTime: KnockoutObservable<number | null> = ko.observable(null);
            const range: KnockoutComputed<string> = ko.computed({
                read: () => {
                    const start = ko.unwrap(startTime);
                    const end = ko.unwrap(endTime);

                    if (_.isNil(start)) {
                        return '';
                    }

                    if (_.isNil(end)) {
                        return '';
                    }

                    if (start > end) {
                        return '';
                    }

                    return number2String(end - start);
                },
                disposeWhenNodeIsRemoved: element
            });
            const subscribe = (v: TimeRange) => {
                const { start, end } = v;

                if (start !== startTime()) {
                    startTime(start);
                }

                if (end !== endTime()) {
                    endTime(end);
                }
            };

            $start.type = 'text';
            $end.type = 'text';

            $error.classList.add('message');

            value
                .subscribe(subscribe);

            //  update for first binding
            subscribe(value());

            $(element)
                .append($start)
                .append($space)
                .append($end)
                .append($wtime)
                .append($value)
                .append($error);

            ko.applyBindingsToNode($space, { i18n: 'KDW013_30' }, bindingContext);
            ko.applyBindingsToNode($wtime, { i18n: 'KDW013_25' }, bindingContext);

            ko.applyBindingsToNode($value, { text: range }, bindingContext);

            ko.applyBindingsToNode($start, { 'input-time-raw': startTime }, bindingContext);
            ko.applyBindingsToNode($end, { 'input-time-raw': endTime }, bindingContext);

            ko.applyBindingsToNode($error, {
                text: ko.computed({
                    read: () => {
                        const id = ko.unwrap(errorId);
                        const params = ko.unwrap(errorParams);

                        viewModel.$nextTick(update);

                        if (!id) {
                            element.classList.remove('error');

                            if (ko.isObservable(hasError)) {
                                hasError(false);
                            }

                            return '';
                        }

                        element.classList.add('error');

                        if (ko.isObservable(hasError)) {
                            hasError(true);
                        }

                        return viewModel.$i18n.message(id, params);
                    },
                    disposeWhenNodeIsRemoved: element
                })
            }, bindingContext);

            element.classList.add('ntsControl');
            element.classList.add('time-range-control');

            $start.classList.add('nts-input');
            $end.classList.add('nts-input');

            const validateRange = (start: number | null, end: number | null) => {
                // validate required
                if (!validateNumb(start) || !validateNumb(end)) {
                    errorParams(['TIME_RANGE']);

                    errorId('MsgB_1');

                    return;
                }

                // validate outofrange at here

                // validate time range small than slotDuration

                // validate revert value
                if (start > end) {
                    errorId('Msg_1811');

                    return;
                }

                // clear error if all validate is valid
                errorId('');

                // binding value to model
                value({ start, end });
            };

            startTime.subscribe((s: number | null) => {
                const e: number | null = ko.unwrap(endTime);

                validateRange(s, e);
            });

            endTime.subscribe((e: number | null) => {
                const s: number | null = ko.unwrap(startTime);

                validateRange(s, e);
            });
        }
    }
}