package nts.uk.screen.at.ws.kdp.kdp003.p;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.dom.notice.adapter.DatePeriodDto;
import nts.uk.screen.at.app.query.kdp.kdp003.p.GetNoticeByStamping;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tutt
 *
 */
@Path("at/record/stamp/notice")
@Produces("application/json")
@Stateless
public class MessageNoticePWebService {

	@Inject
	private GetNoticeByStamping noticeByStamping;

	// 打刻入力で作成したお知らせを取得する
	@POST
	@Path("/getNoticeByStamping")
	public List<MessageNoticeDto> getNoticeByStamping(DatePeriodDto param) {
		String sid = AppContexts.user().employeeId();

		DatePeriod period = param == null ? new DatePeriod(GeneralDate.today(), GeneralDate.today())
				: new DatePeriod(param.getStartDate(), param.getEndDate());
		return noticeByStamping.getNoticeByStamping(period, sid);
	}
}
