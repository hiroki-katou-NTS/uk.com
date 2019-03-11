package nts.uk.ctx.sys.gateway.app.find.system;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendService;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompanyRepository;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopModeType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystem;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;
import nts.uk.shr.com.operation.SystemOperationSetting;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemOperationMode;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemStopMode;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemStopType;
import nts.uk.shr.com.operation.SystemOperationSettingAdapter;
import nts.uk.shr.com.operation.SystemSuspendOut;

@Stateless
public class SystemOperationSettingAdapterImpl implements SystemOperationSettingAdapter {

	@Inject
	private SystemSuspendService sysSuspendSv;
	
	@Inject
	private StopBySystemRepository stopBySysRepo;

	@Inject
	private StopByCompanyRepository stopByComRepo;

	@Override
	public SystemOperationSetting getSetting() {

		/** IF STOP ALL SYSTEM */
		/** IF BEFORE STOP ALL SYSTEM AND COMPANY IS NOT STOP */
		String contractCd = AppContexts.user().contractCode();
		Optional<StopBySystem> sys = stopBySysRepo.findByKey(contractCd);
		Optional<StopByCompany> com = stopByComRepo.findByKey(contractCd, AppContexts.user().companyCode());
		if(sys.isPresent() && 
				((sys.get().getSystemStatus().equals(SystemStatusType.STOP)) ||//SYSTEM: stop
				(sys.get().getSystemStatus().equals(SystemStatusType.IN_PROGRESS) //system: in progress
					&& com.isPresent() && !com.get().getSystemStatus().equals(SystemStatusType.STOP)))){//company != stop
			StopBySystem sysSet = sys.get();
			return SystemOperationSetting.setting(SystemStopType.ALL_SYSTEM, 
					EnumAdaptor.valueOf(sysSet.getSystemStatus().value, SystemOperationMode.class), 
					sysSet.getStopMode() == null ? null : EnumAdaptor.valueOf(sysSet.getStopMode().value, SystemStopMode.class),
					sysSet.getStopMessage() == null ? "" : sysSet.getStopMessage().v(),
					sysSet.getUsageStopMessage() == null ? "" : sysSet.getUsageStopMessage().v()); 

		}

		/** IF STOP COMPANY */
		/** IF STOP COMPANY */
		if(com.isPresent() && com.get().getSystemStatus().equals(SystemStatusType.STOP)){
			StopByCompany comSet = com.get();
			return SystemOperationSetting.setting(SystemStopType.COMPANY, 
					EnumAdaptor.valueOf(comSet.getSystemStatus().value, SystemOperationMode.class), 
					comSet.getStopMode() == null ? null : EnumAdaptor.valueOf(comSet.getStopMode().value, SystemStopMode.class),
					comSet.getStopMessage() == null ? "" : comSet.getStopMessage().v(),
					comSet.getUsageStopMessage() == null ? "" : comSet.getUsageStopMessage().v()); 
		}
		return SystemOperationSetting.setting(SystemStopType.ALL_SYSTEM, SystemOperationMode.RUNNING, SystemStopMode.ADMIN_MODE, null, null);
	}

	@Override
	public Optional<String> stopUseConfirm() {
		LoginUserRoles role = AppContexts.user().roles();
		if (role.forSystemAdmin() != null || role.forCompanyAdmin() != null) {
			return Optional.empty();
		}

		BusinessState businessState = isBusinessPerson(role) ? checkBusinessStateForAdmin() : checkBusinessState();

		if (businessState.canDoBusiness) {
			return Optional.empty();
		} else {
			return Optional.of(businessState.stopMessage);
		}

	}

	private boolean isBusinessPerson(LoginUserRoles role) {
		return role.forAttendance() != null || role.forPayroll() != null || role.forPersonnel() != null
				|| role.forOfficeHelper() != null;
	}

	/**
	 * 業務担当者が業務可能か判別する
	 * 
	 * @return
	 */
	private BusinessState checkBusinessStateForAdmin() {
		Optional<StopBySystem> stopBySystemOpt = stopBySysRepo.findByKey(AppContexts.user().contractCode());

		if (isStopSystemAdmin(stopBySystemOpt)) {
			return new BusinessState(stopBySystemOpt.get().getStopMessage().v());
		}

		Optional<StopByCompany> stopByComOpt = stopByComRepo.findByKey(AppContexts.user().contractCode(),
				AppContexts.user().companyCode());

		if (isStopCompanyAdmin(stopByComOpt)) {
			return new BusinessState(stopByComOpt.get().getStopMessage().v());
		}

		return new BusinessState();

	}

	/**
	 * システム管理者/会社管理者/業務担当者でない場合業務可能か判別する
	 * 
	 * @return
	 */
	private BusinessState checkBusinessState() {
		Optional<StopBySystem> stopBySystemOpt = stopBySysRepo.findByKey(AppContexts.user().contractCode());

		if (isStopSystem(stopBySystemOpt)) {
			return new BusinessState(stopBySystemOpt.get().getStopMessage().v());
		}

		Optional<StopByCompany> stopByComOpt = stopByComRepo.findByKey(AppContexts.user().contractCode(),
				AppContexts.user().companyCode());

		if (isStopCompany(stopByComOpt)) {
			return new BusinessState(stopByComOpt.get().getStopMessage().v());
		}

		return new BusinessState();
	}

	private boolean isStopSystemAdmin(Optional<StopBySystem> stopBySystemOpt) {
		if (!stopBySystemOpt.isPresent()) {
			return false;
		}

		StopBySystem stopBySystem = stopBySystemOpt.get();

		return stopBySystem.getSystemStatus() == SystemStatusType.STOP
				&& stopBySystem.getStopMode() == StopModeType.ADMIN_MODE;
	}

	private boolean isStopSystem(Optional<StopBySystem> stopBySystemOpt) {
		if (!stopBySystemOpt.isPresent()) {
			return false;
		}

		StopBySystem stopBySystem = stopBySystemOpt.get();

		return stopBySystem.getSystemStatus() == SystemStatusType.STOP;
	}

	private boolean isStopCompanyAdmin(Optional<StopByCompany> stopByComOpt) {
		if (!stopByComOpt.isPresent()) {
			return false;
		}

		StopByCompany stopByCom = stopByComOpt.get();

		return stopByCom.getSystemStatus() == SystemStatusType.STOP
				&& stopByCom.getStopMode() == StopModeType.ADMIN_MODE;
	}

	private boolean isStopCompany(Optional<StopByCompany> stopByComOpt) {
		if (!stopByComOpt.isPresent()) {
			return false;
		}

		StopByCompany stopByCom = stopByComOpt.get();

		return stopByCom.getSystemStatus() == SystemStatusType.STOP;
	}

	class BusinessState {

		boolean canDoBusiness;

		String stopMessage;

		public BusinessState(String stopMessage) {
			this.canDoBusiness = false;
			this.stopMessage = stopMessage;
		}

		public BusinessState() {
			this.canDoBusiness = true;
			this.stopMessage = "";
		}

	}
	@Override
	public SystemSuspendOut stopUseConfirm_loginBf(String contractCD, String companyCD, int loginMethod, String programID, String screenID) {
		SystemSuspendOutput sys = sysSuspendSv.confirmSystemSuspend_BefLog(contractCD, companyCD, loginMethod, programID, screenID);
		return new SystemSuspendOut(sys.isError(), sys.getMsgID(), sys.getMsgContent());
	}
}
