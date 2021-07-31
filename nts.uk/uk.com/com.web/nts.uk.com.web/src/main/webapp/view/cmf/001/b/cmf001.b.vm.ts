/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.b.viewmodel {
	import ajax = nts.uk.request.ajax;
	
	@bean()
	class ViewModel extends ko.ViewModel {
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
		selectedGroup: KnockoutObservable<number> = ko.observable();
		selectedItem: KnockoutObservable<number> = ko.observable();
		
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
					self.updateMode(value);
				} else {
					self.newMode();
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
					if (self.settingList().length == 0) {
						self.newMode();
					}
					else {
						self.selectedCode(self.settingList()[0].code.toString());
						self.updateMode(self.settingList()[0].code.toString());
					}
					dfd.resolve();
			});
			return dfd.promise();
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
		}

		updateMode(code: string){
			let self = this;
			ajax("com", "exio/input/setting/find/" + code).done((infoData: viewmodel.SettingInfo) => {
				self.setInfo(infoData);
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
			// for (let val of info.layouts) {
			// 	self.layoutList.push(val)
			// }
			// for(let i = 0; i < info.layouts.length; i++){
			// 	self.layoutList.push(info.layouts[i])
			// }
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
		code: string;
		name: string;
		group: number;
		mode: number;
		itemNameRow: number;
		importStartRow: number;
		layouts: Array<Layout>;

		constructor(code: string, name: string, group: number, mode: number, itemNameRow: number, importStartRow: number, layouts: Array<Layout>) {
			this.code = code;
			this.name = name;
			this.group = group;
			this.mode = mode;
			this.itemNameRow = itemNameRow;
			this.importStartRow = importStartRow;
			this.layouts = layouts;
		}
		
		static new(){
			return new SettingInfo("", "", null, null, null, null, [])
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