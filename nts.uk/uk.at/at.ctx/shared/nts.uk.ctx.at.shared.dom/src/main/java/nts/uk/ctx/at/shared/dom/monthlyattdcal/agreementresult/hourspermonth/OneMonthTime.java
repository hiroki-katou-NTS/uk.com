package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.OverState;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * @author quang.nh1
 * １ヶ月時間
 */
@Getter
@AllArgsConstructor
public class OneMonthTime extends ValueObject {

    /**
     * エラーアラーム時間
     */
    private ErrorTimeInMonth errorTimeInMonth;

    /**
     * 上限時間
     */
    private AgreementOneMonthTime upperLimitTime;

    /**
     * [C-1] １ヶ月時間
     */
    public static OneMonthTime create(ErrorTimeInMonth errorTimeInMonth, AgreementOneMonthTime upperLimitTime) {
        if (upperLimitTime.v() < errorTimeInMonth.getErrorTime().v()) {
            throw new BusinessException("Msg_59", "KMK008_66", "KMK008_129");
        }
        return new OneMonthTime(errorTimeInMonth, upperLimitTime);
    }

    /**
     * [1] エラーチェック
     * input.対象時間 <= エラーアラーム時間.アラーム時間：
     * 　超過状態←"正常"
     * else
     * input.対象時間 <= エラーアラーム時間.エラー時間：
     * 　超過状態←"アラーム時間超過"
     * else
     * input.対象時間 <= 上限時間：
     * 　超過状態←"エラー時間超過"
     * else
     * 　超過状態←"上限時間超過"
     */
    public OverState errorCheck(AttendanceTimeMonth applicationTime) {
        int value;
        if (applicationTime.lessThanOrEqualTo(errorTimeInMonth.getAlarmTime().v())) {
            value = OverState.NORMAL.value;
        } else if (applicationTime.lessThanOrEqualTo(errorTimeInMonth.getErrorTime().v())) {
            value = OverState.ALARM_OVER.value;
        } else if (applicationTime.lessThanOrEqualTo(upperLimitTime.v())) {
            value = OverState.ERROR_OVER.value;
        } else {
            value = OverState.UPPER_LIMIT_OVER.value;
        }
        return EnumAdaptor.valueOf(value, OverState.class);
    }

    /**
     * [2] エラー時間を超えているか
     */
    public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
        return errorTimeInMonth.checkErrorTimeExceeded(applicationTime);
    }

    /**
     * [3] アラーム時間を計算する
     */
    public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
        return errorTimeInMonth.calculateAlarmTime(applicationTime);
    }
}
