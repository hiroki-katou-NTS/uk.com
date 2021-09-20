/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.b {

    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {
        model: Model = new Model();
        roundingRules: KnockoutObservableArray<any>;
        datePeriod: any;

        constructor() {
            super();
            const vm = this;
            vm.model.employeeCode("11111111111");
            vm.model.employeeName("test name");
            vm.model.periodStart("2021/08/01");
            vm.model.periodEnd("2021/08/31");
            vm.roundingRules = ko.observableArray([
                {code: true, name: vm.$i18n('KSU002_54')},
                {code: false, name: vm.$i18n('KSU002_55')}
            ]);
            vm.datePeriod = ko.observable(vm.getDatePeriod());
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        getDatePeriod(): string {
            let vm = this;
            return vm.model.periodStart() + " " + vm.$i18n('KSU002_51') + " " + vm.model.periodEnd()
        }

        mounted() {
            const vm = this;
        }

        outPut() {
            const vm = this;
        }

        close() {
            let vm = this;
            vm.$window.close();
        }
    }

    class Model {
        /** 社員コード*/
        employeeCode: KnockoutObservable<string>;
        /** 社員名*/
        employeeName: KnockoutObservable<string>;
        /** 期間_開始         */
        periodStart: KnockoutObservable<string>;
        /** 期間_終了*/
        periodEnd: KnockoutObservable<string>;
        /**週合計判定*/
        isTotalDisplay: KnockoutObservable<boolean>;

        constructor() {
            this.employeeCode = ko.observable("");
            this.employeeName = ko.observable("");
            this.periodStart = ko.observable("");
            this.periodEnd = ko.observable("");
            this.isTotalDisplay = ko.observable(true);
        }
    }
}