module nts.uk.ui.at.kdw013.eventheadear {
    @component({
        name: 'fc-event-header',
        template:
        `<td data-bind="i18n: 'KDW013_20'"></td>
                               

                <!-- ko foreach: { data: $component.params.data, as: 'day' } -->
                <td class="fc-event-note fc-day" style='text-align: center;' data-bind="css: { 'no-data': !day.events.length }, attr: { 'data-date': day.date }">
                    <div style="display: flex;"> 
                        <div style='text-align: left;' data-bind="foreach: { data: day.events, as: 'note' }">
                            <div class="text-note" data-bind="text: note.title"></div>
                        </div>
                        <div style='text-align: left;margin-left: 5px;width: calc(100% - 70px);' data-bind="foreach: { data: day.events, as: 'note' }">
                            <div style='display: block;' class="text-note limited-label" data-bind="text: note.text"></div>
                        </div>
                    </div>
                    <!-- ko if: $component.showHIcon(day.date) -->
                    <div style="min-height: 20px;">     
                        <i class='openHIcon' data-bind="ntsIcon: { no: 232, width: 20, height: 20 },click: function(day) { $component.openHDialog(day) } " > </i>
                    </div>
                    <!-- /ko -->
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
        
        showHIcon(date) {
            const vm = this;
            const data = ko.unwrap(vm.params.screenA.$datas);

            if (!data || !date) {
                return false;
            }

            return !!_.find(_.get(data, 'convertRes', []), cv => { return moment(cv.ymd).isSame(moment(date), 'days'); });

        }
        
        openHDialog(day) {
            const vm = this;
            vm.params.setting();
            const screenA = vm.params.screenA;
            const IntegrationOfDaily = _.find(_.get(vm.params.screenA.$datas(), 'lstIntegrationOfDaily', null), id => moment(id.ymd).isSame(moment(day.date), 'days'));
            const lockInfos = _.get(vm.params.screenA.$datas(), 'lockInfos', []);
            //const displayAttItems =  _.get(vm.params.screenA.$setting(), 'manHrInputDisplayFormat.displayAttItems', []);
            const itemValues = _.find(_.get(vm.params.screenA.$datas(), 'convertRes', []), cv => moment(cv.ymd).isSame(moment(day.date), 'days')); 
            const displayAttItems = _.get(vm.params.screenA.$settings(), 'manHrInputDisplayFormat.displayAttItems');
           
            
            let param = {
                employeeId: vm.params.screenA.editable() ? vm.$user.employeeId : vm.params.screenA.employee(),
                date: day.date,
                IntegrationOfDaily,
                displayAttItems,
                itemValues: _.get(itemValues,'manHrContents',[]),
                lockInfos
            };
            
            vm.$window.shared('KDW013H', param);
            vm.$window.modal('at', '/view/kdw/013/h/index.xhtml').then(() => { 
                vm.params.screenA.dateRange.valueHasMutated();
            });
        }
    }

}
