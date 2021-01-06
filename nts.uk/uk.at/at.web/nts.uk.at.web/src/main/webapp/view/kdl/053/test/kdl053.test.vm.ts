module nts.uk.at.view.kdl053.test {
	import setShare = nts.uk.ui.windows.setShared;
	// Import
	export module viewmodel {
		export class ScreenModel {	
            currentScreen: any = null;		
			constructor() {
				var self = this;
			}
			openDialog(): void {
				let self = this;
				let request: any = {};				
				self.currentScreen = nts.uk.ui.windows.sub.modal("/view/kdl/053/index.xhtml");
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