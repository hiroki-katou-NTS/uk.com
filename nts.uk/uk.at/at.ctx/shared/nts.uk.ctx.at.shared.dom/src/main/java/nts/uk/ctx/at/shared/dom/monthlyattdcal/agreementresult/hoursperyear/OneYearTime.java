package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
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
        if (upperLimitTime.v() >= errorTimeInYear.getErrorTime().v()) {
            throw new BusinessException("Msg_59", "KMK008_66", "KMK008_129");
        }
        return new OneYearTime(errorTimeInYear, upperLimitTime);
    }

    //todo tobe continue [1] エラーチェック

    /**
     *[2] エラー時間を超えているか
     */
    public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime) {
        Pair<Boolean, AgreementOneYearTime> reusult = errorTimeInYear.checkErrorTimeExceeded(applicationTime);
        return reusult;
    }

    /**
     *[3] アラーム時間を計算する
     */
    public AgreementOneYearTime calculateAlarmTime(AgreementOneYearTime applicationTime) {
        return errorTimeInYear.calculateAlarmTime(applicationTime);
    }
}
