package nts.uk.ctx.at.function.dom.adapter.notice;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.message.NoticeMessageImport;

public interface MessageNoticeServiceAdapter {
	public List<NoticeMessageImport> getMessage(String cid, String sid, DatePeriod period);

}
