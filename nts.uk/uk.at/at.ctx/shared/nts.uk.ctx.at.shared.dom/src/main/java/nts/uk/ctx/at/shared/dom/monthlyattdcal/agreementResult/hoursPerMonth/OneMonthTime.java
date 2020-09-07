package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.hoursPerMonth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.AgreementOneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * @author quang.nh1
 * 	１ヶ月時間
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
        if (upperLimitTime.v() >= errorTimeInMonth.getErrorTime().v()) {
            throw new BusinessException("Msg_59", "KMK008_66", "KMK008_129");
        }
        return new OneMonthTime(errorTimeInMonth, upperLimitTime);
    }

    //todo tobe continue [1] エラーチェック

    /**
     *[2] エラー時間を超えているか
     */
    public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
        Pair<Boolean, AgreementOneMonthTime> reusult = errorTimeInMonth.checkErrorTimeExceeded(applicationTime);
        return reusult;
    }

    /**
     *[3] アラーム時間を計算する
     */
    public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
        return errorTimeInMonth.calculateAlarmTime(applicationTime);
    }
}
