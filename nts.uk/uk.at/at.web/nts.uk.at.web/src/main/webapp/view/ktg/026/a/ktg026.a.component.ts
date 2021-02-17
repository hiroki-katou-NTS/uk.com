module nts.uk.at.view.ktg026.a {
    import time = nts.uk.time;

    declare const Chart: any | undefined;

    const API = {
        startScreen: 'screen/at/ktg26/startScreen',
        extractOvertime: 'screen/at/ktg26/extractOvertime'
    };
    // chartjs script cdn
    const SCR_URL = 'https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js';

    // options for chartjs
    const options = (max: number, type: 'head' | 'body' = 'body') => ({
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: false
        },
        tooltips: {
            callbacks: {
                label: (item: { value: string }) => {
                    const { format } = time as any;
                    const numb = parseInt(item.value);

                    if (typeof numb === 'number') {
                        return format.byId('Clock_Short_HM', numb);
                    }

                    return '';
                }
            }
        },
        scales: {
            xAxes: [{
                ticks: {
                    min: 0,
                    max: Math.min(max, 6000),
                    display: true,
                    beginAtZero: true,
                    callback: function (value: number, index: number, data: number[]) {
                        const { format } = time as any;

                        if (!value) {
                            return '0';
                        }

                        if (value === 2700) {
                            return '45:00';
                        }

                        if (index === data.length - 1) {
                            return format.byId('Clock_Short_HM', value);
                        }

                        return '';
                    },
                    stepSize: 2700,
                    fontSize: 14,
                    fontColor: '#000',
                    fontFamily: 'Meiryo UI'
                },
                stacked: true,
                position: 'top',
                afterFit: (scale: any) => {
                    scale.height = type === 'body' ? 0 : 41;
                },
                gridLines: {
                    color: '#999',
                    z: 1,
                    borderDash: [3, 2]
                }
            }],
            yAxes: [{
                ticks: {
                    display: false
                },
                gridLines: {
                    color: '#999',
                    z: 1,
                    zeroLineColor: '#fff'
                },
                stacked: true
            }]
        }
    });

    // 色	※色定義-就業.xlsxを参照
    const timeStyle = (state: number): string => {
        if (state === 1) {
            return 'exceeding-limit-error';
        }

        if (state === 2) {
            return 'exceeding-limit-alarm';
        }

        if (state === 3) {
            return 'special-exceeded-limit-error';
        }

        if (state === 4) {
            return 'special-exceeded-limit-alarm';
        }

        if (state === 6) {
            return 'special-exceeding-limit-error';
        }

        if (state === 7) {
            return 'special-exceeding-limit';
        }

        return '';
    }

    @handler({
        bindingName: 'time',
        validatable: true,
        virtual: false
    })
    export class TimeBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLCanvasElement, valueAccessor: () => number | undefined) {
            const numb = valueAccessor();
            const { format } = time as any;

            if (typeof numb !== 'number') {
                element.innerText = '';

                return;
            }

            element.innerText = format.byId('Clock_Short_HM', numb);
        }
    }

    @handler({
        bindingName: 'ktg-026-chart',
        validatable: true,
        virtual: false
    })
    export class Ktg026ChartBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLCanvasElement, valueAccessor: () => KnockoutObservableArray<DataRow>, allBindingsAccessor: KnockoutAllBindingsAccessor) {
            let chart: any = null;
            const dataSource = valueAccessor();
            const type: 'head' | 'body' | KnockoutObservable<'head' | 'body'> = allBindingsAccessor.get('type');

            const ie = !!navigator.userAgent.match(/MSIE|Trident/g);

            $.Deferred()
                .resolve(true)
                .then(() => _.get(window, 'Chart') || $.getScript(SCR_URL))
                .then(() => {
                    ko.computed({
                        read: () => {
                            const data = ko.unwrap<DataRow[]>(dataSource) || [];
                            const tpe = ko.unwrap<'head' | 'body'>(type) || 'body';

                            // destroy old chart instance
                            if (chart && typeof chart.destroy === 'function') {
                                chart.destroy();
                            }

                            const max = _
                                .chain(data)
                                .reduce((p, c) => {
                                    const { time } = c;
                                    const { tt } = time;

                                    return p >= tt ? p : tt;
                                }, 0)
                                .value();

                            // create new chart (draw with new data)
                            chart = new Chart(element.getContext("2d"), {
                                type: 'horizontalBar',
                                data: {
                                    labels: tpe === 'head' ? [] : data.map(({ date }) => date),
                                    datasets: tpe === 'head' ? [] : [{
                                        data: data.map(({ time }) => Math.min(time.ot, 6000)),
                                        backgroundColor: data.map(() => "#49bfa8")
                                    }, {
                                        data: data.map(({ time }) => time.wh),
                                        backgroundColor: data.map(() => "#e05f4e")
                                    }]
                                },
                                options: options(max || 4800, tpe)
                            });

                            if (ie) {
                                $(element).closest('.widget-container').addClass('ie')
                            } else {
                                $(element).closest('.widget-container').removeClass('ie');
                            }
                        },
                        disposeWhenNodeIsRemoved: element
                    })
                });
        }
    }

    @component({
        name: 'ktg-026-a',
        template: `
            <div class="ktg-026-a widget-title">
                <table>
                    <thead>
                        <tr>
                            <th data-bind="i18n: 'KTG026_5'"></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="ktg-026-a widget-content">
                <table>
                    <colgroup>
                        <col width="auto" />
                        <col width="180px" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <td>
                                <div data-bind="ntsDatePicker: {
                                    name: $component.$i18n('KTG026_1'),
                                    value: $component.targetYear,
                                    dateFormat: 'YYYY',
                                    valueFormat: 'YYYY',
                                    showJumpButtons: true
                                }"></div>
                            </td>
                            <td class="text-left">
                                <div class="statutory" data-bind="i18n: 'KTG026_2'"></div>
                                <div class="outside" data-bind="i18n: 'KTG026_3'"></div>
                            </td>
                        </tr>
                        <tr>
                            <td data-bind="text: $component.exceededNumber" colspan="2"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="ktg-026-a widget-content widget-fixed scroll-padding ui-resizable">
                <div>
                    <table>
                        <colgroup>
                            <col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                        <head>
                            <tr>
                                <th class="text-center" data-bind="i18n: 'KTG026_7'"></th>
                                <th class="text-center" data-bind="i18n: 'KTG026_4'"></th>
                                <td rowspan="1">
                                    <canvas data-bind="ktg-026-chart: $component.dataTable, type: 'head'"></canvas>
                                </td>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="ktg-026-a" data-bind="widget-content: 110">
                <div>
                    <table>
                        <colgroup>
                            <col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                        <tbody data-bind="foreach: { data: $component.dataTable, as: 'row' }">
                            <tr>
                                <td data-bind="text: row.date"></td>
                                <td class="text-right" data-bind="time: row.time.tt, css: row.state"></td>
                                <!-- ko if: $index() === 0 -->
                                <td data-bind="attr: { rowspan: $component.dataTable().length }">
                                    <canvas data-bind="ktg-026-chart: $component.dataTable, type: 'body'"></canvas>
                                </td>
                                <!-- /ko -->
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <style>
                .ktg-026-a .outside {
                    color: #e05f4e;
                }
                .ktg-026-a .statutory {
                    color: #49bfa8;
                }
                .ktg-026-a.widget-content.ui-resizable {
                    border-top: 0;
                }
                .ktg-026-a.widget-content:not(.ui-resizable) {
                    padding-bottom: 0;
                }
                .ktg-026-a.widget-content:not(.ui-resizable) td {
                    border-bottom: 0;
                }
                .ktg-026-a.widget-content.ui-resizable th,
                .ktg-026-a.widget-content.ui-resizable td {
                    border-bottom: 1px solid #999;
                }
                .ktg-026-a.widget-content.ui-resizable td[rowspan] {
                    padding: 0;
                    overflow: hidden;
                }
                .ktg-026-a.widget-content.ui-resizable td[rowspan] canvas {
                    width: 230px !important;
                }
                .ktg-026-a.widget-content.ui-resizable.widget-fixed {
                    padding-bottom: 0px;
                }
                .ktg-026-a.widget-content.ui-resizable.widget-fixed>div {
                    padding-bottom: 0px;
                }
                .ktg-026-a.widget-content.ui-resizable.widget-fixed td[rowspan] canvas {
                    height: 41px !important;
                }
                .widget-container.ie .ktg-026-a.widget-content.ui-resizable td[rowspan] canvas {
                    margin-top: -1px;
                }
                .widget-container.has-scroll .ktg-026-a.scroll-padding {
                    padding-right: 17px;
                }
                /* 限度アラーム時間超過 */
                .ktg-026-a.widget-content.ui-resizable .exceeding-limit-alarm {
                    background-color: #f6f636; /* 36協定アラーム */
                    color: #ff0000; /* 36協定アラーム文字 */
                }
                /* 限度エラー時間超過 */
                .ktg-026-a.widget-content.ui-resizable .exceeding-limit-error {
                    background-color: #FD4D4D; /* 36協定エラー */
                    color: #ffffff; /* 36協定エラー文字 */
                }
                /* 限度アラーム時間超過（特例あり） */
                .ktg-026-a.widget-content.ui-resizable .special-exceeding-limit {
                    background-color: #eb9152; /* 36協定特例 */
                }
                /* 限度エラー時間超過（特例あり） */
                .ktg-026-a.widget-content.ui-resizable .special-exceeding-limit-error {
                    background-color: #eb9152; /* 36協定特例 */
                }
                /* 特例限度アラーム時間超過 */
                .ktg-026-a.widget-content.ui-resizable .special-exceeded-limit-alarm  {
                    background-color: #f6f636; /* 36協定アラーム */
                    color: #ff0000; /* 36協定アラーム文字 */
                }
                /* 特例限度エラー時間超過 */
                .ktg-026-a.widget-content.ui-resizable .special-exceeded-limit-error {
                    background-color: #FD4D4D; /* 36協定エラー */
                    color: #ffffff; /* 36協定エラー文字 */
                }
            </style>
            <style data-bind="html: $component.chartStyle"></style>
        `
    })
    export class KTG026AComponent extends ko.ViewModel {
        widget: string = 'KTG026A';

        // A1_2 対象年選択
        targetYear: KnockoutObservable<string> = ko.observable('');
        excessTimes: KnockoutObservable<number> = ko.observable(0);
        // A1_5 超過回数
        exceededNumber!: KnockoutComputed<string>;

        // A1_6 社員名
        employeeName: KnockoutObservable<String> = ko.observable('');

        dataTable: KnockoutObservableArray<DataRow> = ko.observableArray([]);
        chartStyle!: KnockoutComputed<string>;

        employeesOvertime!: any;

        constructor(private cache: { currentOrNextMonth: number; } | null) {
            super();
            const vm = this;

            vm.chartStyle = ko.computed({
                read: () => {
                    const data = ko.unwrap<DataRow[]>(vm.dataTable);

                    if (!data.length) {
                        return '.ktg-026-a.widget-content:not(.widget-fixed) td[rowspan] canvas { height: 0px !important; }';
                    }

                    return `.ktg-026-a.widget-content:not(.widget-fixed) td[rowspan] canvas { height: ${data.length * 41}px !important; }`;
                }
            });

            vm.exceededNumber = ko.computed({
                read: () => {
                    const year = ko.unwrap<string>(vm.targetYear);
                    const time = ko.unwrap<number>(vm.excessTimes);

                    return vm.$i18n('KTG026_6', [`${year}`, `${time}回`]);
                }
            });
        }

        created() {
            const vm = this;
            const { $user, cache } = vm;
            const { employeeId } = $user;
            // 1: 従業員参照モード 2: 上長参照モード
            const { currentOrNextMonth } = (cache || { currentOrNextMonth: 1 });
            const targetDate: any = null;
            const targetYear: any = null;
            const command = { employeeId, targetDate, targetYear, currentOrNextMonth };

            vm
                .$blockui('invisibleView')
                .then(() => vm.$ajax('at', API.startScreen, command))
                .then((response: EmployeesOvertimeDisplay) => {
                    if (!!response) {
                        vm.employeesOvertime = response;
                        const { yearIncludeThisMonth, yearIncludeNextMonth } = response;

                        vm.excessTimes(response.agreeInfo.excessTimes);

                        // ???
                        vm.employeeName(response.empInfo.businessName);

                        const targetYear = (currentOrNextMonth === 1 ? yearIncludeThisMonth : yearIncludeNextMonth) || '';

                        vm.targetYear(targetYear.toString());

                        vm.displayDataTable(response);
                    }
                })
                .fail((error: { messageId: string }) => {
                    vm.$dialog.alert(error).then(() => vm.dataTable([]));
                })
                .always(() => vm.$blockui('clearView'))
                .always(() => {
                    vm.$nextTick(() => {
                        $(vm.$el)
                            .find('[data-bind]')
                            .removeAttr('data-bind');

                        $('.ktg-026-a.ui-resizable').trigger('wg.resize');
                    });
                });
        }

        mounted() {
            const vm = this;
            const { employeeId } = vm.$user;

            vm.targetYear
                .subscribe((targetYear) => {
                    const { employeesOvertime } = vm;
                    const { closureID, closingPeriod } = employeesOvertime;
                    const { processingYm } = closingPeriod || { processingYm: moment().format('YYYYMM') };

                    const requestBody = {
                        employeeId,
                        targetYear,
                        processingYm,
                        closingId: closureID
                    };

                    vm.extractOvertime(requestBody);
                });

            $('.ktg-026-a.ui-resizable').trigger('wg.resize');
        }

        private extractOvertime(command: any): void {
            const vm = this;

            vm
                .$blockui('invisibleView')
                // 指定する年と指定する社員の時間外時間と超過回数の取得
                .then(() => vm.$ajax('at', API.extractOvertime, command))
                .then((response: EmployeesOvertimeDisplay) => {
                    if (!!response) {
                        vm.displayDataTable(response);
                    }
                })
                .fail((error: { messageId: string }) => {
                    vm.$dialog.alert(error).then(() => vm.dataTable([]));
                })
                .always(() => vm.$blockui('clearView'))
                .always(() => {
                    vm.$nextTick(() => {
                        $(vm.$el)
                            .find('[data-bind]')
                            .removeAttr('data-bind');
                    });
                });
        }

        private displayDataTable(data: EmployeesOvertimeDisplay): void {
            const vm = this;

            // clear table
            vm.dataTable([]);

            vm.$nextTick(() => {
                const { ymOvertimes } = data;

                if (!!ymOvertimes) {
                    const data = _
                        .chain(ymOvertimes)
                        .map(({ yearMonth, agreeTime }) => {
                            const date = moment(yearMonth, 'YYYYMM').format('YYYY/MM');
                            const { agreMax: tt, agreementTime: wh, state } = agreeTime;

                            return {
                                date,
                                time: {
                                    // tt =  ot + wh
                                    // total = overtime + work with holiday
                                    tt: wh.agreementTime || 0,
                                    ot: (wh.agreementTime || 0) - (tt.agreementTime || 0),
                                    wh: (tt.agreementTime || 0)
                                },
                                state: timeStyle(state)
                            };
                        })
                        .value();

                    // rebind new data to table & chart
                    vm.dataTable(data);
                }

                vm.$nextTick(() => $('.ktg-026-a.ui-resizable').trigger('wg.resize'));
            });
        }
    }

    interface YearMonthOverTime {
        yearMonth: number;
        agreeTime?: AgreeTime;
    }

    interface AgreeTime {
        state: 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7;
        agreMax: AgreTime;
        agreementTime: AgreTime;
    }

    interface AgreTime {
        agreementTime: number;
        alarmTime: number;
        errorTime: number;
        exceptionLimitTime: number;
    }

    interface DataRow {
        date: string;
        time: {
            tt: number;
            ot: number;
            wh: number;
        };
        state: string;
    }

    interface EmployeesOvertimeDisplay {
        // ログイン者の締めID
        closureID: number;
        // 対象社員の個人情報
        empInfo: any;
        // 対象社員の各月の時間外時間
        ymOvertimes: YearMonthOverTime[];
        // 対象社員の年間超過回数
        agreeInfo: any;
        // 当月の締め情報
        closingPeriod: any;
        // 当月含む年
        yearIncludeThisMonth: number;
        // 翌月含む年
        yearIncludeNextMonth: number;
        // 表示する年
        displayYear: number;
    }
}