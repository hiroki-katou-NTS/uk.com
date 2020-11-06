/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.s {

    const KMK004A_API = {
		GET_USAGE_UNIT_SETTING: 'screen/at/kmk004/getUsageUnitSetting',
	};

    @bean()
    export class ViewModel extends ko.ViewModel {

        public model: UnitSetting = new UnitSetting();
        public managerUnit: KnockoutObservable<boolean> = ko.observable(false);
        public value: KnockoutObservable<number| null> = ko.observable(null);
        public check: KnockoutObservable<number | null> = ko.observable(null);

        create() {
            const vm = this;

            

        }

        mounted() {
            const vm = this;

            vm.$blockui('invisible')
                .then(() => vm.$ajax(KMK004A_API.GET_USAGE_UNIT_SETTING))
                .then((data: IUnitSetting) => {
                    if (!data.employee && !data.workPlace) {
                        vm.managerUnit(false);
                    }

                    if (data.employment) {
                        vm.check(1);
                    }

                    if (data.workPlace) {
                        vm.check(2);
                    }

                    vm.model.employee(data.employee);
                    vm.model.employment(data.employment);
                    vm.model.workPlace(data.workPlace);
                })
                .then(() => vm.$blockui('clear'));
        }

        setting() {

        }

        closeDalog() {
            const vm = this;

            vm.$window.close();
        }
    }

    export interface IUnitSetting {
        workPlace: boolean;
        employment: boolean;
        employee: boolean;
    }

    export class UnitSetting {
        workPlace: KnockoutObservable<boolean> = ko.observable(false);
        employment: KnockoutObservable<boolean> = ko.observable(true);
        employee: KnockoutObservable<boolean> = ko.observable(false);

        public create(param?: IUnitSetting) {
            this.workPlace(param.workPlace);
            this.employment(param.employment);
            this.employee(param.employee);
        }
    }
}

