/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.newlayout;

import java.util.Optional;


/**
 * @author laitv
 *
 */
public interface NewLayoutReposotory {
	
	void add(NewLayout newLayout);
	
	void update(NewLayout newLayout);
	
	void remove(LayoutID layoutID);
	
	Optional<NewLayout> findSingleLayout(LayoutID layoutID);

}
