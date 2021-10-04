/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.y.viewmodel {
	import ajax = nts.uk.request.ajax;
	import info = nts.uk.ui.dialog.info;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;

	function deleteButton(required, data) {
		if (required === "false") {
				return '<button type="button" class="delete-button" data-target="'+ data.itemNo +'">削除</button>';
		} else {
				return '';
		}
	}
	
	function selectionCsvItem(csvHeaderName, data){
		return '<div class="check-target" data-bind="ntsComboBox: {'
		+ 'name: "csvヘッダ名",'
		+ 'options: csvItemOption,'
		+ 'optionsValue: "no",'
		+ 'visibleItemsCount: 10,'
		+ 'optionsText: "name",'
		+ 'selectFirstIfNull: false,'
		+ 'value: selectedCsvItem,'
		+ 'columns: ['
			+ '{ prop: "no",	length:4 },'
			+ '{ prop: "name", length:30},'
		+'],'
		+ 'required: true}"></div>';
	}

	$(function() {
		$("#layout-list").on("click",".delete-button",function(){
			let vm = nts.uk.ui._viewModel.content;
			vm.removeItem($(this).data("target"));
		});
	})

	@bean()
	class ViewModel extends ko.ViewModel {
        settingCode: string;
		
		itemNameRow: KnockoutObservable<number> = ko.observable();
		importStartRow: KnockoutObservable<number> = ko.observable();
		
		//domain
		domainInfoList:KnockoutObservableArray<DomainInfo> = ko.observableArray([]);
		domainList: KnockoutObservableArray<ImporDomain> = ko.observableArray([]);
		layoutItemNoList: KnockoutObservableArray<number> = ko.observableArray([]);
		layout: KnockoutObservableArray<Layout> = ko.observableArray([]);

		importDomainOption: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ImportingDomainId);
		importDomain: KnockoutObservable<number> = ko.observable();
		selectedDomainId: KnockoutObservable<number> = ko.observable();
		
		//csv
		csvItemOption: KnockoutObservableArray<CsvItem> = ko.observableArray([]);
		selectedCsvItem:KnockoutObservableArray<number> = ko.observableArray([]);
		//selectedCsvItem:KnockoutObservable<number> = ko.observable();

		selectedItem: KnockoutObservable<string> = ko.observable();
		
	    $grid!: JQuery;
	    
		domainListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "ID", 					key: "domainId", 		width: 50 , 	hidden: true },
			{ headerText: "受入ドメイン", 	key: "name", 			width: 280},
		]);

		layoutListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "削除", 				key: "required", 		width: 50 , 	formatter: deleteButton },
			{ headerText: "NO", 					key: "itemNo", 			width: 100 , 	hidden: true },
			{ headerText: "名称", 				key: "name", 			width: 250},
			{ headerText: "CSVヘッダ名", 	key: "csvHeaderName",	width: 250,	formatter: selectionCsvItem },
			{ headerText: "データ", 				key: "csvData", 				width: 120	},
		]);

		constructor() {
			super();
			var self = this;

			self.settingCode = __viewContext.transferred.get().settingCode;
            
			self.startPage();
			
			self.selectedDomainId.subscribe((value) => {
				if (value) {
					var info = $.grep(self.domainInfoList(), function (di) {
						return di.domainId == self.selectedDomainId();
					});
					self.setDomain(info[0]);
				}else{
					self.layoutItemNoList([]);
				}
			})
	
			self.layoutItemNoList.subscribe((value) => {
				self.setLayout(value);
			})
		}
		
		setDomain(info: DomainInfo) {
			let self = this;
			self.layoutItemNoList(info.itemNoList);
		}

		startPage(){
			var self = this;
			let dfd = $.Deferred();
			
			//todo
			self.csvItemOption=ko.observableArray([
	            new CsvItem('1', '項目１'),
	            new CsvItem('2', '項目２'),
	            new CsvItem('3', '項目３')
	        ]);
			self.$grid = $("#grid");
			self.initGrid();
		      
			return dfd.promise();
		}

		reloadPage(){
			var self = this;
			let dfd = $.Deferred();
			self.getListData().done(function() {
				dfd.resolve();
			});
		}
		
		initGrid(){
			var self = this;
			var comboColumns = [
				{ prop: "no",	length:4 },
				{ prop: "name", length:30}
			];
			if (self.$grid.data("igGrid")) {
				self.$grid.ntsGrid("destroy");
			}
			
			self.$grid.ntsGrid({
				height: '300px',
				dataSource: self.layout(),
		        primaryKey: 'itemNo',
		        rowVirtualization: true,
		        virtualization: true,
		        virtualizationMode: 'continuous',
		        columns: [
					{ headerText: "削除", 				key: "required", 			dataType: 'boolean',	width: 50, unbound:true, ntsControl: 'DeleteButton'},
					{ headerText: "NO", 					key: "itemNo", 				dataType: 'number',	width: 50, 	hidden: true },
					{ headerText: "名称", 				key: "name", 				dataType: 'string',		width: 250},
					{ headerText: "CSVヘッダ名", 	key: "selectedCsvItem",	dataType: 'number',	width: 250, ntsControl: 'Combobox' },
					{ headerText: "データ", 				key: "csvData", 				dataType: 'string',		width: 120	}
				],
		        features: [
		          {
		            name: 'Selection',
		            mode: 'row',
		            multipleSelection: false,
		            activation: false
		          },
		        ],
		        ntsControls: [
		          {
		            name: 'DeleteButton',
		            text: '削除',
		            controlType: 'DeleteButton',
		            enable: true
		          },
		          {
		            name: 'Combobox',
		            options: self.csvItemOption(),
		            optionsValue: 'no',
		            optionsText: 'name',
		            columns: comboColumns,
		            controlType: 'ComboBox',
		            visibleItemsCount: 5,
		            dropDownAttachedToBody: false,
		            enable: true
		          }
		        ]
		      });
		}

		checkError(){
			nts.uk.ui.errors.clearAll()
			$('.check-target').ntsError('check');
		}

		setLayout(itemNoList: number[]){
			let self = this;
			if(itemNoList.length > 0){
				let condition = {
					settingCode: self.settingCode,
					importingDomainId: self.selectedDomainId(),
					itemNoList: itemNoList};
				ajax("screen/com/cmf/cmf001/b/get/layout/detail", condition).done((layoutItems: Array<viewmodel.Layout>) => {
					self.layout(layoutItems);
					self.initGrid();
				});
			}else{
				self.layout([]);
			}
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
						self.settingCode,
						self.settingName(),
						self.importDomain(),
						self.itemNameRow(),
						self.importStartRow(),
						self.layoutItemNoList()),
					domains: self.domainInfoList()
				};
				ajax("screen/com/cmf/cmf001/y/save", saveContents).done(() => {
					info(nts.uk.resource.getMessage("Msg_15", []));
					self.reloadPage();
	            });
			}
		}
		
		uploadCsv() {
			let self = this;
		}
		
		addImportDomain() {
			let self = this;
			
			let index = self.domainInfoList().findIndex((di) => di.domainId === self.importDomain());
			if(index != -1){
				info("既に追加されています");
				return;
			}

			let selected = $.grep(__viewContext.enums.ImportingDomainId, (domain) => domain.value === self.importDomain())[0];
			self.domainList.push(new ImporDomain(selected.value, selected.name, true ));
			
			let condition = {
				settingCode: self.settingCode,
				importingDomainId: self.importDomain(),
				itemNoList: []};
			ajax("com", "screen/com/cmf/cmf001/b/get/layout", condition)
			.done((itemNoList: number[]) => {
				self.domainInfoList.push(new DomainInfo(self.importDomain(), itemNoList));
				self.importDomain(null);
			});
		}
		
		deleteImportDomain() {
			let self = this;
			let index = self.domainInfoList().findIndex((di) => di.domainId === self.selectedDomainId());
			self.domainInfoList.splice(index, 1);
			self.domainList.splice(index, 1);
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

		gotoDetailSetting() {
			request.jump("../c/index.xhtml", {
				settingCode: this.settingCode,
				domainId: this.importDomain()
			});
		}
	
		removeItem(target){
			let self = this;
			self.layoutItemNoList(self.layoutItemNoList().filter(function(itemNo){
				return itemNo !== target;
			}));
		}
	}

	export class Setting {
		code: string;
		name: string;
	
		constructor(code: string, name: string) {
				this.code = code;
				this.name = name;
		}
	}
	
	export class SettingInfo {
		companyId: string;
		code: string;
		name: string;
		itemNameRow: number;
		importStartRow: number;
	
		constructor(companyId: string, code: string, name: string, itemNameRow: number, importStartRow: number) {
			this.companyId = companyId;
			this.code = code;
			this.name = name;
			this.itemNameRow = itemNameRow;
			this.importStartRow = importStartRow;
		}
	
		static new(){
			return new SettingInfo(__viewContext.user.companyId, "", "", null, null, null)
		}
	}

	export class ImporDomain {
		domainId: string;
		name: string;
		deletable: boolean;
	
		constructor(domainId: string, name: string) {
				this.domainId = domainId;
				this.name = name;
				this.deletable = true;
		}
	}
	
	export class DomainInfo {
		domainId: number;
		itemNoList: Array<number>;
		
		constructor(domainId: number, itemNoList: Array<number>) {
			this.domainId = domainId;
			this.itemNoList = itemNoList;
		}
	
		static new(){
			return new DomainInfo(null, [])
		}
	}
	
	export class Layout {
		itemNo: number;
		name: string;
		required: boolean;
//		type: string;
//		source: string;
		selectedCsvItem: number;
		csvData: string;
	
		constructor(itemNo: number,　name: string, required: boolean, selectedCsvItem: number, csvData: string) {
			this.itemNo = itemNo;
			this.name = name;
			this.required = required;
			this.selectedCsvItem = selectedCsvItem;
			this.csvData = csvData;
		}
	}
	
	export class CsvItem {
	    no: number;
	    name: string;
	
	    constructor(no: number, name: string) {
	        this.no = no;
	        this.name = name;
	    }
	}
}