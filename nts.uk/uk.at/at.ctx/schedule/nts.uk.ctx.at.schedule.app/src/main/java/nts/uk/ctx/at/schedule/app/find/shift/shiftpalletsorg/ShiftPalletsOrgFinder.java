package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ShiftPalletsOrgFinder {

	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;
	
	public List <PageandName> getbyWorkPlaceId(String workplaceId){
	//	ShiftPalletsOrg shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(workplaceId);
		return null;
	}
}
