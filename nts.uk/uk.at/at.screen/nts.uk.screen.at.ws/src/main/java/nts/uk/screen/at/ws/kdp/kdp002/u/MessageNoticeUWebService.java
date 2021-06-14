package nts.uk.screen.at.ws.kdp.kdp002.u;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.portal.app.command.notice.ViewMessageNoticeCommand;
import nts.uk.ctx.sys.portal.app.command.notice.ViewMessageNoticeCommandHandler;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).U:打刻社員メッセージ表示.メニュー別OCD.打刻入力でお知らせの内容を閲覧する
 * @author tutt
 *
 */
@Path("at/record/stamp/notice")
@Produces("application/json")
@Stateless
public class MessageNoticeUWebService {
	
	@Inject
	private ViewMessageNoticeCommandHandler viewHandler;
	
	@POST
	@Path("/viewMessageNotice")
	public void viewMessageNotice(ViewMessageNoticeCommand command) {
		this.viewHandler.handle(command);
	}
}
