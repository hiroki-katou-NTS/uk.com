/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        checkEmployee?: KnockoutObservable<boolean>
        years: KnockoutObservableArray<IYear>;
    }

    const API = {
        GET_WORK_TIME: 'screen/at/kmk004/getWorkingHoursByCompany'
    };

    const TYPE = 0;

    const template = `
        <div class="list-time">
            <table>
                <tbody>
                    <tr>
                        <!-- ko if: checkEmployee -->
                            <td class= "check-row1"></td>
                        <!-- /ko -->
                        <td class= "label-row1">
                            <div data-bind="i18n: 'KMK004_221'"></div>
                        </td>
                        <td class= "label-row2">
                            <div data-bind="i18n: 'KMK004_222'"></div>
                        </td>
                    </tr>
                    <!-- ko foreach: workTimes -->
                    <tr>
                        <!-- ko if: $parent.checkEmployee -->
                            <td class= "check-column1">
                                <div data-bind="ntsCheckBox: { checked: $data.check }"></div>
                            </td>
                        <!-- /ko -->
                        <td class="label-column1" data-bind="text: $data.nameMonth"></td>
                        <td class="label-column2">
                            <!-- ko if: $parent.checkEmployee -->
                                <input class="lable-input" 
                                    data-bind="ntsTimeEditor: {
                                        value: $data.laborTime,
                                        enable: $data.check,
                                        inputFormat: 'time',
                                        constraint: 'TimeDay',
                                        option: {
                                            width: '60px',
                                            textalign: 'center'}, 
                                        mode: 'time'}" />
                            <!-- /ko -->
                            <!-- ko ifnot: $parent.checkEmployee -->
                                <input class="lable-input" 
                                    data-bind="ntsTimeEditor: {
                                        value: $data.laborTime,
                                        enable: $parent.ckeckNullYear, 
                                        inputFormat: 'time',
                                        constraint: 'TimeDay',
                                        option: {
                                            width: '60px',
                                            textalign: 'center'}, 
                                        mode: 'time'}"/>
                            <!-- /ko -->
                        </td>
                    </tr>
                    <!-- /ko -->
                    <!-- ko ifnot: checkEmployee -->
                        <tr>
                            <td class="label-column1">
                                <div data-bind="i18n: 'KMK004_223'"></div>
                            </td>
                            <td class="label-column2">
                                <div data-bind="text: total"></div>
                            </td>
                        </tr>
                    <!-- /ko -->
                </tbody>
            </table>
        </div>
    `;

    @component({
        name: 'time-work',
        template
    })

    class ListTimeWork extends ko.ViewModel {

        public workTimes: KnockoutObservableArray<WorkTime> = ko.observableArray([]);
        public total: KnockoutObservable<string> = ko.observable('');
        public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
        public ckeckNullYear: KnockoutObservable<boolean> = ko.observable(false);
        public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
        public years: KnockoutObservableArray<IYear>;

        created(params: Params) {
            const vm = this;
            vm.selectedYear = params.selectedYear;
            vm.checkEmployee = params.checkEmployee;
            vm.years = params.years;

            vm.initList(2020);
            vm.reloadData();

            vm.workTimes.subscribe((wts) => {
                const total: number = wts.reduce((p, c) => p += Number(c.laborTime()), 0);
                const finst: string = Math.floor(total / 60) + '';
                var last: string = total % 60 + '';

                if (last.length < 2) {
                    last = '0' + last;
                }

                vm.total(finst + ':' + last);

            });

            vm.selectedYear
                .subscribe(() => {
                    vm.reloadData();
                });
        }

        reloadData() {
            const vm = this;
            const input = { workType: TYPE, year: ko.unwrap(vm.selectedYear) };

            if (ko.unwrap(vm.selectedYear) != null) {
                vm.ckeckNullYear(true);
            }

            const exist = _.find(ko.unwrap(vm.years), (emp: IYear) => emp.year = ko.unwrap(vm.selectedYear));

            if (exist) {
                if (!exist.isNew) {
                    vm.$ajax(API.GET_WORK_TIME, input)
                        .then((data: IWorkTime[]) => {
                            if (data.length > 0) {
                                console.log(data);
                                const data1: IWorkTime[] = [];
                                var check: boolean = true;

                                if (ko.unwrap(vm.checkEmployee)) {
                                    check = false;
                                }
                                data.map(m => {
                                    const s: IWorkTime = { check: check, yearMonth: m.yearMonth, laborTime: m.laborTime };
                                    data1.push(s);
                                });

                                vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                            }
                        });
                    ;
                }
                else{
                    vm.initList(ko.unwrap(vm.selectedYear));
                }
            }
        }

        initList(year: number) {
            const vm = this
            var check: boolean = true;

            if (ko.unwrap(vm.checkEmployee)) {
                check = false;
            }

            const IWorkTime1: IWorkTime[] = [{ check: check, yearMonth: year*100 +1, laborTime: 0 },
            { check: check, yearMonth: year*100 +2, laborTime: 0 },
            { check: check, yearMonth: year*100 +3, laborTime: 0 },
            { check: check, yearMonth: year*100 +4, laborTime: 0 },
            { check: check, yearMonth: year*100 +5, laborTime: 0 },
            { check: check, yearMonth: year*100 +6, laborTime: 0 },
            { check: check, yearMonth: year*100 +7, laborTime: 0 },
            { check: check, yearMonth: year*100 +8, laborTime: 0 },
            { check: check, yearMonth: year*100 +9, laborTime: 0 },
            { check: check, yearMonth: year*100 +10, laborTime: 0 },
            { check: check, yearMonth: year*100 +11, laborTime: 0 },
            { check: check, yearMonth: year*100 +12, laborTime: 0 }];
            vm.workTimes(IWorkTime1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
        }
    }
}

