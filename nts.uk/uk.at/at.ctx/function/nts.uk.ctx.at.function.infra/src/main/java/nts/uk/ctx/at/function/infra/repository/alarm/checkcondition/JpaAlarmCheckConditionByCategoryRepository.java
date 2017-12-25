package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaAlarmCheckConditionByCategoryRepository extends JpaRepository implements AlarmCheckConditionByCategoryRepository {

	@Override
	public Optional<AlarmCheckConditionByCategory> find(String companyId, int category, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlarmCheckConditionByCategory> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlarmCheckConditionByCategory> findByCategory(String companyId, int category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(AlarmCheckConditionByCategory domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AlarmCheckConditionByCategory domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(AlarmCheckConditionByCategory domain) {
		// TODO Auto-generated method stub
		
	}

}
