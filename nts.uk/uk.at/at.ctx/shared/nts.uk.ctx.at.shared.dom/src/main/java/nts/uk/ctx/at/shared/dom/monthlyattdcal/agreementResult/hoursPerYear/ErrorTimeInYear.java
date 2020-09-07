package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.hoursPerYear;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.AgreementOneMonthTime;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * @author quang.nh1
 * 1年間のエラーアラーム時間
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorTimeInYear extends ValueObject {

    /**
     * エラー時間
     */
    private AgreementOneMonthTime errorTime;

    /**
     * アラーム時間
     */
    private AgreementOneMonthTime alarmTime;

    /**
     * [C-1] 1ヶ月のエラーアラーム時間
     */
    public ErrorTimeInYear create(AgreementOneMonthTime errorTime, AgreementOneMonthTime alarmTime) {
        if (errorTime.v() >= alarmTime.v()) {
            throw new BusinessException("Msg_59", "KMK008_67", "KMK008_66");
        }
        return new ErrorTimeInYear(errorTime, alarmTime);
    }

    /**
     * [1] エラー時間を超えているか
     */
    public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
        Pair<Boolean, AgreementOneMonthTime> reusult = new ImmutablePair<>(errorTime.v() < applicationTime.v(), errorTime);
        return reusult;
    }

    /**
     * [2] アラーム時間を計算する
     */
    public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
        val calculateAlarmTime =  applicationTime.v() - (errorTime.v() - alarmTime.v());

        return calculateAlarmTime > 0 ? new AgreementOneMonthTime(calculateAlarmTime)  : new AgreementOneMonthTime(0);
    }
}
