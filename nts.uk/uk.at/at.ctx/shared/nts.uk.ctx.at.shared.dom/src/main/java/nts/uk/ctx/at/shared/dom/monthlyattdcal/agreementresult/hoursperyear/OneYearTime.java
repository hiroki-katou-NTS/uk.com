package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.OverState;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * @author quang.nh1
 * 	１年間時間
 */
@Getter
@AllArgsConstructor
public class OneYearTime extends ValueObject {

    /**
     * エラーアラーム時間
     */
    private ErrorTimeInYear errorTimeInYear;

    /**
     * 上限時間
     */
    private AgreementOneYearTime upperLimitTime;

    /**
     * [C-1] １ヶ月時間
     */
    public static OneYearTime create(ErrorTimeInYear errorTimeInYear, AgreementOneYearTime upperLimitTime) {
        if (upperLimitTime.v() < errorTimeInYear.getErrorTime().v()) {
            throw new BusinessException("Msg_59", "KMK008_66", "KMK008_129");
        }
        return new OneYearTime(errorTimeInYear, upperLimitTime);
    }

    /**
     * [1] エラーチェック
     */
    public OverState checkError(AttendanceTimeYear attendanceTimeYear) {
        if (upperLimitTime.v() <= errorTimeInYear.getErrorTime().v()) {
            throw new BusinessException("Msg_59");
        }

        //TODO does not describe the handling logic
        return OverState.NORMAL;
    }



    /**
     *[2] エラー時間を超えているか
     */
    public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime) {
        return errorTimeInYear.checkErrorTimeExceeded(applicationTime);
    }

    /**
     *[3] アラーム時間を計算する
     */
    public AgreementOneYearTime calculateAlarmTime(AgreementOneYearTime applicationTime) {
        return errorTimeInYear.calculateAlarmTime(applicationTime);
    }
}
