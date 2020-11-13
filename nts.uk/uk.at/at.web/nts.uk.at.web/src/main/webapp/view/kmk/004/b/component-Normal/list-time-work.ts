/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
    }

    const API = {
        GET_WORK_TIME: 'screen/at/kmk004/getWorkingHoursByCompany'
    };

    const TYPE = 0;

    const template = `
        <div class="list-time">
            <table>
                <tbody>
                    <!-- ko if: check -->
                    <tr>
                    </tr>
                    <!-- /ko -->
                    <tr>
                        <td>
                            <div class= "label-row1" data-bind="i18n: 'KMK004_221'"></div>
                        </td>
                        <td>
                            <div class= "label-row2" data-bind="i18n: 'KMK004_222'"></div>
                        </td>
                    </tr>
                    <!-- ko foreach: workTimes -->
                    <tr>
                        <td class="label-column1" data-bind="text: $data.month"></td>
                        <td>
                            <div class="label-column2">
                                <input class="lable-input" data-bind="ntsTextEditor: {value: $data.legalLaborTime}" />
                            </div>
                        </td>
                    </tr>
                    <!-- /ko -->
                    <tr>
                        <td>
                            <div class="label-column1" data-bind="i18n: 'KMK004_223'"></div>
                        </td>
                        <td>
                            <div class="label-column2" data-bind="text: total"></div>
                        </td>
                    </tr>
                    <!-- ko if: flex -->
                    <tr>
                    </tr>
                    <tr>
                    </tr>
                    <!-- /ko -->
                </tbody>
            </table>
        </div>
        <style type="text/css" rel="stylesheet">
		.list-time .label-column1 {
            padding: 3px;
            border-left: solid 1px #AAAAAA;
            border-right: solid 1px #AAAAAA;
            border-bottom: solid 1px #AAAAAA;
            text-align: center;
            width: 50px;
            background: #E0F59E;
            max-width: 50px;
            height: 24px;
        }
        .list-time .label-row1 {
            border: solid 1px #AAAAAA;
            padding: 3px;
            text-align: center;
            background: #97D155;
            max-width: 50px;
            height: 24px;
        }
        .list-time .label-column2 {
            padding: 3px;
            border-right: solid 1px #AAAAAA;
            border-bottom: solid 1px #AAAAAA;
            text-align: center;
            width: 125px;
            max-width: 125px;
            height: 24px;
        }
        .list-time .label-row2 {
            border-top: solid 1px #AAAAAA;
            border-right: solid 1px #AAAAAA;
            border-bottom: solid 1px #AAAAAA;
            padding: 3px;
            text-align: center;
            background: #97D155;
            max-width: 125px;
            height: 24px;
        }
        .list-time .lable-input {
            width: 60px;
            text-align: center;
            height: 13px;
        }
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

    @component({
        name: 'time-work',
        template
    })

    class ListTimeWork extends ko.ViewModel {

        public flex: KnockoutObservable<boolean> = ko.observable(true);
        public check: KnockoutObservable<boolean> = ko.observable(true);
        public workTimes: KnockoutObservableArray<WorkTime> = ko.observableArray([]);
        public total: KnockoutObservable<string> = ko.observable('');


        created() {
            const vm = this;
            const input = { workType: TYPE, year: 2020 };
            var s = 0;

            vm.$ajax(API.GET_WORK_TIME, input)
                .then((data: IWorkTime[]) => {
                    vm.workTimes(data.map(m => new WorkTime({...m, parrent: vm.workTimes})));
                });
            
            vm.workTimes.subscribe((wts) => {
                console.log(wts.reduce((p, c) => p+= Number(c.legalLaborTime()), 0));
            });
        }
    }

    interface IWorkTime {
        check: boolean;
        month: string;
        legalLaborTime: number;
        withinLaborTime: number;
        weekAvgTime: number;
    }

    class WorkTime {
        check: KnockoutObservable<boolean> = ko.observable(false);
        month: KnockoutObservable<string> = ko.observable('');
        legalLaborTime: KnockoutObservable<number | null> = ko.observable(null);
        withinLaborTime: KnockoutObservable<number | null> = ko.observable(null);
        weekAvgTime: KnockoutObservable<number | null> = ko.observable(null);

        constructor(params?: IWorkTime & { parrent: KnockoutObservableArray<WorkTime>}) {
            const md = this;

            md.create(params);

            this.legalLaborTime.subscribe(c => params.parrent.valueHasMutated());
        }

        public create(param?: IWorkTime) {
            this.check(param.check)
            this.month(param.month);
            this.legalLaborTime(param.legalLaborTime);
            this.withinLaborTime(param.withinLaborTime);
            this.weekAvgTime(param.weekAvgTime);
        }
    }
}
