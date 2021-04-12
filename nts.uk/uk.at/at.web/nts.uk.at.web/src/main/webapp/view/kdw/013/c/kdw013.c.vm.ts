module nts.uk.ui.at.kdp013.c {
    const COMPONENT_NAME = 'kdp013c';

    const style = `.edit-event {
        width: 350px;
    }
    .edit-event .header {
        box-sizing: border-box;
        position: relative;
        padding-bottom: 5px;
        line-height: 35px;
        margin-top: -5px;
    }
    .edit-event .header .actions {
        position: absolute;
        top: 0px;
        right: -5px;
    }
    .edit-event .header .actions button {
        margin: 0;
        padding: 0;
        box-shadow: none;
        border: none;
        border-radius: 50%;
        width: 30px;
    }
    .edit-event>table {
        width: 100%;
    }
    .edit-event>table>tbody>tr>td:first-child {
        vertical-align: top;
        padding-top: 6px;
    }
    .edit-event>table>tbody>tr.functional>td {
        text-align: center;
    }
    .edit-event>table>tbody>tr>td>.ntsControl {
        width: 100%;
        display: block;
        box-sizing: border-box;
        margin-bottom: 10px;
    }
    .edit-event>table>tbody>tr>td>.ntsControl>input {
        width: 100%;
        box-sizing: border-box;
    }
    .edit-event>table>tbody>tr>td>.ntsControl>textarea {
        width: 100%;
        height: 80px;
        display: block;
        box-sizing: border-box;
    }
    .edit-event .time-range-control input.nts-input {
        width: 60px;
        text-align: center;
        padding: 5px 3px;
    }
    .edit-event .time-range-control input.nts-input+span {
        margin-left: 7px;
        margin-right: 7px;
    }
    .edit-event .time-range-control .message {
        display: none;
        color: #ff6666;
        font-size: 12px;
        padding-top: 3px;
    }
    .edit-event .time-range-control.error .message {
        display: block;
    }
    .edit-event .time-range-control.error input.nts-input {
        border: 1px solid #ff6666 !important;
    }
    .edit-event .time-range-control:not(.error) input.nts-input {
        border: 1px solid #999 !important;
    }`;

    const { randomId } = nts.uk.util;
    const { number2String, string2Number, validateNumb, getTimeOfDate, setTimeOfDate } = share;

    type TimeRange = {
        start: number | null;
        end: number | null;
    };

    type EventModel = {
        timeRange: KnockoutObservable<TimeRange>;
        descriptions: KnockoutObservable<string>;
    }

    const defaultModelValue = (): EventModel => ({
        descriptions: ko.observable(''),
        timeRange: ko.observable({
            start: null,
            end: null
        })
    });

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class BindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const params = valueAccessor();

            ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

            return { controlsDescendantBindings: true };
        }

    }

    @component({
        name: COMPONENT_NAME,
        template: `
        <div class="edit-event">
            <div class="header">
                <div data-bind="i18n: 'KDW013_26'"></div>
                <div class="actions">
                    <button class="close" tabindex="-1" data-bind="click: $component.params.close, icon: 202, size: 12"></button>
                </div>
            </div>
            <table>
                <colgroup>
                    <col width="80px" />
                </colgroup>
                <tbody>
                    <tr>
                        <td data-bind="i18n: 'KDW013_27'"></td>
                        <td>
                            <div data-bind="
                                    kdw-timerange: $component.model.timeRange,
                                    update: $component.params.update,
                                    hasError: $component.hasError
                                "></div>
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_10'"></td>
                        <td><div data-bind="dropdown: ko.observable(''), items: $component.items"></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_13'"></td>
                        <td><div data-bind="dropdown: ko.observable(''), items: $component.items"></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_16'"></td>
                        <td><div data-bind="dropdown: ko.observable(''), items: $component.items"></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_19'"></td>
                        <td><div data-bind="dropdown: ko.observable(''), items: $component.items"></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_22'"></td>
                        <td><div data-bind="dropdown: ko.observable(''), items: $component.items"></div></td>
                    </tr>
                    <tr class="workplace">
                        <td data-bind="i18n: 'KDW013_28'"></td>
                        <td>
                            <input type="text" data-bind="ntsTextEditor: { value: ko.observable('') }" />
                        </td>
                    </tr>
                    <tr class="note">
                        <td data-bind="i18n: 'KDW013_29'"></td>
                        <td>
                            <textarea data-bind="ntsMultilineEditor: { value: $component.model.descriptions }" />
                        </td>
                    </tr>
                    <tr class="functional">
                        <td colspan="2">
                            <button class="proceed" data-bind="i18n: 'KDW013_43', click: $component.save, disable: $component.hasError"></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        `
    })
    export class ViewModel extends ko.ViewModel {
        hasError: KnockoutObservable<boolean> = ko.observable(false);

        model: EventModel = defaultModelValue();

        items: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(public params: Params) {
            super();

            const items = _.range(0, 10).map((v: number) => ({ selected: false, id: randomId(), name: `Option ${v}`, code: `0000${v}` }));

            this.items(items);
        }

        mounted() {
            const vm = this;
            const { $el, params, model, hasError } = vm;
            const { view, position, data } = params;
            const cache = {
                view: ko.unwrap(view),
                position: ko.unwrap(position)
            };
            const subscribe = (event: FullCalendar.EventApi | null) => {
                if (event) {
                    const { extendedProps, start, end } = event;
                    const { descriptions } = extendedProps;
                    const startTime = getTimeOfDate(start);
                    const endTime = getTimeOfDate(end);

                    model.descriptions(descriptions);

                    model.timeRange({ start: startTime, end: endTime });
                } else {
                    model.descriptions('');
                    model.timeRange({ start: null, end: null });
                }

                // clear error
                hasError(false);
            };

            data.subscribe(subscribe);

            subscribe(data());

            // focus to first input element
            ko.computed({
                read: () => {
                    const _v = ko.unwrap(view);

                    if (_v === 'edit' && cache.view !== _v) {
                        $($el).find('input:first').focus();
                    }

                    cache.view = _v;
                },
                disposeWhenNodeIsRemoved: $el
            });

            position
                .subscribe((p: any) => {
                    if (!p) {
                        cache.view = 'view';
                    }

                    if (p && cache.position !== p) {
                        $($el).find('input:first').focus();
                    }

                    cache.position = p;
                });

            const $ctn = $($el);

            $ctn
                // prevent tabable to out of popup control
                .on("keydown", ":tabbable", (evt: JQueryKeyEventObject) => {
                    const fable = $ctn.find(":tabbable:not(.close)").toArray();

                    const last = _.last(fable);
                    const first = _.first(fable);

                    if (evt.keyCode === 9) {
                        if ($(evt.target).is(last) && evt.shiftKey === false) {
                            first.focus();

                            evt.preventDefault();
                        } else if ($(evt.target).is(first) && evt.shiftKey === true) {
                            last.focus();

                            evt.preventDefault();
                        }
                    }
                });

            if (!$(`style#${COMPONENT_NAME}`).length) {
                $('<style>', { id: COMPONENT_NAME, html: style }).appendTo('head');
            }
        }

        save() {
            const vm = this;
            const { params, model } = vm;
            const { data } = params;
            const event = data();
            const { timeRange, descriptions } = model;

            if (event) {
                const { start } = event;
                const tr = ko.unwrap(timeRange);

                event.setStart(setTimeOfDate(start, tr.start));
                event.setEnd(setTimeOfDate(start, tr.end));

                event.setExtendedProp('id', randomId());
                event.setExtendedProp('status', 'update');
                event.setExtendedProp('descriptions', descriptions());
            }

            // close popup
            params.close();
        }
    }

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



    type Params = {
        close: () => void;
        remove: () => void;
        mode: KnockoutObservable<boolean>;
        view: KnockoutObservable<'view' | 'edit'>;
        data: KnockoutObservable<FullCalendar.EventApi>;
        position: KnockoutObservable<null | any>;
    }
}