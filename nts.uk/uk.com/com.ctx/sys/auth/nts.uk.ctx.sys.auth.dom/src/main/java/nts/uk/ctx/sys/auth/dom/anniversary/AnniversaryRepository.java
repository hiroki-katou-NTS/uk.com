package nts.uk.ctx.sys.auth.dom.anniversary;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.Optional;

/**
 * Repository 個人の記念日情報
 */
public interface AnniversaryRepository {

    /**
     * Add new AnniversaryNotice
     *
     * @param anniversaryNotice
     */
    void insert(AnniversaryNotice anniversaryNotice);

    /**
     * Update AnniversaryNotice
     *
     * @param anniversaryNotice
     */
    void update(AnniversaryNotice anniversaryNotice);

    /**
     * Delete AnniversaryNotice
     *
     * @param anniversaryNotice
     */
    void delete(AnniversaryNotice anniversaryNotice);

    /**
     * Find AnniversaryNotice by personalId and anniversary
     *
     * @param personalId
     * @param anniversary
     */
    Optional<AnniversaryNotice> getByPersonalIdAndAnniversary(String personalId, GeneralDate anniversary);

    /**
     * Find List AnniversaryNotice by personalId
     *
     * @param personalId
     */
    List<AnniversaryNotice> getByPersonalId(String personalId);

    /**
     * Find List AnniversaryNotice by anniversary
     *
     * @param anniversary
     */
    List<AnniversaryNotice> getTodayAnniversary(GeneralDate anniversary, String loginPersonalId);

    /**
     * Find List AnniversaryNotice by datePeriod
     *
     * @param datePeriod
     */
    List<AnniversaryNotice> getByDatePeriod(DatePeriod datePeriod, String loginPersonalId);
}

