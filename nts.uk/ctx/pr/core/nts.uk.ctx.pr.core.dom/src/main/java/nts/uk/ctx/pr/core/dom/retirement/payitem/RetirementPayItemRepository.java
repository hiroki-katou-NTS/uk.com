package nts.uk.ctx.pr.core.dom.retirement.payitem;

import java.util.List;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RetirementPayItemRepository {
	
	void add(RetirementPayItem itemSetting);
	
	List<RetirementPayItem> findAll();
	
	void update(RetirementPayItem itemSetting);
	
	void remove(RetirementPayItem itemSetting);
}
