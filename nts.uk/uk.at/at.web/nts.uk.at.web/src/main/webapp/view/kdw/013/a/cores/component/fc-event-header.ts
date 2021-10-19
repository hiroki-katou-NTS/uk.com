module nts.uk.ui.at.kdw013.eventheadear {
    @component({
        name: 'fc-oneday-events',
        template:
        `<td data-bind="i18n: 'KDW013_20'"></td>
                <!-- ko foreach: { data: $component.data, as: 'day' } -->
                <td class="fc-event-note fc-day" style='text-align: center;' data-bind="css: { 'no-data': !day.events.length }, attr: { 'data-date': day.date }">
                    <div style='text-align: left;' data-bind="foreach: { data: day.events, as: 'note' }">
                        <div class="text-note limited-label" data-bind="text: note"></div>
                    </div>
                    <i class='openHIcon' data-bind="ntsIcon: { no: 2, width: 20, height: 20 },click: function(day) { $component.openHDialog(day) } " > </i>
                </td>
                <!-- /ko -->
                <style rel="stylesheet">
                    .openHIcon{
                        cursor: pointer;
                        }
                </style>
                `
    })
    export class FullCalendarEventHeaderComponent extends ko.ViewModel {
        today: string = moment().format('YYYY-MM-DD');

        constructor(private data: KnockoutComputed<string[][]>) {
            super();

            if (!this.data) {
                this.data = ko.computed(() => []);
            }
        }

        mounted() {
            const vm = this;
            const { $el, data } = vm;

            ko.computed({
                read: () => {
                    const ds = ko.unwrap(data);

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
        openHDialog() {
            const vm = this;
            console.log('open H');
        }
    }

}