package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;

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
	public String addHandler(SpecialLeaveBasicInfo domain){
		specialLeaveBasicInfoRepository.add(domain);
		return domain.getSID();
	}
	
	/**
	 * Update specialLeave basic info
	 * @param command
	 * @param spLeaveCD
	 */
	public void updateHandler(SpecialLeaveBasicInfo domain){
		specialLeaveBasicInfoRepository.update(domain);
	}
}
