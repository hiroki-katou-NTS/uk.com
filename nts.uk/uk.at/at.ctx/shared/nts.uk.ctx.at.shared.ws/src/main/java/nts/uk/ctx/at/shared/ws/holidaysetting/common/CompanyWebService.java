package nts.uk.ctx.at.shared.ws.holidaysetting.common;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanyWebService.
 */
@Path("at/shared/holidaysetting/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyWebService extends WebService{
	
	/** The company adapter. */
	@Inject
	private CompanyAdapter companyAdapter;
	
	/**
	 * Find all.
	 *
	 * @return the workplace month day setting dto
	 */
	@Path("getFirstMonth")
	@POST
	public CompanyDto getFirstMonth(){
		String cid = AppContexts.user().companyId();
		return companyAdapter.getFirstMonth(cid);
	}
}
