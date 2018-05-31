package nts.uk.ctx.sys.assist.ws.mastercopy;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.find.mastercopy.MasterCopyCategoryFindDto;
import nts.uk.ctx.sys.assist.app.find.mastercopy.MasterCopyCategoryFinder;

@Path("sys/assist/mastercopy/category")
@Produces(MediaType.APPLICATION_JSON)
public class MasterCopyCategoryWs extends WebService{
	
	@Inject 
	private MasterCopyCategoryFinder finder;
	
	@POST
	@Path("getAllMasterCopyCategory")
	public List<MasterCopyCategoryFindDto> getAllMasterCopyCategory(){
		return finder.getAllMasterCopyCategory();
	}
}
