module nts.uk.ui.at.kdw013.confirm {
    @component({
        name: 'fc-confirm',
        template:
        `<td id='confirm-header' data-bind="html:$component.headerText, click:$component.toggleConfirmList"></td>
            <!-- ko foreach: { data: $component.params.timesSet, as: 'time' } -->
                 <td class="fc-day fc-confirm" style='position: relative;'">
                    <div class='fc-confirm-checkbox' data-bind="ntsCheckBox: { checked: $component.checked , text: $component.getText() }"></div>
                </td>
            <!-- /ko -->
        <style rel="stylesheet">
            .fc-confirm {
                    text-align: center;
            }
            .fc-confirm-checkbox input[type="checkbox"]+span::before{
                        top: 2px;
                        left: 0px;
                        width: 12px;
                        height: 12px;
            }
            .fc-confirm-checkbox input[type="checkbox"]+span::after{
                top: 5px;
                left: 2px;
                width: 8px;
                height: 5px;
                transform: rotate(-59deg);
            }
            .fc-confirm-checkbox input[type="checkbox"]+span{
                line-height: 14px;
                font-size: 12px;
                cursor: default;
            }
            .fc-confirm-checkbox{
                padding: 0px 0px 0px 17px;
                height: 17px;
            }
            .fc-confirm-checkbox label{
                 line-height: 11px;
            }
            .fc-confirm .checkbox-wrapper input[type="checkbox"]:checked+span::after{
                        top: 4px;
                        left: 1px;
            }
            #confirm-header{
                    cursor: pointer;
            }
        </style>
                `
    })
    export class FullCalendarConFirmComponent extends ko.ViewModel {
        today: string = moment().format('YYYY-MM-DD');
        headerText: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText('KDW013_99') + '△');
        checked: KnockoutObservable<boolean> = ko.observable(false);

        constructor(private params: TimeHeaderParams) {
            super();

            if (!this.params || !this.params.timesSet) {
                this.params.timesSet = ko.computed(() => []);
            }
        }

        mounted() {
            const vm = this;
            const { $el, params } = vm;
            const {timesSet} = params;

            ko.computed({
                read: () => {
                    const ds = ko.unwrap(timesSet);

                    if (ds.length) {
                        $el.style.display = null;
                    } else {
                        $el.style.display = 'none';
                    }

                    $($el).find('[data-bind]').removeAttr('data-bind');
                },
                disposeWhenNodeIsRemoved: $el
            });

            // fix display on ie
            vm.$el.removeAttribute('style');
        }

        getText() {
            const vm = this;

            let iconText = vm.params.showConfirmList() ? nts.uk.resource.getText('KDW013_102') : nts.uk.resource.getText('KDW013_101');

            return iconText;
        }

        toggleConfirmList() {
            const vm = this;

            let nameText = nts.uk.resource.getText('KDW013_99');

            //△ ▽
            let iconText = vm.params.showConfirmList() ? '△' : '▽';

            vm.params.showConfirmList(!vm.params.showConfirmList());
            vm.headerText(nameText + iconText);

        }

    }
    type TimeHeaderParams = {
        items: KnockoutObservableArray<any>;
        mode: KnockoutComputed<boolean>;
        screenA: nts.uk.ui.at.kdw013.a.ViewModel;
        showConfirmList: KnockoutComputed<boolean>;
    };
}