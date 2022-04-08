package nts.uk.ctx.sys.portal.pub.notice;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface MessageNoticeServicePub {
	
	public List<NoticeMessageExport> getMessage(String cid, String sid, DatePeriod period);

}
