/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;

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

	public Boolean checkLayoutExist() {
		Optional<NewLayout> layout = repo.getLayout();
		return layout.isPresent();
	}

	public NewLayoutDto getLayout() {
		Optional<NewLayout> layout = repo.getLayoutWithCreateNew();
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
