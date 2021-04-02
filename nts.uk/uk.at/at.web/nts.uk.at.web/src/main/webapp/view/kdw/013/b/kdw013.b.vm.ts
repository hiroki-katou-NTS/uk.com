module nts.uk.ui.at.kdp013.b {
    const COMPONENT_NAME = 'kdp013b';

    const { getTimeOfDate, number2String } = share;

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
                    <button data-bind="click: $component.params.remove, icon: 203, size: 12"></button>
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
                        <td data-bind="html: pair.value"></td>
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
        </style>
        `
    })
    export class ViewModel extends ko.ViewModel {
        dataSources: KnockoutObservableArray<KeyValue> = ko.observableArray([]);

        constructor(public params: Params) {
            super();
        }

        mounted() {
            const vm = this;
            const { params } = vm;
            const { data } = params;

            ko.computed({
                read: () => {
                    const model: KeyValue[] = [];
                    const event = ko.unwrap(data);

                    if (event) {
                        const { extendedProps, start, end } = event;
                        const { descriptions } = extendedProps;

                        const startTime = getTimeOfDate(start);
                        const endTime = getTimeOfDate(end);

                        //
                        model.push({ key: 'KDW013_27', value: `${number2String(startTime)}${vm.$i18n('KDW013_30')}${number2String(endTime)}` });
                        model.push({ key: 'KDW013_25', value: number2String(endTime - startTime) });

                        model.push({ key: 'C1_10', value: '' });
                        model.push({ key: 'C1_13', value: '' });
                        model.push({ key: 'C1_16', value: '' });
                        model.push({ key: 'C1_19', value: '' });
                        model.push({ key: 'C1_22', value: '' });

                        model.push({ key: 'KDW013_28', value: '' });
                        model.push({ key: 'KDW013_29', value: descriptions });
                    } else {
                        model.push({ key: 'KDW013_27', value: '' });
                        model.push({ key: 'KDW013_25', value: '' });

                        model.push({ key: 'C1_10', value: '' });
                        model.push({ key: 'C1_13', value: '' });
                        model.push({ key: 'C1_16', value: '' });
                        model.push({ key: 'C1_19', value: '' });
                        model.push({ key: 'C1_22', value: '' });

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
    }
}