interface IWorkTime {
    check: boolean;
    yearMonth: number;
    laborTime: number
}

class WorkTime {
    check: KnockoutObservable<boolean> = ko.observable(false);
    yearMonth: KnockoutObservable<number | null> = ko.observable(null);
    nameMonth: KnockoutObservable<string> = ko.observable('');
    laborTime: KnockoutObservable<number> = ko.observable(0);

    constructor(params?: IWorkTime & { parrent: KnockoutObservableArray<WorkTime> }) {
        const md = this;

        md.create(params);
        this.laborTime.subscribe(c => params.parrent.valueHasMutated());
    }

    public create(param?: IWorkTime) {
        const md = this;
        md.check(param.check);
        md.yearMonth(param.yearMonth);

        if (param.check) {
            md.laborTime(param.laborTime);
        }

        switch (param.yearMonth.toString().substring(4, 6)) {
            case "01":
                md.nameMonth('1月度')
                break
            case "02":
                md.nameMonth('2月度')
                break
            case "03":
                md.nameMonth('3月度')
                break
            case "04":
                md.nameMonth('4月度')
                break
            case "05":
                md.nameMonth('5月度')
                break
            case "06":
                md.nameMonth('6月度')
                break
            case "07":
                md.nameMonth('7月度')
                break
            case "08":
                md.nameMonth('8月度')
                break
            case "09":
                md.nameMonth('9月度')
                break
            case "10":
                md.nameMonth('10月度')
                break
            case "11":
                md.nameMonth('11月度')
                break
            case "12":
                md.nameMonth('12月度')
                break
        }
    }
}

// class LaborTime {
//     legalLaborTime: KnockoutObservable<number | null> = ko.observable(null);
//     withinLaborTime: KnockoutObservable<number | null> = ko.observable(null);
//     weekAvgTime: KnockoutObservable<number | null> = ko.observable(null);

//     constructor(params?: ILaborTime) {
//         const md = this;
//         md.create(params);
//     }

//     public create(param?: ILaborTime) {
//         const md = this;
//         md.legalLaborTime(param.legalLaborTime);
//         md.withinLaborTime(param.withinLaborTime);
//         md.weekAvgTime(param.weekAvgTime);
//     }
// }
