module nts.uk.com.view.cmm018.q.viewmodel {
	@bean()
	export class Cmm018QViewModel extends ko.ViewModel{
		param: PARAM;
	    created(params: any) {
	        // data transfer from parent view call modal
			const self = this;
			self.param = params;
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
	}
	
	const API = {
		getSetting: '',
		register: ''
	}
	class PARAM {
		public systemAtr: number;
	}
	
}