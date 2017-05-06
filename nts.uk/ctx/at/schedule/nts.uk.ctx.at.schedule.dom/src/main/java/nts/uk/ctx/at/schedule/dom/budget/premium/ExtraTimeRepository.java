package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;

public interface ExtraTimeRepository {
	
	public void update(ExtraTime extraTime);
	
	public List<ExtraTime> findByCompanyID(String companyID);

}
