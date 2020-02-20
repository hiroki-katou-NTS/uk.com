module nts.uk.at.view.ksm015.b.viewmodel {
	
	export class ScreenModel {
		
		searchOptions: ShiftGridOption;
		shiftRegistrationList: KnockoutObservableArray<any>;
		currentShiftRegistration: KnockoutObservable<any>;
		searchValue: KnockoutObservable<String>;
		registrationForm: KnockoutObservable<RegistrationForm>;
		
		constructor() {
			var self = this;
			self.searchOptions = new ShiftGridOption();
			self.shiftRegistrationList = ko.observableArray([]);
			self.currentShiftRegistration = ko.observable({});
			self.searchValue = ko.observable("");
			self.registrationForm = ko.observable(new RegistrationForm());
		}
		
		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			nts.uk.ui.block.grayout();
			service.startPage().done((data) => {
				dfd.resolve(data);
				console.log(data);
			}).fail(function(error) {
				nts.uk.ui.dialog.alertError({ messageId: error.messageId });
			}).always(function() {
				nts.uk.ui.block.clear();
			});
			
			return dfd.promise();
		}

		public createNew () {
			let self = this;
			self.registrationForm().newMode(true);
		}

		public register () {
			
		}
	}
}