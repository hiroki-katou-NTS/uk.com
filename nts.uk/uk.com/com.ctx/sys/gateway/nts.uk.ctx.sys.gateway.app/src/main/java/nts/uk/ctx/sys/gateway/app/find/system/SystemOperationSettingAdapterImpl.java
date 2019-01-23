package nts.uk.ctx.sys.gateway.app.find.system;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

@Stateless
public class SystemOperationSettingAdapterImpl implements SystemOperationSettingAdapter {

	@Inject
	private StopBySystemRepository stopBySysRepo;

	@Inject
	private StopByCompanyRepository stopByComRepo;

	@Override
	public SystemOperationSetting getSetting() {

		/** IF STOP ALL SYSTEM */
		/** IF BEFORE STOP ALL SYSTEM AND COMPANY IS NOT STOP */
		if (true) {
			return SystemOperationSetting.setting(SystemStopType.ALL_SYSTEM, SystemOperationMode.STOP,
					SystemStopMode.ADMIN_MODE, "test1", "test1");
		}

		/** IF STOP COMPANY */
		/** IF STOP COMPANY */
		return SystemOperationSetting.setting(SystemStopType.COMPANY, SystemOperationMode.STOP,
				SystemStopMode.ADMIN_MODE, "test 1", "test 1");
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

}
