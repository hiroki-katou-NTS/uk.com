package nts.uk.ctx.at.record.dom.agreementbasicsettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;

import javax.ws.rs.GET;

/**
 * 	３６協定1ヶ月
 */
@Getter
@Setter
public class AgreementsOneMonth extends ValueObject{
    /**
     * 	基本設定
     */
  private OneMonthTime basicSetting;

    /**
     * 特例条項による上限
     */
  private OneMonthTime upperLimitDueToSpecialProvisions;

    /**
     * [C-0] ３６協定1ヶ月 (基本設定,特例条項による上限)
     * @param basicSetting
     * @param upperLimitDueToSpecialProvisions
     */
  public AgreementsOneMonth(OneMonthTime basicSetting,OneMonthTime upperLimitDueToSpecialProvisions){
      this.basicSetting = basicSetting;
      this.upperLimitDueToSpecialProvisions = upperLimitDueToSpecialProvisions;
  }
 // 	[1] エラーチェック TODO : đang check EA
  public AgreementTimeStatusOfMonthly checkError(AttendanceTimeMonth agreementTargetTime,
                                                 AttendanceTimeMonth hoursSubjectToLegalUpperLimit,
                                                 ErrorTimeInMonth applicationTime ){
        return null;
  }
  //	[2] 特例条項による上限のエラー時間を超えているか
  public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime){

        return basicSetting.checkErrorTimeExceeded(applicationTime);
  }
  // 	[3] アラーム時間を計算する
    public AgreementOneMonthTime calculateAlarmTime(AgreementOneMonthTime applicationTime) {
        return basicSetting.calculateAlarmTime(applicationTime);
    }

}
