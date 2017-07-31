/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.util.Optional;

/**
 * @author laitv
 *
 */
public interface LayoutPersonInfoClsRepository {
	
	void add(LayoutPersonInfoClassification layoutPersonInfo);
	
	void update(LayoutPersonInfoClassification layoutPersonInfo);
	
	void remove(LayoutPersonInfoClassification layoutPersonInfo);
	
	Optional<LayoutPersonInfoClassification> getDetailLayoutPerInfoClassification(String layoutID , DisPOrder disPOrder);
	
	
	

}
