/**
 * 
 */
package find.newlayout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.newlayout.NewLayout;
import nts.uk.ctx.bs.person.dom.person.newlayout.INewLayoutReposotory;

/**
 * @author laitv
 *
 */
@Stateless
public class NewLayoutFinder {

	@Inject
	private INewLayoutReposotory repo;

	public NewLayoutDto getLayout() {
		Optional<NewLayout> layout = repo.getLayout();
		if (layout.isPresent()) {
			NewLayout _layout = layout.get();
			// get classifications
			
			return NewLayoutDto.fromDomain(_layout, null);
		} else {
			return null;
		}
	}

}
