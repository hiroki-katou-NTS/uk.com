module nts.uk.ui.at.kdw013.eventheadear {
    @component({
        name: 'fc-event-header',
        template:
        `<td data-bind="i18n: 'KDW013_20'"></td>
                <!-- ko foreach: { data: $component.params.data, as: 'day' } -->
                <td class="fc-event-note fc-day" style='text-align: center;' data-bind="css: { 'no-data': !day.events.length }, attr: { 'data-date': day.date }">
                    <div style='text-align: left;' data-bind="foreach: { data: day.events, as: 'note' }">
                        <div class="text-note limited-label" data-bind="text: note"></div>
                    </div>
                    <i class='openHIcon' data-bind="ntsIcon: { no: 232, width: 20, height: 20 },click: function(day) { $component.openHDialog(day) } " > </i>
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

        constructor(private params: any) {
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
        openHDialog(day) {
            const vm = this;
            vm.params.setting();
            const screenA = vm.params.screenA;
            const IntegrationOfDaily = _.find(_.get(vm.params.screenA.$datas(), 'lstIntegrationOfDaily', null), id => moment(id.ymd).isSame(moment(day.date), 'days'));
            const lockInfos = _.get(vm.params.screenA.$datas(), 'lockInfos', []);
            let param = {
                //対象社員
                sid: vm.$user.employeeId,
                //対象日 
                date: day,
                //日別実績(Work) 
                IntegrationOfDaily,
//                //実績入力ダイアログ表示項目一覧
//                displayAttItems:,
//                 //実績内容
//                itemValues:manHrContents
                //日別実績のロック状態 Optional<日別実績のロック状態>
               //lockInfos: DailyLock 
            }
            vm.$window.modal('at', '/view/kdw/013/h/index.xhtml', param).then(() => { });
            console.log('open H');
        }
    }

}