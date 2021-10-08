/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.f.viewmodel {
	import ajax = nts.uk.request.ajax;
	import info = nts.uk.ui.dialog.info;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	

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
		domainList: KnockoutObservableArray<ImportDomain> = ko.observableArray([]);
		layoutItemNoList: KnockoutObservableArray<number> = ko.observableArray([]);
		layout: KnockoutObservableArray<Layout> = ko.observableArray([]);

		importDomainOption: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ImportingDomainId);
		importDomain: KnockoutObservable<number> = ko.observable();
		selectedDomainId: KnockoutObservable<number> = ko.observable();
		
		//csv
		csvItemOption: KnockoutObservableArray<CsvItem> = ko.observableArray([]);
		selectedCsvItem:KnockoutObservableArray<number> = ko.observableArray([]);

		selectedItem: KnockoutObservable<string> = ko.observable();
		
	    $grid!: JQuery;
	    
		domainListColumns: KnockoutObservableArray<any> = ko.observableArray([
			{ headerText: "ID", 					key: "domainId", 		width: 50 , 	hidden: true },
			{ headerText: "受入ドメイン", 	key: "name", 			width: 280},
		]);

		constructor() {
			super();
			var self = this;
			
			var params = __viewContext.transferred.get();
			self.settingCode = params.settingCode;

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
			
			if (params.domainId !== undefined){
				self.selectedDomainId(params.domainId);
			}
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
			
			self.getListData().done(function() {
				if (self.settingList().length !== 0) {
					self.selectedCode(self.settingList()[0].code.toString());
				}
				dfd.resolve();
			});
			
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
		
		getListData() {
			var self = this;
			let dfd = $.Deferred();
			ajax("screen/com/cmf/cmf001/e/get/setting/" + self.settingCode)
			.done((res) => {
				let importDomains = $.map(res.domains, d => {
					let target = $.grep(__viewContext.enums.ImportingDomainId, (domain) => domain.value === d.domainId)[0];
					return new ImportDomain(d.domainId, target.name, true);
				});
				let importDomainInfoList = $.map(res.domains, d => {
					let target = $.grep(__viewContext.enums.ImportingDomainId, (domain) => domain.value === d.domainId)[0];
					return new DomainInfo(d.domainId, d.itemNoList);
				});
				self.domainList(importDomains);
				self.domainInfoList(importDomainInfoList);

				dfd.resolve();
			}).fail(function(error) {
				dfd.reject();
				alert(error.message);
			})
			return dfd.promise();
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
					{ headerText: "名称", 				key: "mappingAtr",		dataType: 'number',	width: 100, ntsControl: 'SwitchButtons'},
					{ headerText: "CSVヘッダ名", 	key: "selectedCsvItem",	dataType: 'number',	width: 250, ntsControl: 'Combobox' },
					{ headerText: "データ", 				key: "csvData", 				dataType: 'string',		width: 120	}
				],
		        features: [
		          {
		            name: 'Selection',
		            kay:{}self
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
		            enable: self.isDeletable()
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
		
		isDeletable() {
			return true;
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
				ajax("screen/com/cmf/cmf001/f/get/layout/detail", condition).done((layoutItems: Array<viewmodel.Layout>) => {
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
				let domains = $.map(self.layout(), l =>{
					return {
						itemNo: l.itemNo,
						isFixedValue: l.isFixedValue,
						csvItemNo: l.selectedCsvItem,
						fixedValue: l.fixedValue
					}
				});
				let saveContents = {
					code: self.settingCode,
					domainId: self.selectedDomainId(),
					items: domains
				};
				ajax("screen/com/cmf/cmf001/f/save", saveContents).done(() => {
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
			self.domainList.push(new ImportDomain(selected.value, selected.name, true ));
			
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

	        ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
				ajax("screen/com/cmf/cmf001/f/delete", {
					code: self.settingCode,
					domainId: self.selectedDomainId()
				}).done(() => {
					info(nts.uk.resource.getMessage("Msg_16", []));
					
					let index = self.domainInfoList().findIndex((di) => di.domainId === self.selectedDomainId());
					self.domainInfoList.splice(index, 1);
					self.domainList.splice(index, 1);
					self.selectedDomainId(null);
					
					self.reloadPage();
	            });
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

		gotoDetailSetting() {
			let self = this;
			request.jump("../c/index.xhtml", {
				settingCode: self.settingCode,
				domainId: self.self.selectedDomainId(),
				screenId: 'cmf001f'
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

	export class ImportDomain {
		domainId: string;
		name: string;
		deletable: boolean;
	
		constructor(domainId: string, name: string, deletable: boolean) {
				this.domainId = domainId;
				this.name = name;
				this.deletable = deletable;
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
		isFixedValue: boolean;
		selectedCsvItem: number;
		fixedValue: string;
		csvData: string;
	
		constructor(itemNo: number, name: string, required: boolean, isFixedValue: boolean, selectedCsvItem: number, fixedValue: string, csvData: string) {
			this.itemNo = itemNo;
			this.name = name;
			this.required = required;
			this.isFixedValue = isFixedValue;
			this.selectedCsvItem = selectedCsvItem;
			this.fixedValue = fixedValue;
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