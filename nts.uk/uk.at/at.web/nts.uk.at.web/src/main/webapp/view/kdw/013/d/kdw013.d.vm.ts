module nts.uk.ui.at.kdp013.d {
    enum OverTimeLeaveAtr {
        // 0: 残業申請
        OVER_TIME_APPLICATION = 0,

        //1: 休日出勤申請
        HOLIDAY_WORK_APPLICATION = 1
    };

    const { OVER_TIME_APPLICATION } = OverTimeLeaveAtr;

    const $vm = new ko.ViewModel();
    const { time } = nts.uk as any;
    const { byId } = time.format as { byId: (format: string, time: number) => string };

    const map2Link = (otAtr: OverTimeLeaveAtr) => {
        if (otAtr === OVER_TIME_APPLICATION) {
            return $vm.$i18n('KDW013_35');
        }

        return $vm.$i18n('KDW013_36');
    };
    const map2Description = (otAtr: OverTimeLeaveAtr, time: number) => {
        if (otAtr === OVER_TIME_APPLICATION) {
            return $vm.$i18n('KDW013_38', [byId('Time_Short_HM', time)]);
        }

        return $vm.$i18n('KDW013_39', [byId('Time_Short_HM', time)]);
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        dataSource: KnockoutObservableArray<DataItem> = ko.observableArray([]);

        constructor(params: a.OvertimeLeaveTime[] | null) {
            super();

            if (!params || !params.length) {
                this.close();
            }

            const $ds = _
                .chain(params)
                .orderBy(['date'])
                // mapping data from dto to grid
                .map(({ date, overtimeLeaveAtr, time }) => ({
                    overtimeLeaveAtr,
                    date,
                    time,
                    link: map2Link(overtimeLeaveAtr),
                    description: map2Description(overtimeLeaveAtr, time)
                }))
                .value();

            this.dataSource($ds);
        }

        mounted() {
            const vm = this;

            $(vm.$el)
                // patch click link
                // note: change pseudo if DataContent change property
                .on('click', 'td[aria-describedby="kdw-013-ddata_link"]', (evt: JQueryEventObject) => {
                    const ds = ko.unwrap(vm.dataSource);
                    const di = $(evt.target).closest('tr[data-id]').data('id');
                    const exist = _.find(ds, ({ overtimeLeaveAtr }) => overtimeLeaveAtr === di);

                    if (exist) {
                        // logic per record at here
                        // note: change jump data
                        if (exist.overtimeLeaveAtr === OVER_TIME_APPLICATION) {
                            vm.$jump('at', '/view/kaf/005/a/index.xhtml', exist);
                        } else {
                            vm.$jump('at', '/view/kaf/006/a/index.xhtml', exist);
                        }
                    }
                });

            $(vm.$el)
                // remove grid tabindex
                .find('#kdw-013-ddata_container')
                .removeAttr('tabindex')
                // find link & set tabindex
                .find('td[aria-describedby="kdw-013-ddata_link"]')
                .attr('tabindex', '1')
                .on('keydown', (evt) => {
                    if (evt.keyCode === 13) {
                        evt.preventDefault();

                        $(evt.target).trigger('click');
                    }
                })
                // focus to first link
                .first()
                .focus();
        }

        // close dialog
        close() {
            const vm = this;

            vm.$window.close();
        }
    }
}