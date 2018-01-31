/**
 * 3:38:52 PM Jan 30, 2018
 */
package nts.uk.ctx.at.request.app.command.setting.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class ApprovalSettingWkpCommandHandler {

	@Inject
	private RequestOfEachWorkplaceRepository repo;
	
}
