/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.s {
    
	export module s {

		@bean()
		export class ViewModel extends ko.ViewModel {
            
            public model: UnitSetting = new UnitSetting();
            public managerUnit: KnockoutObservable<boolean> = ko.observable(true);
            public value: KnockoutObservable<boolean> = ko.observable(true);

			create() {
			}

			mounted() {
			}
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

        public create (param?: IUnitSetting){
            this.workPlace(param.workPlace);
            this.employment(param.employment);
            this.employee(param.employee);
        }
    }
}

