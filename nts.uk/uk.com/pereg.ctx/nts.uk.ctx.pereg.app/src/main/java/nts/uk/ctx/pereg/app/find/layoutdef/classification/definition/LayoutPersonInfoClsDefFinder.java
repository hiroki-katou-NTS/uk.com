/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef.classification.definition;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class LayoutPersonInfoClsDefFinder {

	@Inject
	private ILayoutPersonInfoClsDefRepository classItemDefRepo;

	public List<String> getItemDefineIds(String layoutId, int classDispOrder) {
		return classItemDefRepo.getAllItemDefineIds(layoutId, classDispOrder);
	}

}
