module nts.uk.com.view.cmm018.q.viewmodel {
	@bean()
	export class Cmm018QViewModel extends ko.ViewModel{
		param: PARAM;
		model: CheckBoxModel = new CheckBoxModel();
		dataSource: SettingUseUnitDto;
	    created(params: any) {
	        // data transfer from parent view call modal
			const self = this;
			self.param = params;		
			self.dataFetch();
	    }
		mounted() {
			const self = this;
			console.log(self.param.systemAtr);
		}
		closeModal() {
		    const self = this;
		    self.$window.close({
		        // data return to parent
		    });
	    }
		
		register() {
			console.log('register');
		}
		
		
		dataFetch() {
			const self = this;
			self.$blockui("show");
			let startQCommand = {} as StartQCommand;
			startQCommand.companyId = self.$user.companyId;
			startQCommand.systemAtr = self.param.systemAtr;
			self.$ajax(API.getSetting, startQCommand)
				.done(res => {
					self.dataSource = res as SettingUseUnitDto;
					if (self.param.systemAtr == SystemAtr.EMPLOYMENT) {
						if (!self.dataSource.mode) {
							let approverSet = self.dataSource.approvalSetting.approverSet;
							self.model.changeValue(approverSet.companyUnit == 1, approverSet.workplaceUnit == 1, approverSet.employeeUnit == 1);
						}
						
					} else {
						// HUMAN_RESOURCE
						if (!self.dataSource.mode) {
							let hrApprovalRouteSetting = self.dataSource.hrApprovalRouteSetting;
							self.model.changeValue(hrApprovalRouteSetting.comMode, hrApprovalRouteSetting.devMode, hrApprovalRouteSetting.empMode);
						}
						
					}
				}).fail(res => {
					
				}).always(() => {
					self.$blockui("hide");
				})
			
		}
	}
	
	const API = {
		getSetting: 'workflow/approvermanagement/workroot/appSetQ',
		register: ''
	}
	class PARAM {
		public systemAtr: number;
	}
	class SettingUseUnitDto {
		public mode: boolean;
		public approvalSetting: ApprovalSettingDto;
		public hrApprovalRouteSetting: HrApprovalRouteSettingWFDto;
	}
	class ApprovalSettingDto {
		public companyId: string;
		public prinFlg: number
		public approverSet: ApproverRegisterSetDto;
	}
	class ApproverRegisterSetDto {
		public companyUnit: number;
		public workplaceUnit: number;
		public employeeUnit: number;
	}
	class HrApprovalRouteSettingWFDto {
		public comMode: boolean;
		public cid: string;
		public empMode: boolean;
		public devMode: boolean;
	}
	class RegisterCommand {
		
	}
	class StartQCommand {
		
		public companyId: string;
		
		public systemAtr: number;
	}
	class CheckBoxModel {
		
		public companyUnit: KnockoutObservable<Boolean> = ko.observable(false);
		
		public workPlaceUnit: KnockoutObservable<Boolean> = ko.observable(false);
		
		public personUnit: KnockoutObservable<Boolean> = ko.observable(false);
		
		public changeValue(companyUnit: boolean, workPlaceUnit: boolean, personUnit: boolean) {
			this.companyUnit(companyUnit);
			this.workPlaceUnit(workPlaceUnit);
			this.personUnit(personUnit);
		}
	}
	const SystemAtr = {
		EMPLOYMENT: 0,
		HUMAN_RESOURSE: 1
	}
	
}