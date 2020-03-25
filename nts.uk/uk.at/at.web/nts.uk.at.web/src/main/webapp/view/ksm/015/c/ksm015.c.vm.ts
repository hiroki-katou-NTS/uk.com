module nts.uk.at.view.ksm015.c.viewmodel {
	import flat = nts.uk.util.flatArray;

	export class ScreenModel {
		baseDate: KnockoutObservable<Date>;
		selectedWorkplaceId: KnockoutObservable<string>;
		baseDate: KnockoutObservable<Date>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		treeGrid: TreeComponentOption;
		shiftColumns: Array<any>;
		shiftItems: KnockoutObservableArray<ShiftMaster>;
		selectedShiftMaster: KnockoutObservableArray<any>;
		forAttendent: KnockoutObservable<Boolean>;
		workplaceName: KnockoutObservable<String>;
		isWorkplaceAlreadySetting: KnockoutObservable<Boolean>;

		constructor() {
			let self = this;
			self.forAttendent = ko.observable(true);
			self.baseDate = ko.observable(new Date());
			self.selectedWorkplaceId = ko.observableArray("");
			self.workplaceName = ko.observable('');

			self.alreadySettingList = ko.observableArray([]);
			self.treeGrid = {
				isShowAlreadySet: true,
				isMultipleUse: false,
				isMultiSelect: false,
				treeType: 1,
				selectedId: self.selectedWorkplaceId,
				baseDate: self.baseDate,
				selectType: 3,
				isShowSelectButton: true,
				isDialog: false,
				alreadySettingList: self.alreadySettingList,
				maxRows: 15,
				tabindex: 1,
				systemType: 2
			};

			let ksm015Data = new Ksm015Data();
			self.shiftItems = ko.observableArray([]);
			self.shiftColumns = ko.observableArray(ksm015Data.shiftGridColumns);
			self.selectedShiftMaster = ko.observableArray([]);
			self.isWorkplaceAlreadySetting = ko.observable(false);

			$('#tree-grid').ntsTreeComponent(self.treeGrid)
				.done(() => {
					self.selectedWorkplaceId.subscribe((val) => {
						if (val) {
							let lwps = $('#tree-grid').getDataList();
							let rstd = $('#tree-grid').getRowSelected();
							let flwps = flat(_.cloneDeep(lwps), "children");
							let wkp = _.find(flwps, wkp => wkp.id == _.head(rstd).id);
							self.workplaceName(wkp ? wkp.name : '');
							if (val) {
								let param = {
									workplaceId: val,
									targetUnit: TargetUnit.WORKPLACE
								}
								service.getShiftMasterByWorkplace(param)
									.done((data) => {
										data = _.sortBy(data, 'shiftMasterCode');
										self.shiftItems(data);
										self.selectedShiftMaster([]);
										self.isWorkplaceAlreadySetting(data && data.length > 0);
									});
							}
						}

					});
					self.selectedWorkplaceId.valueHasMutated();
				});
		}

		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			nts.uk.ui.block.invisible();
			service.startPage()
				.done((data) => {
					self.forAttendent(!_.isNull(data.forAttendent));
					if (data.alreadyConfigWorkplaces) {
						let alreadySettings = []
						_.forEach(data.alreadyConfigWorkplaces, (wp) => {
							alreadySettings.push({ workplaceId: wp, isAlreadySetting: true });
						});
						self.alreadySettingList(alreadySettings);
					}

					dfd.resolve(data);
				}).fail(function (error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function () {
					nts.uk.ui.block.clear();
				});
			return dfd.promise();
		}

		public registerOrd() {
			let self = this;

			let param = {
				targetUnit: TargetUnit.WORKPLACE,
				workplaceId: self.selectedWorkplaceId(),
				shiftMasterCodes: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode })
			};

			nts.uk.ui.block.grayout();
			service.registerOrg(param)
				.done(() => {
					nts.uk.ui.dialog.info({ messageId: "Msg_15" });
					let isNew = _.findIndex(self.alreadySettingList(), (val) => { return val.workplaceId === self.selectedWorkplaceId() }) === -1;
					if (isNew) {
						self.reloadAlreadySetting();
					}
					self.selectedWorkplaceId.valueHasMutated();
				}).fail(function (error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function () {
					nts.uk.ui.block.clear();
				});
		}

		public deleteShiftMaster() {
			nts.uk.ui.block.invisible();
			let self = this;
			let param = {
				targetUnit: TargetUnit.WORKPLACE,
				workplaceId: self.selectedWorkplaceId(),
				shiftMasterCodes: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode })
			};
			nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
				.ifYes(() => {
					service.deleteOrg(param).done((data) => {
						nts.uk.ui.dialog.info({ messageId: "Msg_16" });
						self.selectedWorkplaceId.valueHasMutated();
						nts.uk.ui.block.clear();
						self.reloadAlreadySetting();
					}).fail((res) => {
						nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function () { nts.uk.ui.block.clear(); });
					});
				}).ifNo(() => {
					nts.uk.ui.block.clear();
				});
		}

		public clearShiftMaster() {
			let self = this;
			if (this.selectedShiftMaster().length > 0) {
				self.shiftItems(_.filter(self.shiftItems(), (val) => { return self.selectedShiftMaster().indexOf(val.shiftMasterCode) === -1 }));
				self.selectedShiftMaster([]);
			} else {
				nts.uk.ui.dialog.info({ messageId: "Msg_85" });
			}
		}

		public openDialogKDL044(): void {
			let self = this;
			// set update data input open dialog kdl044
			nts.uk.ui.windows.setShared('kdl044Data', {
				isMultiSelect: true,
				filter: 0,
				permission: false,
				// shifutoCodes: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode })
				shifutoCodes: []
			}, true);

			nts.uk.ui.windows.sub.modal('/view/kdl/044/a/index.xhtml').onClosed(function (): any {
				//view all code of selected item 
				let isCancel = nts.uk.ui.windows.getShared('kdl044_IsCancel');
				if (!isCancel) {
					let shiftItems = nts.uk.ui.windows.getShared('kdl044ShifutoData');
					let selectedShiftMaster = nts.uk.ui.windows.getShared('kdl044ShifutoCodes');
					let currents = self.shiftItems();
					let differentFromCurrents = _.differenceWith(shiftItems, currents, (a, b) => { return a.shiftMasterCode === b.shiftMasterCode });
					currents = currents.concat(differentFromCurrents);
					currents = _.sortBy(currents, 'shiftMasterCode');
					self.shiftItems(currents);
				}
			});
		}

		/**
			* open dialog copy monthly pattern setting by on click button
			*/
		public openDialogCopy(): void {
			let self = this,
				lwps = $('#tree-grid').getDataList(),
				rstd = $('#tree-grid').getRowSelected(),
				flwps = flat(_.cloneDeep(lwps), "children"),
				wkp = _.find(flwps, wkp => wkp.id == _.head(rstd).id),
				param = {
					targetType: 4,
					name: wkp ? wkp.name : '',
					code: wkp ? wkp.code : '',
					baseDate: ko.toJS(self.baseDate),
					itemListSetting: _.map(self.alreadySettingList(), m => m.workplaceId)
				};

			// create object has data type IObjectDuplication and use:
			nts.uk.ui.windows.setShared("CDL023Input", param);

			// open dialog
			nts.uk.ui.windows.sub.modal('com', '/view/cdl/023/a/index.xhtml').onClosed(() => {
				// show data respond
				let lstSelection: any = nts.uk.ui.windows.getShared("CDL023Output");
				if (!nts.uk.util.isNullOrEmpty(lstSelection)) {
					let wkps = [];
					lstSelection.forEach((wp) => {
						wkps.push({ targetUnit: TargetUnit.WORKPLACE, workplaceId: wp, shiftMasterCodes: [] });
					});
					let param = {
						targetUnit: TargetUnit.WORKPLACE,
						workplaceId: self.selectedWorkplaceId(),
						shiftMasterCodes: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode }),
						toWkps: wkps
					}
					service.copyOrg(param)
						.done((results) => {
							let msg = '';
							results.forEach((result) => {
								let dataWkp = _.find(flwps, wkp => wkp.id == result.workplaceId);
								if(dataWkp) {
									let status = result.status ? nts.uk.resource.getText('KSM015_26') : nts.uk.resource.getText('KSM015_27');
									msg += dataWkp.code + ' ' + dataWkp.name + ' ' + status + '<br>';
								}
							});
							nts.uk.ui.dialog.info(msg);
							self.reloadAlreadySetting();
						});
				}
			});
		}

		public reloadAlreadySetting() {
			let self = this;
			service.getAlreadyConfigOrg()
				.done((data) => {
					let alreadySettings = []
					_.forEach(data.workplaceIds, (wp) => {
						alreadySettings.push({ workplaceId: wp, isAlreadySetting: true });
					});
					self.alreadySettingList(alreadySettings);
				});
		}

		public reCalGridWidth() {
			let panelWidthResize = window.innerWidth - 650;
			panelWidthResize = panelWidthResize < 400 ? 400 : panelWidthResize;
			$('#shift-list').igGrid("option", "width", panelWidthResize);
			$('#form-title').css("width", panelWidthResize + "px");
		}


	}
}