module nts.uk.at.view.ksm015.d.viewmodel {

	import getShared = nts.uk.ui.windows.getShared;
	import setShared = nts.uk.ui.windows.setShared;
	import modal = nts.uk.ui.windows.sub.modal;
	import block = nts.uk.ui.block;
	import resource = nts.uk.resource;

	export class ScreenModel {
		options: Option;
		currentIds: KnockoutObservable<string> = ko.observable("");
		currentCodes: KnockoutObservable<any> = ko.observable([]);
		currentNames: KnockoutObservable<any> = ko.observable([]);
		workplaceGroupList: KnockoutObservable<any> = ko.observable([]);
		registerForm: RegisterForm = ko.observable(new RegisterForm());
		forAttendent: KnockoutObservable<Boolean>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		shiftItems: KnockoutObservableArray<ShiftMaster>;
		selectedShiftMaster: KnockoutObservableArray<any>;
		shiftColumns: Array<any>;
		isWorkplaceAlreadySetting: KnockoutObservable<Boolean>;
		baseDate: KnockoutObservable<Date>;
		constructor() {
			let self = this;
			let ksm015Data = new Ksm015Data();
			self.shiftColumns = ko.observableArray(ksm015Data.shiftGridColumnsD);
			self.shiftItems = ko.observableArray([]);
			self.selectedShiftMaster = ko.observableArray([]);
			self.alreadySettingList = ko.observableArray([]);
			self.isWorkplaceAlreadySetting = ko.observable(false);
			self.baseDate = ko.observable(new Date());
			self.options = {
				itemList: self.workplaceGroupList,
				currentCodes: self.currentCodes,
				currentNames: self.currentNames,
				currentIds: self.currentIds,
				multiple: false,
				baseDate: self.baseDate,
				tabindex: 2,
				alreadySettingList: self.alreadySettingList,
				isAlreadySetting: true,
				showEmptyItem: false,
				reloadData: ko.observable(''),
				height: 373,
				selectedMode: 1
			};

			self.currentIds.subscribe((val) => {
				let self = this;
				if (val) {
				let param = {
				workplaceGroupId: self.currentIds().length == 0 ? "" : self.currentIds(),
				targetUnit: TargetUnit.WORKPLACE_GROUP
				}
				if (param.workplaceGroupId != "") {
				service.getShiftMasterByWplGroup(param)
					.done((data) => {
						data = _.sortBy(data, 'shiftMasterCode');
						self.shiftItems(data);
						self.selectedShiftMaster([]);
						self.isWorkplaceAlreadySetting(data && data.length > 0);
					});
					setTimeout(function () {
						$("#register-btn-d").attr("disabled", false);
					}, 100);
				}}
				$('#cre-shift').focus();
				nts.uk.ui.errors.clearAll();
			});

			if (self.workplaceGroupList().length === 0) {
				self.createNew();
			}
			self.forAttendent = ko.observable(true);
		}

		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();

			let param = {
				workplaceGroupId: self.currentIds().length == 0 ? "" : self.currentIds(),
				targetUnit: TargetUnit.WORKPLACE_GROUP
			}
			if (param.workplaceGroupId != "") {
				service.getShiftMasterByWplGroup(param)
					.done((data) => {
						data = _.sortBy(data.shiftMastersDto, 'shiftMasterCode');
						self.shiftItems(data);
						self.selectedShiftMaster([]);
						self.isWorkplaceAlreadySetting(data.shiftMastersDto && data.shiftMastersDto.length > 0);
					});
			}
			self.getShiftMasterCode();

			return dfd.promise();
		}

