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
	void update(NewLayout domain);
	
	/**
	 * new-layout of company
	 * @return
	 */
	Optional<NewLayout> getLayout();

	/*
	 * get first (once) layout
	 */
	Optional<NewLayout> getLayoutWithCreateNew();
}
