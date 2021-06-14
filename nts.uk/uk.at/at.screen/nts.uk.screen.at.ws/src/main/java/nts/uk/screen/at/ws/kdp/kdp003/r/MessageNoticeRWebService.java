package nts.uk.screen.at.ws.kdp.kdp003.r;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.adapter.DatePeriodDto;
import nts.uk.screen.at.app.query.kdp.kdp003.r.DisplayNoticeMessage;
import nts.uk.screen.at.app.query.kdp.kdp003.r.MsgNoticeDto;

/**
 * 
 * @author tutt
 *
 */
@Path("at/record/stamp/notice")
@Produces("application/json")
@Stateless
public class MessageNoticeRWebService {

	@Inject
	private DisplayNoticeMessage noticeMessage;

	// 打刻入力(共有)でお知らせメッセージを表示する
	@POST
	@Path("/displayNoticeMessage")
	public List<MsgNoticeDto> displayNoticeMsg(DisplayNoticeMsgParam param) {
		DatePeriodDto periodDto = param.getPeriodDto();
		DatePeriod period = periodDto == null ? new DatePeriod(GeneralDate.today(), GeneralDate.today())
				: new DatePeriod(periodDto.getStartDate(), periodDto.getEndDate());

		return noticeMessage.displayNoticeMessage(period, param.getWkpIds());
	}
}
