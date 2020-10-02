module nts.uk.at.view.ksm015.b.viewmodel {

	export class ScreenModel {

		searchOptions: ShiftGridOption;
		shiftMasters: KnockoutObservableArray<any>;
		selectedShiftMaster: KnockoutObservable<any>;
		searchValue: KnockoutObservable<String>;
		registrationForm: KnockoutObservable<RegistrationForm>;
		workStyle: KnockoutObservable<WorkStyle>;


		constructor() {
			var self = this;
			self.searchOptions = new ShiftGridOption();
			self.shiftMasters = ko.observableArray([]);
			self.selectedShiftMaster = ko.observable({});
			self.searchValue = ko.observable("");
			self.registrationForm = ko.observable(new RegistrationForm());
			self.workStyle = ko.observable(new WorkStyle());
			self.selectedShiftMaster.subscribe((value) => {
				if (!value) {
					self.createNew();
					
				} else {
					nts.uk.ui.errors.clearAll();
					self.bindShiftMasterInfoToForm(value);
					$('.b73-desc').show();
					$('#requiredName').focus();
				}
				self.clearPreviewColor();
				self.getWorkStyle();
			});
			
			self.registrationForm().color.subscribe((value) => {
				if(value)
				nts.uk.ui.errors.clearAll();
			})

			/*	self.registrationForm().workTypeCd.subscribe((value) => {
					if(!value){
					$(".table-workTime").hide();
				} else {
					$(".table-workTime").show();
				}
				});
				
				self.registrationForm().workTimeSetCd.subscribe((value) => {
					if(!value){
					$(".table-workType").hide();
				} else {
					$(".table-workType").show();
				}
				});*/

		}

		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			nts.uk.ui.block.grayout();
			service.startPage().done((data) => {
				let sorted = _.sortBy(data.shiftMasters, 'shiftMasterCode');
				self.shiftMasters(sorted);
				if (data.shiftMasters && data.shiftMasters.length > 0) {
					self.selectedShiftMaster(sorted[0].shiftMasterCode);
					//self.getWorkStyle();
					$('#requiredName').focus();
				} else {
					self.clearPreviewColor();
					self.createNew();
					$('#requiredCode').focus();
				}

			}).fail(function(error) {
				nts.uk.ui.dialog.alertError({ messageId: error.messageId });
			}).always(function() {
				nts.uk.ui.block.clear();
			});

			return dfd.promise();
		}

		public getWorkStyle() {
			let self = this;
			let dfd = $.Deferred();
			if (_.isNil(self.registrationForm().color())) {
				let KSM015_19 = nts.uk.resource.getText('KSM015_19');
				$('#colorpicker').ntsError('set', nts.uk.resource.getMessage("MsgB_2", [KSM015_19]), "MsgB_2");
			};

			if (nts.uk.ui.errors.hasError()) {
				return;
			};
			nts.uk.ui.block.grayout();
			if (self.registrationForm().shiftMasterName() == "" || self.registrationForm().workTypeCd() == ""
				|| self.registrationForm().workTimeSetName().search(nts.uk.resource.getText('KSM015_29')) != -1
				|| self.registrationForm().workTypeName().search(nts.uk.resource.getText('KSM015_29')) != -1) {
				nts.uk.ui.block.clear();
				return;
			}
			let dataByCode = _.filter(self.shiftMasters(), (val) => { return val.shiftMasterCode == self.selectedShiftMaster() }),
				dto = {
					shiftMasterCode:  self.registrationForm().selectedCode(),
					shiftMasterName: self.registrationForm().shiftMasterName(),
					workTypeCode: self.registrationForm().workTypeCd(),
					workTimeCode: self.registrationForm().workTimeSetCd(),
					color: self.registrationForm().color(),
					remarks: self.registrationForm().note()
				};

			service.getWorkStyle(dto).done((workStyle) => {

				if (workStyle == 3)
					self.workStyle().color('#0000ff');

				if (workStyle == 1)
					self.workStyle().color('#FF7F27');

				if (workStyle == 2)
					self.workStyle().color('#FF7F27');

				if (workStyle == 0)
					self.workStyle().color('#ff0000');
					
				self.workStyle().borderColor('solid');
				self.workStyle().backGroundColor(self.registrationForm().color());
				self.workStyle().workTimeSetDisplay(self.registrationForm().shiftMasterName());
				
			}).fail(function(error) {
				nts.uk.ui.dialog.alertError({ messageId: error.messageId });
			}).always(function() {
				nts.uk.ui.block.clear();
			});
		}

		public createNew() {
			let self = this;
			nts.uk.ui.errors.clearAll();
			self.selectedShiftMaster("");
			self.registrationForm().clearData();
			$('#requiredCode').focus();
			self.clearPreviewColor();
			$('.b73-desc').hide();
			/*if(self.registrationForm().workTimeSetDisplay != ''){
				$(".table-workTime").hide();
			}
			
			if(self.registrationForm().workTypeSetDisplay != ''){
				$(".table-workType").hide();
			}*/

		}

		public clearPreviewColor() {
			let self = this;
			self.workStyle().color('#ff0000');
			self.workStyle().borderColor('none');
			self.workStyle().backGroundColor(self.registrationForm().color());
			self.workStyle().workTimeSetDisplay("");
		}

		public bindShiftMasterInfoToForm(code: String) {
			let self = this;
			let selectedSm = _.findLast(self.shiftMasters(), (val) => {
				return val.shiftMasterCode === code;
			});
			self.registrationForm().bindData(selectedSm);
			if (self.registrationForm().workTimeSetCd() == '') {
				$('#worktime-lable').hide();
			} else {
				$('#worktime-lable').show();
			}
		}

		public register() {
			nts.uk.ui.errors.clearAll();
			let self = this;

			self.registrationForm().trimData();

			$(".nts-input").trigger("validate");
			
			// fix bug 111259
			if (_.isNil(self.registrationForm().color())) {
				let KSM015_19 = nts.uk.resource.getText('KSM015_19');
				$('#colorpicker').ntsError('set', nts.uk.resource.getMessage("MsgB_2", [KSM015_19]), "MsgB_2");
			};

			if (!self.registrationForm().workTypeCd() || self.registrationForm().workTypeCd().trim() == '') {
				let KSM015_17 = nts.uk.resource.getText('KSM015_17');
				$('#worktype-chose').ntsError('set', nts.uk.resource.getMessage("MsgB_2", [KSM015_17]), "MsgB_2");
			};

			if (nts.uk.ui.errors.hasError()) {
				return;
			};

			nts.uk.ui.block.grayout();
			let param = new RegisterShiftMasterDto(self.registrationForm());
			service.register(param)
				.done(() => {
					service.getlist()
						.done((data) => {
							nts.uk.ui.dialog.info({ messageId: "Msg_15" });
							self.shiftMasters(_.sortBy(data, 'shiftMasterCode'));
							self.selectedShiftMaster(param.shiftMasterCode);
							self.getWorkStyle();
						});
				}).fail(function(error) {
					nts.uk.ui.dialog.alertError({ messageId: error.messageId });
				}).always(function() {
					nts.uk.ui.block.clear();
				});
		}

		public deleteShiftMaster() {
			nts.uk.ui.block.invisible();
			let self = this;
			let param = new RegisterShiftMasterDto(self.registrationForm());
			nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
				.ifYes(() => {
					// 削除後のB5_a1[シフトリスト]選択処理									
					let i = _.findIndex(self.shiftMasters(), x => { return x.shiftMasterCode == self.selectedShiftMaster() });
					let nextSelectedCode;
					if (self.shiftMasters().length == 1) {
						nextSelectedCode = '';
					} else if (i === 0) {
						nextSelectedCode = self.shiftMasters()[1].shiftMasterCode;
					} else if (i === (self.shiftMasters().length - 1)) {
						nextSelectedCode = self.shiftMasters()[self.shiftMasters().length - 2].shiftMasterCode;
					} else {
						nextSelectedCode = self.shiftMasters()[i + 1].shiftMasterCode;
					}
					service.deleteShiftMaster(param).done((data) => {
						service.getlist()
							.done((data) => {
								if (!data || data.length === 0) {
									nts.uk.ui.dialog.info({ messageId: "Msg_16" });
									self.shiftMasters([]);
									self.createNew();
								} else {
									self.shiftMasters(_.sortBy(data, 'shiftMasterCode'));
									self.selectedShiftMaster(nextSelectedCode);
									nts.uk.ui.dialog.info({ messageId: "Msg_16" });
									nts.uk.ui.block.clear();
								}
								self.clearPreviewColor();
								self.getWorkStyle();
							});
					}).fail((res) => {
						nts.uk.ui.dialog.alertError({ messageId: res.messageId });
					}).always(function() {
						nts.uk.ui.block.clear();
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
				selectedWorkTimeCode: self.registrationForm().workTimeSetCd()
			}, true);

			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
				//view all code of selected item 
				var childData = nts.uk.ui.windows.getShared('childData');
				if (childData) {
					console.log(childData);
					let param = {
						workTypeCd: childData.selectedWorkTypeCode,
						workTimeCd: childData.selectedWorkTimeCode
					}
					service.getWorkInfo(param)
						.done((res) => {
							self.registrationForm().workTypeName(res.workType ? res.workType.name : childData.selectedWorkTypeName);
							self.registrationForm().workTypeCd(childData.selectedWorkTypeCode);
							self.registrationForm().workTimeSetName(res.workTime ? res.workTime.workTimeName : childData.selectedWorkTimeName);
							self.registrationForm().workTimeSetCd(childData.selectedWorkTimeCode);
							if (self.registrationForm().workTypeCd() || self.registrationForm().workTypeCd().trim() !== '') {
								$('#worktype-chose').ntsError('clear');
							}
							if (self.registrationForm().workTimeSetCd() == '') {
								$('#worktime-lable').hide();
							} else { 
								$('#worktime-lable').show();
								}
							$('.b73-desc').show();
						});
				}
			});
		}

	}
}