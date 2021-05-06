package nts.uk.screen.at.ws.kml.kml002.h;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kml002.H.CompanyInfoFinder;
import nts.uk.screen.at.app.kml002.H.EsimatedInfoDto;
import nts.uk.screen.at.app.kml002.H.EstimatedInfoFinder;
import nts.uk.screen.at.app.kml002.H.InitInfoDto;

/**
 * KML002 H
 * @author hoangnd
 *
 */

@Path("ctx/at/schedule/budget/initCompanyInfo")
@Produces("application/json")
public class CompanyInfoWebService extends WebService {
	
	@Inject
	private CompanyInfoFinder companyInfoFinder;
	
	@Inject
	private EstimatedInfoFinder estimatedInfoFinder;
	
	@Path("init")
	@POST
	public InitInfoDto init() {
		
		return companyInfoFinder.getInitInfo();
	}
	
	@Path("selectedTab")
	@POST
	public EsimatedInfoDto selectedTab() {
		
		return estimatedInfoFinder.getEstimatedInfo();
	}

	
}
