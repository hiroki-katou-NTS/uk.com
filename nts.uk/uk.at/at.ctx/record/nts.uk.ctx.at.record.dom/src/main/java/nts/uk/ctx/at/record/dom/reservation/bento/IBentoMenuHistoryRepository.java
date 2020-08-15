package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.Optional;

public interface IBentoMenuHistoryRepository {
    Optional<BentoMenuHistory> findByCompanyId(String companyId);
    void add(BentoMenuHistory item);
    void update(BentoMenuHistory item);
    void delete(String companyId, String historyId);
}
