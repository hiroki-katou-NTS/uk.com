/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.b.viewmodel {
	import ajax = nts.uk.request.ajax;
	import infor = nts.uk.ui.dialog.info;
	
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

		importGroupOption: KnockoutObservableArray<any> = ko.observableArray([]);
		importModeOption: KnockoutObservableArray<any> = ko.observableArray([]);

		selectedCode: KnockoutObservable<string> = ko.observable();
		selectedItem: KnockoutObservable<string> = ko.observable();
		
		layoutList: KnockoutObservableArray<Layout> = ko.observableArray([]);
		
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

			self.importGroupOption.push(new comboBoxItem(100, "会社情報"));
			self.importGroupOption.push(new comboBoxItem(200, "社員情報"));
			self.importGroupOption.push(new comboBoxItem(300, "所属情報"));
			self.importGroupOption.push(new comboBoxItem(400, "勤務情報"));

			self.importModeOption.push(new comboBoxItem("1", "おまかせモード"));
			self.importModeOption.push(new comboBoxItem("2", "既存データが存在しないデータのみ受け入れる"));
			self.importModeOption.push(new comboBoxItem("3", "既存データが存在するデータのみ受け入れる"));
			self.importModeOption.push(new comboBoxItem("4", "受入対象レコードのみ削除して受け入れる"));
			self.importModeOption.push(new comboBoxItem("5", "受入対象グループのデータをすべて削除して受け入れる"));

			self.startPage();
			self.selectedCode.subscribe((value) => {
				if (value) {
					self.updateMode();
					console.log(value + "を選択した")
				} else {
					self.newMode();
					console.log("選択解除した")
				}
			})
		}

		mounted() {
			const vm = this;
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
			ajax("exio/input/setting/find-all").done((lstData: Array<viewmodel.Setting>) => {
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
			ajax("com", "exio/input/setting/find/" + self.selectedCode()).done((infoData: viewmodel.SettingInfo) => {
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
			self.layoutList(info.layouts);
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
					self.layoutList()));
			ajax("exio/input/setting/save", saveContents);
			infor(nts.uk.resource.getMessage("Msg_15", []));
			self.reloadPage();
			self.selectedCode(self.settingCode());
		}

		remove(){
			let self = this;
			let target = new RemoveTarget(self.selectedCode());
			ajax("exio/input/setting/remove", target);
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
		layouts: Array<Layout>;

		constructor(companyId: string, code: string, name: string, group: number, mode: number, itemNameRow: number, importStartRow: number, layouts: Array<Layout>) {
			this.companyId = companyId;
			this.code = code;
			this.name = name;
			this.group = group;
			this.mode = mode;
			this.itemNameRow = itemNameRow;
			this.importStartRow = importStartRow;
			this.layouts = layouts;
		}
		
		static new(){
			return new SettingInfo(__viewContext.user.companyId, "", "", null, null, null, null, [])
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

	class comboBoxItem {
		code: any;
		name: string;

		constructor(code: any, name: string) {
				this.code = code;
				this.name = name;
		}
	}
	
	class Layout {
		itemNo: number;
		name: string;
		type: string;
		source: string;
		alreadyDetail: boolean;

		constructor(itemNo: number,　name: string, type: string, source: string, alreadyDetail: boolean) {
			this.itemNo = itemNo;
			this.name = name;
			this.type = type;
			this.source = source;
			this.alreadyDetail = alreadyDetail;
			//this.alreadyDetail = alreadyDetail ? "あり" : "なし";
		}
	}
}