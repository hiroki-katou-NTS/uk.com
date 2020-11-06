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
		        

        constructor() {
            var self = this;
        }

        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            ajax("at", KTG004_API.GET_APPROVED_DATA_EXCECUTION).done(function(data: any){
				self.name(data.name);
				dfd.resolve();
            }).always(() => {
				block.clear();  
			});
            return dfd.promise();
        }
        public setting() {
			let self = this;
			nts.uk.ui.windows.sub.modal('at', '/view/ktg/004/b/index.xhtml').onClosed(() => {
				let data = nts.uk.ui.windows.getShared("KTG004B");
				if(data){
					self.startPage();
				}
			});
		}
    }
    export interface RemainingNumberDto{
        name: string;
        before: number;
        after: number;
        grantDate: string;
        showAfter: boolean;
    }
    export class RemainingNumber{
        name: string;
        before: number;
        after: number;
        grantDate: string;
        showAfter: boolean;   
        constructor(dto: RemainingNumberDto){
            this.name = dto.name;
            this.before = dto.before;
            this.after = dto.after;
            this.grantDate = dto.grantDate !=null ? moment(dto.grantDate,'YYYY/MM/DD').format('YY/MM/DD'): '';
            this.showAfter = dto.showAfter;
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
