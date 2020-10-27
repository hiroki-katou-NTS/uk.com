module nts.uk.at.view.ktg004.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    export var STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";
    export class ScreenModel {
        
        title = ko.observable('あなたの承認状況'); 
        
        constructor() {
            var self = this;
        }

        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
//            new service.Service().getOptionalWidgetDisplay(topPagePartCode).done(function(data: any){
//            }); 
			dfd.resolve();
			block.clear();          
            return dfd.promise();
        }
        public setting() {
			let vm = this;
			nts.uk.ui.windows.sub.modal('at', '/view/ktg/004/b/index.xhtml').onClosed(() => {
				
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

