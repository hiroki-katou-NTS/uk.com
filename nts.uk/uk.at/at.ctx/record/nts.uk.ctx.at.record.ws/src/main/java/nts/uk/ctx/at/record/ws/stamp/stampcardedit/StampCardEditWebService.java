package nts.uk.ctx.at.record.ws.stamp.stampcardedit;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.stamp.card.stampcardedit.StampCardEditDto;
import nts.uk.ctx.at.record.app.find.stamp.card.stampcardedit.StampCardEditFinder;

@Path("record/stamp/stampcardedit")
@Produces("application/json")
public class StampCardEditWebService extends WebService {

	@Inject
	private StampCardEditFinder stampCardEditFinder;
	
	@POST
	@Path("find")
	public StampCardEditDto find() {
		return stampCardEditFinder.findDto();
	}
	
}
