package nts.uk.ctx.sys.auth.pubimp.anniversary;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.service.AnniversaryDomainService;
import nts.uk.ctx.sys.auth.pub.anniversary.AnniversaryNoticeExport;
import nts.uk.ctx.sys.auth.pub.anniversary.AnniversaryNoticePub;

@Stateless
public class AnniversaryNoticePubImpl implements AnniversaryNoticePub {

	@Inject
	AnniversaryDomainService anniversaryDomainService;
	
	@Override
	public Map<AnniversaryNoticeExport, Boolean> setFlag(DatePeriod datePeriod) {
		Map<AnniversaryNotice, Boolean> anniversaries = anniversaryDomainService.setFlag(datePeriod);
		Map<AnniversaryNoticeExport, Boolean> result = new HashMap<AnniversaryNoticeExport, Boolean>();
		if (anniversaries.isEmpty()) {
			return result;
		}
		
		anniversaries.forEach((key, value) -> {
			AnniversaryNoticeExport export = AnniversaryNoticeExport.builder()
												.personalId(key.getPersonalId())
												.noticeDay(key.getNoticeDay().value)
												.seenDate(key.getSeenDate())
												.anniversary(key.getAnniversary())
												.anniversaryTitle(key.getAnniversaryTitle().v())
												.notificationMessage(key.getNotificationMessage().v())
												.build();
			result.put(export, value);
		});
		
		return result;
	}
	
}
