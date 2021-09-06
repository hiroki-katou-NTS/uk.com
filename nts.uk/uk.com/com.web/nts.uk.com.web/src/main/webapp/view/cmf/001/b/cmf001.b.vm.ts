/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.b.viewmodel {
	import ajax = nts.uk.request.ajax;
	import info = nts.uk.ui.dialog.info;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;

	let ICON_CONFIGURED = nts.uk.request.resolvePath(
		__viewContext.rootPath + "../" + (<any> nts).uk.request.WEB_APP_NAME.comjs
		 + "/lib/nittsu/ui/style/stylesheets/images/icons/numbered/78.png");

	function renderConfiguredIcon(configured) {
		if (configured === "true") {
				return '<div class="icon-configured" style="text-align: center;">'
						+ '<span id="icon-configured" style="'
						+ 'background: url(\'' + ICON_CONFIGURED + '\');'
						+ 'background-size: 20px 20px; width: 20px; height: 20px;'
						+ 'display: inline-block;"></span></div>';
		} else {
				return '';
		}
	}
	function deleteButton(deletable, data) {
		if (deletable === "true") {
				return '<button type="button" class="delete-button" data-target="'+ data.itemNo +'">delete</button>';
		} else {
				return '';
		}
	}

	$(function() {
		$("#layout-list").on("click",".delete-button",function(){
			let vm = nts.uk.ui._viewModel.content;
			console.log("さくじょ1"+ $(this).data("target"));
			vm.removeItem($(this).data("target"));
		});
	})

	@bean()
	class ViewModel extends ko.ViewModel {
		isNewMode: KnockoutObservable<boolean> = ko.observable(true);

		settingList: KnockoutObservableArray<Setting> = ko.observableArray([]);

		settingCode: KnockoutObservable<string> = ko.observable();
		settingName: KnockoutObservable<string> = ko.observable();
		importDomain: KnockoutObservable<number> = ko.observable();
		importMode: KnockoutObservable<number> = ko.observable();
		itemNameRow: KnockoutObservable<number> = ko.observable();
		importStartRow: KnockoutObservable<number> = ko.observable();
		layoutItemNoList: KnockoutObservableArray<number> = ko.observableArray([]);

		importDomainOption: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ImportingDomainId);
		importModeOption: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ImportingMode);

		selectedCode: KnockoutObservable<string> = ko.observable();

		layout: KnockoutObservableArray<Layout> = ko.observableArray([]);
		selectedItem: KnockoutObservable<string> = ko.observable();

		settingListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "コード", 				key: "code", 					width: 50 	},
			{ headerText: "名称", 					key: "name", 					width: 200 	},
			{ headerText: "", 							key: "configured", 		width: 25 	},
		]);

		layoutListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "削除", 					key: "deletable", 		width: 75 , 	formatter: deleteButton },
			{ headerText: "NO", 						key: "itemNo", 				width: 100 , 	hidden: true },
			{ headerText: "名称", 					key: "name", 					width: 200 		},
			{ headerText: "型", 						key: "type", 					width: 75 		},
			{ headerText: "受入元", 				key: "source", 				width: 50 		},
			{ headerText: "詳細設定", 			key: "alreadyDetail", width: 75 ,		formatter: renderConfiguredIcon },
		]);

		constructor() {
			super();
			var self = this;

			self.startPage();

			self.selectedCode.subscribe((value) => {
				if (value) {
					self.updateMode();
				} else {
					self.newMode();
				}
			})

			self.importDomain.subscribe((value) => {
				if (value) {
					let condition = {
						settingCode: self.settingCode(),
						importingDomainId: self.importDomain(),
						itemNoList: []};
					ajax("com", "screen/com/cmf/cmf001/b/get/layout", condition).done((itemNoList: number[]) => {
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
			ajax("screen/com/cmf/cmf001/b/get/setting/all").done((lstData: Array<viewmodel.Setting>) => {
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
			self.isNewMode(true);
			self.checkError();
		}

		updateMode(){
			let self = this;
			ajax("com", "screen/com/cmf/cmf001/b/get/setting/" + self.selectedCode()).done((infoData: viewmodel.SettingInfo) => {
				self.setInfo(infoData);
				self.isNewMode(false);
				self.checkError();
			});
		}

		checkError(){
			nts.uk.ui.errors.clearAll()
			$('.check-target').ntsError('check');
		}

		setInfo(info: SettingInfo){
			let self = this;
			self.settingCode(info.code);
			self.settingName(info.name);
			self.importDomain(info.domain);
			self.importMode(info.mode);
			self.itemNameRow(info.itemNameRow);
			self.importStartRow(info.importStartRow);
			// importDomainの変更にトリガして自動でセットされるため不要
			// self.layoutItemNoList(info.itemNoList);
		}

		setLayout(itemNoList: number[]){
			let self = this;
			if(itemNoList.length > 0){
				let condition = {
					settingCode: self.settingCode(),
					importingDomainId: self.importDomain(),
					itemNoList: itemNoList};
				ajax("screen/com/cmf/cmf001/b/get/layout/detail", condition).done((layoutItems: Array<viewmodel.Layout>) => {
					self.layout(layoutItems);
				});
			}else{
				self.layout([]);
			}
		}

		selectLayout() {
			let self = this;
			setShared('CMF001DParams', {
					domainId: self.importDomain(),
					selectedItems: self.layoutItemNoList()
			}, true);

			nts.uk.ui.windows.sub.modal("/view/cmf/001/d/index.xhtml").onClosed(function() {
				// ダイアログを閉じたときの処理
				if(!getShared('CMF001DCancel')){
					let ItemNoList: string[] = getShared('CMF001DOutput')
					console.log("closed: " + ItemNoList)
					ko.utils.arrayPushAll(self.layoutItemNoList, ItemNoList.map(n => Number(n)));
				}
			});
		}

		canSave = ko.computed(() => !nts.uk.ui.errors.hasError() );

		save(){
			let self = this;
			self.checkError();
			if(!nts.uk.ui.errors.hasError()){
				let saveContents = {
					createMode: self.isNewMode(),
					setting: new SettingInfo(
						__viewContext.user.companyId,
						self.settingCode(),
						self.settingName(),
						self.importDomain(),
						self.importMode(),
						self.itemNameRow(),
						self.importStartRow(),
						self.layoutItemNoList()),
				};
				ajax("screen/com/cmf/cmf001/b/save", saveContents);
				info(nts.uk.resource.getMessage("Msg_15", []));
				self.reloadPage();
				self.selectedCode(self.settingCode());
			}
		}

    canRemove = ko.computed(() => !util.isNullOrEmpty(this.selectedCode()));

		remove(){
			let self = this;
			let target = {code: self.selectedCode()};
			ajax("exio/input/setting/remove", target);
			info(nts.uk.resource.getMessage("Msg_16", []));
			self.reloadPage();
			self.selectedCode("");
		}

		gotoDetailSetting() {
			request.jump("../c/index.xhtml", { settingCode: this.settingCode() });
		}

		removeItem(target){
			let self = this;
			self.layoutItemNoList(self.layoutItemNoList().filter(function(itemNo){
				return itemNo !== target;
			}))
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
		domain: number;
		mode: number;
		itemNameRow: number;
		importStartRow: number;
		itemNoList: Array<number>;

		constructor(companyId: string, code: string, name: string, domain: number, mode: number, itemNameRow: number, importStartRow: number, itemNoList: Array<number>) {
			this.companyId = companyId;
			this.code = code;
			this.name = name;
			this.domain = domain;
			this.mode = mode;
			this.itemNameRow = itemNameRow;
			this.importStartRow = importStartRow;
			this.itemNoList = itemNoList;
		}

		static new(){
			return new SettingInfo(__viewContext.user.companyId, "", "", null, null, null, null, [])
		}
	}

	export class Layout {
		itemNo: number;
		name: string;
		required: boolean;
		deletable: boolean;
		type: string;
		source: string;
		alreadyDetail: boolean;

		constructor(itemNo: number,　name: string, required: boolean, deletable: boolean, type: string, source: string, alreadyDetail: boolean) {
			this.itemNo = itemNo;
			this.name = name;
			this.required = required;
			this.deletable = deletable;
			this.type = type;
			this.source = source;
			this.alreadyDetail = alreadyDetail;
		}
	}
}