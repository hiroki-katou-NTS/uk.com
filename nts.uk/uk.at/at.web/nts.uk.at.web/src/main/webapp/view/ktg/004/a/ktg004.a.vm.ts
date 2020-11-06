module nts.uk.at.view.ktg004.a.viewmodel {
    import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    export var STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";

	const KTG004_API = {
		GET_APPROVED_DATA_EXCECUTION: 'screen/at/ktg004/getSetting'
	};
    export class ScreenModel {
        name = ko.observable(''); 
		itemsSetting: KnockoutObservableArray<any> = ko.observableArray([]);
		        
        constructor() {
            var self = this;
        }

        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            $.when(self.getSetting()).done(function() {
				block.clear();
				dfd.resolve();
            });
            return dfd.promise();
        }
		private getSetting(): JQueryPromise<any> {
			var self = this;
            var dfd = $.Deferred();
            block.invisible();
            ajax("at", KTG004_API.GET_APPROVED_DATA_EXCECUTION).done(function(data: any){
				self.name(data.name);
				self.itemsSetting(data.itemsSetting);
				dfd.resolve();
            }).always(() => {
			});
            return dfd.promise();
		}
        public setting() {
			let self = this;
			nts.uk.ui.windows.sub.modal('at', '/view/ktg/004/b/index.xhtml').onClosed(() => {
				let data = nts.uk.ui.windows.getShared("KTG004B");
				if(data){
					self.getSetting().done(() => {
						block.clear();
					});
				}
			});
		}
    }
    
}
module nts.uk.at.view.ktg004.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
