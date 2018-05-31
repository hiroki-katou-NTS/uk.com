/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.layout;

import java.util.Optional;

/**
 * @author laitv
 *
 */
public interface INewLayoutReposotory {

	/*
	 * update details to first layout
	 */
	void save(NewLayout domain);
	
	/**
	 * new-layout of company
	 * @return
	 */
	Optional<NewLayout> getLayout();
}
