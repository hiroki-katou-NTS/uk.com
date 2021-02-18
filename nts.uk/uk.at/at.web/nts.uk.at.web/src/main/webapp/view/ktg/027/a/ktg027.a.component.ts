module nts.uk.at.view.ktg027.a {
    const API = {
        CHANGE_DATE: "/screen/at/overtimehours/onChangeDate",
        GET_DATA_INIT: "/screen/at/overtimehours/getOvertimedDisplayForSuperiorsDto",
    };

    @component({
        name: 'ktg-027-a',
        template: `
            <div class="ktg-027-a widget-title">
                <table>
                    <thead>
                        <tr>
                            <th data-bind="i18n: 'KTG027_5'"></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="ktg-027-a widget-content">
                <table>
                    <colgroup>
                        <col width="auto" />
                        <col width="180px" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <td>
                                <div data-bind="ntsDatePicker: {
                                    name: $component.$i18n('KTG027_1'),
                                    value: $component.targetYear,
                                    dateFormat: 'yearmonth',
                                    valueFormat: 'YYYYMM',
                                    showJumpButtons: true
                                }"></div>
                            </td>
                            <td class="text-left">
                                <div class="statutory" data-bind="i18n: 'KTG027_2'"></div>
                                <div class="outside" data-bind="i18n: 'KTG027_3'"></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="ktg-027-a widget-content widget-fixed scroll-padding ui-resizable">
                <div>
                    <table>
                        <colgroup>
                            <col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                        <head>
                            <tr>
                                <th class="text-center" data-bind="i18n: 'Com_Person'"></th>
                                <th class="text-center" data-bind="i18n: 'KTG027_4'"></th>
                                <td rowspan="1">
                                    <canvas data-bind="ktg-026-chart: $component.dataTable, type: 'head'"></canvas>
                                </td>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="ktg-027-a" data-bind="widget-content: 110">
                <div>
                    <table>
                        <colgroup>
                            <col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                        <tbody data-bind="foreach: { data: $component.dataTable, as: 'row' }">
                            <tr>
                                <td data-bind="text: row.businessName, click: function() { $component.openKTG026(row) }"></td>
                                <td class="text-right" data-bind="time: row.time.tt, click: function() { $component.openKDW003(row) }"></td>
                                <td data-bind="ktg-chart: $component.dataTable"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <style>
                .ktg-027-a .outside {
                    color: #e05f4e;
                }
                .ktg-027-a .statutory {
                    color: #49bfa8;
                }
                .ktg-027-a.widget-content.ui-resizable {
                    border-top: 0;
                }
                .ktg-027-a.widget-content:not(.ui-resizable) {
                    padding-bottom: 0;
                }
                .ktg-027-a.widget-content:not(.ui-resizable) td {
                    border-bottom: 0;
                }
                .ktg-027-a.widget-content.ui-resizable th,
                .ktg-027-a.widget-content.ui-resizable td {
                    border-bottom: 1px solid #999;
                }
                .ktg-027-a.widget-content.ui-resizable th:first-child,
                .ktg-027-a.widget-content.ui-resizable td:first-child {
                    overflow: hidden;
                    white-space: nowrap;
                    min-width: 100px;
                    max-width: 100px;
                    padding-left: 0px;
                    padding-right: 0px;
                    text-overflow: ellipsis;
                }
                .ktg-027-a.widget-content.ui-resizable td[rowspan] {
                    padding: 0;
                    overflow: hidden;
                }
                .ktg-027-a.widget-content.ui-resizable td[rowspan] canvas {
                    width: 230px !important;
                }
                .ktg-027-a.widget-content.ui-resizable.widget-fixed {
                    padding-bottom: 0px;
                }
                .ktg-027-a.widget-content.ui-resizable.widget-fixed>div {
                    padding-bottom: 0px;
                }
                .ktg-027-a.widget-content.ui-resizable.widget-fixed td[rowspan] canvas {
                    height: 41px !important;
                }
                .widget-container.ie .ktg-027-a.widget-content.ui-resizable td[rowspan] canvas {
                    margin-top: -1px;
                }
                .widget-container.has-scroll .ktg-027-a.scroll-padding {
                    padding-right: 17px;
                }
            </style>
            <style data-bind="html: $component.chartStyle"></style>
        `
    })
    export class KTG027AComponent extends ko.ViewModel {
        widget: string = 'KTG027A';

        // A1_2 対象年選択
        targetYear: KnockoutObservable<string | null> = ko.observable(null);

        employees: KnockoutObservableArray<PersonalInfo> = ko.observableArray([]);
        overtimeSubor: KnockoutObservableArray<OvertimeSubordinate> = ko.observableArray([]);
        personalSubor: KnockoutObservableArray<PersonalInfoSubordinate> = ko.observableArray([]);

        dataTable!: KnockoutComputed<any[]>;
        chartStyle!: KnockoutComputed<string>;

        constructor(private cache: { currentOrNextMonth: 0 | 1; }) {
            super();

            if (!this.cache) {
                this.cache = { currentOrNextMonth: 0 };
            } else {
                if (typeof this.cache.currentOrNextMonth === 'undefined') {
                    this.cache.currentOrNextMonth = 0;
                }
            }

            const vm = this;

            vm.dataTable = ko.computed({
                read: () => {
                    const employees = ko.unwrap<PersonalInfo[]>(vm.employees);
                    const overtimeSubor = ko.unwrap<OvertimeSubordinate[]>(vm.overtimeSubor);
                    const personalSubor = ko.unwrap<PersonalInfoSubordinate[]>(vm.personalSubor);

                    return _
                        .chain(employees)
                        .map((emp: PersonalInfo) => {
                            const os = _.find(overtimeSubor, ({ employeeId }) => employeeId === emp.employeeId);
                            const ps = _.find(personalSubor, ({ employeeId }) => employeeId === emp.employeeId);

                            if (os) {
                                const { agreementTime, agreMax, state } = os;

                                return _.extend(emp, {
                                    // title of chart
                                    date: emp.businessName,
                                    time: {
                                        tt: agreMax.agreementTime,
                                        ot: agreementTime.agreementTime,
                                        wh: agreMax.agreementTime - agreementTime.agreementTime
                                    },
                                    state
                                });
                            }

                            return _.extend(emp, { time: { tt: 120, ot: 0, wh: 0 } });
                        })
                        .filter(() => overtimeSubor.length)
                        .value();
                }
            });

            vm.chartStyle = ko.computed({
                read: () => {
                    const data = ko.unwrap<PersonalInfo[]>(vm.dataTable);

                    if (!data.length) {
                        return '.ktg-027-a.widget-content:not(.widget-fixed) td[rowspan] canvas { height: 0px !important; }';
                    }

                    return `.ktg-027-a.widget-content:not(.widget-fixed) td[rowspan] canvas { height: ${data.length * 41}px !important; }`;
                }
            });

            vm.dataTable
                .subscribe(() => {
                    vm.$nextTick(() => {
                        $('.ktg-027-a.ui-resizable').trigger('wg.resize');

                        $(vm.$el)
                            .find('.ktg-027-a.widget-content.ui-resizable td:first-child')
                            .each((__: number, el: HTMLElement) => {
                                if (el.offsetWidth < el.scrollWidth) {
                                    el.title = el.innerText;
                                }
                            });

                        $(vm.$el).find('[data-bind]').removeAttr('data-bind');
                    })
                });
        }

        created() {
            const vm = this;
            const { cache } = vm;

            vm
                .$blockui('invisibleView')
                .then(() => vm.$ajax('at', `${API.GET_DATA_INIT}/${cache.currentOrNextMonth}`))
                .then((response: DataInit) => {
                    const { closureId, personalInformationOfSubordinateEmployees, closingInformationForCurrentMonth, closingInformationForNextMonth } = response;

                    vm.employees(personalInformationOfSubordinateEmployees);

                    vm.targetYear
                        .subscribe((ym: string | null) => {
                            if (typeof ym === 'string') {
                                vm.loadData(ym, closureId);
                            }
                        });

                    // update targetYear by closure data
                    if (closingInformationForNextMonth) {
                        const { processingYm } = closingInformationForNextMonth;

                        vm.targetYear(`${processingYm}`);
                    } else {
                        const { processingYm } = closingInformationForCurrentMonth;

                        vm.targetYear(`${processingYm}`);
                    }
                })
                .fail((message: { messageId: string }) => {
                    vm.$dialog.error(message).then(() => vm.$blockui('clearView'));
                });
        }

        // load data by change ym
        private loadData(ym: string, closureId: number) {
            const vm = this;
            const { CHANGE_DATE: getDataWhenChangeDate } = API;

            if (ym) {
                vm.overtimeSubor([]);
                vm.personalSubor([]);

                vm
                    .$blockui('invisibleView')
                    .then(() => vm.$ajax('at', `${getDataWhenChangeDate}/${closureId}/${ym}`))
                    .then((overtime: OverTimeResponse) => {
                        const { overtimeOfSubordinateEmployees, personalInformationOfSubordinateEmployees } = overtime;

                        vm.overtimeSubor(overtimeOfSubordinateEmployees);
                        vm.personalSubor(personalInformationOfSubordinateEmployees);
                    })
                    .always(() => vm.$blockui('clearView'));
            }
        }

        openKTG026(item: any) {
            const vm = this;
            const { $user } = vm;
            let companyID: any = ko.observable(__viewContext.user.companyId);
            let paramKTG026 = {
              companyId: companyID,
              employeeId: item.employeeId,
              targetDate: vm.targetYear(),
              targetYear: "",
              mode: "Superior",
            };
            vm.$window
                .modal('at', '/view/ktg/026/a/superior.xhtml', paramKTG026);
        }

        openKDW003(item: any) {
            const vm = this;
            const { $user } = vm;
            let paramKDW003 = {
              lstEmployeeShare: item.employeeId,
              errorRefStartAtr: false,
              changePeriodAtr: true,
              screenMode: "Normal",
              displayFormat: "individual",
              initClock: "",
            };
            vm.$window
                .shared('KDW003_PARAM', paramKDW003)
                .then(() => vm.$jump('at', "/view/kdw/003/a/index.xhtml"));
        }

        destroyed() {
            const vm = this;

            vm.dataTable.dispose();
            vm.chartStyle.dispose();
        }
    }

    interface DataInit {
        closureId: number;
        closingInformationForCurrentMonth: ClosingInfor | null;
        closingInformationForNextMonth: ClosingInfor | null;
        personalInformationOfSubordinateEmployees: PersonalInfo[];
    }

    interface ClosingInfor {
        processingYm: number;
        closureStartDate: string; // YYYY/MM/DD
        closureEndDate: string;   // YYYY/MM/DD
    }

    interface PersonalInfo {
        personId: string; //"1dec5ac6-e0f8-44c4-8784-c8bc5ccdee6b",
        employeeId: string; //"1e09912b-7edf-454c-89df-9c6da54a8f71",
        businessName: string; //"ＹＳ　００１１",
        gender: 0 | 1 | 2;
        birthday: string; //"2000/01/01",
        employeeCode: string; //"YS0011",
        jobEntryDate: string; //"2020/01/01",
        retirementDate: string; //"9999/12/31"
    }

    interface OverTimeResponse {
        personalInformationOfSubordinateEmployees: PersonalInfoSubordinate[];
        overtimeOfSubordinateEmployees: OvertimeSubordinate[];
    }

    interface PersonalInfoSubordinate {
        personId: string;
        employeeId: string;
        businessName: string;
        gender: 0 | 1 | 2;
        birthday: string; //"2000/01/01",
        employeeCode: string;
        jobEntryDate: string; //"2020/01/01",
        retirementDate: string; //"9999/12/31"
    }

    interface OvertimeSubordinate {
        employeeId: string;
        yearMonth: number;
        agreementTime: {
            agreementTime: number;
            alarmTime: number;
            errorTime: number;
            exceptionLimitTime: number;
        },
        breakdown: {
            overTime: number;
            transferOverTime: number;
            legalHolidayWorkTime: number;
            legalTransferTime: number;
            holidayWorkTime: number;
            transferTime: number;
            flexLegalTime: number;
            flexIllegalTime: number;
            withinPrescribedPremiumTime: number;
            weeklyPremiumTime: number;
            monthlyPremiumTime: number;
            temporaryTime: number;
        },
        agreMax: {
            agreementTime: number;
            alarmTime: number;
            errorTime: number;
            exceptionLimitTime: number;
        },
        maxBreakdown: string | null;
        state: number;
        haveData: boolean;
        closureDate: string | null;
        closureID: number;
    }
}