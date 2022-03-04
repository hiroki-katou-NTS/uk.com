package nts.uk.ctx.link.smile.ws.smilelink;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.link.smile.app.smilelink.SmileAcceptLinkDto;
import nts.uk.ctx.link.smile.app.smilelink.SmileAcceptLinkFinder;
import nts.uk.ctx.link.smile.app.smilelink.SmileOutLinkSetDto;
import nts.uk.ctx.link.smile.app.smilelink.SmileOutLinkSetFinder;

@Path("link/smile")
@Produces("application/json")
public class SmileInforSettingWs {
	@Inject
	private SmileAcceptLinkFinder acceptFinder;
	
	@Inject
	private SmileOutLinkSetFinder outLinkFinder;
	
	@POST
	@Path("get-accept-setting")
	public SmileAcceptLinkDto getAcceptInfor() {
		SmileAcceptLinkDto acceptSmile = acceptFinder.findAcceptForSmile();
		return acceptSmile;
	}	
	
	@POST
	@Path("get-outlink-setting")
	public SmileOutLinkSetDto getOutLinkInfor() {
		SmileOutLinkSetDto outLinkSmile = outLinkFinder.getOutLinkSetForSmile();
		return outLinkSmile;
	}	
}
