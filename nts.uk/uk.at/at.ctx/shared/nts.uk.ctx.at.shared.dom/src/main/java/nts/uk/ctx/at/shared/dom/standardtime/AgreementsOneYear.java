package nts.uk.ctx.at.record.dom.agreementbasicsettings;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * ３６協定1年間
 */
@Getter
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
    // 	[1] エラーチェック TODO: PENDING issues/30574
    public AgreementTimeStatusOfMonthly checkError(AttendanceTimeYear agreementTargetTime,AttendanceTimeYear hoursSubjectToLegalUpperLimit){
            return null;
    }
    // 	[2] 特例条項による上限のエラー時間を超えているか
    public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime){

        return basicSetting.checkErrorTimeExceeded(applicationTime);
    }
    // 	[3] アラーム時間を計算する
    public AgreementOneYearTime calculateAlarmTime(AgreementOneYearTime applicationTime) {
        return basicSetting.calculateAlarmTime(applicationTime);
    }
}
