/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.b.viewmodel {
	
	@bean()
	class ViewModel extends ko.ViewModel {
		settingList: KnockoutObservableArray<Setting>;  
		settingInfo: KnockoutObservable<SettingInfo>;


		
		importGroup: KnockoutObservableArray<any> = ko.observableArray([]);
		importMode: KnockoutObservableArray<any> = ko.observableArray([]);

		selectedSetting: KnockoutObservable<string> = ko.observable("001");
		selectedGroup: KnockoutObservable<number> = ko.observable(1);
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
			self.settingInfo = ko.observable(new SettingInfo);


			//self.settingInfo = ko.observable(new SettingInfo(ko.observable("001"), ko.observable("会社の受入"), ko.observable("1"), ko.observable("1"), ko.observable(1), ko.observable(2)));

			self.importGroup.push(new comboBoxItem("1", "会社情報"));
			self.importGroup.push(new comboBoxItem("2", "社員情報"));
			self.importGroup.push(new comboBoxItem("3", "所属情報"));
			self.importGroup.push(new comboBoxItem("4", "勤務情報"));

			self.importMode.push(new comboBoxItem("1", "おまかせモード"));
			self.importMode.push(new comboBoxItem("2", "既存データが存在しないデータのみ受け入れる"));
			self.importMode.push(new comboBoxItem("3", "既存データが存在するデータのみ受け入れる"));
			self.importMode.push(new comboBoxItem("4", "受入対象レコードのみ削除して受け入れる"));
			self.importMode.push(new comboBoxItem("5", "受入対象グループのデータをすべて削除して受け入れる"));
			
			self.layoutList.push(new Layout(1, "会社コード", "数値", "CSV", true));
			self.layoutList.push(new Layout(2, "会社名称", "文字型", "CSV", true));
			self.layoutList.push(new Layout(3, "会社所在地", "文字型", "CSV", true));
			self.layoutList.push(new Layout(4, "会社創業日", "日付型", "CSV", true));
			self.layoutList.push(new Layout(5, "会社責任者", "文字型", "CSV", true));
			self.layoutList.push(new Layout(6, "会社の存在意義", "文字型", "CSV", true));
			self.layoutList.push(new Layout(7, "ブラック会社区分", "文字型", "固定値", false));
		}

		mounted() {
			const vm = this;
		}

		getListData(){
			var self = this;
			let dfd = $.Deferred();
			nts.uk.request.ajax("exio/input/setting/find-all").done((lstData: Array<viewmodel.Setting>) => {
				let sortedData = _.orderBy(lstData, ['code'], ['asc']);
				self.settingList(sortedData);
				dfd.resolve();
			}).fail(function(error) {
				dfd.reject();
				alert(error.message);
		})
			return dfd.promise();
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

	class SettingInfo {
		code: KnockoutObservable<string>;
		name: KnockoutObservable<string>;
		group: KnockoutObservable<string>;
		mode: KnockoutObservable<string>;
		itemNameRow: KnockoutObservable<number>;
		importStartRow: KnockoutObservable<number>;

		constructor() {
			this.code = ko.observable("");
			this.name = ko.observable("");
			this.group = ko.observable("");
			this.mode = ko.observable("");
			this.itemNameRow = ko.observable(1);
			this.importStartRow = ko.observable(2);
		}
	}

	class comboBoxItem {
		code: string;
		name: string;

		constructor(code: string, name: string) {
				this.code = code;
				this.name = name;
		}
	}
	
	class Layout {
		itemNo: number;
		name: string;
		type: string;
		source: string;
		alreadyDetail: string;

		constructor(itemNo: number,　name: string, type: string, source: string, alreadyDetail: boolean) {
			this.itemNo = itemNo;
			this.name = name;
			this.type = type;
			this.source = source;
			this.alreadyDetail = alreadyDetail ? "あり" : "なし";
		}
	}
}