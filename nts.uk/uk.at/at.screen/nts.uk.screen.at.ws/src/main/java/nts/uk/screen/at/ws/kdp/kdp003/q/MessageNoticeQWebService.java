package nts.uk.screen.at.ws.kdp.kdp003.q;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.screen.at.app.query.kdp.kdp003.q.DisplayNoticeRegisterScreen;
import nts.uk.screen.at.app.query.kdp.kdp003.q.DisplayNoticeRegisterScreenDto;
import nts.uk.screen.at.app.query.kdp.kdp003.q.GetWorkplaceByStampNotice;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tutt
 *
 */
@Path("at/record/stamp/notice")
@Produces("application/json")
@Stateless
public class MessageNoticeQWebService {
	
	@Inject
	private GetWorkplaceByStampNotice wkpByStampNotice;

	@Inject
	private DisplayNoticeRegisterScreen noticeRegisterScreen;

	// 打刻入力のお知らせの職場を取得する
	@POST
	@Path("/getWkpByStampNotice")
	public List<WorkplaceInforExport> getWkpNameByWkpId(List<String> wkpIds) {
		return wkpByStampNotice.getWkpNameByWkpId(wkpIds);
	}

	// 打刻入力の作成するお知らせ登録の画面を表示する
	@POST
	@Path("/displayNoticeRegisterScreen")
	public DisplayNoticeRegisterScreenDto displayNoticeRegisterScreen(MessageNoticeDto param) {
		return noticeRegisterScreen.displayNoticeRegisterScreen(AppContexts.user().employeeId(), param);
	}
}
