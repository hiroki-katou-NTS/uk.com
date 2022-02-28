/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.Optional;

/**
 * The Interface SWorkTimeHistItemRepository.
 */
public interface SWorkTimeHistItemRepository {

    /**
     * Adds the.
     *
     * @param domain the domain
     */
    void add(ShortWorkTimeHistoryItem domain);

    /**
     * Adds all
     *
     * @param domain the domain
     */
    void addAll(List<ShortWorkTimeHistoryItem> domains);

    /**
     * Update.
     *
     * @param domain the domain
     */
    void update(ShortWorkTimeHistoryItem domain);

    /**
     * Update all
     *
     * @param domain the domain
     * @author lanlt
     */
    void updateAll(List<ShortWorkTimeHistoryItem> domains);

    /**
     * Find by key.
     *
     * @param empId  the emp id
     * @param histId the hist id
     * @return the optional
     */
    Optional<ShortWorkTimeHistoryItem> findByKey(String empId, String histId);

    void delete(String sid, String hist);

    /**
     * Find by emp and period.
     *
     * @param empIdList the emp id list
     * @param date      the date
     * @return the map
     */
    List<ShortWorkTimeHistoryItem> findByHistIds(List<String> histIds);

    List<Object[]> findByHistIdsCPS013(List<String> histIds);

    /**
     * 社員の短時間勤務履歴を期間で取得する
     *
     * @param employeeIds
     * @param period
     * @return
     */
    List<ShortWorkTimeHistoryItem> findWithSidDatePeriod(String companyId, List<String> employeeIds, DatePeriod period);

    /**
     * 社員を指定して年月日時点の履歴項目を取得する
     *
     * @param sid      社員ID
     * @param baseDate 年月日
     * @return
     */
    Optional<ShortWorkTimeHistoryItem> getShortWorkTimeHistoryItemBySidAndBaseDate(String sid, GeneralDate baseDate);

    /**
     * 社員IDリストを指定して年月日時点の履歴項目を取得する
     *
     * @param sids     社員IDリスト
     * @param baseDate 年月日
     * @return
     */
    List<ShortWorkTimeHistoryItem> getShortWorkTimeHistoryItemBySidsAndBaseDate(List<String> sids, GeneralDate baseDate);

    /**
     * 社員を指定して年月日時点の履歴項目を取得する ( 社員ID, 年月日 ) ver2
     *
     * @param employeeId 社員ID
     * @param baseDate   年月日
     * @return the optional
     */
    Optional<ShortWorkTimeHistoryItem> findByEmployeeIdAndDate(String employeeId, GeneralDate baseDate);

    /**
     * 社員IDリストを指定して年月日時点の履歴項目を取得する ( List<社員ID>, 年月日 ) ver2
     *
     * @param employeeIdList <社員ID>
     * @param baseDate       年月日
     * @return the optional
     */
    List<ShortWorkTimeHistoryItem> findByEmployeeIdListAndDate(List<String> employeeIdList, GeneralDate baseDate);


}
