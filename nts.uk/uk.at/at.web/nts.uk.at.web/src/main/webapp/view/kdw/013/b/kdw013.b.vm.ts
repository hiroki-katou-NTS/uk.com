module nts.uk.ui.at.kdp013.b {
    const COMPONENT_NAME = 'kdp013b';

    const { getTimeOfDate, number2String } = share;

    const API_REMOVE = '/screen/at/kdw013/delete';

    @handler({
        bindingName: 'content',
        validatable: true,
        virtual: false
    })
    export class ContentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => KeyValue) {
            const { key, value } = valueAccessor();

            if (key !== 'KDW013_29') {
                $(element).text(value);
            } else {
                $(element).append($('<div>', { text: value }));
            }
        }
    }

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
        <div class="detail-event">
            <div class="header">
                <div data-bind="i18n: 'KDW013_26'"></div>
                <div class="actions">
                    <!-- ko if: $component.params.mode -->
                    <button data-bind="click: $component.params.update, icon: 204, size: 12"></button>
                    <button data-bind="click: $component.remove, icon: 203, size: 12"></button>
                    <!-- /ko -->
                    <button data-bind="click: $component.params.close, icon: 202, size: 12"></button>
                </div>
            </div>
            <table>
                <colgroup>
                    <col width="80px" />
                </colgroup>
                <tbody data-bind="foreach: { data: $component.dataSources, as: 'pair' }">
                    <tr>
                        <td data-bind="i18n: pair.key"></td>
                        <td data-bind="content: pair"></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <style>
            .detail-event {
                width: 300px;
            }
            .detail-event .header {
                box-sizing: border-box;
                position: relative;
                padding-bottom: 5px;
                line-height: 35px;
                margin-top: -5px;
            }
            .detail-event .header .actions {
                position: absolute;
                top: 0px;
                right: -5px;
            }
            .detail-event .header .actions button {
                margin: 0;
                padding: 0;
                box-shadow: none;
                border: none;
                border-radius: 50%;
                width: 30px;
            }
            .detail-event table {
                width: 100%;
            }
            .detail-event table tr {
                height: 34px;
            }
            .detail-event table tr>td:first-child {
                vertical-align: top;
                padding-top: 6px;
            }
            .detail-event table tr>td>div {
                max-height: 120px;
                overflow-y: auto;
            }
        </style>
        `
    })
    export class ViewModel extends ko.ViewModel {
        dataSources: KnockoutObservableArray<KeyValue> = ko.observableArray([]);

        taskFrameSettings: KnockoutObservableArray<a.TaskFrameSettingDto> = ko.observableArray([]);

        constructor(public params: Params) {
            super();

            const vm = this;
            const { $settings } = params;

            ko.computed({
                read: () => {
                    const settings = ko.unwrap($settings);


                    if (settings) {
                        const { startManHourInputResultDto } = settings;

                        const { taskFrameUsageSetting } = startManHourInputResultDto;
                        const { frameSettingList } = taskFrameUsageSetting;

                        vm.taskFrameSettings(frameSettingList);;
                    }
                }
            });
        }

        mounted() {
            const vm = this;
            const { params, taskFrameSettings } = vm;
            const { data } = params;

            ko.computed({
                read: () => {
                    const model: KeyValue[] = [];
                    const event = ko.unwrap(data);
                    const settings = ko.unwrap(taskFrameSettings);

                    const [first, second, thirt, four, five] = settings;

                    if (event) {
                        const { extendedProps, start, end } = event;
                        const { descriptions } = extendedProps;

                        const startTime = getTimeOfDate(start);
                        const endTime = getTimeOfDate(end);

                        //
                        model.push({ key: 'KDW013_27', value: `${number2String(startTime)}${vm.$i18n('KDW013_30')}${number2String(endTime)}` });
                        model.push({ key: 'KDW013_25', value: number2String(endTime - startTime) });

                        if (first && first.useAtr === 1) {
                            model.push({ key: first.frameName, value: '' });
                        }

                        if (second && second.useAtr === 1) {
                            model.push({ key: second.frameName, value: '' });
                        }

                        if (thirt && thirt.useAtr === 1) {
                            model.push({ key: thirt.frameName, value: '' });
                        }

                        if (four && four.useAtr === 1) {
                            model.push({ key: four.frameName, value: '' });
                        }

                        if (five && five.useAtr === 1) {
                            model.push({ key: five.frameName, value: '' });
                        }

                        model.push({ key: 'KDW013_28', value: '' });
                        model.push({ key: 'KDW013_29', value: descriptions });
                    } else {
                        model.push({ key: 'KDW013_27', value: '' });
                        model.push({ key: 'KDW013_25', value: '' });

                        if (first && first.useAtr === 1) {
                            model.push({ key: first.frameName, value: '' });
                        }

                        if (second && second.useAtr === 1) {
                            model.push({ key: second.frameName, value: '' });
                        }

                        if (thirt && thirt.useAtr === 1) {
                            model.push({ key: thirt.frameName, value: '' });
                        }

                        if (four && four.useAtr === 1) {
                            model.push({ key: four.frameName, value: '' });
                        }

                        if (five && five.useAtr === 1) {
                            model.push({ key: five.frameName, value: '' });
                        }

                        model.push({ key: 'KDW013_28', value: '' });
                        model.push({ key: 'KDW013_29', value: '' });
                    }

                    vm.dataSources(model);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            $(vm.$el)
                .removeAttr('data-bind')
                .find('[data-bind]')
                .removeAttr('data-bind');
        }

        remove() {
            const vm = this;
            const { data } = vm.params;
            const { extendedProps } = ko.unwrap(data);
            const { employeeId, confirmerId, date } = extendedProps;
            const params = { employeeId, date, confirmerId };

            vm
                .$ajax('at', API_REMOVE, params)
                .then(() => {
                    vm.params.remove();
                });
        }
    }

    type KeyValue = {
        key: string;
        value: string;
    }

    type Params = {
        close: () => void;
        update: () => void;
        remove: () => void;
        mode: KnockoutObservable<boolean>;
        data: KnockoutObservable<FullCalendar.EventApi>;
        $settings: KnockoutObservable<a.StartProcessDto | null>;
    }
}