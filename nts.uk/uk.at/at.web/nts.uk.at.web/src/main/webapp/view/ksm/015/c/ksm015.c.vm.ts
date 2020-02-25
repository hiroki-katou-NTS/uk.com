module nts.uk.at.view.ksm015.c.viewmodel {
	export class ScreenModel {

		selectedWorkplaceId: KnockoutObservable<string>;
		baseDate: KnockoutObservable<Date>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		treeGrid: TreeComponentOption;
		shiftColumns: Array<any>;
		shiftItems: KnockoutObservableArray<ShiftMaster>;
		selectedShiftMaster: KnockoutObservableArray<any>;
		constructor() {
			let self = this;
			self.baseDate = ko.observable(new Date());
			self.selectedWorkplaceId = ko.observableArray("");
			self.selectedWorkplaceId.subscribe((val) => {
				if (val) {
					let param = {
						workplaceId: val,
						targetUnit: TargetUnit.WORKPLACE
					}
					service.getShiftMasterByWorkplace(param)
						.done((data) => {
							self.shiftItems(data);
							self.selectedShiftMaster([]);
						});
				}
			});
			self.alreadySettingList = ko.observableArray([]);
			self.treeGrid = {
				isShowAlreadySet: true,
				isMultipleUse: true,
				isMultiSelect: false,
				treeType: 1,
				selectedWorkplaceId: self.selectedWorkplaceId,
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
			$('#tree-grid').ntsTreeComponent(self.treeGrid);
		}

		startPage(): JQueryPromise<any> {
			let self = this;

			let dfd = $.Deferred();
			dfd.resolve(1);
			return dfd.promise();
		}

		public registerOrd() {
			let self = this;
			let param = {
				targetUnit: TargetUnit.WORKPLACE,
				workplaceId: self.selectedWorkplaceId(),
				shiftMasterCodes: _.map(self.shiftItems(), (val) => {return val.shiftMasterCode})
			};
			nts.uk.ui.block.grayout();
			service.registerOrg(param)
				.done(() => {
					self.selectedWorkplaceId.valueHasMutated();
				}).fail(function (error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function () {
					nts.uk.ui.block.clear();
				});
		}

		public clearShiftMaster () {
			let self = this;
			if(this.selectedShiftMaster().length > 0) {
				self.shiftItems( _.filter(self.shiftItems(), (val) => {return self.selectedShiftMaster().indexOf(val.shiftMasterCode) === -1 }));
				self.selectedShiftMaster([]);
			}
			
		}

		public openDialogKDL044(): void {
			let self = this;
			// set update data input open dialog kdl044
			nts.uk.ui.windows.setShared('kdl044Data', {
				isMultiSelect: true,
				filter: 0,
				permission: true,
				shifutoCodes: _.map(self.shiftItems(), (val) => {return val.shiftMasterCode})
			}, true);

			nts.uk.ui.windows.sub.modal('/view/kdl/044/a/index.xhtml').onClosed(function (): any {
				//view all code of selected item 
				let isCancel = nts.uk.ui.windows.getShared('kdl044_IsCancel');
				if(!isCancel) {
					let shiftItems = nts.uk.ui.windows.getShared('kdl044ShifutoData');
					let selectedShiftMaster = nts.uk.ui.windows.getShared('kdl044ShifutoCodes');

					let differentFromCurrents = _.differenceBy(self.shiftItems(), shiftItems, 'shiftMasterCode');

					self.shiftItems(self.shiftItems().push(differentFromCurrents));
					// self.selectedShiftMaster(selectedShiftMaster);
				}
			});
		}
	}
}