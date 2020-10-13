module nts.uk.com.view.cmm018.q.viewmodel {
	@bean()
	export class Cmm018QViewModel extends ko.ViewModel{
		param: PARAM;
	    created(params: any) {
	        // data transfer from parent view call modal
			const self = this;
			self.param = params;		
			self.dataFetch();
	    }
		mounted() {
			const self = this;
			console.log(self.param.systemAtr);
		}
		closeModal() {
		    const self = this;
		    self.$window.close({
		        // data return to parent
		    });
	    }
		
		register() {
			console.log('register');
		}
		
		
		dataFetch() {
			const self = this;
			self.$blockui("show");
			let startQCommand = {} as StartQCommand;
			startQCommand.companyId = self.$user.companyId;
			startQCommand.systemAtr = self.param.systemAtr;
			self.$ajax(API.getSetting, startQCommand)
				.done(res => {
					
				}).fail(res => {
					
				}).always(() => {
					self.$blockui("hide");
				})
			
		}
	}
	
	const API = {
		getSetting: 'workflow/approvermanagement/workroot/appSetQ',
		register: ''
	}
	class PARAM {
		public systemAtr: number;
	}
	class RegisterCommand {
		
	}
	class StartQCommand {
		
		public companyId: string;
		
		public systemAtr: number;
	}
	class CheckBoxModel {
		
		public companyUnit: KnockoutObservable<Boolean> = ko.observable(false);
		
		public workPlaceUnit: KnockoutObservable<Boolean> = ko.observable(false);
		
		public personUnit: KnockoutObservable<Boolean> = ko.observable(false);
	}
	
}