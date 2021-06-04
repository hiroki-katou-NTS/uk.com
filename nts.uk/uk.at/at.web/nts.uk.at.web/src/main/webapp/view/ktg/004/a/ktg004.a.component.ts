module nts.uk.ui.ktg004.a {
    export const STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";

    const KTG004_API = {
        GET_DATA: 'screen/at/ktg004/getData'
    };

    const DIALOGS = {
        KTG004B: '/view/ktg/004/b/index.xhtml'
    };

    const WINDOWS = {
        KTG003A: '/view/kdw/003/a/index.xhtml'
    };

    const convertToTime = (data: string | number): string => {
        if (['0', 0, null, undefined, ''].indexOf(data) > -1) {
            return '00:00';
        } else {
            const numb = Number(data);
            const negative = numb < 0;
            const hour = numb < 0 ? Math.floor((numb < 0 ? numb * -1: numb) / 60)
									: Math.floor(numb / 60);
            const minute = Math.floor(numb % 60);

            return `${negative ? '-' : ''}${Math.abs(hour)}:${_.padStart(Math.abs(minute) + '', 2, '0')}`;
        }
    }

    @component({
        name: 'ktg-004-a',
        template: `
            <div class="widget-title">
                <table>
                    <colgroup>
                        <col width="auto" />
                        <col width="41px" />
                    </colgroup>
                    <thead>
                        <th data-bind="i18n: $component.name"></th>
                        <th data-bind="if: $component.detailedWorkStatusSettings">
                            <button class="icon" data-bind="click: $component.setting">
                                <i data-bind="ntsIcon: { no: 5 }"></i>
                            </button>
                        </th>
                    </thead>
                </table>
            </div>
            <div class="ktg-004-a" data-bind="widget-content: 100">
                <div>
                    <table class="widget-table">
                        <colgroup>
                            <col width="auto" />
                            <col width="auto" />
                            <col width="180px" />
                        </colgroup>
                        <tbody data-bind="foreach: { data: $component.itemsDisplay, as: 'row' }">
                            <tr>
                                <td data-bind="i18n: row.name"></td>
                                <td data-bind="if: row.btn">
                                    <button class="icon" data-bind="click: function() { $component.openKDW003() }">
                                        <i data-bind="ntsIcon: { no: 145 }"></i>
                                    </button>
                                </td>
                                <td class="text-right" data-bind="i18n: row.text"></td>
                            </tr>
                        </tbody>
                        <tbody data-bind="foreach: { data: $component.specialHolidaysRemainings, as: 'row'}"> 
                            <tr>
                                <td data-bind="i18n: row.name" colspan="2"></td>
                                <td class="text-right" data-bind="i18n: row.specialResidualNumber"></td>
                            </tr>
                        </tbody>
                    </table>	
                </div>
            </div>
            <style rel="stylesheet">
                .ktg-004-a .text-right {
                    text-align: right;
                }
            </style>
        `
    })
    export class KTG004AComponent extends ko.ViewModel {
        widget: string = 'KTG004A';

        name: KnockoutObservable<string> = ko.observable('');

        detailedWorkStatusSettings = ko.observable(false);

        itemsDisplay: KnockoutObservableArray<ItemDisplay> = ko.observableArray([]);
        specialHolidaysRemainings: KnockoutObservableArray<SpecialHolidaysRemaining> = ko.observableArray([]);

        constructor(private params: { currentOrNextMonth: 1 | 2; }) {
            super();

            if (this.params === undefined) {
                this.params = { currentOrNextMonth: 1 };
            }

            if (this.params.currentOrNextMonth === undefined) {
                this.params.currentOrNextMonth = 1;
            }
        }

        created() {
            const vm = this;
            const { params } = vm;
            const { currentOrNextMonth } = params || { currentOrNextMonth: 1 };

            vm.$blockui('invisibleView')
                .then(() => vm.$ajax("at", KTG004_API.GET_DATA, { currentOrNextMonth: currentOrNextMonth }))
                .then(function (data: ResponseData) {
                    const {
                        name,
                        detailedWorkStatusSettings,
                        itemsSetting,
                        attendanceInfor,
                        remainingNumberInfor
                    } = data;
                    const {
                        dailyErrors,
                        early,
                        flexCarryOverTime,
                        flexTime,
                        holidayTime,
                        late,
                        nigthTime,
                        overTime
                    } = attendanceInfor;

                    const {
                        longTermCareRemainingNumber,
                        numberAccumulatedAnnualLeave,
                        numberOfAnnualLeaveRemain,
                        numberOfSubstituteHoliday,
                        nursingRemainingNumberOfChildren,
                        remainingHolidays,
                        specialHolidaysRemainings
                    } = remainingNumberInfor;

                    const itemsDisplay: ItemDisplay[] = [];
                    const itemsHolidaysRemainings: SpecialHolidaysRemaining[] = [];

                    const items: number[] = _
                        .chain(itemsSetting)
                        .orderBy(['item'], ['asc'])
                        .filter(({ displayType }) => !!displayType)
                        .map(({ item }) => item)
                        .value();

                    vm.name(name || "");
                    vm.detailedWorkStatusSettings(detailedWorkStatusSettings);

                    _
                        .chain(items)
                        .each((item: number) => {
                            switch (item) {
                                case 21:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_1',
                                            text: dailyErrors ? 'KTG004_16' : 'KTG004_17',
                                            btn: dailyErrors ? true : false
                                        })
                                    break;
                                case 22:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_2',
                                            text: convertToTime(overTime)
                                        })
                                    break;
                                case 23:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_3',
                                            text: `${convertToTime(flexTime)}${vm.$i18n('KTG004_4', [convertToTime(flexCarryOverTime)])}`
                                        })
                                    break;
                                case 24:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_5',
                                            text: convertToTime(nigthTime)
                                        })
                                    break;
                                case 25:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_6',
                                            text: convertToTime(holidayTime)
                                        })
                                    break;
                                case 26:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_7',
                                            text: vm.$i18n('KTG004_8', [late, early])
                                        })
                                    break;
                                case 27:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_9',
                                            text: vm.$i18n('KTG004_15', [`${numberOfAnnualLeaveRemain.day}`])
                                        })
                                    break;
                                case 28:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_10',
                                            text: vm.$i18n('KTG004_15', [`${numberAccumulatedAnnualLeave}`])
                                        })
                                    break;
                                case 29:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_11',
                                            text: vm.$i18n('KTG004_15', [`${numberOfSubstituteHoliday.day}`])
                                        })
                                    break;
                                case 30:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_12',
                                            text: vm.$i18n('KTG004_15', [`${remainingHolidays}`])
                                        })
                                    break;
                                case 31:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_13',
                                            text: vm.$i18n('KTG004_15', [`${nursingRemainingNumberOfChildren.day}`])
                                        })
                                    break;
                                case 32:
                                    itemsDisplay
                                        .push({
                                            name: 'KTG004_14',
                                            text: vm.$i18n('KTG004_15', [`${longTermCareRemainingNumber.day}`])
                                        })
                                    break;
                            }
                        })
                        .value();

                    _
                        .chain(specialHolidaysRemainings)
                        .filter(({ code }) => items.indexOf(code) > -1)
                        .each(({
                            code,
                            name,
                            specialResidualNumber
                        }) => {
                            itemsHolidaysRemainings
                                .push({
                                    code,
                                    name,
                                    specialResidualNumber: vm.$i18n('KTG004_15', [`${specialResidualNumber.day}`])
                                });
                        })
                        .value();

                    vm.itemsDisplay(itemsDisplay);
                    vm.specialHolidaysRemainings(itemsHolidaysRemainings);

                    vm.$nextTick(() => {
                        $(vm.$el)
                            .find('[data-bind]')
                            .removeAttr('data-bind');
                    });
                })
                .always(() => vm.$blockui('clearView'));
        }

        public setting() {
            const vm = this;

            vm.$window
                .modal('at', DIALOGS.KTG004B)
                .then(() => {
                    vm.$window
                        .shared("KTG004B")
                        .then((data: any) => {
                            if (data) {
                                vm.created();
                            }
                        });
                });
        }

        public openKDW003() {
            const vm = this;

            vm.$jump('at', WINDOWS.KTG003A);
        }
    }

    interface ItemDisplay {
        name: string;
        text: string;
        btn?: boolean;
    }

    interface SpecialHolidaysRemaining {
        //特別休暇コード
        code: number;
        //特別休暇名称
        name: string;
        //特休残数
        specialResidualNumber: string;
    }

    interface ResponseData {
        attendanceInfor: Attendance;
        closingDisplay: Closing;
        closingThisMonth: Closing;
        closureId: number;
        detailedWorkStatusSettings: boolean;
        itemsSetting: ItemSetting[];
        name: any | null;
        nextMonthClosingInformation: any | null;

        remainingNumberInfor: RemainingNumberInfor;
    }

    interface ItemSetting {
        displayType: boolean;
        item: 21 | 22 | 23 | 24 | 25 | 26 | 27 | 28 | 29 | 30 | 31 | 32;
        name: string;
    }

    interface Closing {
        endDate: string;
        processingYm: number;
        startDate: string;
    }

    interface NumberOfShift {
        day: number;
        time: string;
    }

    interface Attendance {
        dailyErrors: boolean;
        early: string;
        flexCarryOverTime: string;
        flexTime: string;
        holidayTime: string
        late: string;
        nigthTime: string;
        overTime: string;
    }

    interface RemainingNumberInfor {
        longTermCareRemainingNumber: NumberOfShift;
        numberAccumulatedAnnualLeave: number;
        numberOfAnnualLeaveRemain: NumberOfShift;
        numberOfSubstituteHoliday: NumberOfShift;
        nursingRemainingNumberOfChildren: NumberOfShift;
        remainingHolidays: number;
        specialHolidaysRemainings: SpecialHolidaysRemainings[];
    }

    interface SpecialHolidaysRemainings {
        code: number;
        name: string;
        specialResidualNumber: NumberOfShift;
    }
}