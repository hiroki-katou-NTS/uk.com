module nts.uk.at.view.ksu003.ab.viewmodel {
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	import formatById = nts.uk.time.format.byId;
	import alertError = nts.uk.ui.dialog.alertError;
	import getText = nts.uk.resource.getText;
	import characteristics = nts.uk.characteristics;
	import block = nts.uk.ui.block;

	export class ScreenModel {
		KEY: string = 'USER_KSU003_INFOR';
		dataFromScrA: any = [];
		localStore: KnockoutObservable<ksu003.a.model.ILocalStore> = ko.observable();

		// Screen Ab1
		width: KnockoutObservable<number>;
		tabIndex: KnockoutObservable<number | string>;
		filter: KnockoutObservable<boolean> = ko.observable(false);
		disabled: KnockoutObservable<boolean> = ko.observable(false);
		workplaceIdKCP013: KnockoutObservable<string> = ko.observable('');
		selected: KnockoutObservable<string> = ko.observable();
		dataSources: KnockoutObservableArray<any> = ko.observableArray([]);
		taskList: KnockoutObservableArray<any> = ko.observableArray([]);
		taskChecked: KnockoutObservable<string> = ko.observable('');
		taskSelect: KnockoutObservable<string> = ko.observable('');

		// Screen Ab2
		//list hyper link
		textButtonArr: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedButton: KnockoutObservable<any> = ko.observable({});
		sourceCompany: KnockoutObservableArray<any> = ko.observableArray([]);
		dataSourceCompany: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null]);
		hasDataButton: any = [];
		selectedPage: KnockoutObservable<number> = ko.observable(0);
		contextMenu: Array<any>;
		dataTaskPallet: any = [];
		dataTaskInfo: any = [];
		constructor(data: ksu003.a.model.ILocalStore) { //id : workplaceId || workplaceGroupId; 
			let self = this;
			self.localStore(data);
			if (!_.isNil(data) && !_.isNil(data.pageNo)) {
				self.selectedPage(data.pageNo);
			}
			if (_.isNil(data) || _.isNil(data.pageNo)) {
				self.selectedPage(0);
			};

			// Screen Ab1
			self.width = ko.observable(875);
			self.tabIndex = ko.observable('');
			// Screen Ab2
			let source: any[] = [{}, {}, {}, {}, {}];
			self.sourceCompany(source);
			self.contextMenu = [
				{ id: "openDialog", text: getText("作業選択"), action: "" }
			];
			
			$('#contain-view-right-ksu003').mousedown(function(event) {
				if (event.which == 3)
					setTimeout(function() {
						$('.ntsContextMenu').css("display", "none");
						$('.ntsContextMenu').css("margin-left", "30000px");
					}, 10);
			});
			
			self.selectedButton.subscribe((value) => {
				if (value.data == null || value.data == {})
					return;
					
				if (value.column == -1 || value.row == -1) {
					let param = {
						column: 0,
						data: {
							page: 6,
							text: value.data.text,
							tooltip: value.data.tooltip
						},
						row: 1
					}
					self.selectedButton(param);
				} else {
					self.getLocalStore();
					self.localStore().workPalletDetails = value;
					characteristics.save(self.KEY, self.localStore());
				}
				//$("#tableButton1").ntsButtonTable("setSelectedCell", value.row, value.column);
			});
		}
		
		public startPage(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			self.dataFromScrA = getShared("dataShareAbFromA");
			
			if (_.isNil(self.localStore())) {
				self.localStore(self.dataFromScrA.localStore);
				self.localStore().pageNo = self.selectedPage();
				self.localStore().work1Selection = self.taskChecked();
				self.localStore().workSelection = 0;
				characteristics.save(self.KEY, self.localStore());
			}
			
			self.getTaskInfo().done(() => {
				//$("#tableButton1").ntsButtonTable("setSelectedCell", 1, 0);
				self.getTaskPallet().done(() => {
					self.setDataTaskInfo();
					self.sourceCompany(self.dataSourceCompany()[self.selectedPage()]);
					
					if (self.sourceCompany() != null)
						$("#tableButton1").ntsButtonTable("init", { row: 2, column: 5, source: self.sourceCompany(), contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1], mode: "normal" });
					
					if (self.sourceCompany() != null)
						self.checkSelectButton(0);
						
					if (_.isNil(self.localStore().workPalletDetails)) {
						self.localStore().workPalletDetails = self.selectedButton();
					}
					
					self.taskChecked.subscribe((value) => {
						self.getLocalStore().done(() => {
							self.localStore().work1Selection = value;
							characteristics.save(self.KEY, self.localStore());
						});
					});
				});
			});
			return dfd.promise();
		}
		
		// ③<<ScreenQuery>>作業選択準備情報を取得する
		public getTaskInfo(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			let page = 1;
			if (!_.isNil(self.localStore())) {
				if (!_.isNil(self.localStore().pageNo))
					page = self.localStore().pageNo + 1;
			}
			let param = {
				baseDate: self.dataFromScrA.targetDate,
				targetUnit: self.dataFromScrA.dataScreen003A.unit,
				page: page,
				organizationID: self.dataFromScrA.dataScreen003A.id
			}
			service.getTaskInfo(param)
				.done((data: any) => {
					self.dataTaskInfo = data;
					dfd.resolve();
				}).fail(function(error) {
					alertError({ messageId: error.messageId });
					dfd.reject();
				}).always(function() {
				});
			return dfd.promise();
		}
		
		// ①<<Query>> 作業パレットを取得する
		public getTaskPallet(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			let param = {
				baseDate: self.dataFromScrA.targetDate,
				targetUnit: self.dataFromScrA.dataScreen003A.unit,
				page: self.selectedPage() + 1, // fake
				organizationID: self.dataFromScrA.dataScreen003A.id
			}
			
			service.getTaskPallet(param)
				.done((data: any) => {
					if (!_.isNil(data))
						self.hasDataButton.add(data.page);
						
					self.dataTaskPallet = data;
					dfd.resolve();
				}).fail(function(error) {
					alertError({ messageId: error.messageId });
					dfd.reject();
				}).always(function() {
				});
			return dfd.promise();
		}
		
		setDataTaskInfo() {
			let datas: any = [];
			let self = this, data = self.dataTaskInfo, pallet = self.dataTaskPallet;
			for (let i = 0; i < data.lstTaskDto.length; i++) {
				let taskData = new TaskModel(data.lstTaskDto[i].code, data.lstTaskDto[i].taskDisplayInfoDto.taskName, data.lstTaskDto[i].taskDisplayInfoDto.taskNote);
				datas.add(taskData);
			}
			datas = datas.sort();
			if (datas.length > 0)
				self.taskList(datas);
			else {
				self.taskList([
					new TaskModel('なし', '', '')
				]);
			}
			if (!_.isNil(self.localStore().work1Selection)) {
				self.taskChecked(self.localStore().work1Selection);
			}
			
			if (self.localStore().workSelection == 0 || _.isNil(self.localStore().workSelection)) {
				setTimeout(function() {
					$("#screen-Ab2").hide();
				}, 10);
				setTimeout(function() {
					$("#screen-Ab1").show();
				}, 10);
			} else if (!_.isNil(self.localStore().workSelection) && self.localStore().workSelection == 1) {
				setTimeout(function() {
					$("#screen-Ab1").hide();
				}, 10);
				setTimeout(function() {
					$("#screen-Ab2").show();
				}, 10);
			}
			
			self.setPalletButton();
			
			for (let i = 1; i <= 5; i++) {
				let dataPallet = data.workPaletteDisplayInforDto.lstTaskPaletteOrganizationDto;
				if (!_.isNil(dataPallet[i - 1])) {
					self.textButtonArr.push({ name: ko.observable(dataPallet[i - 1].taskPaletteDisplayInfoDto.taskPaletteName), id: dataPallet[i - 1].page, formatter: _.escape });
				} else {
					if (!_.includes(self.hasDataButton, i)) {
						self.textButtonArr.push({ name: ko.observable(getText("KSU003_84", [i])), id: i, formatter: _.escape });
					}
				}
			}
		}
		
		setPalletButton() {
			let self = this, pallet = self.dataTaskPallet;
			let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}], lstPallet: any = [];
			if (_.isNil(pallet)) {
				self.dataSourceCompany().splice(self.selectedPage(), 1, source);
			} else {
				let dataPallet = pallet.tasks;
				_.map(dataPallet, (x: any, y: any) => {
					lstPallet.push({ page: Number(y), name: x.taskName, abName: x.taskAbName, code: x.taskCode, status: x.taskStatus })
				})
				_.forEach(lstPallet, (tsk: any) => {
					let name = tsk.name, abName = tsk.abName;
					if (tsk.status == 1) {
						abName = name = getText("KSU003_70");
					}
					else if (tsk.status == 2) {
						abName = name = getText("KSU003_82");
						$($(window.parent[0].$('body').contents().find('.ntsButtonCell'))[tsk.page - 1]).css("border", "1px solid #ff9999")
					}
					
					source.splice(tsk.page - 1, 1, { text: name, tooltip: abName, page: tsk.page });
				});
				self.dataSourceCompany().splice(self.selectedPage(), 1, source);
			}
		}
		
		getLocalStore(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			characteristics.restore("USER_KSU003_INFOR").done(function(data: any) {
				self.localStore(data);
				dfd.resolve();
			})
			return dfd.promise();
		}
		
		checkSelectButton(type: number) {
			let self = this;
			let selectButtonChoice = _.filter(self.sourceCompany(), x => {
				return !_.isNil(x.page);
			});
				
				if (_.isNil(self.localStore().workPalletDetails) || type == 1) {
					if (!_.isEmpty(self.sourceCompany()[selectButtonChoice[0].page - 1])) {
						let value = self.getColumn(selectButtonChoice[0].page - 1);
							$("#tableButton1").ntsButtonTable("setSelectedCell", 
								selectButtonChoice[0].page > 5 ? 1 : 0, value.column);
						return;
					}
				} else {
					let value = {
						column: self.localStore().workPalletDetails.column,
						data: {
							page: self.localStore().workPalletDetails.page,
							text: self.localStore().workPalletDetails.text,
							tooltip: self.localStore().workPalletDetails.tooltip
						},
						row: self.localStore().workPalletDetails.row
					}
					if (type == 0) {
						self.selectedButton(value)
					}
					return;
				}
		}
		
		getColumn(i: number) {
			let self = this;
			let column = 0;
			if (self.sourceCompany()[i].page > 5) {
				column = self.sourceCompany()[i].page - 6;
			} else {
				column = self.sourceCompany()[i].page - 1;
			}
			let value = {
				column: column,
				data: {
					page: self.sourceCompany()[i].page,
					text: self.sourceCompany()[i].text,
					tooltip: self.sourceCompany()[i].tooltip
				},
				row: self.sourceCompany()[i].page > 5 ? 1 : 0
			}
			return value;
		}
		
		clickLinkButton(element: any, indexLinkBtn?: any): void {
			let self = this, soure: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
			self.selectedPage(indexLinkBtn());
			self.getTaskPallet().done(() => {
				self.setPalletButton();
				if (_.includes(self.hasDataButton, indexLinkBtn() + 1)) {
					self.sourceCompany(self.dataSourceCompany()[self.selectedPage()]);
				} else {
					self.sourceCompany(soure);
				}
				self.checkSelectButton(1);
			});
			
			self.getLocalStore().done(() => {
				self.localStore().pageNo = indexLinkBtn();
				characteristics.save(self.KEY, self.localStore());
			});
		}
		
		closeAb1() {
			let self = this;
			$("#screen-Ab1").hide();
			$("#screen-Ab2").show();
			self.getLocalStore().done(() => {
				self.localStore().workSelection = 1;
				characteristics.save(self.KEY, self.localStore());
			});
		}
		
		closeAb2() {
			let self = this;
			$("#screen-Ab2").hide();
			$("#screen-Ab1").show();
			self.getLocalStore().done(() => {
				self.localStore().workSelection = 0;
				characteristics.save(self.KEY, self.localStore());
			});
		}
		
		openC() {
			nts.uk.ui.windows.sub.modal('/view/ksu/003/c/index.xhtml').onClosed(() => {
			});
		}
		
		openB() {
			nts.uk.ui.windows.sub.modal('/view/ksu/003/b/index.xhtml').onClosed(() => {
			});
		}
	}
	export class TaskModel {
		code: string;
		name: string;
		des: string;
		constructor(code: string, name: string, des: string) {
			this.code = code;
			this.name = name;
			this.des = des;
		}
	}
}
