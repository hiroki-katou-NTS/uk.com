package nts.uk.ctx.at.schedule.infra.repository.schedule.workschedules.displaysetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;

@Stateless
public class JpaDisplayControlPersonalConditionRepository extends JpaRepository implements DisplayControlPersonalConditionRepo {

	
	
	
	@Override
	public void insert(DisplayControlPersonalCondition DisplayControlPersonalCondition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(DisplayControlPersonalCondition DisplayControlPersonalCondition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<DisplayControlPersonalCondition> get(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
