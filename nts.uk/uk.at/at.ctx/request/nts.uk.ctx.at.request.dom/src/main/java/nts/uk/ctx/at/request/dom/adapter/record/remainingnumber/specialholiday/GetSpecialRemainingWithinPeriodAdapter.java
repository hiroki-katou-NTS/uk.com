package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.Optional;

public interface GetSpecialRemainingWithinPeriodAdapter {
    /**
     * RequestList273 (NO.273) 期間中の特別休暇残数を取得
     * @param companyId                 会社ID
     * @param employeeId                社員ID
     * @param aggrPeriod                集計開始日 , 集計終了日
     * @param achievementsReferenceOnly 実績のみ参照区分
     * @param criteriaDate              基準日
     * @param specialLeaveCode          特別休暇コード
     * @param isOverWriteOpt            上書きフラグ
     * @param forOverWriteList          List<上書き用の暫定管理データ>
     * @param prevHolidayOver60h        前回の特別休暇の集計結果
     */
    TotalResultOfSpecialLeaveImport algorithm(
            String companyId,
            String employeeId,
            DatePeriod aggrPeriod,
            boolean achievementsReferenceOnly,
            GeneralDate criteriaDate,
            Integer specialLeaveCode,
            Optional<Boolean> isOverWriteOpt,
            Optional<List<Object>> forOverWriteList,
            Optional<Object> prevHolidayOver60h
    );
}
