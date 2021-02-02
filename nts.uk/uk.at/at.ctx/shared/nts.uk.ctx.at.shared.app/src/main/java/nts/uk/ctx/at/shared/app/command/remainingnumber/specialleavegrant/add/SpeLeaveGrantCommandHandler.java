package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;

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
		repo.add(AppContexts.user().companyId(), domain);
		return domain.getEmployeeId();
	}

	
	public List<MyCustomizeException> addHandler(List<SpecialLeaveGrantRemainingData> domains){
		repo.addAll(AppContexts.user().companyId(), domains);
		return new ArrayList<>();
	}

}
