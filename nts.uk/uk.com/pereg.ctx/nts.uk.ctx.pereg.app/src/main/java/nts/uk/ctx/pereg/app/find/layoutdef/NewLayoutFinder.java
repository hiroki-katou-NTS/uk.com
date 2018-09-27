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

	public Optional<NewLayoutDto> getLayout() {
		return repo.getLayout().map(m -> {
			// Get list Classification Item by layoutID
			List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(m.getLayoutID());

			return NewLayoutDto.fromDomain(m, listItemCls,null);
		});
	}

}
