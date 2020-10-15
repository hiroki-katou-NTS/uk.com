module nts.uk.at.view.ksu001.g.test {
	import setShare = nts.uk.ui.windows.setShared;
	// Import
	export module viewmodel {
		export class ScreenModel {
			currentScreen: any = null;
			dateValue: KnockoutObservable<any>;
			startDateString: KnockoutObservable<string>;
			endDateString: KnockoutObservable<string>;		
			enable: KnockoutObservable<boolean>;
			required: KnockoutObservable<boolean>;		
			constructor() {
				var self = this;
				self.enable = ko.observable(true);
				self.required = ko.observable(true);
				self.startDateString = ko.observable("2020/10/01");
				self.endDateString = ko.observable("2020/10/31");
				self.dateValue = ko.observable({});				

				self.startDateString.subscribe(function(value) {
					self.dateValue().startDate = value;
					self.dateValue.valueHasMutated();
				});

				self.endDateString.subscribe(function(value) {
					self.dateValue().endDate = value;
					self.dateValue.valueHasMutated();
				});
			
				

				
			}
			openDialog(): void {
				let self = this;
				let request: any = {};			

				
				// request.baseDate = moment(self.date()).format('YYYY/MM/DD');	
				// request.startDate = self.dateValue().startDate.format('YYYY/MM/DD');
				// request.endDate = self.dateValue().endDate.format('YYYY/MM/DD');
				setShare('dataShareDialogU', request);		
				self.currentScreen = nts.uk.ui.windows.sub.modal("/view/ksu/001/g/index.xhtml");
			}
			public startPage(): JQueryPromise<any> {
				let self = this,
				dfd = $.Deferred();

				dfd.resolve();
				return dfd.promise();
			}
		}
	}
}