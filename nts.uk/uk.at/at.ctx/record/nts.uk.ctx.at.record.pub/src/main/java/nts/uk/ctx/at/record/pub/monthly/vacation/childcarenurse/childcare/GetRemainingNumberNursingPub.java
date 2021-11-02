package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import nts.arc.time.GeneralDate;

public interface GetRemainingNumberNursingPub {

    /**
     * @param cId 会社ID
     * @param sId 社員ID
     * @param date 基準日
     * @return 子の看護介護休暇集計結果
     */
    // [RQ726]基準日時点の介護残数を取得する
    ChildCareNursePeriodExport getRemainingNumberNursing(String cId, String sId, GeneralDate date);
}
