package nts.uk.ctx.sys.gateway.app.find.system;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendService;
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
	@Override
	public SystemOperationSetting getSetting() {
		
		/** IF STOP ALL SYSTEM */
		/** IF BEFORE STOP ALL SYSTEM AND COMPANY IS NOT STOP */
		if(true){
			return SystemOperationSetting.setting(SystemStopType.ALL_SYSTEM, SystemOperationMode.STOP, SystemStopMode.ADMIN_MODE, "test 1", "test 1"); 
		}
		
		/** IF STOP COMPANY */
		/** IF STOP COMPANY */
		return SystemOperationSetting.setting(SystemStopType.COMPANY, SystemOperationMode.STOP, SystemStopMode.ADMIN_MODE, "test 1", "test 1");
	}

	@Override
	public SystemSuspendOut stopUseConfirm(String contractCD, String companyCD, int loginMethod, String programID, String screenID) {
		SystemSuspendOutput sys = sysSuspendSv.confirmSystemSuspend(contractCD, companyCD, loginMethod, programID, screenID);
		return new SystemSuspendOut(sys.isError(), sys.getMsgID(), sys.getMsgContent());
	}

}
