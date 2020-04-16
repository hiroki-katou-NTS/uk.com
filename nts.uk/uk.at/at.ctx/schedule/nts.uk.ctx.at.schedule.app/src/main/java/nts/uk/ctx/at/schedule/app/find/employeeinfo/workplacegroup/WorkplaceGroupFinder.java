package nts.uk.ctx.at.schedule.app.find.employeeinfo.workplacegroup;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupRespository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt
 * 職場グループをすべて取得する
 * 部品起動
 */
@Stateless
public class WorkplaceGroupFinder {
	
	@Inject
	private WorkplaceGroupRespository wkpGroupRepo;
	
	public WorkplaceGroupDto getWorkplaceGroup () {
		String cid = AppContexts.user().companyId();
		List<WorkplaceGroup> wkpGroups = wkpGroupRepo.getAll(cid);
		return new WorkplaceGroupDto(wkpGroups);
	}
}
