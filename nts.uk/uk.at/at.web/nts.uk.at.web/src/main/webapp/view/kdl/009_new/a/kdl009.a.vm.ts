module nts.uk.at.view.kdl009.a {
    export module viewmodel {
        export class ScreenModel {
			listEmpId : any;
            constructor(data: any) {
				let self = this;
				self.listEmpId = nts.uk.ui.windows.getShared('KDL009_DATA');
				
			}

			public startPage(): JQueryPromise<any> {
				let self = this, dfd = $.Deferred<any>();
				service.getStartHolidayConf(self.listEmpId).done((data: any) => {
					let a = 1;
				});
				return dfd.promise();
			}

			cancel() {
				let self = this;
				nts.uk.ui.windows.close();
			}
		}
 	}
}