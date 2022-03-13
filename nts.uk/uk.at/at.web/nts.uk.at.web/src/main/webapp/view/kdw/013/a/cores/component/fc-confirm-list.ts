module nts.uk.ui.at.kdw013.confirm {
    @component({
        name: 'fc-confirm-list',
        template:
        `<td data-bind="i18n: 'KDW013_100', visible:$component.params.showConfirmList"></td>
            <!-- ko foreach: { data: $component.params.timesSet, as: 'time' } -->
                <td class="fc-day fc-confirm" style='position: relative;' data-bind="html: time.date, attr: { 'data-date': time.date }, visible:$component.params.showConfirmList"></td>
            <!-- /ko -->
        <style rel="stylesheet">
            .fc-confirm {
                    text-align: center;
                }
        </style>
                `
    })
    export class FullCalendarConFirmComponent extends ko.ViewModel {
        today: string = moment().format('YYYY-MM-DD');
        headerText: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText('KDW013_99') + '△');

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
        
    }
    type TimeHeaderParams = {
        items: KnockoutObservableArray<any>;
        mode: KnockoutComputed<boolean>;
        screenA: nts.uk.ui.at.kdw013.a.ViewModel;
        showConfirmList: KnockoutComputed<boolean>;
    }; 
}