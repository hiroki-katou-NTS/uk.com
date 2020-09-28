package nts.uk.ctx.at.shared.dom.standardtime;


import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.OverState;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.persistence.internal.xr.ValueObject;


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
        //input.対象時間 <= エラーアラーム時間.アラーム時間：
        //        　超過状態←"正常"
        //        else
        //input.対象時間 <= エラーアラーム時間.エラー時間：
        //        　超過状態←"アラーム時間超過"
        //        else
        //input.対象時間 <= 上限時間：
        //       　超過状態←"エラー時間超過"
        //        else
        //        　超過状態←"上限時間超過"
      int value;
      if (agreementTargetTime.lessThanOrEqualTo(applicationTime.getAlarmTime().v())) {
          value = OverState.NORMAL.value;
      } else if (agreementTargetTime.lessThanOrEqualTo(applicationTime.getErrorTime().v())) {
          value = OverState.ALARM_OVER.value;
      } else if (agreementTargetTime.lessThanOrEqualTo(upperLimitDueToSpecialProvisions.getUpperLimitTime().v())) {
          value = OverState.ERROR_OVER.value;
      } else {
          value = OverState.UPPER_LIMIT_OVER.value;
      }

      int values;

      if (hoursSubjectToLegalUpperLimit.lessThanOrEqualTo(applicationTime.getAlarmTime().v())) {
          values = OverState.NORMAL.value;
      } else if (hoursSubjectToLegalUpperLimit.lessThanOrEqualTo(applicationTime.getErrorTime().v())) {
          values = OverState.ALARM_OVER.value;
      } else if (hoursSubjectToLegalUpperLimit.lessThanOrEqualTo(upperLimitDueToSpecialProvisions.getUpperLimitTime().v())) {
          values = OverState.ERROR_OVER.value;
      } else {
          values = OverState.UPPER_LIMIT_OVER.value;
      }
      int rsValue;

      //超過状態=アラーム時間超過：
      //月別実績の36協定時間状態←"特例限度アラーム時間超過"
      // 超過状態=エラー時間超過：
      // 月別実績の36協定時間状態←"特例限度エラー時間超過"
      // 超過状態=上限時間超過：
      // 月別実績の36協定時間状態←"特別条項の上限時間超過"

      if (value != OverState.NORMAL.value) {
          if (value == OverState.ALARM_OVER.value) {
              rsValue = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM.value;
          } else if (value == OverState.ERROR_OVER.value) {
              rsValue = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.value;
          } else {
              rsValue = AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY.value;
          }
      }
      // 超過状態=正常：
      //　月別実績の36協定時間状態←"正常"
      //超過状態=アラーム時間超過：
      // 　月別実績の36協定時間状態←"限度アラーム時間超過"
      //超過状態=エラー時間超過 or 超過状態=上限時間超過：
      //　月別実績の36協定時間状態←"限度エラー時間超過"
      if (values == OverState.NORMAL.value) {
          rsValue = AgreementTimeStatusOfMonthly.NORMAL_SPECIAL.value;
      } else if (values == OverState.ALARM_OVER.value) {
          rsValue = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP.value;
      }else if (values==OverState.ERROR_OVER.value||values==OverState.UPPER_LIMIT_OVER.value){
          rsValue = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP.value;
      }

      return EnumAdaptor.valueOf(value, AgreementTimeStatusOfMonthly.class);
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
