module nts.uk.at.view.ksm015.a.viewmodel {
	export class ScreenModel {
		
		constructor() {
			var self = this;
			
		}
		
		startPage(): JQueryPromise<any> {
			let self = this;
			
			let dfd = $.Deferred();
			dfd.resolve(1);
			return dfd.promise();
		}
	}
}