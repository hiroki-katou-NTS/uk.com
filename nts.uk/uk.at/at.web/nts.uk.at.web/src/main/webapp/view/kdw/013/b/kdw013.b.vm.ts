module nts.uk.ui.at.kdp013.b {
    const COMPONENT_NAME = 'kdp013b';


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
            <ul data-bind="foreach: { data: $component.dataSources, as: 'pair' }">
                <li>
                    <div data-bind="i18n: pair.key"></div>
                    <div data-bind="text: pair.value"></div>
                </li>
            </ul>
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
        </style>
        `
    })
    export class ViewModel extends ko.ViewModel {
        dataSources: KnockoutObservableArray<KeyValue> = ko.observableArray([]);

        constructor(public params: Params) {
            super();

            const model: KeyValue[] = [];

            model.push({ key: 'KDW013_27', value: '' });
            model.push({ key: 'KDW013_27', value: '' });
            model.push({ key: 'KDW013_27', value: '' });
            model.push({ key: 'KDW013_27', value: '' });

            this.dataSources(model);
        }


        mounted() {
            const vm = this;

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
        view: KnockoutObservable<'view' | 'edit'>;
        data: KnockoutObservable<FullCalendar.EventApi>;
    }
}