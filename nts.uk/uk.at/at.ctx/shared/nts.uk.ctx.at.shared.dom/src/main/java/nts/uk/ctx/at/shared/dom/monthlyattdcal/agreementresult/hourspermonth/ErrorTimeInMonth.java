package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * @author quang.nh1
 * 1ヶ月のエラーアラーム時間
 */
@Getter
public class ErrorTimeInMonth extends ValueObject {

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
    public ErrorTimeInMonth(AgreementOneMonthTime errorTime, AgreementOneMonthTime alarmTime) {
        if (errorTime.v() <= alarmTime.v()) {
            throw new BusinessException("Msg_59", "KMK008_67", "KMK008_66");
        }
        this.errorTime = errorTime;
        this.alarmTime = alarmTime;
    }

    /**
     * [1] エラー時間を超えているか
     */
    public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
        return new ImmutablePair<>(errorTime.v() < applicationTime.v(), errorTime);
    }

    /**
     * [2] アラーム時間を計算する
     */
    public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
        val calculateAlarmTime =  applicationTime.v() - (errorTime.v() - alarmTime.v());

        return calculateAlarmTime > 0 ? new AgreementOneMonthTime(calculateAlarmTime)  : new AgreementOneMonthTime(0);
    }
}
