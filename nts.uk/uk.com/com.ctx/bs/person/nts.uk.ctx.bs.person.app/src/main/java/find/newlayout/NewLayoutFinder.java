/**
 * 
 */
package find.newlayout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layoutitemclassification.LayoutPersonInfoClsDto;
import find.layoutitemclassification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;

/**
 * @author laitv
 *
 */
@Stateless
public class NewLayoutFinder {

	@Inject
	private INewLayoutReposotory repo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	public NewLayoutDto getLayout() {
		Optional<NewLayout> layout = repo.getLayout();
		if (layout.isPresent()) {
			NewLayout _layout = layout.get();
			// get classifications

			// Get list Classification Item by layoutID
			List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

			return NewLayoutDto.fromDomain(_layout, listItemCls);
		} else {
			return null;
		}
	}

}
