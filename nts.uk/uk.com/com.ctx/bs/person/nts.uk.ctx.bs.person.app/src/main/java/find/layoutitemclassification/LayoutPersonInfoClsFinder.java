/**
 * 
 */
package find.layoutitemclassification;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClsRepository;

/**
 * @author laitv
 *
 */
public class LayoutPersonInfoClsFinder {

	@Inject
	private LayoutPersonInfoClsRepository layoutPersonInfoClsRepository;

	Optional<LayoutPersonInfoClsDto> getDetailLayoutPerInfoCls(String layoutID, DisPOrder disPOrder) {
		return this.layoutPersonInfoClsRepository.getDetailLayoutPerInfoClassification(layoutID, disPOrder)
				.map(c -> LayoutPersonInfoClsDto.fromDomain(c));
	}

}
