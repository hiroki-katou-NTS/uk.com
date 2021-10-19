module nts.uk.ui.at.kdw013.timeheader {
    @component({
        name: 'fc-times',
        template:
        `<td data-bind="i18n: 'KDW013_25'"></td>
                <!-- ko foreach: { data: $component.data, as: 'time' } -->
                    <td class="fc-day" data-bind="html: $component.formatTime(time.value,time), attr: { 'data-date': time.date }"></td>
                <!-- /ko -->
                <style rel="stylesheet">
                    .warningIcon{
                         float: right;
                        }
                </style>
                `
    })
    export class FullCalendarTimesHeaderComponent extends ko.ViewModel {
        today: string = moment().format('YYYY-MM-DD');

        constructor(private data: KnockoutComputed<{ date: string; value: number | null; }[]>) {
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

        formatTime(value: number | null, time) {
            let icon = `<i class='warningIcon'> </i>`;
            setTimeout(function() { ko.applyBindingsToNode($('.warningIcon'), { ntsIcon: { no: 2, size: '20px', width: 20, height: 20 } }); }, 300);
            if (!value) {
                return '&nbsp;' + icon;
            }

            const hour = Math.floor(value / 60);
            const minute = Math.floor(value % 60);
            let timeString = `${hour}:${_.padStart(`${minute}`, 2, '0')}`


            return timeString;
        }
    }
}