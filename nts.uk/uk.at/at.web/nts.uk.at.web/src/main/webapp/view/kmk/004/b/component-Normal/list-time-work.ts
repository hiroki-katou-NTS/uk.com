/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        checkEmployee?: KnockoutObservable<boolean>
        years: KnockoutObservableArray<IYear>;
        type: SIDEBAR_TYPE;
        selectId: KnockoutObservable<string>;
        workTimes: KnockoutObservableArray<WorkTime>;
        yearDelete: KnockoutObservable<number | null>;
        startDate: KnockoutObservable<number>;
        newYearQ?: KnockoutObservable<boolean>;
    }

    const API = {
        GET_WORK_TIME: 'screen/at/kmk004/getWorkingHoursByCompany',
        GET_WORKTIME_WORKPLACE: 'screen/at/kmk004/viewc/wkp/getWorkTimes',
        GET_WORKTIME_EMPLOYMENT: 'screen/at/kmk004/viewd/emp/getWorkTimes',
        GET_WORKTIME_EMPLOYEE: 'screen/at/kmk004/viewe/sha/getWorkTimes'
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
                                        name:'#[KMK004_222]',
                                        value: $data.laborTime,
                                        enable: $data.check,
                                        inputFormat: 'time',
                                        constraint: 'MonthlyTime',
                                        option: {
                                            width: '65px',
                                            textalign: 'center'}, 
                                        mode: 'time'}" />
                            <!-- /ko -->
                            <!-- ko ifnot: $parent.checkEmployee -->
                                <input class="lable-input" 
                                    data-bind="ntsTimeEditor: {
                                        name:'#[KMK004_222]',
                                        value: $data.laborTime,
                                        enable: $data.check, 
                                        inputFormat: 'time',
                                        constraint: 'MonthlyTime',
                                        option: {
                                            width: '65px',
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
        public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
        public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
        public type: SIDEBAR_TYPE;
        public selectId: KnockoutObservable<string>;
        public mode: KnockoutObservable<'New' | 'Update'> = ko.observable('New');
        public workTimeSaves: KnockoutObservableArray<WorkTimeSave> = ko.observableArray([]);
        public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
        public startDate: KnockoutObservable<number> = ko.observable(2020);
        public newYearQ: KnockoutObservable<boolean> = ko.observable(false);

        created(params: Params) {
            const vm = this;
            vm.selectedYear = params.selectedYear;
            vm.checkEmployee = params.checkEmployee;
            vm.years = params.years;
            vm.type = params.type;
            vm.selectId = params.selectId;
            vm.workTimes = params.workTimes;
            vm.yearDelete = params.yearDelete;
            vm.startDate = params.startDate;

            if (params.newYearQ) {
                vm.newYearQ = params.newYearQ;
            }

            if (vm.type != "Com_Company"){
                vm.initList();
            }

            vm.workTimes.subscribe((wts) => {
                const total: number = wts.reduce((p, c) => p += Number(c.laborTime()), 0);
                if (!isNaN(total)) {
                    const finst: string = Math.floor(total / 60) + '';
                    var last: string = total % 60 + '';

                    if (last.length < 2) {
                        last = '0' + last;
                    }

                    vm.total(finst + ':' + last);
                }

                if (ko.unwrap(vm.selectedYear) != null) {
                    const index = _.map(ko.unwrap(vm.years), m => m.year.toString()).indexOf(ko.unwrap(vm.selectedYear).toString());

                    if (ko.unwrap(vm.years).length > 0) {
                        if (ko.unwrap(vm.mode) === 'Update') {
                            if (!ko.unwrap(vm.years)[index].isNew) {
                                _.remove(ko.unwrap(vm.years), ((value) => {
                                    return value.year == ko.unwrap(vm.selectedYear);
                                }));
                                vm.years.push(new IYear(ko.unwrap(vm.selectedYear), true));
                                vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
                                ko.unwrap(vm.years)[index].isNew = false;
                            }
                        }
                    }
                    vm.updateListSave();
                }

                _.forEach(ko.unwrap(vm.workTimes), ((value) => {
                    if (ko.unwrap(value.check)) {
                        if (ko.unwrap(value.laborTime) == null) {
                            value.updateLaborTime(0);
                        }
                    } else {
                        value.updateLaborTime(null);
                    }
                }));

            });

            vm.selectedYear
                .subscribe(() => {
                    if (ko.unwrap(ko.unwrap(vm.selectedYear) != null)) {
                        vm.mode("New");
                        vm.reloadData();
                    }
                });

            vm.selectId
                .subscribe(() => {
                    vm.workTimeSaves([]);
                });

            vm.years
                .subscribe(() => {
                    if (ko.unwrap(vm.years).length == 0) {
                        vm.workTimeSaves([]);
                        vm.initList();

                    } else {
                        if (ko.unwrap(vm.workTimeSaves).length > ko.unwrap(vm.years).length) {
                            _.remove(ko.unwrap(vm.workTimeSaves), ((value) => {
                                return value.year == ko.unwrap(vm.yearDelete);
                            }))
                        }
                    }
                });
        }

        reloadData() {
            const vm = this;
            const input = { workType: TYPE, year: ko.unwrap(vm.selectedYear) };

            const exist = _.find(ko.unwrap(vm.years), (emp: IYear) => emp.year as number == ko.unwrap(vm.selectedYear) as number);

            if (exist) {
                switch (vm.type) {
                    case 'Com_Company':
                        const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSave) => m.year as number == ko.unwrap(vm.selectedYear) as number);

                        if (exist) {
                            vm.workTimes(exist.worktimes.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                            vm.mode('Update');
                        } else {
                            vm.$ajax(API.GET_WORK_TIME, input)
                                .done((data: IWorkTime[]) => {
                                    if (data.length > 0) {
                                        const data1: IWorkTime[] = [];
                                        vm.startDate(data[0].yearMonth);
                                        data.map(m => {
                                            const s: IWorkTime = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
                                            data1.push(s);
                                        });

                                        vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                        vm.mode('Update');
                                    }
                                });
                        }
                        break;
                    case 'Com_Workplace':
                        if (ko.unwrap(vm.selectId) !== '') {
                            const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSave) => m.year as number == ko.unwrap(vm.selectedYear) as number);
                            if (exist) {
                                vm.workTimes(exist.worktimes.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                vm.mode('Update');
                            } else {

                                vm.$ajax(API.GET_WORKTIME_WORKPLACE + '/' + ko.unwrap(vm.selectId) + '/' + ko.unwrap(vm.selectedYear))
                                    .done((data: IWorkTime[]) => {
                                        if (data.length > 0) {
                                            const data1: IWorkTime[] = [];
                                            vm.startDate(data[0].yearMonth);
                                            data.map(m => {
                                                const s: IWorkTime = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
                                                data1.push(s);
                                            });

                                            vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                        }
                                        vm.mode('Update');
                                    });
                            }
                        }
                        break;
                    case 'Com_Employment':
                        if (ko.unwrap(vm.selectId) !== '') {
                            const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSave) => m.year as number == ko.unwrap(vm.selectedYear) as number);

                            if (exist) {
                                vm.workTimes(exist.worktimes.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                vm.mode('Update');
                            } else {
                                vm.$ajax(API.GET_WORKTIME_EMPLOYMENT + '/' + ko.unwrap(vm.selectId) + '/' + ko.unwrap(vm.selectedYear))
                                    .done((data: IWorkTime[]) => {
                                        if (data.length > 0) {
                                            const data1: IWorkTime[] = [];
                                            vm.startDate(data[0].yearMonth);
                                            data.map(m => {
                                                const s: IWorkTime = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
                                                data1.push(s);
                                            });

                                            vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                        }
                                        vm.mode('Update');
                                    });
                            }
                        }
                        break;
                    case 'Com_Person':
                        if (ko.unwrap(vm.selectId) !== '') {
                            const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSave) => m.year as number == ko.unwrap(vm.selectedYear) as number);

                            if (exist) {
                                vm.workTimes(exist.worktimes.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                vm.mode('Update');
                            } else {
                                vm.$ajax(API.GET_WORKTIME_EMPLOYEE + '/' + ko.unwrap(vm.selectId) + '/' + ko.unwrap(vm.selectedYear))
                                    .done((data: IWorkTime[]) => {
                                        if (data.length > 0) {
                                            const data1: IWorkTime[] = [];
                                            vm.startDate(data[0].yearMonth);
                                            data.map(m => {

                                                var laborTime = 0;
                                                var check = true;

                                                if (!ko.unwrap(vm.newYearQ)) {
                                                    laborTime = m.laborTime == -1 ? null : m.laborTime;
                                                    check = m.laborTime == -1 ? false : true;
                                                }

                                                const s: IWorkTime = {
                                                    check: check,
                                                    yearMonth: m.yearMonth,
                                                    laborTime: laborTime
                                                };

                                                data1.push(s);
                                            });
                                            vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                                        }
                                        vm.mode('Update');
                                        vm.newYearQ(false);
                                    });
                            }
                        }
                        break;
                }
            } else {
                vm.initList();
            }
        }

        initList() {
            const vm = this
            var check: boolean = false;

            if (vm.type === 'Com_Person') {
                check = true;
            }
            const input = { workType: TYPE, year: 9998 };

            vm.$ajax(API.GET_WORK_TIME, input)
                .done((data: IWorkTime[]) => {
                    if (data.length > 0) {
                        const data1: IWorkTime[] = [];

                        if (ko.unwrap(vm.checkEmployee)) {
                            check = false;
                        }
                        data.map(m => {
                            const s: IWorkTime = { check: check, yearMonth: m.yearMonth, laborTime: null };
                            data1.push(s);
                        });

                        vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                        vm.mode('Update');
                        vm.total('');
                    }
                });
            ;
        }

        updateListSave() {
            const vm = this;
            const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSave) => m.year as number == ko.unwrap(vm.selectedYear) as number);

            if (exist) {
                _.remove(ko.unwrap(vm.workTimeSaves), ((value) => {
                    return value.year == ko.unwrap(vm.selectedYear) as number;
                }));

                let s: IWorkTime[] = [];

                _.map(ko.unwrap(vm.workTimes), ((value) => {
                    const t = { check: ko.unwrap(value.check), yearMonth: ko.unwrap(value.yearMonth), laborTime: ko.unwrap(value.laborTime) };
                    s.push(t);
                }));

                vm.workTimeSaves.push(new WorkTimeSave(ko.unwrap(vm.selectedYear), s));

            } else {
                let s: IWorkTime[] = [];

                _.map(ko.unwrap(vm.workTimes), ((value) => {
                    const t = { check: ko.unwrap(value.check), yearMonth: ko.unwrap(value.yearMonth), laborTime: ko.unwrap(value.laborTime) };
                    s.push(t);
                }));

                vm.workTimeSaves.push(new WorkTimeSave(ko.unwrap(vm.selectedYear) as number, s));
            }

            if (ko.unwrap(vm.years).length == 0) {
                vm.workTimeSaves([]);
            }
        }
    }
}

class WorkTimeSave {
    year: number;
    worktimes: IWorkTime[] = [];

    constructor(year: number, workTimes: IWorkTime[]) {
        this.year = year;
        this.worktimes = workTimes;
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
    laborTime: KnockoutObservable<number | null> = ko.observable(null);

    constructor(params?: IWorkTime & { parrent: KnockoutObservableArray<WorkTime> }) {
        const md = this;

        md.create(params);
        this.laborTime.subscribe(c => params.parrent.valueHasMutated());
        this.check.subscribe(c => params.parrent.valueHasMutated());
    }

    public create(param?: IWorkTime) {
        const md = this;
        md.check(param.check);
        md.yearMonth(param.yearMonth);

        if (param.check) {
            md.laborTime(param.laborTime);
        }

        if (!md.check) {
            md.laborTime(0);
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

    public updateLaborTime(value: number | null) {
        this.laborTime(value);
    }
}
