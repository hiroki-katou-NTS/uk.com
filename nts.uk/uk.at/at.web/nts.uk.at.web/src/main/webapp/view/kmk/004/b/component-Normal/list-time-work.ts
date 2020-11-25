/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        change: KnockoutObservable<boolean>;
        checkEmployee: KnockoutObservable<boolean>
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
                                        value: $data.legalLaborTime,
                                        enable: $data.check,
                                        inputFormat: 'time',
                                        option: {
                                            width: '60px',
                                            textalign: 'center'}, 
                                        mode: 'time'}" />
                            <!-- /ko -->
                            <!-- ko ifnot: $parent.checkEmployee -->
                                <input class="lable-input" 
                                    data-bind="ntsTimeEditor: {
                                        value: $data.legalLaborTime,
                                        enable: $parent.ckeckNullYear, 
                                        inputFormat: 'time',
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
        public change: KnockoutObservable<boolean> = ko.observable(true);
        public ckeckNullYear: KnockoutObservable<boolean> = ko.observable(false);
        public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);

        created(params: Params) {
            const vm = this;
            vm.selectedYear = params.selectedYear;
            vm.change = params.change;
            vm.checkEmployee = params.checkEmployee;

            vm.initList();
            vm.reloadData();

            vm.workTimes.subscribe((wts) => {
                const total: number = wts.reduce((p, c) => p += Number(c.legalLaborTime()), 0);
                const finst: string = Math.floor(total / 60) + '';
                var last: string = total % 60 + '';

                if (last.length < 2){
                    last = '0' + last;
                }
                
                vm.total(finst + ':' + last);

            });

            vm.selectedYear
                .subscribe(() => {
                    vm.reloadData();
                });

            vm.total
                .subscribe(() => {
                    vm.change.valueHasMutated();
                });
        }

        reloadData() {
            const vm = this;
            const input = { workType: TYPE, year: ko.unwrap(vm.selectedYear) };

            if (ko.unwrap(vm.selectedYear) != null) {
                vm.ckeckNullYear(true);
            }

            vm.$ajax(API.GET_WORK_TIME, input)
                .then((data: IWorkTime[]) => {
                    if (data.length > 0) {
                        const data1: IWorkTime[] = [];
                        var check: boolean = true;

                        if (ko.unwrap(vm.checkEmployee)) {
                            check = false;
                        }
                        data.map(m => {
                            const laborTime: ILaborTime = {
                                legalLaborTime: m.laborTime.legalLaborTime,
                                withinLaborTime: m.laborTime.weekAvgTime,
                                weekAvgTime: m.laborTime.weekAvgTime
                            };
                            const s: IWorkTime = { check: check, yearMonth: m.yearMonth, laborTime: laborTime };
                            data1.push(s);
                        });

                        vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                    }
                });
        }

        reloadList() {
            const vm = this;
            const list: IWorkTime[] = [];

            // ko.unwrap(vm.workTimes).map(m => {
            //     const legalLaborTime: number = parseInt(ko.unwrap(m.legalLaborTime).replace(':', ''), 10);
            //     const laborTime: ILaborTime = {
            //         legalLaborTime: legalLaborTime,
            //         withinLaborTime: null,
            //         weekAvgTime: null
            //     }
            //     const a: IWorkTime = {
            //         check: ko.unwrap(m.check),
            //         yearMonth: ko.unwrap(m.yearMonth),
            //         laborTime: laborTime
            //     }
            //     list.push(a);
            // });

            // vm.workTimes(list.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
        }

        initList() {
            const vm = this,
                laborTime: ILaborTime = {
                    legalLaborTime: null,
                    withinLaborTime: null,
                    weekAvgTime: null
                };
            var check: boolean = true;

            if (ko.unwrap(vm.checkEmployee)) {
                check = false;
            }

            const IWorkTime1: IWorkTime[] = [{ check: check, yearMonth: 202001, laborTime: laborTime },
            { check: check, yearMonth: 202002, laborTime: laborTime },
            { check: check, yearMonth: 202003, laborTime: laborTime },
            { check: check, yearMonth: 202004, laborTime: laborTime },
            { check: check, yearMonth: 202005, laborTime: laborTime },
            { check: check, yearMonth: 202006, laborTime: laborTime },
            { check: check, yearMonth: 202007, laborTime: laborTime },
            { check: check, yearMonth: 202008, laborTime: laborTime },
            { check: check, yearMonth: 202009, laborTime: laborTime },
            { check: check, yearMonth: 202010, laborTime: laborTime },
            { check: check, yearMonth: 202011, laborTime: laborTime },
            { check: check, yearMonth: 202012, laborTime: laborTime }];
            vm.workTimes(IWorkTime1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
        }
    }
}

interface IWorkTime {
    check: boolean;
    yearMonth: number;
    laborTime: ILaborTime;
}

interface ILaborTime {
    legalLaborTime: number;
    withinLaborTime: number;
    weekAvgTime: number;
}

class WorkTime {
    check: KnockoutObservable<boolean> = ko.observable(false);
    yearMonth: KnockoutObservable<number | null> = ko.observable(null);
    nameMonth: KnockoutObservable<string> = ko.observable('');
    legalLaborTime: KnockoutObservable<number> = ko.observable(0);

    constructor(params?: IWorkTime & { parrent: KnockoutObservableArray<WorkTime> }) {
        const md = this;

        md.create(params);
        this.legalLaborTime.subscribe(c => params.parrent.valueHasMutated());
    }

    public create(param?: IWorkTime) {
        const md = this;
        md.check(param.check);
        md.yearMonth(param.yearMonth);

        if (param.check) {
            // var firstLegalLabor = '0';
            // var lastLegalLabor = param.laborTime.legalLaborTime % 60 + '';

            // firstLegalLabor = Math.floor(param.laborTime.legalLaborTime / 60) + '';

            // if (lastLegalLabor.length < 2) {
            //     lastLegalLabor = '0' + lastLegalLabor;
            // }

            md.legalLaborTime(param.laborTime.legalLaborTime);
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
