package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.care;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.ChildCareNursePeriodImport;

/**
 * @author anhnm
 *
 */
public interface GetRemainingNumberNursingAdapter {

    /**
     * [RQ726]基準日時点の介護残数を取得する
     * @param cId
     * @param sId
     * @param date
     * @return
     */
    ChildCareNursePeriodImport getRemainingNumberNursing(String cId, String sId, GeneralDate date);
}
