module nts.uk.ui.at.kdw013.confirm {
    @component({
        name: 'fc-confirm-list',
        template:
        `<td data-bind="i18n: 'KDW013_100', visible:$component.params.screenA.showConfirm"></td>
            <!-- ko foreach: { data: $component.params.confirmers, as: 'confirmer' } -->
                <td class="fc-day fc-confirm" style='position: relative;' data-bind="visible:$component.params.screenA.showConfirm">
                    <div style='text-align: left;flex-grow: 1;'">
                            <!-- ko foreach: { data: confirmer.confirmers, as: 'cfInfo'  } -->
                                <div class="text-note" data-bind="text: cfInfo.name"></div>
                            <!-- /ko -->
                    </div>
                </td>
            <!-- /ko -->
        <style rel="stylesheet">
            .fc-confirm {
                    text-align: center;
                }
        </style>
                `
    })
    export class FullCalendarConfirmListComponent extends ko.ViewModel {

        constructor(private params: ConfirmListParam) {
            super();
            if (!this.params || !this.params.confirmers) {
                this.params.confirmers = ko.computed(() => []);
            }
        }

        mounted() {
            const vm = this;
            const { $el, params } = vm;
            const {confirmers} = params;

            ko.computed({
                read: () => {
                    const ds = ko.unwrap(confirmers);

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
    type ConfirmListParam = {
        items: KnockoutObservableArray<any>;
        mode: KnockoutComputed<boolean>;
        screenA: nts.uk.ui.at.kdw013.a.ViewModel;
        confirmers: Array<nts.uk.ui.at.kdw013.ConfirmerByDayDto>;
    }; 
}