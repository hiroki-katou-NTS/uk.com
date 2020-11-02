package nts.uk.ctx.bs.person.pubimp.anniversary;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryNotice;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.service.AnniversaryDomainService;
import nts.uk.ctx.bs.person.pub.anniversary.AnniversaryNoticeExport;
import nts.uk.ctx.bs.person.pub.anniversary.AnniversaryNoticePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

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
