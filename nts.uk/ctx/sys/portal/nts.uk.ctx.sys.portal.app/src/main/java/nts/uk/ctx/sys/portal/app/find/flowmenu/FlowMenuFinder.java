/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.find.flowmenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class FlowMenuFinder {
	@Inject
	private FlowMenuRepository repository ;
	
	public List<FlowMenuDto> getAllFlowMenu() {
		String companyID = AppContexts.user().companyId();
		return this.repository.findAll(companyID).stream().map(flow -> FlowMenuDto.fromDomain(flow))
				.collect(Collectors.toList());
	}
	
	public Optional<FlowMenuDto> getFlowMenu(String toppagePartID) {
		String companyID = AppContexts.user().companyId();
		return this.repository.findByCode(companyID, toppagePartID).map(flow -> FlowMenuDto.fromDomain(flow));
	}
	
}
