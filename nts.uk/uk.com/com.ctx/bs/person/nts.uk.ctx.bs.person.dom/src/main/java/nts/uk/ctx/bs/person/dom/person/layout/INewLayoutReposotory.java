/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layout;

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

	/*
	 * get first (once) layout
	 */
	Optional<NewLayout> getLayout();
}
