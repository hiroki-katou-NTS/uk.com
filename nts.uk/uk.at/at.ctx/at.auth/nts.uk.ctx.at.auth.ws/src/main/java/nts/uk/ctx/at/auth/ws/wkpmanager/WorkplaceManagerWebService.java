package nts.uk.ctx.at.auth.ws.wkpmanager;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import nts.uk.ctx.at.auth.pub.wkpmanager.WorkplaceManagerExport;
//import nts.uk.ctx.at.auth.pub.wkpmanager.WorkplaceManagerPub;

@Path("at/auth/workplace/manager")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceManagerWebService {
	/** Finder */
	
	/** Handler */
	@Inject
//	private WorkplaceManagerPub pub;
	
	@Path("findAll")
    @POST
    public String findAllWkpManager() {
        return "";
    }
}
