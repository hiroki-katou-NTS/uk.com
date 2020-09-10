package nts.uk.ctx.at.record.dom.monthly.registrationOfApprovers;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Unit;

import java.util.List;

/**
 * 履歴を追加可能な開始日か
 *
 * @author Nguyen Huy Quang
 */
public class TimeStartCanAddHistory {

    /**
     * [1] 確認する
     */
    public static boolean checkAdd(Require require, Unit unit, String workplaceId, GeneralDate startDate){

        if (unit.value == EnumAdaptor.valueOf(Unit.WORKPLACE.value, Unit.class).value){
            List<Approver36AgrByCompany> agrByCompany = require.getByCompanyIdFromDate(startDate);
            return agrByCompany.size() != 0;
        }

        List<Approver36AgrByWorkplace> agrByWorkplaces = require.getByWorkplaceIdFromDate(workplaceId,startDate);

        return agrByWorkplaces.size() != 0;
    }

    public interface Require {

        //[R-1] 会社の承認者を取得する
        List<Approver36AgrByCompany> getByCompanyIdFromDate(GeneralDate date);

        //[R-2] 職場の承認者を取得する
        List<Approver36AgrByWorkplace> getByWorkplaceIdFromDate(String workplaceId, GeneralDate date);
    }
}
