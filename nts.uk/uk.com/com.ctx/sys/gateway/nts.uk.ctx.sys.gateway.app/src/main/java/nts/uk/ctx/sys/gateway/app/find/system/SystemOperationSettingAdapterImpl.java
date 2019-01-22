package nts.uk.ctx.sys.gateway.app.find.system;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompanyRepository;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystem;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.operation.SystemOperationSetting;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemOperationMode;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemStopMode;
import nts.uk.shr.com.operation.SystemOperationSetting.SystemStopType;
import nts.uk.shr.com.operation.SystemOperationSettingAdapter;

@Stateless
public class SystemOperationSettingAdapterImpl implements SystemOperationSettingAdapter {

	
	@Inject
	private StopBySystemRepository repoStopBySys;
	
	@Inject
	private StopByCompanyRepository repoStopByCom;
	
	@Override
	public SystemOperationSetting getSetting() {
		
		/** IF STOP ALL SYSTEM */
		/** IF BEFORE STOP ALL SYSTEM AND COMPANY IS NOT STOP */
		String contractCd = AppContexts.user().contractCode();
		Optional<StopBySystem> sys = repoStopBySys.findByKey(contractCd);
		Optional<StopByCompany> com = repoStopByCom.findByKey(contractCd, AppContexts.user().companyCode());
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
		// TODO: implement checking if the user can login
		return Optional.empty();
	}

}
