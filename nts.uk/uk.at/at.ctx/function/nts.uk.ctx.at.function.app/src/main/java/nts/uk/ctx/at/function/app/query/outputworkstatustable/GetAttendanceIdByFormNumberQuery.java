package nts.uk.ctx.at.function.app.query.outputworkstatustable;


import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DailyValue;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemUsedRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyAttendanceItemUsedRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Query: 帳票番号で勤怠項目IDを取得する
 * Get attendance item ID by form number
 *
 * @author chinh.hm
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetAttendanceIdByFormNumberQuery {

    @Inject
    private DailyAttendanceItemUsedRepository dailyRepository;

    @Inject
    private MonthlyAttendanceItemUsedRepository monthlyRepository;

    public List<Integer> getAttendanceId(DailyMonthlyClassification classification, int screenNumber) {
        val cid = AppContexts.user().companyId();
        if (classification == DailyMonthlyClassification.DAILY) {

            return dailyRepository.getAllDailyItemId(cid, BigDecimal.valueOf(screenNumber));
        } else
            return monthlyRepository.getAllMonthlyItemId(cid, screenNumber);

    }
}
