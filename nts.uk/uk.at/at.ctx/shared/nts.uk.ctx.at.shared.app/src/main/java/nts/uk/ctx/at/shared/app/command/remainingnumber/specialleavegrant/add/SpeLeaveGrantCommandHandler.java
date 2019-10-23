package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;

@Stateless
public class SpeLeaveGrantCommandHandler {
	
	@Inject
	private SpecialLeaveGrantRepository repo;
	
	/**
	 * Add SpecialLeaveGrantRemainingData
	 * @param command
	 * @param spLeaveCD
	 * @return
	 */
	public String addHandler(SpecialLeaveGrantRemainingData domain){
		if(domain == null) return null; 
		repo.add(domain);
		return domain.getEmployeeId();
	}

}
