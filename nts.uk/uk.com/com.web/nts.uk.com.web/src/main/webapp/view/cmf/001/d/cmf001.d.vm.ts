module nts.uk.com.view.cmf001.d {
	import ajax = nts.uk.request.ajax;
	import close = nts.uk.ui.windows.close;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	import alertError = nts.uk.ui.dialog.alertError;
	
	export module viewmodel {
		@bean()
		export class ScreenModel {
			selectablItemList: KnockoutObservableArray<SelectableItem> = ko.observableArray([]);
			selectedItems: KnockoutObservableArray<string> = ko.observableArray([]);


			listColumns: KnockoutObservableArray<any> = ko.observableArray([
				{ headerText: "NO", 				key: "itemNo",		 		width: 50, hidden: true	},
				{ headerText: "名称", 			key: "itemName", 			width: 200 	},
				{ headerText: "項目型", 		key: "itemType", 			width: 75 	},
				{ headerText: "必須", 			key: "required", 			width: 25, hidden: true	},
			]);

			constructor() {
				console.log("ダイアログ起動した")
				let self = this;
				var params = getShared('CMF001DParams');
				console.log("パラメータ受け取った")
				self.selectablItemList = ko.observableArray<SelectableItem>([]);
				self.getSelectableItem(params.groupId);
				self.selectedItems(params.selectedItems ? params.selectedItems : []);

			}	

			getSelectableItem(groupId: string){
				let self = this;
				let dfd = $.Deferred<any>();
				ajax('com', "screen/com/cmf/cmf001/b/get/importableitem/" + groupId).done((lstData: Array<viewmodel.SelectableItem>) => {
					let sortedData = _.orderBy(lstData, ['itemNo'], ['asc']);
					self.selectablItemList(sortedData);
				});
				return dfd.promise();
			}

			decide(): void {
				let self = this;
				if(self.selectedItems().length == 0) {
					alertError({ messageId: "項目が選択されていません。" });
					return;
				}
				setShared('CMF001DOutput', self.selectedItems());
				close();
			}

			cancel(): void {
				setShared('CMF001DCancel', true);
				close();
			}
		}

		export class SelectableItem {
			itemNo: number;
			itemName: string;
			required: boolean;
			itemType: string;

			constructor(itemNo: number, itemName: string, itemType: string, required: boolean) {
					this.itemNo = itemNo;
					this.itemName = itemName;
					this.required = required;
					this.itemType = itemType;
			}
		}
	}
}