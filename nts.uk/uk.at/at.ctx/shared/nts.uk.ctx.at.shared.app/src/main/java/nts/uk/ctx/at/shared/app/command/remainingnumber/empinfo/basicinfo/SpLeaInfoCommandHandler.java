package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

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
	 * Add specialLeaveBasicInfo
	 * @param command
	 * @param spLeaveCD
	 * @return
	 */
	public List<PeregAddCommandResult> addAllHandler(List<SpecialLeaveBasicInfo> domains){
		specialLeaveBasicInfoRepository.addAll(domains);
		return domains.parallelStream().map(c ->{ return new PeregAddCommandResult(c.getSID());}).collect(Collectors.toList());
	}
	
	/**
	 * Update specialLeave basic info
	 * @param command
	 * @param spLeaveCD
	 */
	public void updateHandler(SpecialLeaveBasicInfo domain){
		specialLeaveBasicInfoRepository.update(domain);
	}
	
	/**
	 * Update specialLeave basic info
	 * @param command
	 * @param spLeaveCD
	 */
	public void updateAllHandler(List<SpecialLeaveBasicInfo> domains){
		specialLeaveBasicInfoRepository.updateAll(domains);
	}
}
