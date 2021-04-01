module nts.uk.ui.at.kdp013.c {
    const COMPONENT_NAME = 'kdp013c';

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
                    <button data-bind="click: $component.params.close, icon: 202, size: 12"></button>
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
                            <div data-bind="kdw-timerange: ko.observable()"></div>
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_10'"></td>
                        <td>
                            <input type="text" data-bind="ntsTextEditor: { value: ko.observable('') }" />
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_13'"></td>
                        <td>
                            <input type="text" data-bind="ntsTextEditor: { value: ko.observable('') }" />
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_16'"></td>
                        <td>
                            <input type="text" data-bind="ntsTextEditor: { value: ko.observable('') }" />
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_19'"></td>
                        <td>
                            <input type="text" data-bind="ntsTextEditor: { value: ko.observable('') }" />
                        </td>
                    </tr>
                    <tr>
                        <td data-bind="i18n: 'C1_22'"></td>
                        <td>
                            <input type="text" data-bind="ntsTextEditor: { value: ko.observable('') }" />
                        </td>
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
                            <textarea data-bind="ntsMultilineEditor: { value: ko.observable('') }" />
                        </td>
                    </tr>
                    <tr class="functional">
                        <td colspan="2">
                            <button class="proceed" data-bind="i18n: 'C1_30', click: $component.params.close"></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <style>
            .edit-event {
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
            .edit-event table {
                width: 100%;
            }
            .edit-event table tr>td:first-child {
                vertical-align: top;
                padding-top: 6px;
            }
            .edit-event table tr.functional td {
                text-align: center;
            }
            .edit-event table tr td>.ntsControl {
                width: 100%;
                display: block;
                box-sizing: border-box;
                margin-bottom: 10px;
            }
            .edit-event table tr td>.ntsControl>input {
                width: 100%;
                box-sizing: border-box;
            }
            .edit-event table tr td>.ntsControl>textarea {
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
        </style>
        `
    })
    export class ViewModel extends ko.ViewModel {
        constructor(public params: Params) {
            super();
        }

        mounted() {
            const vm = this;
            const { $el, params } = vm;
            const { view } = params;

            // focus to first input element
            ko.computed({
                read: () => {
                    const _v = ko.unwrap(view);

                    if (_v === 'edit') {
                        $($el).find('input:first').focus();
                    }
                },
                disposeWhenNodeIsRemoved: $el
            });
        }

        save() {
            const vm = this;


        }
    }

    @handler({
        bindingName: 'kdw-timerange',
        validatable: true,
        virtual: false
    })
    export class KDW013TimeRangeBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) => {
            const $start = document.createElement('input');
            const $end = document.createElement('input');
            const $space = document.createElement('span');
            const $wtime = document.createElement('span');
            const $value = document.createElement('span');

            $start.type = 'text';
            $end.type = 'text';

            $(element)
                .append($start)
                .append($space)
                .append($end)
                .append($wtime)
                .append($value);

            ko.applyBindingsToNode($space, { i18n: 'KDW013_30' }, bindingContext);
            ko.applyBindingsToNode($wtime, { i18n: 'KDW013_25' }, bindingContext);

            ko.applyBindingsToNode($value, { text: '24:00' }, bindingContext);

            ko.applyBindingsToNode($start, { 'input-time': ko.observable(null) }, bindingContext);
            ko.applyBindingsToNode($end, { 'input-time': ko.observable(null) }, bindingContext);

            element.classList.add('ntsControl');
            element.classList.add('time-range-control');

            $start.classList.add('nts-input');
            $end.classList.add('nts-input');
        }
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