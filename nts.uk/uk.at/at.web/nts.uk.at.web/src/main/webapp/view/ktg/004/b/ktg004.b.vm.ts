module nts.uk.at.view.ktg004.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
	import ajax = nts.uk.request.ajax;
	import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
	import windows = nts.uk.ui.windows;
	
	const KTG004_API = {
		GET_APPROVED_DATA_EXCECUTION: 'screen/at/ktg004/getSetting',
		UPDATE_APPROVED_DATA_EXCECUTION: 'screen/at/ktg004/save',
	};
	
    export class ScreenModel {
        
        columns: KnockoutObservableArray<NtsGridListColumn>;
		name: KnockoutObservable<string> = ko.observable('');
		itemsSetting: KnockoutObservableArray<any> = ko.observableArray([]);
        
        constructor() {}

        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            ajax("at", KTG004_API.GET_APPROVED_DATA_EXCECUTION).done(function(data: any){
				self.name(data.name);
				let tg:any[] = []; 
				let tg1:any[] = []; 
				_.forEach(data.itemsSetting, function(x) {
					if(x.item <= 20 ){
						tg.push(new ItemsSetting(x));
					}else{
						tg1.push(new ItemsSetting(x));	
					}
				});
				let total = _.orderBy(tg1, ['item'], ['asc']);
				Array.prototype.push.apply(total, _.orderBy(tg, ['item'], ['asc']));
				self.itemsSetting(total);
				if(self.itemsSetting().length > 13){
					$("#scrollTable").addClass("scroll");
				}
				$(document).ready(function() {
	                $('#title-txt').focus();
	            });
				dfd.resolve();
            }).always(() => {
				block.clear();  
			});
            return dfd.promise();
        }

        submitAndCloseDialog() {
			if(nts.uk.ui.errors.hasError()){
                return;
            }else{
				var self = this;
				let param = {
					name: self.name(),
					itemsSetting: ko.toJS(self.itemsSetting)
				};
	            block.invisible();
	            ajax("at", KTG004_API.UPDATE_APPROVED_DATA_EXCECUTION, param).done(() => {
	            	windows.setShared("KTG004B", true);
					windows.close();
				}).always(() => {
					block.clear();  
				});
			}
		}

		closeDialog() {
			windows.setShared("KTG004B", false);
			windows.close();
		}
       
    }
    class ItemsSetting{
		// 項目
        item: number;
		// 特別休暇名称
        name: string;
		// 表示区分
		displayType : KnockoutObservable<boolean>;
        constructor(dto: any){
            this.item = dto.item;
            this.displayType = ko.observable(dto.displayType);
			switch(dto.item) { 
			   	case 21: { 
			      	this.name = getText('KTG004_1'); break; 
			   	} 
			   	case 22: { 
			      	this.name = getText('KTG004_2'); break; 
			   	}
				case 23: { 
			      	this.name = getText('KTG004_3'); break; 
			   	} 
			   	case 24: { 
			      	this.name = getText('KTG004_5'); break; 
			   	}
				case 25: { 
			      	this.name = getText('KTG004_6'); break; 
			   	} 
			   	case 26: { 
			      	this.name = getText('KTG004_7'); break; 
			   	}
				case 27: { 
			      	this.name = getText('KTG004_9'); break; 
			   	} 
			   	case 28: { 
			      	this.name = getText('KTG004_10'); break; 
			   	}
	    		case 29: { 
			      	this.name = getText('KTG004_11'); break; 
			   	} 
			   	case 30: { 
			      	this.name = getText('KTG004_12'); break; 
			   	}
				case 31: { 
			      	this.name = getText('KTG004_13'); break; 
			   	} 
			   	case 32: { 
			      	this.name = getText('KTG004_14'); break; 
			   	}
			   	default: { 
			      	this.name = dto.name;
			      	break; 
			   	} 
			} 
        }
    }
}
module nts.uk.at.view.ktg004.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
