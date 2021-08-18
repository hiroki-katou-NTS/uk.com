/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.b.viewmodel {
	import ajax = nts.uk.request.ajax;
	import info = nts.uk.ui.dialog.info;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	
	@bean()
	class ViewModel extends ko.ViewModel {
		isNewMode: boolean;

		settingList: KnockoutObservableArray<Setting>; 
		
		settingCode: KnockoutObservable<string> = ko.observable();
		settingName: KnockoutObservable<string> = ko.observable();
		importGroup: KnockoutObservable<number> = ko.observable();
		importMode: KnockoutObservable<number> = ko.observable();
		itemNameRow: KnockoutObservable<number> = ko.observable();
		importStartRow: KnockoutObservable<number> = ko.observable();
		layoutItemNoList: KnockoutObservableArray<number> = ko.observableArray([]);
		deletableItemNoList: KnockoutObservableArray<number> = ko.observableArray([]);

		importGroupOption: KnockoutObservableArray<any> = ko.observableArray([]);
		importModeOption: KnockoutObservableArray<any> = ko.observableArray([]);

		selectedCode: KnockoutObservable<string> = ko.observable();
		
		layout: KnockoutObservableArray<Layout> = ko.observableArray([]);
		selectedItem: KnockoutObservable<string> = ko.observable();

		settingListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "コード", 				key: "code", 					width: 50 	},
			{ headerText: "名称", 					key: "name", 					width: 200 	},
			{ headerText: "", 							key: "configured", 		width: 25 	},
		]);

		layoutListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "NO", 						key: "itemNo", 				width: 100 , hidden: true },
			{ headerText: "名称", 					key: "name", 					width: 200 		},
			{ headerText: "型", 						key: "type", 					width: 75 		},
			{ headerText: "受入元", 				key: "source", 				width: 50 		},
			{ headerText: "詳細設定の有無", key: "alreadyDetail", width: 120 		},
		]);

		constructor() {
			super();
			var self = this;

			self.settingList = ko.observableArray<Setting>([]);
			self.importGroupOption = ko.observableArray(__viewContext.enums.ImportingGroupId);
			self.importModeOption = ko.observableArray(__viewContext.enums.ImportingMode);

			self.startPage();

			self.selectedCode.subscribe((value) => {
				if (value) {
					self.updateMode();
				} else {
					self.newMode();
				}
			})

			self.importGroup.subscribe((value) => {
				if (value) {
					let condition = new LayoutGetCondition(self.settingCode(), self.importGroup(), []);
					ajax("com", "screen/com/cmf/cmf001/get/layout", condition).done((itemNoList: number[]) => {
						self.layoutItemNoList(itemNoList);
					});
				}else{
					self.layoutItemNoList([]);
				}
			})

			self.layoutItemNoList.subscribe((value) => {
				self.setLayout(value);
			})
		}

		startPage(){
			var self = this;
			let dfd = $.Deferred();
			self.getListData().done(function() {
				if (self.settingList().length !== 0) {
					self.selectedCode(self.settingList()[0].code.toString());
				}
				dfd.resolve();
			});
			return dfd.promise();
		}

		reloadPage(){
			var self = this;
			let dfd = $.Deferred();
			self.getListData().done(function() {
				dfd.resolve();
			});
		}

		getListData(){
			var self = this;
			let dfd = $.Deferred();
			ajax("screen/com/cmf/cmf001/get/setting/all").done((lstData: Array<viewmodel.Setting>) => {
				let sortedData = _.orderBy(lstData, ['code'], ['asc']);
				self.settingList(sortedData);
				dfd.resolve();
			}).fail(function(error) {
				dfd.reject();
				alert(error.message);
			})
			return dfd.promise();
		}

		newMode() {
			let self = this;
			self.selectedCode("");
			self.setInfo(SettingInfo.new());
			self.isNewMode = true;
		}

		updateMode(){
			let self = this;
			ajax("com", "screen/com/cmf/cmf001/get/setting/" + self.selectedCode()).done((infoData: viewmodel.SettingInfo) => {
				self.setInfo(infoData);
				self.isNewMode = false;
			});
		}

		setInfo(info: SettingInfo){
			let self = this;
			self.settingCode(info.code);
			self.settingName(info.name);
			self.importGroup(info.group);
			self.importMode(info.mode);
			self.itemNameRow(info.itemNameRow);
			self.importStartRow(info.importStartRow);
			self.layoutItemNoList(info.itemNoList);
			self.deletableItemNoList(info.itemNoList);
		}

		setLayout(itemNoList: number[]){
			let self = this;
			if(itemNoList.length > 0){
				let condition = new LayoutGetCondition(self.settingCode(), self.importGroup(), itemNoList);
				ajax("screen/com/cmf/cmf001/get/layout/detail", condition).done((layouItems: Array<viewmodel.Layout>) => {
					self.layout(layouItems);
				});
			}else{
				self.layout([]);
			}
		}



		selectLayout() {
			let self = this;
			setShared('CMF001DParams', {
					groupId: self.importGroup(),
					selectedItems: self.layoutItemNoList()
			}, true);

			nts.uk.ui.windows.sub.modal("/view/cmf/001/d/index.xhtml").onClosed(function() {
				// ダイアログを閉じたときの処理
				if(!getShared('CMF001DCancel')){
					let ItemNoList: string[] = getShared('CMF001DOutput')
					console.log("closed: " + ItemNoList)
					ItemNoList.map(n => Number(n)).forEach(n => {
						self.layoutItemNoList.push(n);
					});
				}
			});
		}

		save(){
			let self = this;
			let saveContents = new SaveContents(
				self.isNewMode, 
				new SettingInfo(
					__viewContext.user.companyId, 
					self.settingCode(), 
					self.settingName(), 
					self.importGroup(), 
					self.importMode(), 
					self.itemNameRow(), 
					self.importStartRow(), 
					self.layoutItemNoList()));
			ajax("screen/com/cmf/cmf001/save", saveContents);
			info(nts.uk.resource.getMessage("Msg_15", []));
			self.reloadPage();
			self.selectedCode(self.settingCode());
		}

		remove(){
			let self = this;
			let target = new RemoveTarget(self.selectedCode());
			ajax("exio/input/setting/remove", target);
			info(nts.uk.resource.getMessage("Msg_16", []));
			self.reloadPage();
			self.selectedCode("");
		}

		gotoDetailSetting() {
			request.jump("../c/index.xhtml", { settingCode: this.settingCode() });
		}
	}





	
	export class Setting {
		code: string;
		name: string;
		configured: string;

		constructor(code: string, name: string, configured: boolean) {
				this.code = code;
				this.name = name;
				this.configured = configured ? "✓" : "";
		}
	}
	
	export class SettingInfo {
		companyId: string;
		code: string;
		name: string;
		group: number;
		mode: number;
		itemNameRow: number;
		importStartRow: number;
		itemNoList: Array<number>;

		constructor(companyId: string, code: string, name: string, group: number, mode: number, itemNameRow: number, importStartRow: number, itemNoList: Array<number>) {
			this.companyId = companyId;
			this.code = code;
			this.name = name;
			this.group = group;
			this.mode = mode;
			this.itemNameRow = itemNameRow;
			this.importStartRow = importStartRow;
			this.itemNoList = itemNoList;
		}
		
		static new(){
			return new SettingInfo(__viewContext.user.companyId, "", "", null, null, null, null, [])
		}
	}

	class LayoutGetCondition{
		settingCode: string;
		importingGroupId: number;
		itemNoList: Array<number>;

		constructor(settingCode: string, importingGroupId: number, itemNoList: Array<number>) {
			this.settingCode = settingCode;
			this.importingGroupId = importingGroupId;
			this.itemNoList = itemNoList;
		}
	}

	class SaveContents{
		isCreateMode: boolean;
		setting: SettingInfo;

		constructor(isCreateMode: boolean, setting: SettingInfo) {
			this.isCreateMode = isCreateMode;
			this.setting = setting;
		}
	}
	
	class RemoveTarget{
		code: string;

		constructor(code: string) {
			this.code = code;
		}
	}
	
	export class Layout {
		itemNo: number;
		name: string;
		required: boolean;
		type: string;
		source: string;
		alreadyDetail: string;

		constructor(itemNo: number,　name: string, required: boolean, type: string, source: string, alreadyDetail: boolean) {
			this.itemNo = itemNo;
			this.name = name;
			this.required = required;
			this.type = type;
			this.source = source;
			this.alreadyDetail = alreadyDetail ? "✓" : "";
		}
	}
}