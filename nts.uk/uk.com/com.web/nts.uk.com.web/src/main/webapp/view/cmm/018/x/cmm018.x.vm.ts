module nts.uk.com.view.cmm018.x.viewmodel {

	const API = {
		checkBootMode: 'screen/approvermanagement/workroot/check-boot-mode',
		changeOperationMode: 'screen/approvermanagement/workroot/change-operation-mode',
	};

	@bean()
	export class Cmm018XViewModel extends ko.ViewModel {
		systemAtr: KnockoutObservable<number> = ko.observable(SystemAtr.EMPLOYMENT);
		mode: KnockoutObservable<number> = ko.observable(OperationMode.PERSON_IN_CHARGE);
		oldMode: number = OperationMode.PERSON_IN_CHARGE;
		isPersonInCharge: KnockoutComputed<boolean>;

		created() {
			const self = this;
			let url = $(location).attr('search');
            let urlParam: number = url.split("=")[1];
			self.systemAtr(urlParam || SystemAtr.EMPLOYMENT);
			self.checkBootMode();
			self.isPersonInCharge = ko.computed(() => self.mode() === OperationMode.PERSON_IN_CHARGE);
		}

		checkBootMode() {
			const self = this;
			self
				.$blockui('grayout')
				.then(() => self.$ajax('com', API.checkBootMode))
				.then((response: { operationMode: number }) => {
					self.mode(response?.operationMode || OperationMode.PERSON_IN_CHARGE);
				})
				.always(() => {
					self.subscribeMode();
					self.$blockui('clear');
				});
		}

		mounted() {
			$('#btnM').focus();
		}

		subscribeMode() {
			const self = this;
			let isSubscribeMode = false;
			self.mode.subscribe(() => {
				if (isSubscribeMode) return;
				isSubscribeMode = true;
				self.$dialog
					.confirm({ messageId: 'Msg_3306' })
					.then((rs: 'yes' | 'no' | 'cancel') => {
						if (rs !== 'yes') {
							self.mode(self.oldMode);
							isSubscribeMode = false;
							return;
						}

						self
							.$blockui('grayout')
							.then(() => self.$ajax('com', API.changeOperationMode, {
								opeMode: self.mode(),
								itemNameInfor: {
									firstItemName: self.$i18n("CMM018_250"),
									secondItemName: self.$i18n("CMM018_251"),
									thirdItemName: self.$i18n("CMM018_252"),
									fourthItemName: self.$i18n("CMM018_253"),
									fifthItemName: self.$i18n("CMM018_254"),
								}
							}))
							.then(() => {
								self.oldMode = _.cloneDeep(self.mode());
							})
							.fail(error => {
								self.$dialog.error(error);
								self.mode(self.oldMode);
							})
							.always(() => {
								isSubscribeMode = false;
								self.$blockui('clear');
							});
					});
			}, null, 'beforeChange');
		}

		openDialogQ() {
			console.log('openDialogQ');
			const self = this;
			let param = {
				systemAtr: ko.toJS(self.systemAtr)
			}
			self.$window
				.modal('com', '/view/cmm/018/q/index.xhtml', param)
				.then((result: any) => {
					// bussiness logic after modal closed
					// location.reload();
				});
		}
		jumpToA() {
			const self = this;
			self.$jump('com', '/view/cmm/018/a/index.xhtml', ko.toJS(self.systemAtr));

		}
		jumpToCmm013H() {	
			const self = this;
			self.$window
				.modal('com', '/view/cmm/013/h/index.xhtml', {})
				.then((result: any) => {
					// bussiness logic after modal closed
					// location.reload();
				});
		}
		openDialogM() {
			const self = this;
			let param = {
				sysAtr: ko.toJS(self.systemAtr)
			}
			
			self.$window.storage('CMM018M_PARAM', param)
			.then(() => self.$window.modal('com', '/view/cmm/018/m/index.xhtml'))
			.then((result: any) => {
				// bussiness logic after modal closed
			});
		}

		jumpToCMM018R() {
			const vm = this;
			vm.$jump('com', '/view/cmm/018/r/index.xhtml');
		}

		jumpToCMM030A() {
			const vm = this;
			const params = { requestUrl: '/view/cmm/018/x/index.xhtml' };
			vm.$jump('com', '/view/cmm/030/a/index.xhtml', params);
		}
		
	}
	export const SystemAtr = {
		EMPLOYMENT: 0,
		HUMAN_RESOURSE: 1
	}
	export const MODE_SYSTEM = 'SYSTEM_MODE';

	export enum OperationMode {
		/** 就業担当者が行う */
		PERSON_IN_CHARGE = 0,
		/** 上長・社員が行う */
		SUPERIORS_EMPLOYEE = 1,
	}
}
