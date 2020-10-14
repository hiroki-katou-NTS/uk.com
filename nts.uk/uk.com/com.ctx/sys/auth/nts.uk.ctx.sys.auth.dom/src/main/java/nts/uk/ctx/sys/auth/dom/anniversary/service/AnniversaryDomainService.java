package nts.uk.ctx.sys.auth.dom.anniversary.service;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class AnniversaryDomainService {


    @Inject
    AnniversaryRepository repository;

    //DomainService 新記念日があるか
    public boolean isTodayHaveNewAnniversary() {
        String loginPersonalId = AppContexts.user().personId();
        List<AnniversaryNotice> anniversaryNotices = repository.getTodayAnniversary(GeneralDate.today(), loginPersonalId).stream()
                .filter(item -> item.isNewAnniversary(GeneralDate.today()))
                .collect(Collectors.toList());
        return !anniversaryNotices.isEmpty();
    }

    //DomainService 期間で記念日情報を取得する
    public Map<AnniversaryNotice, Boolean> setFlag(DatePeriod datePeriod) {
        GeneralDate dateStart = datePeriod.start();
        GeneralDate dateEnd = datePeriod.end();
        String loginPersonalId = AppContexts.user().personId();
        List<AnniversaryNotice> anniversaryNotices = new ArrayList<>();
        if (dateStart.compareTo(dateEnd) == 0 && dateEnd.compareTo(GeneralDate.today()) == 0) {
            anniversaryNotices = repository.getTodayAnniversary(GeneralDate.today(), loginPersonalId);
        }
        if (dateStart.compareTo(GeneralDate.today()) != 0 || dateEnd.compareTo(GeneralDate.today()) != 0) {
            if (dateStart.compareTo(dateEnd) == 0) {
                anniversaryNotices = repository.getByDatePeriod(new DatePeriod(dateStart, dateEnd), loginPersonalId);
            } else {
                GeneralDate date = GeneralDate.ymd(dateStart.year(), 12, 31);
                anniversaryNotices = repository.getByDatePeriod(new DatePeriod(dateStart, date), loginPersonalId);
                List<AnniversaryNotice> anniversaryNoticeList = repository.getByDatePeriod(new DatePeriod(date.addDays(1), dateEnd), loginPersonalId);
                anniversaryNotices.addAll(anniversaryNoticeList);
            }
        }
        Map<AnniversaryNotice, Boolean> anniversaryNoticeMap = new HashMap<>();
        for (AnniversaryNotice anniversaryNotice : anniversaryNotices) {
            anniversaryNoticeMap.put(anniversaryNotice, anniversaryNotice.isNewAnniversary(dateEnd));
        }
        return anniversaryNoticeMap;
    }
}
