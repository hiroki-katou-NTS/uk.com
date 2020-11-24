/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.f {

    interface IInput {
        type: string;
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        public swInsurrance: KnockoutObservableArray<ISwitch> = ko.observableArray([
            { code: '1', name: this.$i18n('KMK004_244') },
            { code: '0', name: this.$i18n('KMK004_245') }
        ]);

        public surcharges: ISwitch[] = [
            { code: '1', name: this.$i18n('KMK004_250') },
            { code: '0', name: this.$i18n('KMK004_251') }
        ];
        public valueInsurrance: KnockoutObservable<string> = ko.observable('1');
        public valueSurcharges: KnockoutObservable<string> = ko.observable('1');
        public checkCompany: KnockoutObservable<boolean> = ko.observable(true);

        public model: Model = new Model();
        public input: Input = new Input();

        create() {
            const vm = this;

            vm.init();
        }

        mounted() {
            const vm = this;
            vm.$window
                .storage('KMK004F')
                .then((data: IInput) => {
                    vm.input.update(data);
                });

            $(document).ready(function () {
                $('.input-forcus').focus();
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }

        init() {
            const vm = this;

            vm.model.regularWorkTimeAggrSet.aggregateTimeSet.surchargeWeekMonth.subscribe(() => {
                if (ko.unwrap(vm.model.regularWorkTimeAggrSet.aggregateTimeSet.surchargeWeekMonth)) {
                    vm.valueInsurrance('1');
                } else {
                    vm.valueInsurrance('0');
                }
            })

            vm.model.regularWorkTimeAggrSet.excessOutsideTimeSet.surchargeWeekMonth.subscribe(() => {
                if (ko.unwrap(vm.model.regularWorkTimeAggrSet.excessOutsideTimeSet.surchargeWeekMonth)) {
                    vm.valueSurcharges('1');
                } else {
                    vm.valueSurcharges('0');
                }
            })
        }
    }

    class Input{
        type: KnockoutObservable<string> = ko.observable('Com_Company');

        constructor(param?: IInput) {
            const md = this;
            if (param) {
                md.update(param);
            }
        }

        update(param: IInput) {
            const md = this;
            md.type(param.type);
        }
    }
}

interface ISwitch {
    code: string;
    name: string;
}

interface IWorkingTimeSetting {
    dailyTime: number;
    weeklyTime: number;
}

class WorkingTimeSetting {
    dailyTime: KnockoutObservable<number> = ko.observable(0);
    weeklyTime: KnockoutObservable<number> = ko.observable(0);

    constructor(params?: IWorkingTimeSetting) {
        const md = this;

        if (params) {
            md.dailyTime(params.dailyTime);
            md.weeklyTime(params.weeklyTime);
        }
    }
}

interface IExcessOutsideTimeSetReg {
    legalOverTimeWork: boolean;
    legalHoliday: boolean;
    surchargeWeekMonth: boolean;
    exceptLegalHdwk: boolean;
}

class ExcessOutsideTimeSetReg {
    legalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);
    legalHoliday: KnockoutObservable<boolean> = ko.observable(true);
    surchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
    exceptLegalHdwk: KnockoutObservable<boolean> = ko.observable(true);

    constructor(params?: IExcessOutsideTimeSetReg) {
        const md = this;

        if (params) {
            md.legalOverTimeWork(params.legalOverTimeWork);
            md.legalHoliday(params.legalHoliday);
            md.surchargeWeekMonth(params.surchargeWeekMonth);
            md.exceptLegalHdwk(params.exceptLegalHdwk);
        }
    }
}

interface IRegularWorkTimeAggrSet {
    aggregateTimeSet: IExcessOutsideTimeSetReg;
    excessOutsideTimeSet: IExcessOutsideTimeSetReg;
}

class RegularWorkTimeAggrSet {
    aggregateTimeSet: ExcessOutsideTimeSetReg = new ExcessOutsideTimeSetReg();
    excessOutsideTimeSet: ExcessOutsideTimeSetReg = new ExcessOutsideTimeSetReg();

    constructor(params?: IRegularWorkTimeAggrSet) {
        const md = this;

        if (params) {
            md.aggregateTimeSet = new ExcessOutsideTimeSetReg(params.aggregateTimeSet);
            md.excessOutsideTimeSet = new ExcessOutsideTimeSetReg(params.excessOutsideTimeSet);
        }
    }
}

interface IModel {
    workingTimeSetting: IWorkingTimeSetting;
    regularWorkTimeAggrSet: IRegularWorkTimeAggrSet;
}

class Model {
    workingTimeSetting: WorkingTimeSetting = new WorkingTimeSetting();
    regularWorkTimeAggrSet: RegularWorkTimeAggrSet = new RegularWorkTimeAggrSet();

    constructor(params?: IModel) {
        const md = this;

        if (params) {
            md.workingTimeSetting = new WorkingTimeSetting(params.workingTimeSetting);
            md.regularWorkTimeAggrSet = new RegularWorkTimeAggrSet(params.regularWorkTimeAggrSet);
        }
    }
}
