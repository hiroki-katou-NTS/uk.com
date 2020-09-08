package nts.uk.ctx.at.record.dom.agreementbasicsettings;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.hoursPerYear.OneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * ３６協定1年間
 */
public class AgreementsOneYear extends ValueObject {

    //	基本設定
    private OneYearTime basicSetting;
    // 特例条項による上限
    private OneYearTime upperLimitDueToSpecialProvisions;

    /**
     * 	[C-0] ３６協定1年間 (基本設定,特例条項による上限)
     */
    public AgreementsOneYear(OneYearTime basicSetting,OneYearTime upperLimitDueToSpecialProvisions){
        this.basicSetting = basicSetting;
        this.upperLimitDueToSpecialProvisions = upperLimitDueToSpecialProvisions;
    }
    // 	[1] エラーチェック TODO
    public AgreementTimeStatusOfMonthly checkError(AttendanceTimeYear agreementTargetTime,AttendanceTimeYear hoursSubjectToLegalUpperLimit){
            return AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY;
    }
    // 	[2] 特例条項による上限のエラー時間を超えているか
    public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime){

        return basicSetting.checkErrorTimeExceeded(applicationTime);
    }
    // 	[3] アラーム時間を計算する
    public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
        return basicSetting.calculateAlarmTime(applicationTime);
    }
}
