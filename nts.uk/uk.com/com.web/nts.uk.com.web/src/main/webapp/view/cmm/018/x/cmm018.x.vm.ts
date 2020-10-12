module nts.uk.com.view.cmm018.x.viewmodel {
	@bean()
	export class Cmm018XViewModel extends ko.ViewModel {
		systemAtr: KnockoutObservable<number> = ko.observable(SystemAtr.EMPLOYMENT);
		created() {

		}
		mounted() {

		}

		openDialogQ() {
			console.log('openDialogQ');
			const self = this;
			let param = {
				systemAtr: ko.toJS(self.systemAtr)
			}
			self.$window
				.modal('com', '/view/cmm/018/q/index.xhtml', {})
				.then((result: any) => {
					// bussiness logic after modal closed
				});
		}
	}
	const SystemAtr = {
		EMPLOYMENT: 0,
		HUMAN_RESOURSE: 1
	}

}