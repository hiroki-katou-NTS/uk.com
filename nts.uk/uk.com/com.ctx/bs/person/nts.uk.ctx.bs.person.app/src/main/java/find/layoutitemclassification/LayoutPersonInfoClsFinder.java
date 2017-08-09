/**
 * 
 */
package find.layoutitemclassification;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;


@Stateless
public class LayoutPersonInfoClsFinder {

	@Inject
	private ILayoutPersonInfoClsRepository clsRepo;
	
	
	
}
