/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000_ref.a.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;

    export class Kaf000AViewModel extends ko.ViewModel {
    	appDispInfoStartupOutput: KnockoutObservable<any> = ko.observable(CommonProcess.initCommonSetting());
		
		component3ValidateEvent: () => void;
		component4ValidateEvent: () => void;
		component5ValidateEvent: () => void;
    	
        loadData(empLst: Array<string>, dateLst: Array<string>) {
            const vm = this;
            let command = { empLst, dateLst };
            return vm.$ajax(API.startNew, command)
            .then((data: any) => {
                vm.appDispInfoStartupOutput(data);
                return CommonProcess.checkUsage(true, "#kaf000-a-component4-singleDate", vm);
            },() => {
                return false;
            });
        }

		validateCommon() {
			const vm = this;
			return vm.component4Validate()
			.then((valid: boolean) => {
				if(valid) {
					return vm.component3Validate();
				}
			}).then((valid: boolean) => {
				if(valid) {
					return vm.component5Validate();
				}
			})
		}
		
		component3Validate() {
			const vm = this;
            // nếu component con có bind event ra
            if(_.isFunction(vm.component3ValidateEvent)) {
                return vm.component3ValidateEvent();
            }
		}
		
		getComponent3ValidEvent(evt: () => void) {
            const vm = this;
            // gán event update của component vào childUpdateEvent
            vm.component3ValidateEvent = evt;
        }
		
		component4Validate() {
			const vm = this;
            // nếu component con có bind event ra
            if(_.isFunction(vm.component4ValidateEvent)) {
                return vm.component4ValidateEvent();
            }
		}
		
		getComponent4ValidEvent(evt: () => void) {
            const vm = this;
            // gán event update của component vào childUpdateEvent
            vm.component4ValidateEvent = evt;
        }
		
		component5Validate() {
			const vm = this;
            // nếu component con có bind event ra
            if(_.isFunction(vm.component5ValidateEvent)) {
                return vm.component5ValidateEvent();
            }
		}
		
		getComponent5ValidEvent(evt: () => void) {
            const vm = this;
            // gán event update của component vào childUpdateEvent
            vm.component5ValidateEvent = evt;
        }
    }

    const API = {
        startNew: "at/request/application/getStartPC"
    }
}