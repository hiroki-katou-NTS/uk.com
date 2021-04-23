module nts.uk.ui.at.kdp013.c {
    const COMPONENT_NAME = 'kdp013c';
    import calendar = nts.uk.ui.components.fullcalendar;

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
    .edit-event .nts-dropdown .message,
    .edit-event .nts-description .message,
    .edit-event .time-range-control .message {
        display: none;
        color: #ff6666;
        font-size: 12px;
        padding-top: 3px;
    }
    .edit-event .nts-dropdown.error .message,
    .edit-event .nts-description.error .message,
    .edit-event .time-range-control.error .message {
        display: block;
    }
    .edit-event .nts-description.error textarea.nts-input,
    .edit-event .time-range-control.error input.nts-input {
        border: 1px solid #ff6666 !important;
    }
    .edit-event .nts-description:not(.error) textarea.nts-input,
    .edit-event .time-range-control:not(.error) input.nts-input {
        border: 1px solid #999 !important;
    }`;

    const { randomId } = nts.uk.util;
    const { number2String, string2Number, validateNumb, getTimeOfDate, setTimeOfDate } = share;

    type API = {
        readonly START: string;
        readonly SELECT: string;
    };

    const API: API = {
        START: '/screen/at/kdw013/c/start',
        SELECT: '/screen/at/kdw013/c/select'
    };

    type EventModel = {
        timeRange: KnockoutObservable<share.TimeRange>;

        task1: KnockoutObservable<string>;
        task2: KnockoutObservable<string>;
        task3: KnockoutObservable<string>;
        task4: KnockoutObservable<string>;
        task5: KnockoutObservable<string>;

        workplace: KnockoutObservable<string>;
        descriptions: KnockoutObservable<string>;
    }

    type TaskDto = {
        code: string;
        taskFrameNo: number | null;
        childTaskList: string[];
        expirationStartDate: string;
        expirationEndDate: string;
        displayInfo: TaskDisplayInfoDto;
        cooperationInfo: ExternalCooperationInfoDto;
    };

    type ExternalCooperationInfoDto = {
        /**
         * 外部コード1
         */
        externalCode1: string;

        /**
         * 外部コード2
         */
        externalCode2: string;

        /**
         * 外部コード3
         */
        externalCode3: string;

        /**
         * 外部コード4
         */
        externalCode4: string;

        /**
         * 外部コード5
         */
        externalCode5: string;
    };

    type TaskDisplayInfoDto = {
        /**
         * 名称
         */
        taskName: string;

        /**
         * 略名
         */
        taskAbName: string;

        /**
         * 作業色
         */
        color: string;

        /**
         * 備考
         */
        taskNote: string;

    };

    type StartWorkInputParam = {
        // 社員ID
        sId: string;

        // 基準日
        refDate: string;

        // 作業グループ
        workGroupDto: WorkGroupDto;
    }

    type WorkGroupDto = {
        /** 作業CD1 */
        workCD1: string;

        /** 作業CD2 */
        workCD2: string;

        /** 作業CD3 */
        workCD3: string;

        /** 作業CD4 */
        workCD4: string;

        /** 作業CD5 */
        workCD5: string;
    };

    type StartWorkInputPanelDto = {
        /** 利用可能作業1リスト */
        taskListDto1: TaskDto[];

        /** 利用可能作業2リスト */
        taskListDto2: TaskDto[];

        /** 利用可能作業3リスト */
        taskListDto3: TaskDto[];

        /** 利用可能作業4リスト */
        taskListDto4: TaskDto[];

        /** 利用可能作業5リスト */
        taskListDto5: TaskDto[];
    }

    const defaultModelValue = (): EventModel => ({
        descriptions: ko.observable(''),
        timeRange: ko.observable({
            start: null,
            end: null
        }),
        workplace: ko.observable(''),
        task1: ko.observable(''),
        task2: ko.observable(''),
        task3: ko.observable(''),
        task4: ko.observable(''),
        task5: ko.observable('')
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
                                    hasError: $component.errors.time,
                                    exclude-times: $component.params.excludeTimes
                                "></div>
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_10'"></td>
                        <td><div data-bind="
                                dropdown: $component.model.task1,
                                items: $component.items,
                                required: true,
                                name: 'GET_FROM_DOMAIN',
                                hasError: $component.errors.dropdown
                            "></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_13'"></td>
                        <td><div data-bind="
                                dropdown: $component.model.task2,
                                items: $component.items
                            "></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_16'"></td>
                        <td><div data-bind="
                                dropdown: $component.model.task3,
                                items: $component.items
                            "></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_19'"></td>
                        <td><div data-bind="
                                dropdown: $component.model.task4,
                                items: $component.items
                            "></div></td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_22'"></td>
                        <td><div data-bind="
                                dropdown: $component.model.task5,
                                items: $component.items
                            "></div></td>
                    </tr>
                    <tr class="workplace">
                        <td data-bind="i18n: 'KDW013_28'"></td>
                        <td><div data-bind="
                                dropdown: $component.model.workplace,
                                items: $component.items,
                                required: true,
                                name: 'WORKPLACE',
                                hasError: $component.errors.workplace
                            "></div></td>
                    </tr>
                    <tr class="note">
                        <td data-bind="i18n: 'KDW013_29'"></td>
                        <td>
                            <div data-bind="
                                    description: $component.model.descriptions,
                                    name: 'KDW013_29', 
                                    constraint: '',
                                    hasError: $component.errors.description
                                "></div>
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
        hasError!: KnockoutComputed<boolean>;

        errors: {
            time: KnockoutObservable<boolean>;
            dropdown: KnockoutObservable<boolean>;
            description: KnockoutObservable<boolean>;
            workplace: KnockoutObservable<boolean>;
        } = {
                time: ko.observable(false),
                dropdown: ko.observable(false),
                workplace: ko.observable(false),
                description: ko.observable(false)
            };

        model: EventModel = defaultModelValue();

        items: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(public params: Params) {
            super();

            const items = _.range(0, 10).map((v: number) => ({ selected: false, id: randomId(), name: `Option ${v}`, code: `0000${v}` }));

            this.items(items);

            this.hasError = ko.computed({
                read: () => {
                    const errors = ko.toJS(this.errors);

                    return !!errors.time || !!errors.description || !!errors.dropdown || !!errors.workplace;
                },
                write: (value: boolean) => {
                    this.errors.time(value);
                    this.errors.dropdown(value);
                    this.errors.workplace(value);
                    this.errors.description(value);
                }
            });

            this.model.timeRange.subscribe(({ start, end }) => { console.log(start, end) });
        }

        mounted() {
            const vm = this;
            const { $el, params, model, hasError } = vm;
            const { view, position, data, excludeTimes } = params;
            const cache = {
                view: ko.unwrap(view),
                position: ko.unwrap(position)
            };
            const subscribe = (event: FullCalendar.EventApi | null) => {
                $.Deferred()
                    .resolve(true)
                    .then(() => {
                        if (event) {
                            const { extendedProps, start, end } = event as any as calendar.EventRaw;
                            const { descriptions, employeeId } = extendedProps;
                            const startTime = getTimeOfDate(start);
                            const endTime = getTimeOfDate(end);

                            model.descriptions(descriptions);

                            model.timeRange({ start: startTime, end: endTime });

                            const params: StartWorkInputParam = {
                                refDate: moment(start).toISOString(),
                                sId: employeeId || vm.$user.employeeId,
                                workGroupDto: {
                                    workCD1: '',
                                    workCD2: '',
                                    workCD3: '',
                                    workCD4: '',
                                    workCD5: ''
                                }
                            };

                            return params;
                        } else {
                            model.descriptions('');
                            model.timeRange({ start: null, end: null });
                        }

                        return null;
                    })
                    .then((params: StartWorkInputParam | null) => !!params ? vm.$ajax('at', API.START, params) : null)
                    .then((response: StartWorkInputPanelDto | null) => {
                        if (response) {
                            const { taskListDto1, taskListDto2, taskListDto3, taskListDto4, taskListDto5 } = response;
                        }

                        return true;
                    })
                    // clear error
                    .then(() => hasError(false));
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

            $.Deferred()
                .resolve(true)
                // validate control
                .then(() => $(vm.$el).find('input, textarea').trigger('blur'))
                .then(() => vm.hasError())
                .then((invalid: boolean) => {
                    if (!invalid) {
                        if (event) {
                            const { start } = event;
                            const tr = ko.unwrap(timeRange);

                            event.setStart(setTimeOfDate(start, tr.start));
                            event.setEnd(setTimeOfDate(start, tr.end));

                            const { status } = event.extendedProps;

                            if (['new', 'add'].indexOf(status) === -1) {
                                event.setExtendedProp('status', 'add');
                            } else {
                                event.setExtendedProp('status', 'update');
                            }
                            event.setExtendedProp('descriptions', descriptions());
                        }

                        // close popup
                        params.close();
                    }
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
        excludeTimes: KnockoutObservableArray<share.BussinessTime>;
    }
}