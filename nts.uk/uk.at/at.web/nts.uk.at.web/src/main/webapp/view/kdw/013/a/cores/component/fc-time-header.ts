module nts.uk.ui.at.kdw013.timeheader {
    @component({
        name: 'fc-times',
        template:
        `<td data-bind="i18n: 'KDW013_25'"></td>
                <!-- ko foreach: { data: $component.params.timesSet, as: 'time' } -->
                    <td class="fc-day" data-bind="html: $component.formatTime(time.value,time), attr: { 'data-date': time.date }"></td>
                <!-- /ko -->
                <style rel="stylesheet">
                    .warningIcon{
                         float: right;
                         cursor: pointer;
                        }
                </style>
                `
    })
    export class FullCalendarTimesHeaderComponent extends ko.ViewModel {
        today: string = moment().format('YYYY-MM-DD');

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
        
        regisPopup(time) {
            const className =  time.date;
            $(".popup-area-i").ntsPopup({
                trigger: '.' + className,
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: '.' + className
                },
                showOnStart: false,
                dismissible: true
            });
        }
        
        OpenIDialog(vm, time) {
            let screenA = vm.params.screenA;
            let {$settings} = screenA;
                screenA.taskSettings(_.get($settings(), 'taskFrameUsageSetting.frameSettingList', []));

                // 作業リスト
                screenA.taskDtos();
                let eventInDay = _.chain(screenA.events())
                    .filter((evn) => { return moment(time).isSame(evn.start, 'days'); })
                    .filter((evn) => { return evn.extendedProps.id != extendedProps.id })
                    .sortBy('end')
                    .value();
                _.map(eventInDay, evn => { return { workNo: _.get(evn, 'extendedProps.taskBlock.taskDetails[0].supNo') 
                                                    }; });
                screenA.events()
                // 日別勤怠の応援作業時間
                screenA.ouenWorkTimes();

                // 日別勤怠の応援作業時間帯
                screenA.ouenWorkTimeSheets();

                //対象日
                screenA.targetDate(moment(time).toDate());
        }
        
        

        formatTime(value: number | null, time) {
            const vm = this;
            const className =  time.date;
            let icon = `<i class='warningIcon ` + className + `'> </i>`;
            
            setTimeout(()=> { 
                ko.applyBindingsToNode($('.' + className).not('.img-icon'), { ntsIcon: { no: 2, size: '20px', width: 20, height: 20 }, click: () => { vm.OpenIDialog(vm, time); } }); 
                $('.' + className).on('mousedown', () => { vm.regisPopup(time); });
            }, 300);
            if (!value) {
                return '&nbsp;' + icon;
            }

            const hour = Math.floor(value / 60);
            const minute = Math.floor(value % 60);
            let timeString = `${hour}:${_.padStart(`${minute}`, 2, '0')}`;


            return timeString + icon;
        }
        
        
    }
    type TimeHeaderParams = {
        items: KnockoutObservableArray<any>;
        mode: KnockoutComputed<boolean>;
        screenA: nts.uk.ui.at.kdw013.a.ViewModel;
    }; 
}