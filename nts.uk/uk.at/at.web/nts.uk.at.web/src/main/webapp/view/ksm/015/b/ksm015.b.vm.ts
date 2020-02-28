module nts.uk.at.view.ksm015.b.viewmodel {

	export class ScreenModel {

		searchOptions: ShiftGridOption;
		shiftMasters: KnockoutObservableArray<any>;
		selectedShiftMaster: KnockoutObservable<any>;
		searchValue: KnockoutObservable<String>;
		registrationForm: KnockoutObservable<RegistrationForm>;

		constructor() {
			var self = this;
			self.searchOptions = new ShiftGridOption();
			self.shiftMasters = ko.observableArray([]);
			self.selectedShiftMaster = ko.observable({});
			self.searchValue = ko.observable("");
			self.registrationForm = ko.observable(new RegistrationForm());
			self.selectedShiftMaster.subscribe((value) => {
				nts.uk.ui.errors.clearAll();
				self.bindShiftMasterInfoToForm(value);
			});
		}

		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			nts.uk.ui.block.grayout();
			service.startPage().done((data) => {
				self.shiftMasters(data.shiftMasters);
				if (data.shiftMasters && data.shiftMasters.length > 0) {
					self.selectedShiftMaster(data.shiftMasters[0].shiftMasterCode);
				} else {
					self.registrationForm().newMode(true);
				}

			}).fail(function (error) {
				nts.uk.ui.dialog.alertError({ messageId: error.messageId });
			}).always(function () {
				nts.uk.ui.block.clear();
			});

			return dfd.promise();
		}

		public createNew() {
			let self = this;
			nts.uk.ui.errors.clearAll();
			self.registrationForm().clearData();
		}

		public bindShiftMasterInfoToForm(code: String) {
			let self = this;
			let selectedSm = _.findLast(self.shiftMasters(), (val) => {
				return val.shiftMasterCode === code;
			});
			self.registrationForm().bindData(selectedSm);
		}

		public register() {

			$(".nts-input").trigger("validate");
			if (nts.uk.ui.errors.hasError()){
				return;
			}

			let self = this;
			let param = new RegisterShiftMasterDto(self.registrationForm());
			nts.uk.ui.block.grayout();
			service.register(param)
				.done(() => {
					service.getlist()
						.done((data) => {
							nts.uk.ui.dialog.info({ messageId: "Msg_15" });
							self.shiftMasters(data);
							self.selectedShiftMaster(param.shiftMasterCode);
						});
				}).fail(function (error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function () {
					nts.uk.ui.block.clear();
				});
		}

		public deleteShiftMaster() {
			nts.uk.ui.block.invisible();
			let self = this;
			let param = new RegisterShiftMasterDto(self.registrationForm());
			nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
				.ifYes(() => {
					let i = _.findIndex(self.shiftMasters(), x => { return x.shiftMasterCode == self.selectedShiftMaster });
					service.deleteShiftMaster(param).done((data) => {
						service.getlist()
							.done((data) => {
								if (!data || data.length === 0) {
									self.createNew();
								} else {
									if (i >= self.shiftMasters().length - 1) {
										self.shiftMasters(data);
										self.selectedShiftMaster(self.shiftMasters()[i - 1].shiftMasterCode);
									} else {
										self.shiftMasters(data);
										self.selectedShiftMaster(self.shiftMasters()[i + 1].shiftMasterCode);
										nts.uk.ui.dialog.info({ messageId: "Msg_16" });
										nts.uk.ui.block.clear();
									}
								}
							});
					}).fail((res) => {
						nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function () { nts.uk.ui.block.clear(); });
					});
				}).ifNo(() => {
					nts.uk.ui.block.clear();
				});
		}

		public openDialogKDL003(): void {
			var self = this;
			// set update data input open dialog kdl003
			nts.uk.ui.windows.setShared('parentCodes', {
				workTypeCodes: [],
				selectedWorkTypeCode: self.registrationForm().workTypeCd(),
				workTimeCodes: [],
				selectedWorkTimeCode: self.registrationForm().workTypeCd()
			}, true);

			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function (): any {
				//view all code of selected item 
				var childData = nts.uk.ui.windows.getShared('childData');
				if (childData) {
					self.registrationForm().workTypeName(childData.selectedWorkTypeName);
					self.registrationForm().workTypeCd(childData.selectedWorkTypeCode);
					self.registrationForm().workTimeSetName(childData.selectedWorkTimeName);
					self.registrationForm().workTimeSetCd(childData.selectedWorkTimeCode);
				}
			});
		}

	}
}