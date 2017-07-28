/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.util.Optional;

/**
 * @author laitv
 *
 */
public interface LayoutPersonInfoRepository {
	
	void add(LayoutPersonInfo layoutPersonInfo);
	
	void update(LayoutPersonInfo layoutPersonInfo);
	
	void remove(LayoutPersonInfo layoutPersonInfo);
	
	Optional<LayoutPersonInfo> getSingleLayout(String layoutID , DisPOrder disPOrder);
	
	

}
