package nts.uk.ctx.sys.gateway.app.find.system;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.shr.com.operation.SystemOperationSetting;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemOperationMode;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemStopMode;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemStopType;
import nts.uk.shr.com.operation.SystemOperationSettingAdapter;

@Stateless
public class SystemOperationSettingAdapterImpl implements SystemOperationSettingAdapter {

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
	public Optional<String> stopUseConfirm() {
		// TODO Auto-generated method stub
		return null;
	}

}
