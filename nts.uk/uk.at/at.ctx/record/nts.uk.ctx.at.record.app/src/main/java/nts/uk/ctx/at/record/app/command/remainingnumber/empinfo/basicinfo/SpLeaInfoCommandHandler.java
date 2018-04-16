package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpLeaInfoCommandHandler {
	
	@Inject 
	private SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepository;
	
	/**
	 * Add specialLeaveBasicInfo
	 * @param command
	 * @param spLeaveCD
	 * @return
	 */
	public String addHandler(SpecialleaveinformationCommand command, int spLeaveCD){
		String cid = AppContexts.user().companyId();
		SpecialLeaveBasicInfo domain = new SpecialLeaveBasicInfo(cid, command.getSID(), spLeaveCD, command.getUseAtr(), command.getAppSet(), command.getGrantDate(),
				command.getGrantDays(), command.getGrantTable());
		specialLeaveBasicInfoRepository.add(domain);
		return command.getSID();
	}
	
	/**
	 * Update specialLeave basic info
	 * @param command
	 * @param spLeaveCD
	 */
	public void updateHandler(SpecialleaveinformationCommand command, int spLeaveCD){
		String cid = AppContexts.user().companyId();
		SpecialLeaveBasicInfo domain = new SpecialLeaveBasicInfo(cid, command.getSID(), spLeaveCD, command.getUseAtr(), command.getAppSet(), command.getGrantDate(),
				command.getGrantDays(), command.getGrantTable());
		specialLeaveBasicInfoRepository.update(domain);
	}
}