		getShiftMasterCode(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			nts.uk.ui.block.invisible();
			service.startPages(TargetUnit.WORKPLACE_GROUP)
				.done((data) => {
					self.forAttendent(data.forAttendent);
					if (data.alreadyConfigWorkplaces) {
						let alreadySettings = []
						_.forEach(data.alreadyConfigWorkplaces, (wp) => {
							alreadySettings.push(wp);
						});
						self.alreadySettingList(alreadySettings);
					}

					dfd.resolve(data);
				}).fail(function(error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function() {
					nts.uk.ui.block.clear();
				});
			return dfd.promise();
		}

		public openDialogCopy(): void {
			let self = this,
				lwps = self.workplaceGroupList(),
				rstd = self.currentIds(),
				wkp = _.find(self.workplaceGroupList(), function(o) { return o.id == rstd; }),
				param = {
					targetType: 10,
					name: wkp ? wkp.name : '',
					code: wkp ? wkp.code : '',
					baseDate: ko.toJS(self.baseDate),
					itemListSetting: self.alreadySettingList()
				};

			// create object has data type IObjectDuplication and use:
			nts.uk.ui.windows.setShared("CDL023Input", param);

			// open dialog
			nts.uk.ui.windows.sub.modal('com', '/view/cdl/023/a/index.xhtml').onClosed(() => {
				// show data respond
				let lstSelection: any = nts.uk.ui.windows.getShared("CDL023Output");
				if (!nts.uk.util.isNullOrEmpty(lstSelection)) {
					let wkps = [];
					let data = [];
					lstSelection.forEach((wp) => {
						wkps.push({ targetUnit: TargetUnit.WORKPLACE_GROUP, workplaceGroupId: wp, shiftMasterCodes: [] });
					});
					let param = {
						targetUnit: TargetUnit.WORKPLACE_GROUP,
						workplaceGroupId: self.currentIds(),
						shiftMasterCodes: _.map(self.workplaceGroupList(), (val) => { return val.shiftMasterCode }),
						toWkps: wkps
					}
					let bundledErrors = [];
					service.copyOrg(param)
						.done((results) => {
							let msg = '';
							results.forEach((result) => {
								let dataWkp = _.find(lwps, wkp => wkp.id == result.workplaceId);
								if (dataWkp) {
									let status = result.status ? nts.uk.resource.getText('KSM015_26') : nts.uk.resource.getText('KSM015_27');
									msg += dataWkp.code + ' ' + dataWkp.name + ' ' + status + '<br>';
								}
								data.push({
									code: dataWkp.code + ' ' + dataWkp.name,
									status: status
								})
								
								bundledErrors.push({
	                            message: dataWkp.code + ' ' + dataWkp.name,
	                            messageId: status,
	                            supplements: {}
                        });
							});
							nts.uk.ui.windows.setShared("KSM_K_Input", data);
							nts.uk.ui.dialog.bundledErrors({ errors: bundledErrors });
							self.reloadAlreadySetting();
						});
				}
			});
		}

		public reloadAlreadySetting() {
			let self = this;
			service.getAlreadyConfigOrg(TargetUnit.WORKPLACE_GROUP)
				.done((data) => {
					let alreadySettings = []
					_.forEach(data.workplaceGrpIds, (wp) => {
						alreadySettings.push(wp);
					});
					self.alreadySettingList(alreadySettings);
				});
		}

		register() {
			let self = this;
			setTimeout(function() {
			if (nts.uk.ui.errors.hasError()) {
				$("#register-btn-d").attr("disabled", true);
			};
            }, 100);

			
			if (nts.uk.util.isNullOrEmpty(self.shiftItems())) {
				let KSM015_12 = nts.uk.resource.getText('KSM015_12');
				$('#register-btn-d').ntsError('set', nts.uk.resource.getMessage("MsgB_2", [KSM015_12]), "MsgB_2");
				return;
			};

			let param = {
				targetUnit: TargetUnit.WORKPLACE_GROUP,
				workplaceGroupId: self.currentIds(),
				shiftMasterCodes: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode })
			};

			nts.uk.ui.block.grayout();
			service.registerOrg(param)
				.done(() => {
					nts.uk.ui.dialog.info({ messageId: "Msg_15" });
					let isNew = _.findIndex(self.alreadySettingList(), (val) => { return val.workplaceGroupId === self.currentIds() }) === -1;
					if (isNew) {
						self.reloadAlreadySetting();
					}
					self.currentIds.valueHasMutated();
				}).fail(function(error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function() {
					$('#cre-shift').focus();
					$('#cre-shift').focus();
					nts.uk.ui.block.clear();
				});
		}

		public deleteShiftMaster() {
			nts.uk.ui.block.invisible();
			let self = this;
			
			let param = {
				targetUnit: TargetUnit.WORKPLACE_GROUP,
				workplaceGroupId: self.currentIds(),
				shiftMasterCodes: _.map(self.alreadySettingList(), (val) => { return val.shiftMasterCode })
			};
			nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
				.ifYes(() => {
					service.deleteOrg(param).done((data) => {
						nts.uk.ui.dialog.info({ messageId: "Msg_16" });
						self.currentIds.valueHasMutated();
						nts.uk.ui.block.clear();
						self.reloadAlreadySetting();
					}).fail((res) => {
						nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
					});
				}).ifNo(() => {
					nts.uk.ui.block.clear();
				});
		}

		removeShift() {
			let self = this;
			if (this.selectedShiftMaster().length > 0) {
				self.shiftItems(_.filter(self.shiftItems(), (val) => { return self.selectedShiftMaster().indexOf(val.shiftMasterCode) === -1 }));
				self.selectedShiftMaster([]);
			} else {
				//nts.uk.ui.dialog.info({ messageId: "Msg_85" });
			}
		}

		createNew() {
			let self = this;
			$('#requiredCode').focus();
			self.currentIds("");
		}

        /**
         * chose work place
         * @param baseDate, isMultiple, workplaceId
         * @return workplaceId
         */
		public openDialogKDL044(): void {
			let self = this;
			// set update data input open dialog kdl044
			nts.uk.ui.windows.setShared('kdl044Data', {
				isMultiSelect: true,
				filter: 0,
				permission: false,
				shiftCodeExpel: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode }),
				shifutoCodes: []
			}, true);

			nts.uk.ui.windows.sub.modal('/view/kdl/044/a/index.xhtml').onClosed(function(): any {
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
					if(!nts.uk.util.isNullOrEmpty(self.shiftItems())){
						setTimeout(function () {
								$("#register-btn-d").attr("disabled", false);
						}, 100);
					nts.uk.ui.errors.clearAll();	
					}
					
				}
			});
		}

		public reCalGridWidth() {
			let panelWidthResize = window.innerWidth - 650;
			panelWidthResize = panelWidthResize < 400 ? 400 : panelWidthResize;
			$('#workplace-list').igGrid("option", "width", panelWidthResize);
			$('#form-title-d').css("width", panelWidthResize + "px");
		}
	}
}