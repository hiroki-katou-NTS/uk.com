package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */
public interface AlarmCheckConditionByCategoryRepository {
	
	public Optional<AlarmCheckConditionByCategory> find();

	public List<AlarmCheckConditionByCategory> findAll();

	public List<AlarmCheckConditionByCategory> findByCategory();

	public void add(AlarmCheckConditionByCategory domain);

	public void update(AlarmCheckConditionByCategory domain);

	public void delete(AlarmCheckConditionByCategory domain);
	
}
