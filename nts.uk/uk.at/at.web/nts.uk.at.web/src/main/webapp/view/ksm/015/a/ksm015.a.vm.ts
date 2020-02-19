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

		public gotoKsm015b() {
			nts.uk.request.jump("/view/ksm/015/b/index.xhtml");
		}
		
		public gotoKsm015c() {
			nts.uk.request.jump("/view/ksm/015/c/index.xhtml");
		}
	}
}