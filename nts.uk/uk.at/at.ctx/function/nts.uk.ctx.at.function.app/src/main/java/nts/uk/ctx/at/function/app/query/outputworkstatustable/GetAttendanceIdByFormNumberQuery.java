package nts.uk.ctx.at.function.app.query.outputworkstatustable;


import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

/**
 * Query: 帳票番号で勤怠項目IDを取得する
 * Get attendance item ID by form number
 * @author chinh.hm
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetAttendanceIdByFormNumberQuery {
    public List<Integer>getAttendanceId(DailyMonthlyClassification monthlyClassification, int screenNumber){
        return null;
    }
}
