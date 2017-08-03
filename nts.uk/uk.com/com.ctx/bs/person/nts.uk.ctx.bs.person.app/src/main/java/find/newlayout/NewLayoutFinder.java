/**
 * 
 */
package find.newlayout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.newlayout.NewLayoutReposotory;

/**
 * @author laitv
 *
 */
@Stateless
public class NewLayoutFinder {

//	@Inject
//	private NewLayoutReposotory newLayoutReposotory;
//
//	public List<NewLayoutDto> getAllLayout() {
//		return this.newLayoutReposotory.getAllNewLayout().stream().map(item -> NewLayoutDto.fromDomain(item))
//				.collect(Collectors.toList());
//	}
//
//	public Optional<NewLayoutDto> getDetailLayout(String layoutID) {
//		return this.newLayoutReposotory.getDetailNewLayout(layoutID).map(c -> NewLayoutDto.fromDomain(c));
//	}
}
