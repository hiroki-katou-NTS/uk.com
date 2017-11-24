package nts.uk.ctx.bs.employee.infra.repository.department.affiliate;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository_v1;

@Stateless
public class JpaAffDepartmentHistoryItemRepository extends JpaRepository implements AffWorkplaceHistoryItemRepository_v1{

	@Override
	public void addAffWorkplaceHistory(AffWorkplaceHistoryItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAffWorkplaceHistory(String histID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAffWorkplaceHistory(AffWorkplaceHistoryItem domain) {
		// TODO Auto-generated method stub
		
	}


}
